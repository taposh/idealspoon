package com.netshelter.ifbrands.etl.dataplatform.model;

public class DpImageSpec
{
  private int width, height;
  private String url, secureUrl, uri;

  public int getWidth()
  {
    return width;
  }

  public void setWidth( int width )
  {
    this.width = width;
  }

  public int getHeight()
  {
    return height;
  }

  public void setHeight( int height )
  {
    this.height = height;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public String getSecureUrl()
  {
    return secureUrl;
  }

  public void setSecureUrl(String secureUrl)
  {
    this.secureUrl = secureUrl;
  }

  public String getUri()
  {
    return uri;
  }

  public void setUri( String uri )
  {
    this.uri = uri;
  }

  @Override
  public String toString()
  {
    return "DpImageSpec [width=" + width + ", height=" + height + ", url=" + url + ", secureUrl=" + secureUrl
        + "]";
  }

}
