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

    var socket = new SockJS("/sock");
    stompClient = Stomp.over(socket);
    stompClient.connect(user, onConnected);
}

function onConnected(e) {

    // var newSubs = stompClient.subscribe(`/live-chat/${roomId}`, allMessage)
    currentSubscription = stompClient.subscribe(`/midway/${roomId}`, onMessageRecieved)

    $(".chat-header").empty();
    $(".chat-header").append($("<h4>").text(`${user.displayName}`), $("<p>").text(`online`));

    /*        stompClient.send(`/live-chat/send-to-broker/${roomId}/addUser`,
                {},
                JSON.stringify({sender: loggedIn_name, message:"Join"}))*/
}

function allMessage(payload) {
    //body = {date : [{message, sender, time, username}, {}, {}, {}], date: [{}, {}, {}].....}
    var payloadBody = JSON.parse(payload.body);
    var dateBox = $(".date");
    var outerDateBox = $('.msg-date-slot');
    var appendSendRcvMessage = $('.append-msg')


    var date = new Date().toJSON().slice(0, 10);
    var entries = Object.entries(payloadBody);

    entries.map(([key, valList]) => {
        var dateKey = date === key ? "Today" : key
        dateBox.text(dateKey).appendTo(outerDateBox)
        for (const i in valList) {
            if (valList[i].username === loggedIn_un) {
                $(appendSendRcvMessage).append(`<div class="send-msg">
                                        <p class="msg-content">
                                        ${valList[i].message}
                                        <i class="time">${valList[i].time}</i>
                                        </p>
                                    </div>`)
            } else {
                $(appendSendRcvMessage).append(`<div class="received-msg">
                                        <p class="msg-content">
                                        ${valList[i].message}
                                        <i class="time">${valList[i].time}</i>
                                        </p>
                                    </div>`)
                /*$(".received-msg").appendTo(appendSendRcvMessage).append(
                    $("<p>").addClass("msg-content").text(valList[i].message),
                    $("<i>").addClass("time").text(valList[i].time)
                );*/
            }
        }
    })
    dateBox.text("Today").appendTo(outerDateBox);
}

function addMessage(sender, message) {
    var ul = $("#chat-msg");
    var li = $("<li>").addClass("media");
    var img = $("<img>").addClass("mr-3 rounded-circle").attr("src", "profile.jpg").attr("alt", "Profile Image");
    var div = $("<div>").addClass("media-body");
    var h5 = $("<h5>").addClass("mt-0 mb-1").text(sender);
    var p = $("<p>").text(message);
    div.append(h5, p);
    li.append(img, div);
    ul.append(li);
}

function checkName(username, displayName) {
    return username.toLowerCase() === loggedIn_un.toLowerCase() ? "You" : displayName;
}


function onMessageRecieved(payload) {
    var payloadBody = JSON.parse(payload.body)

    // recorrect the code
    const isLoggedIn = payloadBody.sender.toLowerCase() === loggedIn_name.toLowerCase();
    var currentTime = new Date();

    const messageType = isLoggedIn ? 'send-msg' : 'received-msg';
    const alignment = isLoggedIn ? 'end' : 'start';

    $(".append-msg").append(`<div class="d-flex align-items-${alignment} flex-column ${messageType}">
            <p class="msg-content">
                ${payloadBody.message}
                <i class="time">${currentTime.getHours()}:${currentTime.getMinutes()}</i>
            </p>
        </div>`);

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
}


$("#msg-form").on("submit", function (event) {
    sendMessage();
    // event.preventDefault();
});


