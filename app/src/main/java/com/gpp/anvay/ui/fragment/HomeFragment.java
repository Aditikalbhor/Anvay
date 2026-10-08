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

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.gpp.anvay.R;
import com.gpp.anvay.data.AlertRepository;
import com.gpp.anvay.data.PreferenceManager;
import com.gpp.anvay.model.AlertItem;
import com.gpp.anvay.model.UserProfile;
import com.gpp.anvay.ui.EmergencyActivity;
import com.gpp.anvay.ui.LoginActivity;
import com.gpp.anvay.ui.MainActivity;
import com.gpp.anvay.ui.admin.AdminDashboardActivity;
import com.gpp.anvay.ui.dialog.FeaturePlaceholderDialog;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private PreferenceManager preferenceManager;
    private TextView tvHomeGreeting;
    private TextView tvHomeSubtitle;
    private TextView tvHomeUserRoleBadge;
    private MaterialCardView cardGuestBanner;
    private ChipGroup chipGroupRecentSearches;
    private FrameLayout containerHomeAlertPreview;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        tvHomeGreeting = view.findViewById(R.id.tvHomeGreeting);
        tvHomeSubtitle = view.findViewById(R.id.tvHomeSubtitle);
        tvHomeUserRoleBadge = view.findViewById(R.id.tvHomeUserRoleBadge);
        cardGuestBanner = view.findViewById(R.id.cardGuestBanner);
        chipGroupRecentSearches = view.findViewById(R.id.chipGroupRecentSearches);
        containerHomeAlertPreview = view.findViewById(R.id.containerHomeAlertPreview);

        updatePersonalizedHeader();

        // Top Admin Button with role protection
        view.findViewById(R.id.btnHomeAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAdminAccess();
            }
        });

        // Guest Login Prompt on banner
        view.findViewById(R.id.tvGuestLoginPrompt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(requireContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // Emergency Mode Card
        MaterialCardView cardEmergency = view.findViewById(R.id.cardEmergencyMode);
        cardEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), EmergencyActivity.class));
            }
        });

        // 1. Navigation Button
        MaterialCardView cardIndoorNav = view.findViewById(R.id.cardIndoorNav);
        cardIndoorNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), com.gpp.anvay.ui.NavigationActivity.class));
            }
        });

        // 2. Building Directory Button
        MaterialCardView cardDirectory = view.findViewById(R.id.cardBuildingDirectory);
        cardDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToTab(R.id.nav_directory, null);
                }
            }
        });

        // 3. Search Locations Button
        MaterialCardView cardSearch = view.findViewById(R.id.cardSearchLocations);
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToTab(R.id.nav_search, null);
                }
            }
        });

        // 4. Class Timetable & Schedules Button
        MaterialCardView cardTimetable = view.findViewById(R.id.cardHomeTimetable);
        if (cardTimetable != null) {
            cardTimetable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), com.gpp.anvay.ui.TimetableActivity.class));
                }
            });
        }

        // Category Quick Links
        setupCategoryChips(view);

        // Recent Searches
        TextView tvClearRecent = view.findViewById(R.id.tvClearRecentSearches);
        tvClearRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.clearRecentSearches();
                loadRecentSearches();
            }
        });
        loadRecentSearches();

        // Alert Preview
        loadAlertPreview();

        TextView tvViewAllAlerts = view.findViewById(R.id.tvHomeViewAllAlerts);
        tvViewAllAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToTab(R.id.nav_alerts, null);
                }
            }
        });
    }

    private void updatePersonalizedHeader() {
        if (preferenceManager == null) return;

        boolean isGuest = preferenceManager.isGuestMode();
        UserProfile profile = preferenceManager.getUserProfile();

        if (isGuest) {
            tvHomeGreeting.setText("Welcome to ANVAY 👋");
            tvHomeSubtitle.setText("Guest User • Government Polytechnic Pune");
            tvHomeUserRoleBadge.setText("Guest");
            cardGuestBanner.setVisibility(View.VISIBLE);
        } else {
            String timeGreeting = getTimeBasedGreeting();
            String displayName = profile.getName();
            if (displayName != null && displayName.contains(" ")) {
                displayName = displayName.split(" ")[0]; // First name
            }

            tvHomeGreeting.setText(timeGreeting + ", " + (displayName != null ? displayName : "User") + " 👋");
            tvHomeSubtitle.setText((profile.getDepartment() != null ? profile.getDepartment() : "Comp & IT") + " • Government Polytechnic Pune");
            tvHomeUserRoleBadge.setText(profile.getRole() != null ? profile.getRole() : "Student");
            cardGuestBanner.setVisibility(View.GONE);
        }
    }

    private String getTimeBasedGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else if (hour >= 17 && hour < 22) {
            return "Good Evening";
        } else {
            return "Welcome";
        }
    }

    private void handleAdminAccess() {
        if (preferenceManager.isGuestMode()) {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Admin Access Restricted")
                    .setMessage("Administrative features are not accessible in Guest Mode.\n\nPlease log in with your campus Administrator credentials to access the Admin Portal.")
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
            // Prompt admin password verification
            View promptView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_alert, null);
            // Re-use clean text input dialog or simple prompt
            final TextInputEditText input = new TextInputEditText(requireContext());
            input.setHint("Admin Password (demo: admin123)");
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
                    .setTitle("Administrator Authentication")
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

    private void setupCategoryChips(View root) {
        View.OnClickListener catListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getActivity() instanceof MainActivity)) return;
                String category = "All";
                int id = v.getId();
                if (id == R.id.chipHomeLabs) category = "Laboratory";
                else if (id == R.id.chipHomeClassrooms) category = "Classrooms";
                else if (id == R.id.chipHomeStaff) category = "Staff Rooms";
                else if (id == R.id.chipHomeHOD) category = "HOD & Offices";
                else if (id == R.id.chipHomeWashrooms) category = "Washrooms";
                else if (id == R.id.chipHomeServer) category = "Server Rooms";
                else if (id == R.id.chipHomeFacilities) category = "Facilities";

                Bundle args = new Bundle();
                args.putString("selected_category", category);
                ((MainActivity) getActivity()).navigateToTab(R.id.nav_directory, args);
            }
        };

        root.findViewById(R.id.chipHomeLabs).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeClassrooms).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeStaff).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeHOD).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeWashrooms).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeServer).setOnClickListener(catListener);
        root.findViewById(R.id.chipHomeFacilities).setOnClickListener(catListener);
    }

    private void loadRecentSearches() {
        if (chipGroupRecentSearches == null) return;
        chipGroupRecentSearches.removeAllViews();
        List<String> recentList = preferenceManager.getRecentSearches();

        for (final String query : recentList) {
            Chip chip = new Chip(requireContext());
            chip.setText(query);
            chip.setCheckable(false);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof MainActivity) {
                        Bundle args = new Bundle();
                        args.putString("search_query", query);
                        ((MainActivity) getActivity()).navigateToTab(R.id.nav_search, args);
                    }
                }
            });
            chipGroupRecentSearches.addView(chip);
        }
    }

    private void loadAlertPreview() {
        if (containerHomeAlertPreview == null) return;
        containerHomeAlertPreview.removeAllViews();

        List<AlertItem> activeAlerts = AlertRepository.getInstance().getActiveAlerts();
        if (!activeAlerts.isEmpty()) {
            AlertItem topAlert = activeAlerts.get(0);
            View alertView = LayoutInflater.from(requireContext()).inflate(R.layout.item_alert_card, containerHomeAlertPreview, false);

            TextView tvPriority = alertView.findViewById(R.id.tvAlertPriority);
            TextView tvCategory = alertView.findViewById(R.id.tvAlertCategory);
            TextView tvTime = alertView.findViewById(R.id.tvAlertTimestamp);
            TextView tvTitle = alertView.findViewById(R.id.tvAlertTitle);
            TextView tvMessage = alertView.findViewById(R.id.tvAlertMessage);
            TextView tvLocation = alertView.findViewById(R.id.tvLocationAffected);

            tvPriority.setText(topAlert.getPriority());
            tvCategory.setText(topAlert.getCategory());
            tvTime.setText(topAlert.getTimestamp());
            tvTitle.setText(topAlert.getTitle());
            tvMessage.setText(topAlert.getMessage());
            if (topAlert.getLocationAffected() != null) {
                tvLocation.setText("Affected: " + topAlert.getLocationAffected());
            }

            alertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToTab(R.id.nav_alerts, null);
                    }
                }
            });

            containerHomeAlertPreview.addView(alertView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePersonalizedHeader();
        loadRecentSearches();
        loadAlertPreview();
    }
}
