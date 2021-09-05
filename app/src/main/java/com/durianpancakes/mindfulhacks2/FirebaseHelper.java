package com.durianpancakes.mindfulhacks2;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class FirebaseHelper {
    private static FirebaseHelper INSTANCE = null;
    private String mUserId;
    private DatabaseReference mReference;
    private ArrayList<Message> mMessages;

    public static FirebaseHelper getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseHelper();
        }

        return INSTANCE;
    }

    private FirebaseHelper() {
        mReference = FirebaseDatabase.getInstance("https://mindfulhacks-115a6-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getUserMessages(new FirebaseHelperInterface() {
            @Override
            public void onUserMessagesObtained(ArrayList<Message> messages) {
                mMessages = messages;
            }
        });
    }

    public void addNewUser(String name) {
        DatabaseReference usersReference = mReference.child("users");
        usersReference.child(mUserId).child("name").setValue(name);
    }

    public void getUserMessages(FirebaseHelperInterface helperInterface) {
        System.out.println("getUserMessages");

        DatabaseReference userMessagesReference = mReference.child("users")
                .child(mUserId)
                .child("messages");

        ValueEventListener userMessagesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Message> userMessagesResult = new ArrayList<>();

                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    userMessagesResult.add(message);
                }

                helperInterface.onUserMessagesObtained(userMessagesResult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        userMessagesReference.addValueEventListener(userMessagesListener);
    }

    public void addNewMessage(Message message) {
        DatabaseReference messagesReference = mReference.child("users").child(mUserId)
                .child("messages");
        messagesReference.push().setValue(message);
    }
}
