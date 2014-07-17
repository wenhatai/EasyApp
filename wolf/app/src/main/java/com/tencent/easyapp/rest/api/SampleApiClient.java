package com.tencent.easyapp.rest.api;

import com.tencent.easyapp.rest.error.EappErrorHandler;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleApiClient {
    private static EappApiService sEappApiService;

    public interface EappApiService{
        @GET("/stream/list.json")
        void getStreams(@Query("limit") int limit, @Query("offset") int offset,
                        Callback<List<SampleJustinTvStreamData>> callback);
    }

    public static EappApiService getEappApiClient(){
        if(sEappApiService == null){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.justin.tv/api")
                    .setErrorHandler(new EappErrorHandler())
                    .build();
            sEappApiService = restAdapter.create(EappApiService.class);
        }
        return  sEappApiService;
    }
}
