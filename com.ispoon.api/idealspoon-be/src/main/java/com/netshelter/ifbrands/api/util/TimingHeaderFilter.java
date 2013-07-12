package com.netshelter.ifbrands.api.util;

import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Date;

/**
 * @author Dmitriy T
 */
public class TimingHeaderFilter
    extends ShallowEtagHeaderFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws
            IOException,
            ServletException
    {
        long ms = System.currentTimeMillis();
        HttpCacheResponseWrapper responseWrapper = new HttpCacheResponseWrapper(response);
        responseWrapper.setHeader("IFBB_EXECUTION_START_TIME", new Date().toString());
        try
        {
            super.doFilterInternal(request, responseWrapper, filterChain);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            responseWrapper.setHeader("IFBB_EXECUTION_DURATION", (System.currentTimeMillis()-ms)+"");
        }
    }

    public class HttpCacheResponseWrapper extends HttpServletResponseWrapper
    {
        public HttpCacheResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void flushBuffer() throws IOException {
            System.out.println("Not flushing...");
        }
    }
}
