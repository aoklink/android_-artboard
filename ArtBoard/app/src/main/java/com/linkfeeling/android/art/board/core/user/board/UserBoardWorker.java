package com.linkfeeling.android.art.board.core.user.board;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.core.data.trans.UserBoardItemTrans;
import com.linkfeeling.android.art.board.core.event.IEventManifest;
import com.linkfeeling.android.art.board.core.ui.BackgroundProvider;
import com.linkfeeling.android.art.board.core.user.board.bean.support.UserBoardItemBeanFactory;
import com.linkfeeling.android.art.board.event.EventEngine;
import com.linkfeeling.android.art.board.event.IEventListener;
import com.linkfeeling.android.art.board.third.img.ImgUtil;
import com.linkfeeling.android.art.board.widget.support.RecyclerViewGridItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserBoardWorker implements IEventListener<JSONObject> {
    private RecyclerView recyclerView;

    private List<UserBoardItem> userBoardItemList;

    private RecyclerView.Adapter adapter;

    private LoadingViewWorker loadingViewWorker;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setLoadingViewWorker(LoadingViewWorker loadingViewWorker) {
        this.loadingViewWorker = loadingViewWorker;
    }

    public void init() {
        userBoardItemList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),4));
        recyclerView.addItemDecoration(new RecyclerViewGridItemDecoration(48,30));
        adapter = new RecyclerView.Adapter<UserSportItemHolder>() {

            @NonNull
            @Override
            public UserSportItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return UserSportItemHolder.create(viewGroup.getContext());
            }

            @Override
            public void onBindViewHolder(@NonNull UserSportItemHolder userSportItemHolder, int i) {
                userSportItemHolder.drawItem(userBoardItemList.get(i),i);
            }

            @Override
            public int getItemCount() {
                return userBoardItemList.size();
            }
        };

        recyclerView.setAdapter(adapter);

        EventEngine.listen(IEventManifest.REFRESH_USER_BOARD,this);
        loadingViewWorker.show();
    }

    public void refresh(List<UserBoardItem> userBoardItems){
        UserBoardItemBeanFactory.recycle(new ArrayList<>(userBoardItemList));
        this.userBoardItemList.clear();
        this.userBoardItemList.addAll(userBoardItems);
        adapter.notifyDataSetChanged();
        if(loadingViewWorker.isShowing()){
            loadingViewWorker.hide();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void on(JSONObject data) {
        try {
            if("200".equals(data.getString("code"))){
                JSONArray jsonArray = data.getJSONObject("data").getJSONArray("gym_data");
                refresh(UserBoardItemTrans.fromJsonArray(jsonArray));
                EventEngine.postOnUI(IEventManifest.REFRESH_USER_COUNT, userBoardItemList.size());
            }else{
                loadingViewWorker.error();
            }
        } catch (JSONException e) {
            loadingViewWorker.error();
        }
    }

    private static class UserSportItemHolder extends RecyclerView.ViewHolder{

        public static UserSportItemHolder create(Context context) {
            return new UserSportItemHolder(View.inflate(context,R.layout.user_item_layout,null));
        }

        private TextView caloriesTv;
        private TextView heartRateTv;
        private ImageView userIconIv;
        private TextView userNameTv;
        private View contentHeadLayout;

        public UserSportItemHolder(@NonNull View itemView) {
            super(itemView);
            caloriesTv = itemView.findViewById(R.id.calories_tv);
            heartRateTv = itemView.findViewById(R.id.heart_rate_tv);
            userIconIv = itemView.findViewById(R.id.user_icon_iv);
            userNameTv = itemView.findViewById(R.id.user_name_tv);
            contentHeadLayout = itemView.findViewById(R.id.user_sport_content_layout);
        }

        public void drawItem(UserBoardItem userBoardItem,int index) {
            caloriesTv.setText(String.valueOf(userBoardItem.getCalorie()));
            heartRateTv.setText(String.valueOf(userBoardItem.getHeartRate()));
            ImgUtil.drawImg(userIconIv,userBoardItem.getUserIcon());
            userNameTv.setText(userBoardItem.getUserNick());
            contentHeadLayout.setBackground(BackgroundProvider.get(BackgroundProvider.AT_USER_SPORT,userBoardItem.getUserNick(),userBoardItem.getResult()));
        }
    }
}
