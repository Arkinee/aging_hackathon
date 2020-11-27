package com.elderlink.android.src.chat.interfaces;

import com.elderlink.android.src.chat.model.Chat;

import java.util.ArrayList;

public interface ChatView {

    void getChatSuccess(ArrayList<Chat> result);
    void getChatFailure(String message);

    void postSuccess();
    void postFailure(String message);
}
