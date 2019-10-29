package com.zx.haijixing.logistics.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.ProductAdapter;
import com.zx.haijixing.logistics.contract.QuantitySetConnect;
import com.zx.haijixing.logistics.entry.ProductEntry;
import com.zx.haijixing.logistics.presenter.QuantitySetImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;


/**
 * @作者 zx
 * @创建日期 2019/10/29 9:21
 * @描述 批量管理线路
 */
@Route(path = PathConstant.QUANTITY_SET)
public class QuantitySetActivity extends BaseActivity<QuantitySetImp> implements QuantitySetConnect.QuantityView,HaiDialogUtil.TruckResultListener {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.quantity_logistic_productText)
    TextView productText;
    @BindView(R.id.quantity_logistic_companyText)
    TextView companyText;
    @BindView(R.id.quantity_logistic_diverText)
    TextView diverText;
    @BindView(R.id.quantity_logistic_carIdText)
    TextView carIdText;
    @BindView(R.id.quantity_logistic_listText)
    TextView listText;

    private String token;
    private List<ProductEntry> productEntries = new ArrayList<>();
    private ProductAdapter productAdapter;
    private CommonDialogFragment loDialog;
    private int loIndex = 0;
    //0物流产品 1物流公司 2承运司机 3车牌号 4 班次列表
    private int clickTag = 0;

    @Override
    protected void initView() {
        title.setText(getString(R.string.quantity_set));

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");

        Map<String, String> waysParams = new HashMap<>();
        waysParams.put("token", token);
        waysParams.put("timestamp", System.currentTimeMillis() + "");
        waysParams.put("sign", "");
        waysParams.put("sign", HaiTool.sign(waysParams));
        mPresenter.loProductMethod(waysParams);
    }

    @Override
    protected void initInjector() {
        mPresenter = new QuantitySetImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quantity_set;
    }
    @OnClick({R.id.common_title_back, R.id.quantity_logistic_product, R.id.quantity_logistic_company, R.id.quantity_logistic_diver, R.id.quantity_logistic_carId, R.id.quantity_logistic_list, R.id.quantity_logistic_complete})
    public void onViewClicked(View view) {
        loIndex = 0;
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.quantity_logistic_product:
                clickTag = 0;
                if (productAdapter != null){
                    loDialog = HaiDialogUtil.showTruck(getSupportFragmentManager(), productAdapter, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("未查询到物流产品信息，请稍后重试...");
                }
                break;
            case R.id.quantity_logistic_company:
                clickTag = 1;
                break;
            case R.id.quantity_logistic_diver:
                clickTag = 2;
                break;
            case R.id.quantity_logistic_carId:
                clickTag = 3;
                break;
            case R.id.quantity_logistic_list:
                ARouter.getInstance().build(PathConstant.QUANTITY_CLASS).navigation();
                break;
            case R.id.quantity_logistic_complete:
                finish();
                break;
        }
    }

    @Override
    public void loProductSuccess(List<ProductEntry> data) {
        if (data.size()>0){
            productEntries.addAll(data);
            productAdapter = new ProductAdapter(productEntries);
        }else {
            ZxToastUtil.centerToast("未查询到物流产品信息，请稍后重试...");
        }
    }

    @Override
    public void truckResult(int index) {
        if (index == -1){
            loDialog.dismissAllowingStateLoss();
            switch (clickTag){
                case 0:
                    ProductEntry productEntry = productEntries.get(loIndex);
                    productText.setText(productEntry.getDictValue());
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:break;
            }
        }else if (index == -2){
            loDialog.dismissAllowingStateLoss();
        }else {
            loIndex = index;
        }

    }
}
