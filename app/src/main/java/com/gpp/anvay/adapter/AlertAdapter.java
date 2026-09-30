package com.gpp.anvay.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gpp.anvay.R;
import com.gpp.anvay.model.AlertItem;

import java.util.ArrayList;
import java.util.List;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    public interface OnAlertClickListener {
        void onAlertClick(AlertItem alert);
    }

    private final Context context;
    private List<AlertItem> alertList;
    private final OnAlertClickListener listener;

    public AlertAdapter(Context context, List<AlertItem> alertList, OnAlertClickListener listener) {
        this.context = context;
        this.alertList = alertList != null ? alertList : new ArrayList<>();
        this.listener = listener;
    }

    public void updateList(List<AlertItem> newList) {
        this.alertList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alert_card, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        AlertItem item = alertList.get(position);
        holder.bind(item, context, listener);
    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    public static class AlertViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAlertPriority;
        private final TextView tvAlertCategory;
        private final TextView tvAlertTimestamp;
        private final TextView tvAlertTitle;
        private final TextView tvAlertMessage;
        private final TextView tvLocationAffected;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlertPriority = itemView.findViewById(R.id.tvAlertPriority);
            tvAlertCategory = itemView.findViewById(R.id.tvAlertCategory);
            tvAlertTimestamp = itemView.findViewById(R.id.tvAlertTimestamp);
            tvAlertTitle = itemView.findViewById(R.id.tvAlertTitle);
            tvAlertMessage = itemView.findViewById(R.id.tvAlertMessage);
            tvLocationAffected = itemView.findViewById(R.id.tvLocationAffected);
        }

        public void bind(final AlertItem item, final Context context, final OnAlertClickListener listener) {
            tvAlertPriority.setText(item.getPriority());
            tvAlertCategory.setText(item.getCategory());
            tvAlertTimestamp.setText(item.getTimestamp());
            tvAlertTitle.setText(item.getTitle());
            tvAlertMessage.setText(item.getMessage());

            if (item.getLocationAffected() != null && !item.getLocationAffected().isEmpty()) {
                tvLocationAffected.setText("Affected: " + item.getLocationAffected());
                tvLocationAffected.setVisibility(View.VISIBLE);
            } else {
                tvLocationAffected.setVisibility(View.GONE);
            }

            // Priority colors
            int bgCol = ContextCompat.getColor(context, R.color.surface_variant);
            int textCol = ContextCompat.getColor(context, R.color.text_primary);

            if ("Urgent".equalsIgnoreCase(item.getPriority()) || "Emergency".equalsIgnoreCase(item.getCategory())) {
                bgCol = ContextCompat.getColor(context, R.color.emergency_container);
                textCol = ContextCompat.getColor(context, R.color.emergency_red);
            } else if ("Medium".equalsIgnoreCase(item.getPriority()) || "Maintenance".equalsIgnoreCase(item.getCategory())) {
                bgCol = ContextCompat.getColor(context, R.color.warning_container);
                textCol = ContextCompat.getColor(context, R.color.warning_amber);
            } else {
                bgCol = ContextCompat.getColor(context, R.color.info_container);
                textCol = ContextCompat.getColor(context, R.color.info_blue);
            }

            tvAlertPriority.setBackgroundTintList(ColorStateList.valueOf(bgCol));
            tvAlertPriority.setTextColor(textCol);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAlertClick(item);
                    }
                }
            });
        }
    }
}
