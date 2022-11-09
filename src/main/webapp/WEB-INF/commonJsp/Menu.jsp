<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>


<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel" >
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasRightLabel">
            ${sessionScope.user.firstName} ${sessionScope.user.lastName}
            <img width="40" height="40" src="${sessionScope.user.userImgUrl}" alt="user image">
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <div class="list-group">
            <a href="${pageContext.servletContext.contextPath}${UrlPatterns.USER_PAGE}"
               class="list-group-item list-group-item-action" aria-current="true">
                Change account information
            </a>
            <c:if test="${false}">
                <a href="#" class="list-group-item list-group-item-action">Create channel</a>
            </c:if>
            <c:if test="${false}">
                <a href="#" class="list-group-item list-group-item-action">My channel</a>
            </c:if>
            <a href="${pageContext.servletContext.contextPath}${UrlPatterns.EXIT}" class="list-group-item list-group-item-action">Exit from account</a>
        </div>
    </div>
</div>
