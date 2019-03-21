package com.linkfeeling.android.art.board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.linkfeeling.android.art.board.core.sport.summary.SportSummaryWorker;
import com.linkfeeling.android.art.board.core.ui.config.BoardUIConfig;
import com.linkfeeling.android.art.board.core.user.board.UserBoardWorker;

public class ArtBoardActivity extends AppCompatActivity {

    private RecyclerView userBoardRV;
    private UserBoardWorker userBoardWorker;

    private RecyclerView userSportSummaryRV;
    private SportSummaryWorker sportSummaryWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoardUIConfig.load(getApplicationContext());
        setContentView(R.layout.activity_art_board);
        userBoardRV = findViewById(R.id.user_board_recycler_view);
        userBoardWorker = new UserBoardWorker();
        userBoardWorker.setRecyclerView(userBoardRV);
        userBoardWorker.init();

        userSportSummaryRV = findViewById(R.id.user_sport_summary_recycler_view);
        sportSummaryWorker = new SportSummaryWorker();
        sportSummaryWorker.setSportSummaryRecyclerView(userSportSummaryRV);
        sportSummaryWorker.init();
    }


}
