<%@ page contentType="text/html;charset=UTF-8" %>

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
            <%@include file="/WEB-INF/commonJsp/videoCoversList.jsp" %>
        </div>
    </div>

</main>


<%@include file="/WEB-INF/commonJsp/footer.jsp" %>
</body>
</html>
