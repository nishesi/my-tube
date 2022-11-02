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

            <form action="" method="get">
                <div class="input-group flex-nowrap">
                    <input
                            type="text"
                            name="search"
                            class="form-control"
                            placeholder="Search"
                            aria-label="Search"
                    />
                    <button class="btn btn-secondary" type="submit">?</button>
                </div>
            </form>
        </div>

        <div class="col text-end">
            <div class="container m-1">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="d-inline">${sessionScope.user.firstName} ${sessionScope.user.lastName}</div>
                        <img class="d-inline" src="${sessionScope.user.userImgUrl}" height="40px" width="40px" alt="user image">
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/register" >register</a>
                        <a href="${pageContext.request.contextPath}/authenticate">login</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>
