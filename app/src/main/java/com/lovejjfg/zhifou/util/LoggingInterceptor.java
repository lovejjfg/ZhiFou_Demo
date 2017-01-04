package com.lovejjfg.zhifou.util;

import android.util.Log;

import com.google.gson.Gson;
import com.lovejjfg.zhifou.util.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Joe on 2017/1/4.
 * Email lovejjfg@gmail.com
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Logger.i(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        String format = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());
        Logger.i(format);
        Log.e("TAG", "intercept: " + format);

//        Logger.i("response:" + response.toString() + "\n");
//        Logger.json(new Gson().toJson(response.body()));
        return response;
    }
}
