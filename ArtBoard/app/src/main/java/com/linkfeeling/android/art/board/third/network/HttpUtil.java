package com.linkfeeling.android.art.board.third.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class HttpUtil {

    protected static final String TAG = "HttpUtil";

    private static String APPLICATION = "application/x-www-form-urlencoded;charset=UTF-8";
    private static String CONTENT_TYPE = "Content-Type";
    private static String USER_AGENT = "User-Agent";

    public static String post(String url, String contentType, String content) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .removeHeader(USER_AGENT)
                .addHeader(CONTENT_TYPE, APPLICATION)
                .post(new StringBody(content, contentType))
                .build();//创建Request 对象
        return LinkOkHttpClient.okHttpClient().newCall(request).execute().body().string();
    }

    private static class StringBody extends RequestBody {

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
