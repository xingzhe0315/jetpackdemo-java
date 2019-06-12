package com.xingzhe.ui_libaray.dataview;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/5/5.
 */
public class EmptyObservable {
    public static Observable<Object> getNullObservable(){
        return Observable.just(null);
    }
}
