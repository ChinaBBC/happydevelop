package com.zx.haijixing.driver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.EveryEvaluateEntry;
import com.zx.haijixing.driver.entry.TotalEvaluateEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 11:48
 *@描述 评价
 */
public class EvaluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEAD_ITEM = 0;
    private final static int BODY_ITEM = 1;

    private List<EveryEvaluateEntry> everyEvaluateEntries;

    private TotalEvaluateEntry totalEvaluateEntry;
    private String name;
    private String head;
    private Context context;

    public EvaluateAdapter(List<EveryEvaluateEntry> everyEvaluateEntries,String name,String head,Context context) {
        this.everyEvaluateEntries = everyEvaluateEntries;
        this.name = name;
        this.head = head;
        this.context =context;
    }

    public void setTotalEvaluateEntry(TotalEvaluateEntry totalEvaluateEntry) {
        this.totalEvaluateEntry = totalEvaluateEntry;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        return BODY_ITEM;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (i == HEAD_ITEM){
            View head = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evaluate_header, viewGroup, false);
            viewHolder = new EvaluateHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evaluate_data, viewGroup, false);
            viewHolder = new EvaluateViewHolder(body);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == HEAD_ITEM){
            if (totalEvaluateEntry != null){
                EvaluateHeadViewHolder evaluateHeadViewHolder = (EvaluateHeadViewHolder) viewHolder;
                evaluateHeadViewHolder.intact.setText(totalEvaluateEntry.getGoodsScore());
                evaluateHeadViewHolder.score.setText(totalEvaluateEntry.getAvgScore());
                evaluateHeadViewHolder.manner.setText(totalEvaluateEntry.getServerScore());
                evaluateHeadViewHolder.time.setText(totalEvaluateEntry.getLogScore());
                RequestOptions options = new RequestOptions().circleCrop().error(R.mipmap.user_head);
                Glide.with(context).load(head).apply(options).into(evaluateHeadViewHolder.head);
                evaluateHeadViewHolder.name.setText(name);
            }
        }else {
            if (everyEvaluateEntries.size()>0){
                int realPosition = getRealPosition(viewHolder);
                EveryEvaluateEntry everyEvaluateEntry = everyEvaluateEntries.get(realPosition);
                EvaluateViewHolder evaluateViewHolder = (EvaluateViewHolder) viewHolder;
                evaluateViewHolder.words.setText(everyEvaluateEntry.getRemark());
                evaluateViewHolder.intact.setText(everyEvaluateEntry.getGoodsScore());
                evaluateViewHolder.manner.setText(everyEvaluateEntry.getServiceScore());
                evaluateViewHolder.time.setText(everyEvaluateEntry.getLogScore());
                evaluateViewHolder.start.setText(everyEvaluateEntry.getStartName());
                evaluateViewHolder.end.setText(everyEvaluateEntry.getEndName());
                evaluateViewHolder.quantity.setText(everyEvaluateEntry.getPriceName()+"x"+everyEvaluateEntry.getGoodsNum());
                evaluateViewHolder.number.setText("运单号："+everyEvaluateEntry.getWaybillNo());
            }
        }
    }

    @Override
    public int getItemCount() {
        return everyEvaluateEntries.size()+1;
    }

    public int getRealPosition(RecyclerView.ViewHolder viewHolder){
        int p = viewHolder.getLayoutPosition();
        return p-1;
    }

    class EvaluateViewHolder extends RecyclerView.ViewHolder{

        TextView number,start,end,quantity,manner,time,intact,words;
        public EvaluateViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.evaluate_data_number);
            start = itemView.findViewById(R.id.evaluate_data_start);
            end = itemView.findViewById(R.id.evaluate_data_end);
            quantity = itemView.findViewById(R.id.evaluate_data_quantity);
            manner = itemView.findViewById(R.id.evaluate_data_service_manner);
            time = itemView.findViewById(R.id.evaluate_data_arrive_time);
            intact = itemView.findViewById(R.id.evaluate_data_goods_intact);
            words = itemView.findViewById(R.id.evaluate_some_words);
        }
    }

    class EvaluateHeadViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView head;
        TextView score,manner,time,intact;
        public EvaluateHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.evaluate_user_head);
            name = itemView.findViewById(R.id.evaluate_user_name);
            score = itemView.findViewById(R.id.evaluate_all_score);
            manner = itemView.findViewById(R.id.evaluate_service_manner);
            time = itemView.findViewById(R.id.evaluate_arrive_time);
            intact = itemView.findViewById(R.id.evaluate_goods_intact);
        }
    }
}
