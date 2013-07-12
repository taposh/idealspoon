package com.netshelter.ifbrands.etl.transform.campaign;

import com.netshelter.ifbrands.api.model.campaign.AdSize;
import com.netshelter.ifbrands.data.entity.IfbAdSize;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Dmitriy T
 */
@Component
public class AdSizeTransformer
{
    public AdSize transform( IfbAdSize e )
    {
        if( e == null ) return null;
        AdSize p = new AdSize(
                e.getAdSizeId(),
                e.getAdSizeName(),
                e.getWidth(),
                e.getHeight(),
                new DateTime(e.getCreateTimestamp())
        );

        return p;
    }

    public List<AdSize> transform( Collection<IfbAdSize> list )
    {
        List<AdSize> results = new ArrayList<AdSize>( list.size() );
        for( IfbAdSize e: list ) {
            results.add( transform( e ));
        }
        return results;
    }
}
