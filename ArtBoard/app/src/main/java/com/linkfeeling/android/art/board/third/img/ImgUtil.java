package com.linkfeeling.android.art.board.third.img;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkfeeling.android.art.board.R;

public class ImgUtil {

    private static  RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.icon_user_default)//图片加载出来前，显示的图片
            .fallback( R.drawable.icon_user_default) //url为空的时候,显示的图片
            .error(R.drawable.icon_user_default);//图片加载失败后，显示的图片

    public static void drawImg(ImageView view, String url) {
        Glide.with(view).load(Uri.parse(url)).apply(options).into(view);
    }
}
