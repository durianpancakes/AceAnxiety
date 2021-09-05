package com.durianpancakes.mindfulhacks2;

public class Message {
    public String messageBody;
    public String messageOwner;
    public String messageTimestamp;

    public Message() {

    }

    public Message(String messageBody, String messageOwner, String messageTimestamp) {
        this.messageBody = messageBody;
        this.messageOwner = messageOwner;
        this.messageTimestamp = messageTimestamp;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(String messageOwner) {
        this.messageOwner = messageOwner;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }
}
