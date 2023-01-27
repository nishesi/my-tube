const submitButton = document.getElementById("submitButton");
submitButton.addEventListener("click", function () {
    function printErrors(resp) {
        let json = resp.json();
        const usernameProblem = document.getElementById("usernameProblem");
        usernameProblem.innerText = json['username'];
    }

    const jsonForm = {
        username: document.getElementsByName("username")[0].value,
        password: document.getElementsByName("password")[0].value,
        passwordRepeat: document.getElementsByName("passwordRepeat")[0].value,
        firstName: document.getElementsByName("firstName")[0].value,
        lastName: document.getElementsByName("lastName")[0].value,
        birthdate: document.getElementsByName("birthdate")[0].value,
        country: document.getElementsByName("country")[0].value,
        agreement: document.getElementById("agreement").value,
    };
    fetch("/MyTube/register", {
        method : "POST",
        headers : {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(jsonForm)
    }).then(resp => {
        if (resp.status === 200) {
            return resp.json();

        } else if (resp.status === 302) {
            window.location.href = resp.headers.get("Location");

        } else {
            console.log(resp.text());
        }
    }).then(jsonForm => {
        if (jsonForm.username !== undefined) {
            document.getElementById("usernameProblem").innerText = jsonForm.username;
        }
        if (jsonForm.password !== undefined) {
            document.getElementById("passwordProblem").innerText = jsonForm.password;
        }
        if (jsonForm.passwordRepeat !== undefined) {
            document.getElementById("passwordRepeatProblem").innerText = jsonForm.passwordRepeat;
        }
        if (jsonForm.firstName!== undefined) {
            document.getElementById("firstNameProblem").innerText = jsonForm.firstName;
        }
        if (jsonForm.lastName!== undefined) {
            document.getElementById("lastNameProblem").innerText = jsonForm.lastName;
        }
        if (jsonForm.birthdate!== undefined) {
            document.getElementById("birthdateProblem").innerText = jsonForm.birthdate;
        }
        if (jsonForm.country!== undefined) {
            document.getElementById("countryProblem").innerText = jsonForm.country;
        }
        if (jsonForm.agreement!== undefined) {
            document.getElementById("agreementProblem").innerText = jsonForm.agreement;
        }
    })
});
