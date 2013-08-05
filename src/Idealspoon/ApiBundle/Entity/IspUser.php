<?php

namespace Idealspoon\ApiBundle\Entity;

use FOS\UserBundle\Model\User as BaseUser;
use Doctrine\ORM\Mapping as ORM;

/**
 * IspAddress
 *
 * @ORM\Table(name="isp_user")
 * @ORM\Entity
 */
class IspUser extends BaseUser
{

    /**
     * @ORM\Id
     * @ORM\Column(type="integer")
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;
    
    public function __construct()
    {
    	parent::__construct();
    	// your own logic
    }


}