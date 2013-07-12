package com.netshelter.ifbrands.api.service;

/**
 * @author Dmitriy T
 */
public interface AdTagGenerationService
{
    /**
     * Returns an HTML version of the ad tag generated based on ad id provided,
     * and ad tag settings stored in the db. Using velocity template: templateFileName
     *
     * @param adId
     * @param templateFileName
     * @return
     */
    public String createTag(Integer adId, String templateFileName);
}
