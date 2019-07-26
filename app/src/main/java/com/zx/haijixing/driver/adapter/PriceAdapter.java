package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.OrderDetailEntry;

import java.util.List;

import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 18:29
 *@描述 修改订单的修改价格
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private List<OrderDetailEntry.AppWaybillGoodsVo> appWaybillGoodsVos;
    private PriceChangeListener priceChangeListener;

    public PriceAdapter(List<OrderDetailEntry.AppWaybillGoodsVo> appWaybillGoodsVos) {
        this.appWaybillGoodsVos = appWaybillGoodsVos;
    }

    public void setPriceChangeListener(PriceChangeListener priceChangeListener) {
        this.priceChangeListener = priceChangeListener;
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_viewstub_change, viewGroup, false);
        PriceViewHolder priceViewHolder = new PriceViewHolder(inflate);
        return priceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder priceViewHolder, int i) {
        if (appWaybillGoodsVos.size()>0){
            OrderDetailEntry.AppWaybillGoodsVo appWaybillGoodsVo = appWaybillGoodsVos.get(i);
            priceViewHolder.type.setText(appWaybillGoodsVo.getDwName());
            if ("0".equals(appWaybillGoodsVo.getDwValue())){
                priceViewHolder.sub.setImageResource(R.mipmap.subtract_no);
            }else {
                priceViewHolder.sub.setImageResource(R.mipmap.subtract_can);
            }
            priceViewHolder.num.setText(appWaybillGoodsVo.getDwValue());
            priceViewHolder.add.setOnClickListener(v -> {
                String trim = priceViewHolder.num.getText().toString().trim();
                int c = Integer.parseInt(ZxStringUtil.isEmpty(trim)?"0":trim);
                priceViewHolder.num.setText(c+1+"");
                priceViewHolder.sub.setImageResource(R.mipmap.subtract_can);
            });
            priceViewHolder.sub.setOnClickListener(v ->{
                String trim = priceViewHolder.num.getText().toString().trim();
                int c = Integer.parseInt(ZxStringUtil.isEmpty(trim)?"0":trim);

                if (c <= 0){
                    priceViewHolder.sub.setImageResource(R.mipmap.subtract_no);
                    priceViewHolder.num.setText("0");
                }else {
                    c--;
                    if (c == 0)
                        priceViewHolder.sub.setImageResource(R.mipmap.subtract_no);
                    priceViewHolder.num.setText(c+"");
                }
            });
            priceViewHolder.num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (ZxStringUtil.isEmpty(s)){
                        appWaybillGoodsVo.setDwValue("0");
                    }else {
                        int i1 = Integer.parseInt(s.toString());
                        if (i1>=0){
                            appWaybillGoodsVo.setDwValue(s.toString());
                        }else {
                            priceViewHolder.num.setText("0");
                            appWaybillGoodsVo.setDwValue("0");
                        }
                    }
                    priceChangeListener.priceChangeResult();
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return appWaybillGoodsVos.size();
    }

    public interface PriceChangeListener{
        void priceChangeResult();
    }

    class PriceViewHolder extends RecyclerView.ViewHolder{
        ImageView add,sub;
        EditText num;
        TextView type;
        public PriceViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.viewstub_change_add);
            num = itemView.findViewById(R.id.viewstub_change_count);
            sub = itemView.findViewById(R.id.viewstub_change_subtract);
            type = itemView.findViewById(R.id.viewstub_change_type);
        }
    }
}
