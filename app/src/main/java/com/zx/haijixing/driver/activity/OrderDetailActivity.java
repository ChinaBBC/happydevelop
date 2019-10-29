package com.zx.haijixing.driver.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.GoodTypeWheel;
import com.zx.haijixing.driver.adapter.PriceAdapter;
import com.zx.haijixing.driver.contract.OrderDetailContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;
import com.zx.haijixing.driver.entry.OrderDetailEntry;
import com.zx.haijixing.driver.fragment.SendingFragment;
import com.zx.haijixing.driver.presenter.OrderDetailImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/4 14:41
 * @描述 订单详情
 */
@Route(path = PathConstant.DRIVER_ORDER_DETAIL)
public class OrderDetailActivity extends BaseActivity<OrderDetailImp> implements OrderDetailContract.OrderDetailView
        ,HaiDialogUtil.TruckResultListener,PriceAdapter.PriceChangeListener,HaiDialogUtil.PayMoneyResultListener {

    @BindView(R.id.order_detail_back)
    ImageView back;
    @BindView(R.id.order_detail_title)
    TextView title;
    @BindView(R.id.order_detail_start)
    TextView start;
    @BindView(R.id.order_detail_sTime)
    TextView sTime;
    @BindView(R.id.order_detail_duration)
    TextView duration;
    @BindView(R.id.order_detail_end)
    TextView end;
    @BindView(R.id.order_detail_eTime)
    TextView eTime;
    @BindView(R.id.order_detail_number)
    TextView number;
    @BindView(R.id.order_detail_cTime)
    TextView cTime;
    @BindView(R.id.order_detail_send_man)
    TextView sendMan;
    @BindView(R.id.order_detail_send_phone)
    TextView sendPhone;
    @BindView(R.id.order_detail_send_locate)
    TextView sendLocate;
    @BindView(R.id.order_detail_receive_man)
    TextView receiveMan;
    @BindView(R.id.order_detail_receive_phone)
    TextView receivePhone;
    @BindView(R.id.order_detail_receive_locate)
    TextView receiveLocate;
   /* @BindView(R.id.order_detail_sure_change)
    Button sureChange;*/

    @BindView(R.id.stub_normal_dan)
    ViewStub stubNormalDan;
    @BindView(R.id.stub_change_dan)
    ViewStub stubChangeDan;
    @BindView(R.id.stub_normal_down)
    ViewStub stubNormalDown;
    @BindView(R.id.stub_normal_down_b)
    ViewStub stubNormalDownB;
    @BindView(R.id.stub_has_complete)
    ViewStub stubHasComplete;
    @BindView(R.id.stub_some_buttons)
    ViewStub stubButtons;

    @Autowired(name = "orderId")
    String orderId;
    @Autowired(name = "detailType")
    String detailType;
    @Autowired(name = "linesId")
    String linesId;
    @Autowired(name = "priceFlag")
    String priceFlag;
    @Autowired(name = "modify")
    String modify;


    private String token;

    private List<OrderDetailEntry.AppWaybillGoodsVo> appWaybillGoodsVos = new ArrayList<>();

    private AViewHolder aViewHolder;
    private BViewHolder bViewHolder;//底部
    private CViewHolder cViewHolder;//修改
    private DViewHolder dViewHolder;
    private EViewHolder eViewHolder;//按钮们

    private GoodTypeWheel goodTypeWheel = null;
    private CommonDialogFragment showTruck;
    private int temp = 0;
    private int showTag = 0;
    private List<GoodsTypePriceEntry.GoodType> goodTypeList;
    private Map<String,String> mathMap = new HashMap<>();
    private Map<String,String> upMap = new HashMap<>();
    private Map<String,String> params = new HashMap<>();
    private Map<String,String> detailParams = new HashMap<>();
    private String loginTpe;
    private int payType=1;
    private CommonDialogFragment showPay;
    private CommonDialogFragment showChangeMoney;
    private ArrayList<String> permissions;
    private CommonDialogFragment sendComplete;

    @Override
    protected void initView() {
        setTitleTopMargin(back);
        setTitleTopMargin(title);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");
        loginTpe = (String) instance.getParam("login_type", "null");
        permissions = (ArrayList<String>) instance.getParam("limit",null);

        int bg = Integer.parseInt(ZxStringUtil.isEmpty(detailType)?"0":detailType);

        String offline = (String) instance.getParam("offline", "0");
        String online = (String) instance.getParam("online", "0");

        if (offline.equals("1")){
            showTag = 2;
        }else if (online.equals("1")){
            showTag = 1;
            payType = 2;
        }
        switch (bg){
            case OtherConstants.DETAIL_WAIT_ALLOT:
                title.setText(getHaiString(R.string.wait_allot_order));
                break;
            case OtherConstants.DETAIL_WAIT_RECEIVE:
                title.setText(getHaiString(R.string.wait_receive));
                break;
            case OtherConstants.DETAIL_WAIT_SEND:
                title.setText(getHaiString(R.string.wait_send));
                break;
            case OtherConstants.DETAIL_SENDING:
                title.setText(getHaiString(R.string.sending));
                break;
            case OtherConstants.DETAIL_CANCEL:
                title.setText(getHaiString(R.string.has_cancel));
                break;
            case OtherConstants.DETAIL_COMPLETE:
                title.setText(getHaiString(R.string.complete));
                break;
            case OtherConstants.ORDER_INFO:
                title.setText(getHaiString(R.string.order_detail));
                break;
            case OtherConstants.CHANGE_ORDER:
                title.setText(getHaiString(R.string.change_order));
                Map<String,String> changeMap = new HashMap<>();
                changeMap.put("token",token);
                changeMap.put("lineMasterId",linesId);
                changeMap.put("timestamp",System.currentTimeMillis()+"");
                changeMap.put("sign",HaiTool.sign(changeMap));
                mPresenter.goodTypePriceMethod(changeMap);
                break;
        }
        params.put("token",token);
        params.put("waybillId",orderId);


        detailParams.put("token",token);
        if (orderId.length()>20){
            detailParams.put("waybillId",orderId);
        }else {
            detailParams.put("waybillNo",orderId);
        }
        detailParams.put("timestamp",System.currentTimeMillis()+"");
        detailParams.put("sign",HaiTool.sign(detailParams));
        mPresenter.orderDetailMethod(detailParams);
    }

    @Override
    protected void initInjector() {
        mPresenter = new OrderDetailImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @OnClick({R.id.order_detail_back,R.id.order_detail_send_phone,R.id.order_detail_receive_phone})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.order_detail_back:
                finish();
                break;
            case R.id.order_detail_send_phone:
                call(sendPhone.getText().toString().trim());
                break;
            case R.id.order_detail_receive_phone:
                call(receivePhone.getText().toString().trim());
                break;
            case R.id.change_dan_down:
            case R.id.change_dan_cType:
                if (goodTypeWheel != null) {
                    temp = 0;
                    showTruck = HaiDialogUtil.showTruck(getSupportFragmentManager(), goodTypeWheel, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("请稍候重试");
                }
                break;
            case R.id.dialog_pay_no:
                showPay.dismissAllowingStateLoss();
                break;
            case R.id.dialog_pay_yes:
                showPay.dismissAllowingStateLoss();
                complete();
                break;
            case R.id.dialog_update_no:
                sendComplete.dismissAllowingStateLoss();
                break;
            case R.id.dialog_update_yes:
                sendComplete.dismissAllowingStateLoss();
                sendCompleteMethod();
                break;

        }
    }
    //出发
    private void complete() {
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", "0");
        param.put("waybillIds",orderId);
        param.put("payType",payType+"");
        param.put("timestamp",System.currentTimeMillis()+"");
        param.put("sign","");
        param.put("sign",HaiTool.sign(param));
        mPresenter.surePayMethod(param);

    }
    private void sendCompleteMethod(){
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", "0");
        param.put("waybillIds", orderId);
        param.put("timestamp",System.currentTimeMillis()+"");
        param.put("sign","");
        param.put("sign",HaiTool.sign(param));
        mPresenter.completeMethod(param);
    }
    private void call(String phone){
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(call);
    }
    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public void orderDetailSuccess(OrderDetailEntry orderDetailEntry) {
        mathMap.put("startMoney",orderDetailEntry.getStartPrice());
        List<OrderDetailEntry.AppWaybillGoodsVo> goods = orderDetailEntry.getGoods();
        appWaybillGoodsVos.addAll(goods);
        StringBuilder valueArray = new StringBuilder();
        for (OrderDetailEntry.AppWaybillGoodsVo goodsVo:goods){
            mathMap.put(goodsVo.getDictCode(),goodsVo.getDwMoney()+","+goodsVo.getDwAvg());
            upMap.put(goodsVo.getDictCode(),goodsVo.getGoodsId());
            valueArray.append(goodsVo.getGoodsId()+","+goodsVo.getDwValue()+","+goodsVo.getDwMoney()+","+goodsVo.getDwAvg()+","+goodsVo.getGtId()+":");
        }
        String goodsArray = valueArray.toString().substring(0, valueArray.length() - 1);

        mathMap.put("coupon",orderDetailEntry.getCouponMoney());
        mathMap.put("integral",orderDetailEntry.getIntegralMoney());

        params.put("category",orderDetailEntry.getCategory());
        params.put("categoryId", orderDetailEntry.getCategoryId());
        params.put("weight",orderDetailEntry.getWeight());
        params.put("totalNum",orderDetailEntry.getTotalNum()+"");
        double v = Double.parseDouble((ZxStringUtil.isEmpty(orderDetailEntry.getPrice()) ? "0" : orderDetailEntry.getPrice())) * 100;
        int p = new Double(v).intValue();
        params.put("price", p +"");
        params.put("goodsArray",goodsArray);

        String timeEnd = orderDetailEntry.getTimeEnd();
        long timeStamp = Long.parseLong(ZxStringUtil.isEmpty(timeEnd)?"0":timeEnd);
        duration.setText(HaiTool.calculateTime(timeStamp));

        start.setText(orderDetailEntry.getLineStartName());
        end.setText(orderDetailEntry.getLineEndName());
        sTime.setText(orderDetailEntry.getLineStartTime());
        eTime.setText(orderDetailEntry.getLineEndTime());
        number.setText("运单号："+orderDetailEntry.getWaybillNo());
        cTime.setText(orderDetailEntry.getCreateTime());
        sendMan.setText(orderDetailEntry.getSenderName());
        sendPhone.setText(orderDetailEntry.getSenderPhone());
        sendLocate.setText(orderDetailEntry.getSenderAddress());
        receiveMan.setText(orderDetailEntry.getIncomeName());
        receivePhone.setText(orderDetailEntry.getIncomePhone());
        receiveLocate.setText(orderDetailEntry.getIncomeAddress());
        int bg = Integer.parseInt(ZxStringUtil.isEmpty(detailType)?"0":detailType);
        eMethod(orderDetailEntry,bg);
        switch (bg){
            case OtherConstants.DETAIL_WAIT_ALLOT:
                aViewHolder = new AViewHolder(stubNormalDan.inflate());
                aMethod(orderDetailEntry);
                break;
            case OtherConstants.DETAIL_WAIT_RECEIVE:
                aViewHolder = new AViewHolder(stubNormalDan.inflate());
                aMethod(orderDetailEntry);
                if (!OtherConstants.LOGIN_DRIVER.equals(loginTpe)){
                    dViewHolder = new DViewHolder(stubHasComplete.inflate());
                    dMethod(orderDetailEntry);
                }
                break;
            case OtherConstants.DETAIL_WAIT_SEND:
            case OtherConstants.DETAIL_SENDING:
            case OtherConstants.DETAIL_CANCEL:
            case OtherConstants.ORDER_INFO:
                aViewHolder = new AViewHolder(stubNormalDan.inflate());
                bViewHolder = new BViewHolder(stubNormalDownB.inflate());
                aMethod(orderDetailEntry);
                bMethod(orderDetailEntry);
                break;
            case OtherConstants.DETAIL_COMPLETE:
                aViewHolder = new AViewHolder(stubNormalDan.inflate());
                bViewHolder = new BViewHolder(stubNormalDownB.inflate());
                dViewHolder = new DViewHolder(stubHasComplete.inflate());
                aMethod(orderDetailEntry);
                bMethod(orderDetailEntry);
                dMethod(orderDetailEntry);
                break;
            case OtherConstants.CHANGE_ORDER:
                cViewHolder = new CViewHolder(stubChangeDan.inflate());
                bViewHolder = new BViewHolder(stubNormalDown.inflate());
                //sureChange.setVisibility(View.VISIBLE);
                cMethod(orderDetailEntry);
                bMethod(orderDetailEntry);
                break;
        }
    }

    @Override
    public void goodTypePriceSuccess(GoodsTypePriceEntry goodsTypePriceEntry) {
        mathMap.put("standard",goodsTypePriceEntry.getStaticPrice());
        mathMap.put("dy",goodsTypePriceEntry.getDyPrice());
        mathMap.put("xy",goodsTypePriceEntry.getXyPrice());
        goodTypeList = goodsTypePriceEntry.getData();
        goodTypeWheel = new GoodTypeWheel(goodTypeList);
    }

    @Override
    public void changeOrderMethodSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        EventBus.getDefault().post(new EventBusEntity(OtherConstants.ALLOT_REQUEST));
        finish();
    }

    @Override
    public void sureMoneySuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        eViewHolder.complete.setVisibility(View.GONE);
    }

    @Override
    public void receiveOrderSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        eViewHolder.resend.setVisibility(View.GONE);
    }

    @Override
    public void completeSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        eViewHolder.complete.setVisibility(View.GONE);
        EventBus.getDefault().post(new EventBusEntity(OtherConstants.STATUS_CHANGE));
    }

    @Override
    public void surePaySuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        eViewHolder.complete.setText("配送完成");
        priceFlag = "1";
        EventBus.getDefault().post(new EventBusEntity(OtherConstants.STATUS_CHANGE));
       /* Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", "0");
        param.put("waybillIds", orderId);
        param.put("timestamp",System.currentTimeMillis()+"");
        param.put("sign","");
        param.put("sign",HaiTool.sign(param));
        mPresenter.completeMethod(param);*/
    }

    @Override
    public void changePriceSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        finish();
    }

    @Override
    public void truckResult(int index) {
        payType = index;
        if (index>-1){
            temp = index;
        }else if (index == -1){
            GoodsTypePriceEntry.GoodType goodType = goodTypeList.get(temp);
            mathMap.put("startMoney", goodType.getStartPrice()+"");
            params.put("category", goodType.getClassName());
            params.put("categoryId", goodType.getCategory());
            List<GoodsTypePriceEntry.GoodType.LinePriceGtVo> linePriceGtVos = goodType.getLinePriceGtVos();
            for (GoodsTypePriceEntry.GoodType.LinePriceGtVo linePriceGtVo : linePriceGtVos){
                ZxLogUtil.logError(linePriceGtVo.getDwMoney()+","+linePriceGtVo.getDwAvg()+","+linePriceGtVo.getDictCode());
                mathMap.put(linePriceGtVo.getDictCode(),linePriceGtVo.getDwMoney()+","+linePriceGtVo.getDwAvg());
            }
            showTruck.dismissAllowingStateLoss();
            cViewHolder.cType.setText(goodType.getClassName());
            doCalculate();

        }else if (index == -2){
            showTruck.dismissAllowingStateLoss();
        }
    }


    @Override
    public void priceChangeResult() {
        doCalculate();
    }

    private void doCalculate(){
        double totalMoney = 0;
        double totalWeight = 0;
        double totalNumber = 0;
        StringBuilder valueArray = new StringBuilder();
        for (OrderDetailEntry.AppWaybillGoodsVo appWaybillGoodsVo : appWaybillGoodsVos){
            String dwValue = appWaybillGoodsVo.getDwValue();
            String priceAndWeight = mathMap.get(appWaybillGoodsVo.getDictCode());
            String goodId = upMap.get(appWaybillGoodsVo.getDictCode());
            String[] split = priceAndWeight.split(",");
            String price = ZxStringUtil.multiplication(dwValue, split[0], 2);
            String weight = ZxStringUtil.multiplication(dwValue, split[1], 2);
            ZxLogUtil.logError("<appWaybillGoodsVo.getDictCode()>"+appWaybillGoodsVo.getDictCode()+"<weight>"+weight+"<price>"+price+"<dwValue>"+dwValue+"<split[0]>"+split[0]+"<split[1]>"+split[1]);
            double dPrice = Double.parseDouble(ZxStringUtil.isEmpty(price)?"0":price);
            double dWeight = Double.parseDouble(ZxStringUtil.isEmpty(weight)?"0":weight);
            double number = Double.parseDouble(ZxStringUtil.isEmpty(dwValue)?"0":dwValue);
            totalMoney = dPrice+totalMoney;
            totalWeight = dWeight+totalWeight;
            totalNumber = number+totalNumber;
            valueArray.append(goodId+","+dwValue+","+split[0]+","+split[1]+","+appWaybillGoodsVo.getGtId()+":");
        }
        String goodsArray = valueArray.toString().substring(0, valueArray.length() - 1);

        ZxLogUtil.logError("<goodsArray>"+goodsArray);
        double startMoney = Double.parseDouble(ZxStringUtil.isEmpty(mathMap.get("startMoney"))?"0":mathMap.get("startMoney") );
        if (totalMoney <= startMoney){
            totalMoney = startMoney;
        }else {
            double standard = Double.parseDouble(ZxStringUtil.isEmpty(mathMap.get("standard"))?"0":mathMap.get("standard"));
            if (startMoney <= standard){
                double xy = Double.parseDouble(ZxStringUtil.isEmpty(mathMap.get("xy"))?"0":mathMap.get("xy"));
                totalMoney = (totalMoney-startMoney)*xy/10+startMoney;
            }else {
                double dy = Double.parseDouble(ZxStringUtil.isEmpty(mathMap.get("dy"))?"0":mathMap.get("dy"));
                totalMoney = (totalMoney-startMoney)*dy/10+startMoney;
            }
        }

        cViewHolder.cWeight.setText(totalWeight+"KG");

        //String trim = cViewHolder.inputFree.getText().toString().trim();

        //double fMoney =Double.parseDouble(ZxStringUtil.isEmpty(totalMoney) ? "0" : trim);
        int intTotalMoney = new Double(totalMoney).intValue();
        int intTotalNumber = new Double(totalNumber).intValue();
        int coupon = Integer.parseInt(ZxStringUtil.isEmpty(mathMap.get("coupon"))?"0":mathMap.get("coupon"));
        int integral = Integer.parseInt(ZxStringUtil.isEmpty(mathMap.get("integral"))?"0":mathMap.get("integral"));

        int finalMoney = intTotalMoney-coupon-integral;

        cViewHolder.inputFree.setText((finalMoney/100.00)+"");

        params.put("weight",totalWeight+"");
        params.put("totalNum",intTotalNumber+"");
        //params.put("price",(intTotalMoney-coupon-integral)+"");
        params.put("goodsArray",goodsArray);
    }

    //普通
    private void aMethod(OrderDetailEntry orderDetailEntry){
        String content = orderDetailEntry.getRemark();
        aViewHolder.remark.setText(ZxStringUtil.isEmpty(content)?"暂无备注":content);

        aViewHolder.wType.setText(orderDetailEntry.getCategory());
        aViewHolder.totalInfo.setText(getDetailGoods());
        aViewHolder.wWeight.setText(orderDetailEntry.getWeight()+"KG");
        aViewHolder.wFreight.setText("￥"+orderDetailEntry.getPrice()+"("+("1".equals(orderDetailEntry.getType())?"寄付)":"到付)"));
        aViewHolder.wTotal.setText(orderDetailEntry.getTotalNum()+"件");
    }

    private void bMethod(OrderDetailEntry orderDetailEntry){
        bViewHolder.carNumber.setText(orderDetailEntry.getIdcard());
        bViewHolder.driverInfo.setText(orderDetailEntry.getDriverName()+" "+orderDetailEntry.getDriverPhone());
        bViewHolder.loCompany.setText(orderDetailEntry.getLgsName());
        bViewHolder.loWay.setText(orderDetailEntry.getProductName());
        bViewHolder.payWay.setText("1".equals(orderDetailEntry.getType())?"寄付":"到付");

        String content = orderDetailEntry.getRemark();
        bViewHolder.remark.setText(ZxStringUtil.isEmpty(content)?"暂无备注":content);

        bViewHolder.rTime.setText(orderDetailEntry.getReceiptTime());
        bViewHolder.gTime.setText(orderDetailEntry.getGoTime());
        bViewHolder.doneTime.setText(orderDetailEntry.getDoneTime());
        bViewHolder.vipservice.setText(orderDetailEntry.getAddedServices());
    }

    //修改订单
    private void cMethod(OrderDetailEntry orderDetailEntry){
        cViewHolder.cMoney.setText("￥"+orderDetailEntry.getPrice()+"("+("1".equals(orderDetailEntry.getType())?"寄付)":"到付)"));
        cViewHolder.cType.setText(orderDetailEntry.getCategory());
        cViewHolder.cType.setOnClickListener(this::onViewClicked);
        cViewHolder.down.setOnClickListener(this::onViewClicked);

        cViewHolder.cWeight.setText(orderDetailEntry.getWeight()+"KG");
        cViewHolder.countList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        PriceAdapter priceAdapter = new PriceAdapter(appWaybillGoodsVos);
        priceAdapter.setPriceChangeListener(this::priceChangeResult);
        cViewHolder.countList.setAdapter(priceAdapter);
        cViewHolder.inputFree.setText(orderDetailEntry.getPrice());
        //priceInput = cViewHolder.inputFree.getText().toString();
    }

    private void dMethod(OrderDetailEntry orderDetailEntry){
        String content = orderDetailEntry.getContent();
        dViewHolder.remark.setText(ZxStringUtil.isEmpty(content)?"暂无评语":content);

        dViewHolder.onTime.setText(orderDetailEntry.getTimelyGrade());
        dViewHolder.manner.setText(orderDetailEntry.getAttitudeGrade());
        dViewHolder.intact.setText(orderDetailEntry.getCompleteGrade());
        dViewHolder.score.setText(orderDetailEntry.getAllGrade());
    }

    private void eMethod(OrderDetailEntry orderEntry,int bg){
        switch (bg){
            case OtherConstants.DETAIL_WAIT_ALLOT:
                eViewHolder = new EViewHolder(stubButtons.inflate());
                eViewHolder.resend.setOnClickListener(v ->{
                    ARouter.getInstance().build(PathConstant.ALLOT)
                            .withString("orderId",orderEntry.getWaybillId())
                            .withString("linesId",linesId)
                            .navigation();
                    finish();
                });
                if ("0".equals(modify)){
                    eViewHolder.change.setOnClickListener(v -> {
                        ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                                .withString("orderId",orderEntry.getWaybillId())
                                .withString("detailType",OtherConstants.CHANGE_ORDER+"")
                                .withString("linesId",linesId)
                                .navigation();
                        finish();
                    });
                    if (permissions != null)
                        eViewHolder.change.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_ORDER)?View.VISIBLE:View.GONE);
                }else {
                    eViewHolder.change.setVisibility(View.GONE);
                }
                if (orderEntry.getType().equals("1") && priceFlag.equals("0")){
                    eViewHolder.complete.setVisibility(View.VISIBLE);
                    eViewHolder.complete.setOnClickListener(v -> {
                        Map<String,String> moneyParams = new HashMap<>();
                        moneyParams.put("token",token);
                        moneyParams.put("waybillId",orderEntry.getWaybillId());
                        moneyParams.put("timestamp",System.currentTimeMillis()+"");
                        moneyParams.put("sign","");
                        moneyParams.put("sign",HaiTool.sign(moneyParams));
                        mPresenter.sureMoneyMethod(moneyParams);
                    });
                }else {
                    eViewHolder.complete.setVisibility(View.GONE);
                }

                break;
            case OtherConstants.DETAIL_WAIT_RECEIVE:
                eViewHolder = new EViewHolder(stubButtons.inflate());
                if (OtherConstants.LOGIN_DRIVER.equals(loginTpe)){
                    eViewHolder.resend.setText("接单");
                    eViewHolder.complete.setVisibility(View.GONE);
                    eViewHolder.change.setVisibility(View.GONE);
                    eViewHolder.resend.setOnClickListener(v->{
                        Map<String,String> param = new HashMap<>();
                        param.put("token",token);
                        param.put("selectAllFlag","0");
                        param.put("waybillIds",orderEntry.getWaybillId());
                        param.put("timestamp",System.currentTimeMillis()+"");
                        param.put("sign","");
                        param.put("sign",HaiTool.sign(param));
                        mPresenter.receiveOrderMethod(param);
                    });
                }else {
                    eViewHolder.resend.setText("改派");
                    eViewHolder.resend.setOnClickListener(v ->{
                        ARouter.getInstance().build(PathConstant.ALLOT)
                                .withString("orderId",orderEntry.getWaybillId())
                                .withString("linesId",linesId)
                                .navigation();
                        finish();
                    });
                    if ("0".equals(modify)){
                        eViewHolder.change.setOnClickListener(v -> {
                            ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                                    .withString("orderId",orderEntry.getWaybillId())
                                    .withString("detailType",OtherConstants.CHANGE_ORDER+"")
                                    .withString("linesId",linesId)
                                    .navigation();
                            finish();
                        });
                        if (permissions != null)
                            eViewHolder.change.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_ORDER)?View.VISIBLE:View.GONE);
                    }else {
                        eViewHolder.change.setVisibility(View.GONE);
                    }
                    if (orderEntry.getType().equals("1") && priceFlag.equals("0")){
                        eViewHolder.complete.setVisibility(View.VISIBLE);
                        eViewHolder.complete.setOnClickListener(v -> {
                            Map<String,String> moneyParams = new HashMap<>();
                            moneyParams.put("token",token);
                            moneyParams.put("waybillId",orderEntry.getWaybillId());
                            moneyParams.put("timestamp",System.currentTimeMillis()+"");
                            moneyParams.put("sign","");
                            moneyParams.put("sign",HaiTool.sign(moneyParams));
                            mPresenter.sureMoneyMethod(moneyParams);
                        });
                    }else {
                        eViewHolder.complete.setVisibility(View.GONE);
                    }

                }
                break;
            case OtherConstants.DETAIL_WAIT_SEND:
                eViewHolder = new EViewHolder(stubButtons.inflate());
                eViewHolder.complete.setVisibility(View.GONE);
                if (OtherConstants.LOGIN_DRIVER.equals(loginTpe)){
                    eViewHolder.resend.setVisibility(View.GONE);
                }
                eViewHolder.resend.setText("改派");
                eViewHolder.resend.setOnClickListener(v ->{
                    ARouter.getInstance().build(PathConstant.ALLOT)
                            .withString("orderId",orderEntry.getWaybillId())
                            .withString("linesId",linesId)
                            .navigation();
                    finish();
                });
                if ("0".equals(modify)){
                    eViewHolder.change.setOnClickListener(v -> {
                        ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                                .withString("orderId",orderEntry.getWaybillId())
                                .withString("detailType",OtherConstants.CHANGE_ORDER+"")
                                .withString("linesId",linesId)
                                .navigation();
                        finish();
                    });
                    if (permissions != null)
                        eViewHolder.change.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_ORDER)?View.VISIBLE:View.GONE);
                }else {
                    eViewHolder.change.setVisibility(View.GONE);
                }

                break;
            case OtherConstants.DETAIL_SENDING:
                eViewHolder = new EViewHolder(stubButtons.inflate());
                eViewHolder.resend.setVisibility(View.GONE);
                if ("1".equals(modify)){
                    eViewHolder.change.setVisibility(View.GONE);
                }else {
                    if (permissions != null)
                        eViewHolder.change.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_MONEY)?View.VISIBLE:View.GONE);
                }
                if (OtherConstants.LOGIN_DRIVER.equals(loginTpe)){
                    eViewHolder.change.setText("修改运费");
                    eViewHolder.complete.setText("配送完成");
                    eViewHolder.complete.setText("0".equals(priceFlag)?"确认收款":"配送完成");
                    eViewHolder.change.setOnClickListener(v -> {
                        showChangeMoney = HaiDialogUtil.showChangeMoney(getSupportFragmentManager(), this::payResult,orderEntry.getPrice());
                    });
                    eViewHolder.complete.setOnClickListener(v -> {
                        if ("0".equals(priceFlag)){
                            int tempTag = 0;
                            if (showTag == 0 && !orderEntry.isCanOnlinePay()){
                                tempTag = 1;
                            }else {
                                tempTag = showTag;
                            }
                            showPay = HaiDialogUtil.showPay(getSupportFragmentManager(), tempTag, OrderDetailActivity.this::onViewClicked,this::truckResult);
                        } else{
                            sendComplete = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "是否确认配送完成？", this::onViewClicked);
                        }

                    });
                }else {
                    eViewHolder.change.setText("修改价格");
                    eViewHolder.complete.setText("查看物流");
                    eViewHolder.complete.setVisibility(View.GONE);
                    eViewHolder.change.setOnClickListener(v -> {
                        showChangeMoney = HaiDialogUtil.showChangeMoney(getSupportFragmentManager(), this::payResult,orderEntry.getPrice());
                    });
                    eViewHolder.complete.setOnClickListener(v -> {
                        ARouter.getInstance().build(PathConstant.CHECK_LOGISTICS)
                                .withString("waybillNo",orderEntry.getWaybillNo())
                                .navigation();
                    });
                }

                break;
            case OtherConstants.CHANGE_ORDER:
                eViewHolder = new EViewHolder(stubButtons.inflate());
                eViewHolder.change.setText("确认修改");
                eViewHolder.resend.setVisibility(View.GONE);
                eViewHolder.print.setVisibility(View.GONE);
                eViewHolder.complete.setVisibility(View.GONE);

                eViewHolder.change.setOnClickListener(v -> {
                    String trim = cViewHolder.inputFree.getText().toString().trim();
                    if (!ZxStringUtil.isEmpty(trim)){
                        if (HaiTool.isFastClick()){
                            ZxToastUtil.centerToast("正在修改，请稍候。。。");
                        }else {
                            double d = Double.parseDouble(trim);
                            int m = new Double(d*100).intValue();
                            params.put("price",m+"");
                            params.put("timestamp",System.currentTimeMillis()+"");
                            params.put("sign","");
                            params.put("sign",HaiTool.sign(params));
                            mPresenter.changeOrderMethod(params);
                        }
                    }else {
                        ZxToastUtil.centerToast("请输入价格！");
                    }
                });
                break;
        }
        if (eViewHolder != null){
            int dadanFlag = Integer.parseInt(ZxStringUtil.isEmpty(orderEntry.getDadanFlag())?"0":orderEntry.getDadanFlag());
            eViewHolder.print.setText(dadanFlag==0?"打单":"补打单");
            eViewHolder.print.setOnClickListener(v ->{
                        if ("1".equals(orderEntry.getType()) && "0".equals(priceFlag)){
                            ZxToastUtil.centerToast("未确认收款，无法打单");
                        }else {
                            ARouter.getInstance().build(PathConstant.PRINT)
                                    .withString("waybillId",orderEntry.getWaybillId())
                                    .withString("printStatus",orderEntry.getDadanFlag())
                                    .navigation();
                        }
                    }
            );
        }
    }

    private String getDetailGoods(){
        if (appWaybillGoodsVos.size()>0){
            StringBuilder idBuilder = new StringBuilder();
            for (OrderDetailEntry.AppWaybillGoodsVo appWaybillGoodsVo : appWaybillGoodsVos){
                idBuilder.append(appWaybillGoodsVo.getDwName()+"x"+appWaybillGoodsVo.getDwValue()+" ");
            }
            return idBuilder.toString();
        }
        return "0";
    }

    @Override
    public void payResult(String result) {
        if ("cancel".equals(result)){
            showChangeMoney.dismissAllowingStateLoss();
        }else if (ZxStringUtil.isEmpty(result)){
            ZxToastUtil.centerToast("请输入运费");
        }else {
            showChangeMoney.dismissAllowingStateLoss();
            Map<String,String> param = new HashMap<>();
            param.put("token",token);
            param.put("waybillId",orderId);
            param.put("realPrice",ZxStringUtil.multiplication(result,"100",0));
            param.put("timestamp",System.currentTimeMillis()+"");
            param.put("sign","");
            param.put("sign",HaiTool.sign(param));
            mPresenter.changePriceMethod(param);
        }
    }


    //普通
    class AViewHolder{
        @BindView(R.id.normal_dan_wType)
        TextView wType;
        @BindView(R.id.normal_dan_wWeight)
        TextView wWeight;
        @BindView(R.id.normal_dan_wFreight)
        TextView wFreight;
        @BindView(R.id.normal_dan_wTotal)
        TextView wTotal;
        @BindView(R.id.normal_dan_total_info)
        TextView totalInfo;
        @BindView(R.id.normal_dan_remark)
        TextView remark;
        public AViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    //修改
    class CViewHolder{
        @BindView(R.id.change_dan_cMoney)//钱
        TextView cMoney;
        @BindView(R.id.change_dan_cType)
        TextView cType;
        @BindView(R.id.change_dan_down)
        ImageView down;
        @BindView(R.id.change_dan_cWeight)
        TextView cWeight;
        @BindView(R.id.change_dan_count_list)
        RecyclerView countList;
        @BindView(R.id.change_dan_input_free)
        EditText inputFree;
        public CViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
    //底部
    class BViewHolder{
        @BindView(R.id.normal_down_rTime)
        TextView rTime;
        @BindView(R.id.normal_down_gTime)
        TextView gTime;
        @BindView(R.id.normal_down_doneTime)
        TextView doneTime;
        @BindView(R.id.normal_down_pay_way)
        TextView payWay;
        @BindView(R.id.normal_down_vip_service)
        TextView vipservice;
        @BindView(R.id.normal_down_logistics_way)
        TextView loWay;
        @BindView(R.id.normal_down_logistics_company)
        TextView loCompany;
        @BindView(R.id.normal_down_driver_info)
        TextView driverInfo;
        @BindView(R.id.normal_down_car_number)
        TextView carNumber;
        @BindView(R.id.normal_down_remark)
        TextView remark;
        public BViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
    //评价
    class DViewHolder{
        @BindView(R.id.has_complete_all_score)
        TextView score;
        @BindView(R.id.has_complete_service_manner)
        TextView manner;
        @BindView(R.id.has_complete_arrive_time)
        TextView onTime;
        @BindView(R.id.has_complete_goods_intact)
        TextView intact;
        @BindView(R.id.has_complete_remark)
        TextView remark;

        public DViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    class EViewHolder{
        @BindView(R.id.view_button_change)
        Button change;
        @BindView(R.id.view_button_print)
        Button print;
        @BindView(R.id.view_button_resend)
        Button resend;
        @BindView(R.id.view_button_complete)
        Button complete;

        public EViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
