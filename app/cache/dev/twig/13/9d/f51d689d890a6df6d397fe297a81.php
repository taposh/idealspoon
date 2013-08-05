<?php

/* WebProfilerBundle:Collector:exception.html.twig */
class __TwigTemplate_139df51d689d890a6df6d397fe297a81 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("@WebProfiler/Profiler/layout.html.twig");

        $this->blocks = array(
            'head' => array($this, 'block_head'),
            'menu' => array($this, 'block_menu'),
            'panel' => array($this, 'block_panel'),
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
    public function block_head($context, array $blocks = array())
    {
        // line 4
        echo "    ";
        if ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "hasexception")) {
            // line 5
            echo "        <style>
            ";
            // line 6
            echo $this->env->getExtension('http_kernel')->renderFragment($this->env->getExtension('routing')->getPath("_profiler_exception_css", array("token" => (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")))));
            echo "
        </style>
    ";
        }
        // line 9
        echo "    ";
        $this->displayParentBlock("head", $context, $blocks);
        echo "
";
    }

    // line 12
    public function block_menu($context, array $blocks = array())
    {
        // line 13
        echo "<span class=\"label\">
    <span class=\"icon\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACoAAAAeCAQAAADwIURrAAAEQ0lEQVR42sWVf0xbVRTHKSCUUNEsWdhE3BT3QzNMjHEydLz+eONnGS2sbBSkKBA7Nn6DGwb+EByLbMbFKEuUiG1kTrFgwmCODZaZqaGJEQYSMXQJMJFBs/pGlV9tv97bAukrZMD+8Z2k957vOfdzz7s579brwU+jSP2mojnmNgOXxQ4pLqa90SjyetjHEKQ6I7MwWGkyi+qMIWjDQPgUiuNMfBTf4kxlkfDZELJSynIMHmwsVyldNxaCC7soUjV/fcTawxmvjCscS6AUR9cdzsgZu0YVCwyiLV/uhGB9UFFmG4O0OXM7inEQCpTf6ZY7nEjbeCdKkUSs9O73iTYGmW0QrQfprWclBNHSAxWegD+ECEXmp0MU2nQLajxJFCH5VTfdYiBx6Fl4r6POYj0FcCcQAoFrG4T1fkS14VpscyEgwLaRU1Qr1rtqhY9mb7L6stCtuooIyd8JnSUvEkCoepiclg1y+C3HHx3NpoBZFQKWtQAoqaYeRijxfAvSxXYGWbXwX074oIwmFJ5FIMLlVjrvT4K/JlfKSTlNLkTf5VOtKwtCrUJ2VzKdXoaAH9VUk1sRTgiPlzdSr/IrbCbAvMi4SyWpprfoOd4sxyZEsDYarqrHM6wTz1qxu6CNzkq/xtMJY3QmWTDuLbtAZ1I7XkuR6V5pbCAqjNUIJlB1BwOx/T1DDpf678DxI5XDysUP8r4xO3bOOZu7USRbcOLnftCm3HOhrlWwJEoNh6TWmMn6VrLplDE/5/XsHV7aXxchNlorgys1Sz0Zb62YoGP5ZMzskhY9WzlKRy0XN7OkIdfwWeI/DJYs6abXUO3pybyZOnOCPd72xcAlPU4y2JjhMNKgky8ccMicZR360wv78Q4+4Vroidz+HEpkbhjKYmt3FUMG43iVtW5q7EPSLtiO8MES5wtbtKfa8/lLNHZPSIY9nue3Hs+oieHozHoeNTgJiaulfMFmTK9WRdW0+arEwde6rp+dWi035x4UCMNTEC02P14wn3/3PrsisWgUKrXOXVF2QH5sxDPvgOO0xXIOz/OuFzwGCWptHX2/XZtwT5ZbJ15i/Jj6ZaW+UNgRw9rcc7r/6htAG6oRhSCP6w4i7IAYh1HHryGz07AZAmYXk0VsCwSdW5N/52fgfaQSYBgCV70G4UvQiZ6vFjuWXq1JyguBT+GzGeWx455xJCJwjcsa4g23lJiu+/+h0R6I+IeCRiXM87rPzm+0fFssz0+YR9Ta0H3ZZl77W4/yNrk4XjDj7mebsW9taHjDDfdFP/W0DLp9ojOc7vLP7vGmq9izNnQLtB+PLZ6fo3kAxTihM7mO4Ijtj2YooW0edJ20BDoTchC8NtQPe/D2XHtvv+kXfIOjeI74RWgZ7OvtXWhAEoKxE8fwLfH70Uoiu/HIev6khdgOMZJxEBEIgR/8EYpXoYQCL2MTvOFH1EjiJ2M/ifivJPwHIs9MRJmsgVwAAAAASUVORK5CYII=\" alt=\"Exception\"></span>
    <strong>Exception</strong>
    <span class=\"count\">
        ";
        // line 17
        if ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "hasexception")) {
            // line 18
            echo "            <span>1</span>
        ";
        }
        // line 20
        echo "    </span>
</span>
";
    }

    // line 24
    public function block_panel($context, array $blocks = array())
    {
        // line 25
        echo "    <h2>Exception</h2>

    ";
        // line 27
        if ((!$this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "hasexception"))) {
            // line 28
            echo "        <p>
            <em>No exception was thrown and uncaught during the request.</em>
        </p>
    ";
        } else {
            // line 32
            echo "        <div class=\"sf-reset\">
            ";
            // line 33
            echo $this->env->getExtension('http_kernel')->renderFragment($this->env->getExtension('routing')->getPath("_profiler_exception", array("token" => (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")))));
            echo "
        </div>
    ";
        }
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Collector:exception.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  357 => 123,  351 => 120,  344 => 119,  332 => 116,  327 => 114,  324 => 113,  306 => 107,  303 => 106,  300 => 105,  297 => 104,  263 => 95,  243 => 92,  231 => 83,  202 => 77,  190 => 76,  185 => 74,  174 => 65,  389 => 160,  386 => 159,  380 => 158,  371 => 156,  361 => 152,  358 => 151,  353 => 121,  345 => 147,  343 => 146,  340 => 145,  334 => 141,  331 => 140,  328 => 139,  326 => 138,  315 => 110,  307 => 128,  302 => 125,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  269 => 107,  265 => 96,  259 => 103,  255 => 93,  253 => 100,  248 => 97,  232 => 88,  227 => 86,  222 => 83,  213 => 78,  210 => 77,  197 => 69,  194 => 68,  191 => 67,  184 => 63,  178 => 59,  175 => 58,  172 => 57,  170 => 56,  155 => 47,  152 => 46,  134 => 39,  97 => 41,  63 => 18,  59 => 13,  53 => 11,  23 => 3,  100 => 27,  81 => 24,  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 194,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 180,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 157,  370 => 166,  367 => 155,  363 => 126,  352 => 155,  350 => 154,  347 => 153,  342 => 150,  333 => 147,  325 => 145,  318 => 111,  301 => 131,  299 => 130,  296 => 121,  291 => 102,  279 => 122,  275 => 121,  271 => 120,  267 => 119,  257 => 116,  244 => 105,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 79,  212 => 78,  205 => 90,  192 => 85,  181 => 76,  179 => 75,  150 => 65,  58 => 14,  161 => 63,  102 => 24,  90 => 20,  34 => 6,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 250,  329 => 146,  323 => 246,  321 => 112,  261 => 117,  127 => 35,  110 => 22,  104 => 32,  76 => 25,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 198,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 150,  341 => 118,  337 => 103,  322 => 101,  314 => 142,  312 => 109,  309 => 108,  305 => 95,  298 => 91,  294 => 90,  285 => 125,  283 => 100,  278 => 98,  268 => 85,  264 => 118,  258 => 94,  252 => 80,  247 => 78,  241 => 93,  235 => 85,  229 => 87,  224 => 81,  220 => 70,  214 => 69,  208 => 76,  169 => 60,  143 => 51,  140 => 55,  132 => 51,  128 => 51,  119 => 40,  107 => 36,  71 => 27,  177 => 65,  165 => 64,  160 => 61,  135 => 62,  126 => 45,  114 => 42,  84 => 29,  70 => 15,  67 => 20,  61 => 17,  28 => 3,  94 => 21,  89 => 35,  85 => 24,  75 => 19,  68 => 23,  56 => 11,  38 => 7,  87 => 25,  201 => 92,  196 => 86,  183 => 70,  171 => 71,  166 => 54,  163 => 53,  158 => 79,  156 => 62,  151 => 57,  142 => 59,  138 => 57,  136 => 48,  121 => 46,  117 => 39,  105 => 25,  91 => 33,  62 => 19,  49 => 11,  93 => 29,  88 => 32,  78 => 26,  44 => 9,  31 => 3,  27 => 3,  21 => 2,  24 => 7,  25 => 35,  46 => 10,  26 => 9,  19 => 1,  79 => 21,  72 => 18,  69 => 17,  47 => 8,  40 => 6,  37 => 5,  22 => 2,  246 => 96,  157 => 30,  145 => 52,  139 => 49,  131 => 45,  123 => 59,  120 => 31,  115 => 46,  111 => 37,  108 => 19,  101 => 31,  98 => 30,  96 => 17,  83 => 25,  74 => 27,  66 => 22,  55 => 13,  52 => 12,  50 => 10,  43 => 11,  41 => 8,  35 => 6,  32 => 5,  29 => 3,  209 => 91,  203 => 73,  199 => 87,  193 => 73,  189 => 66,  187 => 75,  182 => 66,  176 => 74,  173 => 74,  168 => 66,  164 => 59,  162 => 68,  154 => 66,  149 => 51,  147 => 43,  144 => 42,  141 => 51,  133 => 55,  130 => 52,  125 => 42,  122 => 41,  116 => 36,  112 => 36,  109 => 35,  106 => 45,  103 => 28,  99 => 23,  95 => 34,  92 => 27,  86 => 28,  82 => 28,  80 => 27,  73 => 24,  64 => 13,  60 => 16,  57 => 12,  54 => 16,  51 => 13,  48 => 9,  45 => 9,  42 => 7,  39 => 6,  36 => 5,  33 => 4,  30 => 3,);
    }
}
