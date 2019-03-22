package com.linkfeeling.android.art.board.third.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class HttpUtil {

    protected static final String TAG = "HttpUtil";


    public static String post(String url,String contentType,String content)throws Exception{
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        Request request = new Request.Builder()
                .url(url)
                .post(new StringBody(content,contentType))
                .build();//创建Request 对象
        return client.newCall(request).execute().body().string();
    }

    private static class StringBody extends RequestBody{

        private String content;
        private String contentType;

        public StringBody(String content, String contentType) {
            this.content = content;
            this.contentType = contentType;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(contentType);
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            sink.write(content.getBytes());
        }
    }
}
