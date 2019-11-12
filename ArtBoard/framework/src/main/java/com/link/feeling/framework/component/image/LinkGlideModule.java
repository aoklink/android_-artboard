package com.link.feeling.framework.component.image;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created on 2019/10/22  14:37
 * chenpan pan.chen@linkfeeling.cn
 */
@GlideModule
public final class LinkGlideModule extends AppGlideModule {
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//    }
//
//
//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        //设置请求方式为okhttp 并设置okhttpClient的证书及超时时间
//        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(UnsafeOkHttpClient.getUnsafeOkHttpClient());
//        registry.replace(GlideUrl.class, InputStream.class, factory);
//    }
//
//    //自定义工具类修改OkHttpClient证书和超时时间
//    static class UnsafeOkHttpClient {
//        public static OkHttpClient getUnsafeOkHttpClient() {
//            try {
//                // Create a trust manager that does not validate certificate chains
//                final TrustManager[] trustAllCerts = new TrustManager[]{
//                        new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                            }
//
//                            @Override
//                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                            }
//
//                            @Override
//                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                return new java.security.cert.X509Certificate[]{};
//                            }
//                        }
//                };
//
//                // Install the all-trusting trust manager
//                final SSLContext sslContext = SSLContext.getInstance("SSL");
//                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//                // Create an ssl socket factory with our all-trusting manager
//                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//                OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
//                builder.hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                });
//
//                builder.connectTimeout(20, TimeUnit.SECONDS);
//                builder.readTimeout(20, TimeUnit.SECONDS);
//                builder.writeTimeout(20, TimeUnit.SECONDS);
//
//                OkHttpClient okHttpClient = builder.build();
//                return okHttpClient;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
