package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdNetwork;
import com.netshelter.ifbrands.data.dao.AdNetworkDao;
import com.netshelter.ifbrands.data.entity.IfbAdNetwork;
import com.netshelter.ifbrands.etl.transform.campaign.AdNetworkTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class AdNetworkServiceImpl
    implements AdNetworkService
{

    @Autowired
    private AdNetworkDao adNetworkDao = null;
    @Autowired
    private AdNetworkTransformer adNetworkTransformer = null;

    @Override
    public AdNetwork create(String adNetworkName)
    {
        IfbAdNetwork entity = new IfbAdNetwork();
        entity.setAdNetworkName(adNetworkName);
        entity.setCreateTimestamp( new Date() );
        entity = adNetworkDao.save( entity );
        return adNetworkTransformer.transform( entity );
    }

    @Override
    public AdNetwork get(Integer id)
    {
        IfbAdNetwork result = adNetworkDao.getById( id );
        return adNetworkTransformer.transform( result );
    }

    @Override
    public AdNetwork update( Integer adNetworkId, String adNetworkName )
    {
        IfbAdNetwork entity = adNetworkDao.getById( adNetworkId );
        if (entity != null)
        {
            if (adNetworkName != null)
            {
                entity.setAdNetworkName(adNetworkName);
            }
            adNetworkDao.update(entity);
        }
        return adNetworkTransformer.transform( entity );
    }

    @Override
    public boolean delete(Integer id)
    {
        boolean result = false;
        IfbAdNetwork entity = adNetworkDao.getById(id);
        if (entity != null)
        {
            adNetworkDao.delete(entity);
            result = true;
        }
        return result;
    }

    @Override
    public List<AdNetwork> all(List<Integer> adNetworkIds)
    {
        List<AdNetwork> result = null;
        if (adNetworkIds != null && adNetworkIds.size() > 0)
        {
            result = adNetworkTransformer.transform( adNetworkDao.getByIds( adNetworkIds ) );
        }
        else
        {
            result = adNetworkTransformer.transform( adNetworkDao.getAll() );
        }
        return result;
    }
}
