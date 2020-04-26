package com.minssan9.shop.chats;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class MessageSerializer extends JsonSerializer<Message> {


    @Override
    public void serialize(Message message, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeNumberField("accountId", message.getChat().getAccount().getId());
        gen.writeStringField("senderName", message.getSenderName());
        gen.writeStringField("sendMessage", message.getSendMessage());
        gen.writeNumberField("read", message.getRead());
        gen.writeStringField("sendDateTime", message.getSendDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));


        gen.writeEndObject();
    }
}


