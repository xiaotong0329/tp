<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">ClubHub</a>

    <navbar-item slot="left" href="{{baseUrl}}/index.html">Home</navbar-item>
    <navbar-item slot="left" href="{{baseUrl}}/UserGuide.html">User Guide</navbar-item>
    <navbar-item slot="left" href="{{baseUrl}}/DeveloperGuide.html">Developer Guide</navbar-item>
    <navbar-item slot="left" href="{{baseUrl}}/AboutUs.html">About Us</navbar-item>
    <navbar-item slot="left" href="https://github.com/AY2526S1-CS2103T-T10-3/tp" target="_blank">
      <md>:fab-github:</md>
    </navbar-item>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [Quick Start]({{ baseUrl }}/UserGuide.html#quick-start)
  * [Features]({{ baseUrl }}/UserGuide.html#features)
  * [FAQ]({{ baseUrl }}/UserGuide.html#faq)
  * [Command Summary]({{ baseUrl }}/UserGuide.html#command-summary)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>

  <div id="content-wrapper">
    {{ content }}
  </div>

  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>

  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <div class="text-center">
    <small>
      [<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]
    </small>
  </div>
</footer>
