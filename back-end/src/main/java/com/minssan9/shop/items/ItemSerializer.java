package com.minssan9.shop.items;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class

ItemSerializer extends JsonSerializer<Item> {

    @Override
    public void serialize(Item item, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeNumberField("id",item.getId());
        gen.writeStringField("title",item.getTitle());
        gen.writeStringField("created",item.getItemCreated().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        gen.writeNumberField("sellCount",item.getSellCount());
        gen.writeNumberField("price",item.getPrice());
        gen.writeNumberField("discount",item.getDiscount());
        gen.writeNumberField("savings",item.getSavings());
        gen.writeNumberField("deliveryDate",item.getDeliveryDate());
        gen.writeStringField("content",item.getContent());
        gen.writeObjectField("options",item.getOptions());

        gen.writeObjectField("itemFileList",item.getItemFileList());

        gen.writeEndObject();
    }
}

