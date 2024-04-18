package com.example.first;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AngryBreathFragment extends Fragment {

    private TextView countdownTextView;
    private TextView instructionsTextView;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_angry_breath, container, false);

        countdownTextView = view.findViewById(R.id.countdownTextView);
        instructionsTextView = view.findViewById(R.id.instructionsTextView);
        Button startExerciseButton = view.findViewById(R.id.startExerciseButton);

        startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreathingExercise();
            }
        });

        return view;
    }

    private void startBreathingExercise() {
        // Show countdown timer and hide instructions
        countdownTextView.setVisibility(View.VISIBLE);
        instructionsTextView.setVisibility(View.VISIBLE);

        // Define the duration of the exercise (20 seconds)
        final long exerciseDuration = 20 * 1000; // in milliseconds
        final long halfDuration = exerciseDuration / 2; // Duration for each inhale/exhale phase

        // Start the countdown timer
        countDownTimer = new CountDownTimer(exerciseDuration, 1000) {
            boolean isInhalePhase = true;

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                countdownTextView.setText("Time remaining: " + secondsLeft + " seconds");

                // Check if it's time to switch between inhale and exhale phases
                if (millisUntilFinished <= halfDuration && isInhalePhase) {
                    instructionsTextView.setText("Exhale slowly...");
                    isInhalePhase = false; // Change to exhale phase
                } else if (millisUntilFinished <= halfDuration && !isInhalePhase) {
                    instructionsTextView.setText("Inhale deeply...");
                    isInhalePhase = true; // Change to inhale phase
                }
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("Exercise completed.");
                instructionsTextView.setText("Press the button to start the exercise");
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cancel the countdown timer to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
