<?php

/* WebProfilerBundle:Profiler:toolbar.html.twig */
class __TwigTemplate_27a752e075fb09a97a59e71372f9d6d2 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<!-- START of Symfony2 Web Debug Toolbar -->
";
        // line 2
        if (("normal" != (isset($context["position"]) ? $context["position"] : $this->getContext($context, "position")))) {
            // line 3
            echo "    <style>
        ";
            // line 4
            $this->env->loadTemplate("@WebProfiler/Profiler/toolbar.css.twig")->display(array_merge($context, array("position" => (isset($context["position"]) ? $context["position"] : $this->getContext($context, "position")), "floatable" => true)));
            // line 5
            echo "    </style>
    <div id=\"sfMiniToolbar-";
            // line 6
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "\" class=\"sf-minitoolbar\">
        <a href=\"javascript:void(0);\" title=\"Show Symfony toolbar\" onclick=\"
            var elem = this.parentNode;
            if (elem.style.display == 'none') {
                document.getElementById('sfToolbarMainContent-";
            // line 10
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "').style.display = 'none';
                document.getElementById('sfToolbarClearer-";
            // line 11
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "').style.display = 'none';
                elem.style.display = 'block';
            } else {
                document.getElementById('sfToolbarMainContent-";
            // line 14
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "').style.display = 'block';
                document.getElementById('sfToolbarClearer-";
            // line 15
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "').style.display = 'block';
                elem.style.display = 'none'
            }

            Sfjs.setPreference('toolbar/displayState', 'block');
        \">
            <img width=\"26\" height=\"28\" alt=\"Symfony\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAcCAMAAABIzV/hAAACZFBMVEUwLjL///////////////////////////////////////////////////////////////////+Eg4b///+Ni46Xlpj///////////+op6n///////////////////////////////////////////////////////////9ZWFv///////9qaWz///////+mpaf///////////////9ZWFv///////////////9PTVH///91dHb////////////////////g4OD///9NTE+Ih4r///////+Ni47///////92dHeRkJLk5OTLy8xlY2b///////+cm53///////+5ubr////o6Oj////////U1NT///9DQURsa22rq6ysq61hX2L///+LioxTUVVBP0NEQkZpZ2rGxsf///9ram3////s7O2SkZNfXmFxcHKmpae4uLnKysuXlpiop6l3dXiIh4pYVlmrq6ycm52trK7Nzc48Oj5dW158e36dnJ49Oz/Pz9BiYGPAv8BDQUTQz9BVU1aioaNHRUnBwcJXVVk6ODxJR0t3dnmko6U8Oj6lpKY9Oz+0tLXDwsRQTlF7en1QTlHi4eJhX2LFxcZTUVViYGNwb3J+fX83NTlFQ0dUUlW4t7icm524uLk8Oj5YVlmPjpBLSU2enZ9aWVw/PkFBP0NdW153dnk0MjZQTlE1MzdQT1JdXF9ram15eHqGhYdDQkV5eHo2NThEQkZRUFNFQ0dta244NjpGREhTUVU5NztUUlVhX2JubG9HRUlVU1ZiYGM7OTxIRkk7OT1IR0o8Oj4wLjI9Oz8YdG13AAAAynRSTlMAAAEDBAUGCgsMDQ4QEhMUGRobGx0gISIkJiYnKCktLi8wMjM0NTk6Ozw+P0NFSEpLTE5PUFBTWlteXmBiZGVmaWxtcHBxc3R0dnl5fX+BgoOGi46Pj5CRmZqanZ6eoKeoq6ytsLCwsrO0tbe5urq8vL+/wsTFx8jJycvLy8vM0NHR0tLU1NfX2NnZ2trc3N3d3eHh4uLl5ebm5ubn5+fo6urt7e3u7vDx8/Pz9PT19fX19fX29vf39/j4+fn5+vr6+vv7+/z8/f3+yR5EtwAAAbVJREFUeNpl0mVXVFEYhuF3zhkOFqMjYmCi2MUYYKGIYiJ2YCd2t4IBFqgoKjZ2jg3igI2KyO2f8sTMngGvj/te71r7wyMuk4jofZccAihcMzJKXDYnuYcVotyeYKiktV5LA0faaE7S4s7TyMsBupnMcoH/vO6gmanJaiLV1Py+Xwn5zc0fjCbSi2LI2QdkGdLyFBG+rHwMzz4BD7wyGEfFk8pfsD2TkGmyFNvFHfDj55v02VD6DcteOYylatY8oG7boA2QV4vlklzB8tU3/DIwo+dWv58guY5tRcLMtwSGdi1DkTvAn9Jqsgbu4kafRBqlV4sDFCWuIjdhMsp7yQU49rB28/QPLOu2DuWqLMfmfw6M716GskdGVRMSGDKRsEzpvZ+Qs0lFKDd94s2oArh2F7K3oNQt6ChGr5x6+Dx3J7d2E3Ygqam4PCkngRNnngYIO5cWq4lLb5t+vJ6GiifFR1nbMNqP3fSOCN8PZnQynEW5W/nmHy0PXv79eHpRSjsjtEOtWZcRc9YXlNx7VFKwceGYHjG6s0Ob3iK+X3LqlKnjkvt39rjD6w3W6BhvrCdaVw//ADrWicJIvtkmAAAAAElFTkSuQmCC\">
        </a>
    </div>
    <div id=\"sfToolbarClearer-";
            // line 24
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "\" style=\"clear: both; height: 38px;\"></div>
";
        }
        // line 26
        echo "
<div id=\"sfToolbarMainContent-";
        // line 27
        echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
        echo "\" class=\"sf-toolbarreset\">
    ";
        // line 28
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["templates"]) ? $context["templates"] : $this->getContext($context, "templates")));
        foreach ($context['_seq'] as $context["name"] => $context["template"]) {
            // line 29
            echo "        ";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["template"]) ? $context["template"] : $this->getContext($context, "template")), "renderblock", array(0 => "toolbar", 1 => array("collector" => $this->getAttribute((isset($context["profile"]) ? $context["profile"] : $this->getContext($context, "profile")), "getcollector", array(0 => (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name"))), "method"), "profiler_url" => (isset($context["profiler_url"]) ? $context["profiler_url"] : $this->getContext($context, "profiler_url")), "token" => $this->getAttribute((isset($context["profile"]) ? $context["profile"] : $this->getContext($context, "profile")), "token"), "name" => (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")))), "method"), "html", null, true);
            // line 35
            echo "
    ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['name'], $context['template'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 37
        echo "
    ";
        // line 38
        if (("normal" != (isset($context["position"]) ? $context["position"] : $this->getContext($context, "position")))) {
            // line 39
            echo "        <a class=\"hide-button\" title=\"Close Toolbar\" onclick=\"
            var p = this.parentNode;
            p.style.display = 'none';
            (p.previousElementSibling || p.previousSibling).style.display = 'none';
            document.getElementById('sfMiniToolbar-";
            // line 43
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "').style.display = 'block';
            Sfjs.setPreference('toolbar/displayState', 'none');
        \"></a>
    ";
        }
        // line 47
        echo "</div>
<!-- END of Symfony2 Web Debug Toolbar -->
";
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Profiler:toolbar.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  77 => 28,  65 => 24,  118 => 49,  462 => 202,  446 => 197,  441 => 196,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  408 => 176,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  342 => 137,  325 => 129,  320 => 127,  289 => 113,  286 => 112,  275 => 105,  270 => 102,  267 => 101,  262 => 98,  256 => 96,  233 => 87,  226 => 84,  200 => 72,  181 => 65,  150 => 55,  113 => 40,  806 => 488,  803 => 487,  792 => 485,  788 => 484,  784 => 482,  771 => 481,  745 => 476,  742 => 475,  723 => 473,  706 => 472,  702 => 470,  698 => 469,  694 => 468,  690 => 467,  686 => 466,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 460,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  515 => 404,  244 => 136,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 119,  332 => 116,  327 => 114,  324 => 113,  318 => 111,  306 => 107,  303 => 122,  300 => 121,  297 => 104,  263 => 95,  212 => 78,  202 => 77,  190 => 76,  185 => 66,  174 => 65,  389 => 160,  386 => 159,  380 => 160,  378 => 157,  371 => 156,  363 => 126,  361 => 146,  358 => 151,  343 => 146,  340 => 145,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  296 => 121,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  265 => 96,  255 => 93,  253 => 100,  248 => 94,  232 => 88,  222 => 83,  216 => 79,  213 => 78,  210 => 77,  197 => 71,  184 => 63,  178 => 66,  175 => 65,  172 => 64,  170 => 84,  155 => 47,  152 => 46,  134 => 54,  97 => 41,  53 => 15,  23 => 3,  100 => 36,  81 => 29,  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 406,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 155,  353 => 121,  349 => 153,  345 => 147,  338 => 135,  334 => 141,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 102,  287 => 126,  280 => 124,  277 => 123,  259 => 103,  257 => 109,  249 => 105,  231 => 83,  211 => 91,  207 => 75,  194 => 70,  191 => 67,  186 => 76,  165 => 60,  70 => 26,  58 => 25,  161 => 63,  90 => 27,  84 => 35,  34 => 5,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 198,  447 => 360,  335 => 134,  329 => 131,  323 => 128,  321 => 112,  261 => 188,  127 => 35,  110 => 22,  104 => 37,  76 => 31,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 143,  341 => 118,  337 => 103,  322 => 101,  314 => 99,  312 => 124,  309 => 108,  305 => 132,  298 => 120,  294 => 90,  285 => 89,  283 => 100,  278 => 106,  268 => 85,  264 => 84,  258 => 94,  252 => 80,  247 => 78,  241 => 90,  229 => 85,  220 => 81,  214 => 92,  177 => 73,  169 => 71,  140 => 58,  132 => 51,  128 => 51,  107 => 36,  61 => 17,  273 => 122,  269 => 107,  254 => 108,  243 => 92,  240 => 101,  238 => 85,  235 => 85,  230 => 82,  227 => 86,  224 => 81,  221 => 77,  219 => 76,  217 => 75,  208 => 76,  204 => 72,  179 => 69,  159 => 61,  143 => 51,  135 => 62,  119 => 40,  102 => 43,  71 => 13,  67 => 24,  63 => 18,  59 => 16,  28 => 6,  94 => 38,  89 => 35,  85 => 23,  75 => 28,  68 => 12,  56 => 16,  38 => 6,  87 => 33,  201 => 92,  196 => 92,  183 => 82,  171 => 61,  166 => 54,  163 => 82,  158 => 62,  156 => 58,  151 => 59,  142 => 59,  138 => 54,  136 => 71,  121 => 50,  117 => 39,  105 => 34,  91 => 37,  62 => 24,  49 => 14,  93 => 29,  88 => 32,  78 => 18,  44 => 11,  31 => 8,  27 => 4,  21 => 2,  24 => 3,  25 => 35,  46 => 13,  26 => 3,  19 => 1,  79 => 29,  72 => 18,  69 => 17,  47 => 15,  40 => 8,  37 => 6,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 49,  131 => 45,  123 => 42,  120 => 31,  115 => 46,  111 => 47,  108 => 19,  101 => 31,  98 => 45,  96 => 39,  83 => 30,  74 => 27,  66 => 25,  55 => 38,  52 => 12,  50 => 15,  43 => 11,  41 => 19,  35 => 9,  32 => 6,  29 => 5,  209 => 82,  203 => 73,  199 => 93,  193 => 73,  189 => 66,  187 => 75,  182 => 87,  176 => 63,  173 => 85,  168 => 61,  164 => 59,  162 => 59,  154 => 60,  149 => 51,  147 => 54,  144 => 42,  141 => 51,  133 => 53,  130 => 46,  125 => 51,  122 => 41,  116 => 39,  112 => 36,  109 => 47,  106 => 51,  103 => 28,  99 => 31,  95 => 28,  92 => 28,  86 => 28,  82 => 28,  80 => 32,  73 => 27,  64 => 23,  60 => 20,  57 => 39,  54 => 16,  51 => 37,  48 => 16,  45 => 14,  42 => 11,  39 => 10,  36 => 10,  33 => 9,  30 => 5,);
    }
}
