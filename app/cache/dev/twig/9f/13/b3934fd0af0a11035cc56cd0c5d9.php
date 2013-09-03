<?php

/* WebProfilerBundle:Profiler:base_js.html.twig */
class __TwigTemplate_9f13b3934fd0af0a11035cc56cd0c5d9 extends Twig_Template
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
        echo "<script>/*<![CDATA[*/
    Sfjs = (function() {
        \"use strict\";

        var noop = function() {},

            profilerStorageKey = 'sf2/profiler/',

            request = function(url, onSuccess, onError, payload, options) {
                var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');
                options = options || {};
                xhr.open(options.method || 'GET', url, true);
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
                xhr.onreadystatechange = function(state) {
                    if (4 === xhr.readyState && 200 === xhr.status) {
                        (onSuccess || noop)(xhr);
                    } else if (4 === xhr.readyState && xhr.status != 200) {
                        (onError || noop)(xhr);
                    }
                };
                xhr.send(payload || '');
            },

            hasClass = function(el, klass) {
                return el.className.match(new RegExp('\\\\b' + klass + '\\\\b'));
            },

            removeClass = function(el, klass) {
                el.className = el.className.replace(new RegExp('\\\\b' + klass + '\\\\b'), ' ');
            },

            addClass = function(el, klass) {
                if (!hasClass(el, klass)) { el.className += \" \" + klass; }
            },

            getPreference = function(name) {
                if (!window.localStorage) {
                    return null;
                }

                return localStorage.getItem(profilerStorageKey + name);
            },

            setPreference = function(name, value) {
                if (!window.localStorage) {
                    return null;
                }

                localStorage.setItem(profilerStorageKey + name, value);
            };

        return {
            hasClass: hasClass,

            removeClass: removeClass,

            addClass: addClass,

            getPreference: getPreference,

            setPreference: setPreference,

            request: request,

            load: function(selector, url, onSuccess, onError, options) {
                var el = document.getElementById(selector);

                if (el && el.getAttribute('data-sfurl') !== url) {
                    request(
                        url,
                        function(xhr) {
                            el.innerHTML = xhr.responseText;
                            el.setAttribute('data-sfurl', url);
                            removeClass(el, 'loading');
                            (onSuccess || noop)(xhr, el);
                        },
                        function(xhr) { (onError || noop)(xhr, el); },
                        options
                    );
                }

                return this;
            },

            toggle: function(selector, elOn, elOff) {
                var i,
                    style,
                    tmp = elOn.style.display,
                    el = document.getElementById(selector);

                elOn.style.display = elOff.style.display;
                elOff.style.display = tmp;

                if (el) {
                    el.style.display = 'none' === tmp ? 'none' : 'block';
                }

                return this;
            }
        }
    })();
/*]]>*/</script>
";
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Profiler:base_js.html.twig";
    }

    public function getDebugInfo()
    {
        return array (  77 => 25,  65 => 22,  118 => 49,  462 => 202,  446 => 197,  441 => 196,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  408 => 176,  401 => 172,  394 => 168,  373 => 156,  348 => 140,  342 => 137,  325 => 129,  320 => 127,  289 => 113,  286 => 112,  275 => 105,  270 => 102,  267 => 101,  262 => 98,  256 => 96,  233 => 87,  226 => 84,  200 => 72,  181 => 65,  150 => 55,  113 => 40,  806 => 488,  803 => 487,  792 => 485,  788 => 484,  784 => 482,  771 => 481,  745 => 476,  742 => 475,  723 => 473,  706 => 472,  702 => 470,  698 => 469,  694 => 468,  690 => 467,  686 => 466,  682 => 465,  678 => 464,  675 => 463,  673 => 462,  656 => 461,  645 => 460,  630 => 455,  625 => 453,  621 => 452,  618 => 451,  616 => 450,  602 => 449,  565 => 414,  547 => 411,  530 => 410,  527 => 409,  525 => 408,  515 => 404,  244 => 136,  188 => 90,  153 => 56,  357 => 123,  351 => 141,  344 => 119,  332 => 116,  327 => 114,  324 => 113,  318 => 111,  306 => 107,  303 => 122,  300 => 121,  297 => 104,  263 => 95,  212 => 78,  202 => 77,  190 => 76,  185 => 66,  174 => 65,  389 => 160,  386 => 159,  380 => 160,  378 => 157,  371 => 156,  363 => 126,  361 => 146,  358 => 151,  343 => 146,  340 => 145,  331 => 140,  328 => 139,  326 => 138,  315 => 125,  307 => 128,  302 => 125,  296 => 121,  293 => 120,  290 => 119,  288 => 101,  281 => 114,  276 => 111,  274 => 97,  265 => 96,  255 => 93,  253 => 100,  248 => 94,  232 => 88,  222 => 83,  216 => 79,  213 => 78,  210 => 77,  197 => 71,  184 => 63,  178 => 66,  175 => 65,  172 => 64,  170 => 84,  155 => 47,  152 => 46,  134 => 54,  97 => 41,  53 => 17,  23 => 3,  100 => 36,  81 => 23,  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 406,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 155,  353 => 121,  349 => 153,  345 => 147,  338 => 135,  334 => 141,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 102,  287 => 126,  280 => 124,  277 => 123,  259 => 103,  257 => 109,  249 => 105,  231 => 83,  211 => 91,  207 => 75,  194 => 70,  191 => 67,  186 => 76,  165 => 60,  70 => 19,  58 => 25,  161 => 63,  90 => 27,  84 => 24,  34 => 5,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 198,  447 => 360,  335 => 134,  329 => 131,  323 => 128,  321 => 112,  261 => 188,  127 => 35,  110 => 22,  104 => 37,  76 => 31,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 199,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 164,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 143,  341 => 118,  337 => 103,  322 => 101,  314 => 99,  312 => 124,  309 => 108,  305 => 132,  298 => 120,  294 => 90,  285 => 89,  283 => 100,  278 => 106,  268 => 85,  264 => 84,  258 => 94,  252 => 80,  247 => 78,  241 => 90,  229 => 85,  220 => 81,  214 => 92,  177 => 73,  169 => 71,  140 => 58,  132 => 51,  128 => 51,  107 => 36,  61 => 17,  273 => 122,  269 => 107,  254 => 108,  243 => 92,  240 => 101,  238 => 85,  235 => 85,  230 => 82,  227 => 86,  224 => 81,  221 => 77,  219 => 76,  217 => 75,  208 => 76,  204 => 72,  179 => 69,  159 => 61,  143 => 51,  135 => 62,  119 => 40,  102 => 40,  71 => 27,  67 => 24,  63 => 18,  59 => 16,  28 => 6,  94 => 21,  89 => 35,  85 => 24,  75 => 19,  68 => 30,  56 => 11,  38 => 7,  87 => 33,  201 => 92,  196 => 92,  183 => 82,  171 => 61,  166 => 54,  163 => 82,  158 => 62,  156 => 58,  151 => 59,  142 => 59,  138 => 54,  136 => 71,  121 => 50,  117 => 39,  105 => 34,  91 => 34,  62 => 21,  49 => 14,  93 => 29,  88 => 32,  78 => 26,  44 => 10,  31 => 4,  27 => 3,  21 => 2,  24 => 7,  25 => 35,  46 => 13,  26 => 9,  19 => 1,  79 => 21,  72 => 18,  69 => 17,  47 => 15,  40 => 11,  37 => 5,  22 => 2,  246 => 93,  157 => 30,  145 => 74,  139 => 49,  131 => 45,  123 => 42,  120 => 31,  115 => 46,  111 => 47,  108 => 19,  101 => 31,  98 => 45,  96 => 35,  83 => 33,  74 => 27,  66 => 22,  55 => 16,  52 => 12,  50 => 16,  43 => 12,  41 => 19,  35 => 6,  32 => 5,  29 => 3,  209 => 82,  203 => 73,  199 => 93,  193 => 73,  189 => 66,  187 => 75,  182 => 87,  176 => 63,  173 => 85,  168 => 61,  164 => 59,  162 => 59,  154 => 60,  149 => 51,  147 => 54,  144 => 42,  141 => 51,  133 => 53,  130 => 46,  125 => 51,  122 => 41,  116 => 39,  112 => 36,  109 => 52,  106 => 51,  103 => 28,  99 => 31,  95 => 28,  92 => 43,  86 => 28,  82 => 28,  80 => 32,  73 => 23,  64 => 23,  60 => 20,  57 => 19,  54 => 16,  51 => 13,  48 => 9,  45 => 14,  42 => 7,  39 => 6,  36 => 9,  33 => 4,  30 => 7,);
    }
}
