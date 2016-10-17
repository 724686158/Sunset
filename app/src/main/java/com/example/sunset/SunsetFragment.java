package com.example.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;

/**
 * Created by 离子态狍子 on 2016/10/17.
 */

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private View mSeaView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean isNight;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }
    private float sunYStart;
    private float sunYEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sunset, container, false);
        isNight = false;
        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mSeaView = view.findViewById(R.id.sea);
        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        sunYStart = mSunView.getTop();
        sunYEnd = mSkyView.getHeight();


        mSunView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNight) {
                    sunDown();
                }
            }
        });

        mSeaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNight) {
                    sunUp();
                }
            }
        });
        return view;
    }

    private void sunUp() {

        ObjectAnimator heightAnimatorBack = ObjectAnimator.ofFloat(mSunView, "y", sunYEnd, sunYStart).
                setDuration(3000);
        heightAnimatorBack.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator sunsetSkyAnimatorBack = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        sunsetSkyAnimatorBack.setEvaluator(new ArgbEvaluator());
        ObjectAnimator nightSkyAnimatorBack = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(2000);
        nightSkyAnimatorBack.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(sunsetSkyAnimatorBack)
                .with(heightAnimatorBack)
                .after(nightSkyAnimatorBack);
        animatorSet.start();
        isNight = false;
    }

    private void sunDown() {

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYStart, sunYEnd).
                setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
        isNight = true;
    }
}
