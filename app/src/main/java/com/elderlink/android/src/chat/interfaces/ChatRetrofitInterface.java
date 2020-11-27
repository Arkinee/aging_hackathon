package com.elderlink.android.src.chat.interfaces;

import com.elderlink.android.src.chat.model.ChatPostResponse;
import com.elderlink.android.src.chat.model.ChatResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatRetrofitInterface {

    @GET("/chat")
    Call<ChatResponse> getChatList();

    @POST("/chat")
    Call<ChatPostResponse> postChatList(@Body HashMap<String, Object> params);
}
