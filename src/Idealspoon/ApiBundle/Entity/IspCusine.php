<?php

namespace Idealspoon\ApiBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * IspCusine
 *
 * @ORM\Table(name="isp_cusine")
 * @ORM\Entity
 */
class IspCusine
{
    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=45, nullable=true)
     */
    private $name;

    /**
     * @var string
     *
     * @ORM\Column(name="dscr", type="string", length=255, nullable=true)
     */
    private $dscr;

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
     * @ORM\ManyToMany(targetEntity="Idealspoon\ApiBundle\Entity\IspRestaurant", mappedBy="cusine")
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
     * Set name
     *
     * @param string $name
     * @return IspCusine
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
     * Set dscr
     *
     * @param string $dscr
     * @return IspCusine
     */
    public function setDscr($dscr)
    {
        $this->dscr = $dscr;
    
        return $this;
    }

    /**
     * Get dscr
     *
     * @return string 
     */
    public function getDscr()
    {
        return $this->dscr;
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
     * Add restaurant
     *
     * @param \Idealspoon\ApiBundle\Entity\IspRestaurant $restaurant
     * @return IspCusine
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