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
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.GoodTypeWheel;
import com.zx.haijixing.driver.adapter.PriceAdapter;
import com.zx.haijixing.driver.contract.OrderDetailContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;
import com.zx.haijixing.driver.entry.OrderDetailEntry;
import com.zx.haijixing.driver.presenter.OrderDetailImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

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
public class OrderDetailActivity extends BaseActivity<OrderDetailImp> implements OrderDetailContract.OrderDetailView ,HaiDialogUtil.TruckResultListener,PriceAdapter.PriceChangeListener {

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
    @BindView(R.id.order_detail_sure_change)
    Button sureChange;

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

    @Autowired(name = "orderId")
    String orderId;
    @Autowired(name = "detailType")
    String detailType;
    @Autowired(name = "linesId")
    String linesId;


    private String token;

    private List<OrderDetailEntry.AppWaybillGoodsVo> appWaybillGoodsVos = new ArrayList<>();

    private AViewHolder aViewHolder;
    private BViewHolder bViewHolder;//底部
    private CViewHolder cViewHolder;//修改
    private DViewHolder dViewHolder;

    private GoodTypeWheel goodTypeWheel = null;
    private CommonDialogFragment showTruck;
    private int temp = 0;
    private List<GoodsTypePriceEntry.GoodType> goodTypeList;
    private Map<String,String> mathMap = new HashMap<>();
    private Map<String,String> upMap = new HashMap<>();
    private Map<String,String> params = new HashMap<>();
    private Map<String,String> detailParams = new HashMap<>();
    private String loginTpe;

    @Override
    protected void initView() {
        setTitleTopMargin(back);
        setTitleTopMargin(title);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");
        loginTpe = (String) instance.getParam("login_type", "null");
        int bg = Integer.parseInt(ZxStringUtil.isEmpty(detailType)?"0":detailType);
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
        params.put("category","");

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

    @OnClick({R.id.order_detail_back,R.id.order_detail_sure_change,R.id.order_detail_send_phone,R.id.order_detail_receive_phone})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.order_detail_back:
                finish();
                break;
            case R.id.order_detail_sure_change:
                params.put("timestamp",System.currentTimeMillis()+"");
                params.put("sign","");
                params.put("sign",HaiTool.sign(params));
                mPresenter.changeOrderMethod(params);
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
                    showTruck = HaiDialogUtil.showTruck(getSupportFragmentManager(), goodTypeWheel, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("请稍候重试");
                }
                break;

        }
    }
    private void call(String phone){
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
        for (OrderDetailEntry.AppWaybillGoodsVo goodsVo:goods){
            mathMap.put(goodsVo.getDictCode(),goodsVo.getDwMoney()+","+goodsVo.getDwAvg());
            upMap.put(goodsVo.getDictCode(),goodsVo.getGoodsId());
        }
        mathMap.put("coupon",orderDetailEntry.getCouponMoney());
        mathMap.put("integral",orderDetailEntry.getIntegralMoney());
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
                sureChange.setVisibility(View.VISIBLE);
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
        finish();
    }

    @Override
    public void truckResult(int index) {
        if (index>-1){
            temp = index;
        }else if (index == -1){
            GoodsTypePriceEntry.GoodType goodType = goodTypeList.get(temp);
            mathMap.put("startMoney", goodType.getStartPrice()+"");
            params.put("category", goodType.getClassName());
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
            ZxLogUtil.logError("<appWaybillGoodsVo.getDictCode()>"+appWaybillGoodsVo.getDictCode()+"<weight>"+weight+"<split[0]>"+split[0]+"<split[1]>"+split[1]);
            double dPrice = Double.parseDouble(ZxStringUtil.isEmpty(price)?"0":price);
            double dWeight = Double.parseDouble(ZxStringUtil.isEmpty(weight)?"0":weight);
            double number = Double.parseDouble(ZxStringUtil.isEmpty(dwValue)?"0":dwValue);
            totalMoney = dPrice+totalMoney;
            totalWeight = dWeight+totalWeight;
            totalNumber = number+totalNumber;
            valueArray.append(goodId+","+dwValue+","+split[0]+","+split[1]+":");
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
        cViewHolder.inputFree.setText(totalMoney/100+"");
        cViewHolder.cWeight.setText(totalWeight+"KG");

        String trim = cViewHolder.inputFree.getText().toString().trim();

        double fMoney =Double.parseDouble(ZxStringUtil.isEmpty(trim) ? "0" : trim);
        int intTotalMoney = new Double(fMoney*100).intValue();
        int intTotalNumber = new Double(totalNumber).intValue();
        ZxLogUtil.logError("<<<<totalWeight"+totalWeight+"<<<<intTotalMoney"+intTotalMoney+"<<<<intTotalNumber"+intTotalNumber);
        int coupon = Integer.parseInt(ZxStringUtil.isEmpty(mathMap.get("coupon"))?"0":mathMap.get("coupon"));
        int integral = Integer.parseInt(ZxStringUtil.isEmpty(mathMap.get("integral"))?"0":mathMap.get("integral"));

        params.put("weight",totalWeight+"");
        params.put("totalNum",intTotalNumber+"");
        params.put("price",(intTotalMoney-coupon-integral)+"");
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
}
