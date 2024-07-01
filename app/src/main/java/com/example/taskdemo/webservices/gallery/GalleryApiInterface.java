package com.example.taskdemo.webservices.gallery;

<<<<<<< HEAD

=======
>>>>>>> edfe99dd9d45dbc37732bc6193191ed7edd03b23
import com.example.taskdemo.gallerymodels.response.GalleryDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GalleryApiInterface {

    @GET("search/photos")
    Call<GalleryDataResponse> getMakeUpPhotos(
            @Query("client_id") String clientId,
            @Query("query") String query,
            @Query("page") int page
    );
}
