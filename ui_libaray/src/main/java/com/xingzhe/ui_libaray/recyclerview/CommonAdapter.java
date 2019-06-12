package com.xingzhe.ui_libaray.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wumm on 2019/3/20.
 */
public abstract class CommonAdapter<Data> extends RecyclerView.Adapter<CommonViewHolder> {
    public static final int VIEW_TYPE_HEADER_BASE = 100;
    public static final int VIEW_TYPE_FOOTER_BASE = 200;
    public static final int VIEW_TYPE_NORMAL = 0;
    private List<Data> data;
    protected Context context;

    private List<View> headerList;
    private List<View> footerList;

    public CommonAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Data> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void dataAppend(List<Data> data) {
        if (this.data == null) {
            this.data = new ArrayList<>(data);
        } else {
            this.data.addAll(data);
        }
    }

    public void dataAppendAndNotify(List<Data> data) {
        if (data == null)
            return;
        int startIndex = this.data.size()+getHeaderCount();
        dataAppend(data);
        notifyItemRangeInserted(startIndex,data.size());
    }

    public List<Data> getData() {
        return data;
    }

    public Data getItemData(int position) {
        if (data == null || position >= data.size()) {
            return null;
        }
        return data.get(position);
    }

    protected abstract View getItemView();

    protected abstract void bindItemView(CommonViewHolder commonViewHolder, int position, Data data);

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType >= VIEW_TYPE_FOOTER_BASE) {
            return new CommonViewHolder(footerList.get(viewType - VIEW_TYPE_FOOTER_BASE));
        } else if (viewType >= VIEW_TYPE_HEADER_BASE) {
            return new CommonViewHolder(headerList.get(viewType - VIEW_TYPE_HEADER_BASE));
        }
        return new CommonViewHolder(getItemView());
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder commonViewHolder, int position) {
        int headerCount = getHeaderCount();
        int footerCount = getFooterCount();
        int dataCount = getDataCount();
        if (position < headerCount) {
            bindHeaderView(headerList.get(position), position);
        } else if (position < headerCount + dataCount) {
            position = position - headerCount;
            bindItemView(commonViewHolder, position, data.get(position));
        } else if (position >= headerCount + dataCount && position < headerCount + dataCount + footerCount) {
            bindFooterView(footerList.get(position - headerCount - dataCount), position - headerCount - dataCount);
        }
    }

    protected void bindFooterView(View view, int position) {

    }

    private void bindHeaderView(View view, int position) {

    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount();
    }

    public void addHeaderView(View view) {
        if (headerList == null) {
            headerList = new ArrayList<>(5);
        }
        headerList.add(view);
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        if (footerList == null) {
            footerList = new ArrayList<>(5);
        }
        footerList.add(view);
        notifyDataSetChanged();
    }

    public View getHeaderByIndex(int index) {
        return headerList.get(index);
    }

    public View getFooterByIndex(int index) {
        return footerList.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = getHeaderCount();
        if (position < headerCount) {
            return position + VIEW_TYPE_HEADER_BASE;
        } else if (position < headerCount + getDataCount()) {
            return getDataItemViewType(position - headerCount);
        }
        return position - headerCount - getDataCount() + VIEW_TYPE_FOOTER_BASE;
    }

    protected int getDataItemViewType(int dataIndex) {
        return VIEW_TYPE_NORMAL;
    }

    private int getDataCount() {
        return data == null ? 0 : data.size();
    }

    private int getHeaderCount() {
        return headerList == null ? 0 : headerList.size();
    }

    private int getFooterCount() {
        return footerList == null ? 0 : footerList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new CommonSpanSizeLookup(((GridLayoutManager) layoutManager).getSpanCount()));
        }
    }

    public class CommonSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        int spanSize;

        public CommonSpanSizeLookup(int spanSize) {
            this.spanSize = spanSize;
        }

        @Override
        public int getSpanSize(int position) {
            if (position < getHeaderCount() || position >= getDataCount() + getHeaderCount()) {
                return spanSize;
            }
            return 1;
        }
    }
}
