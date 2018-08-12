package com.example.dsm2018.pickup.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.SearchDestinationActivity;
import com.example.dsm2018.pickup.activity.SearchStartingPointActivity;
import com.example.dsm2018.pickup.dialog.EmailDialog;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;

public class FilterSheetFragment extends BottomSheetDialogFragment{

    RelativeLayout startingPointTab, setStartingPoint, destinationTab, setDestination, timeTab;
    LinearLayout setTime;
    TextView cancel;

    boolean isSetStartingPointOpen = false, isSetDestinationOpen = false, isSetTimeOpen = false;

    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sheet, container, false);

        startingPointTab = (RelativeLayout)view.findViewById(R.id.startingPointTab);
        setStartingPoint = (RelativeLayout)view.findViewById(R.id.setStartingPoint);
        destinationTab = (RelativeLayout)view.findViewById(R.id.destinationTab);
        setDestination = (RelativeLayout)view.findViewById(R.id.setDestination);
        timeTab = (RelativeLayout)view.findViewById(R.id.timeTab);
        setTime = (LinearLayout)view.findViewById(R.id.setTime);
        cancel = (TextView)view.findViewById(R.id.cancelButton);

        setStartingPoint.setVisibility(View.GONE);
        setDestination.setVisibility(View.GONE);
        setTime.setVisibility(View.GONE);

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

        destinationTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSetDestinationOpen){
                    collapse(setDestination);
                    isSetDestinationOpen = false;
                } else {
                    expand(setDestination, 200, 300);
                    isSetDestinationOpen = true;
                }
            }
        });

        timeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSetTimeOpen){
                    collapse(setTime);
                    isSetTimeOpen = false;
                } else {
                    expand(setTime, 200, 300);
                    isSetTimeOpen = true;
                }
            }
        });

        setStartingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
                startActivity(intent);
            }
        });

        setDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchDestinationActivity.class);
                startActivity(intent);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                SearchDateDialog dialogFragment = new SearchDateDialog();
                dialogFragment.show(fragmentManager, "searchDateDialog");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void expand(final View v, int duration, int targetHeight) {

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

    public void collapse(final View v) {
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
