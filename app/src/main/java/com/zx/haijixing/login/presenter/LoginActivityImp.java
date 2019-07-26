package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.login.contract.ILoginActivityContract;
import com.zx.haijixing.login.entry.LoginEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:43
 *@描述 登录presenter
 */
public class LoginActivityImp extends BasePresenter<ILoginActivityContract.LoginView> implements ILoginActivityContract.LoginPresenter {

    @Override
    public void loginMethod(String account, String password) {
        mView.showLoading();
        RxHttpUtils.createApi(LoginApiService.class)
                .loginApiPass(account,password)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<LoginEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.hideLoading();
                    }

                    @Override
                    protected void onSuccess(LoginEntry data) {
                        mView.hideLoading();
                        mView.loginSuccess(data);
                    }
                });
    }

    @Override
    public void loginMethodC(String account, String code) {
        mView.showLoading();
        RxHttpUtils.createApi(LoginApiService.class)
                .loginApiCode(account,code)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<LoginEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.hideLoading();
                    }

                    @Override
                    protected void onSuccess(LoginEntry data) {
                        mView.hideLoading();
                        mView.loginSuccess(data);
                    }
                });
    }

    @Override
    public void loginCodeMethod(String phone) {
        RxHttpUtils.createApi(LoginApiService.class)
                .sendRegisterCode(phone,1)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 0){
                                mView.loginCodeSuccess();
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
