<?php

/* FOSUserBundle::layout.html.twig */
class __TwigTemplate_3bc66a427f299af7cf6c7adaf519f64f extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'fos_user_content' => array($this, 'block_fos_user_content'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<!DOCTYPE html>
<html>
    <head>
        <meta charset=\"UTF-8\" />
    </head>
    <body>
        <div>
            ";
        // line 8
        if ($this->env->getExtension('security')->isGranted("IS_AUTHENTICATED_REMEMBERED")) {
            // line 9
            echo "                ";
            echo twig_escape_filter($this->env, $this->env->getExtension('translator')->trans("layout.logged_in_as", array("%username%" => $this->getAttribute($this->getAttribute((isset($context["app"]) ? $context["app"] : $this->getContext($context, "app")), "user"), "username")), "FOSUserBundle"), "html", null, true);
            echo " |
                <a href=\"";
            // line 10
            echo $this->env->getExtension('routing')->getPath("fos_user_security_logout");
            echo "\">
                    ";
            // line 11
            echo twig_escape_filter($this->env, $this->env->getExtension('translator')->trans("layout.logout", array(), "FOSUserBundle"), "html", null, true);
            echo "
                </a>
            ";
        } else {
            // line 14
            echo "                <a href=\"";
            echo $this->env->getExtension('routing')->getPath("fos_user_security_login");
            echo "\">";
            echo twig_escape_filter($this->env, $this->env->getExtension('translator')->trans("layout.login", array(), "FOSUserBundle"), "html", null, true);
            echo "</a>
            ";
        }
        // line 16
        echo "        </div>

        ";
        // line 18
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable($this->getAttribute($this->getAttribute($this->getAttribute((isset($context["app"]) ? $context["app"] : $this->getContext($context, "app")), "session"), "flashbag"), "all", array(), "method"));
        foreach ($context['_seq'] as $context["type"] => $context["messages"]) {
            // line 19
            echo "            ";
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable((isset($context["messages"]) ? $context["messages"] : $this->getContext($context, "messages")));
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 20
                echo "                <div class=\"flash-";
                echo twig_escape_filter($this->env, (isset($context["type"]) ? $context["type"] : $this->getContext($context, "type")), "html", null, true);
                echo "\">
                    ";
                // line 21
                echo twig_escape_filter($this->env, (isset($context["message"]) ? $context["message"] : $this->getContext($context, "message")), "html", null, true);
                echo "
                </div>
            ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['_key'], $context['message'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 24
            echo "        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['type'], $context['messages'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 25
        echo "
        <div>
            ";
        // line 27
        $this->displayBlock('fos_user_content', $context, $blocks);
        // line 29
        echo "        </div>
    </body>
</html>
";
    }

    // line 27
    public function block_fos_user_content($context, array $blocks = array())
    {
        // line 28
        echo "            ";
    }

    public function getTemplateName()
    {
        return "FOSUserBundle::layout.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  100 => 27,  81 => 24,  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 218,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 160,  353 => 154,  349 => 153,  345 => 152,  338 => 150,  334 => 149,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 127,  287 => 126,  280 => 124,  277 => 123,  259 => 110,  257 => 109,  249 => 105,  231 => 98,  211 => 91,  207 => 90,  194 => 80,  191 => 79,  186 => 76,  165 => 70,  70 => 24,  58 => 18,  161 => 32,  90 => 15,  84 => 13,  34 => 6,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 361,  447 => 360,  335 => 250,  329 => 248,  323 => 246,  321 => 138,  261 => 188,  127 => 56,  110 => 45,  104 => 42,  76 => 29,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 172,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 106,  341 => 151,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 132,  298 => 91,  294 => 90,  285 => 89,  283 => 125,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 95,  214 => 92,  177 => 73,  169 => 71,  140 => 55,  132 => 51,  128 => 51,  107 => 36,  61 => 13,  273 => 122,  269 => 94,  254 => 108,  243 => 88,  240 => 101,  238 => 85,  235 => 74,  230 => 82,  227 => 97,  224 => 96,  221 => 77,  219 => 76,  217 => 75,  208 => 68,  204 => 72,  179 => 69,  159 => 61,  143 => 56,  135 => 53,  119 => 22,  102 => 21,  71 => 27,  67 => 20,  63 => 15,  59 => 14,  28 => 3,  94 => 28,  89 => 35,  85 => 33,  75 => 17,  68 => 23,  56 => 21,  38 => 8,  87 => 25,  201 => 92,  196 => 81,  183 => 82,  171 => 61,  166 => 71,  163 => 62,  158 => 68,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 24,  121 => 46,  117 => 47,  105 => 40,  91 => 27,  62 => 19,  49 => 14,  93 => 29,  88 => 33,  78 => 10,  44 => 12,  31 => 9,  27 => 4,  21 => 2,  24 => 2,  25 => 4,  46 => 14,  26 => 6,  19 => 2,  79 => 18,  72 => 21,  69 => 25,  47 => 17,  40 => 11,  37 => 8,  22 => 3,  246 => 90,  157 => 30,  145 => 57,  139 => 45,  131 => 52,  123 => 47,  120 => 48,  115 => 46,  111 => 37,  108 => 44,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 17,  52 => 6,  50 => 10,  43 => 6,  41 => 5,  35 => 5,  32 => 5,  29 => 8,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 64,  173 => 72,  168 => 72,  164 => 59,  162 => 69,  154 => 29,  149 => 51,  147 => 58,  144 => 49,  141 => 48,  133 => 53,  130 => 52,  125 => 50,  122 => 23,  116 => 41,  112 => 42,  109 => 42,  106 => 36,  103 => 28,  99 => 31,  95 => 28,  92 => 36,  86 => 28,  82 => 31,  80 => 30,  73 => 26,  64 => 14,  60 => 9,  57 => 11,  54 => 16,  51 => 15,  48 => 8,  45 => 17,  42 => 7,  39 => 9,  36 => 10,  33 => 6,  30 => 7,);
    }
}
