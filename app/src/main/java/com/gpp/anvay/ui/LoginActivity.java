package com.gpp.anvay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpp.anvay.R;
import com.gpp.anvay.data.AuthManager;
import com.gpp.anvay.data.PreferenceManager;
import com.gpp.anvay.model.UserProfile;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUserId;
    private TextInputLayout tilPassword;
    private TextInputEditText etUserId;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private MaterialButton btnContinueGuest;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceManager = new PreferenceManager(this);

        tilUserId = findViewById(R.id.tilUserId);
        tilPassword = findViewById(R.id.tilPassword);
        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnContinueGuest = findViewById(R.id.btnContinueGuest);

        setupDemoChips();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        btnContinueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueAsGuest();
            }
        });

        findViewById(R.id.tvForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });

        // Sign Up Link
        TextView tvSignUpLink = findViewById(R.id.tvSignUpLink);
        if (tvSignUpLink != null) {
            tvSignUpLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(signUpIntent);
                }
            });
        }

        handlePrefillId(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handlePrefillId(intent);
    }

    private void handlePrefillId(Intent intent) {
        if (intent != null && intent.hasExtra("prefill_id")) {
            String prefillId = intent.getStringExtra("prefill_id");
            if (prefillId != null && !prefillId.isEmpty()) {
                etUserId.setText(prefillId);
                etPassword.requestFocus();
            }
        }
    }

    private void setupDemoChips() {
        Chip chipStudent = findViewById(R.id.chipDemoStudent);
        Chip chipFaculty = findViewById(R.id.chipDemoFaculty);
        Chip chipStaff = findViewById(R.id.chipDemoStaff);
        Chip chipAdmin = findViewById(R.id.chipDemoAdmin);

        chipStudent.setOnClickListener(v -> {
            etUserId.setText("student");
            etPassword.setText("gpp123");
            tilUserId.setError(null);
            tilPassword.setError(null);
        });

        chipFaculty.setOnClickListener(v -> {
            etUserId.setText("faculty");
            etPassword.setText("gpp123");
            tilUserId.setError(null);
            tilPassword.setError(null);
        });

        chipStaff.setOnClickListener(v -> {
            etUserId.setText("staff");
            etPassword.setText("gpp123");
            tilUserId.setError(null);
            tilPassword.setError(null);
        });

        chipAdmin.setOnClickListener(v -> {
            etUserId.setText("admin");
            etPassword.setText("admin123");
            tilUserId.setError(null);
            tilPassword.setError(null);
        });
    }

    private void performLogin() {
        String userId = etUserId.getText() != null ? etUserId.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        tilUserId.setError(null);
        tilPassword.setError(null);

        AuthManager.AuthResult result = AuthManager.authenticate(this, userId, password);
        if (result.isSuccess()) {
            UserProfile profile = result.getUserProfile();
            preferenceManager.saveUserProfile(profile);
            preferenceManager.setLoggedIn(true);
            preferenceManager.setGuestMode(false);

            Toast.makeText(this, "Welcome, " + profile.getName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            if (userId.isEmpty()) {
                tilUserId.setError(result.getMessage());
            } else {
                tilPassword.setError(result.getMessage());
            }
            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void continueAsGuest() {
        UserProfile guest = AuthManager.createGuestProfile();
        preferenceManager.saveUserProfile(guest);
        preferenceManager.setLoggedIn(true);
        preferenceManager.setGuestMode(true);

        Toast.makeText(this, "Entering as Guest Explorer", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showForgotPasswordDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Account Credential Assistance")
                .setMessage("Student and Faculty credentials are synchronized with the Government Polytechnic Pune IT Cell.\n\n" +
                        "For password resets or ID verifications, please visit the Network Operations Center (Room G-05) or contact the IT Department Administrator.")
                .setPositiveButton("Understood", null)
                .show();
    }
}
