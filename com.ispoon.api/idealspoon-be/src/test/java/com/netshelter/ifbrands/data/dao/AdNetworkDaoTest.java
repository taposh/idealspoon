package com.netshelter.ifbrands.data.dao;

import com.netshelter.ifbrands.data.entity.IfbAdNetwork;
import com.netshelter.ifbrands.data.entity.IfbAdSize;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

/**
 * @author Dmitriy T
 */
public class AdNetworkDaoTest
    extends BaseDaoTest<IfbAdNetwork>
{

    private final Random r = new Random();

    @Autowired
    AdNetworkDao dao;

    @Override
    protected BaseDao<IfbAdNetwork> getDao()
    {
        return dao;
    }

    @Override
    protected IfbAdNetwork makeEntity()
    {
        IfbAdNetwork entity = new IfbAdNetwork();
        entity.setAdNetworkName("test-" + r.nextDouble());
        entity.setCreateTimestamp(new Date());
        return dao.save( entity );
    }
}
