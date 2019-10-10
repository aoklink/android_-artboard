package com.linkfeeling.android.art.board.ui.rank;


import android.os.Bundle;
import android.widget.ImageView;

import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.base.EmptyMvpPresenter;
import com.link.feeling.framework.base.FrameworkBaseFragment;
import com.link.feeling.framework.utils.ThreadUtils;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.StringUtils;
import com.link.feeling.framework.widgets.NumParseUtil;
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends FrameworkBaseFragment {

    @BindView(R.id.rk_logo1)
    ImageView mLogo1;
    @BindView(R.id.rk_logo2)
    ImageView mLogo2;
    @BindView(R.id.rk_logo3)
    ImageView mLogo3;

    @BindView(R.id.rk_rv1)
    RecyclerView mRv1;
    @BindView(R.id.rk_rv2)
    RecyclerView mRv2;
    @BindView(R.id.rk_rv3)
    RecyclerView mRv3;

    private List<RankRemoteItem> mRankList1;
    private List<RankRemoteItem> mRankList2;
    private List<RankRemoteItem> mRankList3;
    private List<RankRemoteItem> mRankList4;
    private List<RankRemoteItem> mRankList5;
    private List<RankRemoteItem> mRankList6;
    private List<RankRemoteItem> mRankList7;
    private List<RankRemoteItem> mRankList8;
    private List<RankRemoteItem> mRankList9;

    private RankItemAdapter mItemAdapter1;
    private RankItemAdapter mItemAdapter2;
    private RankItemAdapter mItemAdapter3;

    private Disposable mDisposable1;
    private Disposable mDisposable2;
    private Disposable mDisposable3;

    static RankFragment newInstance(int index) {
        RankFragment fragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KeysConstants.INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        int mIndex = getArguments() == null ? 0 : getArguments().getInt(KeysConstants.INDEX);
        mLogo1.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 1));
        mLogo2.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 2));
        mLogo3.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 3));

        mRv1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv3.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        mItemAdapter1 = new RankItemAdapter(getContext(), mIndex * 3 + 1);
        mItemAdapter2 = new RankItemAdapter(getContext(), mIndex * 3 + 2);
        mItemAdapter3 = new RankItemAdapter(getContext(), mIndex * 3 + 3);

        mRv1.setAdapter(mItemAdapter1);
        mRv2.setAdapter(mItemAdapter2);
        mRv3.setAdapter(mItemAdapter3);
    }

    @NotNull
    @Override
    public MvpPresenter createPresenter() {
        return new EmptyMvpPresenter();
    }

    private boolean isGo(List<RankRemoteItem> items) {
        int index = 0;
        for (RankRemoteItem item : items) {
            if (StringUtils.isNotEmpty(item.getUid())) {
                index++;
            }
        }
        return index <= 5;
    }

    void initRank1(List<RankRemoteItem> list1, List<RankRemoteItem> list2, List<RankRemoteItem> list3) {
        mRankList1 = list1;
        mRankList2 = list2;
        mRankList3 = list3;

        mItemAdapter1.setItems(mRankList1);
        mItemAdapter2.setItems(mRankList2);
        mItemAdapter3.setItems(mRankList3);

        ThreadUtils.execute(() -> startLooper1(mRankList1));
        ThreadUtils.execute(() -> startLooper2(mRankList2));
        ThreadUtils.execute(() -> startLooper3(mRankList3));
    }

    void initRank2(List<RankRemoteItem> list4, List<RankRemoteItem> list5, List<RankRemoteItem> list6) {
        mItemAdapter1.setItems(list4);
        mItemAdapter2.setItems(list5);
        mItemAdapter3.setItems(list6);

        mRankList4 = list4;
        mRankList5 = list5;
        mRankList6 = list6;

        ThreadUtils.execute(() -> startLooper1(mRankList4));
        ThreadUtils.execute(() -> startLooper2(mRankList5));
        ThreadUtils.execute(() -> startLooper3(mRankList6));
    }

    void initRank3(List<RankRemoteItem> list7, List<RankRemoteItem> list8, List<RankRemoteItem> list9) {
        mItemAdapter1.setItems(list7);
        mItemAdapter2.setItems(list8);
        mItemAdapter3.setItems(list9);

        mRankList7 = list7;
        mRankList8 = list8;
        mRankList9 = list9;

        ThreadUtils.execute(() -> startLooper1(mRankList7));
        ThreadUtils.execute(() -> startLooper2(mRankList8));
        ThreadUtils.execute(() -> startLooper3(mRankList9));
    }

    private void startLooper1(List<RankRemoteItem> items) {
        if (isGo(items)) {
            stopLooper1();
            return;
        }
        if (mDisposable1 == null) {
            mDisposable1 = Flowable
                    .interval(1000, 100, TimeUnit.MILLISECONDS)
                    .onBackpressureLatest()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mRv1.smoothScrollBy(0, 20));
        }
    }

    private void startLooper2(List<RankRemoteItem> items) {
        if (isGo(items)) {
            stopLooper2();
            return;
        }
        if (mDisposable2 == null) {
            mDisposable2 = Flowable
                    .interval(1000, 100, TimeUnit.MILLISECONDS)
                    .onBackpressureLatest()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mRv2.smoothScrollBy(0, 20));
        }
    }

    private void startLooper3(List<RankRemoteItem> items) {
        if (isGo(items)) {
            stopLooper3();
            return;
        }
        if (mDisposable3 == null) {
            mDisposable3 = Flowable
                    .interval(1000, 100, TimeUnit.MILLISECONDS)
                    .onBackpressureLatest()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mRv3.smoothScrollBy(0, 20));
        }
    }


    private void stopLooper1() {
        if (mDisposable1 != null && !mDisposable1.isDisposed()) {
            mDisposable1.dispose();
        }
        mDisposable1 = null;
    }

    private void stopLooper2() {
        if (mDisposable2 != null && !mDisposable2.isDisposed()) {
            mDisposable2.dispose();
        }
        mDisposable2 = null;
    }

    private void stopLooper3() {
        if (mDisposable3 != null && !mDisposable3.isDisposed()) {
            mDisposable3.dispose();
        }
        mDisposable3 = null;
    }


    void updateRank1(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList1) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList1, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList1));
                return;
            }
        }
        mRankList1.add(item);
        Collections.sort(mRankList1, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
        if (CollectionsUtil.size(mRankList1) > 10) {
            mRankList1 = mRankList1.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList1.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList1));
        ThreadUtils.execute(() -> startLooper1(mRankList1));
    }

    void updateRank2(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList2) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList2, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList2));
                return;
            }
        }
        mRankList2.add(item);
        Collections.sort(mRankList2, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
        if (CollectionsUtil.size(mRankList2) > 10) {
            mRankList2 = mRankList2.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList2.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList2));
        ThreadUtils.execute(() -> startLooper1(mRankList2));
    }

    void updateRank3(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList3) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList3, (o1, o2) -> Long.compare(NumParseUtil.parseLong(o2.getValue()), NumParseUtil.parseLong(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList3));
                return;
            }
        }
        mRankList3.add(item);
        Collections.sort(mRankList3, (o1, o2) -> Long.compare(NumParseUtil.parseLong(o2.getValue()), NumParseUtil.parseLong(o1.getValue())));
        if (CollectionsUtil.size(mRankList3) > 10) {
            mRankList3 = mRankList3.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList3.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList3));
        ThreadUtils.execute(() -> startLooper1(mRankList3));
    }


    void updateRank4(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList4) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList4, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList4));
                return;
            }
        }
        mRankList4.add(item);
        Collections.sort(mRankList4, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList4) > 10) {
            mRankList4 = mRankList4.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList4.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList4));
        ThreadUtils.execute(() -> startLooper1(mRankList4));
    }

    void updateRank5(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList5) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList5, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList5));
                return;
            }
        }
        mRankList5.add(item);
        Collections.sort(mRankList5, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList5) > 10) {
            mRankList5 = mRankList5.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList5.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList5));
        ThreadUtils.execute(() -> startLooper1(mRankList5));
    }

    void updateRank6(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList6) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList6, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList6));
                return;
            }
        }
        mRankList6.add(item);
        Collections.sort(mRankList6, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList6) > 10) {
            mRankList6 = mRankList6.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList6.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList6));
        ThreadUtils.execute(() -> startLooper1(mRankList6));
    }

    void updateRank7(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList7) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList7, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList7));
                return;
            }
        }
        mRankList7.add(item);
        Collections.sort(mRankList7, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList7) > 10) {
            mRankList7 = mRankList7.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList7.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList7));
        ThreadUtils.execute(() -> startLooper1(mRankList7));
    }

    void updateRank8(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList8) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList8, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList8));
                return;
            }
        }
        mRankList8.add(item);
        Collections.sort(mRankList8, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList8) > 10) {
            mRankList8 = mRankList8.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList8.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList8));
        ThreadUtils.execute(() -> startLooper1(mRankList8));
    }

    void updateRank9(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList9) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList9, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList9));
                return;
            }
        }
        mRankList9.add(item);
        Collections.sort(mRankList9, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList9) > 10) {
            mRankList9 = mRankList9.subList(0, 10);
        }
        for (int i = 0; i < 5; i++) {
            mRankList9.add(new RankRemoteItem());
        }
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList9));
        ThreadUtils.execute(() -> startLooper1(mRankList9));
    }

}
