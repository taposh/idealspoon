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
        return array (  59 => 13,  53 => 11,  23 => 3,  100 => 27,  81 => 24,  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 194,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 180,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 169,  370 => 166,  367 => 165,  363 => 164,  352 => 155,  350 => 154,  347 => 153,  342 => 150,  333 => 147,  325 => 145,  318 => 143,  301 => 131,  299 => 130,  296 => 129,  291 => 126,  279 => 122,  275 => 121,  271 => 120,  267 => 119,  257 => 116,  244 => 105,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 93,  212 => 92,  205 => 90,  192 => 85,  181 => 76,  179 => 75,  150 => 65,  58 => 18,  161 => 32,  102 => 17,  90 => 32,  34 => 6,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 250,  329 => 146,  323 => 246,  321 => 144,  261 => 117,  127 => 28,  110 => 22,  104 => 42,  76 => 17,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 198,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 142,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 125,  283 => 88,  278 => 86,  268 => 85,  264 => 118,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  235 => 74,  229 => 73,  224 => 71,  220 => 70,  214 => 69,  208 => 68,  169 => 60,  143 => 63,  140 => 55,  132 => 51,  128 => 51,  119 => 22,  107 => 36,  71 => 27,  177 => 65,  165 => 64,  160 => 61,  135 => 47,  126 => 45,  114 => 42,  84 => 29,  70 => 24,  67 => 20,  61 => 12,  28 => 5,  94 => 34,  89 => 35,  85 => 33,  75 => 23,  68 => 23,  56 => 11,  38 => 6,  87 => 25,  201 => 92,  196 => 86,  183 => 70,  171 => 71,  166 => 71,  163 => 70,  158 => 67,  156 => 58,  151 => 57,  142 => 59,  138 => 57,  136 => 24,  121 => 46,  117 => 19,  105 => 18,  91 => 27,  62 => 19,  49 => 10,  93 => 29,  88 => 31,  78 => 26,  44 => 12,  31 => 6,  27 => 5,  21 => 2,  24 => 7,  25 => 4,  46 => 11,  26 => 12,  19 => 1,  79 => 18,  72 => 22,  69 => 25,  47 => 8,  40 => 11,  37 => 8,  22 => 2,  246 => 32,  157 => 30,  145 => 46,  139 => 50,  131 => 42,  123 => 47,  120 => 20,  115 => 46,  111 => 37,  108 => 19,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 14,  52 => 10,  50 => 10,  43 => 7,  41 => 5,  35 => 5,  32 => 5,  29 => 3,  209 => 91,  203 => 78,  199 => 87,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 74,  173 => 74,  168 => 66,  164 => 59,  162 => 68,  154 => 66,  149 => 51,  147 => 64,  144 => 53,  141 => 51,  133 => 55,  130 => 52,  125 => 50,  122 => 23,  116 => 36,  112 => 42,  109 => 42,  106 => 36,  103 => 28,  99 => 30,  95 => 34,  92 => 36,  86 => 28,  82 => 28,  80 => 30,  73 => 16,  64 => 13,  60 => 16,  57 => 12,  54 => 16,  51 => 13,  48 => 9,  45 => 8,  42 => 7,  39 => 7,  36 => 5,  33 => 3,  30 => 3,);
    }
}
