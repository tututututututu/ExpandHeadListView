package com.tutu.gogogo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ShadowListView extends ListView implements OnScrollListener {
	private int mSelfHeight;
	private boolean isShow = true;
	private ScrollAnimation mAnimation;
	private boolean isAnimationing = false;
	private static final int ANIMATION_DURATION = 500;
	private View mTopView;
	private int height = 0;
	private MyScrollCallBack mCallBack;

	public ShadowListView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setOnScrollListener(this);
	}

	public ShadowListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOnScrollListener(this);
		init();
	}

	public ShadowListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(this);
		init();
	}

	public ShadowListView(Context context) {
		super(context);
		setOnScrollListener(this);
		init();
	}

	public int getOrignHeight() {
		return mSelfHeight;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mSelfHeight = getHeight();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if (mCallBack == null) {
			return;
		}

		if (firstVisibleItem == 0 && !isShow) {
			mCallBack.onTop();

			isShow = !isShow;
		}

		if (firstVisibleItem > 0 && isShow) {
			mCallBack.onFirstInvisiable();

			isShow = !isShow;
		}

		if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
			mCallBack.onButtom();
		}
		mCallBack
				.onScroll(view, totalItemCount, totalItemCount, totalItemCount);
	}

	private void init() {
		mCallBack = new MyScrollCallBack();

	}

	public boolean getIsShow() {
		return isShow;
	}

	public void setTopView(View view) {
		mTopView = view;

	}

	public void setTopViewHeight(int height) {
		this.height = height;
	}

	private class MyScrollCallBack implements ScrollCallBack {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			System.out.println("xxxxx onScroll()");
			if (mAnimation == null) {
				return;
			}

			mAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					isAnimationing = true;
					ShadowListView.this.setEnabled(false);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					isAnimationing = false;
					ShadowListView.this.setEnabled(true);
					if (ShadowListView.this.getIsShow()) {
						ShadowListView.this.smoothScrollToPosition(0);
					}
				}
			});
		}

		@Override
		public void onButtom() {

		}

		@Override
		public void onTop() {
			// ÏÔÊ¾¶¥²¿
			if (isAnimationing)
				return;
			mAnimation = new ScrollAnimation(mTopView, 0, height,
					ANIMATION_DURATION);
			mAnimation.setFillAfter(true);
			mTopView.startAnimation(mAnimation);
			System.out.println("xxxxx onTop()");
		}

		@Override
		public void onFirstInvisiable() {
			// Òþ²Ø¶¥²¿
			if (isAnimationing)
				return;
			mAnimation = new ScrollAnimation(mTopView, height, 0,
					ANIMATION_DURATION);
			mAnimation.setFillAfter(true);
			mTopView.startAnimation(mAnimation);
			System.out.println("xxxxx onFirstInvisiable()");
		}

	}

	private class ScrollAnimation extends Animation {
		private View mAnimationView;
		private int mStartHeight;
		private int mEndHeight;

		public ScrollAnimation(View view, int startHeight, int endHeight,
				int duration) {
			this.mAnimationView = view;
			this.mStartHeight = mAnimationView.getLayoutParams().height;
			this.mEndHeight = endHeight;
			setDuration(duration);
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			int newHeight = (int) ((this.mEndHeight - this.mStartHeight)
					* interpolatedTime + mStartHeight);
			mAnimationView.getLayoutParams().height = newHeight;
			mAnimationView.requestLayout();
			System.out.println("xxxxx applyTransformation() newHeight="
					+ newHeight);
		}
	}

	public interface ScrollCallBack {
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount);

		public void onButtom();

		public void onTop();

		public void onFirstInvisiable();
	}
}
