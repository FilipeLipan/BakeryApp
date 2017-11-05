package com.github.filipelipan.bakeryapp.modules.recipe_steps.step;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.StepsActivity;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.CardAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.CardFragmentPagerAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.IRecipeStepsView;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsPresenter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lispa on 22/10/2017.
 */

public class StepFragment extends AppFragment<IRecipeStepsView, RecipeStepsPresenter> implements IStepView, ExoPlayer.EventListener {

	public static final String STEP_KEY = "STEP_KEY";
	public static final String IS_LAST_ITEM_KEY = "IS_LAST_ITEM_KEY";

	private Step mStep;

	private boolean mIsLastItem = false;

	@BindView(R.id.description_tv)
	TextView descriptionTextView;
	@BindView(R.id.next_bt)
	Button mNextButton;
	@BindView(R.id.cardView)
	CardView mCardView;
	private StepFragmentListItemListener listener;


	//////////////////////////// ExoPlayer

	@BindView(R.id.playerView_sep)
	SimpleExoPlayerView mPlayerView;
	private static final int CORRECT_ANSWER_DELAY_MILLIS = 1000;
	private static final String REMAINING_SONGS_KEY = "remaining_songs";
	private static final String TAG = StepFragment.class.getSimpleName();
	private ArrayList<Integer> mRemainingSampleIDs;
	private ArrayList<Integer> mQuestionSampleIDs;
	private int mAnswerSampleID;
	private int mCurrentScore;
	private int mHighScore;
	private Button[] mButtons;
	private SimpleExoPlayer mExoPlayer;
	private static MediaSessionCompat mMediaSession;
	private PlaybackStateCompat.Builder mStateBuilder;

	//////////////////////////// ExoPlayer

	public static StepFragment newInstance(Step step, boolean isLastItem){
		StepFragment stepFragment = new StepFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(STEP_KEY, step);
		bundle.putBoolean(IS_LAST_ITEM_KEY, isLastItem);
		stepFragment.setArguments(bundle);
		return stepFragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.d("", "");
		// This makes sure that the host activity has implemented the callback interface
		// If not, it throws an exception
		try {
			listener = (StepFragmentListItemListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString()
					+ " must implement StepFragmentListItemListener");
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if(getArguments().containsKey(STEP_KEY) && getArguments().containsKey(IS_LAST_ITEM_KEY)){
			mStep = getArguments().getParcelable(STEP_KEY);
			mIsLastItem = getArguments().getBoolean(IS_LAST_ITEM_KEY);

			//set up player background image
			Glide.with(getContext())
					.asBitmap()
					.load(mStep.getThumbnailURL())
					.into(new SimpleTarget<Bitmap>() {
						@Override
						public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
							mPlayerView.setDefaultArtwork(resource);
						}
					});

			descriptionTextView.setText(mStep.getDescription());

			if(mIsLastItem){
				mNextButton.setText(getContext().getString(R.string.finish));
			}
		}

		mCardView.setMaxCardElevation(mCardView.getCardElevation()
				* CardAdapter.MAX_ELEVATION_FACTOR);


		mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
		mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

		// Initialize the Media Session.
		initializeMediaSession();

		// Initialize the player.
		initializePlayer(Uri.parse(mStep.getVideoURL()));
	}

	@OnClick(R.id.next_bt)
	public void onNextClick(){
		if(mIsLastItem){
			listener.onLastStepClick();
		}else {
			listener.onNextStepClick(mStep);
		}
	}

	@Override
	public String getFragmentTag() {
		return StepFragment.class.getSimpleName();
	}

	@Override
	public int getFragmentLayout() {
		return R.layout.step_fragment;
	}

	@Override
	public RecipeStepsPresenter createPresenter() {
		return new RecipeStepsPresenter();
	}

	public CardView getCardView() {
		return mCardView;
	}

	public interface StepFragmentListItemListener {
		void onNextStepClick(Step stepClicked);
		void onLastStepClick();
	}

	/**
	 * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
	 * and media controller.
	 */
	private void initializeMediaSession() {

		// Create a MediaSessionCompat.
		mMediaSession = new MediaSessionCompat(getContext(), TAG);

		// Enable callbacks from MediaButtons and TransportControls.
		mMediaSession.setFlags(
				MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
						MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

		// Do not let MediaButtons restart the player when the app is not visible.
		mMediaSession.setMediaButtonReceiver(null);

		// Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
		mStateBuilder = new PlaybackStateCompat.Builder()
				.setActions(
						PlaybackStateCompat.ACTION_PLAY |
								PlaybackStateCompat.ACTION_PAUSE |
								PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
								PlaybackStateCompat.ACTION_PLAY_PAUSE);

		mMediaSession.setPlaybackState(mStateBuilder.build());


		// MySessionCallback has methods that handle callbacks from a media controller.
		mMediaSession.setCallback(new MySessionCallback());

		// Start the Media Session since the activity is active.
		mMediaSession.setActive(true);

	}

	/**
	 * Initialize ExoPlayer.
	 * @param mediaUri The URI of the sample to play.
	 */
	private void initializePlayer(Uri mediaUri) {
		if (mExoPlayer == null) {
			// Create an instance of the ExoPlayer.
			TrackSelector trackSelector = new DefaultTrackSelector();
			LoadControl loadControl = new DefaultLoadControl();
			mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
			mPlayerView.setPlayer(mExoPlayer);

			// Set the ExoPlayer.EventListener to this activity.
			mExoPlayer.addListener(this);

			// Prepare the MediaSource.
			String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
			MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
					getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
			mExoPlayer.prepare(mediaSource);
//			mExoPlayer.setPlayWhenReady(true);
		}
	}


	/**
	 * Release ExoPlayer.
	 */
	private void releasePlayer() {
		mExoPlayer.stop();
		mExoPlayer.release();
		mExoPlayer = null;
	}


	/**
	 * Release the player when the activity is destroyed.
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		releasePlayer();
		mMediaSession.setActive(false);
	}

	// ExoPlayer Event Listeners

	@Override
	public void onTimelineChanged(Timeline timeline, Object manifest) {
	}

	@Override
	public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
	}

	@Override
	public void onLoadingChanged(boolean isLoading) {
	}

	/**
	 * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
	 * PlayBackState to keep in sync, and post the media notification.
	 * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
	 * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
	 *                      STATE_BUFFERING, or STATE_ENDED.
	 */
	@Override
	public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
		if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
			mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
					mExoPlayer.getCurrentPosition(), 1f);
		} else if((playbackState == ExoPlayer.STATE_READY)){
			mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
					mExoPlayer.getCurrentPosition(), 1f);
		}
		mMediaSession.setPlaybackState(mStateBuilder.build());
	}

	@Override
	public void onPlayerError(ExoPlaybackException error) {
	}

	@Override
	public void onPositionDiscontinuity() {
	}

	/**
	 * Media Session Callbacks, where all external clients control the player.
	 */
	private class MySessionCallback extends MediaSessionCompat.Callback {
		@Override
		public void onPlay() {
			mExoPlayer.setPlayWhenReady(true);
		}

		@Override
		public void onPause() {
			mExoPlayer.setPlayWhenReady(false);
		}

		@Override
		public void onSkipToPrevious() {
			mExoPlayer.seekTo(0);
		}
	}

	/**
	 * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
	 */
	public static class MediaReceiver extends BroadcastReceiver {

		public MediaReceiver() {
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			MediaButtonReceiver.handleIntent(mMediaSession, intent);
		}
	}
}
