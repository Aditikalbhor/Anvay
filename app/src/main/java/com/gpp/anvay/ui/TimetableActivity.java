package com.gpp.anvay.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpp.anvay.R;
import com.gpp.anvay.data.PreferenceManager;
import com.gpp.anvay.data.TimetableRepository;
import com.gpp.anvay.model.TimetableSlot;
import com.gpp.anvay.model.UserProfile;
import com.gpp.anvay.ui.timetable.TimetableAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimetableActivity extends AppCompatActivity implements TimetableAdapter.OnSlotActionListener {

    private TimetableRepository repository;
    private PreferenceManager preferenceManager;
    private TimetableAdapter adapter;

    private TextView tvRolePermissionBadge;
    private ChipGroup chipGroupDays;
    private ChipGroup chipGroupClassFilter;
    private TextInputEditText etSearchSlot;
    private RecyclerView rvTimetableSlots;
    private View layoutEmptyState;
    private ExtendedFloatingActionButton fabAddSlot;

    private String selectedDay = "Monday";
    private String selectedClass = "All Classes";
    private String searchQuery = "";
    private boolean canEdit = false;

    private static final String[] DAYS = new String[]{
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    private static final String[] CLASSES = new String[]{
            "TY Computer (CO-5I)",
            "SY Computer (CO-3I)",
            "FY Computer (CO-1I)",
            "TY IT (IF-5I)",
            "SY IT (IF-3I)",
            "FY IT (IF-1I)"
    };

    private static final String[] ROOMS = new String[]{
            "C-101 (Classroom C-101)",
            "C-102 (Classroom C-102)",
            "C-201 (Classroom C-201)",
            "C-202 (Classroom C-202)",
            "G-01 (Computer Center Lab 1)",
            "G-02 (Programming Lab)",
            "1-01 (Advanced Computing Lab)",
            "1-02 (IoT & Embedded Lab)",
            "2-01 (Software Engg Lab)",
            "2-02 (Hardware & Network Lab)",
            "2-03 (Project & Research Lab)",
            "2-05 (Seminar Hall)",
            "IT-204 (Classroom IT-204)"
    };

    private static final String[] SESSION_TYPES = new String[]{
            "Lecture", "Practical / Lab", "Tutorial", "Seminar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        repository = TimetableRepository.getInstance(this);
        preferenceManager = new PreferenceManager(this);

        initViews();
        setupRolePermissions();
        setupRecyclerView();
        setupDaySelection();
        setupClassFilters();
        setupSearch();
        loadTimetable();
    }

    private void initViews() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        tvRolePermissionBadge = findViewById(R.id.tvRolePermissionBadge);
        chipGroupDays = findViewById(R.id.chipGroupDays);
        chipGroupClassFilter = findViewById(R.id.chipGroupClassFilter);
        etSearchSlot = findViewById(R.id.etSearchSlot);
        rvTimetableSlots = findViewById(R.id.rvTimetableSlots);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        fabAddSlot = findViewById(R.id.fabAddSlot);

        fabAddSlot.setOnClickListener(v -> {
            if (canEdit) {
                showAddEditSlotDialog(null);
            }
        });
    }

    private void setupRolePermissions() {
        boolean isGuest = preferenceManager.isGuestMode();
        UserProfile userProfile = preferenceManager.getUserProfile();
        String role = isGuest ? "Guest" : (userProfile.getRole() != null ? userProfile.getRole() : "Student");

        if ("Administrator".equalsIgnoreCase(role) || "Admin".equalsIgnoreCase(role)) {
            canEdit = true;
            tvRolePermissionBadge.setText("🛡️ Admin • Management Mode");
            tvRolePermissionBadge.setBackgroundTintList(getColorStateList(R.color.primary_container));
            tvRolePermissionBadge.setTextColor(getColor(R.color.primary));
            fabAddSlot.setVisibility(View.VISIBLE);
        } else if ("Faculty".equalsIgnoreCase(role)) {
            canEdit = true;
            tvRolePermissionBadge.setText("👨‍🏫 Faculty • Add/Edit Enabled");
            tvRolePermissionBadge.setBackgroundTintList(getColorStateList(R.color.secondary_container));
            tvRolePermissionBadge.setTextColor(getColor(R.color.secondary_dark));
            fabAddSlot.setVisibility(View.VISIBLE);
        } else if (isGuest || "Visitor / Guest".equalsIgnoreCase(role) || "Visitor".equalsIgnoreCase(role)) {
            canEdit = false;
            tvRolePermissionBadge.setText("🧭 Guest • View Only");
            tvRolePermissionBadge.setBackgroundTintList(getColorStateList(R.color.surface_variant));
            tvRolePermissionBadge.setTextColor(getColor(R.color.text_secondary));
            fabAddSlot.setVisibility(View.GONE);
        } else {
            // Student (Default)
            canEdit = false;
            tvRolePermissionBadge.setText("👤 Student • View Only");
            tvRolePermissionBadge.setBackgroundTintList(getColorStateList(R.color.primary_container));
            tvRolePermissionBadge.setTextColor(getColor(R.color.primary));
            fabAddSlot.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        adapter = new TimetableAdapter(this, this);
        adapter.setCanEdit(canEdit);
        rvTimetableSlots.setLayoutManager(new LinearLayoutManager(this));
        rvTimetableSlots.setAdapter(adapter);
    }

    private void setupDaySelection() {
        // Automatically default to current day
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.TUESDAY:
                selectedDay = "Tuesday";
                chipGroupDays.check(R.id.chipDayTue);
                break;
            case Calendar.WEDNESDAY:
                selectedDay = "Wednesday";
                chipGroupDays.check(R.id.chipDayWed);
                break;
            case Calendar.THURSDAY:
                selectedDay = "Thursday";
                chipGroupDays.check(R.id.chipDayThu);
                break;
            case Calendar.FRIDAY:
                selectedDay = "Friday";
                chipGroupDays.check(R.id.chipDayFri);
                break;
            case Calendar.SATURDAY:
                selectedDay = "Saturday";
                chipGroupDays.check(R.id.chipDaySat);
                break;
            case Calendar.SUNDAY:
            case Calendar.MONDAY:
            default:
                selectedDay = "Monday";
                chipGroupDays.check(R.id.chipDayMon);
                break;
        }

        chipGroupDays.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            if (id == R.id.chipDayMon) selectedDay = "Monday";
            else if (id == R.id.chipDayTue) selectedDay = "Tuesday";
            else if (id == R.id.chipDayWed) selectedDay = "Wednesday";
            else if (id == R.id.chipDayThu) selectedDay = "Thursday";
            else if (id == R.id.chipDayFri) selectedDay = "Friday";
            else if (id == R.id.chipDaySat) selectedDay = "Saturday";

            loadTimetable();
        });
    }

    private void setupClassFilters() {
        chipGroupClassFilter.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                selectedClass = "All Classes";
            } else {
                int id = checkedIds.get(0);
                if (id == R.id.chipClassTYComp) selectedClass = "TY Computer (CO-5I)";
                else if (id == R.id.chipClassSYComp) selectedClass = "SY Computer (CO-3I)";
                else if (id == R.id.chipClassTYIT) selectedClass = "TY IT (IF-5I)";
                else if (id == R.id.chipClassSYIT) selectedClass = "SY IT (IF-3I)";
                else selectedClass = "All Classes";
            }
            loadTimetable();
        });
    }

    private void setupSearch() {
        etSearchSlot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                searchQuery = s != null ? s.toString().trim() : "";
                loadTimetable();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadTimetable() {
        List<TimetableSlot> list = repository.getSlotsFiltered(selectedDay, selectedClass, null, searchQuery);
        adapter.setSlots(list);
        adapter.setCanEdit(canEdit);

        if (list.isEmpty()) {
            rvTimetableSlots.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvTimetableSlots.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSlotClick(TimetableSlot slot) {
        if (canEdit) {
            // Give Faculty/Admin option to either edit or view
            showAddEditSlotDialog(slot);
        } else {
            // Student & Guest: Read-only details
            showSlotDetailsDialog(slot);
        }
    }

    @Override
    public void onEditSlot(TimetableSlot slot) {
        if (canEdit) {
            showAddEditSlotDialog(slot);
        }
    }

    @Override
    public void onDeleteSlot(TimetableSlot slot) {
        if (!canEdit) return;

        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Schedule Slot")
                .setMessage("Are you sure you want to remove " + slot.getSubjectName() + " (" + slot.getTimeRange() + ") from the timetable?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    repository.deleteSlot(slot.getId());
                    Toast.makeText(TimetableActivity.this, "Slot deleted successfully", Toast.LENGTH_SHORT).show();
                    loadTimetable();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showSlotDetailsDialog(TimetableSlot slot) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_timetable_details, null);

        TextView tvDetailSubject = dialogView.findViewById(R.id.tvDetailSubject);
        TextView tvDetailSubjectCode = dialogView.findViewById(R.id.tvDetailSubjectCode);
        TextView tvDetailSessionBadge = dialogView.findViewById(R.id.tvDetailSessionBadge);
        TextView tvDetailScheduleTime = dialogView.findViewById(R.id.tvDetailScheduleTime);
        TextView tvDetailRoom = dialogView.findViewById(R.id.tvDetailRoom);
        TextView tvDetailClass = dialogView.findViewById(R.id.tvDetailClass);
        TextView tvDetailTeacher = dialogView.findViewById(R.id.tvDetailTeacher);
        TextView tvDetailNotes = dialogView.findViewById(R.id.tvDetailNotes);

        tvDetailSubject.setText(slot.getSubjectName());
        tvDetailSubjectCode.setText("Course Code: " + slot.getSubjectCode() + " • " + slot.getDepartment());
        tvDetailSessionBadge.setText(slot.getSessionType());
        tvDetailScheduleTime.setText(slot.getDayOfWeek() + " • " + slot.getTimeRange());

        String roomFull = slot.getRoomCode();
        if (slot.getRoomName() != null && !slot.getRoomName().isEmpty()) {
            roomFull += " (" + slot.getRoomName() + ")";
        }
        tvDetailRoom.setText(roomFull);

        String classBatch = slot.getTargetClass();
        if (slot.getBatch() != null && !slot.getBatch().isEmpty()) {
            classBatch += " • " + slot.getBatch();
        }
        tvDetailClass.setText(classBatch);

        tvDetailTeacher.setText(slot.getFacultyName());

        if (slot.getNotes() != null && !slot.getNotes().trim().isEmpty()) {
            tvDetailNotes.setVisibility(View.VISIBLE);
            tvDetailNotes.setText("Session Note: " + slot.getNotes());
        } else {
            tvDetailNotes.setVisibility(View.GONE);
        }

        androidx.appcompat.app.AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btnDetailClose).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showAddEditSlotDialog(TimetableSlot slotToEdit) {
        boolean isEdit = (slotToEdit != null);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_timetable, null);

        TextView tvTitle = dialogView.findViewById(R.id.tvDialogTitle);
        tvTitle.setText(isEdit ? "Edit Schedule Slot" : "Add Schedule Slot");

        AutoCompleteTextView actvDay = dialogView.findViewById(R.id.actvDialogDay);
        TextInputEditText etStartTime = dialogView.findViewById(R.id.etDialogStartTime);
        TextInputEditText etEndTime = dialogView.findViewById(R.id.etDialogEndTime);
        TextInputEditText etSubjectName = dialogView.findViewById(R.id.etDialogSubjectName);
        TextInputEditText etSubjectCode = dialogView.findViewById(R.id.etDialogSubjectCode);
        AutoCompleteTextView actvSessionType = dialogView.findViewById(R.id.actvDialogSessionType);
        AutoCompleteTextView actvRoom = dialogView.findViewById(R.id.actvDialogRoom);
        AutoCompleteTextView actvClass = dialogView.findViewById(R.id.actvDialogTargetClass);
        TextInputEditText etBatch = dialogView.findViewById(R.id.etDialogBatch);
        TextInputEditText etFaculty = dialogView.findViewById(R.id.etDialogFaculty);
        TextInputEditText etNotes = dialogView.findViewById(R.id.etDialogNotes);

        // Populate dropdown adapters
        actvDay.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, DAYS));
        actvSessionType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, SESSION_TYPES));
        actvRoom.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ROOMS));
        actvClass.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CLASSES));

        // Default or existing values
        if (isEdit) {
            actvDay.setText(slotToEdit.getDayOfWeek(), false);
            etStartTime.setText(slotToEdit.getStartTime());
            etEndTime.setText(slotToEdit.getEndTime());
            etSubjectName.setText(slotToEdit.getSubjectName());
            etSubjectCode.setText(slotToEdit.getSubjectCode());
            actvSessionType.setText(slotToEdit.getSessionType(), false);
            actvRoom.setText(slotToEdit.getRoomCode() + (slotToEdit.getRoomName() != null ? " (" + slotToEdit.getRoomName() + ")" : ""), false);
            actvClass.setText(slotToEdit.getTargetClass(), false);
            etBatch.setText(slotToEdit.getBatch());
            etFaculty.setText(slotToEdit.getFacultyName());
            etNotes.setText(slotToEdit.getNotes());
        } else {
            actvDay.setText(selectedDay, false);
            UserProfile profile = preferenceManager.getUserProfile();
            if ("Faculty".equalsIgnoreCase(profile.getRole())) {
                etFaculty.setText(profile.getName());
            }
        }

        androidx.appcompat.app.AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btnDialogCancel).setOnClickListener(v -> dialog.dismiss());

        dialogView.findViewById(R.id.btnDialogSave).setOnClickListener(v -> {
            String day = actvDay.getText().toString().trim();
            String start = etStartTime.getText() != null ? etStartTime.getText().toString().trim() : "";
            String end = etEndTime.getText() != null ? etEndTime.getText().toString().trim() : "";
            String subject = etSubjectName.getText() != null ? etSubjectName.getText().toString().trim() : "";
            String code = etSubjectCode.getText() != null ? etSubjectCode.getText().toString().trim() : "";
            String sessionType = actvSessionType.getText().toString().trim();
            String roomSelection = actvRoom.getText().toString().trim();
            String targetClass = actvClass.getText().toString().trim();
            String batch = etBatch.getText() != null ? etBatch.getText().toString().trim() : "All";
            String faculty = etFaculty.getText() != null ? etFaculty.getText().toString().trim() : "";
            String notes = etNotes.getText() != null ? etNotes.getText().toString().trim() : "";

            if (subject.isEmpty()) {
                etSubjectName.setError("Subject name cannot be empty");
                return;
            }
            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(TimetableActivity.this, "Please specify start and end time", Toast.LENGTH_SHORT).show();
                return;
            }

            // Extract room code and room name from dropdown text
            String roomCode = roomSelection;
            String roomName = "";
            if (roomSelection.contains("(")) {
                roomCode = roomSelection.split("\\(")[0].trim();
                roomName = roomSelection.substring(roomSelection.indexOf("(") + 1, roomSelection.indexOf(")")).trim();
            }

            String department = targetClass.contains("IT") ? "Information Technology" : "Computer Engineering";

            if (isEdit) {
                slotToEdit.setDayOfWeek(day);
                slotToEdit.setStartTime(start);
                slotToEdit.setEndTime(end);
                slotToEdit.setSubjectName(subject);
                slotToEdit.setSubjectCode(code);
                slotToEdit.setSessionType(sessionType);
                slotToEdit.setRoomCode(roomCode);
                slotToEdit.setRoomName(roomName);
                slotToEdit.setTargetClass(targetClass);
                slotToEdit.setBatch(batch);
                slotToEdit.setFacultyName(faculty);
                slotToEdit.setDepartment(department);
                slotToEdit.setNotes(notes);

                repository.updateSlot(slotToEdit);
                Toast.makeText(TimetableActivity.this, "Schedule slot updated", Toast.LENGTH_SHORT).show();
            } else {
                TimetableSlot newSlot = new TimetableSlot(
                        "slot_" + System.currentTimeMillis(),
                        day,
                        start,
                        end,
                        subject,
                        code,
                        roomCode,
                        roomName,
                        department,
                        targetClass,
                        batch,
                        faculty,
                        sessionType,
                        notes
                );
                repository.addSlot(newSlot);
                Toast.makeText(TimetableActivity.this, "Schedule slot created", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
            loadTimetable();
        });

        dialog.show();
    }
}
