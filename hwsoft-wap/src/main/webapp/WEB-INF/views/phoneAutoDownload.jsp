<%--
  Created by IntelliJ IDEA.
  User: shaodongying
  Date: 15/4/20
  Time: 上午12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
  <title></title>
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
  open_or_download_app();
</script>
<!--<div style="display:none;">-->
<!--<script type="text/javascript">-->
<!--var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");-->
<!--document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fb9ca1fb98a8894d96844f49ab755e654' type='text/javascript'%3E%3C/script%3E"));-->
<!--</script>-->
<!--<script type="text/javascript">-->
<!--var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");-->
<!--document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2acb42d99110e1171f6af85efa46b0d1' type='text/javascript'%3E%3C/script%3E"));-->
<!--</script>-->
<!--</div>-->

</body>
</html>
