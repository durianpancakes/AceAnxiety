package com.durianpancakes.mindfulhacks2;

import java.util.Locale;

public class ChatBot {
    private static ChatBot INSTANCE = null;

    public static ChatBot getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ChatBot();
        }

        return INSTANCE;
    }

    public String respondToUser(Message userMessage) {
        String userMessageBody = userMessage.messageBody.toLowerCase(Locale.ROOT);
        String responseMessageBody = "";
        int numResponsesMatched = 0;

        if (userMessageBody.contains("i feel i have let my parents down")) {
            if (numResponsesMatched != 0) {
                responseMessageBody += "\n";
            }
            responseMessageBody += "No matter what,your parents will " +
                    "always be proud of you and will love you.\n" +
                    "You will feel much better if you share your feelings with them.";
            numResponsesMatched++;
        }

        if (userMessageBody.contains("i am good for nothing")) {
            if (numResponsesMatched != 0) {
                responseMessageBody += "\n";
            }
            responseMessageBody += "Don't ever think you cannot achieve what you want.\n" +
                    "You are capable of reaching your goals.Here is a quote " +
                    "for you Everything you can imagine is real.";
            numResponsesMatched++;
        }

        if (userMessageBody.contains("i am feeling stressed lately")) {
            if (numResponsesMatched != 0) {
                responseMessageBody += "\n";
            }
            responseMessageBody += "My research says that music is the best way to calm yourself.\n" +
                    "  - so visit the link and listen to your favourite music.\n" +
                    "  - it will definitely cheer you up \"https://www.spotify.com\"";
            numResponsesMatched++;
        }

        if (userMessageBody.contains("i want to kill myself")) {
            if (numResponsesMatched != 0) {
                responseMessageBody += "\n";
            }
            responseMessageBody += "Helpline is available,you can contact the " +
                    "counsellor today: 1800 221 4444 (Samaritans of Singapore). " +
                    "Always remember you mean something to someone.";
            numResponsesMatched++;
        }

        return responseMessageBody;
    }
}
