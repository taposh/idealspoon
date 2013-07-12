package com.netshelter.ifbrands.etl.analytics.model;

public enum NapDimension
{
  CAMPAIGN, CREATIVE, ASSET, SITE, PLACEMENT, GENRE, CATEGORY, SUBCATEGORY, KIND, PARENT, SPECIAL;

  @Override
  public String toString()
  {
    return super.toString().toLowerCase();
  }
}
