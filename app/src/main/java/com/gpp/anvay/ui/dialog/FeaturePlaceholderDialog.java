package com.gpp.anvay.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.gpp.anvay.R;

public class FeaturePlaceholderDialog extends Dialog {

    private final String title;
    private final String message;
    private final int iconResId;

    public FeaturePlaceholderDialog(@NonNull Context context, String title, String message, int iconResId) {
        super(context);
        this.title = title;
        this.message = message;
        this.iconResId = iconResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_feature_placeholder);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvTitle = findViewById(R.id.tvPlaceholderTitle);
        TextView tvMessage = findViewById(R.id.tvPlaceholderMessage);
        ImageView ivIcon = findViewById(R.id.ivPlaceholderIcon);
        MaterialButton btnDismiss = findViewById(R.id.btnPlaceholderDismiss);

        if (title != null) tvTitle.setText(title);
        if (message != null) tvMessage.setText(message);
        if (iconResId != 0) ivIcon.setImageResource(iconResId);

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void show(Context context, String title, String message, int iconResId) {
        new FeaturePlaceholderDialog(context, title, message, iconResId).show();
    }
}
