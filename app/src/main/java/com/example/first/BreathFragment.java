package com.example.first;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BreathFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breath, container, false);

        // Find buttons
        Button angryButton = view.findViewById(R.id.angryButton);
        Button sadButton = view.findViewById(R.id.sadButton);
        Button nervousButton = view.findViewById(R.id.nervousButton);
        Button concentrationButton = view.findViewById(R.id.concentrationButton);

        // Set click listeners
        angryButton.setOnClickListener(this);
        sadButton.setOnClickListener(this);
        nervousButton.setOnClickListener(this);
        concentrationButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (v.getId() == R.id.angryButton) {
            loadFragment(new AngryBreathFragment());
        } else if (v.getId() == R.id.sadButton) {
            loadFragment(new SadBreathFragment());
        } else if (v.getId() == R.id.nervousButton) {
            loadFragment(new NervousBreathFragment());
        } else if (v.getId() == R.id.concentrationButton) {
            loadFragment(new ConcentrationBreathFragment());
        }
    }


    private void loadFragment(Fragment fragment) {
        // Replace the current fragment with the new one
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
