package com.netshelter.ifbrands.data.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netshelter.ifbrands.api.model.user.UserValue;
import com.netshelter.ifbrands.data.entity.IfbUserValue;
import com.netshelter.ifbrands.util.MoreCollections;

public class UserValueDao extends HibernateDaoSupport
{
  public void init() {}

  public UserValue getValue( String userKey, String keyName )
  {
    // Fetch values from DB
    List<IfbUserValue> result = getUserValuesByProperties( userKey, keyName );
    // Get value, if exists; null otherwise
    IfbUserValue ifb = MoreCollections.firstOrNull( result );
    String value = (ifb == null ) ? null : ifb.getValue();
    return new UserValue( keyName, value );
  }

  public List<UserValue> getAllValues( String userKey )
  {
    // Fetch values from DB
    // Fetch values from DB
    List<IfbUserValue> result = getUserValuesByProperties( userKey, null );

    // Get values
    List<UserValue> uvList = new ArrayList<UserValue>( result.size() );
    for( IfbUserValue ifb: result ) {
      uvList.add( new UserValue( ifb.getKeyName(), ifb.getValue() ));
    }
    return uvList;
  }

  public void setValue( String userKey, String keyName, String value )
  {
    // Fetch entity
    List<IfbUserValue> result = getUserValuesByProperties( userKey, keyName );
    if( result.size() > 0 ) {
      // If exists we update or delete
      IfbUserValue uv = result.get( 0 );
      if( value != null ) {
        uv.setValue( value );
        getHibernateTemplate().merge( uv );
      } else {
        getHibernateTemplate().delete( uv );
      }
    } else {
      // Not exist so create
      if( value != null ) {
        IfbUserValue uv = new IfbUserValue();
        uv.setUserKey( userKey );
        uv.setKeyName( keyName );
        uv.setValue( value );
        uv.setCreateTimestamp( new Date() );
        getHibernateTemplate().save( uv );
      } else {
        // Delete a non-existant key
      }
    }
  }

  public List<IfbUserValue> getUserValuesByProperties( String userKey, String keyName )
  {
    // Fetch values from DB
    DetachedCriteria criteria = DetachedCriteria.forClass( IfbUserValue.class );
    if( userKey != null ) criteria.add( Restrictions.eq( "userKey", userKey ));
    if( keyName != null ) criteria.add( Restrictions.eq( "keyName", keyName ));
    @SuppressWarnings( "unchecked" )
    List<IfbUserValue> result = getHibernateTemplate().findByCriteria( criteria );
    return result;
  }
}
