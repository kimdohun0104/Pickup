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
import com.example.dsm2018.pickup.activity.SearchActivity;
import com.example.dsm2018.pickup.activity.SearchEndPointActivity;
import com.example.dsm2018.pickup.activity.SearchStartingPointActivity;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;

public class FilterSheetFragment extends BottomSheetDialogFragment{

    RelativeLayout startingPointTab, setStartingPointView, destinationTab, setDestinationView, timeTab;
    LinearLayout setTimeView;
    TextView setStartingPoint, setDestination, setDate, setTime, cancelButton, doneButton;
    ImageView startingPointIcon, endPointIcon;

    SearchActivity searchActivity;
    Bundle filterBundle;

    boolean isDeparture = false, isDestination = false, isTime = false, isDate = false;

    @NonNull
    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100) {
            String title = data.getExtras().getString("startingPointName");
            setStartingPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            setStartingPoint.setText(title);
            setStartingPoint.setTextColor(getResources().getColor(R.color.colorTextBlack));
            startingPointIcon.setImageResource(R.drawable.ic_location_orange);

            filterBundle.putString("filter_departure_lat", String.valueOf(data.getExtras().getDouble("latitude")));
            filterBundle.putString("filter_departure_lng", String.valueOf(data.getExtras().getDouble("longitude")));

            isDeparture = true;
            confirmSetting();
        } else if(resultCode == 101) {
            String title = data.getExtras().getString("endPointName");
            setDestination.setBackgroundResource(R.drawable.round_layout_side_orange);
            setDestination.setText(title);
            setDestination.setTextColor(getResources().getColor(R.color.colorTextBlack));
            endPointIcon.setImageResource(R.drawable.ic_location_orange);

            filterBundle.putString("filter_destination_lat", String.valueOf(data.getExtras().getDouble("latitude")));
            filterBundle.putString("filter_destination_lng", String.valueOf(data.getExtras().getDouble("longitude")));

            isDestination = true;
            confirmSetting();
        } else if(resultCode == 102) {
            filterBundle.putString("filter_date_year", data.getExtras().getString("party_year"));
            filterBundle.putString("filter_party_month", data.getExtras().getString("party_month"));
            filterBundle.putString("filter_party_day", data.getExtras().getString("party_day"));

            isDate = true;
            confirmSetting();
        } else if(resultCode == 103) {
            filterBundle.putString("filter_party_hour", data.getExtras().getString("party_hour"));
            filterBundle.putString("filter_party_minute", data.getExtras().getString("party_minute"));

            isTime = true;
            confirmSetting();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sheet, container, false);

        searchActivity = (SearchActivity) getActivity();

        startingPointTab = view.findViewById(R.id.startingPointTab);
        setStartingPointView = view.findViewById(R.id.setStartingPointView);
        setStartingPoint = view.findViewById(R.id.setStartingPoint);
        destinationTab = view.findViewById(R.id.endPointTab);
        setDestinationView = view.findViewById(R.id.setDestinationView);
        setDestination = view.findViewById(R.id.setDestination);
        timeTab = view.findViewById(R.id.timeTab);
        setTimeView = view.findViewById(R.id.setTimeView);
        setDate = view.findViewById(R.id.setDate);
        setTime = view.findViewById(R.id.setTime);
        cancelButton = view.findViewById(R.id.cancelButton);
        startingPointIcon = view.findViewById(R.id.startingPointIcon);
        endPointIcon = view.findViewById(R.id.endPointIcon);
        doneButton = view.findViewById(R.id.doneButton);

        doneButton.setEnabled(false);

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

        setDate.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), SearchDateDialog.class), 102));

        setTime.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), SearchTimeDialog.class), 103));

        cancelButton.setOnClickListener(v -> dismiss());

        doneButton.setOnClickListener(v -> {
            searchActivity.filterBundle = this.filterBundle;
            searchActivity.isFilter = true;
            dismiss();
        });

        return view;
    }

    private void confirmSetting() {
        if(isDestination && isDeparture && isDate && isTime) {
            doneButton.setEnabled(true);
        }
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
