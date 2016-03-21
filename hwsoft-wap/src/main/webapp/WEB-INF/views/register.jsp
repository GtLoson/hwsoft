<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册-钱生钱，好方便</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="alternate icon" type="image/png" href="/wap/theme/assets/i/favicon.ico">
    <link rel="stylesheet" href="/wap/theme/assets/css/amazeui.css"/>
    <link rel="stylesheet" href="/wap/theme/assets/css/common.css">
</head>
<body style="background:#f0f2f5;">
<!-- Header -->
<header data-am-widget="header" class="am-header am-header-bg">
    <%--<div class="am-header-left am-header-nav">--%>
        <%--<a href="javascript: void(0)" class="am-icon-chevron-left" data-am-close="pureview"></a>--%>
    <%--</div>--%>
    <h1 class="am-header-title">
        钱妈妈注册
    </h1>
    <%--<div class="am-header-right am-header-nav">--%>
        <%--<a href="#right-link" class="">账户中心</a>--%>
    <%--</div>--%>
</header>
<!-- content -->
<div class="am-g" style="background:#f0f2f5;">
    <form class="am-form am-qmm-form" id="registerform" name="registerform" method="post" action="/wap/registerForWap" autocomplete="off"  >
        <fieldset>
            <div class="am-form-group">
                <div class="am-input-group">
                    <span class="am-input-group-label" ><i class="am-icon-mobile"></i></span>
                    <input type="text" class="J_input " name="mobile" id="mobile" placeholder="输入您的手机号码">

                </div>
            </div>

            <div class="am-form-group">
                <div class="am-input-group">
                    <input type="text" class="J_input am-form-field  J_remoteValidateCode" placeholder="输入短信验证码" name="code">
                    <span class="am-input-group-label" id="sendremotecodebutton" style="background: #e7316a; color:#fff;">获取验证码</span>
                    <input type="hidden" name="channelId" id="channelId" value=${channelId}>
                </div>
            </div>

            <div class="am-form-group">
                <div class="am-input-group">
                    <span class="am-input-group-label"><i class="am-icon-lock"></i></span>
                    <input type="password" placeholder="请输入登录密码" class="J_input " id="password" name="password">
                </div>
            </div>

            <div class="am-form-group">
                <div class="am-input-group">
                    <span class="am-input-group-label"><i class="am-icon-lock"></i></span>
                    <input type="password" placeholder="请再次输入密码" class="J_input " name="confirmPassword" id="confirmPassword">
                </div>
            </div>
            <div class="am-form-group">
                <div class="am-input-group">
                    <span class="am-input-group-label" ><i class="am-icon-mobile"></i></span>
                    <input type="text" class="J_input " name="recommend" id="recommend" placeholder="请输入推荐人手机号码">
                </div>
            </div>
            <div class="am-checkbox ">
                <label>
                    <input type="checkbox" checked="checked"> 我同意<a href="javascript:void();" data-am-modal="{target: '#my-popup1'}">《钱妈妈用户使用协议》</a>
                    <div class="am-popup" id="my-popup1">
                        <div class="am-popup-inner">
                            <div class="am-popup-hd">
                                <h4 class="am-popup-title">钱妈妈用户使用协议</h4>
                    <span data-am-modal-close
                          class="am-close">&times;</span>
                            </div>
                            <div class="am-popup-bd">
                                <dl><dd>本网站由上海素海金融信息服务有限公司负责运营（以下本网站均指网站及上海素海金融信息服务有限公司）。在
                                    您注册成为本网站用户前请务必仔细阅读以下条款。若您一旦注册，则您接受本网站的服务必须受以下条款的约束。若您不接受以下条款，请立即离开本网站。</dd>
                                    <dd>本服务协议内容包括以下条款及已经发布的或将来可能发布的各类规则。所有规则为协议不可分割的一部分，与协议正文具有同等法律效力。本协议是由您与本网站共同签订的，适用于您在本网站的全部活动。在您注册成为用户时，您已经阅读、理解并接受本协议的全部条款及各类规则，并承诺遵守中国现行的法律、法规、规章及其他政府规定，如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。</dd>
                                    <dd>本网站有权根据需要不时地制定、修改本协议或各类规则，如本协议及规则有任何变更，一切变更以本网站最新公布条例为准。您应不时地注意本协议及附属规则的变更，若您不同意相关变更，本网站有权不经任何告知终止、中止本协议或者限制您进入本网站的全部或者部分板块且不承担任何法律责任。但该中止、终止或限制行为并不能豁免您在本网站已经进行的交易下所应承担的义务。</dd>
                                    <dd> 您确认本服务协议后，本服务协议即在您和本网站之间产生法律效力。您只要点击协议正本下方的&quot;确认&quot;按钮并按照本网站规定的注册程序成功注册为用户，您的行为既表示同意并签署了本服务协议。本服务协议不涉及您与本网站的其他用户之间因网上交易而产生的法律关系及法律纠纷，但您在此同意将全面接受和履行与本网站其他用户在本网站签订的任何电子法律文本，并承诺按该法律文本享有和/或放弃相应的权利、承担和/或豁免相应的义务。</dd>
                                    <dt>一、使用限制</dt>
                                    <dd> 本网站中的全部内容的版权均属于本网站所有，该等内容包括但不限于文本、数据、文章、设计、源代码、软件、图片、照片及其他全部信息（以下称&quot;网站内 容&quot;）。网站内容受中华人民共和国著作权法及各国际版权公约的保护。未经本网站事先书面同意，您承诺不以任何方式、不以任何形式复制、模仿、传播、出版、公布、展示网站内容，包括但不限于电子的、机械的、复印的、录音录像的方式和形式等。您承认网站内容是属于本网站的财产。未经本网站书面同意，您亦不得将本网站包含的资料等任何内容镜像到任何其他网站或者服务器。任何未经授权对网站内容的使用均属于违法行为，本网站将追究您的法律责任。</dd>
                                    <dd>您承诺合法使用本网站提供的服务及网站内容。禁止在本网站从事任何可能违反中国现行的法律、法规、规章和政府规范性文件的行为或者任何未经授权使用本网站的行为，如擅自进入本网站的未公开的系统、不正当的使用密码和网站的任何内容等。</dd>
                                    <dd>本网站只接受持有中国大陆有效身份证的（不包括香港特区、澳门特区及台湾地区）的18周岁以上的具有完全民事行为能力的自然人成为网站用户。如您不符合资格，请勿注册。本网站保留随时中止或终止您用户资格的权利。</dd>
                                    <dd> 您注册成功后，不得将本网站的用户名转让给第三方或者授权给第三方使用。您确认，您用您的用户名和密码登录本网站后在本网站的一切行为均代表您并由您承担相应的法律后果。</dd>
                                    <dd> 您有义务在注册时提供自己的真实资料，并保证诸如电子邮件地址、联系电话、联系地址、邮政编码等内容的有效性及安全性。如您因网上交易与其他用户产生诉讼的，其他用户有权通过司法部门要求网站提供相关资料。</dd>
                                    <dd> 经国家生效法律文书或行政处罚决定确认您存在违法行为，或者本网站有足够事实依据可以认定您存在违法或违反本服务协议的行为的或者您借款逾期未还的，本网站有权在本网站及互联网络上公布您的违法、违约行为，并有权将该内容记入任何与您相关的信用资料和档案。</dd>
                                    <dt>二、涉及第三方网站</dt>
                                    <dd> 本网站不能保证也没有义务保证第三方网站上的信息的真实性和有效性。您应按照第三方网站的服务协议使用第三方网站，而不是按照本协议。第三方网站的内容、产品、广告和其他任何信息均由您自行判断并承担风险，而与本网站无关。</dd>
                                    <dt> 三、不保证</dt>
                                    <dd>本网站提供的服务中不带有对本网站中的任何用户、任何交易的任何保证或条件，无论是明示、默示或法定的，因此本网站及其股东、创建人、高级职员、董事、代理人、关联公司、母公司、子公司和雇员（以下称&quot;本网站方&quot;）不保证网站内容的真实性、充分性、及时性、可靠性、完整性和有效性，并且免除任何由此引起的 法律责任。</dd>
                                    <dt> 四、责任限制</dt>
                                    <dd> 在任何情况下，本网站方对您使用本网站服务而产生的任何形式的直接或间接损失均不承担法律责任，包括但不限于资金损失、利润损失、营业中断损失等。因为本网站或者涉及的第三方网站的设备、系统存在缺陷或者因为计算机病毒造成的损失，本网站均不负责赔偿，您的补救措施只能是与本网站终止本协议并停止使用本网站。但是，中国现行法律、法规另有规定的除外。</dd>
                                    <dt> 五、网站内容监测</dt>
                                    <dd> 本网站没有义务监测网站内容，但是您确认并同意本网站有权不时地根据法律、法规、政府要求透露、修改或者删除必要的、合适的信息，以便更好地运营本网站并保护自身及本网站的其他合法用户。</dd>
                                    <dt> 六、个人信息的使用</dt>
                                    <dd> 本网站对于您提供的、自行收集到的、经认证的个人信息将按照本网站的隐私规则予以保护、使用或者披露。当您作为借入人借款逾期未还时，作为借出人的其他用户可以采取发布您的个人信息的方式追索债权，但本网站对该等借出人的行为免责。</dd>
                                    <dt> 七、索赔</dt>
                                    <dd> 由于您违反本协议或任何法律、法规或侵害第三方的权利，而引起第三方对本网站提出的任何形式的索赔、要求、诉讼。本网站有权向您追偿相关损失，包括但不限于本网站法律费用、名誉损失、及向第三方支付的补偿金等。</dd>
                                    <dt> 八、终止</dt>
                                    <dd> 除非本网站终止本协议或者您申请终止本协议且经本网站同意，否则本协议始终有效。本网站有权在不通知您的情况下在任何时间终止本协议或者限制您使用本网站。但本网站的终止行为不能免除您根据本服务协议或在本网站生成的其他协议项下的还未履行完毕的义务。</dd>
                                    <dt>九、适用法律和管辖</dt>
                                    <dd> 因本网站提供服务所产生的争议均适用中华人民共和国法律，并由本网站住所地的人民法院管辖。</dd>
                                    <dt> 十、附加条款</dt>
                                    <dd>在本网站的某些部分或页面中可能存在除本协议以外的单独的附加服务条款，当这些条款存在冲突时，在该些部分和页面中附加条款优先适用。</dd>
                                    <dt>十一、条款的独立性</dt>
                                    <dd>若本协议的部分条款被认定为无效或者无法实施时，本协议中的其他条款仍然有效。</dd>
                                    <dt>十二、有关著作权的投诉</dt>
                                    <dd>若您发现本网站中有侵犯您著作权的情形，你可以将投诉信发送到本网站指定的如下机构：</dd>
                                    <dd style="text-align:right;">钱妈妈网站<br />
                                        2015年04月22日</dd>
                                </dl>
                            </div>
                        </div>
                    </div>

                    <br><a href="javascript:void();" data-am-modal="{target: '#my-popup2'}">《使用条款和隐私政策协议》</a>
                    <div class="am-popup" id="my-popup2">
                        <div class="am-popup-inner">
                            <div class="am-popup-hd">
                                <h4 class="am-popup-title">使用条款和隐私政策协议</h4>
                    <span data-am-modal-close
                          class="am-close">&times;</span>
                            </div>
                            <div class="am-popup-bd">
                                <dl><dd>本网站由上海素海金融信息服务有限公司负责运营（以下本网站均指网站及上海素海金融信息服务有限公司）。在
                                    您注册成为本网站用户前请务必仔细阅读以下条款。若您一旦注册，则您接受本网站的服务必须受以下条款的约束。若您不接受以下条款，请立即离开本网站。</dd>
                                    <dd>本服务协议内容包括以下条款及已经发布的或将来可能发布的各类规则。所有规则为协议不可分割的一部分，与协议正文具有同等法律效力。本协议是由您与本网站共同签订的，适用于您在本网站的全部活动。在您注册成为用户时，您已经阅读、理解并接受本协议的全部条款及各类规则，并承诺遵守中国现行的法律、法规、规章及其他政府规定，如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。</dd>
                                    <dd>本网站有权根据需要不时地制定、修改本协议或各类规则，如本协议及规则有任何变更，一切变更以本网站最新公布条例为准。您应不时地注意本协议及附属规则的变更，若您不同意相关变更，本网站有权不经任何告知终止、中止本协议或者限制您进入本网站的全部或者部分板块且不承担任何法律责任。但该中止、终止或限制行为并不能豁免您在本网站已经进行的交易下所应承担的义务。</dd>
                                    <dd> 您确认本服务协议后，本服务协议即在您和本网站之间产生法律效力。您只要点击协议正本下方的&quot;确认&quot;按钮并按照本网站规定的注册程序成功注册为用户，您的行为既表示同意并签署了本服务协议。本服务协议不涉及您与本网站的其他用户之间因网上交易而产生的法律关系及法律纠纷，但您在此同意将全面接受和履行与本网站其他用户在本网站签订的任何电子法律文本，并承诺按该法律文本享有和/或放弃相应的权利、承担和/或豁免相应的义务。</dd>
                                    <dt>一、使用限制</dt>
                                    <dd> 本网站中的全部内容的版权均属于本网站所有，该等内容包括但不限于文本、数据、文章、设计、源代码、软件、图片、照片及其他全部信息（以下称&quot;网站内 容&quot;）。网站内容受中华人民共和国著作权法及各国际版权公约的保护。未经本网站事先书面同意，您承诺不以任何方式、不以任何形式复制、模仿、传播、出版、公布、展示网站内容，包括但不限于电子的、机械的、复印的、录音录像的方式和形式等。您承认网站内容是属于本网站的财产。未经本网站书面同意，您亦不得将本网站包含的资料等任何内容镜像到任何其他网站或者服务器。任何未经授权对网站内容的使用均属于违法行为，本网站将追究您的法律责任。</dd>
                                    <dd>您承诺合法使用本网站提供的服务及网站内容。禁止在本网站从事任何可能违反中国现行的法律、法规、规章和政府规范性文件的行为或者任何未经授权使用本网站的行为，如擅自进入本网站的未公开的系统、不正当的使用密码和网站的任何内容等。</dd>
                                    <dd>本网站只接受持有中国大陆有效身份证的（不包括香港特区、澳门特区及台湾地区）的18周岁以上的具有完全民事行为能力的自然人成为网站用户。如您不符合资格，请勿注册。本网站保留随时中止或终止您用户资格的权利。</dd>
                                    <dd> 您注册成功后，不得将本网站的用户名转让给第三方或者授权给第三方使用。您确认，您用您的用户名和密码登录本网站后在本网站的一切行为均代表您并由您承担相应的法律后果。</dd>
                                    <dd> 您有义务在注册时提供自己的真实资料，并保证诸如电子邮件地址、联系电话、联系地址、邮政编码等内容的有效性及安全性。如您因网上交易与其他用户产生诉讼的，其他用户有权通过司法部门要求网站提供相关资料。</dd>
                                    <dd> 经国家生效法律文书或行政处罚决定确认您存在违法行为，或者本网站有足够事实依据可以认定您存在违法或违反本服务协议的行为的或者您借款逾期未还的，本网站有权在本网站及互联网络上公布您的违法、违约行为，并有权将该内容记入任何与您相关的信用资料和档案。</dd>
                                    <dt>二、涉及第三方网站</dt>
                                    <dd> 本网站不能保证也没有义务保证第三方网站上的信息的真实性和有效性。您应按照第三方网站的服务协议使用第三方网站，而不是按照本协议。第三方网站的内容、产品、广告和其他任何信息均由您自行判断并承担风险，而与本网站无关。</dd>
                                    <dt> 三、不保证</dt>
                                    <dd>本网站提供的服务中不带有对本网站中的任何用户、任何交易的任何保证或条件，无论是明示、默示或法定的，因此本网站及其股东、创建人、高级职员、董事、代理人、关联公司、母公司、子公司和雇员（以下称&quot;本网站方&quot;）不保证网站内容的真实性、充分性、及时性、可靠性、完整性和有效性，并且免除任何由此引起的 法律责任。</dd>
                                    <dt> 四、责任限制</dt>
                                    <dd> 在任何情况下，本网站方对您使用本网站服务而产生的任何形式的直接或间接损失均不承担法律责任，包括但不限于资金损失、利润损失、营业中断损失等。因为本网站或者涉及的第三方网站的设备、系统存在缺陷或者因为计算机病毒造成的损失，本网站均不负责赔偿，您的补救措施只能是与本网站终止本协议并停止使用本网站。但是，中国现行法律、法规另有规定的除外。</dd>
                                    <dt> 五、网站内容监测</dt>
                                    <dd> 本网站没有义务监测网站内容，但是您确认并同意本网站有权不时地根据法律、法规、政府要求透露、修改或者删除必要的、合适的信息，以便更好地运营本网站并保护自身及本网站的其他合法用户。</dd>
                                    <dt> 六、个人信息的使用</dt>
                                    <dd> 本网站对于您提供的、自行收集到的、经认证的个人信息将按照本网站的隐私规则予以保护、使用或者披露。当您作为借入人借款逾期未还时，作为借出人的其他用户可以采取发布您的个人信息的方式追索债权，但本网站对该等借出人的行为免责。</dd>
                                    <dt> 七、索赔</dt>
                                    <dd> 由于您违反本协议或任何法律、法规或侵害第三方的权利，而引起第三方对本网站提出的任何形式的索赔、要求、诉讼。本网站有权向您追偿相关损失，包括但不限于本网站法律费用、名誉损失、及向第三方支付的补偿金等。</dd>
                                    <dt> 八、终止</dt>
                                    <dd> 除非本网站终止本协议或者您申请终止本协议且经本网站同意，否则本协议始终有效。本网站有权在不通知您的情况下在任何时间终止本协议或者限制您使用本网站。但本网站的终止行为不能免除您根据本服务协议或在本网站生成的其他协议项下的还未履行完毕的义务。</dd>
                                    <dt>九、适用法律和管辖</dt>
                                    <dd> 因本网站提供服务所产生的争议均适用中华人民共和国法律，并由本网站住所地的人民法院管辖。</dd>
                                    <dt> 十、附加条款</dt>
                                    <dd>在本网站的某些部分或页面中可能存在除本协议以外的单独的附加服务条款，当这些条款存在冲突时，在该些部分和页面中附加条款优先适用。</dd>
                                    <dt>十一、条款的独立性</dt>
                                    <dd>若本协议的部分条款被认定为无效或者无法实施时，本协议中的其他条款仍然有效。</dd>
                                    <dt>十二、有关著作权的投诉</dt>
                                    <dd>若您发现本网站中有侵犯您著作权的情形，你可以将投诉信发送到本网站指定的如下机构：</dd>
                                    <dd style="text-align:right;">钱妈妈网站<br />
                                        2015年04月22日</dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </label>
            </div>

            <button type="button" class="am-btn am-btn-block qmm-btn J_reqister disable"><span class="am-icon-spinner am-icon-spin J_load" style="display:none;"></span>&nbsp;提交</button>


        </fieldset>
    </form>
    <p class="am-copyright">注册送1000钱豆，首次投资再送10元现金<span>钱豆可以兑换话费哦！</span></p>
</div>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-bd"></div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" id="feeds">确定</span>
        </div>
    </div>
</div>
<script src="/wap/theme/assets/js/jquery.min.js"></script>
<script src="/wap/theme/assets/js/amazeui.js"></script>
<script src="/wap/theme/assets/js/jquery.form.js"></script>
<script type="text/javascript">
    var sendremotecodebuttonHtml = $("#sendremotecodebutton").html();
    $(".J_reqister").bind("click",function(){
        /*  alert();*/
        var mobile = isMobile($("#mobile").val());
        var password = isPassWord($("#password").val());
        var confirmPassword = $("#confirmPassword").val();
        var recommendMobile = $("#recommend").val();
        
        if(mobile == false){
            $("#my-alert .am-modal-bd").text("您输入的手机号码有误！");
            $("#my-alert").modal("open");
            return;
        }else if($(".J_remoteValidateCode").val() == "" || $(".J_remoteValidateCode").val() == null){
            $("#my-alert .am-modal-bd").text("您输入的验证码有误！");
            $("#my-alert").modal("open");
            return;
        }else if(password == false){
            $("#my-alert .am-modal-bd").text("您输入的密码有误！");
            $("#my-alert").modal("open");
            return;
        }else if(confirmPassword != $("#password").val()){
            $("#my-alert .am-modal-bd").text("两次密码不一致");
            $("#my-alert").modal("open");
            return;
        }else if(recommendMobile.length > 0 && isMobile(recommendMobile) == false){
            $("#my-alert .am-modal-bd").text("您输入的推荐人手机号码有误！");
            $("#my-alert").modal("open");
            return;
        }else{
            $(".J_reqister").attr("type","submit");
            $('#registerform').ajaxForm(options);
        }

    });

    //手机号验证
    function isMobile(value){
        if(value == null || '' == value) return false;
        value = $.trim(value);
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return (length == 11 && mobile.test(value));
    }
    //密码验证
    function isPassWord(value){
        if(value == null || '' == value) return false;
        return /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,12}$/.test(value);
    }

    //ajaxform
    var options = {
        beforeSubmit : function() {
            $('.J_reqister').attr('disabled','disabled');
            $(".J_load").show();
        },
        error : function(data) {
            var text = data.message;
            $("#my-alert .am-modal-bd").text(text);
            $("#my-alert").modal("open");
            $('.J_reqister').removeAttr('disabled');
            $('.J_reqister').css('display','visibility');
            $('.J_reqister').removeClass("disable");
            $(".J_load").hide();
        },
        success : function(data) {
            var code = data.code;
            if(code == '1' || code == 1){
                // document.location.href = "/html/register_success.html";

                $("#my-alert .am-modal-bd").text("注册成功，您可以下载钱妈妈app进行理财了：）");
                $("#my-alert").modal("open");
                $("#feeds").click(function(){
                    // document.location.href = "/html/register_success.html";
                    document.location.href = "/wap/download";
                })
            }else{
                var text = data.message;
                $("#my-alert .am-modal-bd").text(text);
                $("#my-alert").modal("open");
                $('.J_reqister').removeAttr('disabled');
                $('.J_reqister').css('display','visibility');
                $('.J_reqister').removeClass("disable");
                $(".J_load").hide();
            }
        }
    };

    //点击获取验证码
    $("#sendremotecodebutton").click(function(){
        var mobile = $('#mobile').val();

        if(mobile == null || mobile == '' || (!isMobile(mobile))){
            $("#my-alert .am-modal-bd").text("您输入的手机号码有误！");
            $("#my-alert").modal("open");
            return;
        }

        //if(sendremotecodebuttonHtml == "获取验证码"){
        $.ajax({
            url : "/wap/registerCode?mobile=" + mobile,  //注意接口
            type : "POST",
            error : function() {
                $("#my-alert .am-modal-bd").text("激活码发送失败，请重试");
                $("#my-alert").modal("open");
            },
            success : function(data) {
                if(data.code == "1" || data.code == 1){
                    timer = setInterval("enablesend()", 1000);
                }else{
                    var text = data.message;
                    $("#my-alert .am-modal-bd").text(text);
                    $("#my-alert").modal("open");
                }
            }
        });
        //}

    });

    //验证码倒计时
    var i = 59;
    function enablesend() {
        if ( i <= 0 ) {
            $("#sendremotecodebutton").html("获取验证码");
            i = 59;
            clearInterval(timer);
        } else {
            $("#sendremotecodebutton").text(i + "s后重新发送");
            i--;
        }
    }


</script>


<!-- footer -->
</body>
</html>
