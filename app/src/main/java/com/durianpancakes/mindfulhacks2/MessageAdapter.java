package com.durianpancakes.mindfulhacks2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private List<Message> messageList = new ArrayList<Message>();
    private Context context;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messageList = messages;
    }

    public void add(Message message) {
        this.messageList.add(message);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messageList.get(i);

        if (message.messageOwner.contentEquals("user")) {
            System.out.println(message.messageBody + " " + message.messageTimestamp + " Belongs to user");
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.messageBody.setText(message.messageBody);
            convertView.setTag(holder);
        } else {
            System.out.println(message.messageBody + " " + message.messageTimestamp + "Belongs to bot");
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.messageBody.setText(message.messageBody);
            convertView.setTag(holder);
        }

        return convertView;
    }
}

class MessageViewHolder {
    public TextView messageBody;
}

