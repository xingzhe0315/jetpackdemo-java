package com.xingzhe.framework.data;

import java.util.List;

/**
 * Created by wumm on 2019/5/8.
 */
public abstract class SingleObserver<Data extends BaseResponseData> extends BaseObserver<Data> {
    @Override
    protected void onDataSuccess(List<Data> response) {
        onDataSuccess(response.get(0));
    }

    protected abstract void onDataSuccess(Data response);
}
