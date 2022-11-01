<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="videoCoverList" scope="request" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>"/>
<jsp:useBean id="commonCssUrl" scope="request" type="java.lang.String"/>
<jsp:useBean id="video" scope="request" type="ru.itis.MyTube.model.dto.Video"/>

<jsp:useBean id="logoUrl" scope="request" type="java.lang.String"/>
<jsp:useBean id="appName" scope="request" type="java.lang.String"/>
<jsp:useBean id="firstName" scope="request" type="java.lang.String"/>
<jsp:useBean id="lastName" scope="request" type="java.lang.String"/>
<jsp:useBean id="userImgUrl" scope="request" type="java.lang.String"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/commonJsp/head.jsp" %>
    <link rel="stylesheet" href="${commonCssUrl}">
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp"%>

<main class="container-fluid">
    <div class="row">

        <%@include file="/WEB-INF/commonJsp/navigationBar.jsp"%>


        <div class="col">
            <video height="480" width="640" type="video/mp4" controls="controls">
                <source src="${video.videoUrl}">
            </video>
        </div>

        <div class="col">
            <div class="container-fluid">
                <div>${video.videoCover.name}</div>
                <div class="container p-0 align-baseline mt-2">
                    <img class="rounded-circle channel-image" src="${video.videoCover.channelCover.channelImgUrl}"
                         alt="channel cover">
                    ${video.videoCover.name}
                </div>
                <div class="mt-2">
                    ${video.info}
                </div>
            </div>
        </div>

    </div>


    <div class="container-fluid p-0 mt-5">
        <%@include file="/WEB-INF/commonJsp/videoCoversList.jsp"%>
    </div>
</main>

<%@include file="/WEB-INF/commonJsp/footer.jsp"%>
</body>
</html>
