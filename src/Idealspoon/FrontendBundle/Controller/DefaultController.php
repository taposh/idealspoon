<?php

namespace Idealspoon\FrontendBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('IdealspoonFrontendBundle:Default:index.html.twig');
    }
}
