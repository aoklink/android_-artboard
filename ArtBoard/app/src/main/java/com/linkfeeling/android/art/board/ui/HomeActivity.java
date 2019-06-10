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
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.data.bean.HomePartModule;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.OffsetModule;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class HomeActivity extends FrameworkBaseActivity<HomeContract.View, HomeContract.Presenter> implements HomeContract.View, ViewSwitcher.ViewFactory {

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
//    @BindView(R.id.rank_item1)
//    View mRankItem1;
//    @BindView(R.id.rank_item2)
//    View mRankItem2;
//    @BindView(R.id.rank_item3)
//    View mRankItem3;
//    @BindView(R.id.rank_item4)
//    View mRankItem4;
//    @BindView(R.id.rank_item5)
//    View mRankItem5;

    private HomeAdapter mAdapter;
    private PartAdapter mPartAdapter;
    private GridLayoutManager mGridManager;
    private List<HomePartModule> mPartModules;
    private String mCurrentCount = "";

    private int mTotalPage;
    private int mCurrentPage = 1;
    private String mTempCount = "0";
    private CountDownTimer mTimer;

    private List<View> mRankItems;

    private Animation animator;

    private String temp = "987654";

    private RankAdapter rankAdapter;
    private List<HomeRemoteModule> list = new ArrayList<>();

    private boolean isChange = true;

    private LayoutAnimationController controller1;
    private LayoutAnimationController controller2;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_art_board;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
//        getPresenter().request();
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

        list.add(new HomeRemoteModule());
        list.add(new HomeRemoteModule());
        list.add(new HomeRemoteModule());
        list.add(new HomeRemoteModule());
        list.add(new HomeRemoteModule());
//        mRankItems.add(mRankItem1);
//        mRankItems.add(mRankItem2);
//        mRankItems.add(mRankItem3);
//        mRankItems.add(mRankItem4);
//        mRankItems.add(mRankItem5);
//        animator = AnimationUtils.loadAnimation(this ,R.anim.item_rank_out);
        rankAdapter = new RankAdapter(this);
        mRvRank.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvRank.setAdapter(rankAdapter);
        rankAdapter.setModules(list);
        controller1 =  AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_in_holder);
        controller2 =  AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_out_holder);
    }

    @NonNull
    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loading(List<HomeRemoteModule> modules) {
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

        mCalorieContainer.removeAllViews();

        for (int i = 0; i < temp.length(); i++) {
            ImageView iv = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_number, mCalorieContainer, false);
            iv.setBackgroundResource(ImageConstants.matchNumberImage(String.valueOf(temp.charAt(i))));
            mCalorieContainer.addView(iv);
        }

        isChange = true;

        for (HomeRemoteModule module : list) {
            module.setDeleteStyle(false);
        }
        mRvRank.setLayoutAnimation(controller2);
        rankAdapter.setModules(list);

        isChange = false;

        postDelay(new Runnable() {
            @Override
            public void run() {
                for (HomeRemoteModule module : list) {
                    module.setDeleteStyle(true);
                }
                mRvRank.setLayoutAnimation(controller1);
                rankAdapter.setModules(list);
            }
        },1200);

    }

    @Override
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
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDestroy();
    }

    private void smoothScrollToPosition(int position) {
        if (mRvBoard != null) {
            mRvBoard.smoothScrollToPosition(position);
        }
    }
}
