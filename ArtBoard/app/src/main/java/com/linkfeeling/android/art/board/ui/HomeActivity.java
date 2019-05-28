package com.linkfeeling.android.art.board.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DeviceUtils;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.link.feeling.framework.utils.data.L;
import com.linkfeeling.android.art.board.R;
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

    private HomeAdapter mAdapter;
    private PartAdapter mPartAdapter;
    private GridLayoutManager mGridManager;
    private List<HomePartModule> mPartModules;
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
//        BoardUIConfig.load(getApplicationContext());
        initRecyclerView();
        getPresenter().request();
        getPresenter().interval();
        initTimerTask();
        L.e(DeviceUtils.getMac());
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
//        mRvPart.addItemDecoration(new RecyclerViewVerticalItemDecoration(30));
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
        L.e("loading", mTotalPage + "");
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
