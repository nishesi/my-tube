<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pr" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="regPageCss" scope="request" type="java.lang.String"/>
<jsp:useBean id="form" scope="request" type="ru.itis.MyTube.model.forms.RegistrationForm"/>
<jsp:useBean id="problems" scope="request" type="java.util.Map<java.lang.String, java.lang.String>"/>


<html>
<head>
    <meta charset="UTF-8">
    <title>registration</title>
    <link rel="stylesheet" href="${regPageCss}">
</head>
<body>
<div id="box">
    <form action="" method="post">

        <h1 class="reg-box-el">Registration</h1>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="login" placeholder="login" value="${form.login}">
            <pr:problemOut name="login" map="${problems}"></pr:problemOut>
        </div>


        <div class="reg-box-el">
            <input class="text-input" type="text" name="password" placeholder="password" value="${form.password}">
            <pr:problemOut name="password" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="passwordRepeat" placeholder="repeat password" value="${form.passwordRepeat}">
            <pr:problemOut name="passwordRepeat" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="firstName" placeholder="first name" value="${form.firstName}">
            <pr:problemOut name="firstName" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="lastName" placeholder="last name" value="${form.lastName}">
            <pr:problemOut name="lastName" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="date" name="birthdate" placeholder="birth date" value="${form.birthdate}">
            <pr:problemOut name="birthdate" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">sex<br>
            <span>
                <input type="radio" name="sex" value="male">male
                <input type="radio" name="sex" value="female">female
                <input type="radio" name="sex" value="another">another
            </span>
            <pr:problemOut name="sex" map="${problems}"></pr:problemOut>
        </div>

        <div class="reg-box-el">
            <input class="text-input" type="text" name="country" placeholder="country" value="${form.country}">
            <pr:problemOut name="country" map="${problems}"></pr:problemOut>
        </div>


        <div class="reg-box-el">
            <input class="text-input" type="checkbox" name="agreement">I am agree with user agreement
            <pr:problemOut name="agreement" map="${problems}"></pr:problemOut>
        </div>


        <div class="reg-box-el"><input class="text-input" type="submit" placeholder="submit"></div>

    </form>
</div>
</body>
</html>
