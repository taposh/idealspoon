package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Dao<E>
{
  public E save( E entity );
  public E update( E entity );
  public void delete( E entity );

  public E getById( Serializable id );
  public E getProxyById( Serializable id );
  public E findFirstByProperty( String propertyName, Object value );
  public List<E> findByProperty( String propertyName, Object value );
  public List<E> getByIds( Collection<? extends Serializable> id );
  public List<E> getAll();

  public String asString( E entity );
  public boolean theSame( E a, E b );
}
