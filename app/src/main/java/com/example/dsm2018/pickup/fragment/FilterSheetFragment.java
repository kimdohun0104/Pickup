package com.example.dsm2018.pickup.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;

public class FilterSheetFragment extends BottomSheetDialogFragment {

    RelativeLayout startingPointTab;
    RelativeLayout setStartingPoint;
    boolean isSetStartingPointOpen = false;

    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sheet, container, false);

        startingPointTab = (RelativeLayout)view.findViewById(R.id.startingPointTab);
        setStartingPoint = (RelativeLayout)view.findViewById(R.id.setStartingPoint);

        setStartingPoint.setVisibility(View.GONE);

        startingPointTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSetStartingPointOpen){
                    collapse(setStartingPoint);
                    isSetStartingPointOpen = false;
                } else {
                    expand(setStartingPoint, 200, 300);
                    isSetStartingPointOpen = true;
                }

            }
        });

        return view;
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(200);
        v.startAnimation(a);
    }
}
