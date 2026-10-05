package com.gpp.anvay.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gpp.anvay.R;
import com.gpp.anvay.model.position.ARDirection;

/**
 * Custom AR HUD View overlaying live camera feed with directional guidance visuals.
 */
public class AROverlayView extends View {

    private ARDirection currentDirection = ARDirection.FORWARD;
    private String destinationName = "Destination";
    private String currentInstruction = "Proceed Ahead";
    private String currentFloor = "Ground Floor";
    private boolean isStaircase = false;

    private Paint arrowPaint;
    private Paint glowPaint;
    private Paint hudPaint;
    private Paint textPaint;
    private Paint targetPaint;

    private float pulseRadius = 0f;
    private ValueAnimator pulseAnimator;

    public AROverlayView(Context context) {
        super(context);
        init();
    }

    public AROverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AROverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary));

        glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(6f);
        glowPaint.setColor(ContextCompat.getColor(getContext(), R.color.accent_teal));

        hudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hudPaint.setStyle(Paint.Style.STROKE);
        hudPaint.setStrokeWidth(2f);
        hudPaint.setColor(Color.parseColor("#50818CF8"));
        hudPaint.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(36f);
        textPaint.setFakeBoldText(true);

        targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetPaint.setStyle(Paint.Style.STROKE);
        targetPaint.setStrokeWidth(8f);
        targetPaint.setColor(ContextCompat.getColor(getContext(), R.color.success_green));

        // Start subtle breathing pulse animation for AR guidance ring
        pulseAnimator = ValueAnimator.ofFloat(0f, 1f);
        pulseAnimator.setDuration(1500);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pulseAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        pulseAnimator.addUpdateListener(animation -> {
            pulseRadius = (float) animation.getAnimatedValue();
            invalidate();
        });
        pulseAnimator.start();
    }

    public void updateGuidance(ARDirection direction, String instruction, String destination, String floor, boolean isStaircaseTransition) {
        this.currentDirection = direction != null ? direction : ARDirection.FORWARD;
        this.currentInstruction = instruction != null ? instruction : "";
        this.destinationName = destination != null ? destination : "";
        this.currentFloor = floor != null ? floor : "";
        this.isStaircase = isStaircaseTransition;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) return;

        float centerX = width / 2.0f;
        float centerY = height / 2.0f + 40f; // Center slightly below middle for AR perspective

        // 1. Draw subtle AR Horizon & Grid Accents
        drawARHud(canvas, centerX, centerY, width, height);

        // 2. Draw Dynamic Directional Visual
        if (currentDirection == ARDirection.ARRIVAL) {
            drawArrivalTarget(canvas, centerX, centerY);
        } else if (currentDirection == ARDirection.STAIRCASE || isStaircase) {
            drawStaircaseGuidance(canvas, centerX, centerY);
        } else if (currentDirection == ARDirection.LEFT) {
            drawTurnArrow(canvas, centerX, centerY, -90f);
        } else if (currentDirection == ARDirection.RIGHT) {
            drawTurnArrow(canvas, centerX, centerY, 90f);
        } else {
            drawForwardArrow(canvas, centerX, centerY);
        }
    }

    private void drawARHud(Canvas canvas, float cx, float cy, int width, int height) {
        // Horizon line
        canvas.drawLine(width * 0.15f, cy + 120f, width * 0.85f, cy + 120f, hudPaint);

        // Perspective corridor lines converging toward center
        canvas.drawLine(width * 0.1f, height * 0.85f, cx - 80f, cy + 120f, hudPaint);
        canvas.drawLine(width * 0.9f, height * 0.85f, cx + 80f, cy + 120f, hudPaint);
    }

    private void drawForwardArrow(Canvas canvas, float cx, float cy) {
        // Animated pulsing base ring
        float baseRadius = 70f + (pulseRadius * 25f);
        glowPaint.setAlpha((int) (200 - (pulseRadius * 150)));
        glowPaint.setColor(ContextCompat.getColor(getContext(), R.color.accent_teal));
        canvas.drawCircle(cx, cy, baseRadius, glowPaint);

        // 3D Forward Arrow Path
        Path path = new Path();
        path.moveTo(cx, cy - 80f); // Tip
        path.lineTo(cx + 45f, cy - 10f); // Right wing
        path.lineTo(cx + 20f, cy - 10f); // Right notch
        path.lineTo(cx + 20f, cy + 60f); // Right base
        path.lineTo(cx - 20f, cy + 60f); // Left base
        path.lineTo(cx - 20f, cy - 10f); // Left notch
        path.lineTo(cx - 45f, cy - 10f); // Left wing
        path.close();

        arrowPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary));
        arrowPaint.setAlpha(240);
        canvas.drawPath(path, arrowPaint);

        // Arrow stroke
        glowPaint.setAlpha(255);
        glowPaint.setColor(Color.WHITE);
        canvas.drawPath(path, glowPaint);
    }

    private void drawTurnArrow(Canvas canvas, float cx, float cy, float angle) {
        canvas.save();
        canvas.rotate(angle, cx, cy);
        drawForwardArrow(canvas, cx, cy);
        canvas.restore();
    }

    private void drawStaircaseGuidance(Canvas canvas, float cx, float cy) {
        // Staircase Steps Glyphs
        float startY = cy + 50f;
        float stepHeight = 25f;
        float stepWidth = 100f;

        arrowPaint.setColor(ContextCompat.getColor(getContext(), R.color.warning_amber));
        arrowPaint.setAlpha(230);

        for (int i = 0; i < 4; i++) {
            float y = startY - (i * stepHeight);
            float x = cx - (stepWidth / 2f) + (i * 12f);
            RectF rect = new RectF(x, y, x + stepWidth, y + 16f);
            canvas.drawRoundRect(rect, 8f, 8f, arrowPaint);
        }

        // Upward chevron indicator
        Path path = new Path();
        path.moveTo(cx, cy - 70f);
        path.lineTo(cx + 35f, cy - 35f);
        path.lineTo(cx - 35f, cy - 35f);
        path.close();

        glowPaint.setColor(Color.WHITE);
        glowPaint.setAlpha(255);
        canvas.drawPath(path, glowPaint);
    }

    private void drawArrivalTarget(Canvas canvas, float cx, float cy) {
        // Pulsing target rings
        float r1 = 50f + (pulseRadius * 20f);
        float r2 = 90f + (pulseRadius * 15f);

        targetPaint.setColor(ContextCompat.getColor(getContext(), R.color.success_green));
        targetPaint.setAlpha(255);
        canvas.drawCircle(cx, cy, 35f, targetPaint);

        targetPaint.setAlpha((int) (200 - (pulseRadius * 120)));
        canvas.drawCircle(cx, cy, r1, targetPaint);
        canvas.drawCircle(cx, cy, r2, targetPaint);

        // Center checkmark
        Path checkPath = new Path();
        checkPath.moveTo(cx - 16f, cy);
        checkPath.lineTo(cx - 5f, cy + 12f);
        checkPath.lineTo(cx + 18f, cy - 14f);

        glowPaint.setColor(Color.WHITE);
        glowPaint.setStrokeWidth(7f);
        glowPaint.setAlpha(255);
        canvas.drawPath(checkPath, glowPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
    }
}
