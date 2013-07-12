package com.netshelter.ifbrands.etl.dataplatform.model;


public class DpCategorySentiment
{
  private int categoryId;
  private DpCategoryType categoryType;
  private DpSentiment sentiment;

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId( int categoryId )
  {
    this.categoryId = categoryId;
  }

  public DpCategoryType getCategoryType()
  {
    return categoryType;
  }

  public void setCategoryType( DpCategoryType categoryType )
  {
    this.categoryType = categoryType;
  }

  public DpSentiment getSentiment()
  {
    return sentiment;
  }

  public void setSentiment( DpSentiment sentiment )
  {
    this.sentiment = sentiment;
  }

  @Override
  public String toString()
  {
    return "DpCategorySentiment [categoryId=" + categoryId + ", categoryType=" + categoryType
        + ", sentiment=" + sentiment + "]";
  }

}
