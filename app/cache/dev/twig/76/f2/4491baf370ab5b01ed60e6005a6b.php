<?php

/* WebProfilerBundle:Profiler:toolbar.css.twig */
class __TwigTemplate_76f24491baf370ab5b01ed60e6005a6b extends Twig_Template
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
        echo ".sf-minitoolbar {
    display: none;

    position: fixed;
    bottom: 0;
    right: 0;

    padding: 5px 5px 0;

    background-color: #f7f7f7;
    background-image: -moz-linear-gradient(top, #e4e4e4, #ffffff);
    background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#e4e4e4), to(#ffffff));
    background-image: -o-linear-gradient(top, #e4e4e4, #ffffff);
    background: linear-gradient(top, #e4e4e4, #ffffff);

    border-radius: 16px 0 0 0;

    z-index: 6000000;
}

.sf-toolbarreset {
    position: fixed;
    background-color: #f7f7f7;
    left: 0;
    right: 0;
    height: 38px;
    margin: 0;
    padding: 0 40px 0 0;
    z-index: 6000000;
    font: 11px Verdana, Arial, sans-serif;
    text-align: left;
    color: #2f2f2f;

    background-image: -moz-linear-gradient(top, #e4e4e4, #ffffff);
    background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#e4e4e4), to(#ffffff));
    background-image: -o-linear-gradient(top, #e4e4e4, #ffffff);
    background: linear-gradient(top, #e4e4e4, #ffffff);

    bottom: 0;
    border-top: 1px solid #bbb;
}
.sf-toolbarreset abbr {
    border-bottom: 1px dotted #000000;
    cursor: help;
}
.sf-toolbarreset span,
.sf-toolbarreset div {
    font-size: 11px;
}
.sf-toolbarreset img {
    width: auto;
    display: inline;
}

.sf-toolbarreset .hide-button {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    width: 40px;
    height: 40px;
    cursor: pointer;
    background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAMAAAAMCGV4AAAAllBMVEXDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PDw8PExMTPz8/Q0NDR0dHT09Pb29vc3Nzf39/h4eHi4uLj4+P6+vr7+/v8/Pz9/f3///+Nh2QuAAAAIXRSTlMABgwPGBswMzk8QktRV4SKjZOWmaKlq7TAxszb3urt+fy1vNEpAAAAiklEQVQIHUXBBxKCQBREwRFzDqjoGh+C2YV//8u5Sll2S0E/dof1tKdKM6GyqCto7PjZRJIS/mbSELgXOSd/BzpKIH1ZefVWpDDTHsi8mZVnwImPi5ndCLbaAZk3M58Bay0h9VbeSvMpjDUAHj4jL55AW1rxN5fU2PLjIgVRzNdxVFOlGzvnJi0Fb1XNGBHA9uQOAAAAAElFTkSuQmCC');
    background-repeat: no-repeat;
    background-position: 13px 11px;
}

.sf-toolbar-block {
    white-space: nowrap;
    color: #2f2f2f;
    display: block;
    min-height: 28px;
    border-right: 1px solid #e4e4e4;
    padding: 0;
    float: left;
    cursor: default;
}

.sf-toolbar-block span {
    display: inline-block;
}

.sf-toolbar-block .sf-toolbar-info-piece {
    line-height: 19px;
    margin-bottom: 5px;
}

.sf-toolbar-block .sf-toolbar-info-piece .sf-toolbar-status {
    padding: 0px 5px;
    border-radius: 5px;
    margin-bottom: 0px;
    vertical-align: top;
}

.sf-toolbar-block .sf-toolbar-info-piece:last-child {
    margin-bottom: 0;
}

.sf-toolbar-block .sf-toolbar-info-piece a,
.sf-toolbar-block .sf-toolbar-info-piece abbr {
    color: #2f2f2f;
}

.sf-toolbar-block .sf-toolbar-info-piece b {
    display: inline-block;
    width: 110px;
    vertical-align: top;
}

.sf-toolbar-block .sf-toolbar-info-with-next-pointer:after {
    content: ' :: ';
    color: #999;
}

.sf-toolbar-block .sf-toolbar-info-with-delimiter {
    border-right: 1px solid #999;
    padding-right: 5px;
    margin-right: 5px;
}

.sf-toolbar-block .sf-toolbar-info {
    display: none;
    position: absolute;
    background-color: #fff;
    border: 1px solid #bbb;
    padding: 9px 0;
    margin-left: -1px;

    bottom: 38px;
    border-bottom-width: 0;
    border-bottom: 1px solid #bbb;
    border-radius: 4px 4px 0 0;
}

.sf-toolbarreset > div:last-of-type .sf-toolbar-info {
    right: -1px;
}

.sf-toolbar-block .sf-toolbar-info:empty {
    visibility: hidden;
}

.sf-toolbar-block .sf-toolbar-status {
    display: inline-block;
    color: #fff;
    background-color: #666;
    padding: 3px 6px;
    border-radius: 3px;
    margin-bottom: 2px;
    vertical-align: middle;
    min-width: 6px;
    min-height: 13px;
}

.sf-toolbar-block .sf-toolbar-status abbr {
    color: #fff;
    border-bottom: 1px dotted black;
}

.sf-toolbar-block .sf-toolbar-status-green {
    background-color: #759e1a;
}

.sf-toolbar-block .sf-toolbar-status-red {
    background-color: #a33;
}

.sf-toolbar-block .sf-toolbar-status-yellow {
    background-color: #ffcc00;
    color: #000;
}

.sf-toolbar-block .sf-toolbar-status-black {
    background-color: #000;
}

.sf-toolbar-block .sf-toolbar-icon {
    display: block;
}

.sf-toolbar-block .sf-toolbar-icon > a,
.sf-toolbar-block .sf-toolbar-icon > span {
    display: block;
    text-decoration: none;
    margin: 0;
    padding: 5px 8px;
    min-width: 20px;
    text-align: center;
    vertical-align: middle;
}

.sf-toolbar-block .sf-toolbar-icon > a,
.sf-toolbar-block .sf-toolbar-icon > a:link
.sf-toolbar-block .sf-toolbar-icon > a:hover {
    color: black !important;
}

.sf-toolbar-block .sf-toolbar-icon img {
    border-width: 0;
    vertical-align: middle;
}

.sf-toolbar-block .sf-toolbar-icon img + span {
    margin-left: 5px;
    margin-top: 2px;
}

.sf-toolbar-block .sf-toolbar-icon .sf-toolbar-status {
    border-radius: 12px;
    border-bottom-left-radius: 0;
    margin-top: 0;
}

.sf-toolbar-block .sf-toolbar-info-method {
    border-bottom: 1px dashed #ccc;
    cursor: help;
}

.sf-toolbar-block .sf-toolbar-info-method[onclick=\"\"] {
    border-bottom: none;
    cursor: inherit;
}

.sf-toolbar-info-php {}
.sf-toolbar-info-php-ext {}

.sf-toolbar-info-php-ext .sf-toolbar-status {
    margin-left: 2px;
}

.sf-toolbar-info-php-ext .sf-toolbar-status:first-child {
    margin-left: 0;
}

.sf-toolbar-block a .sf-toolbar-info-piece-additional,
.sf-toolbar-block a .sf-toolbar-info-piece-additional-detail {
    display: inline-block;
}

.sf-toolbar-block a .sf-toolbar-info-piece-additional:empty,
.sf-toolbar-block a .sf-toolbar-info-piece-additional-detail:empty {
    display: none;
}

.sf-toolbarreset:hover {
    box-shadow: rgba(0, 0, 0, 0.3) 0 0 50px;
}

.sf-toolbar-block:hover {
    box-shadow: rgba(0, 0, 0, 0.35) 0 0 5px;
    border-right: none;
    margin-right: 1px;
    position: relative;
}

.sf-toolbar-block:hover .sf-toolbar-icon {
    background-color: #fff;
    border-top: 1px dotted #DDD;
    position: relative;
    margin-top: -1px;
    z-index: 10002;
}

.sf-toolbar-block:hover .sf-toolbar-info {
    display: block;
    min-width: -webkit-calc(100% + 2px);
    min-width: calc(100% + 2px);
    z-index: 10001;
    box-sizing: border-box;
    padding: 9px;
    line-height: 19px;
}

/***** Override the setting when the toolbar is on the top *****/
";
        // line 275
        if (((isset($context["position"]) ? $context["position"] : $this->getContext($context, "position")) == "top")) {
            // line 276
            echo "    .sf-minitoolbar {
        top: 0;
        bottom: auto;
        right: 0;

        background-color: #f7f7f7;

        background-image: -moz-linear-gradient(225deg, #e4e4e4, #ffffff);
        background-image: -webkit-gradient(linear, 100% 0%, 0% 0%, from(#e4e4e4), to(#ffffff));
        background-image: -o-linear-gradient(135deg, #e4e4e4, #ffffff);
        background: linear-gradient(225deg, #e4e4e4, #ffffff);

        border-radius: 0 0 0 16px;
    }

    .sf-toolbarreset {
        background-image: -moz-linear-gradient(225deg, #e4e4e4, #ffffff);
        background-image: -webkit-gradient(linear, 100% 0%, 0% 0%, from(#e4e4e4), to(#ffffff));
        background-image: -o-linear-gradient(135deg, #e4e4e4, #ffffff);
        background: linear-gradient(225deg, #e4e4e4, #ffffff);

        top: 0;
        bottom: auto;
        border-bottom: 1px solid #bbb;
    }

    .sf-toolbar-block .sf-toolbar-info {
        top: 39px;
        bottom: auto;
        border-top-width: 0;
        border-radius: 0 0 4px 4px;
    }

    .sf-toolbar-block:hover .sf-toolbar-icon {
        border-top: none;
        border-bottom: 1px dotted #DDD;
        margin-top: 0;
        margin-bottom: -1px;
    }
";
        }
        // line 316
        echo "
";
        // line 317
        if ((!(isset($context["floatable"]) ? $context["floatable"] : $this->getContext($context, "floatable")))) {
            // line 318
            echo "    .sf-toolbarreset {
        position: static;
        background: #cbcbcb;

        background-image: -moz-linear-gradient(90deg, #cbcbcb, #e8e8e8); !important;
        background-image: -webkit-gradient(linear, 0% 0%, 100% 0%, from(#cbcbcb), to(#e8e8e8)); !important;
        background-image: -o-linear-gradient(180deg, #cbcbcb, #e8e8e8); !important;
        background: linear-gradient(90deg, #cbcbcb, #e8e8e8); !important;
    }
";
        }
        // line 328
        echo "
/***** Media query *****/
@media screen and (max-width: 779px) {
    .sf-toolbar-block .sf-toolbar-icon > * > :first-child ~ * {
        display: none;
    }

    .sf-toolbar-block .sf-toolbar-icon > * > .sf-toolbar-info-piece-additional,
    .sf-toolbar-block .sf-toolbar-icon > * > .sf-toolbar-info-piece-additional-detail {
        display: none !important;
    }
}

@media screen and (min-width: 880px) {
    .sf-toolbar-block .sf-toolbar-icon a[href\$=\"config\"] .sf-toolbar-info-piece-additional {
        display: inline-block;
    }
}

@media screen and (min-width: 980px) {
    .sf-toolbar-block .sf-toolbar-icon a[href\$=\"security\"] .sf-toolbar-info-piece-additional {
        display: inline-block;
    }
}

@media screen and (max-width: 1179px) {
    .sf-toolbar-block .sf-toolbar-icon > * > .sf-toolbar-info-piece-additional {
        display: none;
    }
}

@media screen and (max-width: 1439px) {
    .sf-toolbar-block .sf-toolbar-icon > * > .sf-toolbar-info-piece-additional-detail {
        display: none;
    }
}
";
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Profiler:toolbar.css.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  356 => 328,  339 => 316,  295 => 275,  77 => 28,  65 => 24,  118 => 49,  462 => 202,  449 => 198,  446 => 197,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  338 => 135,  320 => 127,  289 => 113,  286 => 112,  270 => 102,  262 => 98,  256 => 96,  233 => 87,  226 => 84,  207 => 75,  200 => 72,  113 => 40,  806 => 488,  803 => 487,  792 => 485,  788 => 484,  784 => 482,  771 => 481,  745 => 476,  742 => 475,  723 => 473,  706 => 472,  702 => 470,  698 => 469,  694 => 468,  690 => 467,  686 => 466,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 460,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  520 => 406,  515 => 404,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 318,  332 => 116,  327 => 114,  324 => 113,  306 => 107,  303 => 122,  300 => 121,  297 => 276,  263 => 95,  243 => 92,  231 => 83,  202 => 77,  190 => 76,  185 => 66,  174 => 65,  389 => 160,  386 => 159,  380 => 160,  371 => 156,  361 => 146,  358 => 151,  353 => 121,  345 => 147,  343 => 146,  340 => 145,  334 => 141,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  269 => 107,  265 => 96,  259 => 103,  255 => 93,  253 => 100,  248 => 94,  232 => 88,  227 => 86,  222 => 83,  213 => 78,  210 => 77,  197 => 71,  194 => 70,  191 => 67,  184 => 63,  178 => 66,  175 => 65,  172 => 64,  170 => 84,  155 => 47,  152 => 46,  134 => 54,  97 => 41,  63 => 18,  59 => 16,  53 => 15,  23 => 3,  100 => 36,  81 => 29,  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 196,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 176,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 157,  370 => 166,  367 => 155,  363 => 126,  352 => 155,  350 => 154,  347 => 153,  342 => 317,  333 => 147,  325 => 129,  318 => 111,  301 => 131,  299 => 130,  296 => 121,  291 => 102,  279 => 122,  275 => 105,  271 => 120,  267 => 101,  257 => 116,  244 => 136,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 79,  212 => 78,  205 => 90,  192 => 85,  181 => 65,  179 => 75,  150 => 55,  58 => 25,  161 => 63,  102 => 43,  90 => 27,  34 => 5,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 134,  329 => 131,  323 => 128,  321 => 112,  261 => 117,  127 => 35,  110 => 22,  104 => 37,  76 => 31,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 143,  341 => 118,  337 => 103,  322 => 101,  314 => 142,  312 => 124,  309 => 108,  305 => 95,  298 => 120,  294 => 90,  285 => 125,  283 => 100,  278 => 106,  268 => 85,  264 => 118,  258 => 94,  252 => 80,  247 => 78,  241 => 90,  235 => 85,  229 => 85,  224 => 81,  220 => 81,  214 => 69,  208 => 76,  169 => 60,  143 => 51,  140 => 58,  132 => 51,  128 => 51,  119 => 40,  107 => 36,  71 => 13,  177 => 65,  165 => 60,  160 => 61,  135 => 62,  126 => 45,  114 => 42,  84 => 35,  70 => 26,  67 => 24,  61 => 17,  28 => 6,  94 => 38,  89 => 35,  85 => 23,  75 => 28,  68 => 12,  56 => 16,  38 => 6,  87 => 33,  201 => 92,  196 => 92,  183 => 70,  171 => 71,  166 => 54,  163 => 82,  158 => 62,  156 => 58,  151 => 59,  142 => 59,  138 => 57,  136 => 71,  121 => 50,  117 => 39,  105 => 34,  91 => 37,  62 => 24,  49 => 14,  93 => 29,  88 => 32,  78 => 18,  44 => 11,  31 => 8,  27 => 4,  21 => 2,  24 => 3,  25 => 35,  46 => 13,  26 => 3,  19 => 1,  79 => 29,  72 => 18,  69 => 17,  47 => 15,  40 => 8,  37 => 6,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 49,  131 => 45,  123 => 42,  120 => 31,  115 => 46,  111 => 47,  108 => 19,  101 => 31,  98 => 45,  96 => 39,  83 => 30,  74 => 27,  66 => 25,  55 => 38,  52 => 12,  50 => 15,  43 => 11,  41 => 19,  35 => 9,  32 => 6,  29 => 5,  209 => 91,  203 => 73,  199 => 93,  193 => 73,  189 => 66,  187 => 75,  182 => 87,  176 => 63,  173 => 85,  168 => 61,  164 => 59,  162 => 59,  154 => 60,  149 => 51,  147 => 54,  144 => 42,  141 => 51,  133 => 55,  130 => 46,  125 => 51,  122 => 41,  116 => 39,  112 => 36,  109 => 47,  106 => 51,  103 => 28,  99 => 31,  95 => 34,  92 => 28,  86 => 28,  82 => 28,  80 => 32,  73 => 27,  64 => 23,  60 => 20,  57 => 39,  54 => 16,  51 => 37,  48 => 16,  45 => 14,  42 => 11,  39 => 10,  36 => 10,  33 => 9,  30 => 5,);
    }
}
