package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.entity.Template;
import com.netshelter.ifbrands.util.RestTemplateWithTimeout;
import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Template service for shorty template access.
 * Caching is done in getAllMap(...) method
 *
 * @author Dmitriy T
 */
public class TemplateServiceImpl
    implements TemplateService
{

    private static final Logger log = Logger.getLogger(TemplateServiceImpl.class);

    private RestTemplate restWithTimeout = new RestTemplateWithTimeout( 45 );

    private String shortyAuthority = null;

    public static final LRUMap cache = new LRUMap();

    @Override
    public Template getByKey(String templateKey)
    {
        return getAllMap(false).get(templateKey);
    }

    @Override
    public List<Template> getAllList()
    {
        List<Template> templateList = new ArrayList<Template>(getAllMap(false).values());
        Collections.sort(templateList, new Comparator<Template>()
        {
            @Override
            public int compare(Template template, Template template1)
            {
                return template.getName().compareToIgnoreCase(template1.getName());
            }
        });

        return templateList;
    }

    @Override
    public Map<String, Template> getAllMap(boolean ignoreCache)
    {
        Map<String, Template> result = (Map<String, Template>)cache.get("map");
        if (result == null || ignoreCache)
        {
            result = new HashMap<String, Template>();
            StringBuffer urlBuff = new StringBuffer();
            urlBuff.append("http://");
            urlBuff.append(shortyAuthority);
            urlBuff.append("/templates.json");
            log.debug("Fetching all templates (as Map) from " + urlBuff.toString());
            TemplateList list = restWithTimeout.getForObject(urlBuff.toString(), TemplateList.class);
            List<Template> templateList = Arrays.asList(list.templates);
            for (Template template : templateList)
            {
                result.put(template.getKey(), template);
            }
            cache.put("map", result);
        }
        return result;
    }

    public static class TemplateList
    {
        public Template [] templates;
    }

    // set by spring mvc-context.xml
    public void setShortyAuthority(String shortyAuthority)
    {
        this.shortyAuthority = shortyAuthority;
    }
}
