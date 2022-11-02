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
            <label>Username
                <input type="text" name="username" placeholder="username" value="${username}">
            </label>
            <o:problemOut name="usernameProblem" map="${problems}"/>

            <label>Password
                <input type="password" name="password" placeholder="password" value="${password}">
            </label>
            <o:problemOut name="passwordProblem" map="${problems}"/>

            <input type="submit" placeholder="submit">
            <a href="${regPageLink}">register</a>
        </form>
    </div>
</body>
</html>
