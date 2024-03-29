<?php

namespace Proxies\__CG__\Idealspoon\ApiBundle\Entity;

/**
 * THIS CLASS WAS GENERATED BY THE DOCTRINE ORM. DO NOT EDIT THIS FILE.
 */
class IspAddress extends \Idealspoon\ApiBundle\Entity\IspAddress implements \Doctrine\ORM\Proxy\Proxy
{
    private $_entityPersister;
    private $_identifier;
    public $__isInitialized__ = false;
    public function __construct($entityPersister, $identifier)
    {
        $this->_entityPersister = $entityPersister;
        $this->_identifier = $identifier;
    }
    /** @private */
    public function __load()
    {
        if (!$this->__isInitialized__ && $this->_entityPersister) {
            $this->__isInitialized__ = true;

            if (method_exists($this, "__wakeup")) {
                // call this after __isInitialized__to avoid infinite recursion
                // but before loading to emulate what ClassMetadata::newInstance()
                // provides.
                $this->__wakeup();
            }

            if ($this->_entityPersister->load($this->_identifier, $this) === null) {
                throw new \Doctrine\ORM\EntityNotFoundException();
            }
            unset($this->_entityPersister, $this->_identifier);
        }
    }

    /** @private */
    public function __isInitialized()
    {
        return $this->__isInitialized__;
    }

    
    public function setAddressOne($addressOne)
    {
        $this->__load();
        return parent::setAddressOne($addressOne);
    }

    public function getAddressOne()
    {
        $this->__load();
        return parent::getAddressOne();
    }

    public function setAddressTwo($addressTwo)
    {
        $this->__load();
        return parent::setAddressTwo($addressTwo);
    }

    public function getAddressTwo()
    {
        $this->__load();
        return parent::getAddressTwo();
    }

    public function setCity($city)
    {
        $this->__load();
        return parent::setCity($city);
    }

    public function getCity()
    {
        $this->__load();
        return parent::getCity();
    }

    public function setZipCode($zipCode)
    {
        $this->__load();
        return parent::setZipCode($zipCode);
    }

    public function getZipCode()
    {
        $this->__load();
        return parent::getZipCode();
    }

    public function getId()
    {
        if ($this->__isInitialized__ === false) {
            return (int) $this->_identifier["id"];
        }
        $this->__load();
        return parent::getId();
    }

    public function setState(\Idealspoon\ApiBundle\Entity\IspState $state = NULL)
    {
        $this->__load();
        return parent::setState($state);
    }

    public function getState()
    {
        $this->__load();
        return parent::getState();
    }

    public function addRestaurant(\Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant)
    {
        $this->__load();
        return parent::addRestaurant($restaurant);
    }

    public function removeRestaurant(\Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant)
    {
        $this->__load();
        return parent::removeRestaurant($restaurant);
    }

    public function getRestaurant()
    {
        $this->__load();
        return parent::getRestaurant();
    }


    public function __sleep()
    {
        return array('__isInitialized__', 'addressOne', 'addressTwo', 'city', 'zipCode', 'id', 'state', 'restaurant');
    }

    public function __clone()
    {
        if (!$this->__isInitialized__ && $this->_entityPersister) {
            $this->__isInitialized__ = true;
            $class = $this->_entityPersister->getClassMetadata();
            $original = $this->_entityPersister->load($this->_identifier);
            if ($original === null) {
                throw new \Doctrine\ORM\EntityNotFoundException();
            }
            foreach ($class->reflFields as $field => $reflProperty) {
                $reflProperty->setValue($this, $reflProperty->getValue($original));
            }
            unset($this->_entityPersister, $this->_identifier);
        }
        
    }
}