package com.gpp.anvay.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.gpp.anvay.R;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;
import com.gpp.anvay.navigation.NavigationManager;

import java.util.Collections;

/**
 * AR Indoor Navigation screen rendering directional overlays on top of the live camera preview.
 */
public class ARNavigationActivity extends AppCompatActivity implements NavigationManager.NavigationListener {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2001;

    private NavigationManager navManager;
    private LocationRepository locationRepo;

    private TextureView cameraTextureView;
    private View viewCameraFallback;
    private AROverlayView arOverlayView;

    private ImageView btnExitAR;
    private TextView tvARDestination;
    private TextView tvARStepCounter;
    private TextView tvARCurrentFloor;
    private TextView tvARDestFloor;
    private LinearLayout layoutStaircaseWarning;
    private TextView tvStaircaseWarningText;
    private TextView tvCameraFallbackNotice;
    private TextView tvARInstruction;
    private MaterialButton btnARPrev;
    private MaterialButton btnARNext;
    private MaterialButton btnARRecalculate;
    private MaterialButton btnARExit;

    // Camera2 variables
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private CaptureRequest.Builder previewRequestBuilder;
    private Handler backgroundHandler;
    private boolean isCameraReady = false;

    private LocationItem destinationItem;
    private String destinationId;
    private String startId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_navigation);

        navManager = NavigationManager.getInstance();
        locationRepo = LocationRepository.getInstance();

        initViews();
        extractIntentData();
        setupListeners();
        setupTextureView();
    }

    private void initViews() {
        cameraTextureView = findViewById(R.id.cameraTextureView);
        viewCameraFallback = findViewById(R.id.viewCameraFallback);
        arOverlayView = findViewById(R.id.arOverlayView);

        btnExitAR = findViewById(R.id.btnExitAR);
        tvARDestination = findViewById(R.id.tvARDestination);
        tvARStepCounter = findViewById(R.id.tvARStepCounter);
        tvARCurrentFloor = findViewById(R.id.tvARCurrentFloor);
        tvARDestFloor = findViewById(R.id.tvARDestFloor);
        layoutStaircaseWarning = findViewById(R.id.layoutStaircaseWarning);
        tvStaircaseWarningText = findViewById(R.id.tvStaircaseWarningText);
        tvCameraFallbackNotice = findViewById(R.id.tvCameraFallbackNotice);
        tvARInstruction = findViewById(R.id.tvARInstruction);
        btnARPrev = findViewById(R.id.btnARPrev);
        btnARNext = findViewById(R.id.btnARNext);
        btnARRecalculate = findViewById(R.id.btnARRecalculate);
        btnARExit = findViewById(R.id.btnARExit);
    }

    private void extractIntentData() {
        destinationItem = (LocationItem) getIntent().getSerializableExtra("location_item");
        destinationId = getIntent().getStringExtra("destination_id");
        startId = getIntent().getStringExtra("start_id");

        if (destinationItem != null) {
            destinationId = destinationItem.getId();
        } else if (destinationId != null) {
            destinationItem = locationRepo.getLocationById(destinationId);
        }

        if (destinationItem != null) {
            tvARDestination.setText(destinationItem.getRoomNumber() + " (" + destinationItem.getName() + ")");
            tvARDestFloor.setText("Target: " + destinationItem.getFloor());
        }

        if (!navManager.isNavigating()) {
            navManager.startNavigation(startId, destinationId);
        }
    }

    private void setupListeners() {
        View.OnClickListener exitListener = v -> finish();
        btnExitAR.setOnClickListener(exitListener);
        btnARExit.setOnClickListener(exitListener);

        btnARNext.setOnClickListener(v -> {
            if (navManager.hasArrived()) {
                Toast.makeText(ARNavigationActivity.this, "Destination reached!", Toast.LENGTH_SHORT).show();
            } else {
                navManager.nextStep();
            }
        });

        btnARPrev.setOnClickListener(v -> navManager.previousStep());

        btnARRecalculate.setOnClickListener(v -> {
            navManager.startNavigation(startId, destinationId);
            Toast.makeText(ARNavigationActivity.this, "Route restarted.", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupTextureView() {
        cameraTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
                checkPermissionAndOpenCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {}

            @Override
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
                closeCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {}
        });
    }

    private void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            // Request camera permission at runtime strictly when AR starts
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                showCameraFallback("Camera permission is required for live view. Continuing with fallback guidance.");
            }
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (manager == null) {
            showCameraFallback("AR camera is unavailable. Continue with standard navigation.");
            return;
        }

        try {
            String backCameraId = null;
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    backCameraId = cameraId;
                    break;
                }
            }

            if (backCameraId == null && manager.getCameraIdList().length > 0) {
                backCameraId = manager.getCameraIdList()[0];
            }

            if (backCameraId == null) {
                showCameraFallback("AR camera is unavailable. Continue with standard navigation.");
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            manager.openCamera(backCameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    cameraDevice = camera;
                    startCameraPreview();
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    closeCamera();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    closeCamera();
                    showCameraFallback("AR camera could not be started. Continue with standard navigation.");
                }
            }, null);

        } catch (Exception e) {
            showCameraFallback("AR camera is unavailable. Continue with standard navigation.");
        }
    }

    private void startCameraPreview() {
        if (cameraDevice == null || !cameraTextureView.isAvailable()) return;

        try {
            SurfaceTexture texture = cameraTextureView.getSurfaceTexture();
            if (texture == null) return;

            texture.setDefaultBufferSize(1280, 720);
            Surface surface = new Surface(texture);

            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (cameraDevice == null) return;
                    captureSession = session;
                    try {
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        captureSession.setRepeatingRequest(previewRequestBuilder.build(), null, backgroundHandler);
                        isCameraReady = true;
                        runOnUiThread(() -> {
                            viewCameraFallback.setVisibility(View.GONE);
                            tvCameraFallbackNotice.setText("AR camera tracking active • Step navigation synchronized");
                        });
                    } catch (Exception e) {
                        showCameraFallback("AR camera preview issue. Continuing with overlay guidance.");
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    showCameraFallback("AR camera is unavailable. Continue with standard navigation.");
                }
            }, null);

        } catch (Exception e) {
            showCameraFallback("AR camera is unavailable. Continue with standard navigation.");
        }
    }

    private void showCameraFallback(String message) {
        runOnUiThread(() -> {
            viewCameraFallback.setVisibility(View.VISIBLE);
            tvCameraFallbackNotice.setText(message);
        });
    }

    private void closeCamera() {
        isCameraReady = false;
        if (captureSession != null) {
            try {
                captureSession.close();
            } catch (Exception ignored) {}
            captureSession = null;
        }
        if (cameraDevice != null) {
            try {
                cameraDevice.close();
            } catch (Exception ignored) {}
            cameraDevice = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navManager.addListener(this);
        if (navManager.isNavigating()) {
            onRouteCalculated(navManager.getCurrentRoute());
            NavigationStep curr = navManager.getCurrentStep();
            if (curr != null) {
                onStepChanged(navManager.getCurrentStepIndex(), curr);
            }
        }
        if (cameraTextureView.isAvailable()) {
            checkPermissionAndOpenCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
        navManager.removeListener(this);
    }

    // ==========================================
    // NavigationManager Callbacks for AR Guidance
    // ==========================================

    @Override
    public void onRouteCalculated(NavigationRoute route) {
        if (route == null || !route.isRouteAvailable()) {
            tvARInstruction.setText("Route unavailable.");
            return;
        }

        if (destinationItem != null) {
            tvARDestination.setText(destinationItem.getName());
            tvARDestFloor.setText("Target: " + destinationItem.getFloor());
        }
    }

    @Override
    public void onStepChanged(int stepIndex, NavigationStep step) {
        if (step == null) return;

        int total = navManager.getCurrentRoute() != null ? navManager.getCurrentRoute().getTotalSteps() : 0;
        tvARStepCounter.setText((stepIndex + 1) + "/" + total);
        tvARCurrentFloor.setText("Floor: " + step.getFloor());
        tvARInstruction.setText(step.getInstruction());

        boolean isStaircase = step.isStaircaseTransition();
        if (isStaircase) {
            layoutStaircaseWarning.setVisibility(View.VISIBLE);
            String stairText = step.getStaircaseName() != null ? step.getStaircaseName() : "Staircase";
            String targetFloor = step.getTargetFloor() != null ? step.getTargetFloor() : step.getFloor();
            tvStaircaseWarningText.setText("⚠ " + stairText + " ➔ Proceed to " + targetFloor);
        } else {
            layoutStaircaseWarning.setVisibility(View.GONE);
        }

        if (step.isArrival()) {
            btnARNext.setText("Destination Reached ✔");
            btnARNext.setBackgroundColor(ContextCompat.getColor(this, R.color.success_green));
        } else {
            btnARNext.setText("Next Step →");
            btnARNext.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
        }

        btnARPrev.setEnabled(stepIndex > 0);

        // Update AR overlay view
        ARDirection dir = step.getArDirection() != null ? step.getArDirection() : ARDirection.FORWARD;
        String destTitle = destinationItem != null ? destinationItem.getName() : "Destination";
        arOverlayView.updateGuidance(dir, step.getInstruction(), destTitle, step.getFloor(), isStaircase);
    }

    @Override
    public void onArrival(NavigationStep arrivalStep) {
        Toast.makeText(this, "You have reached your destination!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigationCancelled() {
        finish();
    }
}
