package com.netshelter.ifbrands.api.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.google.common.base.Strings;
import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import com.netshelter.ifbrands.api.model.entity.Brand;
import com.netshelter.ifbrands.api.model.user.UserInfo;
import com.netshelter.ifbrands.api.model.user.UserState;
import com.netshelter.ifbrands.api.model.user.UserValue;
import com.netshelter.ifbrands.cache.CollectionCache;
import com.netshelter.ifbrands.data.dao.UserStateDao;
import com.netshelter.ifbrands.data.dao.UserValueDao;
import com.netshelter.ifbrands.data.entity.IfbUserState;
import com.netshelter.ifbrands.etl.transform.UserStateTransformer;
import com.netshelter.ifbrands.util.MoreCollections;

public class UserServiceImpl implements UserService
{
  private Logger logger = LoggerFactory.getLogger();

  @Autowired
  private UserStateDao userStateDao;
  @Autowired
  private UserValueDao userValueDao;
  @Autowired
  private UserStateTransformer userStateTransformer;

  private String userManagementAuthority;
  public void setUserManagementAuthority(String userManagementAuthority)
  {
      this.userManagementAuthority = userManagementAuthority;
  }

  @Override
  @CacheEvict(value=USER_CACHE, allEntries=true)
  public void flushCache() {}

  @Override
  public List<UserState> getUser( List<String> userKeys )
  {
    String url = String.format( "http://%s/n2ixmr.php?rid=8&fi=9&mw=1&an=ifbrands&ignore_cache=1", userManagementAuthority );
    logger.info( "Requesting %s", url );
    long t0=System.currentTimeMillis();
    Source saxSource = new RestTemplate().getForObject( url, Source.class );
    final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();
    List<UserState> r = xpathTemplate.evaluate( "/results/result", saxSource, new NodeMapper<UserState>() {
      @Override
      public UserState mapNode( Node node, int nodeNum ) throws DOMException
      {
        Source nodeSource = new DOMSource( node );
        int brandId = (int)xpathTemplate.evaluateAsDouble( "./brand_id", nodeSource );
        String brandName = xpathTemplate.evaluateAsString( "./brand_name", nodeSource ).trim();
        Brand brand = new Brand( brandId );
        brand.setName( brandName );

        DateTime llDate;
        final DateTime EPOCH = new DateTime( 0 );
        DateTimeFormatter insightsDatetimeFormat = DateTimeFormat.forPattern( "yyyy-MM-dd HH:mm:ss" );
        String lastLogin = xpathTemplate.evaluateAsString( "./last_login",nodeSource ).trim();
        if(( lastLogin == null )||( lastLogin.startsWith( "0000-00-00" ))) {
          llDate = EPOCH;;
        } else {
          llDate = insightsDatetimeFormat.parseDateTime( lastLogin );
        }

        UserState state = new UserState();
        state.setUserKey( "unknown" );
        state.setBrand( brand );
        state.setLastLogin( llDate );
        return state;
      }
    });
    long t1=System.currentTimeMillis();
    logger.debug( "...response from %s in %dms", userManagementAuthority, (t1-t0));
    return r;
    //    return ( ids == null ) ? userStateDao.getAll() : userStateDao.getByIds( ids );
  }

  @Override
  @CollectionCache( cacheName=USER_CACHE, keyPrefix="userInfo", keyField=CollectionCache.IMPLICIT )
  public UserInfo getUserInfo( String userKey )
  {
    return MoreCollections.firstOrNull( getUserInfo( Collections.singletonList( userKey ) ) );
  }

  @Override
  @CollectionCache( cacheName=USER_CACHE, keyPrefix="userInfo", keyField=CollectionCache.IMPLICIT )
  public List<UserInfo> getUserInfo( List<String> userKeys )
  {
    String url = String.format( "http://%s/n2ixmr.php?rid=6&fi=23&ui=%s&ignore_cache=1&an=inPoweredForBrands", userManagementAuthority, userKeys.get( 0 ) );
    logger.info( "Requesting %s", url );
    long t0=System.currentTimeMillis();
    Source saxSource = new RestTemplate().getForObject( url, Source.class );
    final XPathOperations xpathTemplate = new Jaxp13XPathTemplate();
    List<UserInfo> r = xpathTemplate.evaluate( "/results/result", saxSource, new NodeMapper<UserInfo>() {
      @Override
      public UserInfo mapNode( Node node, int nodeNum ) throws DOMException
      {
        Source nodeSource = new DOMSource( node );
        String userKey = xpathTemplate.evaluateAsString( "./user_id", nodeSource );
        String firstName = xpathTemplate.evaluateAsString( "./firstname", nodeSource ).trim();
        String lastName = xpathTemplate.evaluateAsString( "./lastname", nodeSource ).trim();
        String email = xpathTemplate.evaluateAsString( "./email", nodeSource ).trim();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserKey( userKey );
        userInfo.setFirstName( firstName );
        userInfo.setLastName( lastName );
        userInfo.setEmail( email );
        return userInfo;
      }
    });
    long t1=System.currentTimeMillis();
    logger.debug( "...response from %s in %dms", userManagementAuthority, (t1-t0));
    return r;
  }

  @Override
  public UserState createUser( String userKey, int brandId )
  {
    Date now = new Date();
    IfbUserState user = new IfbUserState();
    user.setDpBrandId( brandId );
    user.setUserKey( userKey );
    user.setLastLogin( now );
    user.setCreateTimestamp( now );
    user = userStateDao.save( user );
    return userStateTransformer.transform( user );
  }

  @Override
  public boolean deleteUser( String userKey )
  {
    boolean success = true;
    try {
      IfbUserState user = userStateDao.getByUserKey( userKey );
      userStateDao.delete( user );
    } catch( Exception e ) {
      success = false;
    }
    return success;
  }

  @Override
  public UserState loginUser( String userKey )
  {
    IfbUserState user = userStateDao.getByUserKey( userKey );
    user.setLastLogin( new Date() );
    user = userStateDao.update( user );
    return userStateTransformer.transform( user );
  }

  @Override
  public List<UserValue> getValues( String userKey, String keyName )
  {
    if( keyName == null ) {
      return userValueDao.getAllValues( userKey );
    } else {
      return Collections.singletonList( userValueDao.getValue( userKey, keyName ));
    }
  }

  @Override
  public boolean setValue( String userKey, String keyName, String value )
  {
    boolean success = true;
    try {
      userValueDao.setValue( userKey, keyName, Strings.emptyToNull( value ));
    } catch( Exception e ) {
      success = false;
    }
    return success;
  }
}
