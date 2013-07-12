package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.entity.Template;

import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy T
 */
public interface TemplateService
{
    /**
     * Returns a Template object by templateKey
     * @param templateKey
     * @return
     */
    public Template getByKey(String templateKey);

    /**
     * Returns a sorted (alpha by name) list of templates
     * @return
     */
    public List<Template> getAllList();

    /**
     * Returns a sorted (alpha by name) map of templates (key:Template)
     * @param ignoreCache
     * @return
     */
    public Map<String, Template> getAllMap(boolean ignoreCache);
}
