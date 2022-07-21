'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {
    username = document.querySelector('#name').value.trim();
    let pwd = document.querySelector('#pwd').value;

    var req = new XMLHttpRequest();
	req.responseType = 'json';
	req.open('POST', "/api/auth/signin");	
	req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	req.onload  = function() {
		var jsonResponse = req.response;
		console.log(jsonResponse)
   		location.reload(); 
	};
	let data = {username:username,password:pwd};
	req.send(JSON.stringify(data));
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}
function logout(){
	var req = new XMLHttpRequest();
	req.responseType = 'json';
	req.open('GET', "/api/auth/signout", true);
	req.onload  = function() {
   		location.reload(); 
	};
	req.send(null);
}


document.addEventListener('DOMContentLoaded', function () {
	
	var req = new XMLHttpRequest();
	req.responseType = 'json';
	req.open('GET', "/api/auth/user/profile", true);
	req.onload  = function() {
   		var jsonResponse = req.response;
   		console.log(jsonResponse["username"])
   		username = jsonResponse["username"];

		    if(username) {
		        usernamePage.classList.add('hidden');
		        chatPage.classList.remove('hidden');
		
		        var socket = new SockJS('/ws');
		        stompClient = Stomp.over(socket);
		
		        stompClient.connect({}, onConnected, onError);
		    }
		    event.preventDefault();
	};
	req.send(null);
   
}, false);

//usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
