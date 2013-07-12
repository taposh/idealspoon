package com.netshelter.ifbrands.api.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dmitriy T
 */
public class AdTagGenerationServiceImplTest
    extends BaseServiceTest
{
    @Autowired
    AdTagGenerationService adTagGenerationService = null;


    @Test
    public void testAdTagGeneration()
    {
        String result = adTagGenerationService.createTag(1, "example.vm");
        System.out.println(result);
    }

}
