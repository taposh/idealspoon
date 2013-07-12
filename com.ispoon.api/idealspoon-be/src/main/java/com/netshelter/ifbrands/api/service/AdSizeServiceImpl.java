package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.campaign.AdSize;
import com.netshelter.ifbrands.data.dao.AdSizeDao;
import com.netshelter.ifbrands.data.entity.IfbAdSize;
import com.netshelter.ifbrands.etl.transform.campaign.AdSizeTransformer;
import org.apache.commons.collections.map.LRUMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * AdSizeService provides high level access to AdSize entities.
 * Using LRU map to cache retrievable responses
 *
 * User: Dmitriy T
 */
public class AdSizeServiceImpl
    implements AdSizeService
{

    /**
     * This LRU map is used as cache which is cleared on CREATE, UPDATE, DELETE
     * Max cache size is 100 elements.
     * When inserting the 101th element least recently used element will be removed
     * Elements are stored by keys:
     * -- "{id}" (for individual entities)
     * -- "LIST_KEY_PREFIX" (for all)
     * -- "LIST_KEY_PREFIX + {hash of id list}" for list by ids
     */
    private static final LRUMap cachedResults = new LRUMap( 100 );
    
    private static final String LIST_KEY_PREFIX = "list";

    @Autowired
    private AdSizeDao adSizeDao = null;
    @Autowired
    private AdSizeTransformer adSizeTransformer = null;

    public List<AdSize> all( List<Integer> adSizeIds )
    {
        List<AdSize> result = null;
        if (adSizeIds != null && adSizeIds.size() > 0)
        {
            result = (List<AdSize>) cachedResults.get(LIST_KEY_PREFIX + adSizeIds.hashCode());
            if (result == null)
            {
                result = adSizeTransformer.transform( adSizeDao.getByIds( adSizeIds ) );
                cachedResults.put(LIST_KEY_PREFIX + adSizeIds.hashCode(), result);
            }
        }
        else
        {
            result = (List<AdSize>) cachedResults.get(LIST_KEY_PREFIX);
            if (result == null)
            {
                result = adSizeTransformer.transform( adSizeDao.getAll() );
                cachedResults.put(LIST_KEY_PREFIX, result);
            }
        }
        return result;
    }

    @Override
    public boolean delete( Integer id )
    {
        boolean result = false;
        IfbAdSize entity = adSizeDao.getById(id);
        if (entity != null)
        {
            adSizeDao.delete(entity);
            result = true;
            cachedResults.clear();
        }
        return result;
    }

    @Override
    public AdSize update( Integer adSizeId, String adSizeName, Integer width, Integer height )
    {
        IfbAdSize entity = adSizeDao.getById(adSizeId);
        if (entity != null)
        {
            if (adSizeName != null)
            {
                entity.setAdSizeName(adSizeName);
            }
            if (width != null)
            {
                entity.setWidth(width);
            }
            if (height != null)
            {
                entity.setHeight(height);
            }
            adSizeDao.update(entity);
            cachedResults.clear();
        }
        return adSizeTransformer.transform( entity );
    }

    @Override
    public AdSize get( Integer id )
    {
        IfbAdSize result = (IfbAdSize) cachedResults.get( id );
        if (result == null)
        {
            result = adSizeDao.getById( id );
            cachedResults.put(id, result);
        }
        return adSizeTransformer.transform( result );
    }

    @Override
    public AdSize create( String adSizeName, Integer width, Integer height )
    {
        IfbAdSize entity = new IfbAdSize();
        entity.setAdSizeName(adSizeName);
        entity.setWidth(width);
        entity.setHeight(height);
        entity.setCreateTimestamp( new Date() );
        entity = adSizeDao.save( entity );
        cachedResults.clear();
        return adSizeTransformer.transform( entity );
    }
}
