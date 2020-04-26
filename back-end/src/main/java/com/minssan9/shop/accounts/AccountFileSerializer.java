package com.minssan9.shop.accounts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountFileSerializer extends JsonSerializer<AccountFile> {


    @Override
    public void serialize(AccountFile accountFile, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        gen.writeStartObject();

        gen.writeStringField("fileName",accountFile.getFileName());
        gen.writeStringField("uploadPath",accountFile.getUploadPath());
        gen.writeStringField("uuid",accountFile.getUuid());

        gen.writeEndObject();
    }
}
