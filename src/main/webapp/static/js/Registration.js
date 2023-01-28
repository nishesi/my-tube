document.getElementById("submitButton").addEventListener("click", onSubmitFunction);

function onSubmitFunction() {
    let agreementValue;
    if (document.getElementById("agreement").checked === true) {
        agreementValue = "on";
    } else {
        agreementValue = "";
    }
    const jsonForm = {
        username: document.getElementsByName("username")[0].value,
        password: document.getElementsByName("password")[0].value,
        passwordRepeat: document.getElementsByName("passwordRepeat")[0].value,
        firstName: document.getElementsByName("firstName")[0].value,
        lastName: document.getElementsByName("lastName")[0].value,
        birthdate: document.getElementsByName("birthdate")[0].value,
        country: document.getElementsByName("country")[0].value,
        agreement: agreementValue,
    };
    fetch( "register", {
        mode: "cors",
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        redirect: "follow",
        body: JSON.stringify(jsonForm)

    }).then(resp => {
        if (resp.status === 400) {
            resp.json().then(jsonForm => printProblems(jsonForm));

        } else if (resp.status === 200) {
            window.location.href = resp.url;
        } else {
            resp.text().then(text => console.log(text))
        }
    })
}

function printProblems(jsonForm) {
    let usernameProblem = document.getElementById("usernameProblem");
    if (jsonForm.username !== undefined) {
        usernameProblem.innerText = jsonForm.username;
    } else {
        usernameProblem.innerText = "";
    }

    let passwordProblem = document.getElementById("passwordProblem");
    if (jsonForm.password !== undefined) {
        passwordProblem.innerText = jsonForm.password;
    } else {
        passwordProblem.innerText = "";
    }

    let passwordRepeatProblem = document.getElementById("passwordRepeatProblem");
    if (jsonForm.passwordRepeat !== undefined) {
        passwordRepeatProblem.innerText = jsonForm.passwordRepeat;
    } else {
        passwordRepeatProblem.innerText = "";
    }

    let firstNameProblem = document.getElementById("firstNameProblem");
    if (jsonForm.firstName !== undefined) {
        firstNameProblem.innerText = jsonForm.firstName;
    } else {
        firstNameProblem.innerText = "";
    }

    let lastNameProblem = document.getElementById("lastNameProblem");
    if (jsonForm.lastName !== undefined) {
        lastNameProblem.innerText = jsonForm.lastName;
    } else {
        lastNameProblem.innerText = "";
    }

    let birthdateProblem = document.getElementById("birthdateProblem");
    if (jsonForm.birthdate !== undefined) {
        birthdateProblem.innerText = jsonForm.birthdate;
    } else {
        birthdateProblem.innerText = "";
    }

    let countryProblem = document.getElementById("countryProblem");
    if (jsonForm.country !== undefined) {
        countryProblem.innerText = jsonForm.country;
    } else {
        countryProblem.innerText = "";
    }

    let agreementProblem = document.getElementById("agreementProblem");
    if (jsonForm.agreement !== undefined) {
        agreementProblem.innerText = jsonForm.agreement;
    } else {
        agreementProblem.innerText = "";
    }
}
