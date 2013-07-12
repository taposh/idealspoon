package com.netshelter.ifbrands.api.service;

import java.util.Collection;
import java.util.Map;

import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.api.model.entity.Story;

public interface EntityService
{
    public static final String ENTITY_CACHE = "ifb.mvc.entity";

    public void flushCache();

    public Site getSite(int id);

    public Brand getBrand(int id);

    public Story getStory(int id);

    public Author getAuthor(int id);

    public Collection<Site> getSites(Collection<Integer> ids);

    public Collection<Brand> getBrands(Collection<Integer> ids);

    public Collection<Story> getStories(Collection<Integer> ids);

    /**
     * Returns same results as getStories without making extra
     * calls for child objects: Brand, Publisher, Author,...
     *
     * @param ids
     * @return
     */
    public Map<Integer, Story> getStoriesLazy(Collection<Integer> ids);

    public Collection<Author> getAuthors(Collection<Integer> ids);

    public Map<Integer, Author> getAuthorsMap(Collection<Integer> ids);

    void flushStory(int id);
}
