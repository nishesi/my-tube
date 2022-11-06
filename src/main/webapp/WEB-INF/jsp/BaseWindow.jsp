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

        <div class="col">
            <%@include file="/WEB-INF/commonJsp/videoCoversList.jsp" %>
        </div>
    </div>

</main>


<%@include file="/WEB-INF/commonJsp/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
</body>
</html>
