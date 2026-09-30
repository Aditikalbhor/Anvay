package com.gpp.anvay.ui.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.AdminRoomAdapter;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ManageRoomsActivity extends AppCompatActivity implements AdminRoomAdapter.OnRoomActionListener {

    private RecyclerView rvRooms;
    private AdminRoomAdapter adapter;
    private EditText etSearch;

    private final String[] categories = {"Classrooms", "Laboratory", "Staff Rooms", "HOD & Offices", "Washrooms", "Server Rooms", "Facilities"};
    private final String[] floors = {"Ground Floor", "1st Floor", "2nd Floor", "3rd Floor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        MaterialToolbar toolbar = findViewById(R.id.toolbarManageRooms);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvRooms = findViewById(R.id.rvAdminRooms);
        etSearch = findViewById(R.id.etAdminSearchRooms);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAddRoom);

        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminRoomAdapter(this, LocationRepository.getInstance().getAllLocations(), this);
        rvRooms.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRooms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOrEditRoomDialog(null);
            }
        });
    }

    private void filterRooms(String query) {
        List<LocationItem> filtered = LocationRepository.getInstance().searchLocations(query, "All", "All Floors");
        adapter.updateList(filtered);
    }

    private void showAddOrEditRoomDialog(final LocationItem existingItem) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_room, null);

        TextView tvTitle = dialogView.findViewById(R.id.tvDialogRoomTitle);
        TextInputEditText etRoomNo = dialogView.findViewById(R.id.etRoomNumber);
        TextInputEditText etName = dialogView.findViewById(R.id.etRoomName);
        Spinner spCategory = dialogView.findViewById(R.id.spinnerCategory);
        Spinner spFloor = dialogView.findViewById(R.id.spinnerFloor);
        TextInputEditText etDept = dialogView.findViewById(R.id.etDepartment);
        TextInputEditText etInCharge = dialogView.findViewById(R.id.etInCharge);
        TextInputEditText etFacilities = dialogView.findViewById(R.id.etFacilities);
        TextInputEditText etDesc = dialogView.findViewById(R.id.etDescription);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(catAdapter);

        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, floors);
        spFloor.setAdapter(floorAdapter);

        if (existingItem != null) {
            tvTitle.setText("Edit Room: " + existingItem.getRoomNumber());
            etRoomNo.setText(existingItem.getRoomNumber());
            etName.setText(existingItem.getName());
            etDept.setText(existingItem.getDepartment());
            etInCharge.setText(existingItem.getInCharge());
            etDesc.setText(existingItem.getDescription());

            if (existingItem.getFacilities() != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < existingItem.getFacilities().size(); i++) {
                    sb.append(existingItem.getFacilities().get(i));
                    if (i < existingItem.getFacilities().size() - 1) sb.append(", ");
                }
                etFacilities.setText(sb.toString());
            }

            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equalsIgnoreCase(existingItem.getCategory())) {
                    spCategory.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < floors.length; i++) {
                if (floors[i].equalsIgnoreCase(existingItem.getFloor())) {
                    spFloor.setSelection(i);
                    break;
                }
            }
        }

        new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomNo = etRoomNo.getText().toString().trim();
                        String name = etName.getText().toString().trim();
                        String dept = etDept.getText().toString().trim();
                        String inCharge = etInCharge.getText().toString().trim();
                        String desc = etDesc.getText().toString().trim();
                        String category = spCategory.getSelectedItem().toString();
                        String floor = spFloor.getSelectedItem().toString();

                        if (roomNo.isEmpty() || name.isEmpty()) {
                            Toast.makeText(ManageRoomsActivity.this, "Room Number and Name are required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<String> facilitiesList = new ArrayList<>();
                        String facRaw = etFacilities.getText().toString().trim();
                        if (!facRaw.isEmpty()) {
                            facilitiesList = Arrays.asList(facRaw.split("\\s*,\\s*"));
                        }

                        if (existingItem != null) {
                            existingItem.setRoomNumber(roomNo);
                            existingItem.setName(name);
                            existingItem.setCategory(category);
                            existingItem.setFloor(floor);
                            existingItem.setDepartment(dept);
                            existingItem.setInCharge(inCharge);
                            existingItem.setDescription(desc);
                            existingItem.setFacilities(facilitiesList);
                            LocationRepository.getInstance().updateLocation(existingItem);
                            Toast.makeText(ManageRoomsActivity.this, "Room updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            LocationItem newItem = new LocationItem(
                                    "loc_" + UUID.randomUUID().toString().substring(0, 8),
                                    roomNo,
                                    name,
                                    category,
                                    dept,
                                    floor,
                                    "Central Wing",
                                    desc,
                                    inCharge,
                                    "08:30 AM - 05:30 PM",
                                    facilitiesList,
                                    Arrays.asList("Central Corridor", "Staircase A"),
                                    "Staircase A -> Ground Exit"
                            );
                            LocationRepository.getInstance().addLocation(newItem);
                            Toast.makeText(ManageRoomsActivity.this, "Room added successfully", Toast.LENGTH_SHORT).show();
                        }

                        filterRooms(etSearch.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onEditRoom(LocationItem location) {
        showAddOrEditRoomDialog(location);
    }

    @Override
    public void onDeleteRoom(final LocationItem location) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Location")
                .setMessage("Are you sure you want to delete " + location.getRoomNumber() + " (" + location.getName() + ")?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocationRepository.getInstance().deleteLocation(location.getId());
                        filterRooms(etSearch.getText().toString());
                        Toast.makeText(ManageRoomsActivity.this, "Location deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
