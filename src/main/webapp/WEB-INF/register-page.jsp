<jsp:useBean id="regPageCss" scope="request" type="java.lang.String"/>
<jsp:useBean id="form" scope="request" type="ru.itis.forms.RegistrationForm"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>registration</title>
    <link rel="stylesheet" href="${regPageCss}">   <%--/context/reg-page/reg-page.css--%>
</head>
<body>
<div id="box">
    <form action="" method="post">

        <h1 class="reg-box-el">Registration</h1>

        <div class="reg-box-el"><input class="text-input" type="text" name="login" placeholder="login" value="${form.login}"></div>
        <c:if test="${login != null}">
            <div class="input-problem"><c:out value="${login}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="text" name="password" placeholder="password"></div>
        <c:if test="${password != null}">
            <div class="input-problem"><c:out value="${password}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="text" name="passwordRepeat" placeholder="repeat password"></div>
        <c:if test="${passwordRepeat != null}">
            <div class="input-problem"><c:out value="${passwordRepeat}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="text" name="firstName" placeholder="first name"></div>
        <c:if test="${firstName != null}">
            <div class="input-problem"><c:out value="${firstName}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="text" name="lastName" placeholder="last name"></div>
        <c:if test="${lastName != null}">
            <div class="input-problem"><c:out value="${lastName}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="date" name="birthdate" placeholder="birth date"></div>
        <c:if test="${birthdate != null}">
            <div class="input-problem"><c:out value="${birthdate}"></c:out></div>
        </c:if>

        <div class="reg-box-el">sex<br>
            <span>
                    <input type="radio" name="sex" value="male">male
                    <input type="radio" name="sex" value="female">female
                    <input type="radio" name="sex" value="another">another
                </span>
        </div>
        <c:if test="${sex != null}">
            <div class="input-problem"><c:out value="${sex}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="text" name="country" placeholder="country"></div>
        <c:if test="${country != null}">
            <div class="input-problem"><c:out value="${country}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="checkbox" name="agreement">I am agree with user agreement</div>
        <c:if test="${agreement != null}">
            <div class="input-problem"><c:out value="${agreement}"></c:out></div>
        </c:if>

        <div class="reg-box-el"><input class="text-input" type="submit" placeholder="submit"></div>

    </form>
</div>
</body>
</html>
