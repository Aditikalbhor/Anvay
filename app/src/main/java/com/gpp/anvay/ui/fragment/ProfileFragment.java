package com.gpp.anvay.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.gpp.anvay.R;
import com.gpp.anvay.data.PreferenceManager;
import com.gpp.anvay.model.UserProfile;
import com.gpp.anvay.ui.LoginActivity;
import com.gpp.anvay.ui.admin.AdminDashboardActivity;

import java.util.List;

public class ProfileFragment extends Fragment {

    private PreferenceManager preferenceManager;
    private TextView tvName;
    private TextView tvRole;
    private TextView tvId;
    private TextView tvEmail;
    private TextView tvDetailFullName;
    private TextView tvDetailUserId;
    private TextView tvDetailEmail;
    private TextView tvDetailDept;
    private TextView tvDetailRole;
    private MaterialCardView cardPersonaSwitcher;
    private ChipGroup chipGroupPersona;
    private MaterialSwitch switchNotifications;
    private MaterialSwitch switchOfflineCache;
    private MaterialSwitch switchHighContrast;
    private MaterialButton btnLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        tvName = view.findViewById(R.id.tvProfileName);
        tvRole = view.findViewById(R.id.tvProfileRole);
        tvId = view.findViewById(R.id.tvProfileId);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        tvDetailFullName = view.findViewById(R.id.tvDetailFullName);
        tvDetailUserId = view.findViewById(R.id.tvDetailUserId);
        tvDetailEmail = view.findViewById(R.id.tvDetailEmail);
        tvDetailDept = view.findViewById(R.id.tvDetailDept);
        tvDetailRole = view.findViewById(R.id.tvDetailRole);

        cardPersonaSwitcher = view.findViewById(R.id.cardPersonaSwitcher);
        chipGroupPersona = view.findViewById(R.id.chipGroupPersona);
        switchNotifications = view.findViewById(R.id.switchNotifications);
        switchOfflineCache = view.findViewById(R.id.switchOfflineCache);
        switchHighContrast = view.findViewById(R.id.switchHighContrast);
        btnLogout = view.findViewById(R.id.btnLogout);

        setupUserProfile();
        setupPersonaSwitcher();
        setupSettings();

        // Admin Portal Button with verification
        view.findViewById(R.id.cardProfileAdminPortal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAdminAccess();
            }
        });

        // About ANVAY Card
        view.findViewById(R.id.cardAboutAnvay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutDialog();
            }
        });

        // Logout Button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmation();
            }
        });
    }

    private void setupUserProfile() {
        boolean isGuest = preferenceManager.isGuestMode();
        UserProfile profile = preferenceManager.getUserProfile();

        if (isGuest) {
            tvName.setText("Guest Explorer");
            tvRole.setText("Visitor / Guest • GPP Campus");
            tvId.setText("Session: Guest Mode");
            if (tvEmail != null) tvEmail.setText("guest@gppune.ac.in");
            updateDetailCard("Guest Explorer", "GUEST-SESSION", "guest@gppune.ac.in", "GPP Campus Visitor", "Visitor / Guest");
            btnLogout.setText("EXIT GUEST MODE / LOG IN");
        } else {
            tvName.setText(profile.getName());
            tvRole.setText((profile.getRole() != null ? profile.getRole() : "User") + " • " +
                    (profile.getDepartment() != null ? profile.getDepartment() : "Computer Engineering"));
            tvId.setText("ID: " + profile.getIdNumber());
            if (tvEmail != null) tvEmail.setText(profile.getEmail() != null ? profile.getEmail() : "user@gppune.ac.in");
            updateDetailCard(
                    profile.getName(),
                    profile.getIdNumber(),
                    profile.getEmail() != null ? profile.getEmail() : "user@gppune.ac.in",
                    profile.getDepartment() != null ? profile.getDepartment() : "Computer Engineering",
                    profile.getRole() != null ? profile.getRole() : "Student"
            );
            btnLogout.setText("LOG OUT / SIGN OUT");
        }

        switchNotifications.setChecked(profile.isPushNotifications());
        switchOfflineCache.setChecked(profile.isOfflineCache());
        switchHighContrast.setChecked(profile.isHighContrast());

        // Select correct persona chip
        if ("Faculty".equalsIgnoreCase(profile.getRole())) {
            chipGroupPersona.check(R.id.chipPersonaFaculty);
        } else if ("Staff Member".equalsIgnoreCase(profile.getRole()) || "Staff".equalsIgnoreCase(profile.getRole())) {
            chipGroupPersona.check(R.id.chipPersonaStaff);
        } else if ("Visitor / Guest".equalsIgnoreCase(profile.getRole()) || "Visitor".equalsIgnoreCase(profile.getRole())) {
            chipGroupPersona.check(R.id.chipPersonaVisitor);
        } else {
            chipGroupPersona.check(R.id.chipPersonaStudent);
        }
    }

    private void updateDetailCard(String name, String id, String email, String dept, String role) {
        if (tvDetailFullName != null) tvDetailFullName.setText(name);
        if (tvDetailUserId != null) tvDetailUserId.setText(id);
        if (tvDetailEmail != null) tvDetailEmail.setText(email);
        if (tvDetailDept != null) tvDetailDept.setText(dept);
        if (tvDetailRole != null) tvDetailRole.setText(role);
    }

    private void setupPersonaSwitcher() {
        chipGroupPersona.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                UserProfile profile = preferenceManager.getUserProfile();

                if (id == R.id.chipPersonaFaculty) {
                    profile.setName("Prof. Anand Kulkarni");
                    profile.setRole("Faculty");
                    profile.setIdNumber("GPP/FAC/CO/108");
                    profile.setEmail("anand.kulkarni@gppune.ac.in");
                    profile.setDepartment("Computer Engineering");
                } else if (id == R.id.chipPersonaStaff) {
                    profile.setName("Mr. Ramesh Jagtap");
                    profile.setRole("Staff Member");
                    profile.setIdNumber("GPP/TECH/IT/014");
                    profile.setEmail("ramesh.jagtap@gppune.ac.in");
                    profile.setDepartment("Information Technology");
                } else if (id == R.id.chipPersonaVisitor) {
                    profile.setName("Guest Visitor");
                    profile.setRole("Visitor / Guest");
                    profile.setIdNumber("VISITOR-PASS #402");
                    profile.setEmail("guest@gppune.ac.in");
                    profile.setDepartment("GPP Campus Visitor");
                } else {
                    profile.setName("Aditi Kulkarni");
                    profile.setRole("Student");
                    profile.setIdNumber("GPP/CO/2024/042");
                    profile.setEmail("aditi.kulkarni@gppune.ac.in");
                    profile.setDepartment("Computer Engineering");
                }

                preferenceManager.saveUserProfile(profile);
                preferenceManager.setGuestMode(false);
                tvName.setText(profile.getName());
                tvRole.setText(profile.getRole() + " • " + profile.getDepartment());
                tvId.setText("ID: " + profile.getIdNumber());
                if (tvEmail != null) tvEmail.setText(profile.getEmail());
                updateDetailCard(profile.getName(), profile.getIdNumber(), profile.getEmail(), profile.getDepartment(), profile.getRole());
                btnLogout.setText("LOG OUT / SIGN OUT");
                Toast.makeText(requireContext(), "Switched persona to " + profile.getRole(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSettings() {
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserProfile profile = preferenceManager.getUserProfile();
            profile.setPushNotifications(isChecked);
            preferenceManager.saveUserProfile(profile);
        });

        switchOfflineCache.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserProfile profile = preferenceManager.getUserProfile();
            profile.setOfflineCache(isChecked);
            preferenceManager.saveUserProfile(profile);
        });

        switchHighContrast.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserProfile profile = preferenceManager.getUserProfile();
            profile.setHighContrast(isChecked);
            preferenceManager.saveUserProfile(profile);
            Toast.makeText(requireContext(), "High contrast mode " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });
    }

    private void handleAdminAccess() {
        if (preferenceManager.isGuestMode()) {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Admin Access Restricted")
                    .setMessage("Administrative features are restricted to authorized personnel and are not accessible in Guest Mode.\n\nPlease sign in with an Administrator account.")
                    .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(requireContext(), LoginActivity.class));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }

        UserProfile profile = preferenceManager.getUserProfile();
        if ("Administrator".equalsIgnoreCase(profile.getRole()) || "Admin".equalsIgnoreCase(profile.getRole())) {
            startActivity(new Intent(requireContext(), AdminDashboardActivity.class));
        } else {
            final TextInputEditText input = new TextInputEditText(requireContext());
            input.setHint("Admin Passcode (demo: admin123)");
            input.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

            FrameLayout container = new FrameLayout(requireContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.rightMargin = 50;
            params.topMargin = 20;
            params.bottomMargin = 10;
            input.setLayoutParams(params);
            container.addView(input);

            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Administrator Verification")
                    .setMessage("Enter the admin security passcode to open the Administration Control Panel:")
                    .setView(container)
                    .setPositiveButton("Verify & Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String pass = input.getText() != null ? input.getText().toString().trim() : "";
                            if ("admin123".equals(pass) || "admin".equals(pass)) {
                                startActivity(new Intent(requireContext(), AdminDashboardActivity.class));
                            } else {
                                Toast.makeText(requireContext(), "Incorrect Admin Passcode", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void showLogoutConfirmation() {
        String msg = preferenceManager.isGuestMode() ?
                "Do you want to exit Guest Mode and return to the Login screen?" :
                "Are you sure you want to sign out of ANVAY?";

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sign Out")
                .setMessage(msg)
                .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferenceManager.logout();
                        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAboutDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("ANVAY — Smart Indoor Navigation")
                .setMessage("ANVAY is an intelligent indoor navigation and emergency guidance system created for the Computer & IT Engineering Building of Government Polytechnic Pune.\n\n" +
                        "Current Release: Phase 1.5 (Branding & Authentication)\n" +
                        "Modules Included:\n" +
                        "• Splash Screen & Demo Authentication\n" +
                        "• Guest Mode & Session Persistence\n" +
                        "• Personalized Home Dashboard\n" +
                        "• Building Directory & Classification\n" +
                        "• Multi-criteria Location Search\n" +
                        "• Location Details & Nearby Facilities\n" +
                        "• Priority Emergency Guidance & Protocols\n" +
                        "• Real-time Alerts & Bulletins\n" +
                        "• Protected Admin Management Portal\n\n" +
                        "Upcoming in Phase 2:\n" +
                        "• 2D/3D Multi-floor CAD Maps\n" +
                        "• Turn-by-turn Indoor Pathfinding\n" +
                        "• AR Live Camera Waypoint Guidance\n\n" +
                        "Government Polytechnic Pune © 2026")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupUserProfile();
    }
}
