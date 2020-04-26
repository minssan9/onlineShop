package com.minssan9.shop.items;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ItemFileSerializer extends JsonSerializer<ItemFile> {


    @Override
    public void serialize(ItemFile itemFile, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeStringField("fileName",itemFile.getFileName());
        gen.writeStringField("uploadPath",itemFile.getUploadPath());
        gen.writeStringField("uuid",itemFile.getUuid());

        gen.writeEndObject();
    }
}
