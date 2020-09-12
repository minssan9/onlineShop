package com.minssan9.shop.chats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chats")
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/{id}")
    public ResponseEntity getChat(@PathVariable Long id)
    {
        Optional<Chat> byId = chatRepository.findById(id);
        Chat chat = byId.orElseThrow(() -> new EntityNotFoundException());

        return ResponseEntity.ok(chat);
    }
    @GetMapping
    public ResponseEntity getChatList(Pageable pageable){
        List<Chat> chats=chatRepository.findAll();

        for(int i=0; i<chats.size(); i++)
        {
            int unRead = messageRepository.findByChat_IdAndReaded(chats.get(i).getId(), 0).size();
            chats.get(i).setUnRead(unRead);

            chatRepository.save(chats.get(i));
        }
        
        Page<Chat> chatPage = chatRepository.findAll(pageable);
        return ResponseEntity.ok(chatPage);
    }

    @GetMapping("/read/{id}")    // 읽음처리
    public ResponseEntity read(@PathVariable Long id){

        List<Message> messages = messageRepository.findByChat_IdAndReaded(id, 0);

        for(int i=0; i<messages.size(); i++)
        {
            messages.get(i).setReaded(1);
            messageRepository.save(messages.get(i));
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}

