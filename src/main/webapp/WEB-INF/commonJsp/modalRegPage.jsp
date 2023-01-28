<%@ page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags" %>
<script defer src="${contextPath}/static/js/Registration.js"></script>

<div class="modal fade" id="regModal" tabindex="-1" aria-labelledby="regModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="regModalLabel">Registration</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div id="regForm" class="form m-4">

                    <div class="input-group">
                        <div class="input-group-text">Username</div>
                        <input type="text" class="form-control" placeholder="Write your username..."
                               aria-label="Username"
                               name="username" value="${form.username}">
                    </div>
                    <div id="usernameProblem" class="mb-4 text-danger">
                        <o:problemOut name="username" map="${requestScope.problems}"/>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">Password</div>
                        <input type="password" class="form-control" placeholder="Write your password..."
                               aria-label="Password"
                               name="password" value="${form.password}">
                    </div>
                    <div id="passwordProblem" class="mb-4 text-danger">
                        <o:problemOut name="password" map="${requestScope.problems}"/>
                    </div>

                    <div class="input-group">
                        <span class="input-group-text">Repeat password</span>
                        <input type="password" class="form-control" placeholder="Repeat your password..."
                               aria-label="Repeat password"
                               name="passwordRepeat" value="${form.passwordRepeat}">
                    </div>
                    <div id="passwordRepeatProblem" class="mb-4 text-danger">
                        <o:problemOut name="passwordRepeat" map="${requestScope.problems}"/>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">First name</div>
                        <input type="text" class="form-control" placeholder="Write..." aria-label="First name"
                               name="firstName"
                               value="${form.firstName}">
                        <div class="input-group-text">Last name</div>
                        <input type="text" class="form-control" placeholder="Write..." aria-label="Last name"
                               name="lastName"
                               value="${form.lastName}">
                    </div>
                    <div class="mb-4 text-danger">
                        <span id="firstNameProblem"></span>
                        <span id="lastNameProblem"></span>
                        <o:problemOut name="firstName" map="${requestScope.problems}"/>
                        <o:problemOut name="lastName" map="${requestScope.problems}"/>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">Birthdate</div>
                        <input type="date" class="form-control" placeholder="Write your birthdate..."
                               aria-label="Birthdate"
                               name="birthdate" value="${form.birthdate}">
                    </div>
                    <div id="birthdateProblem" class="mb-4 text-danger">
                        <o:problemOut name="birthdate" map="${requestScope.problems}"/>
                    </div>

                    <div class="input-group">
                        <div class="input-group-text">Country</div>
                        <input type="text" class="form-control" placeholder="Write your country..." aria-label="Country"
                               name="country" value="${form.country}">
                    </div>
                    <div id="countryProblem" class="mb-5 text-danger">
                        <o:problemOut name="country" map="${requestScope.problems}"/>
                    </div>


                    <div class="input-group">
                        <div class="input-group-text">
                            <input id="agreement" type="checkbox" aria-label="agreement" class="form-check-input"
                                   name="agreement">
                        </div>
                        <span class="form-control">I am agree with processing my personal data</span>
                    </div>
                    <div id="agreementProblem" class="mb-4 text-danger">
                        <o:problemOut name="agreement" map="${requestScope.problems}"/>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-target="#authModal"
                        data-bs-toggle="modal">Login
                </button>
                <button id="submitButton" class="btn btn-primary">Register</button>
            </div>
        </div>
    </div>
</div>
