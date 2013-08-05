<?php
namespace Idealspoon\ApiBundle\Controller;

use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations\View;
//use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class RestaurantController extends FOSRestController {
	
	/**
	 * 
	 * @return array
	 * @View();
	 */
	public function getRestaurantsAction() {
		$restaurants = $this->getDoctrine()->getRepository('IdealspoonApiBundle:IspRestaurant')->findAll();
		
		return array("restaurants"=>$restaurants);
	}
	
	/**
	 * @return array
	 * @View();
	 */
	public function getMetadataAction() {
		$returnArray = array("metadata"=>array());
		
		$ambience = $this->getDoctrine()->getRepository('IdealspoonApiBundle:IspAmbience')->findAll();
		$returnArray["metadata"]["ambience"] = $ambience;
		$cusine = $this->getDoctrine()->getRepository('IdealspoonApiBundle:IspCusine')->findAll();
		$returnArray["metadata"]["cusine"] = $cusine;
		$rating = array(0=>0,1=>0.5,2=>1.0,3=>1.5,4=>2.0,5=>2.5,6=>3.0,7=>3.5,8=>4.0,9=>4.5,10=>5.0);
		$returnArray["metadata"]["rating"] = $rating;
		return $returnArray;
	}
}
