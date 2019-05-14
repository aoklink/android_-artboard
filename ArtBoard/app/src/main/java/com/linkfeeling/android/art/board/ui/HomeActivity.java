package com.linkfeeling.android.art.board.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.HomePartModule;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.widget.RecyclerViewVerticalItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class HomeActivity extends FrameworkBaseActivity<HomeContract.View, HomeContract.Presenter> implements HomeContract.View, ViewSwitcher.ViewFactory {

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

    private String mCurrentCount;

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

        mPartModules.add(new HomePartModule("有氧\n耐力", "70-79%"));
        mPartModules.add(new HomePartModule("无氧\n耐力", "80-89%"));
        mPartModules.add(new HomePartModule("峰值\n锻炼", "90-99%"));

        mPartAdapter = new PartAdapter(this);
        mRvPart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvPart.addItemDecoration(new RecyclerViewVerticalItemDecoration(30));
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

        if (CollectionsUtil.isNotEmpty(modules) && !String.valueOf(CollectionsUtil.size(modules)).equals(mCurrentCount)) {
            mCurrentCount = String.valueOf(CollectionsUtil.size(modules));
            mTsCount.setText(String.valueOf(CollectionsUtil.size(modules)));
        }
    }

    @Override
    public View makeView() {
        return View.inflate(mTsCount.getContext(), R.layout.people_count_text_view, null);
    }
}
