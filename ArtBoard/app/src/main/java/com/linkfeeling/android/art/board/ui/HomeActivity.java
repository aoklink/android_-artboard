package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.link.feeling.framework.base.FrameworkBaseActivity;
import com.link.feeling.framework.bean.MqttRequest;
import com.link.feeling.framework.component.mqtt.MqttManager;
import com.link.feeling.framework.utils.ThreadUtils;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.HeartRemoteModule;
import com.linkfeeling.android.art.board.data.bean.HomeModule;
import com.linkfeeling.android.art.board.data.bean.HomePartModule;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.OffsetModule;
import com.linkfeeling.android.art.board.data.bean.RemoveRemoteModule;
import com.linkfeeling.android.art.board.utils.DateUtils;
import com.linkfeeling.android.art.board.widget.WrapGridLayoutManager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends FrameworkBaseActivity<HomeContract.View, HomeContract.Presenter> implements HomeContract.View, ViewSwitcher.ViewFactory, MqttCallbackExtended {

    public static List<OffsetModule> sOffsetCache = new ArrayList<>();

    @BindView(R.id.user_board_recycler_view)
    RecyclerView mRvBoard;

    @BindView(R.id.user_sport_summary_recycler_view)
    RecyclerView mRvPart;

    @BindView(R.id.people_count_text_switcher)
    TextSwitcher mTsCount;

    @BindView(R.id.timer)
    TextView mTvTimer;

    @BindView(R.id.rk_real)
    ImageView mIvReal;

    private HomeAdapter mAdapter;
    private GridLayoutManager mGridManager;
    private String mCurrentCount = "";
    private int mTotalPage;
    private int mCurrentPage = 0;

    private CountDownTimer mTimer;
    private MqttManager mMqttManager;
    private List<HomeRemoteModule> mModules;
    private List<HomeRemoteModule> mTempModules;
    private LayoutAnimationController mControllerIn;
    private LayoutAnimationController mControllerOut;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_art_board;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mControllerIn = AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_in_holder);
        mControllerOut = AnimationUtils.loadLayoutAnimation(this, R.anim.item_rank_out_holder);
        initRecyclerView();
        mModules = new ArrayList<>();
        mTempModules = new ArrayList<>();
        mMqttManager = MqttManager.newInstance();
        mMqttManager.connect(this, 100);
        getPresenter().interval();
        getPresenter().count();
        mTvTimer.requestFocus();
    }

    private void initTimerTask() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new CountDownTimer(Long.MAX_VALUE, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mTotalPage < 1) {
                    mTimer.cancel();
                    mTimer = null;
                    return;
                }
                mRvBoard.setLayoutAnimation(mControllerOut);
                mAdapter.setModules(mTempModules);
                mCurrentPage++;
                if (mCurrentPage > mTotalPage) {
                    mCurrentPage = 0;
                }
                long duration = CollectionsUtil.size(mTempModules) * 100L;
                if (mCurrentPage < mTotalPage) {
                    postDelay(() -> {
                        mTempModules.clear();
                        mTempModules.addAll(mModules.subList(mCurrentPage * 8, mCurrentPage * 8 + 8));
                        cancelAnimator();
                        mRvBoard.setLayoutAnimation(mControllerIn);
                        mAdapter.setModules(mTempModules);
                    }, duration);
                    return;
                }
                if (mCurrentPage == mTotalPage) {
                    postDelay(() -> {
                        mTempModules.clear();
                        mTempModules.addAll(mModules.subList(mCurrentPage * 8, CollectionsUtil.size(mModules)));
                        cancelAnimator();
                        mRvBoard.setLayoutAnimation(mControllerIn);
                        mAdapter.setModules(mTempModules);
                    }, duration);
                }
            }

            @Override
            public void onFinish() {
                initTimerTask();
            }
        };
        mTimer.start();
    }

    private void cancelAnimator() {
        if (CollectionsUtil.isNotEmpty(mTempModules)) {
            for (HomeRemoteModule module : mTempModules) {
                module.getAnimatorOffline().cancel();
                module.getAnimatorBpm().cancel();
                module.getAnimatorWarn().cancel();
            }
        }
    }

    private void initRecyclerView() {
        mAdapter = new HomeAdapter(this);
        mAdapter.setRecyclerViewHeight(DisplayUtils.getScreenHeight() - DisplayUtils.dp2px(100));
        mAdapter.setRecyclerViewWidth((float) (DisplayUtils.getScreenWidth() * 0.8 - DisplayUtils.dp2px(80)));
        mGridManager = new WrapGridLayoutManager(this, 2);
        mRvBoard.setLayoutManager(mGridManager);
        mRvBoard.setAdapter(mAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(mRvBoard.getItemAnimator())).setSupportsChangeAnimations(false);

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
    public void loading() {
        mMqttManager.publishMessage(JSON.toJSONString(new MqttRequest(100)));
    }

    @Override
    public void timer() {
        if (!DateUtils.formatHour(System.currentTimeMillis()).equals(mTvTimer.getText().toString())) {
            mTvTimer.setText(DateUtils.formatHour(System.currentTimeMillis()));
        }
    }

    @OnClick({R.id.rk_real})
    public void onViewClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        if (v.getId() == R.id.rk_real) {
            finish();
        }
    }

    public View makeView() {
        return View.inflate(mTsCount.getContext(), R.layout.people_count_text_view, null);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
//            if (mMqttManager != null) {
//                mMqttManager.destroy();
//            }
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
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
            case 201:
                ThreadUtils.execute(() -> notifyBpmOrKcChanged(JSON.parseObject(body, HeartRemoteModule.class)));
                L.e("HomeActivity201", "messageArrived:" + body);
                break;
            case 202:
                notifyInsertChanged(JSON.parseObject(body, HomeRemoteModule.class));
                L.e("HomeActivity202", "messageArrived:" + body);
                break;
            case 203:
                notifyRemoveChanged(JSON.parseObject(body, RemoveRemoteModule.class));
                L.e("HomeActivity203", "messageArrived:" + body);
                break;
            case 209:
                L.e("HomeActivity209", "messageArrived:" + body);
                initData(JSON.parseObject(body, HomeModule.class));
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void initData(HomeModule module) {
        if (module == null || CollectionsUtil.isEmpty(module.getData())) {
            return;
        }
        if (CollectionsUtil.isEmpty(mModules)) {
            mModules.clear();
            mModules.addAll(module.getData());
            initCache();
            initSpan();
            initPeopleCount();
            mTempModules.clear();
            if (mTotalPage < 1) {
                mTempModules.addAll(mModules);
            } else {
                if (mCurrentPage < mTotalPage) {
                    mTempModules.addAll(mModules.subList(mCurrentPage * 8, mCurrentPage * 8 + 8));
                } else {
                    mTempModules.addAll(mModules.subList(mCurrentPage * 8, CollectionsUtil.size(mModules)));
                }
            }
            mAdapter.setModules(mTempModules);
        } else {
            mModules.clear();
            mModules.addAll(module.getData());
            initCache();
            initSpan();
            initPeopleCount();
            mTempModules.clear();
            if (mTotalPage < 1) {
                mTempModules.addAll(mModules);
            } else {
                if (mCurrentPage < mTotalPage) {
                    mTempModules.addAll(mModules.subList(mCurrentPage * 8, mCurrentPage * 8 + 8));
                } else {
                    mTempModules.addAll(mModules.subList(mCurrentPage * 8, CollectionsUtil.size(mModules)));
                }
            }
            mAdapter.notifyItemRangeChanged(0, CollectionsUtil.size(mTempModules));
        }
    }

    private void initCache() {
        if (CollectionsUtil.size(mModules) > CollectionsUtil.size(HomeActivity.sOffsetCache)) {
            int mTempSize = CollectionsUtil.size(mModules) - CollectionsUtil.size(HomeActivity.sOffsetCache);
            for (int i = 0; i < mTempSize; i++) {
                HomeActivity.sOffsetCache.add(new OffsetModule());
            }
        }
    }

    private void initPeopleCount() {
        String mTempCount = String.valueOf(CollectionsUtil.size(mModules));
        if (!mCurrentCount.equals(mTempCount)) {
            mCurrentCount = mTempCount;
            mTsCount.setText(mCurrentCount);
        }
        mTotalPage = (CollectionsUtil.size(mModules) / 8) + (CollectionsUtil.size(mModules) % 8 > 0 ? 1 : 0) - 1;
        if (mTotalPage >= 1) {
            postDelay(() -> {
                if (mTimer == null) {
                    initTimerTask();
                }
            }, 10000);
        } else {
            if (mTimer != null) {
                mCurrentPage = 0;
                mTimer.cancel();
                mTimer = null;
            }
        }
    }

    private void initSpan() {
        mAdapter.setSize(CollectionsUtil.size(mModules));
        switch (CollectionsUtil.size(mModules)) {
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
    }

    private void notifyBpmOrKcChanged(HeartRemoteModule module) {
        if (CollectionsUtil.isEmpty(mTempModules) || module == null) {
            return;
        }
        for (HomeRemoteModule item : mTempModules) {
            if (item.getUid().equals(module.getUid())) {
                if ((!item.getHeart_rate().equals(module.getHeart_rate()) || !item.getCalorie().equals(module.getCalorie())) && System.currentTimeMillis() - item.getMillis() > 666) {
                    item.setMillis(System.currentTimeMillis());
                    item.setCalorie(module.getCalorie());
                    item.setHeart_rate(module.getHeart_rate());
                    item.setRatio(module.getRatio());
                    item.setStatus(true);
                    ThreadUtils.runOnMainThread(() -> mAdapter.notifyItemChanged(mTempModules.indexOf(item)));
                }
            }
        }
    }

    // 添加
    private void notifyInsertChanged(HomeRemoteModule module) {
        if (module == null) {
            return;
        }
        if (!isContain(module)) {
            mModules.add(module);
            initCache();
            initSpan();
            initPeopleCount();
            if (CollectionsUtil.size(mTempModules) < 8) {
                mTempModules.add(module);
                mAdapter.notifyItemRangeChanged(0, CollectionsUtil.size(mModules) - 1);
            }
        }
    }

    private boolean isContain(HomeRemoteModule module) {
        for (HomeRemoteModule item : mModules) {
            if (item.getUid().equals(module.getUid())) {
                return true;
            }
        }
        return false;
    }

    // 删除
    private void notifyRemoveChanged(RemoveRemoteModule module) {
        if (CollectionsUtil.isEmpty(mModules) || CollectionsUtil.isEmpty(mTempModules) || module == null || !isContain(module)) {
            return;
        }
        Iterator<HomeRemoteModule> iterator = mModules.iterator();
        while (iterator.hasNext()) {
            HomeRemoteModule item = iterator.next();
            if (item.getUid().equals(module.getUid())) {
                mModules.remove(item);
                initPeopleCount();
            }
        }
        mTempModules.clear();
        if (mTotalPage < 1) {
            mTempModules.addAll(mModules);
        } else {
            if (mCurrentPage < mTotalPage) {
                mTempModules.addAll(mModules.subList(mCurrentPage * 8, mCurrentPage * 8 + 8));
            } else {
                mTempModules.addAll(mModules.subList(mCurrentPage * 8, CollectionsUtil.size(mModules)));
            }
        }
        initSpan();
        mAdapter.notifyItemRangeChanged(0, CollectionsUtil.size(mTempModules));
    }

    private boolean isContain(RemoveRemoteModule module) {
        for (HomeRemoteModule item : mModules) {
            if (item.getUid().equals(module.getUid())) {
                return true;
            }
        }
        return false;
    }
}
