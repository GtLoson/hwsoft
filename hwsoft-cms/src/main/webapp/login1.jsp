<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>后台管理系统</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/api/theme/images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="theme/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="theme/stylesheets/theme.css">
    <link rel="stylesheet" type="text/css" href="theme/stylesheets/style.css">
    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>
  </head>
  <script type="text/javascript">
  	//获取父类框架
  	var ele = window.parent.document.getElementById("indexTopPage");
  	if(ele != null){
  		window.parent.location = "login.jsp";
  	}
  </script>
  <body> 
    <div class="navbar">
        <div class="navbar-inner">
                <%--<a class="brand" href="javascript:alert('火热开发中');"><span class="first">钱妈妈</span><span class="second">&nbsp;Company</span></a>--%>
        </div>
    </div>
        <div class="row-fluid">
    <div class="dialog">
        <div class="block">
            <p class="block-heading">登&nbsp;&nbsp;录</p>
            <div class="block-body">
                <form id="loginform" action="login" method="post">
                    <label>用户名称:</label>
                    <input type="text" class="span12" name="username" id="username">
                    <label>密码:</label>
                    <input type="password" class="span12" name="password" id="password" >
                    <!--
                    <label>验证码:</label>
                    <input type="text" class="span12" name="validatecode" id="validatecode" style="width:100px">
                    <img src="../api/image.jsp" id="randImage" border="0"  name="randImage" alt="验证码" style="cursor:pointer; width:60px; height:23px;margin-bottom:7px;margin-right:10px;" onclick="loadimage();"/>
                    -->
                    <a href="#" class="btn btn-primary pull-right" onclick="login();">登录</a>
                    	<%
						  	String msg = request.getAttribute("error_msg") == null ? null : request.getAttribute("error_msg").toString();
						   	if(msg == null || "".equals(msg)){
						   		Object obj = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
						   		
						   		if(obj != null){
						   			msg = ((org.springframework.security.core.AuthenticationException)obj).getMessage();
						   			if("Bad credentials".equals(msg)){
						   				msg = "请输入正确的用户名称和密码";
						   			}
						   			if("User is disabled".equals(msg)){
						   				msg = "当前用户禁止登录";
						   			}
						   			if(msg == null || "".equals(msg)){
						   				msg = "登录失败，请重试";
						   			}
						   		}
						   	}
						 %>
                    <label class="error_msg"><%=msg == null ? "" : msg %></label>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	function login(){
		var username = document.getElementById("username").value;
		if(username == null || username == ''){
			alert('请输入用户名称');
			return;
		}
		var password = document.getElementById('password').value;
		if(password == null || password == ''){
			alert('请输入用户密码');
			return;
		}
		
		document.getElementById("loginform").submit();
	}
	function loadimage(){
	    document.getElementById("randImage").src = "./image.jsp?"+Math.random();
	}
</script>
  </body>
</html>


