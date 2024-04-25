package com.example.first;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactUsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);

        final EditText bodyEditText = root.findViewById(R.id.bodyEditText);
        Button messageUsButton = root.findViewById(R.id.messageUsButton);
        messageUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = bodyEditText.getText().toString();
                sendMessage(body);
            }
        });

        return root;
    }

    private void sendMessage(String body) {
        String recipient = "cpradeepbharathi@gmail.com";
        String subject = "Enquiry - Feedback | SoulConnect";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + recipient + "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body)));

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle error if no email client is installed
            Toast.makeText(requireContext(), "No email client installed", Toast.LENGTH_SHORT).show();
        }
    }
}
