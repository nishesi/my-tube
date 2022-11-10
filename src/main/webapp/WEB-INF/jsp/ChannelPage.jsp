<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>

<jsp:useBean id="commonCssUrl" scope="application" type="java.lang.String"/>
<jsp:useBean id="channel" scope="request" type="ru.itis.MyTube.model.dto.Channel"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyTube</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${commonCssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/commonJsp/header.jsp"/>

<main class="container-fluid">

    <div class="container-fluid">
        <img class="rounded-circle d-inline-block" src="${channel.channelCover.channelImgUrl}" width="100" height="100" alt="channel icon">
        <div class="d-inline-block ms-2 mt-4">
            <h2 class="">${channel.channelCover.name}</h2>
            <p class="">${channel.countOfSubscribers} subscribers</p>
        </div>
        <div class="list-group d-inline float-end m-2">
            <c:choose>
                <c:when test="${channel.id.equals(sessionScope.user.channelId)}">
                    <a href="${pageContext.servletContext.contextPath}${UrlPatterns.VIDEO_UPLOAD_PAGE}"
                       class="list-group-item list-group-item-action" aria-current="true">
                        Add video
                    </a>
                    <a href="#"
                       class="list-group-item list-group-item-action" aria-current="true">
                        Update
                    </a>
                </c:when>

                <c:otherwise>
                    <c:choose>
                        <c:when test="">
                            <a href="#"
                               class="list-group-item list-group-item-action" aria-current="true">
                                Subscribe
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="#"
                               class="list-group-item list-group-item-action active" aria-current="true">
                                Subscribed
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
    <div class="container text-center">
        <h4 class="m-4 mb-0 text-muted">Channel Information</h4>
        <p class=" m-4 mt-0">
            ${channel.info}
        </p>
    </div>

    <jsp:include page="/WEB-INF/commonJsp/videoCoverList.jsp"/>

</main>

<%@include file="/WEB-INF/commonJsp/footer.jsp" %>
</body>
</html>
