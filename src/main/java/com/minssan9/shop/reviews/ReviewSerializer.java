package com.minssan9.shop.reviews;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ReviewSerializer extends JsonSerializer<Review> {
    @Override
    public void serialize(Review review, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {


        gen.writeStartObject();

        gen.writeNumberField("id",review.getId());
        gen.writeStringField("content",review.getContent());
        gen.writeStringField("created",review.getReviewCreated().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        gen.writeStringField("likeStatus",review.getLikeStatus());


        gen.writeObjectField("account",review.getAccount());
        gen.writeObjectField("itemId",review.getItem().getId());
        gen.writeObjectField("itemTitle",review.getItem().getTitle());
        gen.writeObjectField("reviewFile",review.getReviewFile());

        gen.writeEndObject();
    }
}
