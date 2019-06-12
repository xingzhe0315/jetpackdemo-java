package com.xingzhe.ui_libaray.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * Created by wumm on 2019/5/7.
 */
public class XZViewPager extends ViewPager {
    private SparseBooleanArray initStatus;
    private Page currentPage;
    private Runnable setupRunnable;

    private static final int SETUP_DELAY = 300;


    public XZViewPager(@NonNull Context context) {
        this(context, null);
    }

    public XZViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStatus = new SparseBooleanArray();
        addOnPageChangeListener(new PagerChangeListener());
    }

    public void showPage(int page){
        if (getCurrentItem() == page){
            post(new Runnable() {
                @Override
                public void run() {
                    if (getAdapter() instanceof BasePagerAdapter){
                        View cacheViewAt = ((BasePagerAdapter) getAdapter()).getCacheViewAt(getContext(), page);
                        Page pageItem = null;
                        if (cacheViewAt instanceof Page){
                            pageItem= (Page) cacheViewAt;
                        }

                        if (currentPage !=null && pageItem != currentPage){
                            currentPage.onPageHide();
                        }
                        if (pageItem != null){
                            if (initStatus.get(page)){
                                pageItem.onPageShow();
                                currentPage = pageItem;
                            } else {
                                pageItem.onPageInit();
                                pageItem.onPageShow();
                                currentPage = pageItem;
                                initStatus.put(page,true);
                            }
                        }
                    }
                }
            });
        } else {
            setCurrentItem(page);
        }
    }

    public class PagerChangeListener implements OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if (getAdapter() instanceof BasePagerAdapter) {
                View cacheViewAt = ((BasePagerAdapter) getAdapter()).getCacheViewAt(getContext(), i);
                Page pageItemView;
                if (cacheViewAt instanceof Page) {
                    pageItemView = (Page) cacheViewAt;
                } else {
                    pageItemView = null;
                }

                if (currentPage != null && currentPage != pageItemView) {
                    currentPage.onPageHide();
                }

                if (pageItemView != null) {
                    if (initStatus.get(i)) {
                        currentPage = pageItemView;
                        currentPage.onPageShow();
                    } else {
                        if (setupRunnable != null) {
                            removeCallbacks(setupRunnable);
                        }
                        setupRunnable = new Runnable() {
                            @Override
                            public void run() {
                                currentPage = pageItemView;
                                currentPage.onPageInit();
                                currentPage.onPageShow();
                                initStatus.put(i, true);
                                setupRunnable = null;
                            }
                        };
                        postDelayed(setupRunnable, SETUP_DELAY);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
