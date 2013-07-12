package com.netshelter.ifbrands.api.service;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

/**
 * @author Dmitriy T
 */
@Component
public class VelocityTemplateService
{
    public Template getTemplate(String template)
    {
        Template result = null;
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        ve.setProperty("runtime.log", "/tmp/velocity.log");
        try
        {
            ve.init();
            result = ve.getTemplate(template);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
