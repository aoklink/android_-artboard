package com.linkfeeling.android.art.board.core.title;

import android.view.View;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.core.event.IEventManifest;
import com.linkfeeling.android.art.board.event.EventEngine;
import com.linkfeeling.android.art.board.event.IEventListener;

public class TitleWorker implements IEventListener<Integer> {
    private TextSwitcher textSwitcher;
    private int nowCount = -1;

    public TitleWorker(TextSwitcher textSwitcher) {
        this.textSwitcher = textSwitcher;
    }

    public void init(){
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return View.inflate(textSwitcher.getContext(),R.layout.people_count_text_view,null);
            }
        });
        EventEngine.listen(IEventManifest.REFRESH_USER_COUNT,this);
    }

    @Override
    public void on(Integer data) {
        if(data.intValue() != nowCount){
            textSwitcher.setText(String.valueOf(data));
            nowCount = data.intValue();
        }
    }
}
