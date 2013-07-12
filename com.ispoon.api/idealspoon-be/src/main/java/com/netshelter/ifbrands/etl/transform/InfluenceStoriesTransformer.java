package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Functions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultiset;
import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories.AuthorCount;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories.SentimentCount;
import com.netshelter.ifbrands.api.model.influence.InfluenceStories.Synopsis;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategorySentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.DpSentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStoryInfluence;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStoryInfluenceAndSentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceAndSentimentStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetInfluenceStoriesResponse;

@Component
public class InfluenceStoriesTransformer
{
  @Autowired
  private EntityService entityService;

  public InfluenceStories transform( GetInfluenceStoriesResponse response )
  {
    InfluenceStories result = transform( response.getCategoryId(), response.getStoryInfluence() );
    result.setBrandId( response.getCategoryId() );
    result.setResultCount( response.getResultCount() );
    result.setTotalResultCount( response.getTotalResultCount() );
    return result;
  }

  public InfluenceStories transform( GetInfluenceAndSentimentStoriesResponse response )
  {
    InfluenceStories result = transform( response.getCategoryId(), response.getStoryInfluenceAndSentiment() );
    result.setBrandId( response.getCategoryId() );
    result.setResultCount( response.getResultCount() );
    result.setTotalResultCount( response.getTotalResultCount() );

    // Find brand sentiment for each story
    // Synopsis is 1:1 with DpStoryInfluence
    int posv=0, neut=0, negv=0, none=0;
    Iterator<Synopsis> synIter = result.getSynopses().iterator();
    Iterator<DpStoryInfluenceAndSentiment> dsiIter = response.getStoryInfluenceAndSentiment().iterator();
    while( synIter.hasNext() ) {
      Synopsis syn = synIter.next();
      DpStoryInfluenceAndSentiment dsi = dsiIter.next();
      // Find the brand sentiment within the dsi (NONE if not found)
      DpSentiment sentiment = DpSentiment.NONE;
      for( DpCategorySentiment dcs: dsi.getCategorySentiments() ) {
        if( dcs.getCategoryId() == response.getCategoryId() ) {
          sentiment = dcs.getSentiment();
        }
      }
      syn.setBrandSentiment( sentiment.toString() );
      // Total up sentiment frequency
      switch( sentiment ) {
        case POSITIVE: posv++; break;
        case NEGATIVE: negv++; break;
        case NEUTRAL : neut++; break;
        case NONE    : none++; break;
      }
    }

    SentimentCount sentimentCount = new SentimentCount();
    sentimentCount.setPositiveCount( posv );
    sentimentCount.setNegativeCount( negv );
    sentimentCount.setNeutralCount( neut );
    sentimentCount.setNoneCount( none );
    result.setSentimentCount( sentimentCount );

    return result;
  }

  private InfluenceStories transform( int categoryId, Iterable<? extends DpStoryInfluence> dsiList )
  {
    InfluenceStories result = new InfluenceStories();

    // Author histogram
    Multiset<Integer> authorStory = TreeMultiset.<Integer>create();
    Multiset<Integer> authorScore = TreeMultiset.<Integer>create();

    // Do synopses first
    List<Synopsis> synopses = new ArrayList<Synopsis>();
    for( DpStoryInfluence dsi: dsiList ) {
      Synopsis syn = new InfluenceStories.Synopsis();
      syn.setSiteId( dsi.getSynopsis().getSiteId() );
      syn.setBrandId( categoryId );
      syn.setStoryId( dsi.getSynopsis().getStoryId() );
      syn.setAuthorId( dsi.getSynopsis().getAuthorId() );
      syn.setPublishTime( dsi.getSynopsis().getPublishTime() );
      synopses.add( syn );

      // Count & score authors
      authorStory.add( syn.getAuthorId() );
      authorScore.add( syn.getAuthorId(), (int)dsi.getScoredInfluence().getScore() );
    }
    result.setSynopses( synopses );

    // Do AuthorCount
    Map<Integer,AuthorCount> counts = new HashMap<Integer,AuthorCount>();
    for( Multiset.Entry<Integer> e: authorStory.entrySet() ) {
      // Get id & count
      int authorId = e.getElement().intValue();
      int count = e.getCount();
      int score = authorScore.count( e.getElement() );

      // Placeholder for Author
      Author author = new Author( authorId );

      // Create entry
      AuthorCount ac = new AuthorCount();
      ac.setAuthor( author );
      ac.setStoryCount( count );
      ac.setTotalScore( score );
      counts.put( authorId, ac );
    }

    // Get keys (AuthorIds) representing top results
    final int authorCountLimit = 50;
    List<Integer> topIds =
        Ordering
//        .from( AuthorCount.totalScoreComparator() )
        .from( AuthorCount.storyCountComparator() )
        .onResultOf( Functions.forMap( counts ))
        .greatestOf( counts.keySet(), authorCountLimit );

    // Fill in Author info for top authors (result set is unordered)
    Collection<Author> topAuthors = entityService.getAuthors( topIds );
    for( Author author: topAuthors ) {
      counts.get( author.getId() ).setAuthor( author );
    }

    // Prepare return collection of top authors
    List<AuthorCount> sorted = new ArrayList<AuthorCount>( topIds.size() );
    for( Integer id: topIds ) {
      sorted.add( counts.get( id ));
    }
    result.setAuthorCounts( sorted );
    return result;
  }
}
