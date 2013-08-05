<?php

use Symfony\Component\Routing\Exception\MethodNotAllowedException;
use Symfony\Component\Routing\Exception\ResourceNotFoundException;
use Symfony\Component\Routing\RequestContext;

/**
 * appProdUrlMatcher
 *
 * This class has been auto-generated
 * by the Symfony Routing Component.
 */
class appProdUrlMatcher extends Symfony\Bundle\FrameworkBundle\Routing\RedirectableUrlMatcher
{
    /**
     * Constructor.
     */
    public function __construct(RequestContext $context)
    {
        $this->context = $context;
    }

    public function match($pathinfo)
    {
        $allow = array();
        $pathinfo = rawurldecode($pathinfo);

        // idealspoon_api_default_index
        if (0 === strpos($pathinfo, '/hello') && preg_match('#^/hello/(?P<name>[^/]++)$#s', $pathinfo, $matches)) {
            return $this->mergeDefaults(array_replace($matches, array('_route' => 'idealspoon_api_default_index')), array (  '_controller' => 'Idealspoon\\ApiBundle\\Controller\\DefaultController::indexAction',));
        }

        // get_restaurants
        if (0 === strpos($pathinfo, '/restaurants') && preg_match('#^/restaurants(?:\\.(?P<_format>rss|html))?$#s', $pathinfo, $matches)) {
            if (!in_array($this->context->getMethod(), array('GET', 'HEAD'))) {
                $allow = array_merge($allow, array('GET', 'HEAD'));
                goto not_get_restaurants;
            }

            return $this->mergeDefaults(array_replace($matches, array('_route' => 'get_restaurants')), array (  '_controller' => 'Idealspoon\\ApiBundle\\Controller\\RestaurantController::getRestaurantsAction',  '_format' => NULL,));
        }
        not_get_restaurants:

        throw 0 < count($allow) ? new MethodNotAllowedException(array_unique($allow)) : new ResourceNotFoundException();
    }
}
