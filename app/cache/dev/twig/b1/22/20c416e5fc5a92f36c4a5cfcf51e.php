<?php

/* SensioDistributionBundle:Configurator:check.html.twig */
class __TwigTemplate_b12220c416e5fc5a92f36c4a5cfcf51e extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("SensioDistributionBundle::Configurator/layout.html.twig");

        $this->blocks = array(
            'content' => array($this, 'block_content'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "SensioDistributionBundle::Configurator/layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_content($context, array $blocks = array())
    {
        // line 4
        echo "    ";
        if (twig_length_filter($this->env, (isset($context["majors"]) ? $context["majors"] : $this->getContext($context, "majors")))) {
            // line 5
            echo "        <h1>Major Problems that need to be fixed now</h1>
        <p>
            We have detected <strong>";
            // line 7
            echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["majors"]) ? $context["majors"] : $this->getContext($context, "majors"))), "html", null, true);
            echo "</strong> major problems.
            You <em>must</em> fix them before continuing:
        </p>
        <ol>
            ";
            // line 11
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable((isset($context["majors"]) ? $context["majors"] : $this->getContext($context, "majors")));
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 12
                echo "                <li>";
                echo twig_escape_filter($this->env, (isset($context["message"]) ? $context["message"] : $this->getContext($context, "message")), "html", null, true);
                echo "</li>
            ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['_key'], $context['message'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 14
            echo "        </ol>
    ";
        }
        // line 16
        echo "
    ";
        // line 17
        if (twig_length_filter($this->env, (isset($context["minors"]) ? $context["minors"] : $this->getContext($context, "minors")))) {
            // line 18
            echo "        <h1>Some Recommendations</h1>

        <p>
            ";
            // line 21
            if (twig_length_filter($this->env, (isset($context["majors"]) ? $context["majors"] : $this->getContext($context, "majors")))) {
                // line 22
                echo "            Additionally, we
            ";
            } else {
                // line 24
                echo "            We
            ";
            }
            // line 26
            echo "            have detected some minor problems that we <em>recommend</em> you to fix to have a better Symfony
            experience:

            <ol>
                ";
            // line 30
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable((isset($context["minors"]) ? $context["minors"] : $this->getContext($context, "minors")));
            foreach ($context['_seq'] as $context["_key"] => $context["message"]) {
                // line 31
                echo "                    <li>";
                echo twig_escape_filter($this->env, (isset($context["message"]) ? $context["message"] : $this->getContext($context, "message")), "html", null, true);
                echo "</li>
                ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['_key'], $context['message'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 33
            echo "            </ol>
        </p>

    ";
        }
        // line 37
        echo "
    ";
        // line 38
        if ((!twig_length_filter($this->env, (isset($context["majors"]) ? $context["majors"] : $this->getContext($context, "majors"))))) {
            // line 39
            echo "        <ul class=\"symfony_list\">
            <li><a href=\"";
            // line 40
            echo twig_escape_filter($this->env, (isset($context["url"]) ? $context["url"] : $this->getContext($context, "url")), "html", null, true);
            echo "\">Configure your Symfony Application online</a></li>
        </ul>
    ";
        }
    }

    public function getTemplateName()
    {
        return "SensioDistributionBundle:Configurator:check.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  356 => 328,  339 => 316,  295 => 275,  77 => 28,  65 => 17,  118 => 49,  462 => 202,  449 => 198,  446 => 197,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  338 => 135,  320 => 127,  289 => 113,  286 => 112,  270 => 102,  262 => 98,  256 => 96,  233 => 87,  226 => 84,  207 => 75,  200 => 72,  113 => 40,  806 => 488,  803 => 487,  792 => 485,  788 => 484,  784 => 482,  771 => 481,  745 => 476,  742 => 475,  723 => 473,  706 => 472,  702 => 470,  698 => 469,  694 => 468,  690 => 467,  686 => 466,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 460,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  520 => 406,  515 => 404,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 318,  332 => 116,  327 => 114,  324 => 113,  306 => 107,  303 => 122,  300 => 121,  297 => 276,  263 => 95,  243 => 92,  231 => 83,  202 => 77,  190 => 76,  185 => 66,  174 => 65,  389 => 160,  386 => 159,  380 => 160,  371 => 156,  361 => 146,  358 => 151,  353 => 121,  345 => 147,  343 => 146,  340 => 145,  334 => 141,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  269 => 107,  265 => 96,  259 => 103,  255 => 93,  253 => 100,  248 => 94,  232 => 88,  227 => 86,  222 => 83,  213 => 78,  210 => 77,  197 => 71,  194 => 70,  191 => 67,  184 => 63,  178 => 66,  175 => 65,  172 => 64,  170 => 84,  155 => 47,  152 => 46,  134 => 54,  97 => 41,  63 => 21,  59 => 17,  53 => 11,  23 => 3,  100 => 36,  81 => 30,  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 196,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 176,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 157,  370 => 166,  367 => 155,  363 => 126,  352 => 155,  350 => 154,  347 => 153,  342 => 317,  333 => 147,  325 => 129,  318 => 111,  301 => 131,  299 => 130,  296 => 121,  291 => 102,  279 => 122,  275 => 105,  271 => 120,  267 => 101,  257 => 116,  244 => 136,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 79,  212 => 78,  205 => 90,  192 => 85,  181 => 65,  179 => 75,  150 => 55,  58 => 14,  161 => 63,  102 => 43,  90 => 27,  34 => 5,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 134,  329 => 131,  323 => 128,  321 => 112,  261 => 117,  127 => 35,  110 => 38,  104 => 37,  76 => 27,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 143,  341 => 118,  337 => 103,  322 => 101,  314 => 142,  312 => 124,  309 => 108,  305 => 95,  298 => 120,  294 => 90,  285 => 125,  283 => 100,  278 => 106,  268 => 85,  264 => 118,  258 => 94,  252 => 80,  247 => 78,  241 => 90,  235 => 85,  229 => 85,  224 => 81,  220 => 81,  214 => 69,  208 => 76,  169 => 60,  143 => 51,  140 => 58,  132 => 51,  128 => 51,  119 => 40,  107 => 37,  71 => 23,  177 => 65,  165 => 60,  160 => 61,  135 => 62,  126 => 45,  114 => 42,  84 => 27,  70 => 19,  67 => 18,  61 => 17,  28 => 3,  94 => 38,  89 => 35,  85 => 26,  75 => 22,  68 => 20,  56 => 14,  38 => 7,  87 => 32,  201 => 92,  196 => 92,  183 => 70,  171 => 71,  166 => 54,  163 => 82,  158 => 62,  156 => 58,  151 => 59,  142 => 59,  138 => 57,  136 => 71,  121 => 50,  117 => 39,  105 => 34,  91 => 29,  62 => 16,  49 => 12,  93 => 29,  88 => 30,  78 => 24,  44 => 8,  31 => 4,  27 => 4,  21 => 2,  24 => 3,  25 => 35,  46 => 10,  26 => 3,  19 => 1,  79 => 29,  72 => 21,  69 => 21,  47 => 10,  40 => 8,  37 => 7,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 49,  131 => 45,  123 => 42,  120 => 31,  115 => 40,  111 => 47,  108 => 19,  101 => 33,  98 => 34,  96 => 39,  83 => 31,  74 => 22,  66 => 25,  55 => 12,  52 => 13,  50 => 10,  43 => 9,  41 => 7,  35 => 5,  32 => 6,  29 => 4,  209 => 91,  203 => 73,  199 => 93,  193 => 73,  189 => 66,  187 => 75,  182 => 87,  176 => 63,  173 => 85,  168 => 61,  164 => 59,  162 => 59,  154 => 60,  149 => 51,  147 => 54,  144 => 42,  141 => 51,  133 => 55,  130 => 46,  125 => 51,  122 => 41,  116 => 39,  112 => 39,  109 => 47,  106 => 51,  103 => 28,  99 => 31,  95 => 34,  92 => 31,  86 => 28,  82 => 26,  80 => 24,  73 => 27,  64 => 19,  60 => 20,  57 => 20,  54 => 15,  51 => 37,  48 => 11,  45 => 11,  42 => 7,  39 => 10,  36 => 10,  33 => 9,  30 => 5,);
    }
}