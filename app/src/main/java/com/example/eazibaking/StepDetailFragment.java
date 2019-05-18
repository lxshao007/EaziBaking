package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Step;
import com.example.eazibaking.databinding.FragmentStepDetailBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.reflect.TypeToken;

public class StepDetailFragment extends Fragment {

    private static final String ARG_STEP = "ARG_STEP";

    private String arg_step;
    private FragmentStepDetailBinding binding;
    private Step step;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

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
        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt("currentWindow");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            playWhenReady = savedInstanceState.getBoolean("playWhenStart", playWhenReady);
        } else {
            currentWindow = 0;
            playbackPosition = 0;
            playWhenReady = true;

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.tvShortDescription.setText(step.getShortDescription());
        binding.tvDescription.setText(step.getDescription());
        mSimpleExoPlayerView = binding.exoPlayerView;
        return binding.getRoot();
    }

    private void initializePlayer(String videoUrl) {

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        mSimpleExoPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.setPlayWhenReady(playWhenReady);
        mExoPlayer.seekTo(currentWindow, playbackPosition);
        Uri videoUri = Uri.parse(videoUrl);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("step-video")).createMediaSource(videoUri);
        mExoPlayer.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(step.getVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(step.getVideoURL());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        playbackPosition = mExoPlayer.getCurrentPosition();
        currentWindow = mExoPlayer.getCurrentWindowIndex();
        playWhenReady = mExoPlayer.getPlayWhenReady();
        outState.putInt("currentWindow", currentWindow);
        outState.putLong("playbackPosition", playbackPosition);
        outState.putBoolean("playWhenStart", playWhenReady);
    }
}
