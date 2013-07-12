package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.entity.Template;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public class TemplateServiceImplTest
    extends BaseServiceTest
{
    @Autowired
    TemplateService templateService = null;

    @Test
    public void testTemplateService_getAllList()
    {
        List<Template> list = templateService.getAllList();
        printTemplateList(list);
        list = templateService.getAllList();
        printTemplateList(list);
        list = templateService.getAllList();
        printTemplateList(list);
        list = templateService.getAllList();
        printTemplateList(list);
    }

    @Test
    public void testTemplateService_getAllMap()
    {
        Map<String, Template> map = templateService.getAllMap(false);
        printTemplateMap(map);
        map = templateService.getAllMap(false);
        printTemplateMap(map);
        map = templateService.getAllMap(false);
        printTemplateMap(map);
        map = templateService.getAllMap(false);
        printTemplateMap(map);
    }

    @Test
    public void testTemplateService_get()
    {
        Template template = templateService.getByKey("applejacks-1");
        System.err.println("byId: " + template);
        template = templateService.getByKey("boo_berry-1");
        System.err.println("byId: " + template);
        template = templateService.getByKey("capn_crunch-1");
        System.err.println("byId: " + template);
    }

    public void printTemplateMap(Map<String, Template> map)
    {
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); )
        {
            String key = iterator.next();
            System.err.println("map: "+map.get(key));
        }
    }

    public void printTemplateList(List<Template> list)
    {
        for (Template template : list)
        {
            System.err.println("list: "+template);
        }
    }

}
