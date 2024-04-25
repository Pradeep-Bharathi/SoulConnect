package com.example.first;

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

public class NavigationDrawerFragment extends Fragment {

    private ListView drawerListView;

    // Array of menu items
    private String[] menuItems = {"Item 1", "Item 2", "Item 3"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        // Initialize ListView
        drawerListView = rootView.findViewById(R.id.drawer_menu);

        // Create ArrayAdapter to populate ListView with menu items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, menuItems);

        // Set adapter to ListView
        drawerListView.setAdapter(adapter);

        // Set item click listener for ListView
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here (e.g., navigate to different fragments)
            }
        });

        return rootView;
    }
}
