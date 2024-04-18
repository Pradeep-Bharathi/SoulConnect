package com.example.first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.Gravity;

import android.widget.Toast;

public class CustomToast {

    public static void showToast(Context context, String message, int duration) {
        // Inflate custom layout for the toast message
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);

        // Set the text of the toast message
        TextView textView = layout.findViewById(R.id.customToastMessage);
        textView.setText(message);

        // Create and configure the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);

        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 250);
        // Show the toast message
        toast.show();
    }
}
