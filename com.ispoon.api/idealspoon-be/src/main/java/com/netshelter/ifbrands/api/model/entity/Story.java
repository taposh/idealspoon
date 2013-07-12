package com.netshelter.ifbrands.api.model.entity;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.netshelter.ifbrands.api.model.influence.BrandSentiment;
import com.netshelter.ifbrands.api.model.influence.Influence;
import com.netshelter.ifbrands.util.MoreObjects;

public class Story
{
    private Integer id;
    private Site site;
    private Author author;
    private DateTime publishTime;
    private String hash, title, summary, storyUrl, imageUrl;
    private Collection<ImageSpec> thumbnails;
    private Collection<BrandSentiment> brandSentiments;
    private Influence influence;

    public Story()
    {
    }

    public Story(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Site getSite()
    {
        return site;
    }

    public void setSite(Site site)
    {
        this.site = site;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public DateTime getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(DateTime publishTime)
    {
        this.publishTime = publishTime;
    }

    public String getHash()
    {
        return hash;
    }

    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getStoryUrl()
    {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl)
    {
        this.storyUrl = storyUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Collection<ImageSpec> getThumbnails()
    {
        return thumbnails;
    }

    public void setThumbnails(Collection<ImageSpec> thumbnails)
    {
        this.thumbnails = thumbnails;
    }

    public Collection<BrandSentiment> getBrandSentiments()
    {
        return brandSentiments;
    }

    public void setBrandSentiments(Collection<BrandSentiment> brandSentiments)
    {
        this.brandSentiments = brandSentiments;
    }

    public Influence getInfluence()
    {
        return influence;
    }

    public void setInfluence(Influence influence)
    {
        this.influence = influence;
    }

    @Override
    public String toString()
    {
        return "Story [id=" + id + ", title=" + StringUtils.abbreviate(title, 20) + "]";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Story)
        {
            Story that = (Story) obj;
            if ((this.getId() == that.getId()) && MoreObjects.equivalent(this.getSite(), that.getSite())
                    && MoreObjects.equivalent(this.getAuthor(), that.getAuthor())
                    && MoreObjects.equivalent(this.getPublishTime(), that.getPublishTime())
                    && MoreObjects.equivalent(this.getHash(), that.getHash())
                    && MoreObjects.equivalent(this.getTitle(), that.getTitle())
                    && MoreObjects.equivalent(this.getSummary(), that.getSummary())
                    && MoreObjects.equivalent(this.getStoryUrl(), that.getStoryUrl())) return true;
        }
        return false;
    }

    public static class ImageSpec
    {
        private int width, height;
        private String url, secureUrl, uri;

        public ImageSpec(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public ImageSpec(int width, int height, String url, String secureUrl, String uri)
        {
            this.width = width;
            this.height = height;
            this.url = url;
            this.secureUrl = secureUrl;
            this.setUri(uri);
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public String getUrl()
        {
            return url;
        }

        public String getSecureUrl()
        {
            return secureUrl;
        }

        public String getUri()
        {
            return uri;
        }

        public void setUri(String uri)
        {
            this.uri = uri;
        }
    }
}
