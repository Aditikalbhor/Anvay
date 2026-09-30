package com.gpp.anvay.ui.timetable;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.gpp.anvay.R;
import com.gpp.anvay.model.TimetableSlot;

import java.util.ArrayList;
import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.SlotViewHolder> {

    public interface OnSlotActionListener {
        void onSlotClick(TimetableSlot slot);
        void onEditSlot(TimetableSlot slot);
        void onDeleteSlot(TimetableSlot slot);
    }

    private final Context context;
    private List<TimetableSlot> slots = new ArrayList<>();
    private boolean canEdit = false;
    private OnSlotActionListener listener;

    public TimetableAdapter(Context context, OnSlotActionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setSlots(List<TimetableSlot> newSlots) {
        this.slots = newSlots != null ? new ArrayList<>(newSlots) : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timetable_slot, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        TimetableSlot slot = slots.get(position);
        holder.bind(slot, canEdit, listener);
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    static class SlotViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardSlot;
        private final TextView tvSlotTime;
        private final TextView tvSessionTypeBadge;
        private final TextView tvSubjectName;
        private final TextView tvSubjectCode;
        private final TextView tvRoomLocation;
        private final TextView tvTargetClassBatch;
        private final TextView tvFacultyName;
        private final TextView tvSlotNotes;
        private final View layoutActionButtons;
        private final MaterialButton btnEditSlot;
        private final MaterialButton btnDeleteSlot;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSlot = itemView.findViewById(R.id.cardTimetableSlot);
            tvSlotTime = itemView.findViewById(R.id.tvSlotTime);
            tvSessionTypeBadge = itemView.findViewById(R.id.tvSessionTypeBadge);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvSubjectCode = itemView.findViewById(R.id.tvSubjectCode);
            tvRoomLocation = itemView.findViewById(R.id.tvRoomLocation);
            tvTargetClassBatch = itemView.findViewById(R.id.tvTargetClassBatch);
            tvFacultyName = itemView.findViewById(R.id.tvFacultyName);
            tvSlotNotes = itemView.findViewById(R.id.tvSlotNotes);
            layoutActionButtons = itemView.findViewById(R.id.layoutActionButtons);
            btnEditSlot = itemView.findViewById(R.id.btnEditSlot);
            btnDeleteSlot = itemView.findViewById(R.id.btnDeleteSlot);
        }

        public void bind(TimetableSlot slot, boolean canEdit, OnSlotActionListener listener) {
            tvSlotTime.setText(slot.getTimeRange());
            tvSubjectName.setText(slot.getSubjectName());
            tvSubjectCode.setText(slot.getSubjectCode());

            String roomDesc = slot.getRoomCode();
            if (slot.getRoomName() != null && !slot.getRoomName().isEmpty()) {
                roomDesc += " (" + slot.getRoomName() + ")";
            }
            tvRoomLocation.setText(roomDesc);

            String classBatch = slot.getTargetClass();
            if (slot.getBatch() != null && !slot.getBatch().isEmpty()) {
                classBatch += " • " + slot.getBatch();
            }
            tvTargetClassBatch.setText(classBatch);

            tvFacultyName.setText(slot.getFacultyName());

            // Session badge style
            String type = slot.getSessionType() != null ? slot.getSessionType() : "Lecture";
            tvSessionTypeBadge.setText(type);

            if (type.contains("Lab") || type.contains("Practical")) {
                tvSessionTypeBadge.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(), R.color.secondary_container));
                tvSessionTypeBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary_dark));
            } else if (type.contains("Tutorial")) {
                tvSessionTypeBadge.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(), R.color.warning_container));
                tvSessionTypeBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.warning_text));
            } else if (type.contains("Seminar")) {
                tvSessionTypeBadge.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(), R.color.success_container));
                tvSessionTypeBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success_text));
            } else {
                tvSessionTypeBadge.setBackgroundTintList(ContextCompat.getColorStateList(itemView.getContext(), R.color.primary_container));
                tvSessionTypeBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primary));
            }

            // Notes
            if (slot.getNotes() != null && !slot.getNotes().trim().isEmpty()) {
                tvSlotNotes.setVisibility(View.VISIBLE);
                tvSlotNotes.setText("Note: " + slot.getNotes());
            } else {
                tvSlotNotes.setVisibility(View.GONE);
            }

            // Role based actions visibility
            if (canEdit) {
                layoutActionButtons.setVisibility(View.VISIBLE);
            } else {
                layoutActionButtons.setVisibility(View.GONE);
            }

            cardSlot.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSlotClick(slot);
                }
            });

            btnEditSlot.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditSlot(slot);
                }
            });

            btnDeleteSlot.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteSlot(slot);
                }
            });
        }
    }
}
