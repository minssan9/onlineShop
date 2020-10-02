package com.minssan9.shop.qnas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class QnASerializer extends JsonSerializer<QnA> {

    @Override
    public void serialize(QnA qnA, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", qnA.getId());
        gen.writeStringField("title", qnA.getTitle());
        gen.writeStringField("created", qnA.getQnaCreated().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        gen.writeStringField("writer", qnA.getWriter().getAccountId());
        gen.writeStringField("content", qnA.getContent());
        gen.writeNumberField("status", qnA.getStatus());
        gen.writeStringField("answer", qnA.getAnswer());

        gen.writeNumberField("itemId", qnA.getItem().getId());
        gen.writeStringField("itemTitle", qnA.getItem().getTitle());
        gen.writeEndObject();
    }
}
