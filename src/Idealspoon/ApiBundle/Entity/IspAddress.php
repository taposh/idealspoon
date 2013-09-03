<?php

namespace Idealspoon\ApiBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * IspAddress
 *
 * @ORM\Table(name="isp_address")
 * @ORM\Entity
 */
class IspAddress
{
    /**
     * @var string
     *
     * @ORM\Column(name="address_one", type="string", length=255, nullable=true)
     */
    private $addressOne;

    /**
     * @var string
     *
     * @ORM\Column(name="address_two", type="string", length=45, nullable=true)
     */
    private $addressTwo;

    /**
     * @var string
     *
     * @ORM\Column(name="city", type="string", length=45, nullable=true)
     */
    private $city;

    /**
     * @var string
     *
     * @ORM\Column(name="zip_code", type="string", length=45, nullable=true)
     */
    private $zipCode;

    /**
     * @var float
     *
     * @ORM\Column(name="latitude", type="decimal", nullable=true)
     */
    private $latitude;

    /**
     * @var float
     *
     * @ORM\Column(name="longitude", type="decimal", nullable=true)
     */
    private $longitude;

    /**
     * @var string
     *
     * @ORM\Column(name="phone", type="string", length=45, nullable=true)
     */
    private $phone;

    /**
     * @var integer
     *
     * @ORM\Column(name="status", type="integer", nullable=true)
     */
    private $status;

    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var \Idealspoon\ApiBundle\Entity\IspState
     *
     * @ORM\ManyToOne(targetEntity="Idealspoon\ApiBundle\Entity\IspState")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="state_id", referencedColumnName="id")
     * })
     */
    private $state;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Idealspoon\ApiBundle\Entity\IspRestaurant", mappedBy="address")
     */
    private $restaurant;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->restaurant = new \Doctrine\Common\Collections\ArrayCollection();
    }
    

    /**
     * Set addressOne
     *
     * @param string $addressOne
     * @return IspAddress
     */
    public function setAddressOne($addressOne)
    {
        $this->addressOne = $addressOne;
    
        return $this;
    }

    /**
     * Get addressOne
     *
     * @return string 
     */
    public function getAddressOne()
    {
        return $this->addressOne;
    }

    /**
     * Set addressTwo
     *
     * @param string $addressTwo
     * @return IspAddress
     */
    public function setAddressTwo($addressTwo)
    {
        $this->addressTwo = $addressTwo;
    
        return $this;
    }

    /**
     * Get addressTwo
     *
     * @return string 
     */
    public function getAddressTwo()
    {
        return $this->addressTwo;
    }

    /**
     * Set city
     *
     * @param string $city
     * @return IspAddress
     */
    public function setCity($city)
    {
        $this->city = $city;
    
        return $this;
    }

    /**
     * Get city
     *
     * @return string 
     */
    public function getCity()
    {
        return $this->city;
    }

    /**
     * Set zipCode
     *
     * @param string $zipCode
     * @return IspAddress
     */
    public function setZipCode($zipCode)
    {
        $this->zipCode = $zipCode;
    
        return $this;
    }

    /**
     * Get zipCode
     *
     * @return string 
     */
    public function getZipCode()
    {
        return $this->zipCode;
    }

    /**
     * Set latitude
     *
     * @param float $latitude
     * @return IspAddress
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;
    
        return $this;
    }

    /**
     * Get latitude
     *
     * @return float 
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * Set longitude
     *
     * @param float $longitude
     * @return IspAddress
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;
    
        return $this;
    }

    /**
     * Get longitude
     *
     * @return float 
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * Set phone
     *
     * @param string $phone
     * @return IspAddress
     */
    public function setPhone($phone)
    {
        $this->phone = $phone;
    
        return $this;
    }

    /**
     * Get phone
     *
     * @return string 
     */
    public function getPhone()
    {
        return $this->phone;
    }

    /**
     * Set status
     *
     * @param integer $status
     * @return IspAddress
     */
    public function setStatus($status)
    {
        $this->status = $status;
    
        return $this;
    }

    /**
     * Get status
     *
     * @return integer 
     */
    public function getStatus()
    {
        return $this->status;
    }

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set state
     *
     * @param \Idealspoon\ApiBundle\Entity\IspState $state
     * @return IspAddress
     */
    public function setState(\Idealspoon\ApiBundle\Entity\IspState $state = null)
    {
        $this->state = $state;
    
        return $this;
    }

    /**
     * Get state
     *
     * @return \Idealspoon\ApiBundle\Entity\IspState 
     */
    public function getState()
    {
        return $this->state;
    }

    /**
     * Add restaurant
     *
     * @param \Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant
     * @return IspAddress
     */
    public function addRestaurant(\Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant)
    {
        $this->restaurant[] = $restaurant;
    
        return $this;
    }

    /**
     * Remove restaurant
     *
     * @param \Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant
     */
    public function removeRestaurant(\Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant)
    {
        $this->restaurant->removeElement($restaurant);
    }

    /**
     * Get restaurant
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getRestaurant()
    {
        return $this->restaurant;
    }
}