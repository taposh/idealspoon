<?php

/* FOSUserBundle:Resetting:email.txt.twig */
class __TwigTemplate_6ce4f698d754c5d520046686afb41753 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'subject' => array($this, 'block_subject'),
            'body_text' => array($this, 'block_body_text'),
            'body_html' => array($this, 'block_body_html'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 2
        $this->displayBlock('subject', $context, $blocks);
        // line 7
        $this->displayBlock('body_text', $context, $blocks);
        // line 12
        $this->displayBlock('body_html', $context, $blocks);
    }

    // line 2
    public function block_subject($context, array $blocks = array())
    {
        // line 4
        echo $this->env->getExtension('translator')->trans("resetting.email.subject", array("%username%" => $this->getAttribute((isset($context["user"]) ? $context["user"] : $this->getContext($context, "user")), "username"), "%confirmationUrl%" => (isset($context["confirmationUrl"]) ? $context["confirmationUrl"] : $this->getContext($context, "confirmationUrl"))), "FOSUserBundle");
        echo "
";
    }

    // line 7
    public function block_body_text($context, array $blocks = array())
    {
        // line 9
        echo $this->env->getExtension('translator')->trans("resetting.email.message", array("%username%" => $this->getAttribute((isset($context["user"]) ? $context["user"] : $this->getContext($context, "user")), "username"), "%confirmationUrl%" => (isset($context["confirmationUrl"]) ? $context["confirmationUrl"] : $this->getContext($context, "confirmationUrl"))), "FOSUserBundle");
        echo "
";
    }

    // line 12
    public function block_body_html($context, array $blocks = array())
    {
    }

    public function getTemplateName()
    {
        return "FOSUserBundle:Resetting:email.txt.twig";
    }

    public function getDebugInfo()
    {
        return array (  23 => 4,  100 => 27,  81 => 24,  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 218,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 160,  353 => 154,  349 => 153,  345 => 152,  338 => 150,  334 => 149,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 127,  287 => 126,  280 => 124,  277 => 123,  259 => 110,  257 => 109,  249 => 105,  231 => 98,  211 => 91,  207 => 90,  194 => 80,  191 => 79,  186 => 76,  165 => 70,  70 => 24,  58 => 18,  161 => 32,  90 => 15,  84 => 13,  34 => 6,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 361,  447 => 360,  335 => 250,  329 => 248,  323 => 246,  321 => 138,  261 => 188,  127 => 56,  110 => 45,  104 => 42,  76 => 29,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 172,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 106,  341 => 151,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 132,  298 => 91,  294 => 90,  285 => 89,  283 => 125,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 95,  214 => 92,  177 => 73,  169 => 71,  140 => 55,  132 => 51,  128 => 51,  107 => 36,  61 => 13,  273 => 122,  269 => 94,  254 => 108,  243 => 88,  240 => 101,  238 => 85,  235 => 74,  230 => 82,  227 => 97,  224 => 96,  221 => 77,  219 => 76,  217 => 75,  208 => 68,  204 => 72,  179 => 69,  159 => 61,  143 => 56,  135 => 53,  119 => 22,  102 => 21,  71 => 27,  67 => 20,  63 => 15,  59 => 14,  28 => 5,  94 => 28,  89 => 35,  85 => 33,  75 => 17,  68 => 23,  56 => 21,  38 => 6,  87 => 25,  201 => 92,  196 => 81,  183 => 82,  171 => 61,  166 => 71,  163 => 62,  158 => 68,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 24,  121 => 46,  117 => 47,  105 => 40,  91 => 27,  62 => 19,  49 => 14,  93 => 29,  88 => 33,  78 => 10,  44 => 12,  31 => 6,  27 => 4,  21 => 2,  24 => 7,  25 => 4,  46 => 11,  26 => 12,  19 => 2,  79 => 18,  72 => 22,  69 => 25,  47 => 17,  40 => 11,  37 => 8,  22 => 2,  246 => 90,  157 => 30,  145 => 57,  139 => 45,  131 => 52,  123 => 47,  120 => 48,  115 => 46,  111 => 37,  108 => 44,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 14,  52 => 6,  50 => 10,  43 => 6,  41 => 5,  35 => 5,  32 => 5,  29 => 5,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 64,  173 => 72,  168 => 72,  164 => 59,  162 => 69,  154 => 29,  149 => 51,  147 => 58,  144 => 49,  141 => 48,  133 => 53,  130 => 52,  125 => 50,  122 => 23,  116 => 41,  112 => 42,  109 => 42,  106 => 36,  103 => 28,  99 => 31,  95 => 28,  92 => 36,  86 => 28,  82 => 31,  80 => 30,  73 => 26,  64 => 14,  60 => 16,  57 => 11,  54 => 16,  51 => 13,  48 => 12,  45 => 17,  42 => 9,  39 => 7,  36 => 10,  33 => 4,  30 => 2,);
    }
}
