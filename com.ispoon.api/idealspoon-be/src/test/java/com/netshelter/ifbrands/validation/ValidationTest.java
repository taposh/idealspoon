package com.netshelter.ifbrands.validation;

import com.netshelter.ifbrands.data.dao.DaoTest;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Dmitriy T
 */
@Component
public class ValidationTest
    extends DaoTest
{

    @Test
    public void testUrlValidation() throws Exception
    {
        System.out.println("Checking valid url");
        boolean assertion = (new UrlValidator("http://www.google.com", true)).validate();
        assertTrue("Checking valid url", assertion);
        System.out.println("Checking invalid url");
        assertion = (new UrlValidator("http://w1w2w3.4g5o6o7g8l9e0.1c2o3m", true)).validate();
        assertFalse("Checking invalid url", assertion);
        System.out.println("Checking valid bit.ly url with redirects on");
        assertion =  (new UrlValidator("http://bit.ly/Jdz0V", true)).validate();
        assertTrue("Checking valid bit.ly url with redirects on", assertion);
        System.out.println("Checking valid bit.ly url with redirects off");
        assertion =  (new UrlValidator("http://bit.ly/Jdz0V", false)).validate();
        assertFalse("Checking valid bit.ly url with redirects off", assertion);
        System.out.println("Checking content type (html) bit.ly url with redirects on");
        assertion = (new UrlContentTypeValidator( "http://bit.ly/Jdz0V",
                                                  Collections.singletonList("text/html"),
                                                  null,
                                                  true)).validate();
        assertTrue("Checking content type (html) bit.ly url with redirects on", assertion);
        System.out.println("Checking content type (json) bit.ly url with redirects on");
        assertion = (new UrlContentTypeValidator( "http://bit.ly/12t2onq",
                                                  Collections.singletonList("application/json"),
                                                  null,
                                                  true)).validate();
        assertTrue("Checking content type (json) bit.ly url with redirects on", assertion);
    }
}
