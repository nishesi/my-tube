<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <jsp:include page="/WEB-INF/commonJsp/head.jsp"/>
    <link rel="stylesheet" href="${commonCssUrl}">
</head>
<body>
<%@include file="/WEB-INF/commonJsp/header.jsp"%>

<main class="container-fluid">
    <div class="row">

        <div class="col">
            <%@include file="/WEB-INF/commonJsp/videoCoversList.jsp" %>
        </div>
    </div>

</main>
</body>
</html>
