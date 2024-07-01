package com.example.taskdemo.webservices;

<<<<<<< HEAD
=======
import com.example.taskdemo.utils.Constants;

>>>>>>> edfe99dd9d45dbc37732bc6193191ed7edd03b23
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if(retrofit == null ) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(AppUrls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
