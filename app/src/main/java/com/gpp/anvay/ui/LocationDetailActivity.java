package com.gpp.anvay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.gpp.anvay.R;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.ui.dialog.FeaturePlaceholderDialog;

public class LocationDetailActivity extends AppCompatActivity {

    private LocationItem location;
    private ImageView btnBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        location = (LocationItem) getIntent().getSerializableExtra("location_item");
        if (location == null) {
            String locationId = getIntent().getStringExtra("location_id");
            if (locationId != null) {
                location = LocationRepository.getInstance().getLocationById(locationId);
            }
        }

        if (location == null) {
            Toast.makeText(this, "Location details not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        bindData();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarDetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView btnShare = findViewById(R.id.btnDetailShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLocation();
            }
        });

        btnBookmark = findViewById(R.id.btnDetailBookmark);
        updateBookmarkIcon();
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationRepository.getInstance().toggleBookmark(location.getId());
                location.setBookmarked(!location.isBookmarked());
                updateBookmarkIcon();
                Toast.makeText(LocationDetailActivity.this,
                        location.isBookmarked() ? "Location bookmarked" : "Bookmark removed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBookmarkIcon() {
        if (location.isBookmarked()) {
            btnBookmark.setColorFilter(ContextCompat.getColor(this, R.color.warning_amber));
        } else {
            btnBookmark.setColorFilter(ContextCompat.getColor(this, R.color.text_on_dark));
        }
    }

    private void bindData() {
        TextView tvRoomCode = findViewById(R.id.tvDetailRoomCode);
        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvFloor = findViewById(R.id.tvDetailFloor);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvDept = findViewById(R.id.tvDetailDepartment);
        TextView tvInCharge = findViewById(R.id.tvDetailInCharge);
        TextView tvHours = findViewById(R.id.tvDetailHours);
        TextView tvDesc = findViewById(R.id.tvDetailDescription);
        TextView tvNearestExit = findViewById(R.id.tvDetailNearestExit);
        ChipGroup chipGroupFacilities = findViewById(R.id.chipGroupDetailFacilities);
        LinearLayout layoutNearbyContainer = findViewById(R.id.layoutDetailNearbyContainer);
        MaterialButton btnNavigate = findViewById(R.id.btnDetailNavigate);

        tvRoomCode.setText(location.getRoomNumber());
        tvCategory.setText(location.getCategory());
        tvFloor.setText(location.getFloor());
        tvName.setText(location.getName());

        String bName = location.getBuildingName() != null && !location.getBuildingName().isEmpty() ?
                location.getBuildingName() : "Computer/IT Building";

        String deptStr = bName + " • " + (location.getDepartment() != null ? location.getDepartment() : "Department") +
                (location.getWing() != null ? " • " + location.getWing() : "");
        tvDept.setText(deptStr);

        tvInCharge.setText(location.getInCharge() != null && !location.getInCharge().isEmpty() ? location.getInCharge() : "General Department Staff");
        tvHours.setText(location.getOperatingHours() != null && !location.getOperatingHours().isEmpty() ? location.getOperatingHours() : "08:30 AM - 05:30 PM");
        tvDesc.setText(location.getDescription() != null ? location.getDescription() : "No detailed description available.");
        tvNearestExit.setText(location.getNearestExit() != null ? location.getNearestExit() : "Follow building staircases (S1, S2, S3) down to Ground Floor.");

        // Facilities Chips
        chipGroupFacilities.removeAllViews();
        if (location.getFacilities() != null && !location.getFacilities().isEmpty()) {
            for (String fac : location.getFacilities()) {
                Chip chip = new Chip(this);
                chip.setText(fac);
                chip.setCheckable(false);
                chipGroupFacilities.addView(chip);
            }
        } else {
            Chip chip = new Chip(this);
            chip.setText("Standard Campus Utilities");
            chip.setCheckable(false);
            chipGroupFacilities.addView(chip);
        }

        // Nearby Landmarks
        layoutNearbyContainer.removeAllViews();
        if (location.getNearbyLandmarks() != null && !location.getNearbyLandmarks().isEmpty()) {
            for (String landmark : location.getNearbyLandmarks()) {
                TextView tvItem = new TextView(this);
                tvItem.setText("• " + landmark);
                tvItem.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
                tvItem.setTextSize(13);
                tvItem.setPadding(0, 4, 0, 4);
                layoutNearbyContainer.addView(tvItem);
            }
        } else {
            TextView tvItem = new TextView(this);
            tvItem.setText("• Central Corridor Access");
            tvItem.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tvItem.setTextSize(13);
            layoutNearbyContainer.addView(tvItem);
        }

        // Timetable click
        View cardTimetable = findViewById(R.id.cardDetailTimetable);
        if (cardTimetable != null) {
            cardTimetable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LocationDetailActivity.this, TimetableActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Navigate button click
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navIntent = new Intent(LocationDetailActivity.this, NavigationActivity.class);
                navIntent.putExtra("destination_id", location.getId());
                navIntent.putExtra("location_item", location);
                startActivity(navIntent);
            }
        });
    }

    private void shareLocation() {
        String bName = location.getBuildingName() != null && !location.getBuildingName().isEmpty() ?
                location.getBuildingName() : "Computer/IT Building";

        String shareBody = "Location at GPP " + bName + ":\n" +
                "• Room: " + location.getRoomNumber() + " (" + location.getName() + ")\n" +
                "• Building: " + bName + "\n" +
                "• Floor: " + location.getFloor() + " (" + location.getDepartment() + ")\n" +
                "• In-Charge: " + location.getInCharge() + "\n" +
                "• Nearest Exit: " + location.getNearestExit() + "\n\n" +
                "Shared via ANVAY Indoor Navigation System";

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Location Info"));
    }
}
