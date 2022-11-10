<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="commonCssUrl" scope="application" type="java.lang.String"/>

<jsp:useBean id="video" scope="request" type="ru.itis.MyTube.model.dto.Video"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MyTube</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="${commonCssUrl}">
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp"%>

<main class="container-fluid">
    <div class="row">

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
                    ${video.videoCover.channelCover.name}
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
