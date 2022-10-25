<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>

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
            <o:problemOut name="usernameProblem" map="${problems}"></o:problemOut>

            <input type="password" name="password" placeholder="password" value="${password}">
            <o:problemOut name="passwordProblem" map="${problems}"></o:problemOut>

            <input type="submit" placeholder="submit">
            <a href="${regPageLink}">register</a>
        </form>
    </div>
</body>
</html>
