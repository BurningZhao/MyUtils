package com.zhao.myutils.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhao.myutils.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类碎片数据集
 * 包含了对数据集所有操作 添加 删除 设置数据
 */
public class FragePageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;

    public FragePageAdapter(FragmentManager fm) {
        super(fm);
        this.list = new ArrayList<BaseFragment>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 添加一个Fragment 片段
     *
     * @param mFragment
     */
    public void addFragment(BaseFragment mFragment) {
        list.add(mFragment);
        notifyDataSetChanged();
    }

    /**
     * 删除一个片段
     *
     * @param mFragment
     */
    public void delFragment(BaseFragment mFragment) {
        list.remove(mFragment);
        notifyDataSetChanged();
    }

    /**
     * 清空所有片段
     */
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除某个索引片段
     *
     * @param i
     */
    public void delFragment(int i) {
        list.remove(i);
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setList(List<BaseFragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public BaseFragment getItem(int position) {
        return list.get(position);
    }

}
