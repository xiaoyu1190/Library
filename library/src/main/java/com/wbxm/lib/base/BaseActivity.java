package com.wbxm.lib.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.umeng.analytics.MobclickAgent;
import com.wbxm.lib.dialog.LoadingDialog;
import com.wbxm.lib.utils.StringUtil;
import com.wbxm.lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public abstract class BaseActivity<P extends BasePresenter<V>, V extends IBaseView> extends FragmentActivity implements IBaseView {

    protected P presenter;

    private LoadingDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = initPresenter();
        presenter.attachView((V) this);
        initViews();
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void showLoading(String msg) {
        showLoadingProgress(msg);
    }

    @Override
    public void hideLoding() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
            }
        });
    }

    @Override
    public void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isEmpty(msg)) {
                    ToastUtil.ToastMessage(BaseActivity.this, "网络请求失败，请重试!");
                } else {
                    ToastUtil.ToastMessage(BaseActivity.this, msg);
                }

            }
        });
    }

    protected void showLoadingProgress(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new LoadingDialog(BaseActivity.this);
                }
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.setContent(msg);
                if (!progressDialog.isShowing())
                    progressDialog.show();
            }
        });
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setLoadingCanceledOutsideDisable() {
        if (progressDialog != null)
            progressDialog.setCanceledOnTouchOutside(false);
    }

    protected abstract void initViews();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract P initPresenter();

    private PermissionListener mlistener;

    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    protected void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        this.mlistener = listener;

        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {  //为空，则已经全部授权
            listener.onGranted();
        }
    }

    /**
     * 权限申请结果
     *
     * @param requestCode  请求码
     * @param permissions  所有的权限集合
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //被用户拒绝的权限集合
                    List<String> deniedPermissions = new ArrayList<>();
                    //用户通过的权限集合
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        //获取授权结果，这是一个int类型的值
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {  //用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {  //用户拒绝权限为空
                        mlistener.onGranted();
                    } else {  //不为空
                        //回调授权失败的接口
                        mlistener.onDenied(deniedPermissions);
                        //回调授权成功的接口
                        mlistener.onGranted(grantedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 权限回调接口
     */
    public interface PermissionListener {
        //授权成功
        void onGranted();

        //授权部分
        void onGranted(List<String> grantedPermission);

        //拒绝授权
        void onDenied(List<String> deniedPermission);
    }

}
