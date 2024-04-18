package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private SpeechRecognizer speechRecognizer;
    private TextView speechTextView;
    private TextView recordingTextView; // Added recordingTextView

    // Define mood quotes
    private final Map<String, List<String>> moodQuotes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechTextView = findViewById(R.id.speechTextView);
        Button startListeningButton = findViewById(R.id.startListeningButton);
        recordingTextView = findViewById(R.id.recordingTextView); // Initialize recordingTextView

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        //navview
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    loadFragment(new HomeFragment(), false);
                    startListeningButton.setVisibility(View.VISIBLE); // Show startListeningButton for Home
                    speechTextView.setVisibility(View.VISIBLE); // Show speechTextView for Home
                } else if (itemId == R.id.navBreath) {
                    loadFragment(new BreathFragment(), false);
                    recordingTextView.setVisibility(View.GONE); // Hide recordingTextView for Breath
                    startListeningButton.setVisibility(View.GONE); // Hide startListeningButton for Breath
                    speechTextView.setVisibility(View.GONE); // Hide speechTextView for Breath
                } else if (itemId == R.id.navMusic) {
                    loadFragment(new MusicFragment(), false);
                    recordingTextView.setVisibility(View.GONE); // Hide recordingTextView for Music
                    startListeningButton.setVisibility(View.GONE); // Hide startListeningButton for Music
                    speechTextView.setVisibility(View.GONE); // Hide speechTextView for Music
                } else if (itemId == R.id.navHelp) {
                    loadFragment(new HelpFragment(), false);
                    recordingTextView.setVisibility(View.GONE); // Hide recordingTextView for Help
                    startListeningButton.setVisibility(View.GONE); // Hide startListeningButton for Help
                    speechTextView.setVisibility(View.GONE); // Hide speechTextView for Help
                }
                return true;
            }
        });

        loadFragment(new HomeFragment(), true);

        // Initialize mood quotes
        initializeMoodQuotes();

        startListeningButton.setOnClickListener(v -> {
            startListening();
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }

    // Method to start speech recognition
    private void startListening() {
        // Show recording text
        recordingTextView.setVisibility(TextView.VISIBLE);

        // Initialize recognizer intent
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true); // Force offline recognition

        // Create a custom recognition listener to process speech input
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Speech recognition is ready
            }

            @Override
            public void onBeginningOfSpeech() {
                // Speech input has begun
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // RMS value of the input audio
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Called after the endpointer has detected a sound level
            }

            @Override
            public void onEndOfSpeech() {
                // Speech input has ended
            }

            @Override
            public void onError(int error) {
                // Error during speech recognition
                Toast.makeText(MainActivity.this, "Speech recognition error: " + error, Toast.LENGTH_SHORT).show();
                // Hide recording text on error
                recordingTextView.setVisibility(TextView.GONE);
            }

            @Override
            public void onResults(Bundle results) {
                // Speech recognition results are available
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    speechTextView.setText(recognizedText);

                    // Perform basic sentiment analysis
                    String sentiment = analyzeSentiment(recognizedText);

                    // Display random quote based on sentiment
                    displayRandomQuote(sentiment);
                }
                // Hide recording text after processing results
                recordingTextView.setVisibility(TextView.GONE);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Partial recognition results are available
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Reserved for adding future events
            }
        };

        // Set the custom recognition listener
        speechRecognizer.setRecognitionListener(listener);

        // Start speech recognition
        speechRecognizer.startListening(intent);
    }

    // Method to initialize mood quotes
    private void initializeMoodQuotes() {
        moodQuotes.put("lonely", new ArrayList<String>() {{
            add("You are never alone. You are always connected to the universe. - Unknown");
            add("The only way to find true happiness is to risk being completely cut open. - Chuck Palahniuk");
        }});
        moodQuotes.put("happy", new ArrayList<String>() {{
            add("The happiness of your life depends upon the quality of your thoughts. - Marcus Aurelius");
            add("Happiness is not by chance, but by choice. - Jim Rohn");
        }});
        moodQuotes.put("sad", new ArrayList<String>() {{
            add("The wound is the place where the light enters you. - Rumi");
            add("The pain you feel today is the strength you feel tomorrow. - Unknown");
        }});
        moodQuotes.put("founder", new ArrayList<String>() {{
            add("Pradeep Bharathi and Janani");
        }});
        moodQuotes.put("love", new ArrayList<String>() {{
            add("Love you too <33");
        }});
    }

    // Method to perform basic sentiment analysis
    private String analyzeSentiment(String text) {
        // Simplified logic for sentiment analysis
        if (text.contains("Who created this app")) {
            return "founder";
        }
        if (text.contains("I love you")) {
            return "love";
        }
        if (text.contains("happy") || text.contains("joy")) {
            return "happy";
        } else if (text.contains("sad") || text.contains("depressed")) {
            return "sad";
        } else if (text.contains("lonely") || text.contains("alone")) {
            return "lonely";
        } else {
            // Default sentiment
            return "neutral";
        }
    }

    // Method to display a random quote based on the sentiment
    private void displayRandomQuote(String sentiment) {
        List<String> quotes = moodQuotes.get(sentiment);
        if (quotes != null && !quotes.isEmpty()) {
            String randomQuote = quotes.get(new Random().nextInt(quotes.size()));

            // Display toast with custom layout
            CustomToast.showToast(this, randomQuote, Toast.LENGTH_LONG);
        } else {
            // Show a default message if no quotes are found
            Toast.makeText(this, "No quotes found for the recognized sentiment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}
