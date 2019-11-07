package com.linkfeeling.android.art.board.ui.rank;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.bean.MqttRequest;
import com.link.feeling.framework.component.mqtt.MqttManager;
import com.link.feeling.framework.utils.ThreadUtils;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.rank.RankModule;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteModule;
import com.linkfeeling.android.art.board.data.bean.rank.RankUpdateModule;
import com.linkfeeling.android.art.board.ui.HomeActivity;
import com.linkfeeling.android.art.board.utils.DateUtils;
import com.linkfeeling.android.art.board.widget.ViewPagerScroller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class RankActivity extends FrameworkBaseActivity<RankContract.View, RankContract.Presenter> implements RankContract.View, ViewPager.OnPageChangeListener, MqttCallbackExtended {

    @BindView(R.id.rk_vp)
    ViewPager mRankVp;

    @BindView(R.id.rk_real)
    ImageView mIvReal;
    @BindView(R.id.rank_timer)
    TextView mTvTimer;
    @BindView(R.id.rk_holder)
    ImageView mIvHolder;

    private int mCurrentPageIndex = 0;

    private List<RankFragment> mFragments;

    private MqttManager mMqttManager;

    private List<Integer> mIds;

    private int mFmSize;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_rank;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mIds = new ArrayList<>();
        mMqttManager = MqttManager.newInstance();
        mMqttManager.connect(this, 101);
        getPresenter().count();
        getPresenter().countPage();
        mIvHolder.requestFocus();
    }

    @NotNull
    @Override
    public RankContract.Presenter createPresenter() {
        return new RankPresenter();
    }

    @Override
    public void live() {
        mMqttManager.publishMessage(JSON.toJSONString(new MqttRequest(-1)));
    }

    @Override
    public void timer() {
        if (!DateUtils.formatHour(System.currentTimeMillis()).equals(mTvTimer.getText().toString())) {
            mTvTimer.setText(DateUtils.formatHour(System.currentTimeMillis()));
        }
    }

    @Override
    public void scrollPage() {
        if (mCurrentPageIndex < (mFmSize - 1)) {
            mCurrentPageIndex++;
        } else {
            mCurrentPageIndex = 0;
        }
        mRankVp.setCurrentItem(mCurrentPageIndex, true);
    }

    @Override
    public void scrollRank() {
        if (CollectionsUtil.size(mFragments) > mCurrentPageIndex) {
            mFragments.get(mCurrentPageIndex).switchPage();
        }
    }

    @OnClick({R.id.rk_real})
    public void onViewClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        if (v.getId() == R.id.rk_real) {
            HomeActivity.launch(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPageIndex = position;
        mIvReal.requestFocus();
        getPresenter().countRank();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (reconnect) {
            mMqttManager.subscribeToTopic();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String body = new String(message.getPayload());
        JSONObject object = JSONObject.parseObject(body);
        int type = object.getIntValue("type");
        switch (type) {
            case 203:
                L.e("RankActivity203", "messageArrived:" + body);
                ThreadUtils.execute(() -> notifyUpdateChanged(JSON.parseObject(body, RankUpdateModule.class)));
                break;
            case 210:
                L.e("RankActivity210", "messageArrived:" + body);
                notifyRankChanged(JSON.parseObject(body, RankModule.class));
                getPresenter().interval();
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


    private void notifyRankChanged(RankModule remoteModule) {
        if (remoteModule == null || CollectionsUtil.isEmpty(remoteModule.getData())) {
            return;
        }
        mFragments.clear();
        mIds.clear();
        List<RankRemoteModule> modules = remoteModule.getData();
        for (RankRemoteModule module : modules) {
            mIds.add(module.getId());
        }
        int size = CollectionsUtil.size(modules);
        mFmSize = (size % 3) == 0 ? (size / 3) : (size / 3 + 1);
        for (int i = 0; i < mFmSize; i++) {
            mFragments.add(RankFragment.newInstance(modules.get(i * 3), modules.get(i * 3 + 1), modules.get(i * 3 + 2)));
        }
        mRankVp.setOffscreenPageLimit(mFmSize);
        RankPagerAdapter mPagerAdapter = new RankPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragments);
        mRankVp.setAdapter(mPagerAdapter);
        mRankVp.addOnPageChangeListener(this);
        ViewPagerScroller pagerScroller = new ViewPagerScroller(this);
        pagerScroller.setScrollDuration(1250);
        pagerScroller.initViewPagerScroll(mRankVp);

        getPresenter().countRank();
    }


    private void notifyUpdateChanged(RankUpdateModule module) {
        if (module == null || module.getData() == null || module.getData().isEmpty()) {
            return;
        }
        for (Integer key : module.getData().keySet()) {
            if (mIds.contains(key) && module.getData().get(key) != null) {
                int position = mIds.indexOf(key);
                int index = position / 3;
                mFragments.get(index).updateItem(new RankRemoteItem(module.getUser_name(), module.getHead_icon(), module.getUid(), module.getData().get(key).getValue(), module.getData().get(key).getIndex()), position - index * 3);
            }
        }
    }

}
