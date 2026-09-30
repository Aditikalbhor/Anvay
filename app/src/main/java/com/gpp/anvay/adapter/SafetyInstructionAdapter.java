package com.gpp.anvay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpp.anvay.R;
import com.gpp.anvay.model.SafetyInstruction;

import java.util.ArrayList;
import java.util.List;

public class SafetyInstructionAdapter extends RecyclerView.Adapter<SafetyInstructionAdapter.SafetyViewHolder> {

    private final Context context;
    private List<SafetyInstruction> instructionList;

    public SafetyInstructionAdapter(Context context, List<SafetyInstruction> instructionList) {
        this.context = context;
        this.instructionList = instructionList != null ? instructionList : new ArrayList<>();
    }

    public void updateList(List<SafetyInstruction> newList) {
        this.instructionList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SafetyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_safety_instruction, parent, false);
        return new SafetyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyViewHolder holder, int position) {
        SafetyInstruction item = instructionList.get(position);
        holder.bind(item, context);
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public static class SafetyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivSafetyIcon;
        private final TextView tvSafetyTitle;
        private final TextView tvSafetySummary;
        private final TextView tvSafetySteps;
        private final TextView tvAssemblyPoint;

        public SafetyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSafetyIcon = itemView.findViewById(R.id.ivSafetyIcon);
            tvSafetyTitle = itemView.findViewById(R.id.tvSafetyTitle);
            tvSafetySummary = itemView.findViewById(R.id.tvSafetySummary);
            tvSafetySteps = itemView.findViewById(R.id.tvSafetySteps);
            tvAssemblyPoint = itemView.findViewById(R.id.tvAssemblyPoint);
        }

        public void bind(final SafetyInstruction item, final Context context) {
            tvSafetyTitle.setText(item.getTitle());
            tvSafetySummary.setText(item.getSummary());

            StringBuilder sb = new StringBuilder();
            if (item.getSteps() != null) {
                for (int i = 0; i < item.getSteps().size(); i++) {
                    sb.append((i + 1)).append(". ").append(item.getSteps().get(i));
                    if (i < item.getSteps().size() - 1) {
                        sb.append("\n\n");
                    }
                }
            }
            tvSafetySteps.setText(sb.toString());
            tvAssemblyPoint.setText("Assembly Point: " + item.getAssemblyPoint());

            if ("shield".equalsIgnoreCase(item.getIconType())) {
                ivSafetyIcon.setImageResource(R.drawable.ic_shield);
            } else if ("medical".equalsIgnoreCase(item.getIconType())) {
                ivSafetyIcon.setImageResource(R.drawable.ic_medical);
            } else if ("stairs".equalsIgnoreCase(item.getIconType())) {
                ivSafetyIcon.setImageResource(R.drawable.ic_stairs);
            } else {
                ivSafetyIcon.setImageResource(R.drawable.ic_fire);
            }
        }
    }
}
