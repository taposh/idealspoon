package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbAdSize;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

/**
 * User: Dmitriy T
 */
public class AdSizeDaoTest
    extends BaseDaoTest<IfbAdSize>
{

    private final Random r = new Random();

    @Autowired
    AdSizeDao dao;

    @Override
    protected BaseDao<IfbAdSize> getDao()
    {
        return dao;
    }

    @Override
    protected IfbAdSize makeEntity()
    {
        IfbAdSize entity = new IfbAdSize();
        entity.setAdSizeName("test-" + r.nextDouble());
        entity.setWidth(100);
        entity.setHeight(100);
        entity.setCreateTimestamp(new Date());
        return dao.save( entity );
    }
}
