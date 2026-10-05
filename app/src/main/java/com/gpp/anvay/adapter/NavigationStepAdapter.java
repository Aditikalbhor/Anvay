package com.gpp.anvay.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.gpp.anvay.R;
import com.gpp.anvay.model.position.NavigationStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying turn-by-turn navigation steps in NavigationActivity.
 */
public class NavigationStepAdapter extends RecyclerView.Adapter<NavigationStepAdapter.StepViewHolder> {

    public interface OnStepClickListener {
        void onStepClick(int position, NavigationStep step);
    }

    private final Context context;
    private List<NavigationStep> stepList;
    private int activeStepIndex;
    private final OnStepClickListener listener;

    public NavigationStepAdapter(Context context, List<NavigationStep> stepList, OnStepClickListener listener) {
        this.context = context;
        this.stepList = stepList != null ? stepList : new ArrayList<>();
        this.activeStepIndex = 0;
        this.listener = listener;
    }

    public void updateSteps(List<NavigationStep> newSteps, int activeIndex) {
        this.stepList = newSteps != null ? newSteps : new ArrayList<>();
        this.activeStepIndex = activeIndex;
        notifyDataSetChanged();
    }

    public void setActiveStepIndex(int activeIndex) {
        this.activeStepIndex = activeIndex;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_navigation_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        NavigationStep step = stepList.get(position);
        holder.bind(step, position, position == activeStepIndex, context, listener);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final FrameLayout iconContainer;
        private final ImageView ivIcon;
        private final TextView tvInstruction;
        private final TextView tvFloor;
        private final TextView tvNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardStepItem);
            iconContainer = itemView.findViewById(R.id.layoutStepIconContainer);
            ivIcon = itemView.findViewById(R.id.ivStepIcon);
            tvInstruction = itemView.findViewById(R.id.tvStepInstruction);
            tvFloor = itemView.findViewById(R.id.tvStepFloor);
            tvNumber = itemView.findViewById(R.id.tvStepNumber);
        }

        public void bind(final NavigationStep step, final int position, boolean isActive,
                         final Context context, final OnStepClickListener listener) {
            tvInstruction.setText(step.getInstruction());
            tvFloor.setText(step.getFloor());
            tvNumber.setText("Step " + (position + 1));

            // Set appropriate icon based on step type
            if (NavigationStep.STEP_STAIRCASE.equals(step.getStepType())) {
                ivIcon.setImageResource(R.drawable.ic_stairs);
                iconContainer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.warning_container)));
                ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.warning_amber)));
            } else if (NavigationStep.STEP_ARRIVE.equals(step.getStepType())) {
                ivIcon.setImageResource(R.drawable.ic_check_circle);
                iconContainer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.badge_lab_bg)));
                ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.success_green)));
            } else {
                ivIcon.setImageResource(R.drawable.ic_navigation);
                iconContainer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary_container)));
                ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary)));
            }

            // Highlight active step
            if (isActive) {
                cardView.setStrokeColor(ContextCompat.getColor(context, R.color.primary));
                cardView.setStrokeWidth(4);
                cardView.setCardElevation(4f);
                tvInstruction.setTextColor(ContextCompat.getColor(context, R.color.primary));
            } else {
                cardView.setStrokeColor(ContextCompat.getColor(context, R.color.divider));
                cardView.setStrokeWidth(2);
                cardView.setCardElevation(1f);
                tvInstruction.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onStepClick(position, step);
                    }
                }
            });
        }
    }
}
