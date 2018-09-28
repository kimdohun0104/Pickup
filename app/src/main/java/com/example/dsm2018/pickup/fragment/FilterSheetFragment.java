package com.example.dsm2018.pickup.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.SearchEndPointActivity;
import com.example.dsm2018.pickup.activity.SearchStartingPointActivity;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;

public class FilterSheetFragment extends BottomSheetDialogFragment{

    RelativeLayout startingPointTab, setStartingPointView, destinationTab, setDestinationView, timeTab;
    LinearLayout setTimeView;
    TextView setStartingPoint, setDestination, setDate, setTime, cancel;
    ImageView startingPointIcon, endPointIcon;

    @NonNull
    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100) {
            String title = data.getExtras().getString("startingPoint");
            setStartingPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            setStartingPoint.setText(title);
            setStartingPoint.setTextColor(getResources().getColor(R.color.colorTextBlack));
            startingPointIcon.setImageResource(R.drawable.ic_location_orange);
        } else if(resultCode == 101) {
            String title = data.getExtras().getString("destination");
            setDestination.setBackgroundResource(R.drawable.round_layout_side_orange);
            setDestination.setText(title);
            setDestination.setTextColor(getResources().getColor(R.color.colorTextBlack));
            endPointIcon.setImageResource(R.drawable.ic_location_orange);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sheet, container, false);

        startingPointTab = (RelativeLayout) view.findViewById(R.id.startingPointTab);
        setStartingPointView = (RelativeLayout) view.findViewById(R.id.setStartingPointView);
        setStartingPoint = (TextView) view.findViewById(R.id.setStartingPoint);
        destinationTab = (RelativeLayout) view.findViewById(R.id.destinationTab);
        setDestinationView = (RelativeLayout) view.findViewById(R.id.setDestinationView);
        setDestination = (TextView) view.findViewById(R.id.setDestination);
        timeTab = (RelativeLayout) view.findViewById(R.id.timeTab);
        setTimeView = (LinearLayout) view.findViewById(R.id.setTimeView);
        setDate = (TextView) view.findViewById(R.id.setDate);
        setTime = (TextView) view.findViewById(R.id.setTime);
        cancel = (TextView) view.findViewById(R.id.cancelButton);
        startingPointIcon = (ImageView) view.findViewById(R.id.startingPointIcon);
        endPointIcon = (ImageView) view.findViewById(R.id.endPointIcon);

        setStartingPointView.setVisibility(View.GONE);
        setDestinationView.setVisibility(View.GONE);
        setTimeView.setVisibility(View.GONE);

        startingPointTab.setOnClickListener(v -> {
            if(setStartingPointView.getVisibility() == View.GONE){

                if(setDestinationView.getVisibility() == View.VISIBLE)
                    collapse(setDestinationView);
                else if(setTimeView.getVisibility() == View.VISIBLE)
                    collapse(setTimeView);

                expand(setStartingPointView);
            } else {
                collapse(setStartingPointView);
            }
        });

        destinationTab.setOnClickListener(v -> {
            if(setDestinationView.getVisibility() == View.GONE){

                if(setStartingPointView.getVisibility() == View.VISIBLE)
                    collapse(setStartingPointView);
                else if(setTimeView.getVisibility() == View.VISIBLE)
                    collapse(setTimeView);

                expand(setDestinationView);
            } else {
                collapse(setDestinationView);
            }
        });

        timeTab.setOnClickListener(v -> {
            if(setTimeView.getVisibility() == View.GONE){

                if(setStartingPointView.getVisibility() == View.VISIBLE)
                    collapse(setStartingPointView);
                else if(setDestinationView.getVisibility() == View.VISIBLE)
                    collapse(setDestinationView);

                expand(setTimeView);
            } else {
                collapse(setTimeView);
            }
        });

        setStartingPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
            startActivityForResult(intent, 100);
        });

        setDestination.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchEndPointActivity.class);
            startActivityForResult(intent, 101);
        });

        setDate.setOnClickListener(v -> {
            SearchDateDialog searchDateDialog = new SearchDateDialog(getActivity());
            searchDateDialog.showDialog();
        });

        setTime.setOnClickListener(v -> {
            SearchTimeDialog searchTimeDialog = new SearchTimeDialog(getActivity());
            searchTimeDialog.showDialog();
        });

        cancel.setOnClickListener(v -> dismiss());

        return view;
    }


    private void expand(View view) {
        view.setVisibility(View.VISIBLE);

        ValueAnimator mAnimator = slideAnimator(0, dpToPx(getActivity(), 100), view);

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

        animator.addUpdateListener(valueAnimator -> {
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.setLayoutParams(layoutParams);
        });
        return animator;
    }

    private int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
