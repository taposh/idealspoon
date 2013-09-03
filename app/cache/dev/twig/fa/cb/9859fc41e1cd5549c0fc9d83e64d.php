<?php

/* ::base.html.twig */
class __TwigTemplate_facb9859fc41e1cd5549c0fc9d83e64d extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'title' => array($this, 'block_title'),
            'stylesheets' => array($this, 'block_stylesheets'),
            'body' => array($this, 'block_body'),
            'javascripts' => array($this, 'block_javascripts'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<!DOCTYPE html>
<html>
    <head>
        <meta charset=\"UTF-8\" />
        <title>";
        // line 5
        $this->displayBlock('title', $context, $blocks);
        echo "</title>
        ";
        // line 6
        $this->displayBlock('stylesheets', $context, $blocks);
        // line 7
        echo "        <link rel=\"icon\" type=\"image/x-icon\" href=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("favicon.ico"), "html", null, true);
        echo "\" />
    </head>
    <body>
        ";
        // line 10
        $this->displayBlock('body', $context, $blocks);
        // line 11
        echo "        ";
        $this->displayBlock('javascripts', $context, $blocks);
        // line 12
        echo "    </body>
</html>
";
    }

    // line 5
    public function block_title($context, array $blocks = array())
    {
        echo "Welcome!";
    }

    // line 6
    public function block_stylesheets($context, array $blocks = array())
    {
    }

    // line 10
    public function block_body($context, array $blocks = array())
    {
    }

    // line 11
    public function block_javascripts($context, array $blocks = array())
    {
    }

    public function getTemplateName()
    {
        return "::base.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  1352 => 388,  1343 => 387,  1341 => 386,  1338 => 385,  1322 => 381,  1315 => 380,  1313 => 379,  1310 => 378,  1287 => 374,  1262 => 373,  1260 => 372,  1257 => 371,  1245 => 366,  1240 => 365,  1238 => 364,  1235 => 363,  1226 => 357,  1220 => 355,  1217 => 354,  1212 => 353,  1210 => 352,  1207 => 351,  1200 => 346,  1191 => 344,  1187 => 343,  1184 => 342,  1181 => 341,  1179 => 340,  1176 => 339,  1168 => 335,  1166 => 334,  1163 => 333,  1157 => 329,  1151 => 327,  1148 => 326,  1146 => 325,  1143 => 324,  1134 => 319,  1132 => 318,  1109 => 317,  1106 => 316,  1103 => 315,  1100 => 314,  1097 => 313,  1094 => 312,  1091 => 311,  1089 => 310,  1086 => 309,  1079 => 305,  1075 => 304,  1070 => 303,  1068 => 302,  1065 => 301,  1058 => 296,  1055 => 295,  1047 => 290,  1044 => 289,  1042 => 288,  1039 => 287,  1031 => 282,  1027 => 281,  1023 => 280,  1020 => 279,  1018 => 278,  1015 => 277,  1007 => 273,  1005 => 269,  1003 => 268,  1000 => 267,  995 => 263,  973 => 258,  970 => 257,  967 => 256,  964 => 255,  961 => 254,  958 => 253,  955 => 252,  952 => 251,  949 => 250,  946 => 249,  943 => 248,  941 => 247,  938 => 246,  930 => 240,  927 => 239,  925 => 238,  922 => 237,  914 => 233,  911 => 232,  909 => 231,  906 => 230,  894 => 226,  891 => 225,  888 => 224,  885 => 223,  883 => 222,  880 => 221,  872 => 217,  869 => 216,  867 => 215,  864 => 214,  856 => 210,  853 => 209,  851 => 208,  848 => 207,  840 => 203,  837 => 202,  835 => 201,  832 => 200,  824 => 196,  821 => 195,  819 => 194,  816 => 193,  808 => 189,  805 => 188,  800 => 186,  789 => 181,  787 => 180,  776 => 175,  774 => 174,  763 => 169,  760 => 168,  758 => 167,  755 => 166,  747 => 162,  744 => 161,  740 => 159,  737 => 158,  730 => 153,  720 => 152,  715 => 151,  712 => 150,  703 => 147,  701 => 146,  688 => 138,  687 => 137,  685 => 135,  680 => 134,  674 => 132,  671 => 131,  669 => 130,  666 => 129,  657 => 123,  653 => 122,  649 => 121,  640 => 119,  634 => 117,  631 => 116,  629 => 115,  626 => 114,  610 => 110,  608 => 109,  605 => 108,  589 => 104,  584 => 102,  567 => 98,  555 => 96,  548 => 93,  546 => 92,  541 => 91,  538 => 90,  518 => 88,  506 => 82,  503 => 81,  500 => 80,  484 => 75,  481 => 74,  471 => 72,  459 => 69,  450 => 64,  442 => 62,  426 => 58,  414 => 52,  405 => 49,  403 => 48,  400 => 47,  388 => 42,  385 => 41,  377 => 37,  366 => 33,  350 => 26,  313 => 15,  308 => 13,  271 => 371,  266 => 363,  260 => 360,  250 => 338,  245 => 332,  225 => 295,  215 => 277,  146 => 178,  129 => 145,  126 => 144,  124 => 129,  114 => 108,  356 => 328,  339 => 316,  77 => 28,  65 => 17,  118 => 49,  462 => 202,  446 => 197,  441 => 196,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  408 => 50,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  342 => 23,  325 => 129,  320 => 127,  289 => 113,  286 => 112,  275 => 105,  270 => 102,  267 => 101,  262 => 98,  256 => 96,  233 => 301,  226 => 84,  200 => 72,  181 => 229,  150 => 55,  113 => 40,  806 => 488,  803 => 187,  792 => 182,  788 => 484,  784 => 179,  771 => 173,  745 => 476,  742 => 160,  723 => 473,  706 => 148,  702 => 470,  698 => 145,  694 => 468,  690 => 139,  686 => 136,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 120,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  515 => 87,  244 => 136,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 24,  332 => 20,  327 => 114,  324 => 113,  318 => 111,  306 => 107,  303 => 122,  300 => 121,  297 => 276,  263 => 362,  212 => 276,  202 => 263,  190 => 76,  185 => 66,  174 => 214,  389 => 160,  386 => 159,  380 => 160,  378 => 157,  371 => 35,  363 => 32,  361 => 146,  358 => 151,  343 => 146,  340 => 145,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  296 => 121,  293 => 6,  290 => 5,  288 => 4,  281 => 385,  276 => 378,  274 => 97,  265 => 96,  255 => 350,  253 => 339,  248 => 333,  232 => 88,  222 => 294,  216 => 79,  213 => 78,  210 => 267,  197 => 246,  184 => 230,  178 => 66,  175 => 65,  172 => 64,  170 => 84,  155 => 47,  152 => 46,  134 => 158,  97 => 41,  53 => 5,  23 => 1,  100 => 36,  81 => 40,  643 => 293,  593 => 245,  587 => 103,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 89,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 77,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 68,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 60,  428 => 59,  425 => 186,  420 => 183,  411 => 179,  390 => 43,  383 => 171,  372 => 162,  370 => 161,  367 => 155,  353 => 121,  349 => 153,  345 => 147,  338 => 135,  334 => 141,  319 => 137,  316 => 16,  311 => 14,  299 => 8,  295 => 275,  291 => 102,  287 => 126,  280 => 124,  277 => 123,  259 => 103,  257 => 109,  249 => 105,  231 => 83,  211 => 91,  207 => 266,  194 => 245,  191 => 243,  186 => 236,  165 => 60,  70 => 19,  58 => 14,  161 => 199,  90 => 27,  84 => 41,  34 => 5,  501 => 44,  494 => 78,  491 => 383,  487 => 76,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 198,  447 => 360,  335 => 21,  329 => 131,  323 => 128,  321 => 112,  261 => 188,  127 => 35,  110 => 38,  104 => 87,  76 => 31,  20 => 1,  480 => 162,  474 => 161,  469 => 71,  461 => 70,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 61,  435 => 146,  430 => 188,  427 => 143,  423 => 57,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 36,  368 => 34,  365 => 111,  362 => 157,  360 => 109,  355 => 27,  341 => 118,  337 => 22,  322 => 101,  314 => 99,  312 => 124,  309 => 108,  305 => 132,  298 => 120,  294 => 90,  285 => 3,  283 => 100,  278 => 384,  268 => 370,  264 => 84,  258 => 351,  252 => 80,  247 => 78,  241 => 90,  229 => 85,  220 => 287,  214 => 92,  177 => 73,  169 => 207,  140 => 58,  132 => 51,  128 => 51,  107 => 37,  61 => 2,  273 => 377,  269 => 107,  254 => 108,  243 => 324,  240 => 323,  238 => 309,  235 => 308,  230 => 300,  227 => 298,  224 => 81,  221 => 77,  219 => 76,  217 => 286,  208 => 76,  204 => 264,  179 => 221,  159 => 193,  143 => 51,  135 => 62,  119 => 114,  102 => 30,  71 => 19,  67 => 16,  63 => 21,  59 => 6,  28 => 3,  94 => 57,  89 => 47,  85 => 26,  75 => 22,  68 => 20,  56 => 12,  38 => 7,  87 => 26,  201 => 92,  196 => 92,  183 => 82,  171 => 213,  166 => 206,  163 => 82,  158 => 62,  156 => 192,  151 => 185,  142 => 59,  138 => 54,  136 => 165,  121 => 128,  117 => 39,  105 => 34,  91 => 56,  62 => 14,  49 => 12,  93 => 28,  88 => 30,  78 => 24,  44 => 11,  31 => 4,  27 => 4,  21 => 2,  24 => 3,  25 => 35,  46 => 10,  26 => 3,  19 => 1,  79 => 32,  72 => 18,  69 => 11,  47 => 12,  40 => 8,  37 => 7,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 166,  131 => 157,  123 => 42,  120 => 31,  115 => 40,  111 => 107,  108 => 33,  101 => 86,  98 => 29,  96 => 67,  83 => 31,  74 => 20,  66 => 12,  55 => 12,  52 => 13,  50 => 10,  43 => 9,  41 => 7,  35 => 7,  32 => 6,  29 => 5,  209 => 82,  203 => 73,  199 => 262,  193 => 73,  189 => 237,  187 => 75,  182 => 87,  176 => 220,  173 => 85,  168 => 61,  164 => 200,  162 => 59,  154 => 186,  149 => 179,  147 => 54,  144 => 173,  141 => 172,  133 => 53,  130 => 46,  125 => 51,  122 => 41,  116 => 113,  112 => 39,  109 => 102,  106 => 101,  103 => 28,  99 => 68,  95 => 34,  92 => 31,  86 => 46,  82 => 26,  80 => 24,  73 => 27,  64 => 10,  60 => 20,  57 => 20,  54 => 15,  51 => 37,  48 => 11,  45 => 8,  42 => 10,  39 => 10,  36 => 5,  33 => 6,  30 => 3,);
    }
}
