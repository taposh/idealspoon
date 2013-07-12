package com.netshelter.ifbrands.etl.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.netshelter.ifbrands.api.model.entity.Author;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.entity.Site;
import com.netshelter.ifbrands.api.model.entity.Story;
import com.netshelter.ifbrands.api.model.entity.Story.ImageSpec;
import com.netshelter.ifbrands.api.model.influence.BrandSentiment;
import com.netshelter.ifbrands.api.service.EntityService;
import com.netshelter.ifbrands.etl.dataplatform.DpServices;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategorySentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.DpCategoryType;
import com.netshelter.ifbrands.etl.dataplatform.model.DpEngagement;
import com.netshelter.ifbrands.etl.dataplatform.model.DpImageSpec;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStory;
import com.netshelter.ifbrands.etl.dataplatform.model.DpStoryCategorySentiment;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoriesResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryCategorySentimentsResponse;
import com.netshelter.ifbrands.etl.dataplatform.model.GetStoryEngagementsResponse;
import com.netshelter.ifbrands.util.MoreCollections;

@Component
public class StoryTransformer
{
    @Autowired
    private EntityService entityService;
    @Autowired
    private DpServices dpServices;
    @Autowired
    private InfluenceTransformer influenceTransformer;

    public List<Story> transform(GetStoriesResponse response)
    {
        return transform(response.getStories());
    }

    public Map<Integer, Story>  transformLazyMap(GetStoriesResponse response)
    {
        return transformLazyMap(response.getStories());
    }

    public Story transform(DpStory dpStory)
    {
        return MoreCollections.firstOrNull(transform(Collections.singletonList(dpStory)));
    }

    public List<Story> transform(Collection<DpStory> dpStories)
    {
        Map<Integer, Site> siteMap = prefetchSites(dpStories);
        Map<Integer, Author> authorMap = prefetchAuthors(dpStories);

        // Build list of story ids for later sentiment query
        Map<Integer, Story> storyMap = new TreeMap<Integer, Story>();

        // Fill in story list
        List<Story> result = new ArrayList<Story>(dpStories.size());
        for (DpStory item : dpStories)
        {
            Story s = new Story(item.getStoryId());
            s.setSite(siteMap.get(item.getSiteId()));
            s.setAuthor(authorMap.get(item.getAuthorId()));
            s.setBrandSentiments(Collections.<BrandSentiment>emptySet()); // Placeholder
            s.setPublishTime(item.getPublishTime());
            s.setHash(item.getHash());
            s.setTitle(HtmlUtils.htmlUnescape(item.getTitle()));
            s.setSummary(HtmlUtils.htmlUnescape(item.getSummary()));
            s.setStoryUrl(item.getStoryUrl());
            s.setImageUrl(item.getImageUrl());
            s.setThumbnails(transformImage(item.getThumbnails()));
            result.add(s);

            // Track IDs against their stories
            storyMap.put(item.getStoryId(), s);
        }

        // Fetch sentiment info
        GetStoryCategorySentimentsResponse sentimentsResponse = dpServices.getStoryBrandSentiments(storyMap.keySet(),
                                                                                                   null,
                                                                                                   Collections.singleton(
                                                                                                           DpCategoryType.BRAND));
        Map<Integer, Brand> brandMap = prefetchBrands(sentimentsResponse.getStoryCategorySentiments());
        for (DpStoryCategorySentiment scs : sentimentsResponse.getStoryCategorySentiments())
        {
            Story s = storyMap.get(scs.getStoryId());
            List<BrandSentiment> bsList = new ArrayList<BrandSentiment>();
            for (DpCategorySentiment cs : scs.getCategorySentiments())
            {
                Brand brand = brandMap.get(cs.getCategoryId());
                BrandSentiment brandSentiment = new BrandSentiment();
                brandSentiment.setBrand(brand);
                brandSentiment.setSentiment(cs.getSentiment().toString());
                bsList.add(brandSentiment);
            }
            s.setBrandSentiments(bsList);
        }
        return result;
    }

    public Map<Integer, Story>  transformLazyMap(Collection<DpStory> dpStories)
    {
        // Fill in story list
        Map<Integer, Story>  result = new HashMap<Integer, Story>(dpStories.size());
        for (DpStory item : dpStories)
        {
            Story s = new Story(item.getStoryId());
            s.setBrandSentiments(Collections.<BrandSentiment>emptySet()); // Placeholder
            s.setPublishTime(item.getPublishTime());
            s.setHash(item.getHash());
            s.setTitle(HtmlUtils.htmlUnescape(item.getTitle()));
            s.setSummary(HtmlUtils.htmlUnescape(item.getSummary()));
            s.setStoryUrl(item.getStoryUrl());
            s.setImageUrl(item.getImageUrl());
            s.setThumbnails(transformImage(item.getThumbnails()));
            result.put(s.getId(), s);
        }
        return result;
    }

    public Collection<Story> transform(GetStoryEngagementsResponse response)
    {
        // Temporary variable to store extracted stories
        Collection<DpStory> extractedStories = new ArrayList<DpStory>();

        Collection<DpEngagement> dpEngagements = response.getEngagements();

        // Extract a Collection of stories for the GetStoriesResponse injection
        for (DpEngagement dpEngagement : dpEngagements)
        {
            extractedStories.add(dpEngagement.getStory());
        }

        // Now let's transform this into something we can work with
        Collection<Story> stories = transform(extractedStories);

        // Fill in the Influence for every story we have
        for (Story story : stories)
        {
            for (DpEngagement dpEngagement : dpEngagements)
            {
                if (story.getId().equals(dpEngagement.getStory().getStoryId()))
                {
                    story.setInfluence(influenceTransformer.transform(dpEngagement.getInfluence()));
                    break;
                }
            }
        }

        return stories;
    }

    protected Map<Integer, Site> prefetchSites(Collection<DpStory> stories)
    {
        // Prefetch all sites & authors
        Map<Integer, Site> siteMap = new TreeMap<Integer, Site>();
        for (DpStory item : stories)
        {
            siteMap.put(item.getSiteId(), null); // Will overwrite duplicates
        }
        // Fetch sites in batch & fill in tree
        Collection<Site> sites = entityService.getSites(siteMap.keySet());
        for (Site s : sites)
        {
            siteMap.put(s.getId(), s);
        }
        return siteMap;
    }

    protected Map<Integer, Author> prefetchAuthors(Collection<DpStory> stories)
    {
        // Prefetch all authors
        Map<Integer, Author> authorMap = new TreeMap<Integer, Author>();
        for (DpStory item : stories)
        {
            authorMap.put(item.getAuthorId(), null); // Will overwrite duplicates
        }
        // Fetch sites in batch & fill in tree
        Collection<Author> authors = entityService.getAuthors(authorMap.keySet());
        for (Author a : authors)
        {
            authorMap.put(a.getId(), a);
        }
        return authorMap;
    }

    private Map<Integer, Brand> prefetchBrands(Collection<DpStoryCategorySentiment> storyCategorySentiments)
    {
        // Prefetch all brands
        Map<Integer, Brand> brandMap = new TreeMap<Integer, Brand>();
        for (DpStoryCategorySentiment item : storyCategorySentiments)
        {
            for (DpCategorySentiment cs : item.getCategorySentiments())
            {
                brandMap.put(cs.getCategoryId(), null); // Will overwrite duplicates
            }
        }
        // Fetch categories and fill in tree
        Collection<Brand> brands = entityService.getBrands(brandMap.keySet());
        for (Brand b : brands)
        {
            brandMap.put(b.getId(), b);
        }
        return brandMap;
    }

    private Collection<ImageSpec> transformImage(Collection<DpImageSpec> thumbnails)
    {
        List<ImageSpec> results = new ArrayList<ImageSpec>(thumbnails.size());
        for (DpImageSpec d : thumbnails)
        {
            results.add(new ImageSpec(d.getWidth(), d.getHeight(), d.getUrl(), d.getSecureUrl(), d.getUri()));
        }
        return results;
    }
}
