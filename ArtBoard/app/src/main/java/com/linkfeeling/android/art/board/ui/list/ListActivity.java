package com.linkfeeling.android.art.board.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.link.feeling.framework.base.EmptyMvpPresenter;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.ui.rank.RankActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class ListActivity extends FrameworkBaseActivity {

    @BindView(R.id.item_link)
    ImageView mIvLink;
    @BindView(R.id.item_xixi)
    ImageView mIvXiXi;
    @BindView(R.id.item_ruili)
    ImageView mIvRuiLi;
    @BindView(R.id.item_gaote)
    ImageView mIvGaoTe;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mIvLink.requestFocus();
    }

    @NotNull
    @Override
    public MvpPresenter createPresenter() {
        return new EmptyMvpPresenter();
    }


    @OnClick({R.id.item_link, R.id.item_xixi, R.id.item_ruili, R.id.item_gaote, R.id.item_yirendao})
    public void onViewClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.item_link:
                RankActivity.launch(this, 0);
                return;
            case R.id.item_xixi:
                RankActivity.launch(this, 1);
                return;
            case R.id.item_ruili:
                RankActivity.launch(this, 2);
                return;
            case R.id.item_gaote:
                RankActivity.launch(this, 3);
                return;
            case R.id.item_yirendao:
                RankActivity.launch(this, 4);
                return;
        }
    }
}
