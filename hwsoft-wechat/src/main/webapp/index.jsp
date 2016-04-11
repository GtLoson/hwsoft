<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>WeUI</title>
    <link rel="stylesheet" href="theme/weui/weui.css"/>
    <link rel="stylesheet" href="theme/weui/index.css"/>
</head>
<body ontouchstart>
<div class="container" id="container">

</div>
<script type="text/html" id="tpl_tabbar">
    <div class="weui_tab">
        <div class="weui_tab_bd">
            <div class="hd">
                <h1 class="page_title">WeUI</h1>
                <p class="page_desc">为微信Web服务量身设计</p>
            </div>
            <div class="bd">
                <div class="weui_grids">
                    <a href="#/button" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_button"></i>
                        </div>
                        <p class="weui_grid_label">

                        </p>
                    </a>
                    <a href="#/cell" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_cell"></i>
                        </div>
                        <p class="weui_grid_label">
                            Cell
                        </p>
                    </a>
                    <a href="#/toast" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_toast"></i>
                        </div>
                        <p class="weui_grid_label">
                            Toast
                        </p>
                    </a>
                    <a href="#/dialog" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_dialog"></i>
                        </div>
                        <p class="weui_grid_label">
                            Dialog
                        </p>
                    </a>
                    <a href="#/progress" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_progress"></i>
                        </div>
                        <p class="weui_grid_label">
                            Progress
                        </p>
                    </a>
                    <a href="#/msg" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_msg"></i>
                        </div>
                        <p class="weui_grid_label">
                            Msg
                        </p>
                    </a>
                    <a href="#/article" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_article"></i>
                        </div>
                        <p class="weui_grid_label">
                            Article
                        </p>
                    </a>
                    <a href="#/actionsheet" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_actionSheet"></i>
                        </div>
                        <p class="weui_grid_label">
                            ActionSheet
                        </p>
                    </a>
                    <a href="#/icons" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_icons"></i>
                        </div>
                        <p class="weui_grid_label">
                            Icons
                        </p>
                    </a>
                    <a href="#/panel" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_panel"></i>
                        </div>
                        <p class="weui_grid_label">
                            Panel
                        </p>
                    </a>
                    <a href="#/tab" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_tab"></i>
                        </div>
                        <p class="weui_grid_label">
                            Tab
                        </p>
                    </a>
                    <a href="#/searchbar" class="weui_grid">
                        <div class="weui_grid_icon">
                            <i class="icon icon_search_bar"></i>
                        </div>
                        <p class="weui_grid_label">
                            SearchBar
                        </p>
                    </a>
                </div>
            </div>
        </div>
        <div class="weui_tabbar">
            <a href="javascript:;" class="weui_tabbar_item weui_bar_item_on">
                <div class="weui_tabbar_icon">
                    <img src="theme/weui/images/icon_nav_button.png" alt="">
                </div>
                <p class="weui_tabbar_label">微信</p>
            </a>
            <a href="javascript:;" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="theme/weui/images/icon_nav_msg.png" alt="">
                </div>
                <p class="weui_tabbar_label">通讯录</p>
            </a>
            <a href="javascript:;" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="theme/weui/images/icon_nav_article.png" alt="">
                </div>
                <p class="weui_tabbar_label">发现</p>
            </a>
            <a href="javascript:;" class="weui_tabbar_item">
                <div class="weui_tabbar_icon">
                    <img src="theme/weui/images/icon_nav_cell.png" alt="">
                </div>
                <p class="weui_tabbar_label">我</p>
            </a>
        </div>
    </div>
</script>
<script src="theme/weui/zepto.min.js"></script>
<script src="theme/weui/router.min.js"></script>
<script src="theme/weui/index.js"></script>
</body>
</html>

