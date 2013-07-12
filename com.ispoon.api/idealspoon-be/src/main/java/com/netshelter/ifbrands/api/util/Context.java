package com.netshelter.ifbrands.api.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Context implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  private Map<String,Object> configuration;

  private static InheritableThreadLocal<Context> instance = new InheritableThreadLocal<Context>() {
    @Override
    protected Context initialValue()
    {
      super.initialValue();
      return new Context();
    }

    @Override
    public void remove()
    {
      super.remove();
      instance = null;
    }
  };

  public static Context get()
  {
    return instance.get();
  }

  public Context()
  {
    this( null );
  }

  public Context( Map<String,Object> configuration )
  {
    setConfiguration( configuration );
  }

  public Map<String,Object> getConfiguration()
  {
    return configuration;
  }

  public void setConfiguration( Map<String,Object> configuration )
  {
    if( configuration == null ) configuration = new HashMap<String,Object>();
    this.configuration = Collections.synchronizedMap( configuration );
  }

  public <T> T getProperty( String name, Class<T> type )
  {
    @SuppressWarnings( "unchecked" )
    T value = (T)configuration.get( name );
    return value;
  }

  public <T> T getProperty( Enum<?> name, Class<T> type )
  {
    return getProperty( name.toString(), type );
  }

  public void setProperty( String name, Object value )
  {
    configuration.put( name, value );
  }

  @Override
  public Context clone()
  {
    return new Context( configuration );
  }

  @Override
  public int hashCode()
  {
    return 31 * configuration.hashCode();
  }

  @Override
  public boolean equals( Object obj )
  {
    if( this == obj ) return true;
    if( obj == null ) return false;
    if( !(obj instanceof Context )) return false;
    Context other = (Context)obj;
    return configuration.equals( other.getConfiguration() );
  }

  @Override
  public String toString()
  {
    return "Context [configuration="+ configuration +"]";
  }
}
