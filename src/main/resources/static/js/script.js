function addConnection(connectionId) {
    let elementById = document.getElementById(connectionId);
    let firstname = elementById.querySelector("td:nth-child(1)").innerText;
    let lastname = elementById.querySelector("td:nth-child(2)").innerText;
    let isConfirmed = confirm("Are you sure you want to add " + firstname + " " + lastname + " as a connection?");
    if (!isConfirmed) {
        return;
    }
    fetch("/users/mine/connections", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            connectionId: connectionId
        })
    }).then(function (response) {
        location.reload()
    })
}