var ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/cw/chat");
}

function sendMessage(message){
    ws.send(message);
}

function closeSocket() {
    if(isConnect()){
        ws.close();
    }
}

function isConnect(){
    if(ws.readyState == ws.OPEN){
        return true;
    }else{
        return false;
    }
}

