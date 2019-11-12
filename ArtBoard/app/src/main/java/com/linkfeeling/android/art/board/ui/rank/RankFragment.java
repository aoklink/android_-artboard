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
import com.link.feeling.mvp.common.MvpPresenter;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteModule;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    private RankItemAdapter mItemAdapter1;
    private RankItemAdapter mItemAdapter2;
    private RankItemAdapter mItemAdapter3;

    private RankRemoteModule mRankModule1;
    private RankRemoteModule mRankModule2;
    private RankRemoteModule mRankModule3;

    private int mNextPage = 0;
    private int mCurrentPage = 0;

    private LayoutAnimationController mControllerIn;

    private CircleTransform mCircleTransform;

    static RankFragment newInstance(RankRemoteModule module1, RankRemoteModule module2, RankRemoteModule module3) {
        RankFragment fragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KeysConstants.INDEX1, module1);
        bundle.putParcelable(KeysConstants.INDEX2, module2);
        bundle.putParcelable(KeysConstants.INDEX3, module3);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            return;
        }
        mRankModule1 = getArguments().getParcelable(KeysConstants.INDEX1);
        mRankModule2 = getArguments().getParcelable(KeysConstants.INDEX2);
        mRankModule3 = getArguments().getParcelable(KeysConstants.INDEX3);

        LinkImageLoader.INSTANCE.load(mRankModule1.getImg(), mLogo1);
        LinkImageLoader.INSTANCE.load(mRankModule2.getImg(), mLogo2);
        LinkImageLoader.INSTANCE.load(mRankModule3.getImg(), mLogo3);
//
        mRv1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRv3.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//
        mItemAdapter1 = new RankItemAdapter(getContext(), mRankModule1.getUnit());
        mItemAdapter2 = new RankItemAdapter(getContext(), mRankModule2.getUnit());
        mItemAdapter3 = new RankItemAdapter(getContext(), mRankModule3.getUnit());
//
        ((SimpleItemAnimator) mRv1.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) mRv2.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) mRv3.getItemAnimator()).setSupportsChangeAnimations(false);
        mControllerIn = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.item_rank_in_holder1);
        mCircleTransform = new CircleTransform();
        mRv1.setAdapter(mItemAdapter1);
        mRv2.setAdapter(mItemAdapter2);
        mRv3.setAdapter(mItemAdapter3);

        mRankList1 = mRankModule1.getData();
        mRankList2 = mRankModule2.getData();
        mRankList3 = mRankModule3.getData();

        if (CollectionsUtil.isNotEmpty(mRankList1)) {
            mRv1.setLayoutAnimation(mControllerIn);
            fillRankTop(mRankList1.get(0), mRank1View, mRankModule1.getUnit());
            mItemAdapter1.setItems(mRankList1.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }

        if (CollectionsUtil.isNotEmpty(mRankList2)) {
            mRv2.setLayoutAnimation(mControllerIn);
            fillRankTop(mRankList2.get(0), mRank2View, mRankModule2.getUnit());
            mItemAdapter2.setItems(mRankList2.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }

        if (CollectionsUtil.isNotEmpty(mRankList3)) {
            mRv3.setLayoutAnimation(mControllerIn);
            fillRankTop(mRankList3.get(0), mRank3View, mRankModule3.getUnit());
            mItemAdapter3.setItems(mRankList3.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }
    }

    @NotNull
    @Override
    public MvpPresenter createPresenter() {
        return new EmptyMvpPresenter();
    }


    void switchPage() {
        mCurrentPage = mNextPage;
        if (CollectionsUtil.isNotEmpty(mRankList1)) {
            mItemAdapter1.setPage(mNextPage);
            mRv1.setLayoutAnimation(mControllerIn);
            mItemAdapter1.setItems(mRankList1.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }
        if (CollectionsUtil.isNotEmpty(mRankList2)) {
            mItemAdapter2.setPage(mNextPage);
            mRv2.setLayoutAnimation(mControllerIn);
            mItemAdapter2.setItems(mRankList2.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }
        if (CollectionsUtil.isNotEmpty(mRankList3)) {
            mItemAdapter3.setPage(mNextPage);
            mRv3.setLayoutAnimation(mControllerIn);
            mItemAdapter3.setItems(mRankList3.subList(mNextPage * 5 + 1, mNextPage * 5 + 6));
        }
        mNextPage++;
        if (mNextPage >= 2) {
            mNextPage = 0;
        }
    }

    private void fillRankTop(RankRemoteItem item, View view, String unit) {
        ImageView avatar = view.findViewById(R.id.item_rank_avatar1);
        TextView holder = view.findViewById(R.id.item_rank_holder1);
        TextView name = view.findViewById(R.id.item_rank_name1);
        TextView value = view.findViewById(R.id.item_rank_value1);

        ThreadUtils.runOnMainThread(() -> {
            LinkImageLoader.INSTANCE.load(item.getHead_icon(), avatar, mCircleTransform);
            holder.setText(StringUtils.isEmpty(item.getValue()) ? "" : unit);
            name.setText(item.getUser_name());
            value.setText(item.getValue());
        });
    }

    void updateItem(RankRemoteItem item, int index) {
        switch (index) {
            case 0:
                RankRemoteItem local1 = matchContainer(item.getUid(), mRankList1);
                if (local1 != null) {
                    mRankList1.remove(local1);
                }
                mRankList1.add(item.getIndex(), item);
                fillRankTop(mRankList1.get(0), mRank1View, mRankModule1.getUnit());
                ThreadUtils.runOnMainThread(() -> mItemAdapter1.setItems(mRankList1.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                break;
            case 1:
                RankRemoteItem local2 = matchContainer(item.getUid(), mRankList2);
                if (local2 != null) {
                    mRankList2.remove(local2);
                }
                mRankList2.add(item.getIndex(), item);
                fillRankTop(mRankList2.get(0), mRank2View, mRankModule2.getUnit());
                ThreadUtils.runOnMainThread(() -> mItemAdapter2.setItems(mRankList2.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                break;
            case 2:
                RankRemoteItem local3 = matchContainer(item.getUid(), mRankList3);
                if (local3 != null) {
                    mRankList3.remove(local3);
                }
                mRankList3.add(item.getIndex(), item);
                fillRankTop(mRankList3.get(0), mRank3View, mRankModule3.getUnit());
                ThreadUtils.runOnMainThread(() -> mItemAdapter3.setItems(mRankList3.subList(mCurrentPage * 5 + 1, mCurrentPage * 5 + 6)));
                break;
        }
    }

    private RankRemoteItem matchContainer(String uid, List<RankRemoteItem> modules) {
        if (CollectionsUtil.isEmpty(modules)) {
            return null;
        }
        for (RankRemoteItem item : modules) {
            if (item.getUid().equals(uid)) {
                return item;
            }
        }
        return null;
    }
}
