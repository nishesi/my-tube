<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${requestScope.alerts != null}">
        <jsp:useBean id="alerts" scope="request" type="java.util.List<ru.itis.MyTube.auxiliary.Alert>"/>

        <div class="toast-container position-fixed top-0 end-0 me-5 p-3">
            <c:forEach items="${alerts}" var="alert">
                <o:notification alert="${alert}"/>
            </c:forEach>
        </div>
    </c:if>
</div>
