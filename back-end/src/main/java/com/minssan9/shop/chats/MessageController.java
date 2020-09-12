package com.minssan9.shop.chats;

import com.minssan9.shop.ShopApplication;
import com.minssan9.shop.vo.MessageVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(ShopApplication.class);
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * 공지사항 전달
     * 접두어 /app + /boardCasting/{id}로 전달된 메세지를
     */
    @MessageMapping("/boardCasting/{id}")
    public void boardCasting(@DestinationVariable int id, MessageVO message) {
        messagingTemplate.convertAndSend(String.format("/topic/boardCasting/%d", id), message);
    }

    /**
     * 회원과 관리자의 대화 ( 문의 )
     */
    @MessageMapping("/question/{id}")
    public void question(@DestinationVariable Long id, MessageVO message) {
        Optional<Chat> optionalChat = chatRepository.findById(id);
        Chat chat = optionalChat.orElseThrow(() -> new EntityNotFoundException());
        Message msg = Message.builder()
                .chat(chat)
                .senderName(message.getSenderName())
                .sendMessage(message.getSendMessage())
                .sendDateTime(LocalDateTime.now())
                .readed(message.getSenderName().equals("관리자") ? 1 : 0)
                .build();

        messageRepository.save(msg);


        logger.info("메세지 전달:" + message.toString());
        messagingTemplate.convertAndSend(String.format("/queue/board/%d", id), message);
    }

}
