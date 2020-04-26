package com.minssan9.shop.reviews;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ReviewFileSerializer extends JsonSerializer<ReviewFile> {


    @Override
    public void serialize(ReviewFile reviewFile, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("fileName", reviewFile.getFileName());
        gen.writeStringField("uploadPath", reviewFile.getUploadPath());
        gen.writeStringField("uuid", reviewFile.getUuid());
        gen.writeEndObject();
    }
}
