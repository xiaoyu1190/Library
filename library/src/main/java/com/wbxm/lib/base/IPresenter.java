package com.wbxm.lib.base;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public interface IPresenter<V extends IBaseView> {

    /**
     * presenter和对应的view绑定
     * @param view  目标view
     */
    void attachView(V view);
    /**
     * presenter与view解绑
     */
    void detachView();

}
