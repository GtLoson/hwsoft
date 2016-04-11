<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <title>My App</title>
  <!-- Path to Framework7 Library CSS-->
  <link rel="stylesheet" href="theme/dist/css/framework7.ios.min.css">
  <link rel="stylesheet" href="theme/dist/css/framework7.ios.colors.min.css">
  <!-- Path to your custom app styles-->
  <link rel="stylesheet" href="theme/dist/css/my-app.css">
</head>
<body>
<!-- Status bar overlay for fullscreen mode-->
<div class="statusbar-overlay"></div>
<!-- Panels overlay-->
<div class="panel-overlay"></div>
<!-- Left panel with reveal effect-->
<div class="panel panel-left panel-reveal">
  <div class="content-block">
    <p>Left panel content goes here</p>
  </div>
</div>
<!-- Right panel with cover effect-->
<div class="panel panel-right panel-cover">
  <div class="content-block">
    <p>Right panel content goes here</p>
  </div>
</div>
<!-- Views-->
<div class="views">
  <!-- Your main view, should have "view-main" class-->
  <div class="view view-main">
    <!-- Top Navbar-->
    <div class="navbar">
      <div class="navbar-inner">
        <!-- We have home navbar without left link-->
        <div class="left">
          <!-- Right link contains only icon - additional "icon-only" class-->
          <a href="#" class="link icon-only open-panel">
            <i class="icon icon-f7"></i></a>
        </div>
        <div class="center sliding">主页</div>
        <div class="right">
          <!-- Right link contains only icon - additional "icon-only" class-->
          <a href="#" class="link icon-only open-panel" data-panel="right">
            <i class="icon icon-bars"></i></a>
        </div>

      </div>
    </div>


    <!-- Pages, because we need fixed-through navbar and toolbar, it has additional appropriate classes-->
    <div class="pages navbar-through toolbar-through">
      <!-- Page, data-page contains page name-->
      <div data-page="index" class="page">
        <!-- Scrollable page content-->
        <div class="page-content">

          <!--banner-->
          <div class="swiper-container">
            <div class="swiper-wrapper">
              <div class="swiper-slide"><img src="//gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i1/TB1n3rZHFXXXXX9XFXXXXXXXXXX_!!0-item_pic.jpg_320x320q60.jpg" width="200px" height="120px" alt=""></div>
              <div class="swiper-slide"><img src="//gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i4/TB10rkPGVXXXXXGapXXXXXXXXXX_!!0-item_pic.jpg_320x320q60.jpg" width="200px" height="120px" alt=""></div>
              <div class="swiper-slide"><img src="//gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i1/TB1kQI3HpXXXXbSXFXXXXXXXXXX_!!0-item_pic.jpg_320x320q60.jpg" width="200px" height="120px" alt=""></div>
            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Add Arrows -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
          </div>

          <div class="content-block-title">Welcome To My Awesome App</div>
          <div class="content-block">
            <div class="content-block-inner">
              <p>Couple of worlds here because my app is so awesome!</p>
              <p>Duis sed erat ac eros ultrices pharetra id ut tellus. Praesent rhoncus enim ornare ipsum aliquet ultricies. Pellentesque sodales erat quis elementum sagittis.</p>
            </div>
          </div>
          <div class="content-block-title">What about simple navigation?</div>
          <div class="list-block">
            <ul>
              <li><a href="about.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">About</div>
                  </div>
                </div></a></li>
              <li><a href="services.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Services</div>
                  </div>
                </div></a></li>
              <li><a href="form.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </div></a></li>
              <li><a href="form.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </div></a></li>
              <li><a href="form.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </div></a></li><li><a href="form.html" class="item-link">
              <div class="item-content">
                <div class="item-inner">
                  <div class="item-title">Form</div>
                </div>
              </div></a></li>
              <li><a href="form.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </div></a></li>
              <li><a href="form.html" class="item-link">
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </div></a></li><li><a href="form.html" class="item-link">
              <div class="item-content">
                <div class="item-inner">
                  <div class="item-title">Form</div>
                </div>
              </div></a></li>


            </ul>
          </div>
          <div class="content-block-title">Side panels</div>
          <div class="content-block">
            <div class="row">
              <div class="col-50"><a href="#" data-panel="left" class="button open-panel">Left Panel</a></div>
              <div class="col-50"><a href="#" data-panel="right" class="button open-panel">Right Panel</a></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Bottom Toolbar-->


    <div class="toolbar tabbar">
      <div class="toolbar-inner">
        <a href="#tab1" class="tab-link active">
          <i class="icon icon-form-comment"></i>
        </a>
        <a href="#tab2" class="tab-link">
          <i class="icon icon-camera"></i>
        </a>
        <a href="#tab3" class="tab-link">
          <i class="icon icon-plus"></i>
        </a>
        <a href="#tab4" class="tab-link">
          <i class="icon icon-form-settings"></i>
        </a>
      </div>
    </div>
  </div>
</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="theme/dist/js/framework7.min.js"></script>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<!-- Path to your app js-->
<script type="text/javascript" src="theme/dist/js/my-app.js"></script>
<script>
  var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    nextButton: '.swiper-button-next',
    prevButton: '.swiper-button-prev',
    paginationClickable: true,
    spaceBetween: 30,
    centeredSlides: true,
    autoplay: 2500,
    autoplayDisableOnInteraction: false
  });
</script>
</body>
</html>