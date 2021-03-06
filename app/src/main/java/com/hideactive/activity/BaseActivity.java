package com.hideactive.activity;

import com.hideactive.R;
import com.hideactive.SessionApplication;
import com.hideactive.dialog.LoadingDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {

    protected LoadingDialog loadingDialog;
    protected SessionApplication application;

    private CompositeDisposable composite4Destroy = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftInputView();
        loadingDialog = new LoadingDialog(this);
        application = SessionApplication.getInstance();
        application.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        composite4Destroy.clear();
        application.removeActivity(this);
        super.onDestroy();
    }

    /**
     * 绑定至Destroy生命周期
     *
     * @param disposable
     */
    protected void bindUntilDestroy(Disposable disposable) {
        composite4Destroy.add(disposable);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开Activity，并附带动画
     * 可选则anim文件夹下任意动画，此处为其中一种案例
     *
     * @param intent
     */
    protected void openActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * 打开Activity，并附带动画
     * 可选则anim文件夹下任意动画，此处为其中一种案例
     *
     * @param intent
     * @param requestCode
     */
    protected void openForResultActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * 关闭Activity，并附带动画
     * 可选则anim文件夹下任意动画，此处为其中一种案例
     */
    protected void closeActivity() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    /**
     * 打开Activity销毁当前activity，并附带动画
     * 可选则anim文件夹下任意动画，此处为其中一种案例
     *
     * @param intent
     */
    protected void openActivityAndClose(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();
    }

}
