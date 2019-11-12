package com.linkfeeling.android.art.board.ui.rank;

import android.view.ViewGroup;

import com.link.feeling.framework.utils.data.CollectionsUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created on 2019/9/26  14:23
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankPagerAdapter extends FragmentStatePagerAdapter {

    private List<RankFragment> mFragments;

    private FragmentManager mFragmentManager;

    RankPagerAdapter(@NonNull FragmentManager fm, int behavior, List<RankFragment> mFragments) {
        super(fm, behavior);
        this.mFragments = new ArrayList<>();
        this.mFragments.clear();
        this.mFragments.addAll(mFragments);
        this.mFragmentManager = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (!((Fragment) object).isAdded() || !mFragments.contains(object)) {
            return PagerAdapter.POSITION_NONE;
        }
        return mFragments.indexOf(object);
    }

    @Override
    public int getCount() {
        return CollectionsUtil.size(mFragments);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
