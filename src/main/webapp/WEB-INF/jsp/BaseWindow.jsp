<%@ page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="videoCoverList" scope="request" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>"/>
<jsp:useBean id="commonCssUrl" scope="request" type="java.lang.String"/>

<jsp:useBean id="firstName" scope="request" type="java.lang.String"/>
<jsp:useBean id="lastName" scope="request" type="java.lang.String"/>
<jsp:useBean id="userImgUrl" scope="request" type="java.lang.String"/>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/commonJsp/head.jsp" %>
    <link rel="stylesheet" href="${commonCssUrl}">
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp" %>

<main class="container-fluid">
    <div class="row">

        <%@include file="/WEB-INF/commonJsp/navigationBar.jsp" %>

        <div class="col">
            <c:import url="/WEB-INF/commonJsp/videoCoversList.jsp"/>
        </div>
    </div>

</main>


<%@include file="/WEB-INF/commonJsp/footer.jsp" %>
</body>
</html>
