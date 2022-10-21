package Sion_Chat_M.Sion_Chat_M;

import Sion_Chat_M.Sion_Chat_M.Entity.ChatMessage;
import Sion_Chat_M.Sion_Chat_M.Entity.ChatRoom;
import Sion_Chat_M.Sion_Chat_M.Service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    //afterConnectionEstablished 웹소켓 연결시
    //handleTextMessage : 데이터 통신시
    //afterConnectionClosed: 웹소켓 연결 종료 시
    //handleTransportError : 웹소켓 통신 에러시

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}