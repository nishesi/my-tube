document.addEventListener("DOMContentLoaded", function () {
    const videoUuid = document.getElementsByTagName('video')[0].id;
    const reactionTypeButtons = document.getElementsByClassName('reactionType')

    for (let i = 0; i < reactionTypeButtons.length; i++) {
        reactionTypeButtons[i].addEventListener("click", function (ev) {
            let reactionTypeButton = document.getElementsByClassName('reactionType')[i];
            let id = reactionTypeButton.getAttribute("id");
            let reactionType = 0;

            if (id === "like") {
                reactionType = 1;
            } else if (id === "dislike") {
                reactionType = -1;
            }
            reactionTypeButton.removeAttribute("checked");
            ev.stopPropagation();
            return updateReactions(reactionType, videoUuid);
        })
    }


    function updateReactions(reaction, videoUuid) {

        return fetch('/MyTube/reaction/' + videoUuid + "?reaction=" + reaction, {
            method: "POST"
        }).then(res => {
            return res.json();
        }).then(json => {
            if (json.redirect === true) {
                window.location.replace(json.location);
            } else {
                updateInf(json.likes, json.dislikes, json.reaction);
            }
        })
    }

    function updateInf(likes, dislikes, reaction) {
        const likeLbl = document.getElementById("likeLabel");
        const dislikeLbl = document.getElementById("dislikeLabel");
        const voidLbl = document.getElementById("voidLabel");

        likeLbl.innerText = "like " + likes;
        dislikeLbl.innerText = "dislike " + dislikes;

        if (reaction === -1) {
            dislikeLbl.setAttribute("checked", "");
        } else if (reaction === 0) {
            voidLbl.setAttribute("checked", "");
        } else if (reaction === 1) {
            likeLbl.setAttribute("checked", "");
        }
    }
})
