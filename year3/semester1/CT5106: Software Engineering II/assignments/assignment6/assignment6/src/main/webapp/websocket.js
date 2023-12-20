/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// socket for the session
var socket;

function onMessage(event) {
    var data = JSON.parse(event.data);

    // if the action is "join", create the forum on the index.html page
    if (data.action === "join") {
        document.getElementById("content").innerHTML = 
            "<h1>" + data.forum + "</h1><br>" +
            "<textarea id='messagebox' readonly></textarea><br>" +
            "<h2>Post a comment on the forum</h2>" +
            "<input type='text' id='inputbox'>" +
            // make the button always pass the correct forum and username to the onButtonClick method
            "<button type='button' onclick='onButtonClick(`" + data.forum + "`,`" + data.username + "`)'>Post</button>";

        var messagebox = document.getElementById("messagebox");

        // looping over each post in the event data and appending it to the messagebox
        for(let post of data.posts){
            document.getElementById("messagebox").innerHTML += post + "\n";
        }
    }

    // else if action is "update", update the textarea with the posts in the data object
    else if (data.action === "update") {
         document.getElementById("messagebox").innerHTML += data.posts + "\n";
    }
}

// function that's ran when the post button is clicked
function onButtonClick(forum, username) {
    var messageData = {
        action: "update",
        // data will be guaranteed to be defined if the button exists to be clicked, as it is only created in onMessage()
        forum: forum,
        // prepending the user's username onto the message for convenience
        // since there is no real signing-in, we will never need to know who posted which message except for displaying it
        message: username + ": " + document.getElementById("inputbox").value
    };

    socket.send(JSON.stringify(messageData));

    // reset the inputbox to be empty
    document.getElementById("inputbox").value = "";
}


function showForm(){ document.getElementById("forumForm").style.display = ""; }

function hideForm() { document.getElementById("forumForm").style.display = "none"; }

function formSubmit() {
    // request to open a websocket with the server application
    socket = new WebSocket("ws://localhost:8080/assignment6/actions");

    // define the socket's onopen function
    socket.onopen = () => {
        // user data to send to the server
        var user = {
            action: "join",
            username: document.getElementById("forumForm").elements["username"].value,
            forum: document.getElementById("forumForm").elements["forum"].value
        };

        // hide the form and reset it
        hideForm();
        document.getElementById("forumForm").reset();

        // send user data to the server to be instantiated
        socket.send(JSON.stringify(user));
    };

    // set socket's onmessage function
    socket.onmessage = onMessage;
}
