<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="o" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="videoCoverList" scope="request" type="java.util.List<ru.itis.MyTube.model.dto.VideoCover>"/>
<jsp:useBean id="commonCssUrl" scope="request" type="java.lang.String"/>

<html>
<head>
    <%@include file="/WEB-INF/commonJsp/head.jsp"%>
    <link rel="stylesheet" href="${commonCssUrl}">
</head>

<body>
<%@include file="/WEB-INF/commonJsp/header.jsp"%>

    <main class="container-fluid">
        <div class="row">

            <%@include file="/WEB-INF/commonJsp/navigationBar.jsp"%>

            <div class="col">
                <div class="container-fluid p-0">
                    <div class="row row-cols-2 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-3">

                        <c:forEach items="${videoCoverList}" var="videoCover">

                            <o:videoCover videoCoverImageUrl="${videoCover.getVideoCoverImgUrl()}"
                                          videoName="${videoCover.getName()}"
                                          channelImageUrl="${videoCover.getChannelImgUrl()}"
                                          channelName="${videoCover.getChannelName()}"
                                          addedDate="${videoCover.getAddedDate()}"
                                          views="${videoCover.getViews()}"
                                          duration="${videoCover.getDuration()}"
                            >
                            </o:videoCover>

                        </c:forEach>
                        
                    </div>
                </div>
            </div>
        </div>

    </main>


    <%@include file="/WEB-INF/commonJsp/footer.jsp"%>
</body>
</html>
