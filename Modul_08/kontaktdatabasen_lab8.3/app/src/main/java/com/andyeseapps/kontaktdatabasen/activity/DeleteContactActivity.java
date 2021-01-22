package com.andyeseapps.kontaktdatabasen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.adapter.ContactListAdapter;
import com.andyeseapps.kontaktdatabasen.viewmodel.ContactViewModel;
import com.andyeseapps.kontaktdatabasen.RecyclerItemClickListener;


public class DeleteContactActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "contactID";
    private int contactId;
    private AlertDialog alertDialogDeletion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        setUpRecyclerViewWithContactsAndListener();
        setUpAlertDialogForDeletion();

        Utils.setToolbar(this);
    }

    /**
     * Sets up alert-dialog for deletion
     */
    private void setUpAlertDialogForDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteContactActivity.this);
        builder.setMessage(getString(R.string.alert_dialog_delete_contact));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, contactId);
            setResult(RESULT_OK, replyIntent);
            dialog.cancel();
            finish();
        });
        builder.setNegativeButton(getString(R.string.no), (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        alertDialogDeletion = builder.create();
    }

    /**
     * Populates and displays RecyclerView with Contacts
     */
    private void setUpRecyclerViewWithContactsAndListener() {
        RecyclerView recyclerView = findViewById(R.id.delete_recyclerview_contact);
        final ContactListAdapter contactListAdapter = new ContactListAdapter(this);
        recyclerView.setAdapter(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContactViewModel mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contactListAdapter::setContacts);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        contactId = contactListAdapter.getItem(position).getContactID();
                        alertDialogDeletion.show();
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}