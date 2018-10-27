import EventBus from 'vertx3-eventbus-client'

let messageContainer = document.getElementById("results");

let eb = new EventBus('//' + location.host + '/eventbus');
eb.onopen = () => {

  eb.registerHandler('app.chat.data.processed', (err, msg) => {
    if (!err) {

      messageContainer.innerHTML = "";
      for (let entry of msg.body) {

        let message = entry.message.split("_")[0];
        let ip = entry.message.split("_")[1];

        messageContainer.innerHTML += "<tr><td>" + message + "</td><td>" + ip + "</td></tr>";
      }
    } else {

      console.error("err ", err);
      messageContainer.innerHTML = "Cannot get any data";
    }
  });
};

let btn = document.getElementById("send-btn");
btn.onclick = function() {

  let input = document.getElementById("input");

  eb.send("app.chat.data.input", {"value": input.value} );
  input.value = "";
};