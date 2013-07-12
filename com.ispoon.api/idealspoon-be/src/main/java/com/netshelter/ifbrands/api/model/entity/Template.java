package com.netshelter.ifbrands.api.model.entity;

import com.netshelter.ifbrands.api.util.TemplateJsonDateDeserializer;
import org.codehaus.jackson.annotate.JacksonAnnotation;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * DTO object for TemplateService responses
 *
 * Using TemplateJsonDateDeserializer to parse dates in format: 2013-07-01
 *
 * @author Dmitriy T
 */
public class Template
{
    @JsonProperty("internalName")
    private String key;
    @JsonProperty("externalName")
    private String name;
    @JsonProperty("status")
    private String status = null;
    @JsonProperty("created")
    @JsonDeserialize(using = TemplateJsonDateDeserializer.class)
    private Date created;

    public boolean isEnabled()
    {
        return "active".equalsIgnoreCase(status);
    }

    @Override
    public String toString()
    {
        return "Template{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", enabled=" + isEnabled() +
                ", created=" + created +
                '}';
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
}
