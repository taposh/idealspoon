package com.netshelter.ifbrands.api.service;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

/**
 * @author Manu Mukerji
 */
@Component
public class AdTagGenerationServiceImpl
    implements AdTagGenerationService
{

    @Autowired
    VelocityTemplateService velocityTemplateService = null;

    @Override
    public String createTag(Integer adId, String templateFileName)
    {
        // TODO: .....

    	System.out.println("before getting file1");
        String result = null;
        try
        {
            Template t = velocityTemplateService.getTemplate("velocity/"+templateFileName);

            VelocityContext context = new VelocityContext();
            context.put("someparam", "Some Value");

            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            result = writer.toString();
            System.out.println("result is:"+result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
