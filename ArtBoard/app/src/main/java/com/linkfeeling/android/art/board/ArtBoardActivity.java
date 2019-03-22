package com.linkfeeling.android.art.board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.linkfeeling.android.art.board.core.data.ArtBoardDataCenterHolder;
import com.linkfeeling.android.art.board.core.sport.summary.SportSummaryWorker;
import com.linkfeeling.android.art.board.core.title.TitleWorker;
import com.linkfeeling.android.art.board.core.ui.config.BoardUIConfig;
import com.linkfeeling.android.art.board.core.user.board.LoadingViewWorker;
import com.linkfeeling.android.art.board.core.user.board.UserBoardWorker;

public class ArtBoardActivity extends AppCompatActivity {

    private RecyclerView userBoardRV;
    private UserBoardWorker userBoardWorker;

    private RecyclerView userSportSummaryRV;
    private SportSummaryWorker sportSummaryWorker;

    private LoadingViewWorker loadingViewWorker;

    private TitleWorker titleWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoardUIConfig.load(getApplicationContext());
        setContentView(R.layout.activity_art_board);
        userBoardRV = findViewById(R.id.user_board_recycler_view);
        userBoardWorker = new UserBoardWorker();
        loadingViewWorker = new LoadingViewWorker(
                (ImageView) findViewById(R.id.loading_image_view),
                (TextView)findViewById(R.id.loading_tip_text_view),
                (ViewGroup) findViewById(R.id.loading_layout));
        userBoardWorker.setRecyclerView(userBoardRV);
        userBoardWorker.setLoadingViewWorker(loadingViewWorker);
        userBoardWorker.init();

        userSportSummaryRV = findViewById(R.id.user_sport_summary_recycler_view);
        sportSummaryWorker = new SportSummaryWorker();
        sportSummaryWorker.setSportSummaryRecyclerView(userSportSummaryRV);
        sportSummaryWorker.init();

        titleWorker = new TitleWorker((TextSwitcher) findViewById(R.id.people_count_text_switcher));
        titleWorker.init();

        ArtBoardDataCenterHolder.getInstance().install();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArtBoardDataCenterHolder.getInstance().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ArtBoardDataCenterHolder.getInstance().pause();
    }
}
