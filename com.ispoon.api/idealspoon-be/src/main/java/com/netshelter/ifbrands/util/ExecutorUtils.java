package com.netshelter.ifbrands.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.SchedulingException;

public class ExecutorUtils
{
    public static <T> List<Future<T>> invokeAll(ExecutorService executorService,
                                                Collection<? extends Callable<T>> tasks)
    {
        try
        {
            return executorService.invokeAll(tasks);
        }
        catch (InterruptedException e)
        {
            throw new SchedulingException("Could not complete asynchronous operation", e);
        }
    }

    public static <T> List<Future<T>> invokeAll( ExecutorService executorService,
                                                 Collection<? extends Callable<T>> tasks,
                                                 long timeout, TimeUnit unit)
    {
        try {
            return executorService.invokeAll( tasks, timeout, unit );
        } catch( InterruptedException e ) {
            throw new SchedulingException( "Could not complete asynchronous operation", e );
        }
    }

}
