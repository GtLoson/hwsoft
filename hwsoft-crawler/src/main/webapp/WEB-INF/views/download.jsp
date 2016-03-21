<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>钱妈妈-钱生钱，好方便</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="alternate icon" type="image/png" href="/wap/theme/assets/i/favicon.ico">
  <link rel="stylesheet" href="/wap/theme/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="/wap/theme/assets/css/common.css">
  <script src="/wap/theme/assets/js/jquery.min.js"></script>
  <script src="/wap/theme/assets/js/amazeui.js"></script>
  
</head>
<body>

<script language="javascript">
  function open_or_download_app() {

    if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
      // 判断useragent，当前设备为ios设备
      var loadDateTime = new Date();
      // 设置时间阈值，在规定时间里面没有打开对应App的话，直接去App store进行下载。
      window.setTimeout(function() {
                var timeOutDateTime = new Date();
                if (timeOutDateTime - loadDateTime < 5000) {
                  window.location = "https://itunes.apple.com/cn/app/qian-ma-ma/id940134457?mt=8";
                } else {
                  window.close();
                }
              },
              25);
    } else if (navigator.userAgent.match(/android/i)) {
      window.location = "http://112.124.11.227:9080/mgmt/app/download";// Android端URL Schema
    }
  }

  function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
      return true;
    } else {
      return false;
    }
  }
  var isWeixin = is_weixin();
  var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
  var weixinTip = $('<div id="weixinTip"><p><img src="/wap/theme/assets/i/live_weixin.png" alt="微信打开"/></p></div>');

  if(isWeixin){
    $("body").append(weixinTip);
  }
  $("#weixinTip").css({
    "position":"fixed",
    "left":"0",
    "top":"0",
    "height":winHeight,
    "width":"100%",
    "z-index":"1000",
    "background-color":"rgba(0,0,0,0.8)",
    "filter":"alpha(opacity=80)"
  });
  $("#weixinTip p").css({
    "text-align":"center",
    "margin-top":"10%",
    "padding-left":"5%",
    "padding-right":"5%"
  });
</script>
<!-- Header -->
<%--<header data-am-widget="header" class="am-header am-header-bg">--%>
  <%--&lt;%&ndash;<div class="am-header-left am-header-nav">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a href="javascript: void(0)" class="am-icon-chevron-left" data-am-close="pureview"></a>&ndash;%&gt;--%>
  <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
  <%--&lt;%&ndash;<h1 class="am-header-title">&ndash;%&gt;--%>
    <%--&lt;%&ndash;钱生钱 好方便&ndash;%&gt;--%>
  <%--&lt;%&ndash;</h1>&ndash;%&gt;--%>
  <%--&lt;%&ndash;<div class="am-header-right am-header-nav">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a href="#right-link" class="">账户中心</a>&ndash;%&gt;--%>
  <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
   <%----%>
<%--</header>--%>
<!-- content -->
<figure data-am-widget="figure" class="am am-figure-banner am-figure-default">
  <img src="/wap/theme/assets/i/banner1.gif"  alt="钱生钱，好方便" />
  <img src="/wap/theme/assets/i/banner2.png"  alt="钱生钱，好方便" />
</figure>
<div class="am-g">
  <div class="am-u-sm-12 am-download">
    <p class="am-version">最新版本：v1.4.3</p>
    <a  class="am-btn am-btn-block qmm-btn" onclick="open_or_download_app()">马上下载</a>
    <p class="am-notice">
      <i class="am-icon-shield"></i>账户资金安全由“平安保险”全额承保
    </p>
    <hr data-am-widget="divider" style="" class="am-divider am-divider-default"/>
    <p class="am-copyright">上海素海金融信息服务有限公司 版权所有<br>沪ICP备14039777号-1</p>
  </div>
</div>

</body>
</html>
