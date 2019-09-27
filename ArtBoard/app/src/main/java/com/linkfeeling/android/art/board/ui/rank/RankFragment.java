package com.linkfeeling.android.art.board.ui.rank;


import android.os.Bundle;
import android.widget.ImageView;

import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.base.EmptyMvpPresenter;
import com.link.feeling.framework.base.FrameworkBaseFragment;
import com.link.feeling.framework.widgets.NumParseUtil;
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

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


    void initRank1(List<RankRemoteItem> list1, List<RankRemoteItem> list2, List<RankRemoteItem> list3) {
        mItemAdapter1.setItems(list1);
        mItemAdapter2.setItems(list2);
        mItemAdapter3.setItems(list3);

        mRankList1 = list1;
        mRankList2 = list2;
        mRankList3 = list3;
    }

    void initRank2(List<RankRemoteItem> list4, List<RankRemoteItem> list5, List<RankRemoteItem> list6) {
        mItemAdapter1.setItems(list4);
        mItemAdapter2.setItems(list5);
        mItemAdapter3.setItems(list6);

        mRankList4 = list4;
        mRankList5 = list5;
        mRankList6 = list6;
    }

    void initRank3(List<RankRemoteItem> list7, List<RankRemoteItem> list8, List<RankRemoteItem> list9) {
        mItemAdapter1.setItems(list7);
        mItemAdapter2.setItems(list8);
        mItemAdapter3.setItems(list9);

        mRankList7 = list7;
        mRankList8 = list8;
        mRankList9 = list9;
    }


    void updateRank1(RankRemoteItem item) {
        boolean isContain = false;
        for (RankRemoteItem remote : mRankList1) {
            if (remote.getUid().equals(item.getUid())) {
                isContain = true;
                remote.setValue(NumParseUtil.parseInt(remote.getValue()) > NumParseUtil.parseInt(item.getValue()) ? remote.getValue() : item.getValue());
            }
        }
        if (!isContain) {
            mRankList1.add(item);
        }
        Collections.sort(mRankList1, (o1, o2) -> NumParseUtil.parseInt(o1.getValue()) - NumParseUtil.parseInt(o2.getValue()));
        mRankList1 = mRankList1.subList(0, 10);
        mItemAdapter1.setItems(mRankList1);
    }

    void updateRank2(RankRemoteItem item) {
        boolean isContain = false;
        for (RankRemoteItem remote : mRankList2) {
            if (remote.getUid().equals(item.getUid())) {
                isContain = true;
                remote.setValue(NumParseUtil.parseInt(remote.getValue()) > NumParseUtil.parseInt(item.getValue()) ? remote.getValue() : item.getValue());
            }
        }
        if (!isContain) {
            mRankList2.add(item);
        }
        Collections.sort(mRankList2, (o1, o2) -> NumParseUtil.parseInt(o1.getValue()) - NumParseUtil.parseInt(o2.getValue()));
        mRankList2 = mRankList2.subList(0, 10);
        mItemAdapter2.setItems(mRankList2);
    }

    void updateRank3(RankRemoteItem item) {
        boolean isContain = false;
        for (RankRemoteItem remote : mRankList3) {
            if (remote.getUid().equals(item.getUid())) {
                isContain = true;
                remote.setValue(NumParseUtil.parseInt(remote.getValue()) > NumParseUtil.parseInt(item.getValue()) ? remote.getValue() : item.getValue());
            }
        }
        if (!isContain) {
            mRankList3.add(item);
        }
        Collections.sort(mRankList3, (o1, o2) -> NumParseUtil.parseInt(o1.getValue()) - NumParseUtil.parseInt(o2.getValue()));
        mRankList3 = mRankList3.subList(0, 10);
        mItemAdapter3.setItems(mRankList3);
    }

    void updateRank4(RankRemoteItem item) {

    }

    void updateRank5(RankRemoteItem item) {

    }

    void updateRank6(RankRemoteItem item) {

    }

    void updateRank7(RankRemoteItem item) {

    }

    void updateRank8(RankRemoteItem item) {

    }

    void updateRank9(RankRemoteItem item) {

    }


}
