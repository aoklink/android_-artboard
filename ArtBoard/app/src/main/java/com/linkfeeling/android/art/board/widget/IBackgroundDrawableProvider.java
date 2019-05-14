package com.linkfeeling.android.art.board.widget;

import android.graphics.drawable.Drawable;

public interface IBackgroundDrawableProvider {
    Drawable provide(String name, int index);
}
