package com.linkfeeling.android.art.board.ui.rank;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.base.FrameworkBaseFragment;
import com.link.feeling.framework.bean.MqttRequest;
import com.link.feeling.framework.component.mqtt.MqttManager;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.data.StringUtils;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteModule;
import com.linkfeeling.android.art.board.data.bean.rank.RankUpdateModule;
import com.linkfeeling.android.art.board.ui.HomeActivity;

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
    @BindView(R.id.rk_left)
    ImageView mIvLeft;
    @BindView(R.id.rk_right)
    ImageView mIvRight;

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

        mMqttManager = MqttManager.newInstance();
        mMqttManager.connect(this);

        postDelay(() -> notifyRankChanged(null), 500);
        getPresenter().interval();
    }

    @NotNull
    @Override
    public RankContract.Presenter createPresenter() {
        return new RankPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIvReal.requestFocus();
    }

    @Override
    public void live() {
        mMqttManager.publishMessage(JSON.toJSONString(new MqttRequest(-1)));
    }

    @OnClick({R.id.rk_real, R.id.rk_left, R.id.rk_right})
    public void onViewClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.rk_real:
                HomeActivity.launch(this);
                break;
            case R.id.rk_left:
                mRankVp.setCurrentItem(mCurrentPageIndex > 0 ? mCurrentPageIndex - 1 : 0, true);
                mCurrentPageIndex = mCurrentPageIndex > 0 ? mCurrentPageIndex-- : 0;
                break;
            case R.id.rk_right:
                mRankVp.setCurrentItem(mCurrentPageIndex < 2 ? mCurrentPageIndex + 1 : 2, true);
                mCurrentPageIndex = mCurrentPageIndex < 2 ? mCurrentPageIndex++ : 2;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPageIndex = position;
        mIvLeft.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        mIvRight.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        switch (position) {
            case 0:
                mIvReal.setNextFocusDownId(R.id.rk_right);
                break;
            case 1:
                mIvReal.setNextFocusDownId(R.id.rk_right);
                break;
            case 2:
                mIvReal.setNextFocusDownId(R.id.rk_left);
                break;
        }
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
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String body = new String(message.getPayload());
        JSONObject object = JSONObject.parseObject(body);
        int type = object.getIntValue("type");
        switch (type) {
            case 204:
                notifyUpdateChanged(JSON.parseObject(body, RankUpdateModule.class));
                break;
            case 210:
                notifyRankChanged(JSON.parseObject(body, RankRemoteModule.class));
                break;
        }
        L.e("HomeActivity", "messageArrived:" + body);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void notifyRankChanged(RankRemoteModule rankRemoteModule) {
        if (rankRemoteModule == null) {
            rankRemoteModule = new RankRemoteModule();
        }
        mRankFm1.initRank1(rankRemoteModule.getCalorie(), rankRemoteModule.getDay(), rankRemoteModule.getTime());
        mRankFm2.initRank2(rankRemoteModule.getPbj_pace(), rankRemoteModule.getDc_pace(), rankRemoteModule.getTyj_pace());
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
        if (StringUtils.isNotEmpty(update.getTime())) {
            mRankFm1.updateRank3(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getTime()));
        }


        if (StringUtils.isNotEmpty(update.getPbj_pace())) {
            mRankFm2.updateRank4(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getPbj_pace()));
        }
        if (StringUtils.isNotEmpty(update.getDc_pace())) {
            mRankFm2.updateRank5(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getDc_pace()));
        }
        if (StringUtils.isNotEmpty(update.getTyj_pace())) {
            mRankFm2.updateRank6(new RankRemoteItem(update.getUser_name(), update.getHead_icon(), update.getUid(), update.getTyj_pace()));
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

}
