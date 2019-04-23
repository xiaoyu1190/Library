package com.wbxm.lib.base;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class BasePresenter<V extends IBaseView> implements IPresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }

    /**
     * 检查view和presenter是否连接
     */
    public boolean isAttachView() {
        return mView != null;
    }
}
