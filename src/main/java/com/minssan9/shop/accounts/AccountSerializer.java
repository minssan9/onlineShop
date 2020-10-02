package com.minssan9.shop.accounts;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashSet;

public class AccountSerializer extends JsonSerializer<Account> {

    @Override
    public void serialize(Account account, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id", account.getId());
        gen.writeStringField("accountId", account.getAccountId());
        gen.writeStringField("email", account.getEmail());
        gen.writeStringField("phone", account.getPhone());
        gen.writeStringField("address", account.getAddress());
        gen.writeStringField("name", account.getName());
        gen.writeNumberField("level", account.getLevel());
        gen.writeNumberField("point", account.getPoint());
        gen.writeObjectField("roles", account.getRoles());
        gen.writeObjectField("chat", account.getChat().getId());
        gen.writeObjectField("accountFile",account.getAccountFile());
        gen.writeEndObject();
    }
}
