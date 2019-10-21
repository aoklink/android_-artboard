package com.linkfeeling.android.art.board.ui.rank;


import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.base.EmptyMvpPresenter;
import com.link.feeling.framework.base.FrameworkBaseFragment;
import com.link.feeling.framework.component.image.LinkImageLoader;
import com.link.feeling.framework.component.image.transformation.CircleTransform;
import com.link.feeling.framework.utils.ThreadUtils;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.StringUtils;
import com.link.feeling.framework.widgets.NumParseUtil;
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.constants.StringConstants;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
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

    @BindView(R.id.rank1_root)
    View mRank1View;
    @BindView(R.id.rank2_root)
    View mRank2View;
    @BindView(R.id.rank3_root)
    View mRank3View;

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

    private int mIndex;

    private int mNextPage = 0;
    private int mCurrentPage = 0;

    private LayoutAnimationController mControllerIn;

    private CircleTransform mCircleTransform;

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
        mIndex = getArguments() == null ? 0 : getArguments().getInt(KeysConstants.INDEX);
        mLogo1.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 1));
        mLogo2.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 2));
        mLogo3.setImageResource(ImageConstants.matchRankLogo(mIndex * 3 + 3));

        mRv1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv3.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        mItemAdapter1 = new RankItemAdapter(getContext(), mIndex * 3 + 1);
        mItemAdapter2 = new RankItemAdapter(getContext(), mIndex * 3 + 2);
        mItemAdapter3 = new RankItemAdapter(getContext(), mIndex * 3 + 3);

        ((SimpleItemAnimator) Objects.requireNonNull(mRv1.getItemAnimator())).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) Objects.requireNonNull(mRv2.getItemAnimator())).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) Objects.requireNonNull(mRv3.getItemAnimator())).setSupportsChangeAnimations(false);


        mControllerIn = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.item_rank_in_holder);
        mCircleTransform = new CircleTransform();

        mRv1.setAdapter(mItemAdapter1);
        mRv2.setAdapter(mItemAdapter2);
        mRv3.setAdapter(mItemAdapter3);
    }

    @NotNull
    @Override
    public MvpPresenter createPresenter() {
        return new EmptyMvpPresenter();
    }


    private void switchPage() {
        mCurrentPage = mNextPage;
        mItemAdapter1.setPage(mNextPage);
        mItemAdapter2.setPage(mNextPage);
        mItemAdapter3.setPage(mNextPage);

        mRv1.setLayoutAnimation(mControllerIn);
        mRv2.setLayoutAnimation(mControllerIn);
        mRv3.setLayoutAnimation(mControllerIn);

        if (mIndex == 0) {
            mItemAdapter1.setItems(mRankList1.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter2.setItems(mRankList2.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter3.setItems(mRankList3.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        } else if (mIndex == 1) {
            mItemAdapter1.setItems(mRankList4.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter2.setItems(mRankList5.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter3.setItems(mRankList6.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        } else if (mIndex == 2) {
            mItemAdapter1.setItems(mRankList7.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter2.setItems(mRankList8.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
            mItemAdapter3.setItems(mRankList9.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }

        mNextPage++;
        if (mNextPage >= 2) {
            mNextPage = 0;
        }
    }

    void initRank1(List<RankRemoteItem> list1, List<RankRemoteItem> list2, List<RankRemoteItem> list3) {
        mRankList1 = list1;
        mRankList2 = list2;
        mRankList3 = list3;

        mRv1.setLayoutAnimation(mControllerIn);
        mRv2.setLayoutAnimation(mControllerIn);
        mRv3.setLayoutAnimation(mControllerIn);

        fillRankTop(mRankList1.get(0), mRank1View, 1);
        fillRankTop(mRankList2.get(0), mRank2View, 2);
        fillRankTop(mRankList3.get(0), mRank3View, 3);

        mItemAdapter1.setItems(mRankList1.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter2.setItems(mRankList2.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter3.setItems(mRankList3.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
    }

    void initRank2(List<RankRemoteItem> list4, List<RankRemoteItem> list5, List<RankRemoteItem> list6) {
        mRankList4 = list4;
        mRankList5 = list5;
        mRankList6 = list6;

        mRv1.setLayoutAnimation(mControllerIn);
        mRv2.setLayoutAnimation(mControllerIn);
        mRv3.setLayoutAnimation(mControllerIn);

        fillRankTop(mRankList4.get(0), mRank1View, 4);
        fillRankTop(mRankList5.get(0), mRank2View, 5);
        fillRankTop(mRankList6.get(0), mRank3View, 6);

        mItemAdapter1.setItems(mRankList4.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter2.setItems(mRankList5.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter3.setItems(mRankList6.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
    }

    void initRank3(List<RankRemoteItem> list7, List<RankRemoteItem> list8, List<RankRemoteItem> list9) {
        mRankList7 = list7;
        mRankList8 = list8;
        mRankList9 = list9;

        mRv1.setLayoutAnimation(mControllerIn);
        mRv2.setLayoutAnimation(mControllerIn);
        mRv3.setLayoutAnimation(mControllerIn);

        fillRankTop(mRankList7.get(0), mRank1View, 7);
        fillRankTop(mRankList8.get(0), mRank2View, 8);
        fillRankTop(mRankList9.get(0), mRank3View, 9);

        mItemAdapter1.setItems(mRankList7.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter2.setItems(mRankList8.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        mItemAdapter3.setItems(mRankList9.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
    }


    void notifyRank1() {
        switchPage();
    }

    void notifyRank2() {
        switchPage();
    }

    void notifyRank3() {
        switchPage();
    }

    private void fillRankTop(RankRemoteItem item, View view, int index) {
        ImageView avatar = view.findViewById(R.id.item_rank_avatar1);
        TextView holder = view.findViewById(R.id.item_rank_holder1);
        TextView name = view.findViewById(R.id.item_rank_name1);
        TextView value = view.findViewById(R.id.item_rank_value1);

        ThreadUtils.runOnMainThread(() -> {
            LinkImageLoader.INSTANCE.load(item.getHead_icon(), avatar, mCircleTransform);
            holder.setText(StringUtils.isEmpty(item.getUid()) ? "" : StringConstants.matchHolder(index));
            name.setText(item.getUser_name());
            value.setText(item.getFormatValue(index));
        });
    }

    void updateRank1(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList1) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList1, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
                fillRankTop(mRankList1.get(0), mRank1View, 1);
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList1.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList1.add(item);
        Collections.sort(mRankList1, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
        if (CollectionsUtil.size(mRankList1) > KeysConstants.RANK_ITEM) {
            mRankList1 = mRankList1.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList1.get(0), mRank1View, 1);
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList1.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank2(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList2) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList2, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
                fillRankTop(mRankList2.get(0), mRank2View, 2);
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList2.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList2.add(item);
        Collections.sort(mRankList2, (o1, o2) -> NumParseUtil.parseInt(o2.getValue()) - NumParseUtil.parseInt(o1.getValue()));
        if (CollectionsUtil.size(mRankList2) > KeysConstants.RANK_ITEM) {
            mRankList2 = mRankList2.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList2.get(0), mRank2View, 2);
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList2.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank3(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList3) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList3, (o1, o2) -> Long.compare(NumParseUtil.parseLong(o2.getValue()), NumParseUtil.parseLong(o1.getValue())));
                fillRankTop(mRankList3.get(0), mRank3View, 3);
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList3.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList3.add(item);
        Collections.sort(mRankList3, (o1, o2) -> Long.compare(NumParseUtil.parseLong(o2.getValue()), NumParseUtil.parseLong(o1.getValue())));
        if (CollectionsUtil.size(mRankList3) > KeysConstants.RANK_ITEM) {
            mRankList3 = mRankList3.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList3.get(0), mRank3View, 3);
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList3.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }


    void updateRank4(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList4) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList4, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList4.get(0), mRank1View, 4);
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList4.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList4.add(item);
        Collections.sort(mRankList4, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList4) > KeysConstants.RANK_ITEM) {
            mRankList4 = mRankList4.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList4.get(0), mRank1View, 4);
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList4.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank5(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList5) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList5, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList5.get(0), mRank2View, 5);
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList5.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList5.add(item);
        Collections.sort(mRankList5, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList5) > KeysConstants.RANK_ITEM) {
            mRankList5 = mRankList5.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList5.get(0), mRank2View, 5);
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList5.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank6(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList6) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList6, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList6.get(0), mRank3View, 6);
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList6.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList6.add(item);
        Collections.sort(mRankList6, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList6) > KeysConstants.RANK_ITEM) {
            mRankList6 = mRankList6.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList6.get(0), mRank3View, 6);
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList6.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank7(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList7) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList7, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList7.get(0), mRank1View, 7);
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList7.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList7.add(item);
        Collections.sort(mRankList7, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList7) > KeysConstants.RANK_ITEM) {
            mRankList7 = mRankList7.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList7.get(0), mRank1View, 7);
        ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList7.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank8(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList8) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList8, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList8.get(0), mRank2View, 8);
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList8.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList8.add(item);
        Collections.sort(mRankList8, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList8) > KeysConstants.RANK_ITEM) {
            mRankList8 = mRankList8.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList8.get(0), mRank2View, 8);
        ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList8.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }

    void updateRank9(RankRemoteItem item) {
        for (RankRemoteItem remote : mRankList9) {
            if (remote.getUid().equals(item.getUid())) {
                remote.setValue(item.getValue());
                Collections.sort(mRankList9, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
                fillRankTop(mRankList9.get(0), mRank3View, 9);
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList9.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                return;
            }
        }
        mRankList9.add(item);
        Collections.sort(mRankList9, (o1, o2) -> Float.compare(NumParseUtil.parseFloat(o2.getValue()), NumParseUtil.parseFloat(o1.getValue())));
        if (CollectionsUtil.size(mRankList9) > KeysConstants.RANK_ITEM) {
            mRankList9 = mRankList9.subList(0, KeysConstants.RANK_ITEM);
        }
        fillRankTop(mRankList9.get(0), mRank3View, 9);
        ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList9.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
    }
}
