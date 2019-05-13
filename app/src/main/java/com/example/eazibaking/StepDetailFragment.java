package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Step;
import com.example.eazibaking.databinding.FragmentStepDetailBinding;
import com.google.gson.reflect.TypeToken;

public class StepDetailFragment extends Fragment {

    private static final String ARG_STEP = "ARG_STEP";

    private String arg_step;

    private StepDetailViewModel mViewModel;
    private FragmentStepDetailBinding binding;
    private Step step;

    public static StepDetailFragment newInstance(String param) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STEP, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arg_step = getArguments().getString(ARG_STEP);
            step = ModelUtils.toObject(arg_step, new TypeToken<Step>(){});
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.tvShortDescription.setText(step.getShortDescription());
        binding.tvDescription.setText(step.getDescription());
        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
