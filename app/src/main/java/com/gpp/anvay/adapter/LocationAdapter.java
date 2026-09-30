package com.gpp.anvay.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gpp.anvay.R;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.ui.LocationDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    public interface OnLocationClickListener {
        void onLocationClick(LocationItem location);
    }

    private final Context context;
    private List<LocationItem> locationList;
    private final OnLocationClickListener listener;

    public LocationAdapter(Context context, List<LocationItem> locationList, OnLocationClickListener listener) {
        this.context = context;
        this.locationList = locationList != null ? locationList : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<LocationItem> newList) {
        this.locationList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_card, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem item = locationList.get(position);
        holder.bind(item, context, listener);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRoomCode;
        private final TextView tvCategoryBadge;
        private final TextView tvFloorBadge;
        private final TextView tvLocationName;
        private final TextView tvDepartment;
        private final TextView tvSnippet;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomCode = itemView.findViewById(R.id.tvRoomCode);
            tvCategoryBadge = itemView.findViewById(R.id.tvCategoryBadge);
            tvFloorBadge = itemView.findViewById(R.id.tvFloorBadge);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvSnippet = itemView.findViewById(R.id.tvSnippet);
        }

        public void bind(final LocationItem item, final Context context, final OnLocationClickListener listener) {
            tvRoomCode.setText(item.getRoomNumber());
            tvCategoryBadge.setText(item.getCategory());
            tvFloorBadge.setText(item.getFloor());
            tvLocationName.setText(item.getName());

            String deptAndWing = (item.getDepartment() != null ? item.getDepartment() : "") +
                    (item.getWing() != null ? " • " + item.getWing() : "");
            tvDepartment.setText(deptAndWing);

            if (item.getInCharge() != null && !item.getInCharge().isEmpty()) {
                tvSnippet.setText(item.getInCharge());
                tvSnippet.setVisibility(View.VISIBLE);
            } else if (item.getDescription() != null && !item.getDescription().isEmpty()) {
                tvSnippet.setText(item.getDescription());
                tvSnippet.setVisibility(View.VISIBLE);
            } else {
                tvSnippet.setVisibility(View.GONE);
            }

            // Category badge colors
            int bgCol = ContextCompat.getColor(context, R.color.badge_class_bg);
            int textCol = ContextCompat.getColor(context, R.color.badge_class_text);

            if ("Laboratory".equalsIgnoreCase(item.getCategory())) {
                bgCol = ContextCompat.getColor(context, R.color.badge_lab_bg);
                textCol = ContextCompat.getColor(context, R.color.badge_lab_text);
            } else if ("Staff Rooms".equalsIgnoreCase(item.getCategory()) || "HOD & Offices".equalsIgnoreCase(item.getCategory())) {
                bgCol = ContextCompat.getColor(context, R.color.badge_staff_bg);
                textCol = ContextCompat.getColor(context, R.color.badge_staff_text);
            } else if ("Facilities".equalsIgnoreCase(item.getCategory()) || "Washrooms".equalsIgnoreCase(item.getCategory())) {
                bgCol = ContextCompat.getColor(context, R.color.badge_facility_bg);
                textCol = ContextCompat.getColor(context, R.color.badge_facility_text);
            }

            tvCategoryBadge.setBackgroundTintList(ColorStateList.valueOf(bgCol));
            tvCategoryBadge.setTextColor(textCol);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onLocationClick(item);
                    } else {
                        Intent intent = new Intent(context, LocationDetailActivity.class);
                        intent.putExtra("location_item", item);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
