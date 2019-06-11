package com.linkfeeling.android.art.board.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.data.bean.HomePartModule;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.OffsetModule;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class HomeActivity extends FrameworkBaseActivity<HomeContract.View, HomeContract.Presenter> implements HomeContract.View, ViewSwitcher.ViewFactory, Animation.AnimationListener {

    public static List<OffsetModule> sOffsetCache = new ArrayList<>();

    @BindView(R.id.user_board_recycler_view)
    RecyclerView mRvBoard;

    @BindView(R.id.user_sport_summary_recycler_view)
    RecyclerView mRvPart;

    @BindView(R.id.people_count_text_switcher)
    TextSwitcher mTsCount;

    @BindView(R.id.week_calorie_container)
    LinearLayout mCalorieContainer;

    @BindView(R.id.rank_rv)
    RecyclerView mRvRank;

    @BindView(R.id.current_style_group)
    Group mCurrentGroup;
    @BindView(R.id.rank_style_group)
    Group mRankGroup;

    @BindView(R.id.home_root)
    ConstraintLayout mClRoot;

    private HomeAdapter mAdapter;
    private PartAdapter mPartAdapter;
    private GridLayoutManager mGridManager;
    private List<HomePartModule> mPartModules;
    private String mCurrentCount = "";

    private int mTotalPage;
    private int mCurrentPage = 1;
    private String mTempCount = "0";
    private CountDownTimer mTimer;

    private RankAdapter mRankAdapter;
    private String mCacheCalorie = "";
    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;

    private LayoutAnimationController mControllerIn;
    private LayoutAnimationController mControllerOut;
    private List<HomeRemoteModule> mRankModules;
    private int mRankPage = 0;
    private int mCurrentRankPage = 0;
    private CountDownTimer mRankTimer;
    private long mTempDelay = 1350;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_art_board;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        getPresenter().request();
        getPresenter().interval();
        initTimerTask();
    }

    private void initTimerTask() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new CountDownTimer(Long.MAX_VALUE, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mTotalPage <= 1) {
                    smoothScrollToPosition(0);
                    return;
                }
                if (mCurrentPage < mTotalPage) {
                    smoothScrollToPosition(mCurrentPage * 8);
                    mCurrentPage++;
                } else {
                    smoothScrollToPosition(0);
                    mCurrentPage = 1;
                }
            }

            @Override
            public void onFinish() {
                initTimerTask();
            }
        };
        mTimer.start();
    }

    private void initRecyclerView() {
        mAdapter = new HomeAdapter(this);
        mAdapter.setRecyclerViewHeight(DisplayUtils.getScreenHeight() - DisplayUtils.dp2px(100));
        mAdapter.setRecyclerViewWidth((float) (DisplayUtils.getScreenWidth() * 0.8 - DisplayUtils.dp2px(80)));
        mGridManager = new GridLayoutManager(this, 2);
        mRvBoard.setLayoutManager(mGridManager);
        mRvBoard.setAdapter(mAdapter);

        mPartModules = new ArrayList<>();
        mPartModules.add(new HomePartModule("激活\n放松", "01-39%"));
        mPartModules.add(new HomePartModule("动态\n热身", "40-55%"));
        mPartModules.add(new HomePartModule("脂肪\n燃烧", "56-69%"));

        mPartModules.add(new HomePartModule("糖分\n消耗", "70-79%"));
        mPartModules.add(new HomePartModule("心肺\n训练", "80-89%"));
        mPartModules.add(new HomePartModule("极限\n锻炼", "90-99%"));

        mPartAdapter = new PartAdapter(this);
        mRvPart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvPart.setAdapter(mPartAdapter);
        mPartAdapter.setPartModules(mPartModules);
        mTsCount.setFactory(this);

        mRankAdapter = new RankAdapter(this);
        mRvRank.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvRank.setAdapter(mRankAdapter);
        mControllerIn = AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_in_holder);
        mControllerOut = AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_out_holder);
        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        mFadeOutAnimation.setAnimationListener(this);
        mRankModules = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loading(List<HomeRemoteModule> modules) {
        if (mCurrentGroup.getVisibility() == View.GONE) {
            mClRoot.setBackgroundResource(R.drawable.icon_bg);
            mCurrentGroup.setVisibility(View.VISIBLE);
            mCurrentGroup.startAnimation(mFadeInAnimation);
        }
        if (mRankGroup.getVisibility() == View.VISIBLE) {
            mRankGroup.startAnimation(mFadeOutAnimation);
        }
        switch (CollectionsUtil.size(modules)) {
            case 1:
                mGridManager.setSpanCount(1);
                break;
            case 2:
            case 3:
            case 4:
                mGridManager.setSpanCount(2);
                break;
            case 5:
            case 6:
            default:
                mGridManager.setSpanCount(4);
                break;
        }
        mAdapter.setModules(modules);


        mTempCount = String.valueOf(CollectionsUtil.size(modules));

        if (!mCurrentCount.equals(mTempCount)) {
            mCurrentCount = mTempCount;
            mTsCount.setText(mCurrentCount);
        }

        mTotalPage = (CollectionsUtil.size(modules) / 8) + (CollectionsUtil.size(modules) % 8 > 0 ? 1 : 0);
    }

    @Override
    public void loadingRank(List<HomeRemoteModule> modules, String total_calorie) {
        if (mRankGroup.getVisibility() == View.GONE) {
            mClRoot.setBackgroundResource(R.drawable.rank_bg);
            mRankGroup.setVisibility(View.VISIBLE);
            mRankGroup.startAnimation(mFadeInAnimation);
        }
        if (mCurrentGroup.getVisibility() == View.VISIBLE) {
            mCurrentGroup.startAnimation(mFadeOutAnimation);
        }

        mRankModules.clear();
        mRankModules.addAll(modules);
        mRankPage = CollectionsUtil.size(mRankModules) / 5 + (CollectionsUtil.size(mRankModules) % 5 > 0 ? 1 : 0);

        if (!mCacheCalorie.equals(total_calorie)) {
            mCacheCalorie = total_calorie;
            mCalorieContainer.removeAllViews();
            for (int i = 0; i < total_calorie.length(); i++) {
                ImageView iv = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_number, mCalorieContainer, false);
                iv.setBackgroundResource(ImageConstants.matchNumberImage(String.valueOf(total_calorie.charAt(i))));
                mCalorieContainer.addView(iv);
            }
            mCalorieContainer.startAnimation(mFadeInAnimation);
        }
        mRvRank.setLayoutAnimation(mControllerIn);
        mCurrentRankPage = 0;
        if (mRankPage > 1) {
            mRankAdapter.setModules(mRankModules.subList(0, 5));
            scheduledRankAnimation();
        } else {
            mRankAdapter.setModules(mRankModules);
            if (mRankTimer != null) {
                mRankTimer.cancel();
            }
        }
    }


    private boolean mIsRankTimerStart = true;

    private void scheduledRankAnimation() {
        if (mRankTimer != null) {
            mRankTimer.cancel();
        }
        mIsRankTimerStart = true;
        mRankTimer = new CountDownTimer(Long.MAX_VALUE, 6666) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mIsRankTimerStart) {
                    mIsRankTimerStart = false;
                    return;
                }
                mRvRank.setLayoutAnimation(mControllerOut);
                mRankAdapter.setModules(mRankModules.subList(mCurrentRankPage * 5, mRankPage == (mCurrentRankPage + 1) ? CollectionsUtil.size(mRankModules) : (mCurrentRankPage + 1) * 5));
                mCurrentRankPage++;
                if (mCurrentRankPage >= mRankPage) {
                    mCurrentRankPage = 0;
                }
                mTempDelay = 270 * mRankAdapter.getItemCount();
                postDelay(() -> {
                    mRvRank.setLayoutAnimation(mControllerIn);
                    mRankAdapter.setCurrentPage(mCurrentRankPage);
                    mRankAdapter.setModules(mRankModules.subList(mCurrentRankPage * 5, mRankPage == (mCurrentRankPage + 1) ? CollectionsUtil.size(mRankModules) : (mCurrentRankPage + 1) * 5));
                }, mTempDelay);
            }

            @Override
            public void onFinish() {
                scheduledRankAnimation();
            }
        };
        mRankTimer.start();
    }

    public View makeView() {
        return View.inflate(mTsCount.getContext(), R.layout.people_count_text_view, null);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            if (mTimer != null) {
                mTimer.cancel();
            }
            if (mRankTimer != null) {
                mRankTimer.cancel();
            }
        }
    }

    private void smoothScrollToPosition(int position) {
        if (mRvBoard != null) {
            mRvBoard.smoothScrollToPosition(position);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == mFadeOutAnimation) {
            if (ViewUtils.isVisible(mCurrentGroup)) {
                ViewUtils.setGone(mRankGroup);
                L.e("current");
            }
            if (ViewUtils.isVisible(mRankGroup)) {
                ViewUtils.setGone(mCurrentGroup);
                L.e("rank");
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
