package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 18:13
 *@描述 搜索
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_data, viewGroup, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(inflate);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
