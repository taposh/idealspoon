package com.netshelter.ifbrands.etl.transform.campaign;

import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.api.model.campaign.AdSize;
import com.netshelter.ifbrands.data.entity.IfbAdNetwork;
import com.netshelter.ifbrands.data.entity.IfbAdSize;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dmitriy T
 */
@Component
public class AdNetworkTransformer
{
    public AdNetwork transform( IfbAdNetwork e )
    {
        if( e == null ) return null;
        AdNetwork p = new AdNetwork(
                e.getAdNetworkId(),
                e.getAdNetworkName(),
                new DateTime(e.getCreateTimestamp())
        );

        return p;
    }

    public List<AdNetwork> transform( Collection<IfbAdNetwork> list )
    {
        List<AdNetwork> results = new ArrayList<AdNetwork>( list.size() );
        for( IfbAdNetwork e: list ) {
            results.add( transform( e ));
        }
        return results;
    }
}
