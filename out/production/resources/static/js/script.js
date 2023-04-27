function addConnection(connectionId) {
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