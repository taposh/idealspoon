package com.netshelter.ifbrands.etl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Joiner;
import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.util.RestTemplateWithTimeout;

/**
 * Implementation of BaseRestService which provides some common functionality.
 * <ul>
 * <li>Allow injection of an authority value (eg. foo.example.com:8000)</li>
 * <li>Allow injection of a custom Jackson ObjectMapper</li>
 * <li>Basic API for GET requests with parameters marshalled into Objects</li>
 * </ul>
 *
 * @author bgray
 */
public abstract class BaseRestServiceImpl
        implements BaseRestService
{
    protected Logger logger = LoggerFactory.getLogger();
    protected RestTemplate rest = new RestTemplate();
    protected RestTemplate restWithTimeout = new RestTemplateWithTimeout(45);
    private String authority;

    /**
     * Allow the sub-class to provide the root of the URI path.  Usually the servlet name.
     */
    protected abstract String getPathRoot();

    /**
     * Allow the sub-class to provide the API portion of the URI path.  Usually common Controllers path.
     */
    protected abstract String getPathApi();

    /**
     * Set Authority of server (hostname:port).
     */
    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    /**
     * Get Authority of server.
     */
    public String getAuthority()
    {
        return authority;
    }

    /**
     * Allow injection of a custom Jackson1 ObjectMapper.
     *
     * @param objectMapper
     */
    public void setJacksonObjectMapper(ObjectMapper objectMapper)
    {
        for (HttpMessageConverter<?> c : rest.getMessageConverters())
        {
            if (c instanceof MappingJacksonHttpMessageConverter)
            {
                ((MappingJacksonHttpMessageConverter) c).setObjectMapper(objectMapper);
            }
        }
    }

    /**
     * Make an HTTP GET call and marshall the response into an object using standard marshallers.
     *
     * @param type Expected output type
     * @param path full URL path
     * @param query query params in alternating key/value format (ie. 0=1,2=3,...)
     * @return Response object
     */
    protected <T> T getForResponse(Class<T> type, String path, Object... query)
    {
        String url = getUrl(path, query);
        logger.info("GET: %s", url);
        long t0 = System.currentTimeMillis();
        try
        {
            return rest.getForObject(url, type);
        }
        catch (RestClientException rce)
        {
            String info = "";
            if (rce instanceof HttpServerErrorException)
            {
                info = ((HttpServerErrorException) rce).getResponseBodyAsString();
            }
            throw new RestClientException("GET failed: " + url + "\n" + info, rce);
        }
        finally
        {
            long t1 = System.currentTimeMillis();
            logger.debug("...response from %s in %dms", path, (t1 - t0));
        }
    }

    /**
     * This is a "not-so-pretty" dp services v2 api implementation
     *
     * @param type
     * @param path
     * @param query
     * @param <T>
     * @return
     */
    protected <T> T getForResponse_v2(Class<T> type, String path, Object... query)
    {
        String url = getUrl_v2(path, query);
        logger.info("GET: %s", url);
        long t0 = System.currentTimeMillis();
        try
        {
            return rest.getForObject(url, type);
        }
        catch (RestClientException rce)
        {
            String info = "";
            if (rce instanceof HttpServerErrorException)
            {
                info = ((HttpServerErrorException) rce).getResponseBodyAsString();
            }
            throw new RestClientException("GET failed: " + url + "\n" + info, rce);
        }
        finally
        {
            long t1 = System.currentTimeMillis();
            logger.debug("...response from %s in %dms", path, (t1 - t0));
        }
    }

    /**
     * Make an HTTP POST call and marshall the response into an object using standard marshallers.
     *
     * @param type Expected output type
     * @param path full URL path
     * @param query query params in alternating key/value format (ie. 0=1,2=3,...)
     * @return Response object
     */
    protected <T> T postDataForResponse(Class<T> type, String path, Object data, Object... query)
            throws
            Exception
    {
        String url = getUrl(path, query);
        logger.info("POST: %s", url);
        long t0 = System.currentTimeMillis();
        try
        {
            return rest.postForObject(url, data, type);
        }
        catch (RestClientException rce)
        {
            String info = "";
            if (rce instanceof HttpServerErrorException)
            {
                info = ((HttpServerErrorException) rce).getResponseBodyAsString();
            }
            throw new Exception("POST failed: " + url + "\n" + info, rce);
        }
        finally
        {
            long t1 = System.currentTimeMillis();
            logger.debug("...response from %s in %dms", path, (t1 - t0));
        }
    }

    /**
     * Make an HTTP POST call (content-type: application/x-www-form-urlencoded) and marshall the response into an object using standard marshallers.
     *
     * @param type Expected output type
     * @param path full URL path
     * @param formMap request body as MultiValueMap<String,String>
     * @param query query params in alternating key/value format (ie. 0=1,2=3,...)
     * @return Response object
     * @throws Exception
     */
    protected <T> T postFormForResponse(Class<T> type, String path, MultiValueMap<String, String> formMap, Object... query)
            throws
            Exception
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formMap,
                                                                                                         headers);

        String url = getUrl(path, query);
        logger.info("POST: %s", url);
        long t0 = System.currentTimeMillis();
        try
        {
            return rest.postForObject(url, entity, type);
        }
        catch (RestClientException rce)
        {
            String info = "";
            if (rce instanceof HttpServerErrorException)
            {
                info = ((HttpServerErrorException) rce).getResponseBodyAsString();
            }
            throw new Exception("POST failed: " + url + "\n" + info, rce);
        }
        finally
        {
            long t1 = System.currentTimeMillis();
            logger.debug("...response from %s in %dms", path, (t1 - t0));
        }
    }

    /**
     * Form a request URL from the constituent parts.
     *
     * @param path Path to controller
     * @param pairs query params in alternating key/value format
     * @return result URL
     */
    protected String getUrl(String path, Object... pairs)
    {
        return getUrl(path, getQueryFromPairs(pairs));
    }

    protected String getUrl_v2(String path, Object... pairs)
    {
        return getUrl_v2(path, getQueryFromPairs(pairs));
    }

    /**
     * Form a request URL from a given path and query string.
     * This will use authority, root and api paths to construct a full URL.
     *
     * @param path Path to controller
     * @param queryMap Properly formed query string
     * @return result URL
     */
    protected String getUrl(String path, MultiValueMap<String, String> queryMap)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(authority).append(getPathRoot()).append(getPathApi()).append(path);
        if ((queryMap != null) && (!queryMap.isEmpty()))
        {
            sb.append('?');
            for (Map.Entry<String, List<String>> entry : queryMap.entrySet())
            {
                for (String v : entry.getValue())
                {
                    sb.append(entry.getKey()).append('=').append(v).append('&');
                }
            }
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    protected String getUrl_v2(String path, MultiValueMap<String, String> queryMap)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(authority).append(getPathRoot()).append("/api/v2").append(path);
        if ((queryMap != null) && (!queryMap.isEmpty()))
        {
            sb.append('?');
            for (Map.Entry<String, List<String>> entry : queryMap.entrySet())
            {
                for (String v : entry.getValue())
                {
                    sb.append(entry.getKey()).append('=').append(v).append('&');
                }
            }
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Given a set of query parameters, produce a query string.
     * Params are expected to be in alternating key/value order.
     * Values which implement 'Iterable' will be presented as
     * a comma-separated list.
     */
    protected MultiValueMap<String, String> getQueryFromPairs(Object... pairs)
    {
        // Params must be in pairs, so length should be even
        int len = pairs.length;
        if ((len % 2) != 0)
        {
            throw new IllegalArgumentException("Must provide an even number of values");
        }
        // Produce HashMap of key/value pairs
        // Null values are ignored
        // Iterable values are presented as a CSV list
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        @SuppressWarnings("unchecked")
        Iterator<String> iter = new ArrayIterator(pairs);
        while (iter.hasNext())
        {
            Object k = iter.next();
            Object v = iter.next();

            String s;
            if (v == null)
            {
                continue;
            }
            else if (v instanceof Iterable)
            {
                Iterable<?> valueIterable = (Iterable<?>) v;
                if (!valueIterable.iterator().hasNext()) continue;
                if (valueIterable.iterator().next() instanceof String)
                {
                    @SuppressWarnings("unchecked")
                    Iterator<String> stringIter = (Iterator<String>) valueIterable.iterator();
                    while (stringIter.hasNext())
                    {
                        s = stringIter.next();
                        map.add(String.valueOf(k), s);
                    }
                    continue;
                }
                else
                {
                    s = getCsvList(valueIterable);
                }
            }
            else
            {
                s = String.valueOf(v);
            }
            map.add(String.valueOf(k), s);
        }
        return map;
    }

    /**
     * Given a Iterable set of values, produce a CSV string of those values.
     */
    protected String getCsvList(Iterable<?> ids)
    {
        return Joiner.on(',').join(ids);
    }

    protected void addMany(List<Object> list, String name, Collection<?> values)
    {
        for (Object v : values)
        {
            list.add(name);
            list.add(v);
        }
    }
}
