<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //String basePath = "http://localhost:8080/crm/";
    //动态获取根路径
    String basePath = request.getScheme() + "://" + request.getServerName() +
            ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <%--用来处理登录逻辑--%>
    <script type="text/javascript">
        $(function () {
            //需要获取整个窗口的jquery对象  因为保证光标在任何位置点击回车都有效
            //e 代表Event 对象代表事件的状态，比如事件在其中发生的元素、键盘按键的状态、鼠标的位置、鼠标按钮的状态

            //就算按钮设置成失效  同样可以触发点击时间 因此 需要进行判断
            //$("#loginBtn").prop('disabled')   判断是否失效   失效为true  不失效false
            $(window).keydown(function (e) {
                if (e.keyCode == 13 && $("#loginBtn").prop('disabled') == false) {
                    //没有参数 代表调用函数
                    $("#loginBtn").click();
                }
            })

            $("#loginBtn").click(function () {
                //获取用户名 密码
                let loginAct = $("#loginAct").val();
                let loginPwd = $("#loginPwd").val();
                //获取单选框状态  不能使用attr   该方法是获取属性值
                let isRemPwd = $("#isRemPwd").prop("checked");


                //利用ajax发送异步请求
                $.ajax({
                    //默认值: 当前页地址。发送请求的地址。
                    url: 'pages/settings/qx/user/login.do',
                    //发送到服务器的数据。将自动转换为请求字符串格式。
                    data: {
                        loginAct: loginAct,
                        loginPwd: loginPwd,
                        isRemPwd: isRemPwd
                    },
                    //默认值: "GET")。请求方式 ("POST" 或 "GET")， 默认为 "GET"。注意：
                    // 其它 HTTP 请求方法，如 PUT 和 DELETE 也可以使用，但仅部分浏览器支持。
                    type: 'post',

                    //预期服务器返回的数据类型。
                    // 如果不指定，jQuery 将自动根据 HTTP 包 MIME 信息来智能判断，
                    // 比如 XML MIME 类型就被识别为 XML。在 1.4 中，JSON 就会生成一个 JavaScript 对象，
                    // 而 script 则会执行这个脚本。随后服务器端返回的数据会根据这个值解析后，传递给回调函数。
                    dataType: 'json',
                    //请求成功后的回调函数。
                    // 参数：由服务器返回，并根据 dataType 参数进行处理后的数据；描述状态的字符串。
                    success: function (data) {
                        if (data.code == "1") {
                            //跳转到业务主页面
                            window.location.href = "pages/workbench/index.do";
                        } else {
                            //提示信息
                            $("#msg").text(data.message);
                        }
                    },
                    beforeSend: function () {
                        //当ajax向后台发送请求之前，会自动执行本函数
                        //如果该函数返回true,则ajax会真正向后台发送请求；否则，
                        // 如果该函数返回false，则ajax放弃向后台发送请求。

                        //表单验证
                        if (loginAct == "") {
                            alert("用户名不能为空");
                            return false;
                        }
                        if (loginPwd == "") {
                            alert("密码不能为空");
                            return false;
                        }
                        //3.让提交按钮失效，以实现防止按钮重复点击
                        $("#loginBtn").attr('disabled', 'disabled');

                        //4.给用户提供友好状态提示
                        $("#msg").text('登录中...');
                    },
                    complete: function () {
                        //请求完成后会执行该函数(请求成功或失败之后均调用)
                        //5.让登陆按钮重新有效
                        $("#loginBtn").removeAttr('disabled');
                    }
                });
            })
        })
    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.html" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <%--
                    cookie.loginAct  获取的是名为loginAct的cookie对象
                    cookie.loginAct.name是cookie键
                    cookie.loginAct.value是cookie值
                    --%>
                    <input class="form-control" id="loginAct" type="text" placeholder="用户名"
                           value="${cookie.loginAct.value}">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" id="loginPwd" type="password" placeholder="密码"
                           value="${cookie.loginPwd.value}">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
                    <label>
                        <%--单选框状态 由浏览器cookie中是否存在账号密码 决定--%>
                        <c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
                            <%--选中状态--%>
                            <input type="checkbox" id="isRemPwd" checked> 十天内免登录
                        </c:if>
                        <c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
                            <%--未选中状态--%>
                            <input type="checkbox" id="isRemPwd"> 十天内免登录
                        </c:if>
                    </label>
                    &nbsp;&nbsp;
                    <span id="msg"></span>
                </div>
                <%--不能使用submit  submit会发送请求--%>
                <button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>