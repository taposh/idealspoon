<?php
namespace Idealspoon\ApiBundle\Controller;

use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Controller\Annotations\View;
use Symfony\Component\HttpFoundation\Request;
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
	
	/**
	 * @return array
	 * @View();
	 */
	public function getLocationAction(Request $request) {
		if (!$request->query->has('lat')) {
			throw new HttpException(400);
		} else {
			$latitude = $request->get('lat');
		}
		
		if (!$request->query->has('lng')) {
			throw new HttpException(400);
		} else {
			$longitude = $request->get('lng');
		}
		
		if ($request->query->has('km')) {
			$distanceConstant = 6371;
		} else {
			$distanceConstant = 3959;
		}
		
		//Default search radius by 5
		$requestDistance = $request->query->get('r', 5);
		
		$query = $this->getDoctrine()->getRepository('IdealspoonApiBundle:IspAddress')->createQueryBuilder('l');
		$query->select('l')
		->addSelect(
				'( '.$distanceConstant.' * acos(cos(radians(' . $latitude . '))' .
				'* cos( radians( l.latitude ) )' .
				'* cos( radians( l.longitude )' .
				'- radians(' . $longitude . ') )' .
				'+ sin( radians(' . $latitude . ') )' .
				'* sin( radians( l.latitude ) ) ) ) as distance'
		)
		->andWhere('l.status = :status')
		->setParameter('status', 1)
		->andWhere('distance < :distance')
		->setParameter('distance', $requestedDistance)
		->orderBy('distance', 'ASC');
		return $query;
	}
	
}
