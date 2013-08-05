<?php

/* NelmioApiDocBundle::method.html.twig */
class __TwigTemplate_22dbcfa1e335e4bdff437f2ebb663133 extends Twig_Template
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
        echo "<li class=\"";
        echo twig_escape_filter($this->env, twig_lower_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "method")), "html", null, true);
        echo " operation\">
    <div class=\"heading toggler";
        // line 2
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "deprecated")) {
            echo " deprecated";
        }
        echo "\">
    <h3>
        <span class=\"http_method\">
            <a>";
        // line 5
        echo twig_escape_filter($this->env, twig_upper_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "method")), "html", null, true);
        echo "</a>
        </span>

        ";
        // line 8
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "deprecated")) {
            // line 9
            echo "        <span class=\"deprecated\">
            <a>DEPRECATED</a>
        </span>
        ";
        }
        // line 13
        echo "
        ";
        // line 14
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "https")) {
            // line 15
            echo "            <span class=\"icon lock\" title=\"HTTPS\"></span>
        ";
        }
        // line 17
        echo "        ";
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "authentication")) {
            // line 18
            echo "            <span class=\"icon keys\" title=\"Needs authentication\"></span>
        ";
        }
        // line 20
        echo "
        <span class=\"path\">
            ";
        // line 22
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "host", array(), "any", true, true)) {
            // line 23
            echo (($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "https")) ? ("https://") : ("http://"));
            // line 24
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "host"), "html", null, true);
        }
        // line 26
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "uri"), "html", null, true);
        echo "
        </span>
    </h3>
    <ul class=\"options\">
        ";
        // line 30
        if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "description", array(), "any", true, true)) {
            // line 31
            echo "            <li>";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "description"), "html", null, true);
            echo "</li>
        ";
        }
        // line 33
        echo "    </ul>
    </div>

    <div class=\"content\" style=\"display: ";
        // line 36
        if ((array_key_exists("displayContent", $context) && ((isset($context["displayContent"]) ? $context["displayContent"] : $this->getContext($context, "displayContent")) == true))) {
            echo "display";
        } else {
            echo "none";
        }
        echo ";\">
        <ul class=\"tabs\">
            <li class=\"selected\" data-pane=\"content\">Documentation</li>
            ";
        // line 39
        if ((isset($context["enableSandbox"]) ? $context["enableSandbox"] : $this->getContext($context, "enableSandbox"))) {
            // line 40
            echo "                <li data-pane=\"sandbox\">Sandbox</li>
            ";
        }
        // line 42
        echo "        </ul>

        <div class=\"panes\">
            <div class=\"pane content selected\">
            ";
        // line 46
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "documentation", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "documentation"))))) {
            // line 47
            echo "                <h4>Documentation</h4>
                <div>";
            // line 48
            echo $this->env->getExtension('nelmio_api_doc')->markdown($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "documentation"));
            echo "</div>
            ";
        }
        // line 50
        echo "
            ";
        // line 51
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "requirements", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"))))) {
            // line 52
            echo "                <h4>Requirements</h4>
                <table class=\"fullwidth\">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Requirement</th>
                            <th>Type</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        ";
            // line 63
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 64
                echo "                            <tr>
                                <td>";
                // line 65
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                                <td>";
                // line 66
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "requirement"), "html", null, true);
                echo "</td>
                                <td>";
                // line 67
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                echo "</td>
                                <td>";
                // line 68
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                echo "</td>
                            </tr>
                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 71
            echo "                    </tbody>
                </table>
            ";
        }
        // line 74
        echo "
            ";
        // line 75
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "filters", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"))))) {
            // line 76
            echo "                <h4>Filters</h4>
                <table class=\"fullwidth\">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Information</th>
                        </tr>
                    </thead>
                    <tbody>
                    ";
            // line 85
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 86
                echo "                        <tr>
                            <td>";
                // line 87
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                            <td>
                                <table>
                                ";
                // line 90
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")));
                foreach ($context['_seq'] as $context["key"] => $context["value"]) {
                    // line 91
                    echo "                                    <tr>
                                        <td>";
                    // line 92
                    echo twig_escape_filter($this->env, twig_title_string_filter($this->env, (isset($context["key"]) ? $context["key"] : $this->getContext($context, "key"))), "html", null, true);
                    echo "</td>
                                        <td>";
                    // line 93
                    echo twig_escape_filter($this->env, trim(strtr(twig_jsonencode_filter((isset($context["value"]) ? $context["value"] : $this->getContext($context, "value"))), array("\\\\" => "\\")), "\""), "html", null, true);
                    echo "</td>
                                    </tr>
                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['key'], $context['value'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 96
                echo "                                </table>
                            </td>
                        </tr>
                    ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 100
            echo "                    </tbody>
                </table>
            ";
        }
        // line 103
        echo "
            ";
        // line 104
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "parameters", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"))))) {
            // line 105
            echo "                <h4>Parameters</h4>
                <table class='fullwidth'>
                    <thead>
                        <tr>
                            <th>Parameter</th>
                            <th>Type</th>
                            <th>Required?</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        ";
            // line 116
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 117
                echo "                            ";
                if ((!$this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "readonly"))) {
                    // line 118
                    echo "                                <tr>
                                    <td>";
                    // line 119
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "</td>
                                    <td>";
                    // line 120
                    echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                    echo "</td>
                                    <td>";
                    // line 121
                    echo (($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "required")) ? ("true") : ("false"));
                    echo "</td>
                                    <td>";
                    // line 122
                    echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                    echo "</td>
                                </tr>
                            ";
                }
                // line 125
                echo "                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 126
            echo "                    </tbody>
                </table>
            ";
        }
        // line 129
        echo "
            ";
        // line 130
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "response", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "response"))))) {
            // line 131
            echo "                <h4>Return</h4>
                <table class='fullwidth'>
                    <thead>
                        <tr>
                            <th>Parameter</th>
                            <th>Type</th>
                            <th>Versions</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        ";
            // line 142
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "response"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 143
                echo "                            <tr>
                                <td>";
                // line 144
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                                <td>";
                // line 145
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                echo "</td>
                                <td>";
                // line 146
                $this->env->loadTemplate("NelmioApiDocBundle:Components:version.html.twig")->display(array("sinceVersion" => $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "sinceVersion"), "untilVersion" => $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "untilVersion")));
                echo "</td>
                                <td>";
                // line 147
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                echo "</td>
                            </tr>
                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 150
            echo "                    </tbody>
                </table>
            ";
        }
        // line 153
        echo "
            ";
        // line 154
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "statusCodes", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "statusCodes"))))) {
            // line 155
            echo "                <h4>Status Codes</h4>
                <table class=\"fullwidth\">
                    <thead>
                    <tr>
                        <th>Status Code</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    ";
            // line 164
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "statusCodes"));
            foreach ($context['_seq'] as $context["status_code"] => $context["descriptions"]) {
                // line 165
                echo "                        <tr>
                            <td><a href=\"http://en.wikipedia.org/wiki/HTTP_";
                // line 166
                echo twig_escape_filter($this->env, (isset($context["status_code"]) ? $context["status_code"] : $this->getContext($context, "status_code")), "html", null, true);
                echo "\" target=\"_blank\">";
                echo twig_escape_filter($this->env, (isset($context["status_code"]) ? $context["status_code"] : $this->getContext($context, "status_code")), "html", null, true);
                echo "<a/></td>
                            <td>
                                <ul>
                                    ";
                // line 169
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable((isset($context["descriptions"]) ? $context["descriptions"] : $this->getContext($context, "descriptions")));
                foreach ($context['_seq'] as $context["_key"] => $context["description"]) {
                    // line 170
                    echo "                                        <li>";
                    echo twig_escape_filter($this->env, (isset($context["description"]) ? $context["description"] : $this->getContext($context, "description")), "html", null, true);
                    echo "</li>
                                    ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['_key'], $context['description'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 172
                echo "                                </ul>
                            </td>
                        </tr>
                    ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['status_code'], $context['descriptions'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 176
            echo "                    </tbody>
                </table>
            ";
        }
        // line 179
        echo "
            ";
        // line 180
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "cache", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "cache"))))) {
            // line 181
            echo "                <h4>Cache</h4>
                <div>";
            // line 182
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "cache"), "html", null, true);
            echo "s</div>
            ";
        }
        // line 184
        echo "
            </div>

            ";
        // line 187
        if ((isset($context["enableSandbox"]) ? $context["enableSandbox"] : $this->getContext($context, "enableSandbox"))) {
            // line 188
            echo "                <div class=\"pane sandbox\">
                    <form method=\"";
            // line 189
            echo twig_escape_filter($this->env, twig_upper_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "method")), "html", null, true);
            echo "\" action=\"";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "uri"), "html", null, true);
            echo "\">
                        <fieldset class=\"parameters\">
                            <legend>Input</legend>
                            ";
            // line 192
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "requirements", array(), "any", true, true)) {
                // line 193
                echo "                                <h4>Requirements</h4>
                                ";
                // line 194
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 195
                    echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                    // line 196
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                    // line 198
                    if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : null), "description", array(), "any", true, true)) {
                        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                    } else {
                        echo "Value";
                    }
                    echo "\" /> <span class=\"remove\">-</span>
                                    </p>
                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 201
                echo "                            ";
            }
            // line 202
            echo "                            ";
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "filters", array(), "any", true, true)) {
                // line 203
                echo "                                <h4>Filters</h4>
                                ";
                // line 204
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 205
                    echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                    // line 206
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                    // line 208
                    if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : null), "description", array(), "any", true, true)) {
                        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                    } else {
                        echo "Value";
                    }
                    echo "\" /> <span class=\"remove\">-</span>
                                    </p>
                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 211
                echo "                            ";
            }
            // line 212
            echo "                            ";
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "parameters", array(), "any", true, true)) {
                // line 213
                echo "                                <h4>Parameters</h4>
                                ";
                // line 214
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 215
                    echo "                                ";
                    if ((!$this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "readonly"))) {
                        // line 216
                        echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                        // line 217
                        echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                        echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                        // line 219
                        if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType")) {
                            echo "[";
                            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                            echo "] ";
                        }
                        if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description")) {
                            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                        } else {
                            echo "Value";
                        }
                        echo "\" /> <span class=\"remove\">-</span>
                                    </p>
                                ";
                    }
                    // line 222
                    echo "                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 223
                echo "                                <button class=\"add\">New parameter</button>
                            ";
            }
            // line 225
            echo "
                        </fieldset>

                        <fieldset class=\"headers\">
                            <legend>Headers</legend>

                            ";
            // line 231
            if ((isset($context["acceptType"]) ? $context["acceptType"] : $this->getContext($context, "acceptType"))) {
                // line 232
                echo "                                <p class=\"tuple\">
                                    <input type=\"text\" class=\"key\" value=\"Accept\" />
                                    <span>=</span>
                                    <input type=\"text\" class=\"value\" value=\"";
                // line 235
                echo twig_escape_filter($this->env, (isset($context["acceptType"]) ? $context["acceptType"] : $this->getContext($context, "acceptType")), "html", null, true);
                echo "\" /> <span class=\"remove\">-</span>
                                </p>
                            ";
            }
            // line 238
            echo "
                            <p class=\"tuple\">
                                <input type=\"text\" class=\"key\" placeholder=\"Key\" />
                                <span>=</span>
                                <input type=\"text\" class=\"value\" placeholder=\"Value\" /> <span class=\"remove\">-</span>
                            </p>

                            <button class=\"add\">New header</button>
                        </fieldset>

                        <fieldset class=\"request-content\">
                            <legend>Content</legend>

                            <textarea class=\"content\" placeholder=\"Content set here will override the parameters that do not match the url\"></textarea>

                            <p class=\"tuple\">
                                <input type=\"text\" class=\"key content-type\" value=\"Content-Type\" disabled=\"disabled\" />
                                <span>=</span>
                                <input type=\"text\" class=\"value\" placeholder=\"Value\" />
                                <button class=\"set-content-type\">Set header</button> <small>Replaces header if set</small>
                            </p>
                        </fieldset>

                        <div class=\"buttons\">
                            <input type=\"submit\" value=\"Try!\" />
                        </div>
                    </form>

                    <script type=\"text/x-tmpl\" class=\"tuple_template\">
                    <p class=\"tuple\">
                        <input type=\"text\" class=\"key\" placeholder=\"Key\" />
                        <span>=</span>
                        <input type=\"text\" class=\"value\" placeholder=\"Value\" /> <span class=\"remove\">-</span>
                    </p>
                    </script>

                    <div class=\"result\">
                        <h4>Request URL</h4>
                        <pre class=\"url\"></pre>

                        <h4>Response Headers&nbsp;<small>[<a href=\"\" class=\"to-expand\">Expand</a>]</small></h4>
                        <pre class=\"headers to-expand\"></pre>

                        <h4>Response Body&nbsp;<small>[<a href=\"\" class=\"to-raw\">Raw</a>]</small></h4>
                        <pre class=\"response prettyprint\"></pre>
                    </div>
                </div>
            ";
        }
        // line 286
        echo "        </div>
    </div>
</li>
";
    }

    public function getTemplateName()
    {
        return "NelmioApiDocBundle::method.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  620 => 286,  570 => 238,  564 => 235,  559 => 232,  557 => 231,  549 => 225,  545 => 223,  539 => 222,  524 => 219,  519 => 217,  516 => 216,  513 => 215,  509 => 214,  506 => 213,  503 => 212,  500 => 211,  487 => 208,  472 => 203,  466 => 201,  448 => 196,  441 => 194,  438 => 193,  436 => 192,  428 => 189,  425 => 188,  418 => 184,  410 => 181,  408 => 180,  405 => 179,  400 => 176,  391 => 172,  382 => 170,  378 => 169,  370 => 166,  367 => 165,  363 => 164,  352 => 155,  350 => 154,  347 => 153,  342 => 150,  333 => 147,  325 => 145,  318 => 143,  301 => 131,  299 => 130,  296 => 129,  291 => 126,  279 => 122,  275 => 121,  271 => 120,  267 => 119,  257 => 116,  244 => 105,  242 => 104,  239 => 103,  234 => 100,  225 => 96,  216 => 93,  212 => 92,  205 => 90,  192 => 85,  181 => 76,  179 => 75,  150 => 65,  58 => 18,  161 => 32,  102 => 21,  90 => 15,  34 => 6,  489 => 44,  482 => 206,  479 => 205,  475 => 204,  458 => 362,  447 => 359,  445 => 195,  335 => 250,  329 => 146,  323 => 246,  321 => 144,  261 => 117,  127 => 56,  110 => 45,  104 => 42,  76 => 29,  20 => 1,  480 => 162,  474 => 161,  469 => 202,  461 => 155,  457 => 153,  453 => 198,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 187,  413 => 182,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 142,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 125,  283 => 88,  278 => 86,  268 => 85,  264 => 118,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  235 => 74,  229 => 73,  224 => 71,  220 => 70,  214 => 69,  208 => 68,  169 => 60,  143 => 63,  140 => 55,  132 => 51,  128 => 51,  119 => 22,  107 => 36,  71 => 27,  177 => 65,  165 => 64,  160 => 61,  135 => 47,  126 => 45,  114 => 42,  84 => 13,  70 => 24,  67 => 26,  61 => 13,  28 => 3,  94 => 22,  89 => 35,  85 => 33,  75 => 23,  68 => 23,  56 => 21,  38 => 8,  87 => 20,  201 => 92,  196 => 86,  183 => 70,  171 => 71,  166 => 71,  163 => 70,  158 => 67,  156 => 58,  151 => 57,  142 => 59,  138 => 57,  136 => 24,  121 => 46,  117 => 47,  105 => 40,  91 => 31,  62 => 20,  49 => 14,  93 => 36,  88 => 33,  78 => 10,  44 => 12,  31 => 4,  27 => 4,  21 => 2,  24 => 2,  25 => 4,  46 => 13,  26 => 6,  19 => 1,  79 => 18,  72 => 16,  69 => 25,  47 => 17,  40 => 9,  37 => 8,  22 => 2,  246 => 32,  157 => 30,  145 => 46,  139 => 50,  131 => 42,  123 => 47,  120 => 48,  115 => 46,  111 => 37,  108 => 44,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 17,  52 => 6,  50 => 10,  43 => 6,  41 => 5,  35 => 5,  32 => 5,  29 => 3,  209 => 91,  203 => 78,  199 => 87,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 74,  173 => 74,  168 => 66,  164 => 59,  162 => 68,  154 => 66,  149 => 51,  147 => 64,  144 => 53,  141 => 51,  133 => 55,  130 => 52,  125 => 50,  122 => 23,  116 => 36,  112 => 42,  109 => 42,  106 => 36,  103 => 39,  99 => 30,  95 => 34,  92 => 36,  86 => 28,  82 => 31,  80 => 30,  73 => 26,  64 => 14,  60 => 9,  57 => 11,  54 => 10,  51 => 15,  48 => 8,  45 => 17,  42 => 7,  39 => 9,  36 => 5,  33 => 9,  30 => 7,);
    }
}
