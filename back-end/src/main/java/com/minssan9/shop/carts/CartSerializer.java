package com.minssan9.shop.carts;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.minssan9.shop.accounts.Account;

import java.io.IOException;

public class CartSerializer extends JsonSerializer<Cart> {

    @Override
    public void serialize(Cart cart, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", cart.getId());
        gen.writeObjectField("item", cart.getItem());
        gen.writeEndObject();
    }
}
