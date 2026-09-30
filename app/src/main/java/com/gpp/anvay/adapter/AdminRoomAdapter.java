package com.gpp.anvay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.gpp.anvay.R;
import com.gpp.anvay.model.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.AdminRoomViewHolder> {

    public interface OnRoomActionListener {
        void onEditRoom(LocationItem location);
        void onDeleteRoom(LocationItem location);
    }

    private final Context context;
    private List<LocationItem> roomList;
    private final OnRoomActionListener listener;

    public AdminRoomAdapter(Context context, List<LocationItem> roomList, OnRoomActionListener listener) {
        this.context = context;
        this.roomList = roomList != null ? roomList : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<LocationItem> newList) {
        this.roomList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_room, parent, false);
        return new AdminRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomViewHolder holder, int position) {
        LocationItem item = roomList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class AdminRoomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAdminRoomCode;
        private final TextView tvAdminRoomCategory;
        private final TextView tvAdminRoomFloor;
        private final TextView tvAdminRoomName;
        private final TextView tvAdminRoomDept;
        private final MaterialButton btnAdminEdit;
        private final MaterialButton btnAdminDelete;

        public AdminRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdminRoomCode = itemView.findViewById(R.id.tvAdminRoomCode);
            tvAdminRoomCategory = itemView.findViewById(R.id.tvAdminRoomCategory);
            tvAdminRoomFloor = itemView.findViewById(R.id.tvAdminRoomFloor);
            tvAdminRoomName = itemView.findViewById(R.id.tvAdminRoomName);
            tvAdminRoomDept = itemView.findViewById(R.id.tvAdminRoomDept);
            btnAdminEdit = itemView.findViewById(R.id.btnAdminEdit);
            btnAdminDelete = itemView.findViewById(R.id.btnAdminDelete);
        }

        public void bind(final LocationItem item, final OnRoomActionListener listener) {
            tvAdminRoomCode.setText(item.getRoomNumber());
            tvAdminRoomCategory.setText(item.getCategory());
            tvAdminRoomFloor.setText(item.getFloor());
            tvAdminRoomName.setText(item.getName());

            String sub = item.getDepartment() + (item.getInCharge() != null ? " • " + item.getInCharge() : "");
            tvAdminRoomDept.setText(sub);

            btnAdminEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEditRoom(item);
                    }
                }
            });

            btnAdminDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDeleteRoom(item);
                    }
                }
            });
        }
    }
}
