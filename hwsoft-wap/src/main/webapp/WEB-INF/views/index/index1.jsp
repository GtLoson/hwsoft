<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
<head>
  <b:base></b:base>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>我的生活</title>
  <meta name="viewport" content="initial-scale=1, maximum-scale=1">
  <link rel="shortcut icon" href="/favicon.ico">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">

  <link rel="stylesheet" href="theme/dist/css/sm.min.css">
  <link rel="stylesheet" href="theme/dist/css/swiper.min.css">
<style>

  .border{
    border:1px solid red;
  }
  .left{
    width:40%;
    height:80px;
    float:left;
  }

  .right_up{
    width:60%;
    height:40px;
    float:left;
  }

  .right_down{
    width:60%;
    height:40px;
    float:left;
  }
  .show_frame{
    margin-top:120px;
  }
  .product_list{
    margin-top:240px;
  }
</style>

</head>
<body>
<div class="page-group">
  <div class="page page-current">
    <header class="bar bar-nav">
      <a href="#"><span class="icon icon-me pull-left" data-panel='#panel-left'></span></a>
      <a href="#"><span class="icon icon-menu pull-right"></span></a>
      <h1 class="title">主页</h1>
    </header>
    <!-- Swiper -->
    <div class="content">
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
    </div>
   <!--展示窗口-->
    <div class="content show_frame">
      <div class = "left"><a href="#">left</a></div>
      <div class = "right_up"><a href="#">rightUp</a></div>
      <div class = "right_down"><a href="#">rightDown</a></div>
    </div>
    <!--商品列表-->
      <div class="list-block cards-list product_list">
        <ul>
          <li class="card">
            <div class="card-header">卡头</div>
            <div class="card-content">
              <div class="card-content-inner">卡内容</div>
            </div>
            <div class="card-footer">卡脚</div>
          </li>
          <li class="card">
            <div class="card-header">卡头</div>
            <div class="card-content">
              <div class="card-content-inner">卡内容</div>
            </div>
            <div class="card-footer">卡脚</div>
          </li>
          <li class="card">
            <div class="card-header">卡头</div>
            <div class="card-content">
              <div class="card-content-inner">卡内容</div>
            </div>
            <div class="card-footer">卡脚</div>
          </li>
        </ul>
      </div>
    <!--分类列表-->



      <!--navbar 工具栏-->
    <div class="content">
    <nav class="bar bar-tab">
      <a class="tab-item external active" href="#">
        <span class="icon icon-home"></span>
        <span class="tab-label">主页</span>
      </a>
      <a class="tab-item external" href="#">
        <span class="icon icon-me"></span>
        <span class="tab-label">朋友</span>
        <span class="badge">2</span>
      </a>
      <a class="tab-item external" href="#">
        <span class="icon icon-star"></span>
        <span class="tab-label">动态</span>
      </a>
      <a class="tab-item external" href="#">
        <span class="icon icon-cart"></span>
        <span class="tab-label">订单</span>
      </a>
      <a class="tab-item external" href="#">
        <span class="icon icon-settings"></span>
        <span class="tab-label">我的</span>
      </a>
    </nav>
  </div>
    </div>


  <div class="panel-overlay"></div>
  <!-- Left Panel with Reveal effect -->
  <div class="panel panel-left panel-reveal theme-dark" id='panel-left'>
    <div class="content-block">
      <p>这是一个侧栏</p>
      <p>你可以在这里放用户设置页面</p>
      <p><a href="#" class="close-panel">关闭</a></p>
    </div>
    <div class="list-block">
      <!-- .... -->
    </div>
  </div>
  <div class="panel panel-right panel-reveal">
    <div class="content-block">
      <p>这是一个侧栏</p>
      <!-- Click on link with "close-panel" class will close panel -->
      <p><a href="#" class="close-panel">关闭</a></p>
    </div>
  </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='theme/dist/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='theme/dist/js/swiper.min.js' charset='utf-8'></script>
</body>
</html>

<!-- Initialize Swiper -->
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


  $(document).ready(function(){
    $('nav a').on("click",function(){
      $('nav a').removeClass("active");
      $(this).addClass("active");
    });
  });
 </script>