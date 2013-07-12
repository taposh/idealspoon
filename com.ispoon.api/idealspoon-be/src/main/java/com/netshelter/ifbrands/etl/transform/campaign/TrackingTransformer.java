package com.netshelter.ifbrands.etl.transform.campaign;

import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.Tracking;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.data.entity.IfbTracking;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dmitriy T
 */
@Component
public class TrackingTransformer
{

    public Tracking transform( IfbTracking e )
    {
        if( e == null ) return null;
        Tracking p = new Tracking(e.getTrackingId(),
                                  e.getObjectId(),
                                  ObjectTypeEnum.valueOf(e.getObjectType()),
                                  TrackingSetEnum.valueOf(e.getTrackingSet()),
                                  TrackingTypeEnum.valueOf(e.getTrackingType()),
                                  e.getTextValue(),
                                  new DateTime( e.getCreateTimestamp() )
        );

        return p;
    }

    public List<Tracking> transform( Collection<IfbTracking> list )
    {
        List<Tracking> results = new ArrayList<Tracking>( list.size() );
        for( IfbTracking e: list ) {
            results.add( transform( e ));
        }
        return results;
    }

}
