package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.api.model.campaign.ObjectTypeEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingSetEnum;
import com.netshelter.ifbrands.api.model.campaign.TrackingTypeEnum;
import com.netshelter.ifbrands.data.entity.IfbTracking;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class TrackingDaoTest
        extends BaseDaoTest<IfbTracking>
{

    private final Random r = new Random();

    @Autowired
    TrackingDao dao;

    @Override
    protected BaseDao<IfbTracking> getDao()
    {
        return dao;
    }

    @Override
    protected IfbTracking makeEntity()
    {
        IfbTracking entity = new IfbTracking();
        entity.setTextValue("test-" + r.nextDouble());
        entity.setObjectId(12345);
        entity.setObjectType(ObjectTypeEnum.Campaign.name());
        entity.setTrackingSet(TrackingSetEnum.PostClickTracking.name());
        entity.setTrackingType(TrackingTypeEnum.JavaScript.name());
        entity.setCreateTimestamp(new Date());
        return dao.save( entity );
    }
}
