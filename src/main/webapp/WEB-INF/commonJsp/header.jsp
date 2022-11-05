<%@ page contentType="text/html;charset=UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="container-fluid">
    <div class="row ">
        <div class="col">
            <div class="container m-1">
                <img src="${logoUrl}" class="d-inline" height="50px" width="50px" alt="logo">
                <h1 class="d-inline">${appName}</h1>
            </div>
        </div>

        <div class="col-auto text-center m-2">

            <form action="${pageContext.servletContext.contextPath}/search" method="get">
                <div class="input-group flex-nowrap">
                    <input
                            type="text"
                            name="substring"
                            class="form-control"
                            placeholder="Search"
                            aria-label="Search"
                            value="${requestScope.substring}"
                    />
                    <button class="btn btn-secondary" type="submit">?</button>
                </div>
            </form>
            <c:if test="${not empty requestScope.problem}">
                <div class="alert alert-warning text-center p-2" role="alert">
                    ${requestScope.problem}
                </div>
            </c:if>
        </div>

        <div class="col text-end pt-2">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="${pageContext.servletContext.contextPath}/userpage">
                        <div class="d-inline">${sessionScope.user.firstName} ${sessionScope.user.lastName}</div>
                        <img class="d-inline" src="${sessionScope.user.userImgUrl}" height="40px" width="40px" alt="user image">
                    </a>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#regModal">
                        <strong>Register</strong>
                    </button>
                    <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#authModal">
                        <strong>Login</strong>
                    </button>

                    <jsp:include page="/WEB-INF/commonJsp/modalRegPage.jsp"/>
                    <jsp:include page="/WEB-INF/commonJsp/modalAuthPage.jsp"/>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</header>
