package com.netshelter.ifbrands.util;

import com.netshelter.ifbrands.api.util.GeneralServerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * @author Dmitriy T
 */
@Component
public class ApplicationContextFactory
    implements ServletContextAware,
    ApplicationContextAware
{

    private static volatile ServletContext _servletContext = null;
    private static volatile org.springframework.context.ApplicationContext _applicationContext = null;

    public void setServletContext(ServletContext servletContext)
    {
        setSrvCtx(servletContext);
    }

    public void setApplicationContext(ApplicationContext testContext)
    {
        setAppCtx(testContext);
    }

    public static void setSrvCtx(ServletContext servletContext)
    {
        _servletContext = servletContext;
    }

    public static void setAppCtx(ApplicationContext testContext)
    {
        _applicationContext = testContext;
    }

    public static Set getResourcePaths(String location)
    {
        return _servletContext.getResourcePaths(location);
    }

    public static URL getResource(String location) throws MalformedURLException
    {
        return _servletContext.getResource(location);
    }

    public static ApplicationContext getApplicationContext()
    {
        if (_applicationContext == null)
        {
            try
            {
                _applicationContext = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(_servletContext);
            }
            catch (Exception e)
            {
                // e.printStackTrace();
            }
        }
        return _applicationContext;
    }

    public static ServletContext getServletContext()
    {
        ServletContext result = null;
        if (_servletContext != null)
        {
            result = _servletContext;
        }
        return result;
    }

    /**
     * * Returns the object from spring context associated with this name. For example: "userDao"
     * @param beanName name of the bean
     * @return Object bean if found in the context or null if not found
     * @throws GeneralServerException
     */
    public static Object getBean(String beanName) throws GeneralServerException
    {
        try
        {
            if (_applicationContext != null)
                return _applicationContext.getBean(beanName);
            else
                throw new GeneralServerException("Spring bean not found", null, beanName);
        }
        catch (Exception e)
        {
            throw new GeneralServerException("Unable to retrieve spring bean", e, beanName);
        }
    }
}
