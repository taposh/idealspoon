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
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "link", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "link"))))) {
            // line 52
            echo "                <h4>Link</h4>
                <div><a href=\"";
            // line 53
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "link"), "html", null, true);
            echo "\" target=\"_blank\">";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "link"), "html", null, true);
            echo "</a></div>
            ";
        }
        // line 55
        echo "
            ";
        // line 56
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "requirements", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"))))) {
            // line 57
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
            // line 68
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 69
                echo "                            <tr>
                                <td>";
                // line 70
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                                <td>";
                // line 71
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "requirement"), "html", null, true);
                echo "</td>
                                <td>";
                // line 72
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                echo "</td>
                                <td>";
                // line 73
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                echo "</td>
                            </tr>
                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 76
            echo "                    </tbody>
                </table>
            ";
        }
        // line 79
        echo "
            ";
        // line 80
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "filters", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"))))) {
            // line 81
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
            // line 90
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 91
                echo "                        <tr>
                            <td>";
                // line 92
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                            <td>
                                <table>
                                ";
                // line 95
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")));
                foreach ($context['_seq'] as $context["key"] => $context["value"]) {
                    // line 96
                    echo "                                    <tr>
                                        <td>";
                    // line 97
                    echo twig_escape_filter($this->env, twig_title_string_filter($this->env, (isset($context["key"]) ? $context["key"] : $this->getContext($context, "key"))), "html", null, true);
                    echo "</td>
                                        <td>";
                    // line 98
                    echo twig_escape_filter($this->env, trim(strtr(twig_jsonencode_filter((isset($context["value"]) ? $context["value"] : $this->getContext($context, "value"))), array("\\\\" => "\\")), "\""), "html", null, true);
                    echo "</td>
                                    </tr>
                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['key'], $context['value'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 101
                echo "                                </table>
                            </td>
                        </tr>
                    ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 105
            echo "                    </tbody>
                </table>
            ";
        }
        // line 108
        echo "
            ";
        // line 109
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "parameters", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"))))) {
            // line 110
            echo "                <h4>Parameters</h4>
                <table class='fullwidth'>
                    <thead>
                        <tr>
                            <th>Parameter</th>
                            <th>Type</th>
                            <th>Required?</th>
                            <th>Format</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        ";
            // line 122
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 123
                echo "                            ";
                if ((!$this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "readonly"))) {
                    // line 124
                    echo "                                <tr>
                                    <td>";
                    // line 125
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "</td>
                                    <td>";
                    // line 126
                    echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                    echo "</td>
                                    <td>";
                    // line 127
                    echo (($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "required")) ? ("true") : ("false"));
                    echo "</td>
                                    <td>";
                    // line 128
                    echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "format"), "html", null, true);
                    echo "</td>
                                    <td>";
                    // line 129
                    echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                    echo "</td>
                                </tr>
                            ";
                }
                // line 132
                echo "                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 133
            echo "                    </tbody>
                </table>
            ";
        }
        // line 136
        echo "
            ";
        // line 137
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "response", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "response"))))) {
            // line 138
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
            // line 149
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "response"));
            foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                // line 150
                echo "                            <tr>
                                <td>";
                // line 151
                echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                echo "</td>
                                <td>";
                // line 152
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                echo "</td>
                                <td>";
                // line 153
                $this->env->loadTemplate("NelmioApiDocBundle:Components:version.html.twig")->display(array("sinceVersion" => $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "sinceVersion"), "untilVersion" => $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "untilVersion")));
                echo "</td>
                                <td>";
                // line 154
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "description"), "html", null, true);
                echo "</td>
                            </tr>
                        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 157
            echo "                    </tbody>
                </table>
            ";
        }
        // line 160
        echo "
            ";
        // line 161
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "statusCodes", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "statusCodes"))))) {
            // line 162
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
            // line 171
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "statusCodes"));
            foreach ($context['_seq'] as $context["status_code"] => $context["descriptions"]) {
                // line 172
                echo "                        <tr>
                            <td><a href=\"http://en.wikipedia.org/wiki/HTTP_";
                // line 173
                echo twig_escape_filter($this->env, (isset($context["status_code"]) ? $context["status_code"] : $this->getContext($context, "status_code")), "html", null, true);
                echo "\" target=\"_blank\">";
                echo twig_escape_filter($this->env, (isset($context["status_code"]) ? $context["status_code"] : $this->getContext($context, "status_code")), "html", null, true);
                echo "<a/></td>
                            <td>
                                <ul>
                                    ";
                // line 176
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable((isset($context["descriptions"]) ? $context["descriptions"] : $this->getContext($context, "descriptions")));
                foreach ($context['_seq'] as $context["_key"] => $context["description"]) {
                    // line 177
                    echo "                                        <li>";
                    echo twig_escape_filter($this->env, (isset($context["description"]) ? $context["description"] : $this->getContext($context, "description")), "html", null, true);
                    echo "</li>
                                    ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['_key'], $context['description'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 179
                echo "                                </ul>
                            </td>
                        </tr>
                    ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['status_code'], $context['descriptions'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 183
            echo "                    </tbody>
                </table>
            ";
        }
        // line 186
        echo "
            ";
        // line 187
        if (($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "cache", array(), "any", true, true) && (!twig_test_empty($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "cache"))))) {
            // line 188
            echo "                <h4>Cache</h4>
                <div>";
            // line 189
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "cache"), "html", null, true);
            echo "s</div>
            ";
        }
        // line 191
        echo "
            </div>

            ";
        // line 194
        if ((isset($context["enableSandbox"]) ? $context["enableSandbox"] : $this->getContext($context, "enableSandbox"))) {
            // line 195
            echo "                <div class=\"pane sandbox\">
                    <form method=\"";
            // line 196
            echo twig_escape_filter($this->env, twig_upper_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "method")), "html", null, true);
            echo "\" action=\"";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "uri"), "html", null, true);
            echo "\">
                        <fieldset class=\"parameters\">
                            <legend>Input</legend>
                            ";
            // line 199
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "requirements", array(), "any", true, true)) {
                // line 200
                echo "                                <h4>Requirements</h4>
                                ";
                // line 201
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "requirements"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 202
                    echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                    // line 203
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                    // line 205
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
                // line 208
                echo "                            ";
            }
            // line 209
            echo "                            ";
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "filters", array(), "any", true, true)) {
                // line 210
                echo "                                <h4>Filters</h4>
                                ";
                // line 211
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "filters"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 212
                    echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                    // line 213
                    echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                    echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                    // line 215
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
                // line 218
                echo "                            ";
            }
            // line 219
            echo "                            ";
            if ($this->getAttribute((isset($context["data"]) ? $context["data"] : null), "parameters", array(), "any", true, true)) {
                // line 220
                echo "                                <h4>Parameters</h4>
                                ";
                // line 221
                $context['_parent'] = (array) $context;
                $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["data"]) ? $context["data"] : $this->getContext($context, "data")), "parameters"));
                foreach ($context['_seq'] as $context["name"] => $context["infos"]) {
                    // line 222
                    echo "                                ";
                    if ((!$this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "readonly"))) {
                        // line 223
                        echo "                                    <p class=\"tuple\">
                                        <input type=\"text\" class=\"key\" value=\"";
                        // line 224
                        echo twig_escape_filter($this->env, (isset($context["name"]) ? $context["name"] : $this->getContext($context, "name")), "html", null, true);
                        echo "\" placeholder=\"Key\" />
                                        <span>=</span>
                                        <input type=\"text\" class=\"value\" placeholder=\"";
                        // line 226
                        if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType")) {
                            echo "[";
                            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "dataType"), "html", null, true);
                            echo "] ";
                        }
                        if ($this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "format")) {
                            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["infos"]) ? $context["infos"] : $this->getContext($context, "infos")), "format"), "html", null, true);
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
                    // line 229
                    echo "                                ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['name'], $context['infos'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 230
                echo "                                <button class=\"add\">New parameter</button>
                            ";
            }
            // line 232
            echo "
                        </fieldset>

                        <fieldset class=\"headers\">
                            <legend>Headers</legend>

                            ";
            // line 238
            if ((isset($context["acceptType"]) ? $context["acceptType"] : $this->getContext($context, "acceptType"))) {
                // line 239
                echo "                                <p class=\"tuple\">
                                    <input type=\"text\" class=\"key\" value=\"Accept\" />
                                    <span>=</span>
                                    <input type=\"text\" class=\"value\" value=\"";
                // line 242
                echo twig_escape_filter($this->env, (isset($context["acceptType"]) ? $context["acceptType"] : $this->getContext($context, "acceptType")), "html", null, true);
                echo "\" /> <span class=\"remove\">-</span>
                                </p>
                            ";
            }
            // line 245
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
        // line 293
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
        return array (  643 => 293,  593 => 245,  587 => 242,  582 => 239,  580 => 238,  572 => 232,  568 => 230,  562 => 229,  544 => 226,  539 => 224,  536 => 223,  533 => 222,  529 => 221,  526 => 220,  523 => 219,  520 => 218,  507 => 215,  502 => 213,  499 => 212,  495 => 211,  492 => 210,  489 => 209,  486 => 208,  473 => 205,  468 => 203,  465 => 202,  458 => 200,  456 => 199,  448 => 196,  445 => 195,  443 => 194,  438 => 191,  433 => 189,  428 => 187,  425 => 186,  420 => 183,  411 => 179,  390 => 173,  383 => 171,  372 => 162,  370 => 161,  367 => 160,  353 => 154,  349 => 153,  345 => 152,  338 => 150,  334 => 149,  319 => 137,  316 => 136,  311 => 133,  299 => 129,  295 => 128,  291 => 127,  287 => 126,  280 => 124,  277 => 123,  259 => 110,  257 => 109,  249 => 105,  231 => 98,  211 => 91,  207 => 90,  194 => 80,  191 => 79,  186 => 76,  165 => 70,  70 => 24,  58 => 18,  161 => 32,  90 => 15,  84 => 13,  34 => 6,  501 => 44,  494 => 384,  491 => 383,  487 => 381,  482 => 379,  477 => 378,  460 => 364,  455 => 363,  449 => 361,  447 => 360,  335 => 250,  329 => 248,  323 => 246,  321 => 138,  261 => 188,  127 => 56,  110 => 45,  104 => 42,  76 => 29,  20 => 1,  480 => 162,  474 => 161,  469 => 158,  461 => 201,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 188,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 177,  398 => 176,  393 => 126,  387 => 172,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 157,  360 => 109,  355 => 106,  341 => 151,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 132,  298 => 91,  294 => 90,  285 => 89,  283 => 125,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 95,  214 => 92,  177 => 73,  169 => 71,  140 => 55,  132 => 51,  128 => 51,  107 => 36,  61 => 13,  273 => 122,  269 => 94,  254 => 108,  243 => 88,  240 => 101,  238 => 85,  235 => 74,  230 => 82,  227 => 97,  224 => 96,  221 => 77,  219 => 76,  217 => 75,  208 => 68,  204 => 72,  179 => 69,  159 => 61,  143 => 56,  135 => 53,  119 => 22,  102 => 21,  71 => 27,  67 => 26,  63 => 15,  59 => 14,  28 => 3,  94 => 28,  89 => 35,  85 => 33,  75 => 17,  68 => 23,  56 => 21,  38 => 8,  87 => 25,  201 => 92,  196 => 81,  183 => 82,  171 => 61,  166 => 71,  163 => 62,  158 => 68,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 24,  121 => 46,  117 => 47,  105 => 40,  91 => 27,  62 => 20,  49 => 14,  93 => 36,  88 => 33,  78 => 10,  44 => 12,  31 => 4,  27 => 4,  21 => 2,  24 => 2,  25 => 4,  46 => 13,  26 => 6,  19 => 1,  79 => 18,  72 => 16,  69 => 25,  47 => 17,  40 => 9,  37 => 8,  22 => 2,  246 => 90,  157 => 30,  145 => 57,  139 => 45,  131 => 52,  123 => 47,  120 => 48,  115 => 46,  111 => 37,  108 => 44,  101 => 41,  98 => 31,  96 => 17,  83 => 25,  74 => 14,  66 => 22,  55 => 17,  52 => 6,  50 => 10,  43 => 6,  41 => 5,  35 => 5,  32 => 5,  29 => 3,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 84,  182 => 66,  176 => 64,  173 => 72,  168 => 72,  164 => 59,  162 => 69,  154 => 29,  149 => 51,  147 => 58,  144 => 49,  141 => 48,  133 => 53,  130 => 52,  125 => 50,  122 => 23,  116 => 41,  112 => 42,  109 => 42,  106 => 36,  103 => 39,  99 => 31,  95 => 28,  92 => 36,  86 => 28,  82 => 31,  80 => 30,  73 => 26,  64 => 14,  60 => 9,  57 => 11,  54 => 10,  51 => 15,  48 => 8,  45 => 17,  42 => 7,  39 => 9,  36 => 5,  33 => 9,  30 => 7,);
    }
}
