package com.zx.haijixing.logistics.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.entry.ClassManageEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.List;

import zx.com.skytool.ZxStringUtil;


/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 17:20
 *@描述 班次管理
 */
public class ClaManageAdapter extends RecyclerView.Adapter<ClaManageAdapter.ClaManageViewHolder> {
    private List<ClassManageEntry> classManageEntries;
    private ClaManageResultListener claManageResultListener;
    private String linesId;
    private Activity activity;

    public ClaManageAdapter(List<ClassManageEntry> classManageEntries,String linesId) {
        this.classManageEntries = classManageEntries;
        this.linesId = linesId;
    }

    public void setClaManageResultListener(ClaManageResultListener claManageResultListener) {
        this.claManageResultListener = claManageResultListener;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
        if (classManageEntries.size()>0){
            ClassManageEntry classManageEntry = classManageEntries.get(i);
            claManageViewHolder.delete.setOnClickListener(v -> claManageResultListener.claManageResult(classManageEntry.getBakkiId(),OtherConstants.CLASS_DELETE));
            claManageViewHolder.editor.setOnClickListener(v ->  ARouter.getInstance().build(PathConstant.ADD_CLASS)
                    .withString("linesId",linesId)
                    .withSerializable("editor",classManageEntry)
                    .withInt("from",OtherConstants.SKIP_EDITOR)
                    .navigation(activity,OtherConstants.REQUEST_ADD_CLASS)
                    );

            claManageViewHolder.sendWay.setText(classManageEntry.getProductName());
            claManageViewHolder.startT.setText(classManageEntry.getStartTime());
            claManageViewHolder.endT.setText(classManageEntry.getEndTime());

            String differTime = classManageEntry.getDifferTime();
            long s = Long.parseLong(ZxStringUtil.isEmpty(differTime) ? "0" : differTime);
            claManageViewHolder.totalT.setText(HaiTool.calculateTime(s>0?s:s+OtherConstants.TIME_ONE_DAY));

            claManageViewHolder.nameNum.setText(classManageEntry.getDriverName()+" "+ classManageEntry.getDriverPhone());
            claManageViewHolder.truckNum.setText(classManageEntry.getIdcard());
            claManageViewHolder.remark.setText(classManageEntry.getRemark());
        }
    }

    @Override
    public int getItemCount() {
        return classManageEntries.size();
    }

    class ClaManageViewHolder extends RecyclerView.ViewHolder{

        LinearLayout delete,editor;
        TextView sendWay,startT,endT,totalT,nameNum,truckNum,remark;
        public ClaManageViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.cla_manage_delete);
            editor = itemView.findViewById(R.id.cla_manage_editor);

            sendWay = itemView.findViewById(R.id.cla_manage_sendWay);
            startT = itemView.findViewById(R.id.cla_manage_startT);
            endT = itemView.findViewById(R.id.cla_manage_endT);
            totalT = itemView.findViewById(R.id.cla_manage_totalT);
            nameNum = itemView.findViewById(R.id.cla_manage_nameNum);
            truckNum = itemView.findViewById(R.id.cla_manage_truckNum);
            remark = itemView.findViewById(R.id.cla_manage_remark);
        }
    }

    public interface ClaManageResultListener{
        void claManageResult(String bkId,int tag);
    }
}
