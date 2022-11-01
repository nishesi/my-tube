<%@tag description="output video cover for video area" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="v" uri="/WEB-INF/tag/VideoCoverAuxiliary.tld" %>

<%@attribute name="videoCoverImageUrl" required="true" type="java.lang.String" %>
<%@attribute name="videoName" required="true" type="java.lang.String" %>
<%@attribute name="channelImageUrl" required="true" type="java.lang.String" %>
<%@attribute name="channelName" required="true" type="java.lang.String" %>
<%@attribute name="addedDate" required="true" type="java.time.LocalDateTime" %>
<%@attribute name="views" required="true" type="java.lang.Long" %>
<%@attribute name="duration" required="true" type="java.time.LocalTime" %>


<div class="col">
    <div class="card" >

        <img src="${videoCoverImageUrl}" class="card-img-top" alt="video cover">

        <div class="card-body p-1">

            <h6 class="card-title">${videoName}</h6>

            <div class="container p-0 align-baseline">
                <img class="rounded-circle channel-image" src="${channelImageUrl}" alt="channel cover">
                ${channelName}
            </div>

            <div class="container m-1">

                <p class="d-inline">
                    <v:views views="${views}"/>
                </p>
                —
                <p class="d-inline">
                    <v:duration duration="${duration}"/>
                </p>
            </div>
            <p class="m-0 text-center">
                <v:whenAdded addedDate="${addedDate}"/>
            </p>
        </div>
    </div>
</div>
