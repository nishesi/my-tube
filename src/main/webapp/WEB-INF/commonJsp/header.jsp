<%@ page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="container-fluid">
    <nav class="navbar navbar-expand-md bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img id="logo" src="${logoUrl}" alt="logo">
                <strong>${appName}</strong>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Random</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Subscriptions</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Popular</a>
                    </li>
                    <li class="nav-item">
                        <form action="${pageContext.servletContext.contextPath}/search" method="get" role="search"
                              class="ms-md-5">
                            <div class="input-group flex-nowrap">
                                <input
                                        type="text"
                                        name="search"
                                        class="form-control"
                                        placeholder="Search"
                                        aria-label="Search"
                                        value="${requestScope.substring}"
                                />
                                <button class="btn btn-outline-secondary" type="submit">?</button>
                            </div>
                        </form>
                        <c:if test="${not empty requestScope.problem}">
                            <div class="alert alert-warning text-center p-2" role="alert">
                                    ${requestScope.problem}
                            </div>
                        </c:if>
                    </li>
                </ul>

                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <a href="${pageContext.servletContext.contextPath}/userpage" class="navbar-brand fs-5 ms-md-5">
                                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                            <img width="40" height="40" src="${sessionScope.user.userImgUrl}" alt="user image">
                        </a>
                    </c:when>

                    <c:otherwise>
                        <ul class="navbar-nav ms-md-5 mb-2 mb-md-0">
                            <li class="nav-item me-2">
                                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"
                                        data-bs-target="#regModal">
                                    <strong>Register</strong>
                                </button>
                            </li>
                            <li class="nav-item">
                                <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal"
                                        data-bs-target="#authModal">
                                    <strong>Login</strong>
                                </button>
                            </li>
                        </ul>

                        <jsp:include page="/WEB-INF/commonJsp/modalRegPage.jsp"/>
                        <jsp:include page="/WEB-INF/commonJsp/modalAuthPage.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>

