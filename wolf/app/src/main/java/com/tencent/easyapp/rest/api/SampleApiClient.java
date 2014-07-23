package com.tencent.easyapp.rest.api;

import com.tencent.easyapp.Note;
import com.tencent.easyapp.rest.error.EappErrorHandler;
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
        @GET("/note/")
        void getStreams(@Query("limit") int limit, @Query("page") int page,
                        Callback<List<Note>> callback);
    }

    public static EappApiService getEappApiClient(){
        if(sEappApiService == null){
            //ip地址会变动，ipconfig之后手动修改
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://10.66.93.159:8080/easyapp/api/v1")
                    .setErrorHandler(new EappErrorHandler())
                    .build();
            sEappApiService = restAdapter.create(EappApiService.class);
        }
        return  sEappApiService;
    }
}
