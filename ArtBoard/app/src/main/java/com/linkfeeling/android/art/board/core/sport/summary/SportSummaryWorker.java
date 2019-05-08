package com.linkfeeling.android.art.board.core.sport.summary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.core.ui.BackgroundProvider;
import com.linkfeeling.android.art.board.widget.support.RecyclerViewVerticalItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SportSummaryWorker {
    private RecyclerView sportSummaryRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<SportSummaryItem> sportSummaryItemList;

    public void setSportSummaryRecyclerView(RecyclerView sportSummaryRecyclerView) {
        this.sportSummaryRecyclerView = sportSummaryRecyclerView;
    }

    public void init(){
        sportSummaryItemList = new ArrayList<>();
        sportSummaryItemList.add(new SportSummaryItem("激活\n放松","01-39%"));
        sportSummaryItemList.add(new SportSummaryItem("动态\n热身","40-55%"));
        sportSummaryItemList.add(new SportSummaryItem("脂肪\n燃烧","56-69%"));

        sportSummaryItemList.add(new SportSummaryItem("有氧\n耐力","70-79%"));
        sportSummaryItemList.add(new SportSummaryItem("无氧\n耐力","80-89%"));
        sportSummaryItemList.add(new SportSummaryItem("峰值\n锻炼","90-99%"));
        sportSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(sportSummaryRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL,false));
        sportSummaryRecyclerView.addItemDecoration(new RecyclerViewVerticalItemDecoration(30));

        adapter = new RecyclerView.Adapter<SportSummaryItemHolder>() {

            @NonNull
            @Override
            public SportSummaryItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return SportSummaryItemHolder.create(viewGroup.getContext());
            }

            @Override
            public void onBindViewHolder(@NonNull SportSummaryItemHolder sportSummaryItemHolder, int i) {
                sportSummaryItemHolder.drawItem(sportSummaryItemList.get(i),i);
            }

            @Override
            public int getItemCount() {
                return sportSummaryItemList.size();
            }
        };
        sportSummaryRecyclerView.setAdapter(adapter);

    }

    public void refresh(List<SportSummaryItem> sportSummaryItems){
        this.sportSummaryItemList.clear();
        this.sportSummaryItemList.addAll(sportSummaryItems);
        adapter.notifyDataSetChanged();
    }

    private static class SportSummaryItemHolder extends RecyclerView.ViewHolder{

        public static SportSummaryItemHolder create(Context context) {
            return new SportSummaryItemHolder(LayoutInflater.from(context).inflate(R.layout.user_sport_summary_item,null));
        }

        private TextView nameTv;
        private TextView valueTv;
        private View labelView;

        public SportSummaryItemHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.sport_summary_item_name_tv);
            valueTv = itemView.findViewById(R.id.sport_summary_item_value_tv);
            labelView = itemView.findViewById(R.id.sport_summary_item_label);
        }

        public void drawItem(SportSummaryItem sportSummaryItem,int index) {
            nameTv.setText(sportSummaryItem.getName());
            valueTv.setText(sportSummaryItem.getValue());
            labelView.setBackground(BackgroundProvider.get(BackgroundProvider.AT_USER_SPORT_SUMMARY,sportSummaryItem.getName(),index));
        }
    }
}
