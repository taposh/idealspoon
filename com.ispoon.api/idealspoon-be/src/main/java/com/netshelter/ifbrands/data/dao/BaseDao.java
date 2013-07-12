package com.netshelter.ifbrands.data.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.common.base.Joiner;
import com.netshelter.ifbrands.util.MoreCollections;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Base for all DAO classes which can be identified by a unique ID.
 *
 * @param <E> Type of Hibernate entity produced by this DAO
 * @author bgray
 */
public abstract class BaseDao<E>
        extends HibernateDaoSupport
        implements Dao<E>
{
    protected JdbcDao jdbc;

    /**
     * Return the class object for the entity produced by this DAO.
     */
    protected abstract Class<E> getEntityClass();

    /**
     * Get the identifier field of this entity.
     */
    protected abstract Serializable getIdentifier(E entity);

    /**
     * Set the identifier field of this entity.
     */
    protected abstract void updateIdentifier(E entity, Serializable id);

    /**
     * Bean initialization.
     */
    protected void init()
    {
        // Create JDBC Dao support
        jdbc = new JdbcDao();
        jdbc.setDataSource(SessionFactoryUtils.getDataSource(getSessionFactory()));
        // Create EntityConstants
        createEntityConstants(getEntityConstants());
    }

    /**
     * Provide constant entities for db prepopulation.
     * Entities must have their ID field filled.
     */
    protected Collection<E> getEntityConstants()
    {
        return Collections.emptySet();
    }

    /**
     * Initialize and validate a set of EntityConstants.
     */
    protected void createEntityConstants(Collection<E> entityConstants)
    {
        // Get constants from Dao (if any)
        Collection<E> constants = getEntityConstants();
        for (E e : constants)
        {
            // Verify the entity has an ID
            Serializable id = getIdentifier(e);
            if (id == null) throw new IllegalArgumentException(
                    "Cannot have a constant entity without an explicit ID value: " + asString(e));
            // See if we have an entry at the proper ID
            E check = getById(id);
            if (check == null)
            {
                // No entity at that ID; try creating it
                jdbc.forceInsert(e);
                check = getById(id);
            }
            // Entity exists at ID; see if it is the right one
            if (!theSame(e, check))
            {
                String s = String.format("!!! DB IS INCONSISTENT !!!  Expecting %s but found %s", asString(e),
                                         asString(check));
                throw new DataIntegrityViolationException(s);
            }
            logger.info("Validated Entity Constant: " + asString(check));
        }
    }

    /**
     * Allow subclass to provide customisation of the Criteria.  This is called
     * immediately before the query is passed to Hibernate, and takes place
     * with an Criteria attached to the current active Session.
     *
     * @param c active Criteria
     */
    protected void customiseCriteria(Criteria c)
    {
        // Do nothing
    }

    /**
     * Get a DetachedCriteria object for the produced entity.
     */
    protected DetachedCriteria getDetachedCriteria()
    {
        return DetachedCriteria.forClass(getEntityClass());
    }

    /**
     * Add a simple property equality restriction to the given criteria.
     */
    protected void addPropertyRestriction(DetachedCriteria criteria, String property, Object value)
    {
        if (value != null) criteria.add(Restrictions.eq(property, value));
    }

    /**
     * Add a property "is in" restriction to the given criteria.
     */
    protected void addPropertyRestriction(DetachedCriteria criteria, String property, Collection<? extends Serializable> values)
    {
        if ((values != null) && (!values.isEmpty()))
        {
            Disjunction disj = Restrictions.disjunction();
            for (Serializable s : values)
            {
                disj.add(Restrictions.eq(property, s));
            }
            criteria.add(disj);
        }
    }

    /**
     * Add a simple property isNull restriction to the given criteria.
     */
    protected void addNullPropertyRestriction(DetachedCriteria criteria, String property)
    {
        criteria.add(Restrictions.isNull(property));
    }

    /**
     * Add a simple "ID is in" restriction To the given critieria.
     */
    protected void addIdRestriction(DetachedCriteria criteria, Collection<? extends Serializable> ids)
    {
        if (ids != null)
        {
            Disjunction disj = Restrictions.disjunction();
            for (Serializable s : ids)
            {
                disj.add(Restrictions.idEq(s));
            }
            criteria.add(disj);
        }
    }

    /**
     * Return a list of entities which correspond to the given DetachedCriteria.
     * Note that the criteria will further be customised by the sub-class via the
     * customiseCriteria() protected method, if overridden.
     *
     * @param criteria
     * @return
     */
    @Transactional
    public List<E> findByCriteria(final DetachedCriteria criteria)
    {
        @SuppressWarnings("unchecked")
        List<E> result = getHibernateTemplate().executeFind(new HibernateCallback<List<E>>()
        {
            @Override
            public List<E> doInHibernate(Session session)
                    throws
                    HibernateException,
                    SQLException
            {
                Criteria c = criteria.getExecutableCriteria(session);
                customiseCriteria(c);
                c.setCacheable(true);
                return c.list();
            }
        });
        return result;
    }

    /**
     * Find the first entity of all that match the given criteria, or null if no
     * matches are found.  Note that ordering is not guaranteed unless the criteria
     * has Ordering directives contained within.
     *
     * @param criteria
     * @return
     */
    public E findFirstByCriteria(DetachedCriteria criteria)
    {
        criteria.getExecutableCriteria(getSession()).setCacheable(true);
        List<E> result = findByCriteria(criteria);
        return MoreCollections.firstOrNull(result);
    }

    /**
     * Get all entities.
     */
    @Override
    public List<E> getAll()
    {
        DetachedCriteria criteria = getDetachedCriteria();
        criteria.getExecutableCriteria(getSession()).setCacheable(true);
        return findByCriteria(criteria);
    }

    /**
     * Get a single entity by it's identifier.
     */
    @Override
    public E getById(Serializable id)
    {
        DetachedCriteria criteria = getDetachedCriteria();
        criteria.add(Restrictions.idEq(id));
        criteria.getExecutableCriteria(getSession()).setCacheable(true);
        return findFirstByCriteria(criteria);
    }

    /**
     * Get a list of entities which match the Collection of identifiers.
     */
    @Override
    public List<E> getByIds(Collection<? extends Serializable> ids)
    {
        DetachedCriteria criteria = getDetachedCriteria();
        criteria.getExecutableCriteria(getSession()).setCacheable(true);
        addIdRestriction(criteria, ids);
        return findByCriteria(criteria);
    }

    /**
     * Get a list of entities which match the simple property equality restriction.
     */
    @Override
    public List<E> findByProperty(String propertyName, Object value)
    {
        DetachedCriteria criteria = getDetachedCriteria();
        criteria.getExecutableCriteria(getSession()).setCacheable(true);
        addPropertyRestriction(criteria, propertyName, value);
        return findByCriteria(criteria);
    }

    /**
     * Find the first entity which matches the simple property equality restriction,
     * or null if no matches are found.
     * Ordering is not guaranteed.
     */
    @Override
    public E findFirstByProperty(String propertyName, Object value)
    {
        List<E> list = findByProperty(propertyName, value);
        return MoreCollections.firstOrNull(list);
    }

    /**
     * Persist a new entity.
     */
    @Override
    public E save(E entity)
    {
        Serializable id = getHibernateTemplate().save(entity);
        updateIdentifier(entity, id);
        return entity;
    }

    /**
     * Update an existing entity.
     */
    @Override
    public E update(E entity)
    {
        return getHibernateTemplate().merge(entity);
    }

    /**
     * Delete an existing entity.
     */
    @Override
    public void delete(E entity)
    {
        getHibernateTemplate().delete(entity);
    }

    /**
     * Get a proxy given an identifier. This can be used to set up FK associations
     * with other entities without actually loading the entity.
     */
    @Override
    public E getProxyById(Serializable id)
    {
        return getHibernateTemplate().load(getEntityClass(), id);
    }

    private class JdbcDao
            extends JdbcDaoSupport
    {
        public void forceInsert(E entity)
        {
            Class<E> clazz = getEntityClass();
            // Get @Table annotation
            Table table = clazz.getAnnotation(Table.class);
            if (table == null)
            {
                throw new IllegalArgumentException("Insertion entity not annotated with @Table");
            }
            // Get set of @Columns with their values
            List<String> columns = new ArrayList<String>();
            List<Character> qmarks = new ArrayList<Character>();
            final List<Object> values = new ArrayList<Object>();
            for (Method m : clazz.getMethods())
            {
                Column c = m.getAnnotation(Column.class);
                if (c != null)
                {
                    columns.add(c.name());
                    try
                    {
                        values.add(m.invoke(entity));
                        qmarks.add('?');
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(String.format("Cannot pull data from %s.%s() ",
                                                                 clazz.getSimpleName(), m.getName()), e);
                    }
                }
            }
            // Build INSERT
            final String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                                             table.name(),
                                             Joiner.on(',').join(columns),
                                             Joiner.on(',').join(qmarks));
            // Execute
            int rows = getJdbcTemplate().update(sql, values.toArray());
            if (rows != 1)
            {
                throw new DataIntegrityViolationException(
                        String.format("Attempted to update 1 row [%s] but updated %d instead.  " +
                                              "This will likely require manual cleanup. " +
                                              "SQL: %s Values: %s",
                                      rows, entity, sql, values));
            }
        }
    }
}
