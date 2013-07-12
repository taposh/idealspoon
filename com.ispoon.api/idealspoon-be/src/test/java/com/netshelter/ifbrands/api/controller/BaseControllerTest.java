package com.netshelter.ifbrands.api.controller;

import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( {"classpath:application-context.xml" })
public abstract class BaseControllerTest extends Assert
{

    protected Logger logger = LoggerFactory.getLogger();

}
