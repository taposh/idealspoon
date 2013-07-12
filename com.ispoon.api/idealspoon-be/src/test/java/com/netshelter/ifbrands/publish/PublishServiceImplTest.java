package com.netshelter.ifbrands.publish;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.netshelter.ifbrands.api.service.FeedService;
import com.netshelter.ifbrands.data.dao.FeedDao.FeedStoryStatusDao;
import com.netshelter.ifbrands.data.dao.FeedDaoTest;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( { "classpath:common-context.xml", "classpath:etl-context.xml", "classpath:mvc-context.xml", "classpath:data-context.xml", "classpath:aws-context.xml" } )
public class PublishServiceImplTest
{
  @Autowired
  private FeedService feedService;
  @Autowired
  private FeedDaoTest feedDaoTest;
  @Autowired
  private FeedStoryStatusDao feedStoryStatusDao;
  @Autowired
  private AmazonS3 amazonS3;

  private static String keyRoot = "xml/posts/feed_";
  private static String storyFeedBucket = "files.dev.netshelter.net";

  @Test
  public void publishFeedTest() throws JsonGenerationException, JsonMappingException, IOException
  {
    List<Integer> stories = new ArrayList<Integer>();
    //stories.add( 1188852 );
    stories.add( 2883440 );
    stories.add( 2883441 );
    stories.add( 2883442 );

    int fId = feedDaoTest.makeEntity().getFeedId();

    // Publish implicitly on addFeedStory()
    feedService.addFeedStory( fId, stories, feedStoryStatusDao.ACTIVE.getFeedStoryStatusId(), null, null );

    S3Object s3Object = amazonS3.getObject( storyFeedBucket, keyRoot + feedService.getFeed( fId ).getKey() + ".json" );

    InputStream objectData = s3Object.getObjectContent();

    assertNotNull( objectData );

    // Close the stream
    objectData.close();
  }

}
