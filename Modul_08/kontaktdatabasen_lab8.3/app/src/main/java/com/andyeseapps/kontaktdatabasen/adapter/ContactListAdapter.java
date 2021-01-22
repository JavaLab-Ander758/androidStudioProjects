package com.andyeseapps.kontaktdatabasen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.entities.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactItemView;

        private ContactViewHolder(View itemView) {
            super(itemView);
            contactItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> mContacts; // Cached copy of contacts

    public ContactListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            holder.contactItemView.setText(String.format("%s %s (%s)", current.getFirstName(), current.getLastName(), current.getEmail()));
        } else {
            // Covers the case of data not being ready yet.
            holder.contactItemView.setText(R.string.general_no_contacts);
        }
    }

    public void setContacts(List<Contact> contacts){
        mContacts = contacts;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mContacts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }

    public Contact getItem(int position) {
        return mContacts.get(position);
    }

    public List<Contact> getAllContacts() {
        return mContacts;
    }
}