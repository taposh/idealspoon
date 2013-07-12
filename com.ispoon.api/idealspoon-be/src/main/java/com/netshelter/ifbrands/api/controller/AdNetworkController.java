package com.netshelter.ifbrands.api.controller;

import com.netshelter.ifbrands.api.model.GenericPayload;
import com.netshelter.ifbrands.api.model.GenericStatus;
import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.service.AdNetworkService;
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
 * @author Dmitriy T
 */
@Controller( "adNetworkController" )
@RequestMapping( "/adnetwork" )
public class AdNetworkController
    extends BaseController
{
    @Autowired
    private AdNetworkService adNetworkService;

    @RequestMapping( value = "/create" )
    @ResponseBody
    public AdNetwork createAdNetwork(
            @RequestParam( "name"     )             String name
    )
    {
        logger.info( "/adnetwork/create [%s]", name);
        AdNetwork result = adNetworkService.create(name);
        logger.debug("...Created %s", result.toString());
        return result;
    }

    @RequestMapping( value = "/delete/{id}" )
    @ResponseBody
    public GenericStatus deleteAdNetwork(
            @PathVariable( "id" ) Integer adNetworkId
    )
    {
        logger.info( "/adnetwork/delete/%s", adNetworkId );
        boolean success = adNetworkService.delete( adNetworkId );
        return GenericStatus.successFail( success );
    }

    @RequestMapping( value = "/update/{id}" )
    @ResponseBody
    public AdNetwork updateAdNetwork( @PathVariable( "id" ) Integer adNetworkId,
                            @RequestParam( value="name"  , required=false )     String name
    )
    {
        logger.info( "/adnetwork/update/%s [%s]", adNetworkId, name );
        return adNetworkService.update( adNetworkId, name );
    }

    @RequestMapping( value = "/list/{ids}" )
    @ResponseBody
    public GenericPayload<Collection<AdNetwork>> getList(
            @PathVariable( "ids" ) String ids
    )
    {
        logger.info( "/adnetwork/list" );
        List<Integer> adNetworkIds = MvcUtils.getIdsFromFilter(ids);
        Collection<AdNetwork> adNetworks = adNetworkService.all( adNetworkIds );
        logger.debug("...%d found", adNetworks.size());
        return new GenericPayload<Collection<AdNetwork>>( "adNetworks", adNetworks );
    }
}
