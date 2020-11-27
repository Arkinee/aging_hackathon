package com.elderlink.android.src.chat;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elderlink.android.src.chat.interfaces.ChatRetrofitInterface;
import com.elderlink.android.src.chat.interfaces.ChatView;
import com.elderlink.android.src.chat.model.ChatPostResponse;
import com.elderlink.android.src.chat.model.ChatResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elderlink.android.src.ApplicationClass.getRetrofit;

public class ChatService {

    Context mContext;
    private ChatView mView;

    public ChatService(final ChatView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void getChatList() {
        final ChatRetrofitInterface chatRetrofitInterface = getRetrofit().create(ChatRetrofitInterface.class);
        chatRetrofitInterface.getChatList().enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChatResponse> call, @NonNull Response<ChatResponse> response) {

                final ChatResponse scheduleResponse = response.body();
                if (scheduleResponse == null) {
                    mView.getChatFailure("빈 값");
                    return;
                }

                if (scheduleResponse.isSuccess()) {
                    mView.getChatSuccess(scheduleResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                mView.getChatFailure("서버 연결 실패");
            }
        });
    }

    public void postChatList(HashMap<String, Object> params) {
        final ChatRetrofitInterface chatRetrofitInterface = getRetrofit().create(ChatRetrofitInterface.class);
        chatRetrofitInterface.postChatList(params).enqueue(new Callback<ChatPostResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChatPostResponse> call, @NonNull Response<ChatPostResponse> response) {

                final ChatPostResponse chatPostResponse = response.body();
                if (chatPostResponse == null) {
                    mView.postFailure("빈 값");
                    return;
                }

                if (chatPostResponse.isSuccess()) {
                    mView.postSuccess();
                }
            }

            @Override
            public void onFailure(Call<ChatPostResponse> call, Throwable t) {
                mView.postFailure("서버 연결 실패");
            }
        });
    }

}
