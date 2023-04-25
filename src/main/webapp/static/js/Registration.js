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
    const regFields = [
        "username",
        "password",
        "passwordRepeat",
        "firstName",
        "lastName",
        "birthdate",
        "country",
        "agreement"
    ];

    for (let i = 0; i < regFields.length; i++) {
        let attrProblemBox = document.getElementById(regFields[i] + "Problem")
        if (jsonForm[regFields[i]] !== undefined) {
            attrProblemBox.innerText = jsonForm[regFields[i]];
        } else {
            attrProblemBox.innerText = "";
        }
    }
}
