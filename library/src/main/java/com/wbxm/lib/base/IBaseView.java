package com.wbxm.lib.base;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public interface IBaseView {

    /**
     * 显示进度条
     * @param msg   进度条加载内容
     */
    void showLoading(String msg);
    /**
     * 隐藏进度条
     */
    void hideLoding();
    /**
     * 弹toast
     */
    void showMsg(String msg);

    void setLoadingCanceledOutsideDisable();

}
