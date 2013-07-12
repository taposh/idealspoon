package com.netshelter.ifbrands.data.dao;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( {"classpath:application-context.xml" })
public abstract class DaoTest extends Assert
{

}