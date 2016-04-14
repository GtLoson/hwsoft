<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <b:base></b:base>
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
<!-- Right panel with cover effect-->
<div class="panel panel-left panel-cover">
  <div class="content-block content-block-style">

    <div class="head_avatar">
      <img src="">
    </div>
    <div class="list-block list-block-gb">
      <ul class="bgcolor">
        <li class="item-content">
          <div class="item-media"><i class="icon icon-f7"></i></div>
          <div class="item-inner">
            <div class="item-title">个性设置</div>
            <div class="item-after"></div>
          </div>
        </li>
        <li class="item-content">
          <div class="item-media"><i class="icon icon-f7"></i></div>
          <div class="item-inner">
            <div class="item-title">我的消息</div>
            <div class="item-after"><span class="badge">5</span></div>
          </div>
        </li>
        <li class="item-content">
          <div class="item-media"><i class="icon icon-f7"></i></div>
          <div class="item-inner">
            <div class="item-title">模式切换</div>
            <div class="item-after"></div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</div>
<!-- Links popover -->
<div class="popover popover-links">
  <div class="popover-angle"></div>
  <div class="popover-inner">
    <div class="list-block">
      <ul>
        <li><a href="#" class="list-button item-link">我的地址</a></li>
        <li><a href="#" class="list-button item-link">购物车</a></li>
        <li><a href="#" class="list-button item-link">订单跟踪</a></li>
        <li><a href="#" class="list-button item-link">我的设置</a></li>
      </ul>
    </div>
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
          <a href="#" class="open-popover" data-popover=".popover-links"><i class="icon icon-bars"></i></a>
        </div>

      </div>
    </div>


    <!-- Pages, because we need fixed-through navbar and toolbar, it has additional appropriate classes-->
    <div class="pages navbar-through toolbar-through">
      <!-- Page, data-page contains page name-->
      <div data-page="index" class="page">
        <!-- Scrollable page content-->
        <div class="page-content .pull-to-refresh-content">

          <!--banner-->
          <div class="swiper-container">
            <div class="swiper-wrapper" id = "banners">
              <%--<script id="banner-template" type="text/x-handlebars-template">--%>
              <%--{{#banners}}--%>
              <c:forEach items="${banners}" var="item">
                <div class="swiper-slide"><img src="${item.imageURI}" width="100%" height="120px" alt=""></div>
              </c:forEach>
              <%--{{/banners}}--%>
              <%--</script>--%>
            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Add Arrows -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
          </div>

            <div class="man">
              <div class="man-title">
                nv
              </div>

              <div>
                <div class="man-left">lf </div>
                <div class="man-right">
                  1
                </div>
              </div>
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
<script type="text/javascript" src = "theme/dist/js/handlebars.min.js"></script>
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