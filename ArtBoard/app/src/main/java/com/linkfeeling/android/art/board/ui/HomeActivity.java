package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.linkfeeling.android.art.board.R;
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


    @BindView(R.id.current_style_group)
    Group mCurrentGroup;

    @BindView(R.id.home_root)
    ConstraintLayout mClRoot;

    private HomeAdapter mAdapter;
    private GridLayoutManager mGridManager;
    private String mCurrentCount = "";

    private int mTotalPage;
    private int mCurrentPage = 1;
    private String mTempCount = "0";
    private CountDownTimer mTimer;


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

        List<HomePartModule> mPartModules = new ArrayList<>();
        mPartModules.add(new HomePartModule("激活\n放松", "01-39%"));
        mPartModules.add(new HomePartModule("动态\n热身", "40-55%"));
        mPartModules.add(new HomePartModule("脂肪\n燃烧", "56-69%"));

        mPartModules.add(new HomePartModule("心肺\n强化", "70-79%"));
        mPartModules.add(new HomePartModule("无氧\n挑战", "80-89%"));
        mPartModules.add(new HomePartModule("极限\n锻炼", "90-99%"));

        PartAdapter mPartAdapter = new PartAdapter(this);
        mRvPart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvPart.setAdapter(mPartAdapter);
        mPartAdapter.setPartModules(mPartModules);
        mTsCount.setFactory(this);
    }

    @NonNull
    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loading(List<HomeRemoteModule> modules) {
        if (mCurrentGroup.getVisibility() != View.VISIBLE) {
            mClRoot.setBackgroundResource(R.drawable.icon_bg);
            mCurrentGroup.setVisibility(View.VISIBLE);

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
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
