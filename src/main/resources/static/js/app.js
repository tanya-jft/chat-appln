var user = null;
var currentSubscription = null;
var chat = $("#chat");
var loggedIn_id = $("#logged-id").val()
var loggedIn_name = $("#logged-name").val()
var roomId = null;
var loggedIn_un = $("#logged-un").val()


var stompClient = null;

function connect(displayName, username, id) {
    user = {"displayName": displayName, "username": username, "id": id};

    roomId = parseInt(user.id) + parseInt(loggedIn_id);
    sessionStorage.setItem(`${user.id}`, `${user.username}`)
    var socket = new SockJS("/sock");
    stompClient = Stomp.over(socket);
    stompClient.connect(user, onConnected);
}


function onConnected(e) {

    stompClient.subscribe(`/live-chat/${roomId}`, allMessage)
    currentSubscription = stompClient.subscribe(`/midway/${roomId}`, onMessageRecieved)

    $(".chat-header").empty();
    $(".chat-header").append($("<h4>").text(`${user.displayName}`), $("<p>").text(`online`));

    /*        stompClient.send(`/live-chat/send-to-broker/${roomId}/addUser`,
                {},
                JSON.stringify({sender: loggedIn_name, message:"Join"}))*/
}


function allMessage(payload) {
    $(".msg-box").empty();
    let entries = Object.entries(JSON.parse(payload.body));
    let date = new Date();

    entries.map(([key, values]) => {
        $(".msg-box").append(`<div class="msg-date-slot" >
                    <p class="date">${key}</p>
                </div>`)
        for (const i in values) {
            const isLoggedIn = values[i].username === loggedIn_un;
            const messageType = isLoggedIn ? 'send-msg' : 'received-msg';
            const alignment = isLoggedIn ? 'end' : 'start';

            $(".msg-box").append(`
            <div class="col append-msg">
                <div class="d-flex align-items-${alignment} flex-column ${messageType}">
                <p class="msg-content">${values[i].message} <i class="time">${values[i].time}</i> </p>
                </div>
            </div>`);
        }
    })

    scrollBottom();
}


function onMessageRecieved(payload) {
    var payloadBody = JSON.parse(payload.body)

    // recorrect the code
    const isLoggedIn = payloadBody.sender.toLowerCase() === loggedIn_name.toLowerCase();
    var currentTime = new Date();

    const messageType = isLoggedIn ? 'send-msg' : 'received-msg';
    const alignment = isLoggedIn ? 'end' : 'start';

    $(".msg-box").append(`
            <div class="col append-msg">
                <div class="d-flex align-items-${alignment} flex-column ${messageType}">
                <p class="msg-content">${payloadBody.message}
                 <i class="time">${currentTime.getHours()}:${currentTime.getMinutes()}</i> </p>
                </div>
            </div>`);
    scrollBottom();

    console.log(messageType);
}

function sendMessage() {
    var message = $("#msg").val().trim();
    console.log(message);

    if (message && stompClient) {
        var chatMessage = {
            sender: loggedIn_name, message: message
        }
        stompClient.send(`/live-chat/send-to-broker/${roomId}/sendMessage`, {}, JSON.stringify(chatMessage));
        $("#msg").val('');
    }
    event.preventDefault();
    scrollBottom();
}


$("#msg-form").on("submit", function (event) {
    sendMessage();
    // event.preventDefault();
});

function scrollBottom(){
    var messageBody = document.querySelector('.msg-box');
    messageBody.scrollTop = messageBody.scrollHeight - messageBody.clientHeight;
}
// load the notes section when the page get ready
$(document).ready(function () {

});

