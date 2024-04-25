package com.example.first;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        listView = view.findViewById(R.id.listViewContacts);

        // Define a list of mental health contacts with their phone numbers
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Mental Health Hotline", "+1234567890"));
        contacts.add(new Contact("Crisis Helpline", "+1987654321"));
        // Add more contacts as needed

        // Create a custom adapter for the contacts
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(getContext(), android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);

        // Set click listener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected contact
                Contact contact = contacts.get(position);

                // Initiate a phone call intent
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                startActivity(intent);
            }
        });

        return view;
    }
}
