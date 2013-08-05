<?php

namespace Idealspoon\ApiBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * IspRestaurant
 *
 * @ORM\Table(name="isp_restaurant")
 * @ORM\Entity
 */
class IspRestaurant
{
    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=45, nullable=true)
     */
    private $name;

    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Idealspoon\ApiBundle\Entity\IspCusine", inversedBy="restaurant")
     * @ORM\JoinTable(name="isp_restaurant_cusine",
     *   joinColumns={
     *     @ORM\JoinColumn(name="restaurant_id", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="cusine_id", referencedColumnName="id")
     *   }
     * )
     */
    private $cusine;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Idealspoon\ApiBundle\Entity\IspAmbience", inversedBy="restaurant")
     * @ORM\JoinTable(name="isp_restaurant_ambience",
     *   joinColumns={
     *     @ORM\JoinColumn(name="restaurant_id", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="ambience_id", referencedColumnName="id")
     *   }
     * )
     */
    private $ambience;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="Idealspoon\ApiBundle\Entity\IspAddress", inversedBy="restaurant")
     * @ORM\JoinTable(name="isp_restaurant_address",
     *   joinColumns={
     *     @ORM\JoinColumn(name="restaurant_id", referencedColumnName="id")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="address_id", referencedColumnName="id")
     *   }
     * )
     */
    private $address;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->cusine = new \Doctrine\Common\Collections\ArrayCollection();
        $this->ambience = new \Doctrine\Common\Collections\ArrayCollection();
        $this->address = new \Doctrine\Common\Collections\ArrayCollection();
    }
    

    /**
     * Set name
     *
     * @param string $name
     * @return IspRestaurant
     */
    public function setName($name)
    {
        $this->name = $name;
    
        return $this;
    }

    /**
     * Get name
     *
     * @return string 
     */
    public function getName()
    {
        return $this->name;
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
     * Add cusine
     *
     * @param \Idealspoon\ApiBundle\Entity\IspCusine $cusine
     * @return IspRestaurant
     */
    public function addCusine(\Idealspoon\ApiBundle\Entity\IspCusine $cusine)
    {
        $this->cusine[] = $cusine;
    
        return $this;
    }

    /**
     * Remove cusine
     *
     * @param \Idealspoon\ApiBundle\Entity\IspCusine $cusine
     */
    public function removeCusine(\Idealspoon\ApiBundle\Entity\IspCusine $cusine)
    {
        $this->cusine->removeElement($cusine);
    }

    /**
     * Get cusine
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getCusine()
    {
        return $this->cusine;
    }

    /**
     * Add ambience
     *
     * @param \Idealspoon\ApiBundle\Entity\IspAmbience $ambience
     * @return IspRestaurant
     */
    public function addAmbience(\Idealspoon\ApiBundle\Entity\IspAmbience $ambience)
    {
        $this->ambience[] = $ambience;
    
        return $this;
    }

    /**
     * Remove ambience
     *
     * @param \Idealspoon\ApiBundle\Entity\IspAmbience $ambience
     */
    public function removeAmbience(\Idealspoon\ApiBundle\Entity\IspAmbience $ambience)
    {
        $this->ambience->removeElement($ambience);
    }

    /**
     * Get ambience
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getAmbience()
    {
        return $this->ambience;
    }

    /**
     * Add address
     *
     * @param \Idealspoon\ApiBundle\Entity\IspAddress $address
     * @return IspRestaurant
     */
    public function addAddres(\Idealspoon\ApiBundle\Entity\IspAddress $address)
    {
        $this->address[] = $address;
    
        return $this;
    }

    /**
     * Remove address
     *
     * @param \Idealspoon\ApiBundle\Entity\IspAddress $address
     */
    public function removeAddres(\Idealspoon\ApiBundle\Entity\IspAddress $address)
    {
        $this->address->removeElement($address);
    }

    /**
     * Get address
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getAddress()
    {
        return $this->address;
    }
}