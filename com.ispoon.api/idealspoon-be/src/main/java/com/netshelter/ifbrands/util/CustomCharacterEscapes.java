package com.netshelter.ifbrands.util;

import org.codehaus.jackson.SerializableString;
import org.codehaus.jackson.io.CharacterEscapes;

/**
 * Jackson serializer customisation 
 * Add escapeable character, forward slash to standard escapeable characters
 * @author ekrevets
 *
 */
public class CustomCharacterEscapes extends CharacterEscapes 
{
  private final int[] _asciiEscapes;

  public CustomCharacterEscapes() {
    _asciiEscapes = standardAsciiEscapesForJSON();
    _asciiEscapes['/'] = CharacterEscapes.ESCAPE_CUSTOM;
  }

  @Override
  public int[] getEscapeCodesForAscii() {
    return _asciiEscapes;
  }

  @Override
  public SerializableString getEscapeSequence( int ch )
  {
    if( ch == '/' ){
      return new SerializableString() {
          @Override
          public String getValue() {
              return "\\/";
          }

          @Override
          public int charLength() {
              return 2;
          }

          @Override
          public char[] asQuotedChars() {
              return new char[]{'\\','/'};
          }

          @Override
          public byte[] asUnquotedUTF8() {
              return new byte[]{'\\','/'};
          }

          @Override
          public byte[] asQuotedUTF8() {
              return new byte[]{'\\','/'};
          }
      };
    }
    else{
        return null;
    }
  }

}