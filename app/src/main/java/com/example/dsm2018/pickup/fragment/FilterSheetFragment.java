package com.example.dsm2018.pickup.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;

public class FilterSheetFragment extends BottomSheetDialogFragment {

    TextView testView;
    RelativeLayout setStartingPoint;

    public static FilterSheetFragment getInstance() {
        return new FilterSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sheet, container, false);

        testView = (TextView)view.findViewById(R.id.test);
        setStartingPoint = (RelativeLayout)view.findViewById(R.id.setStartingPoint);

        testView.setVisibility(View.GONE);

        setStartingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testView.isShown()){
                    slide_down(testView);
                    testView.setVisibility(View.GONE);
                } else {
                    slide_up(testView);
                    testView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    public void slide_down(View v){
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        if(a != null) {
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public void slide_up(View v){
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
