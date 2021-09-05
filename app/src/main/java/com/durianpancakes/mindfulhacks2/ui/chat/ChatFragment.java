package com.durianpancakes.mindfulhacks2.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.durianpancakes.mindfulhacks2.Message;
import com.durianpancakes.mindfulhacks2.MessageAdapter;
import com.durianpancakes.mindfulhacks2.R;
import com.durianpancakes.mindfulhacks2.databinding.FragmentChatBinding;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ChatInterface mInterface;
    private ChatViewModel chatViewModel;
    private FragmentChatBinding binding;
    private ListView messageView;
    private EditText userInput;
    private ImageButton sendButton;
    private MessageAdapter messageAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        messageView = root.findViewById(R.id.messages_view);
        userInput = root.findViewById(R.id.editText);
        sendButton = root.findViewById(R.id.send_button);

        chatViewModel.getMessages().observe(getViewLifecycleOwner(), new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                messageView.setAdapter(new MessageAdapter(getContext(), messages));
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = userInput.getText().toString();
                if (!message.isEmpty()) {
                    mInterface.onSendButtonClicked(message);
                    userInput.setText("");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mInterface = (ChatInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ChatInterface");
        }
    }
}