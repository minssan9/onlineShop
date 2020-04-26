package com.minssan9.shop.orders;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class OrderSerializer extends JsonSerializer<Order> {

    @Override
    public void serialize(Order order, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeNumberField("id",order.getId());
        gen.writeStringField("created",order.getOrderCreated().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        gen.writeStringField("payment",order.getPayment());
        gen.writeStringField("option",order.getOption());
        gen.writeNumberField("status",order.getStatus());
        gen.writeObjectField("account",order.getAccount());
        gen.writeObjectField("item",order.getItem());

        gen.writeEndObject();
    }
}
