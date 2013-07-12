package com.netshelter.ifbrands.api.service;

import com.netshelter.ifbrands.api.model.usermanagement.GroupInfo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Dmitriy T
 * Date: 2/5/13
 */
public class UserManagementServicesImpl
    implements UserManagementServices
{

    private String userManagementAuthority;
    public void setUserManagementAuthority(String userManagementAuthority)
    {
        this.userManagementAuthority = userManagementAuthority;
    }

    private Logger logger = Logger.getLogger(UserManagementServicesImpl.class);

    @SuppressWarnings( "unchecked" )
    public List<GroupInfo> getGroupInfoAll(boolean ignoreCache)
    {
        long ms = System.currentTimeMillis();
        List<GroupInfo> result = null;
        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache(CACHE_NAME);

        String key = "getGroupInfoAll";

        if ( cache != null )
        {
            Element element = null;
            if ( !ignoreCache )
            {
                element = cache.get(key);
            }
            if ( element != null && element.getObjectValue() instanceof List ) {
                logger.debug("Getting from cache, key:" + key);
                result = (List<GroupInfo>)element.getObjectValue();
            }
        }
        if ( result == null )
        {
            String url = String.format( "http://%s/n2ixmr.php?rid=1&an=inPoweredForBrands&fi=7&rlkey=ifb_client_user&fmt=0",
                    userManagementAuthority );
            logger.info( "Requesting " + url );

            Source saxSource = new RestTemplate().getForObject(url, Source.class);
            final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();
            result = xpathTemplate.evaluate( "/results/result", saxSource, new NodeMapper<GroupInfo>() {
                @Override
                public GroupInfo mapNode( Node node, int nodeNum ) throws DOMException
                {
                    Source nodeSource = new DOMSource( node );
                    String groupId    = xpathTemplate.evaluateAsString( "./group_id", nodeSource ).trim();
                    String groupName  = xpathTemplate.evaluateAsString( "./group_name", nodeSource ).trim();
                    return new GroupInfo(groupId, groupName);
                }
            });
            if (cache != null) {
                logger.debug("Inserting into cache, key:" + key);
                cache.put(new Element(key, result));
            }
        }
        logger.debug( "done in " + (System.currentTimeMillis() - ms) + "ms");
        return result;
    }

    @Override
    public List<GroupInfo> getGroupInfo(Collection<String> groupIds) {
        String groupIdStr = getCsv(groupIds);
        String url = String.format( "http://%s/n2ixmr.php?rid=1&an=inPoweredForBrands&fi=4&gid="+groupIdStr+"&fmt=0",
                userManagementAuthority );
        logger.info( "Requesting " + url );
        long ms = System.currentTimeMillis();
        Source saxSource = new RestTemplate().getForObject(url, Source.class);
        final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();
        List<GroupInfo> result = xpathTemplate.evaluate( "/results/result", saxSource, new NodeMapper<GroupInfo>() {
            @Override
            public GroupInfo mapNode( Node node, int nodeNum ) throws DOMException
            {
                Source nodeSource = new DOMSource( node );
                String groupId    = xpathTemplate.evaluateAsString( "./group_id", nodeSource ).trim();
                String groupName  = xpathTemplate.evaluateAsString( "./group_name", nodeSource ).trim();
                return new GroupInfo(groupId, groupName);
            }
        });
        logger.debug( "done in " + (System.currentTimeMillis() - ms) + "ms");
        return result;
    }

    @Override
    public Map<String, GroupInfo> getGroupInfoMap(Collection<String> groupIds) {
        Map<String, GroupInfo> result = null;
        List<GroupInfo> groupInfoList =  getGroupInfo(groupIds);
        if (groupInfoList != null) {
            result = new HashMap<String, GroupInfo>();
            for (int i = 0; i < groupInfoList.size(); i++) {
                GroupInfo groupInfo = groupInfoList.get(i);
                result.put(groupInfo.getId(), groupInfo);
            }
        }
        return result;
    }

    private String getCsv(Collection<String> collection)
    {
        if (collection == null)
            return null;
        StringBuilder buff = new StringBuilder();
        boolean firstTime = true;
        for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); ) {
          String item = iterator.next();
            if (!firstTime) {
                buff.append(",");
            } else {
                firstTime = false;
            }
            buff.append(item);
        }
        return buff.toString();
    }
}
