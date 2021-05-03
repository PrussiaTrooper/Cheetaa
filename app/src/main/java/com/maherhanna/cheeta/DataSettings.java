package com.maherhanna.cheeta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSettings {

    private static String URL = "https://raw.githubusercontent.com/PrussiaTrooper/appsettinstest/maste/main/";
    private static Retrofit retrofit;

    public static Retrofit Instance() {
        retrofit =new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @SerializedName("array")
    @Expose
    private List<Array> array = null;

    public List<Array> getArray() {
        return array;
    }

    public void setArray(List<Array> array) {
        this.array = array;
    }
}

class Array {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlSecond")
    @Expose
    private String urlSecond;
    @SerializedName("randomValue")
    @Expose
    private String randomValue;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlSecond() {
        return urlSecond;
    }

    public void setUrlSecond(String urlSecond) {
        this.urlSecond = urlSecond;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }

    public String getFlag() {
        return flag;
    }

  //  public void setFlag(String flag) {
      //  this.flag = flag;
    }

