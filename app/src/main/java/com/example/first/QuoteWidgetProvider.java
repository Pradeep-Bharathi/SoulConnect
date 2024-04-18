package com.example.first;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.widget.RemoteViews;

import java.util.Random;

public class QuoteWidgetProvider extends AppWidgetProvider {

    private static final long INITIAL_DELAY = 1000; // 1 second
    private static final long QUOTE_DELAY = 5000; // 5 seconds
    private static final long SOULCONNECT_DELAY = 1000; // 1 second
    private static boolean isRunning = false;
    private static boolean showSoulConnect = true;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final Handler handler = new Handler();
        final Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                if (showSoulConnect) {
                    updateWidget(context, appWidgetManager, "SoulConnect.");
                    handler.postDelayed(this, SOULCONNECT_DELAY);
                    showSoulConnect = false;
                } else {
                    updateWidget(context, appWidgetManager, getRandomQuote());
                    handler.postDelayed(this, QUOTE_DELAY);
                    showSoulConnect = true;
                }
            }
        };
        handler.postDelayed(updateRunnable, INITIAL_DELAY);
        isRunning = true;
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        isRunning = false;
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, String text) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widgetQuoteTextView, text);
        ComponentName thisWidget = new ComponentName(context, QuoteWidgetProvider.class);
        appWidgetManager.updateAppWidget(thisWidget, views);
    }

    private String getRandomQuote() {
        String[] quotes = {
                "The only way to do great work is to love what you do. - Steve Jobs",
                "In the end, it's not the years in your life that count. It's the life in your years. - Abraham Lincoln",
                "Don't watch the clock; do what it does. Keep going. - Sam Levenson",
                "The only impossible journey is the one you never begin. - Tony Robbins",
                "You must be the change you wish to see in the world. - Mahatma Gandhi"
        };
        int index = new Random().nextInt(quotes.length);
        return quotes[index];
    }
}
