package com.minssan9.shop.chats;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findByChat_IdAndRead(Long id, int read);
}
