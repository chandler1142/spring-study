<%--
  Created by IntelliJ IDEA.
  User: chandlerzhao
  Date: 2018/12/24
  Time: 下午4:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach items="${users}" var="user">
        <c:out value="${user.username}" /><br/>
        <c:out value="${user.age}" /><br/>
    </c:forEach>
</body>
</html>
