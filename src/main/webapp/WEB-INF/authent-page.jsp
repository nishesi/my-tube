<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
</head>
<body>
    <c:if test="${error != null}" >
        <div class="error">
            <p>${error}</p>
        </div>
    </c:if>

    <div class="box">
        <h1>Authentication</h1>
        <form action="" method="post">
            <input type="text" name="username" placeholder="username" value="${username}">
            <c:if test="${usernameProblem != null}">
                ${usernameProblem}
            </c:if>
            <input type="password" name="password" placeholder="password" value="${password}">
            <c:if test="${passwordProblem != null}">
                ${passwordProblem}
            </c:if>
            <input type="submit" placeholder="submit">
            <a href="${regPageLink}">register</a>
        </form>
    </div>
</body>
</html>
