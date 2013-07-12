package com.netshelter.ifbrands.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.cache.CollectionCache;
import com.netshelter.ifbrands.cache.CollectionEvict;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.etl.dataplatform.model.GetAuthorsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetCategoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetSitesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoriesResponse;
import com.netshelter.ifbrands.etl.transform.AuthorTransformer;
import com.netshelter.ifbrands.etl.transform.BrandTransformer;
import com.netshelter.ifbrands.etl.transform.SiteTransformer;
import com.netshelter.ifbrands.etl.transform.StoryTransformer;
import com.netshelter.ifbrands.util.MoreCollections;

public class EntityServiceImpl
        implements EntityService
{
    @Autowired
    private DpServices dpServices;
    @Autowired
    private SiteTransformer siteTransformer;
    @Autowired
    private BrandTransformer brandTransformer;
    @Autowired
    private StoryTransformer storyTransformer;
    @Autowired
    private AuthorTransformer authorTransformer;

    @Override
    @CollectionEvict(cacheName = ENTITY_CACHE, removeAll = true)
    public void flushCache()
    {
    }

    @Override
    @CollectionEvict(cacheName = ENTITY_CACHE, keyPrefix = "story", keyField = CollectionCache.IMPLICIT)
    public void flushStory(int id)
    {
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "site", keyField = "id")
    public Collection<Site> getSites(Collection<Integer> ids)
    {
        GetSitesResponse response = dpServices.getSites(ids);
        return siteTransformer.transform(response);
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "brand", keyField = "id")
    public Collection<Brand> getBrands(Collection<Integer> ids)
    {
        GetCategoriesResponse response = dpServices.getBrands(ids);
        return brandTransformer.transform(response);
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "author", keyField = "id")
    public Collection<Author> getAuthors(Collection<Integer> ids)
    {
        GetAuthorsResponse response = dpServices.getAuthors(ids);
        return authorTransformer.transform(response);
    }

    @Override
    public Map<Integer, Author> getAuthorsMap(Collection<Integer> ids)
    {
        GetAuthorsResponse response = dpServices.getAuthors(ids);
        return authorTransformer.transformMap(response);
    }

    @Override
    public Map<Integer, Story> getStoriesLazy(Collection<Integer> ids)
    {
        GetStoriesResponse response = dpServices.getStories(ids);
        return storyTransformer.transformLazyMap(response);
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "story", keyField = "id")
    public Collection<Story> getStories(Collection<Integer> ids)
    {
        GetStoriesResponse response = dpServices.getStories(ids);
        return storyTransformer.transform(response);
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "site", keyField = "id")
    public Site getSite(int id)
    {
        return MoreCollections.firstOrNull(getSites(Collections.singleton(id)));
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "brand", keyField = "id")
    public Brand getBrand(int id)
    {
        return MoreCollections.firstOrNull(getBrands(Collections.singleton(id)));
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "author", keyField = "id")
    public Author getAuthor(int id)
    {
        return MoreCollections.firstOrNull(getAuthors(Collections.singleton(id)));
    }

    @Override
    @CollectionCache(cacheName = ENTITY_CACHE, keyPrefix = "story", keyField = "id")
    public Story getStory(int id)
    {
        return MoreCollections.firstOrNull(getStories(Collections.singleton(id)));
    }
}
