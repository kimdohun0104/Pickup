package com.example.dsm2018.pickup.fragment;

import android.animation.Animator;
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

    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                if(setStartingPoint.getVisibility() == View.GONE){

                    if(setDestination.getVisibility() == View.VISIBLE)
                        collapse(setDestination);
                    else if(setTime.getVisibility() == View.VISIBLE)
                        collapse(setTime);

                    expand(setStartingPoint);
                } else {
                    collapse(setStartingPoint);
                }
            }
        });

        destinationTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setDestination.getVisibility() == View.GONE){

                    if(setStartingPoint.getVisibility() == View.VISIBLE)
                        collapse(setStartingPoint);
                    else if(setTime.getVisibility() == View.VISIBLE)
                        collapse(setTime);

                    expand(setDestination);
                } else {
                    collapse(setDestination);
                }
            }
        });

        timeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setTime.getVisibility() == View.GONE){

                    if(setStartingPoint.getVisibility() == View.VISIBLE)
                        collapse(setStartingPoint);
                    else if(setDestination.getVisibility() == View.VISIBLE)
                        collapse(setDestination);

                    expand(setTime);
                } else {
                    collapse(setTime);
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

    private void expand(View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

        ValueAnimator mAnimator = slideAnimator(0, 315, view);

        mAnimator.start();
    }

    private void collapse(final View view) {
        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, view);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final View view) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
