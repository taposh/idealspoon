package com.netshelter.ifbrands.etl.dataplatform.model;

import com.netshelter.ifbrands.api.model.entity.Story;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dmitriy T
 */
public class DpIndexedStory
{
    private Integer id;
    private String key;
    private Integer authorId;
    private String authorName;
    private Integer siteId;
    private String siteName;
    private DateTime publishTime;
    private String title;
    private String summary;
    private String url;
    private List<DpIndexedStoryBrandSentiment> brandSentiments;
    private String imageUrl;
    private Collection<Story.ImageSpec> thumbnails;

    @Override
    public String toString()
    {
        return "DpIndexedStory{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", siteId=" + siteId +
                ", siteName='" + siteName + '\'' +
                ", publishTime=" + publishTime +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                ", brandSentiments=" + brandSentiments +
                ", imageUrl='" + imageUrl + '\'' +
                ", thumbnails=" + thumbnails +
                '}';
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    @JsonDeserialize(as=ArrayList.class)
    public void setThumbnails( Collection<Story.ImageSpec> thumbnails )
    {
        this.thumbnails = thumbnails;
    }

    public Collection<Story.ImageSpec> getThumbnails()
    {
        return thumbnails;
    }

    public List<DpIndexedStoryBrandSentiment> getBrandSentiments()
    {
        return brandSentiments;
    }

    public void setBrandSentiments(List<DpIndexedStoryBrandSentiment> brandSentiments)
    {
        this.brandSentiments = brandSentiments;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Integer getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Integer authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public Integer getSiteId()
    {
        return siteId;
    }

    public void setSiteId(Integer siteId)
    {
        this.siteId = siteId;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public DateTime getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(DateTime publishTime)
    {
        this.publishTime = publishTime;
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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public static class DpIndexedStoryBrandSentiment
    {
        private Integer categoryId;
        private String sentiment;

        @Override
        public String toString()
        {
            return "DpIndexedStoryBrandSentiment{" +
                    "categoryId=" + categoryId +
                    ", sentiment='" + sentiment + '\'' +
                    '}';
        }

        public Integer getCategoryId()
        {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId)
        {
            this.categoryId = categoryId;
        }

        public String getSentiment()
        {
            return sentiment;
        }

        public void setSentiment(String sentiment)
        {
            this.sentiment = sentiment;
        }
    }
}
