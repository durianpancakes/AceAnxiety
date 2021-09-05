package com.durianpancakes.mindfulhacks2.ui.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.durianpancakes.mindfulhacks2.FirebaseHelper;
import com.durianpancakes.mindfulhacks2.FirebaseHelperInterface;
import com.durianpancakes.mindfulhacks2.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Message>> messages;

    private void loadMessages() {
        FirebaseHelper firebaseHelper = FirebaseHelper.getINSTANCE();
        firebaseHelper.getUserMessages(new FirebaseHelperInterface() {
            @Override
            public void onUserMessagesObtained(ArrayList<Message> messagesResult) {
                System.out.println("loadMessages successful");
                messages.setValue(messagesResult);
            }
        });
    }

    public MutableLiveData<ArrayList<Message>> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<ArrayList<Message>>();
            loadMessages();
        }

        return messages;
    }
}