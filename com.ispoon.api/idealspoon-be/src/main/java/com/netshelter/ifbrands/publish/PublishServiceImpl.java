package com.netshelter.ifbrands.publish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.campaign.Feed;
import com.netshelter.ifbrands.api.model.campaign.FeedStory;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.entity.Story.ImageSpec;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.api.service.FeedService;
import com.netshelter.ifbrands.api.service.InfluenceService;
import com.netshelter.ifbrands.api.util.ThumbnailValidationUtils;
import com.netshelter.ifbrands.data.dao.FeedDao;
import com.netshelter.ifbrands.data.entity.IfbFeedStory;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.util.CustomCharacterEscapes;

/**
 * Implements publishing story feeds to persistent cloud storage (AWS S3)
 *
 * @author ekrevets
 */
public class PublishServiceImpl implements PublishService
{
  Logger logger = LoggerFactory.getLogger();
  @Autowired
  private AmazonS3 amazonS3;
  @Autowired
  private EntityService entityService;
  @Autowired
  private FeedService feedService;
  @Autowired
  private InfluenceService influenceService;
  @Autowired
  private DpServices dpServices;
  @Autowired
  private FeedDao feedDao;

  private static String EXTENSION_UNCOMPRESSED = ".json";
  private static String EXTENSION_COMPRESSED = ".jsongz";
  private static String KEYROOT = "xml/posts/feed_";
  private static String KEYROOT_SMALL = "xml/posts_small/feed_";


  // Pull this from ifbrands.properties
  private String storyFeedBucket, cdnFeedEndPoint;

  public void setStoryFeedBucket( String storyFeedBucket )
  {
    this.storyFeedBucket = storyFeedBucket;
  }
  
  public void setCdnFeedEndPoint( String cdnFeedEndPoint )
  {
    this.cdnFeedEndPoint = cdnFeedEndPoint;
  }

  /**
   * Entry point into feed publishing functionality.  Takes care of logistics of marshalling local
   * and Data Platform data into a JSON representation in an S3 bucket
   *
   * @param feedId
   * @throws JsonGenerationException
   * @throws JsonMappingException
   * @throws IOException
   */
  @Override
  public void publishFeed( int feedId )
  {
    Feed feed = feedService.getFeed( feedId );

    Map<String, Object> boxes = buildJsonFeed( feed.getFeedStories() );
    
    @SuppressWarnings( "unchecked" )
    Collection<JsonFeedStorySmall> small = (Collection<JsonFeedStorySmall>)boxes.get( "jsonFeedStoriesSmall" );
    @SuppressWarnings( "unchecked" )
    Collection<JsonFeedStoryFull> full = (Collection<JsonFeedStoryFull>)boxes.get( "jsonFeedStoriesFull" );
    
    mapFeed( small, feed, "small" );
    mapFeed( full, feed, "full" );
  }

  private <E> void mapFeed( Collection<E> jsonFeedStories, Feed feed, String suffix ) 
  {
    ObjectMapper mapper = new ObjectMapper();
    
    // Custom character escape modification for forward slashes
    // This prevents issues with JSON-P and breaking of </script> tags
    mapper.getJsonFactory().setCharacterEscapes( new CustomCharacterEscapes() );

    String feedStoriesString = "";
    try {
      feedStoriesString = mapper.writeValueAsString( jsonFeedStories );
    } catch ( JsonGenerationException e ) {
      e.printStackTrace();
    } catch ( JsonMappingException e ) {
      e.printStackTrace();
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    // Pre/post fix necessary legacy JS call-back wrapper
    feedStoriesString = "netShelter._onStoryFeedReturned(" +
                        String.format( "\"%s\"", feed.getKey() ) +
                        ", " +
                        feedStoriesString +
                        ", " +
                        "true);";
    
    putToS3( feedStoriesString, feed, OutputType.TEXT, suffix );
    putToS3( feedStoriesString, feed, OutputType.GZIP, suffix );
  }
  
  /**
   * Do the work of putting the string into a file and storing to S3 as text or gzip format
   *
   * @param feedStoriesString The string to put to S3 bucket
   * @param feed The source feed
   * @param storeType The type of storage to use when putting to S3 ("text", "gzip")
   * @throws IOException
   */
  private void putToS3( String feedStoriesString, Feed feed, OutputType outputType, String suffix )
  {
    String extension = null;
    // convert String into InputStream
    InputStream inputStream = null;

    ObjectMetadata objectMetadata = new ObjectMetadata();

    objectMetadata.setContentType( "text/plain; charset=UTF-8" );
    objectMetadata.setCacheControl( "max-age=1800" );

    // Add on encoding for compressed feeds
    if ( outputType.equals( OutputType.GZIP ) ) {
      extension = EXTENSION_COMPRESSED;

      objectMetadata.setContentEncoding( "gzip" );

      ByteArrayOutputStream os = new ByteArrayOutputStream( feedStoriesString.length() );

      GZIPOutputStream gzos;
      try {
        gzos = new GZIPOutputStream( os );

        gzos.write( feedStoriesString.getBytes() );

        gzos.finish();

        byte[] compressed = os.toByteArray();

        inputStream = new ByteArrayInputStream( compressed );

      } catch ( IOException e ) {
        throw new RuntimeException( "Cannot create output stream", e );
      } finally {
        IOUtils.closeQuietly( os );
      }

    }
    // Regular uncompressed feeds
    else if ( outputType.equals( OutputType.TEXT ) ) {
      extension = EXTENSION_UNCOMPRESSED;

      inputStream = new ByteArrayInputStream( feedStoriesString.getBytes() );
    }

    String keyRoot = KEYROOT;
    if ( suffix == "small" ) {
      keyRoot = KEYROOT_SMALL;
    }
    
    PutObjectRequest putRequest = new PutObjectRequest( storyFeedBucket, keyRoot + feed.getKey() + extension, inputStream, objectMetadata);

    // Make it web accessible
    putRequest.setCannedAcl( CannedAccessControlList.PublicRead );

    // Catch issues here, not sure what to do additionally yet
    try {
      amazonS3.putObject( putRequest );
      logger.info( "Tried to publish %s feed to S3: %s", outputType, storyFeedBucket + "/" + keyRoot + feed.getKey() + extension );
    }
    catch ( AmazonServiceException e ) {
      logger.debug( "Failed to publish %s feed to S3: %s", outputType, storyFeedBucket + "/" + keyRoot + feed.getKey() + extension );
      e.printStackTrace();
    }
    catch ( AmazonClientException e ) {
      logger.debug( "Failed to publish %s feed to S3: %s", outputType, storyFeedBucket + "/" + keyRoot + feed.getKey() + extension );
      e.printStackTrace();
    }
  }

  /**
   * Collects all necessary data sources into a JSON representation of an Ad Products story feed
   * Only includes Active stories
   *
   * @param feedStories Collection of feedStories to add to the JSON feed
   */
  private Map<String, Object> buildJsonFeed( Collection<FeedStory> feedStories )
  {
    ThumbnailValidationUtils thumbnailValidationUtils = new ThumbnailValidationUtils();
    
    Collection<JsonFeedStoryFull> jsonFeedStoriesFull = new ArrayList<JsonFeedStoryFull>();
    Collection<JsonFeedStorySmall> jsonFeedStoriesSmall = new ArrayList<JsonFeedStorySmall>();
    boolean buildSuccess = false;    
    // Prep a map for possible feedStory failures
    //Map<Integer, List<FeedStoryFailure>> feedStoryFailures = new HashMap<Integer, List<FeedStoryFailure>>();
  

    for ( FeedStory feedStory : feedStories ) {

      // Don't include inactive stories in published feed
      if ( !feedService.isFeedStoryActive( feedStory ) ) continue;
      
      logger.info( "Flushing story %s", feedStory.getStoryId() );

      // Primary flush (with no cache key prefix)
      entityService.flushStory( feedStory.getStoryId() );
      
      dpServices.setDpMasterMode( true );
      
      // Secondary flush (with "config=admin" cache key prefix)
      entityService.flushStory( feedStory.getStoryId() );
      
      Story story = entityService.getStory( feedStory.getStoryId() );
  
      // Do we have the right amount of thumbnails?  If no, force re-generation
      if ( thumbnailValidationUtils.validateThumbnailQuantity( story.getThumbnails() ) == false ) {
        logger.info( "Story %s is missing thumbnails, re-generating", story.getId() );
        buildSuccess = feedService.buildAllStoryThumbnails( story.getId() );
        if( buildSuccess == false ) {
          logger.info( "Story %s generation of thumbnails failed, de-activating in feed", story.getId() );
          IfbFeedStory ifbFeedStory = feedDao.feedStoryDao.getById( feedStory.getId() );
          ifbFeedStory.setIfbFeedStoryStatus( feedDao.feedStoryStatusDao.INACTIVE );
          feedDao.feedStoryDao.update( ifbFeedStory );
          continue;
        }
        entityService.flushStory( feedStory.getStoryId() );
        story = entityService.getStory( feedStory.getStoryId() );
      }
      
      dpServices.setDpMasterMode( false );

      if ( story == null ) throw new IllegalArgumentException( String.format( "Story with id %s cannot be found", feedStory.getStoryId() ) );

      Map<String,String> metricsMap = influenceService.getEngagementMetrics( feedStory.getStoryId() );

      JsonFeedStoryFull jsonFeedStoryFull = new JsonFeedStoryFull();

      jsonFeedStoryFull.setPostId( feedStory.getStoryId().toString() );
      jsonFeedStoryFull.setPostHash( story.getHash() );
      jsonFeedStoryFull.setTitle( story.getTitle() );
      jsonFeedStoryFull.setSummary( story.getSummary() );
      jsonFeedStoryFull.setSiteName( story.getSite().getDomain() );
      jsonFeedStoryFull.setSiteTitle( story.getSite().getName() );
      jsonFeedStoryFull.setSiteId( story.getSite().getId().toString() );
      jsonFeedStoryFull.setLink( story.getStoryUrl() );
      jsonFeedStoryFull.setAuthor( story.getAuthor().getName() );
      jsonFeedStoryFull.setAuthorId( story.getAuthor().getId().toString() );
      jsonFeedStoryFull.setUpdated( String.valueOf( story.getPublishTime().getMillis()/1000 ) );
      jsonFeedStoryFull.setEvents( metricsMap.get( "events" ) );
      jsonFeedStoryFull.setPoints( metricsMap.get( "points" ) );
      jsonFeedStoryFull.setMetrics( metricsMap.get( "metrics" ) );
      jsonFeedStoryFull.setImgUrl( story.getImageUrl() );
      jsonFeedStoryFull.setImgUrlCustom( story.getImageUrl() );
      jsonFeedStoryFull.setTopics( null );
      jsonFeedStoryFull.setRowId( null );

      Map<String,String> thumbnails = getThumbnailsMap( story );
      jsonFeedStoryFull.setThumbnails( thumbnails );

      // Only set thumbnailsGenerated flag if thumbnails exist
      if ( thumbnails != null ) {
        jsonFeedStoryFull.setThumbnailGenerated( "1" );
      }

      jsonFeedStoriesFull.add( jsonFeedStoryFull );
      
      // The below is the optimized version of the JSON to be consumed by new Ad units
      JsonFeedStorySmall jsonFeedStorySmall = new JsonFeedStorySmall();

      //Map<String,String> metricsMapUnjoined = influenceService.getEngagementMetricsUnjoined( feedStory.getStoryId() );
      
      jsonFeedStorySmall.setPostHash( story.getHash() );
      jsonFeedStorySmall.setTitle( story.getTitle() );
      jsonFeedStorySmall.setSiteName( story.getSite().getDomain() );
      jsonFeedStorySmall.setSiteTitle( story.getSite().getName() );
      jsonFeedStorySmall.setLink( story.getStoryUrl() );
      jsonFeedStorySmall.setMetrics( metricsMap.get( "metrics" ) );
      
      String thumbnailsVersion = getThumbnailsVersion( story );
      jsonFeedStorySmall.setThumbnailsVersion( thumbnailsVersion );

      jsonFeedStoriesSmall.add( jsonFeedStorySmall );
    }
    
    Map<String, Object> boxes = new HashMap<String, Object>();
    boxes.put( "jsonFeedStoriesFull", jsonFeedStoriesFull );
    boxes.put( "jsonFeedStoriesSmall", jsonFeedStoriesSmall );
    
    return boxes;
  }

  private String getThumbnailsVersion( Story story )
  {
    String thumbUrl = null;
    Collection<ImageSpec> storyThumbnails = story.getThumbnails();
    
    String version = null;
    if ( storyThumbnails.size() > 0 ) {
      ImageSpec imageSpec = storyThumbnails.iterator().next();
      
      thumbUrl = imageSpec.getUrl();
      
      String re = ".*?_\\d+_\\d+_(\\d+).jpg";
      Pattern p = Pattern.compile( re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
      Matcher m = p.matcher( thumbUrl ); 
      
      if (m.find())
      {
          version = m.group(1);
      }
    }
    
    return version;
  }

  /**
   * Build a specially formatted Map for thumbnails in the legacy JSON response that Ads read
   *
   * @param story Takes a story to build thumbnails map for
   */
  private Map<String,String> getThumbnailsMap( Story story )
  {
    Collection<ImageSpec> storyThumbnails = story.getThumbnails();
    if ( storyThumbnails == null || storyThumbnails.isEmpty() ) return null;

    Map<String,String> thumbnails = new HashMap<String,String>();

    for ( ImageSpec imageSpec : storyThumbnails ) {
      thumbnails.put( String.format( "thumbnail_%sx%s", imageSpec.getWidth(), imageSpec.getHeight() ), imageSpec.getUrl() );
    }

    return thumbnails;
  }

  /**
   * Generates and returns a feed url where the story feed resides in S3
   *
   * @param feedKey The feed key
   * @return String The feed URI on S3
   */
//  @Override
//  public String getFeedUrl( String feedKey )
//  {
//    return String.format( "http://%s/%s", storyFeedBucket, KEYROOT + feedKey + EXTENSION_UNCOMPRESSED );
//  }
  
  @Override
  public String getGeneratedFeedUrl( String feedKey )
  {
    return String.format( "http://%s/%s", cdnFeedEndPoint, KEYROOT + feedKey + EXTENSION_COMPRESSED );
  }
}
