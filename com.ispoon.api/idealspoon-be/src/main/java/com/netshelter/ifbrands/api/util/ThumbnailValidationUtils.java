package com.netshelter.ifbrands.api.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.entity.Story.ImageSpec;
import com.netshelter.ifbrands.api.service.EntityService;

@Component
public class ThumbnailValidationUtils
{
  @Autowired
  private EntityService entityService;
  
  public boolean validateThumbnailQuantity( Story story ) 
  {
    return validateThumbnailQuantity( story.getThumbnails() );
  }
  
  public boolean validateThumbnailQuantity( int dpStoryId ) 
  {
    Story story = entityService.getStory( dpStoryId );
    
    return validateThumbnailQuantity( story.getThumbnails() );
  }
  
  public boolean validateThumbnailQuantity( Collection<Story.ImageSpec> thumbnails ) 
  {
    boolean success = false;
    int counter = 0;
    
    // Build hard-coded dimension spec list
    Collection<ImageSpec> masterSizes = new ArrayList<ImageSpec>();
    masterSizes.add( new ImageSpec(300,200) );
    masterSizes.add( new ImageSpec(226,80) );
    masterSizes.add( new ImageSpec(120,90) );
    masterSizes.add( new ImageSpec(65,55) );
    masterSizes.add( new ImageSpec(140,70) );
    
    for( ImageSpec spec : masterSizes ) {
      for ( ImageSpec thumb : thumbnails ) {
        if ( spec.getWidth() == thumb.getWidth() && spec.getHeight() == thumb.getHeight() ) {
          counter = counter + 1;
          break;
        }
      }
    }
    
    if ( counter == masterSizes.size() ) {
      success = true;
    }
        
    return success;
  }
}
