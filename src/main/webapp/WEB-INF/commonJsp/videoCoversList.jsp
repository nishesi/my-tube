<%@ page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="o" tagdir="/WEB-INF/tags" %>


<c:choose>
    <c:when test="${not empty requestScope.videoCoverList}">
        <div class="container-fluid p-0">
            <div class="row row-cols-2 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-3">

                <c:forEach items="${requestScope.videoCoverList}" var="videoCover" >

                    <o:videoCover videoCoverImgUrl="${videoCover.videoCoverImgUrl}"
                                  videoName="${videoCover.name}"
                                  channelCover="${videoCover.channelCover}"
                                  addedDate="${videoCover.addedDate}"
                                  views="${videoCover.views}"
                                  duration="${videoCover.duration}"
                    >
                    </o:videoCover>

                </c:forEach>

            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container d-inline-block p-3 rounded-2 bg-danger">
            videos not loaded
        </div>
    </c:otherwise>
</c:choose>
