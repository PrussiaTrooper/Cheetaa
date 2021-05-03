package com.maherhanna.cheeta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerAPI {
    @GET("cfg.gson")
    Call<DataSettings> getInfo();
}
