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
import com.linkfeeling.android.art.board.core.ui.BackgroundProvider;
import com.linkfeeling.android.art.board.third.img.ImgUtil;
import com.linkfeeling.android.art.board.widget.support.RecyclerViewGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class UserBoardWorker {
    private RecyclerView recyclerView;

    private List<UserBoardItem> userBoardItemList;

    private RecyclerView.Adapter adapter;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void init() {
        userBoardItemList = new ArrayList<>();
        userBoardItemList.add(new UserBoardItem(200,300,"","这是最长的名字了"));
        userBoardItemList.add(new UserBoardItem(200,300,"","这个"));
        userBoardItemList.add(new UserBoardItem(200,300,"","这是最长的"));
        userBoardItemList.add(new UserBoardItem(200,300,"","这是名字了"));

        userBoardItemList.add(new UserBoardItem(200,300,"","最长的名字了"));
        userBoardItemList.add(new UserBoardItem(200,300,"","最长的名字"));
        userBoardItemList.add(new UserBoardItem(200,300,"","名字了"));
        userBoardItemList.add(new UserBoardItem(200,300,"","长的名字了"));

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
                userSportItemHolder.drawItem(userBoardItemList.get(i));
            }

            @Override
            public int getItemCount() {
                return userBoardItemList.size();
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public void refresh(List<UserBoardItem> userBoardItems){
        this.userBoardItemList.clear();
        this.userBoardItemList.addAll(userBoardItems);
        adapter.notifyDataSetChanged();
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

        public void drawItem(UserBoardItem userBoardItem) {
            caloriesTv.setText(String.valueOf(userBoardItem.getCalories()));
            heartRateTv.setText(String.valueOf(userBoardItem.getHeartRate()));
            ImgUtil.drawImg(userIconIv,userBoardItem.getUserIcon());
            userNameTv.setText(userBoardItem.getUserNick());
            contentHeadLayout.setBackground(BackgroundProvider.get(BackgroundProvider.AT_USER_SPORT,userBoardItem.getUserNick()));
        }
    }
}
