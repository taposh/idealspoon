package com.netshelter.ifbrands.api.model.influence;

import com.netshelter.ifbrands.api.model.entity.Brand;

public class BrandSentiment
{
  private Brand brand;
  private String sentiment;

  public Brand getBrand()
  {
    return brand;
  }

  public void setBrand( Brand brand )
  {
    this.brand = brand;
  }

  public String getSentiment()
  {
    return sentiment;
  }

  public void setSentiment( String sentiment )
  {
    this.sentiment = sentiment;
  }

  @Override
  public String toString()
  {
    return "BrandSentiment [brand=" + brand.getName() + ", sentiment=" + sentiment + "]";
  }
}
