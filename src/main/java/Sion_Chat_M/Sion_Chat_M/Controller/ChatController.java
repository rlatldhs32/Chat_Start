package Sion_Chat_M.Sion_Chat_M.Controller;

import Sion_Chat_M.Sion_Chat_M.Entity.ChatRoom;
import Sion_Chat_M.Sion_Chat_M.Dto.ChatRoomDto;
import Sion_Chat_M.Sion_Chat_M.Entity.User;
import Sion_Chat_M.Sion_Chat_M.Repository.UserRepository;
import Sion_Chat_M.Sion_Chat_M.Service.ChatService;
import Sion_Chat_M.Sion_Chat_M.Util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserRepository userRepository;

    @PostMapping("/user")
    public String CreateUser(@RequestBody String name) {
        System.out.println("createuser");
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return "good";
    }


    @PostMapping
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ChatRoomDto>>> findAllRoom() {

        System.out.println("find Start!");
        List<ChatRoom> allRoom2 = chatService.findAllRoom();
        List<ChatRoomDto> ids = new ArrayList<>();

        for (ChatRoom chatRoom : allRoom2) {
            ChatRoomDto chatRoom1 = new ChatRoomDto();
            chatRoom1.id=chatRoom.getRoomId();
            chatRoom1.name=chatRoom.getName();
            Set<WebSocketSession> sessions = chatRoom.getSessions();
            for (WebSocketSession session : sessions) {
                System.out.println("session = " + session);
            }
            ids.add(chatRoom1);
        }

        return ResponseWrapper.ok(ids," success");
    }
}