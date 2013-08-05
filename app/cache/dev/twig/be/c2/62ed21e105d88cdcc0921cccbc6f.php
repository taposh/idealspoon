<?php

/* WebProfilerBundle:Collector:memory.html.twig */
class __TwigTemplate_bec262ed21e105d88cdcc0921cccbc6f extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("@WebProfiler/Profiler/layout.html.twig");

        $this->blocks = array(
            'toolbar' => array($this, 'block_toolbar'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "@WebProfiler/Profiler/layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_toolbar($context, array $blocks = array())
    {
        // line 4
        echo "    ";
        ob_start();
        // line 5
        echo "        <span>
            <img width=\"13\" height=\"28\" alt=\"Memory Usage\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA0AAAAcBAMAAABITyhxAAAAJ1BMVEXNzc3///////////////////////8/Pz////////////+NjY0/Pz9lMO+OAAAADHRSTlMAABAgMDhAWXCvv9e8JUuyAAAAQ0lEQVQI12MQBAMBBmLpMwoMDAw6BxjOOABpHyCdAKRzsNDp5eXl1KBh5oHBAYY9YHoDQ+cqIFjZwGCaBgSpBrjcCwCZgkUHKKvX+wAAAABJRU5ErkJggg==\">
            <span>";
        // line 7
        echo twig_escape_filter($this->env, sprintf("%.1f", (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "memory") / 1024) / 1024)), "html", null, true);
        echo " MB</span>
        </span>
    ";
        $context["icon"] = ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
        // line 10
        echo "    ";
        ob_start();
        // line 11
        echo "        <div class=\"sf-toolbar-info-piece\">
            <b>Memory usage</b>
            <span>";
        // line 13
        echo twig_escape_filter($this->env, sprintf("%.1f", (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "memory") / 1024) / 1024)), "html", null, true);
        echo " / ";
        echo ((($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "memoryLimit") == (-1))) ? ("&infin;") : (twig_escape_filter($this->env, sprintf("%.1f", (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "memoryLimit") / 1024) / 1024)))));
        echo " MB</span>
        </div>
    ";
        $context["text"] = ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
        // line 16
        echo "    ";
        $this->env->loadTemplate("@WebProfiler/Profiler/toolbar_item.html.twig")->display(array_merge($context, array("link" => false)));
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Collector:memory.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  462 => 202,  449 => 198,  446 => 197,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  338 => 135,  320 => 127,  289 => 113,  286 => 112,  270 => 102,  262 => 98,  256 => 96,  233 => 87,  226 => 84,  207 => 75,  200 => 72,  113 => 38,  806 => 488,  803 => 487,  792 => 485,  788 => 484,  784 => 482,  771 => 481,  745 => 476,  742 => 475,  723 => 473,  706 => 472,  702 => 470,  698 => 469,  694 => 468,  690 => 467,  686 => 466,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 460,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  520 => 406,  515 => 404,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 119,  332 => 116,  327 => 114,  324 => 113,  306 => 107,  303 => 122,  300 => 121,  297 => 104,  263 => 95,  243 => 92,  231 => 83,  202 => 77,  190 => 76,  185 => 66,  174 => 65,  389 => 160,  386 => 159,  380 => 160,  371 => 156,  361 => 146,  358 => 151,  353 => 121,  345 => 147,  343 => 146,  340 => 145,  334 => 141,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  269 => 107,  265 => 96,  259 => 103,  255 => 93,  253 => 100,  248 => 94,  232 => 88,  227 => 86,  222 => 83,  213 => 78,  210 => 77,  197 => 71,  194 => 70,  191 => 69,  184 => 63,  178 => 64,  175 => 58,  172 => 62,  170 => 84,  155 => 47,  152 => 46,  134 => 47,  97 => 41,  63 => 18,  59 => 16,  53 => 12,  23 => 3,  100 => 27,  81 => 23,  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 196,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 176,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 157,  370 => 166,  367 => 155,  363 => 126,  352 => 155,  350 => 154,  347 => 153,  342 => 137,  333 => 147,  325 => 129,  318 => 111,  301 => 131,  299 => 130,  296 => 121,  291 => 102,  279 => 122,  275 => 105,  271 => 120,  267 => 101,  257 => 116,  244 => 136,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 79,  212 => 78,  205 => 90,  192 => 85,  181 => 65,  179 => 75,  150 => 55,  58 => 25,  161 => 63,  102 => 33,  90 => 27,  34 => 5,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 134,  329 => 131,  323 => 128,  321 => 112,  261 => 117,  127 => 35,  110 => 22,  104 => 32,  76 => 34,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 143,  341 => 118,  337 => 103,  322 => 101,  314 => 142,  312 => 124,  309 => 108,  305 => 95,  298 => 120,  294 => 90,  285 => 125,  283 => 100,  278 => 106,  268 => 85,  264 => 118,  258 => 94,  252 => 80,  247 => 78,  241 => 90,  235 => 85,  229 => 85,  224 => 81,  220 => 81,  214 => 69,  208 => 76,  169 => 60,  143 => 51,  140 => 55,  132 => 51,  128 => 51,  119 => 40,  107 => 36,  71 => 27,  177 => 65,  165 => 60,  160 => 61,  135 => 62,  126 => 45,  114 => 42,  84 => 24,  70 => 19,  67 => 18,  61 => 17,  28 => 3,  94 => 21,  89 => 35,  85 => 24,  75 => 19,  68 => 30,  56 => 11,  38 => 7,  87 => 41,  201 => 92,  196 => 92,  183 => 70,  171 => 71,  166 => 54,  163 => 82,  158 => 80,  156 => 58,  151 => 57,  142 => 59,  138 => 57,  136 => 71,  121 => 46,  117 => 39,  105 => 34,  91 => 33,  62 => 27,  49 => 11,  93 => 29,  88 => 32,  78 => 26,  44 => 10,  31 => 4,  27 => 3,  21 => 2,  24 => 7,  25 => 35,  46 => 10,  26 => 9,  19 => 1,  79 => 21,  72 => 18,  69 => 17,  47 => 11,  40 => 6,  37 => 5,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 49,  131 => 45,  123 => 42,  120 => 31,  115 => 46,  111 => 37,  108 => 19,  101 => 31,  98 => 45,  96 => 30,  83 => 25,  74 => 27,  66 => 22,  55 => 24,  52 => 12,  50 => 22,  43 => 9,  41 => 19,  35 => 5,  32 => 6,  29 => 3,  209 => 91,  203 => 73,  199 => 93,  193 => 73,  189 => 66,  187 => 75,  182 => 87,  176 => 63,  173 => 85,  168 => 61,  164 => 59,  162 => 59,  154 => 66,  149 => 51,  147 => 54,  144 => 42,  141 => 51,  133 => 55,  130 => 46,  125 => 42,  122 => 41,  116 => 39,  112 => 36,  109 => 52,  106 => 51,  103 => 28,  99 => 31,  95 => 34,  92 => 43,  86 => 28,  82 => 28,  80 => 27,  73 => 20,  64 => 17,  60 => 16,  57 => 12,  54 => 16,  51 => 13,  48 => 9,  45 => 10,  42 => 7,  39 => 6,  36 => 5,  33 => 4,  30 => 3,);
    }
}
