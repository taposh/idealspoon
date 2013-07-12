package com.netshelter.ifbrands.api.model.influence;

import java.util.Collection;
import java.util.Comparator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.netshelter.ifbrands.api.model.entity.Author;

public class InfluenceStories
{
  private int resultCount, totalResultCount, brandId;
  private LocalDate start, stop;
  private SentimentCount sentimentCount;
  private Collection<Synopsis> synopses;
  private Collection<AuthorCount> authorCounts;

  public int getResultCount()
  {
    return resultCount;
  }

  public void setResultCount( int resultCount )
  {
    this.resultCount = resultCount;
  }

  public int getTotalResultCount()
  {
    return totalResultCount;
  }

  public void setTotalResultCount( int totalResultCount )
  {
    this.totalResultCount = totalResultCount;
  }

  public int getBrandId()
  {
    return brandId;
  }

  public void setBrandId( int brandId )
  {
    this.brandId = brandId;
  }

  public LocalDate getStart()
  {
    return start;
  }

  public void setStart( LocalDate start )
  {
    this.start = start;
  }

  public LocalDate getStop()
  {
    return stop;
  }

  public void setStop( LocalDate stop )
  {
    this.stop = stop;
  }

  public Collection<Synopsis> getSynopses()
  {
    return synopses;
  }

  public SentimentCount getSentimentCount()
  {
    return sentimentCount;
  }

  public void setSentimentCount( SentimentCount sentimentCount )
  {
    this.sentimentCount = sentimentCount;
  }

  public void setSynopses( Collection<Synopsis> synopses )
  {
    this.synopses = synopses;
  }

  public Collection<AuthorCount> getAuthorCounts()
  {
    return authorCounts;
  }

  public void setAuthorCounts( Collection<AuthorCount> authorCounts )
  {
    this.authorCounts = authorCounts;
  }

  @Override
  public String toString()
  {
    return "InfluenceStories [brandId=" + brandId + ", resultCount=" + resultCount
        + ", totalResultCount=" + totalResultCount + ", start=" + start + ", stop=" + stop + "]";
  }

  public static class Synopsis
  {
    private int siteId, brandId, storyId, authorId;
    private DateTime publishTime;
    private String brandSentiment;

    public int getSiteId()
    {
      return siteId;
    }

    public void setSiteId( int siteId )
    {
      this.siteId = siteId;
    }

    public int getBrandId()
    {
      return brandId;
    }

    public void setBrandId( int brandId )
    {
      this.brandId = brandId;
    }

    public int getStoryId()
    {
      return storyId;
    }

    public void setStoryId( int storyId )
    {
      this.storyId = storyId;
    }

    public int getAuthorId()
    {
      return authorId;
    }

    public void setAuthorId( int authorId )
    {
      this.authorId = authorId;
    }

    public String getBrandSentiment()
    {
      return brandSentiment;
    }

    public void setBrandSentiment( String brandSentiment )
    {
      this.brandSentiment = brandSentiment;
    }

    public DateTime getPublishTime()
    {
      return publishTime;
    }

    public void setPublishTime( DateTime publishTime )
    {
      this.publishTime = publishTime;
    }

    @Override
    public String toString()
    {
      return "Synopsis [siteId=" + siteId + ", brandId=" + brandId + ", storyId=" + storyId + ", authorId="
          + authorId + ", publishTime=" + publishTime + ", brandSentiment=" + brandSentiment + "]";
    }

  }

  public static class AuthorCount
  {
    private int storyCount, totalScore;
    private Author author;

    public int getStoryCount()
    {
      return storyCount;
    }

    public void setStoryCount( int storyCount )
    {
      this.storyCount = storyCount;
    }

    public Author getAuthor()
    {
      return author;
    }

    public void setAuthor( Author author )
    {
      this.author = author;
    }

    public int getTotalScore()
    {
      return totalScore;
    }

    public void setTotalScore( int totalScore )
    {
      this.totalScore = totalScore;
    }

    public float getAverageScore()
    {
      return (float)totalScore / storyCount;
    }

    @Override
    public String toString()
    {
      return "AuthorCount [storyCount=" + storyCount + ", totalScore=" + totalScore + ", author=" + author
          + "]";
    }

    @Override
    public boolean equals( Object o )
    {
      return(( o instanceof AuthorCount )&&( this.getAuthor().equals( ((AuthorCount)o).getAuthor() )));
    }

    public static Comparator<AuthorCount> authorIdComparator()
    {
      return new Comparator<AuthorCount>() {
        @Override
        public int compare( AuthorCount a, AuthorCount b )
        {
          // Sort ASCENDING by AuthorId
          return a.getAuthor().getId() - b.getAuthor().getId();
        }
      };
    }

    public static Comparator<AuthorCount> totalScoreComparator()
    {
      return new Comparator<AuthorCount>() {
        @Override
        public int compare( AuthorCount a, AuthorCount b )
        {
          // Sort DESCENDING by total score
          return a.getTotalScore() - b.getTotalScore();
        }
      };
    }

    public static Comparator<AuthorCount> storyCountComparator()
    {
      return new Comparator<AuthorCount>() {
        @Override
        public int compare( AuthorCount a, AuthorCount b )
        {
          // Sort DESCENDING by story count
          return a.getStoryCount() - b.getStoryCount();
        }
      };
    }
  }

  public static class SentimentCount
  {
    private int positiveCount, neutralCount, negativeCount, noneCount;

    public int getPositiveCount()
    {
      return positiveCount;
    }

    public void setPositiveCount( int positiveCount )
    {
      this.positiveCount = positiveCount;
    }

    public int getNeutralCount()
    {
      return neutralCount;
    }

    public void setNeutralCount( int neutralCount )
    {
      this.neutralCount = neutralCount;
    }

    public int getNegativeCount()
    {
      return negativeCount;
    }

    public void setNegativeCount( int negativeCount )
    {
      this.negativeCount = negativeCount;
    }

    public int getNoneCount()
    {
      return noneCount;
    }

    public void setNoneCount( int noneCount )
    {
      this.noneCount = noneCount;
    }

    @Override
    public String toString()
    {
      return "SentimentCount [positiveCount=" + positiveCount + ", neutralCount=" + neutralCount
          + ", negativeCount=" + negativeCount + ", noneCount=" + noneCount + "]";
    }

  }
}
