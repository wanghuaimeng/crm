<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>

<body>
<script type="text/javascript">
    <%--
    这和自己在地址栏输入地址访问一样
    因此使用该方法直接访问web-inf下的页面会报错
    --%>
    window.location.href = "pages/settings/qx/user/toLogin.do";
</script>
</body>
</html>