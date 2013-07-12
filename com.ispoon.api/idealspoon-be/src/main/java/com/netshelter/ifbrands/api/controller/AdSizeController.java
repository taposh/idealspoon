package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.campaign.Ad;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import com.netshelter.ifbrands.api.service.AdService;
import com.netshelter.ifbrands.api.service.AdSizeService;
import com.netshelter.ifbrands.api.util.MvcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * Controller for 'ad_size' API calls
 *
 * @author Dmitriy T
 */


@Controller( "adSizeController" )
@RequestMapping( "/adsize" )
public class AdSizeController
    extends BaseController
{
    @Autowired
    private AdSizeService adSizeService;

    @RequestMapping( value = "/create" )
    @ResponseBody
    public AdSize createAdSize(
            @RequestParam( "name"     )             String name,
            @RequestParam( "width"     )            Integer width,
            @RequestParam( "height"   )             Integer height
    )
    {
        logger.info( "/adsize/create [%s,%s,%s]", name, width, height);
        AdSize result = adSizeService.create(name, width, height);
        logger.debug("...Created %s", result.toString());
        return result;
    }

    @RequestMapping( value = "/delete/{id}" )
    @ResponseBody
    public GenericStatus deleteAdSize(
            @PathVariable( "id" ) Integer adSizeId
    )
    {
        logger.info( "/adsize/delete/%s", adSizeId );
        boolean success = adSizeService.delete(adSizeId);
        return GenericStatus.successFail( success );
    }

    @RequestMapping( value = "/update/{id}" )
    @ResponseBody
    public AdSize updateAdSize( @PathVariable( "id" ) Integer adSizeId,
                        @RequestParam( value="name"  , required=false )     String name,
                        @RequestParam( value="width"  , required=false )    Integer width,
                        @RequestParam( value="height", required=false )     Integer height
    )
    {
        logger.info( "/adsize/update/%s [%s,%s,%s]", adSizeId, name, width, height );
        return adSizeService.update(adSizeId, name, width, height);
    }

    @RequestMapping( value = "/list/{ids}" )
    @ResponseBody
    public GenericPayload<Collection<AdSize>> getList(
            @PathVariable( "ids" ) String ids
    )
    {
        logger.info( "/adsize/list" );
        List<Integer> adSizeIds = MvcUtils.getIdsFromFilter( ids );
        Collection<AdSize> adSizes = adSizeService.all( adSizeIds );
        logger.debug("...%d found", adSizes.size());
        return new GenericPayload<Collection<AdSize>>( "adSizes", adSizes );
    }
}
