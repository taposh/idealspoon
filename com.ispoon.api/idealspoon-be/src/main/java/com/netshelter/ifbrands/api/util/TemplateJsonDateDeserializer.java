package com.netshelter.ifbrands.api.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parsing dates in format: "2013-06-25"
 * @author Dmitriy T
 */
public class TemplateJsonDateDeserializer extends JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
            throws IOException, JsonProcessingException
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = jsonparser.getText();
        try
        {
            return format.parse(date);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

    }
}
