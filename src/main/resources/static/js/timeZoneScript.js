let timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone
fetch("/MyTube/api/timezone", {
    headers: {
        "Content-type": "application/x-www-form-urlencoded",
    },
    method: "POST",
    body: "timezone=" + timeZone
}).then(r => {
    console.log(r.status)
})