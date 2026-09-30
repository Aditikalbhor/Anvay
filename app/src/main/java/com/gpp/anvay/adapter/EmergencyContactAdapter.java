package com.gpp.anvay.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.gpp.anvay.R;
import com.gpp.anvay.model.EmergencyContact;

import java.util.ArrayList;
import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ContactViewHolder> {

    private final Context context;
    private List<EmergencyContact> contactList;

    public EmergencyContactAdapter(Context context, List<EmergencyContact> contactList) {
        this.context = context;
        this.contactList = contactList != null ? contactList : new ArrayList<>();
    }

    public void updateList(List<EmergencyContact> newList) {
        this.contactList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emergency_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        EmergencyContact item = contactList.get(position);
        holder.bind(item, context);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivContactIcon;
        private final TextView tvContactTitle;
        private final TextView tvContactPerson;
        private final TextView tvContactLocation;
        private final MaterialButton btnCall;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ivContactIcon = itemView.findViewById(R.id.ivContactIcon);
            tvContactTitle = itemView.findViewById(R.id.tvContactTitle);
            tvContactPerson = itemView.findViewById(R.id.tvContactPerson);
            tvContactLocation = itemView.findViewById(R.id.tvContactLocation);
            btnCall = itemView.findViewById(R.id.btnCall);
        }

        public void bind(final EmergencyContact item, final Context context) {
            tvContactTitle.setText(item.getTitle());
            tvContactPerson.setText(item.getContactPerson() + " • " + item.getPhoneNumber());
            tvContactLocation.setText(item.getLocation());

            if ("medical".equalsIgnoreCase(item.getIconType())) {
                ivContactIcon.setImageResource(R.drawable.ic_medical);
            } else if ("fire".equalsIgnoreCase(item.getIconType())) {
                ivContactIcon.setImageResource(R.drawable.ic_fire);
            } else if ("admin".equalsIgnoreCase(item.getIconType())) {
                ivContactIcon.setImageResource(R.drawable.ic_building);
            } else {
                ivContactIcon.setImageResource(R.drawable.ic_phone);
            }

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String cleanPhone = item.getPhoneNumber().replaceAll("[^0-9+]", "");
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cleanPhone));
                        context.startActivity(dialIntent);
                    } catch (Exception e) {
                        Toast.makeText(context, "Dialing: " + item.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
