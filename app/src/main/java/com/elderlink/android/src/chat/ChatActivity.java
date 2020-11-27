package com.elderlink.android.src.chat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.chat.adapter.ChatAdapter;
import com.elderlink.android.src.chat.interfaces.ChatView;
import com.elderlink.android.src.chat.model.Chat;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends BaseActivity implements ChatView {

    private RecyclerView mChatRv;
    private ChatAdapter mChatAdapter;
    private ArrayList<Chat> mChatList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatList = new ArrayList<>();
        mChatRv = findViewById(R.id.chat_rv);
        mChatAdapter = new ChatAdapter(this, mChatList, new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });
        mChatRv.setAdapter(mChatAdapter);

        getChat();
    }

    void getChat() {
        final ChatService service = new ChatService(this, this);
        service.getChatList();
        showProgressDialog();
    }

    public void chatOnClick(View view) {
        switch (view.getId()) {
            case R.id.chat_iv_mic:

                HashMap<String, Object> params = new HashMap<>();
                params.put("user_idx", 3);
                params.put("state", "음성");

                final ChatService chatService = new ChatService(this, this);
                chatService.postChatList(params);
                showProgressDialog();

                break;
            case R.id.chat_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void getChatSuccess(ArrayList<Chat> result) {
        hideProgressDialog();
        mChatList.clear();
        mChatList.addAll(result);
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getChatFailure(String message) {
        hideProgressDialog();
        showCustomToastShort(message);
    }

    @Override
    public void postSuccess() {
        mChatList.clear();
        getChat();
    }

    @Override
    public void postFailure(String message) {
        hideProgressDialog();
        showCustomToastShort(message);
    }
}
