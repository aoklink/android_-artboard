package com.linkfeeling.android.art.board.ui.rank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.base.FrameworkBaseFragment;
import com.link.feeling.framework.bean.MqttRequest;
import com.link.feeling.framework.component.mqtt.MqttManager;
import com.link.feeling.framework.utils.ThreadUtils;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.data.StringUtils;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
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

    private List<FrameworkBaseFragment> mFragments;
    private RankFragment mRankFm1;
    private RankFragment mRankFm2;
    private RankFragment mRankFm3;
    private RankPagerAdapter mPagerAdapter;

    private MqttManager mMqttManager;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_rank;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mRankFm1 = RankFragment.newInstance(0);
        mRankFm2 = RankFragment.newInstance(1);
        mRankFm3 = RankFragment.newInstance(2);

        mFragments.add(mRankFm1);
        mFragments.add(mRankFm2);
        mFragments.add(mRankFm3);
        mPagerAdapter = new RankPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragments);

        mRankVp.setAdapter(mPagerAdapter);
        mRankVp.setOffscreenPageLimit(3);
        mRankVp.addOnPageChangeListener(this);

        ViewPagerScroller pagerScroller = new ViewPagerScroller(this);
        pagerScroller.setScrollDuration(1250);
        pagerScroller.initViewPagerScroll(mRankVp);

        mMqttManager = MqttManager.newInstance();
        mMqttManager.connect(this, 102);
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
    protected void onResume() {
        super.onResume();
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
        if (mCurrentPageIndex < 2) {
            mCurrentPageIndex++;
        } else {
            mCurrentPageIndex = 0;
        }
        mRankVp.setCurrentItem(mCurrentPageIndex, true);
    }

    @Override
    public void scrollRank() {
        switch (mCurrentPageIndex) {
            case 0:
                mRankFm1.notifyRank1();
                break;
            case 1:
                mRankFm2.notifyRank2();
                break;
            case 2:
                mRankFm3.notifyRank3();
                break;
        }
    }

    @OnClick({R.id.rk_real})
    public void onViewClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.rk_real:
                HomeActivity.launch(this);
                break;
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
                notifyRankChanged(JSON.parseObject(body, RankRemoteModule.class));
                getPresenter().interval();
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void notifyRankChanged(RankRemoteModule rankRemoteModule) {
        if (rankRemoteModule == null) {
            rankRemoteModule = new RankRemoteModule();
        }
        mRankFm1.initRank1(rankRemoteModule.getCalorie(), rankRemoteModule.getDay(), rankRemoteModule.getDuration());
        getPresenter().countRank();
        mRankFm2.initRank2(rankRemoteModule.getPbj_distance(), rankRemoteModule.getDc_distance(), rankRemoteModule.getTyj_distance());
        mRankFm3.initRank3(rankRemoteModule.getTotal_capacity(), rankRemoteModule.getSingle_max_capacity(), rankRemoteModule.getHdj_max_weight());
    }

    private void notifyUpdateChanged(RankUpdateModule update) {
        if (update == null || StringUtils.isEmpty(update.getUid()) || StringUtils.isEmpty(update.getHead_icon()) || StringUtils.isEmpty(update.getUser_name())) {
            return;
        }

        if (StringUtils.isNotEmpty(update.getCalorie())) {
            mRankFm1.updateRank1(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getCalorie()));
        }
        if (StringUtils.isNotEmpty(update.getDay())) {
            mRankFm1.updateRank2(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getDay()));
        }
        if (StringUtils.isNotEmpty(update.getDuration())) {
            mRankFm1.updateRank3(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getDuration()));
        }


        if (StringUtils.isNotEmpty(update.getPbj_distance())) {
            mRankFm2.updateRank4(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getPbj_distance()));
        }
        if (StringUtils.isNotEmpty(update.getDc_distance())) {
            mRankFm2.updateRank5(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getDc_distance()));
        }
        if (StringUtils.isNotEmpty(update.getTyj_distance())) {
            mRankFm2.updateRank6(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getTyj_distance()));
        }


        if (StringUtils.isNotEmpty(update.getTotal_capacity())) {
            mRankFm3.updateRank7(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getTotal_capacity()));
        }
        if (StringUtils.isNotEmpty(update.getSingle_max_capacity())) {
            mRankFm3.updateRank8(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getSingle_max_capacity()));
        }
        if (StringUtils.isNotEmpty(update.getHdj_max_weight())) {
            mRankFm3.updateRank9(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getHdj_max_weight()));
        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, RankActivity.class);
        context.startActivity(intent);
    }
}
