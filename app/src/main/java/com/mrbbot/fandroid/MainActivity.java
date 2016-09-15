package com.mrbbot.fandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends PlaceholderUIActivity {

    private ObjectAnimator startAnimation, rotateAnimation, stopAnimation;
    private boolean rotating, changing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView blades = (ImageView) findViewById(R.id.bladesView);

        startAnimation = ObjectAnimator.ofFloat(blades, "rotation", 0f, 360f);
        startAnimation.setDuration(1000);
        startAnimation.setInterpolator(new AccelerateInterpolator(1));
        startAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateAnimation.start();
                changing = false;
            }
        });

        rotateAnimation = ObjectAnimator.ofFloat(blades, "rotation", 0f, 360f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        rotateAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                if(!rotating) {
                    rotateAnimation.cancel();
                    stopAnimation.start();
                }
            }
        });

        stopAnimation = ObjectAnimator.ofFloat(blades, "rotation", 0f, 360f);
        stopAnimation.setDuration(1000);
        stopAnimation.setInterpolator(new DecelerateInterpolator(1));
        stopAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                changing = false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!changing) {
                    rotating = !rotating;
                    changing = true;

                    if(rotating)
                        startAnimation.start();
                }
            }
        });
    }
}
