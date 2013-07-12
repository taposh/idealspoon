package com.netshelter.ifbrands.validation.suite;

import com.netshelter.ifbrands.util.ApplicationContextFactory;
import com.netshelter.ifbrands.validation.ValidationResponse;

/**
 * @author Dmitriy T
 */
public abstract class ValidationSuite
    extends ApplicationContextFactory
{
    public abstract ValidationResponse validate();
}
