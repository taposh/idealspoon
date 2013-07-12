package com.netshelter.ifbrands.etl.transform;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.storyamplification.ShortUrl;

@Component
public class ShortUrlTransformer
{
  public static ShortUrl transform( String shortUrlResponse ) throws JsonParseException, JsonMappingException, IOException
  {
    ObjectMapper mapper = new ObjectMapper();
    ShortUrl shortUrl = mapper.readValue( shortUrlResponse, ShortUrl.class );

    return shortUrl;
  }
}
