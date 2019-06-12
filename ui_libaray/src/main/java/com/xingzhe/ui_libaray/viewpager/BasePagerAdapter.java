package com.xingzhe.ui_libaray.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wumm on 2019/5/7.
 */
public abstract class BasePagerAdapter extends PagerAdapter {
    private SparseArray<View> cachedView = new SparseArray<>();

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = getCacheViewAt(container.getContext(), position);
        if (itemView.getParent() == null)
            container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public View getCacheViewAt(Context context, int position) {
        if (position > getCount() - 1) {
            return null;
        }
        View cachedItemView = cachedView.get(position);
        if (cachedItemView == null) {
            cachedItemView = createItemView(context, position);
            cachedView.put(position,cachedItemView);
        }
        return cachedItemView;
    }

    protected abstract View createItemView(Context context, int position);
}
