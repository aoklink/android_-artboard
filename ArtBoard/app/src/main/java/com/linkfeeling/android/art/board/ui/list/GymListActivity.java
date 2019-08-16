package com.linkfeeling.android.art.board.ui.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.link.feeling.framework.base.EmptyMvpPresenter;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.list.GymListModule;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class GymListActivity extends FrameworkBaseActivity {

    @BindView(R.id.list_recycler_view)
    RecyclerView mRvList;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gym_list;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        // 状态栏
        mRvList.setPadding(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
        DisplayUtils.statusBarDarkFont(this);
        List<GymListModule> mModule = new ArrayList<>();
        mModule.add(new GymListModule("LINK_OFFICE(东方通信)", "link_office", R.drawable.icon_gym_logo));
        mModule.add(new GymListModule("FITTING_GYM(西溪银泰)", "fitting_gym_xixi", R.drawable.icon_gym_logo));
        GymListAdapter mGymAdapter = new GymListAdapter(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvList.setAdapter(mGymAdapter);
        mGymAdapter.setModules(mModule);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {//未开启存储权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0x201);
            }
        }
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new EmptyMvpPresenter();
    }
}
