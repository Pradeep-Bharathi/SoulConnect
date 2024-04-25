package com.example.first;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class RelaxTouchFragment extends Fragment {

    private RelativeLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_relax_touch, container, false);
        mainLayout = root.findViewById(R.id.main_layout);
        setTouchEffect();
        return root;
    }

    private void setTouchEffect() {
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            float initialX, initialY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        initialY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentX = event.getX();
                        float currentY = event.getY();
                        float deltaX = currentX - initialX;
                        float deltaY = currentY - initialY;

                        // If swipe distance is significant
                        if (Math.abs(deltaX) > 20 || Math.abs(deltaY) > 20) {
                            showColorfulSplash((int) event.getX(), (int) event.getY());
                            initialX = currentX;
                            initialY = currentY;
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void showColorfulSplash(int x, int y) {
        final ColorfulSplashView splashView = new ColorfulSplashView(requireContext());
        splashView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        splashView.setSplashColor(getRandomColor());
        splashView.setSplashRadius(100); // Adjust the radius as needed
        splashView.setCenterX(x);
        splashView.setCenterY(y);
        mainLayout.addView(splashView);
        splashView.startAnimation();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mainLayout.removeView(splashView);
            }
        }, 100); // Splash duration, adjust as needed
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    // ColorfulSplashView class
    public static class ColorfulSplashView extends View {
        private int splashColor;
        private int splashRadius;
        private int centerX;
        private int centerY;
        private Paint paint;

        public ColorfulSplashView(Context context) {
            this(context, null);
        }

        public ColorfulSplashView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
        }

        public void setSplashColor(int splashColor) {
            this.splashColor = splashColor;
        }

        public void setSplashRadius(int splashRadius) {
            this.splashRadius = splashRadius;
        }

        public void setCenterX(int centerX) {
            this.centerX = centerX;
        }

        public void setCenterY(int centerY) {
            this.centerY = centerY;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(splashColor);
            canvas.drawCircle(centerX, centerY, splashRadius, paint);
        }

        public void startAnimation() {
            final float maxRadius = Math.max(getWidth(), getHeight()) * 1.2f;
            for (int i = splashRadius; i < maxRadius; i += 20) {
                final int radius = i;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        splashRadius = radius;
                        invalidate();
                    }
                }, i * 10 / 50);
            }
        }
    }
}
