package com.gpp.anvay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpp.anvay.R;
import com.gpp.anvay.data.AuthManager;
import com.gpp.anvay.model.UserProfile;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout tilFullName;
    private TextInputLayout tilIdNumber;
    private TextInputLayout tilEmail;
    private TextInputLayout tilDepartment;
    private TextInputLayout tilPassword;
    private TextInputLayout tilConfirmPassword;

    private TextInputEditText etFullName;
    private TextInputEditText etIdNumber;
    private TextInputEditText etEmail;
    private AutoCompleteTextView actvDepartment;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private ChipGroup chipGroupRole;
    private MaterialButton btnSignUp;

    private static final String[] DEPARTMENTS = new String[]{
            "Computer Engineering",
            "Information Technology",
            "Electronics & Telecommunication",
            "Civil Engineering",
            "Mechanical Engineering",
            "Electrical Engineering",
            "General Science & Humanities"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        setupDepartmentDropdown();
        setupListeners();
    }

    private void initViews() {
        tilFullName = findViewById(R.id.tilFullName);
        tilIdNumber = findViewById(R.id.tilIdNumber);
        tilEmail = findViewById(R.id.tilEmail);
        tilDepartment = findViewById(R.id.tilDepartment);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        etFullName = findViewById(R.id.etFullName);
        etIdNumber = findViewById(R.id.etIdNumber);
        etEmail = findViewById(R.id.etEmail);
        actvDepartment = findViewById(R.id.actvDepartment);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        chipGroupRole = findViewById(R.id.chipGroupRole);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setupDepartmentDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                DEPARTMENTS
        );
        actvDepartment.setAdapter(adapter);
    }

    private void setupListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.tvSignInLink).setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> performSignUp());
    }

    private void performSignUp() {
        String fullName = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
        String idNumber = etIdNumber.getText() != null ? etIdNumber.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String department = actvDepartment.getText() != null ? actvDepartment.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        // Reset errors
        tilFullName.setError(null);
        tilIdNumber.setError(null);
        tilEmail.setError(null);
        tilDepartment.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);

        boolean isValid = true;

        if (fullName.isEmpty()) {
            tilFullName.setError("Full name cannot be empty");
            isValid = false;
        }

        if (idNumber.isEmpty()) {
            tilIdNumber.setError("Student / Employee ID cannot be empty");
            isValid = false;
        }

        if (email.isEmpty()) {
            tilEmail.setError("Email address cannot be empty");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email address");
            isValid = false;
        }

        if (department.isEmpty()) {
            tilDepartment.setError("Please select your department");
            isValid = false;
        }

        String role = getSelectedRole();
        if (role == null || role.isEmpty()) {
            Toast.makeText(this, "Please select your role", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (password.isEmpty()) {
            tilPassword.setError("Password cannot be empty");
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Create UserProfile
        UserProfile newUser = new UserProfile(
                fullName,
                role,
                department,
                idNumber,
                email,
                "+91 98000 00000"
        );

        // Store demo account locally
        boolean registered = AuthManager.registerAccount(this, newUser, password);
        if (registered) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

            // Return to LoginActivity and pre-fill ID
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.putExtra("prefill_id", idNumber);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedRole() {
        int checkedChipId = chipGroupRole.getCheckedChipId();
        if (checkedChipId != View.NO_ID) {
            Chip checkedChip = findViewById(checkedChipId);
            if (checkedChip != null) {
                return checkedChip.getText().toString().trim();
            }
        }
        return "Student";
    }
}
