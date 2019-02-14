package com.devteam.mobile.simpegrri.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RestServices {
    private static Retrofit retrofit = null;
    public static final String BASE_URL= "http://36.66.197.94:81/e-presensi_dev/web/";
    public static final String BASE_URL_PROFILE_PICTURE = "http://103.238.203.243/kta/web/uploads/foto_profil/";
    public static final String BASE_URL_DEFAULT_PROFILE_PICTURE = "https://3.bp.blogspot.com/-ZqZpPDvYL6Q/XBtiRT-HjlI/AAAAAAAADRc/clx-Bin7w3EM1PlixqK-yTwVN-bOoeEfQCLcBGAs/s1600/img_default.jpg";

    public static RestInterface getService() {
        if(retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(RestInterface.class);
        }

        else return retrofit.create(RestInterface.class);
    }
}
