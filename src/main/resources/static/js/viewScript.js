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
    return fetch('/MyTube/reaction',
        {
            headers: {
                "Content-Type": "application/json"
            },
            method: "POST",
            body: JSON.stringify({
                "videoId": videoUuid,
                "reaction": reaction
            })
        }).then(res => {
        return res.json();
    }).then(json => {
        updateInf(json);
    })
}

function updateInf(json) {
    const likeLbl = document.getElementById("likeLabel");
    const dislikeLbl = document.getElementById("dislikeLabel");
    let viewLbl = document.getElementById("viewsLabel");

    viewLbl.innerText = json.views + "Views";
    likeLbl.innerText = json.likes + "Likes";
    dislikeLbl.innerText = json.dislikes + "Dislikes";

    for (let i = 0; i < reactionTypeButtons.length; i++) {
        let reactionBtn = reactionTypeButtons[i];
        reactionBtn.checked = json.reaction === reactionBtn.id;
    }
}

