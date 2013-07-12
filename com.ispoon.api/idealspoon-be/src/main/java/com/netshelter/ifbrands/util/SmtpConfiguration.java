package com.netshelter.ifbrands.util;

public class SmtpConfiguration
{
  private String server, username, password;
  private Integer port;
  private boolean ssl=false;

  public void setServer( String server )
  {
    this.server = server;
  }
  public void setPort( Integer port )
  {
    this.port = port;
  }
  public void setSsl( boolean ssl )
  {
    this.ssl = ssl;
  }
  public void setUsername( String username )
  {
    this.username = username;
  }
  public void setPassword( String password )
  {
    this.password = password;
  }
  public boolean isValid()
  {
    return(( server != null )&&( username != null ));
  }

  public String toCamelUri()
  {
    StringBuilder sb = new StringBuilder();
    sb.append( ssl ? "smtps" : "smtp" );
    sb.append( "://" );
    sb.append( server );
    if( port != null ) sb.append( ':' ).append( port );
    sb.append( '?' );
    sb.append( "username=" ).append( username ).append( '&' );
    if( password != null ) sb.append( "password=" ).append( password ).append( '&' );
    // Truncate trailing character to appease whiny endpoint
    sb.deleteCharAt( sb.length()-1 );
    return sb.toString();
  }
  @Override
  public String toString()
  {
    return "SmtpConfiguration [server=" + server + ", username=" + username + ", password=" + password
        + ", port=" + port + ", ssl=" + ssl + "]";
  }

}
