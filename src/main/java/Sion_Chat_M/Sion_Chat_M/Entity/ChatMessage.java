package Sion_Chat_M.Sion_Chat_M.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ENTER,TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
