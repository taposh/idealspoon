package com.netshelter.ifbrands.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Utilities for working with JODA.
 * @author bgray
 *
 */
public class JodaUtils
{
  /** Create a Spring PropertyEditor for LocalDate using ISO8601 format. */
  public static PropertyEditor getSpringLocalDatePropertyEditor()
  {
    return new PropertyEditorSupport() {
      @Override
      public void setAsText( String text )
      {
        setValue( ISODateTimeFormat.dateParser().parseLocalDate( text ));
      }
    };
  }

  /** Create a Spring PropertyEditor for DateTime using ISO8601 format,
   * which honours the timezone offset field. */
  public static PropertyEditor getSpringDateTimePropertyEditor()
  {
    return new PropertyEditorSupport() {
      @Override
      public void setAsText( String text )
      {
        setValue( ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime( text ));
      }
    };
  }

  /** Create a Spring PropertyEditor for DateTimeZone. */
  public static PropertyEditor getSpringDateTimeZonePropertyEditor()
  {
    return new PropertyEditorSupport() {
      @Override
      public void setAsText( String text )
      {
        setValue( DateTimeZone.forID( text ));
      }
    };
  }

  /** Create a Jackson module for DateTimeZone. */
  public static SimpleModule getJacksonDateTimeZoneModule()
  {
    SimpleModule module = new SimpleModule( "JodaDateTimeModule", new Version( 1, 0, 0, "ifbrands" ));
    // Serializer
    module.addSerializer( DateTimeZone.class, new JsonSerializer<DateTimeZone>() {
      @Override
      public void serialize( DateTimeZone value, JsonGenerator jgen, SerializerProvider provider )
          throws IOException, JsonProcessingException
      {
        jgen.writeString( value.getID() );
      }
    });
    // Deserializer
    module.addDeserializer( DateTimeZone.class, new JsonDeserializer<DateTimeZone>() {
      @Override
      public DateTimeZone deserialize( JsonParser jp, DeserializationContext ctxt )
          throws IOException, JsonProcessingException
      {
        String token = jp.getText();
        try {
          return DateTimeZone.forID( token );
        } catch( IllegalArgumentException e ) {
          throw new JsonParseException( "Cannot parse DateTimeZone", jp.getCurrentLocation(), e );
        }
      }
    });
    return module;
  }
}
