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

        <div class="reg-box-el">
            <input class="text-input" type="text" name="login" placeholder="login" value="${form.login}">
            <c:if test="${login != null}">
                <div class="input-problem">${login}</div>
            </c:if>
        </div>


        <div class="reg-box-el">
            <input class="text-input" type="text" name="password" placeholder="password" value="${form.password}">
            <c:if test="${password != null}">
                <div class="input-problem">${password}</div>
            </c:if>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="passwordRepeat" placeholder="repeat password" value="${form.passwordRepeat}">
            <c:if test="${passwordRepeat != null}">
                <div class="input-problem">${passwordRepeat}</div>
            </c:if>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="firstName" placeholder="first name" value="${form.firstName}">
            <c:if test="${firstName != null}">
                <div class="input-problem">${firstName}</div>
            </c:if>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="lastName" placeholder="last name" value="${form.lastName}">
            <c:if test="${lastName != null}">
                <div class="input-problem">${lastName}</div>
            </c:if>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="date" name="birthdate" placeholder="birth date" value="${form.birthDate}">
            <c:if test="${birthdate != null}">
                <div class="input-problem">${birthdate}</div>
            </c:if>
        </div>

        <div class="reg-box-el">sex<br>
            <span>
                <input type="radio" name="sex" value="male">male
                <input type="radio" name="sex" value="female">female
                <input type="radio" name="sex" value="another">another
            </span>
            <c:if test="${sex != null}">
                <div class="input-problem">${sex}</div>
            </c:if>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="country" placeholder="country" value="${form.country}">
            <c:if test="${country != null}">
                <div class="input-problem">${country}</div>
            </c:if>
        </div>


        <div class="reg-box-el">
            <input class="text-input" type="checkbox" name="agreement">I am agree with user agreement
            <c:if test="${agreement != null}">
                <div class="input-problem">${agreement}</div>
            </c:if>
        </div>


        <div class="reg-box-el"><input class="text-input" type="submit" placeholder="submit"></div>

    </form>
</div>
</body>
</html>
