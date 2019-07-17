package com.zx.haijixing.logistics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zx.haijixing.R;
import com.zx.haijixing.util.HaiDialogUtil;


/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 17:20
 *@描述 班次管理
 */
public class ClaManageAdapter extends RecyclerView.Adapter<ClaManageAdapter.ClaManageViewHolder> {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ClaManageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_cla_manage_data, viewGroup, false);
        ClaManageViewHolder claManageViewHolder = new ClaManageViewHolder(inflate);
        return claManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClaManageViewHolder claManageViewHolder, int i) {
        claManageViewHolder.editor.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ClaManageViewHolder extends RecyclerView.ViewHolder{

        LinearLayout add,editor,delete;
        public ClaManageViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.cla_manage_add);
            editor = itemView.findViewById(R.id.cla_manage_editor);
            delete = itemView.findViewById(R.id.cla_manage_delete);
        }
    }
}
