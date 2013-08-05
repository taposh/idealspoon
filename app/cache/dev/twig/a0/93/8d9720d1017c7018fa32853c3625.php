<?php

/* IdealspoonFrontendBundle:Default:index.html.twig */
class __TwigTemplate_a0938d9720d1017c7018fa32853c3625 extends Twig_Template
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
        echo "<h1>Coming soon....</h1>

";
        // line 3
        if (isset($context['assetic']['debug']) && $context['assetic']['debug']) {
            // asset "05074b5_0"
            $context["asset_url"] = isset($context['assetic']['use_controller']) && $context['assetic']['use_controller'] ? $this->env->getExtension('routing')->getPath("_assetic_05074b5_0") : $this->env->getExtension('assets')->getAssetUrl("/images/image_logo_image_logo_1.png");
            // line 4
            echo "<img src=\"";
            echo twig_escape_filter($this->env, (isset($context["asset_url"]) ? $context["asset_url"] : $this->getContext($context, "asset_url")), "html", null, true);
            echo "\" alt=\"Idealspoon\" />
";
        } else {
            // asset "05074b5"
            $context["asset_url"] = isset($context['assetic']['use_controller']) && $context['assetic']['use_controller'] ? $this->env->getExtension('routing')->getPath("_assetic_05074b5") : $this->env->getExtension('assets')->getAssetUrl("/images/image_logo.png");
            echo "<img src=\"";
            echo twig_escape_filter($this->env, (isset($context["asset_url"]) ? $context["asset_url"] : $this->getContext($context, "asset_url")), "html", null, true);
            echo "\" alt=\"Idealspoon\" />
";
        }
        unset($context["asset_url"]);
    }

    public function getTemplateName()
    {
        return "IdealspoonFrontendBundle:Default:index.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  27 => 4,  23 => 3,  19 => 1,);
    }
}
