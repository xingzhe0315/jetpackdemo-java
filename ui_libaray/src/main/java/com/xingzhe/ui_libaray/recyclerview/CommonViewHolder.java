package com.xingzhe.ui_libaray.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wumm on 2019/3/20.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewMap;
    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        viewMap = new SparseArray<>();
    }

    public View getView(int id){
        View view = viewMap.get(id);
        if (view == null){
            view = itemView.findViewById(id);
            viewMap.put(id,view);
        }
        return view;
    }

    public CommonViewHolder setText(int id,CharSequence text){
        ((TextView) getView(id)).setText(text);
        return this;
    }

    public CommonViewHolder setText(int id,int resId){
        ((TextView) getView(id)).setText(resId);
        return this;
    }

}
