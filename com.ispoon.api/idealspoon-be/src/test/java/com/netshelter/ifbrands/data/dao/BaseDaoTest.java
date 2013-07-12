package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netshelter.ifbrands.util.KeyGeneratorUtils.KeyGenerator;

public abstract class BaseDaoTest<E> extends DaoTest
{
  @Autowired private KeyGenerator keyGenerator;

  protected static Date now = new Date();
  protected static Random R = new Random();
  protected abstract BaseDao<E> getDao();
  protected abstract E makeEntity();

  public String uniqueString()
  {
    return keyGenerator.generateKey();
  }

  public void deleteAll( BaseDao<E> dao )
  {
    dao.getHibernateTemplate().deleteAll( dao.getAll() );
  }

  // Expects entity to be saved, id == entity.getId()
  @Test
  public void testBaseDao() throws Exception
  {
    BaseDao<E> dao = getDao();
    // Get a persistent entity
    E entity = makeEntity();
    Serializable id = dao.getIdentifier( entity );

    // Check class type
    assertEquals( dao.getEntityClass(), entity.getClass() );

    // ID must be valid
    assertNotNull( id );

    // Save two additional entities (sleep to ensure uniqueness)
    Thread.sleep( 2 );  E e1 = makeEntity(); assertNotNull( e1 );
    Thread.sleep( 2 );  E e2 = makeEntity(); assertNotNull( e2 );

    // Load original etity
    E e3 = dao.getById( id );
    assertTrue( dao.theSame( entity,e3 ));

    // Load original entity proxy
    E e4 = dao.getProxyById( id );
    assertNotNull( e4 );

    // Load all entities
    List<E> list = dao.getAll();
    assertNotNull( list );
    assertTrue( list.size() >= 3 );

    // Delete an entity
    dao.delete( e1 );
    E e5 = dao.getById( dao.getIdentifier( e1 ));
    assertNull( e5 );

    // Get/Update Identifier
    E e6 = dao.getEntityClass().newInstance();
    dao.updateIdentifier( e6, new Integer( Integer.MAX_VALUE ));
    assertEquals( Integer.MAX_VALUE, ((Integer)dao.getIdentifier( e6 )).intValue() );
  }

  @Test
  public void testEntityConstants()
  {
    BaseDao<E> dao = getDao();
    Collection<E> ecs = dao.getEntityConstants();
    for( E e: ecs ) {
      E check = dao.getById( dao.getIdentifier( e ));
      assertTrue( dao.theSame( e, check ));
    }
  }
}
