package Sion_Chat_M.Sion_Chat_M.Entity;

import Sion_Chat_M.Sion_Chat_M.Service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roomId;
    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();
    //private final Map<String,WebsocketSession> sessions = new ConcurrentHashMap<>();
    //접속중인 모든 세션에 메세지를 보냄.


    @Builder
    public ChatRoom(String roomId,String name){
        this.roomId =roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다." + chatMessage.getMessage());
        }
        sendMessage(chatMessage, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
