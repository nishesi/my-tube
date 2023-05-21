const videoUuid = document.getElementsByTagName('video')[0].id;
const reactionTypeButtons = document.getElementsByClassName('reactionType')

for (let i = 0; i < reactionTypeButtons.length; i++) {
    reactionTypeButtons[i].addEventListener("click", function (ev) {
        let reactionBtn = document.getElementsByClassName('reactionType')[i];
        let reaction = "NONE";

        if (reactionBtn.checked === true) {
            reaction = reactionBtn.id;
        }
        ev.stopPropagation();
        return updateReactions(reaction, videoUuid);
    })
}


function updateReactions(reaction, videoUuid) {
    let token = document.querySelector('meta[name="_csrf"]').getAttribute('content')
    let header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content')

    return fetch('/MyTube/api/reaction/v1', {
        headers: {
            "Content-Type": "application/json",
            [header] : token
        },
        method: "POST",
        body: JSON.stringify({
            "videoId": videoUuid,
            "reaction": reaction
        })
    }).then(resp => {
        if (resp.status === 202) {
            updateInf(resp);
        } else {
            resp.json().then(json => {
                printToast(json.message)

            }).catch(function (reason) {
                printToast("Cant make request.")

            }).finally(function () {
                for (let i = 0; i < reactionTypeButtons.length; i++) {
                    reactionTypeButtons[i].checked = false;
                }
            })
        }
    })
}

function updateInf(resp) {
    resp.json().then(json => {
        const likeLbl = document.getElementById("likeLabel");
        const dislikeLbl = document.getElementById("dislikeLabel");
        let viewLbl = document.getElementById("viewsLabel");

        viewLbl.innerText = json.views + " Views";
        likeLbl.innerText = json.likes + " Likes";
        dislikeLbl.innerText = json.dislikes + " Dislikes";

        for (let i = 0; i < reactionTypeButtons.length; i++) {
            let reactionBtn = reactionTypeButtons[i];
            reactionBtn.checked = json.reaction === reactionBtn.id;
        }
    })
}

function printToast(mess) {
    let toastContainer = document.getElementById("alertsContainer");
    let toastClone
        = toastContainer.querySelector('template').content.cloneNode(true).querySelector('div');
    toastClone.querySelector('.alert').classList.add("alert-warning");
    let toastBody= toastClone.querySelector(".toast-body");
    toastBody.innerText = mess === undefined ? "Something go wrong" : mess;
    toastContainer.appendChild(toastClone);
}

