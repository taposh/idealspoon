<?php

/* AcmeDemoBundle:Demo:contact.html.twig */
class __TwigTemplate_ce215bb50cf527cf47f5b8c2476658b9 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("AcmeDemoBundle::layout.html.twig");

        $this->blocks = array(
            'title' => array($this, 'block_title'),
            'content' => array($this, 'block_content'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "AcmeDemoBundle::layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_title($context, array $blocks = array())
    {
        echo "Symfony - Contact form";
    }

    // line 5
    public function block_content($context, array $blocks = array())
    {
        // line 6
        echo "    <form action=\"";
        echo $this->env->getExtension('routing')->getPath("_demo_contact");
        echo "\" method=\"POST\" id=\"contact_form\">
        ";
        // line 7
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'errors');
        echo "

        ";
        // line 9
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "email"), 'row');
        echo "
        ";
        // line 10
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "message"), 'row');
        echo "

        ";
        // line 12
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'rest');
        echo "
        <input type=\"submit\" value=\"Send\" class=\"symfony-button-grey\" />
    </form>
";
    }

    public function getTemplateName()
    {
        return "AcmeDemoBundle:Demo:contact.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  53 => 11,  23 => 3,  100 => 27,  81 => 24,  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 218,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 160,  353 => 154,  349 => 153,  345 => 152,  338 => 150,  334 => 149,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 127,  287 => 126,  280 => 124,  277 => 123,  259 => 110,  257 => 109,  249 => 105,  231 => 98,  211 => 91,  207 => 90,  194 => 80,  191 => 79,  186 => 76,  165 => 70,  70 => 24,  58 => 18,  161 => 32,  90 => 32,  84 => 29,  34 => 7,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 361,  447 => 360,  335 => 250,  329 => 248,  323 => 246,  321 => 138,  261 => 188,  127 => 28,  110 => 22,  104 => 42,  76 => 17,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 172,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 106,  341 => 151,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 132,  298 => 91,  294 => 90,  285 => 89,  283 => 125,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 95,  214 => 92,  177 => 73,  169 => 71,  140 => 55,  132 => 51,  128 => 51,  107 => 36,  61 => 12,  273 => 122,  269 => 94,  254 => 108,  243 => 88,  240 => 101,  238 => 85,  235 => 74,  230 => 82,  227 => 97,  224 => 96,  221 => 77,  219 => 76,  217 => 75,  208 => 68,  204 => 72,  179 => 69,  159 => 61,  143 => 56,  135 => 53,  119 => 22,  102 => 17,  71 => 27,  67 => 20,  63 => 15,  59 => 13,  28 => 5,  94 => 34,  89 => 35,  85 => 33,  75 => 17,  68 => 23,  56 => 11,  38 => 6,  87 => 25,  201 => 92,  196 => 81,  183 => 82,  171 => 61,  166 => 71,  163 => 62,  158 => 68,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 24,  121 => 46,  117 => 19,  105 => 18,  91 => 27,  62 => 19,  49 => 10,  93 => 29,  88 => 31,  78 => 26,  44 => 12,  31 => 6,  27 => 4,  21 => 2,  24 => 7,  25 => 4,  46 => 11,  26 => 12,  19 => 1,  79 => 18,  72 => 22,  69 => 25,  47 => 8,  40 => 11,  37 => 8,  22 => 2,  246 => 90,  157 => 30,  145 => 57,  139 => 45,  131 => 52,  123 => 47,  120 => 20,  115 => 46,  111 => 37,  108 => 19,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 14,  52 => 10,  50 => 10,  43 => 7,  41 => 5,  35 => 5,  32 => 5,  29 => 3,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 64,  173 => 72,  168 => 72,  164 => 59,  162 => 69,  154 => 29,  149 => 51,  147 => 58,  144 => 49,  141 => 48,  133 => 53,  130 => 52,  125 => 50,  122 => 23,  116 => 41,  112 => 42,  109 => 42,  106 => 36,  103 => 28,  99 => 31,  95 => 28,  92 => 36,  86 => 28,  82 => 28,  80 => 30,  73 => 16,  64 => 13,  60 => 16,  57 => 12,  54 => 16,  51 => 13,  48 => 9,  45 => 8,  42 => 7,  39 => 7,  36 => 5,  33 => 3,  30 => 3,);
    }
}
