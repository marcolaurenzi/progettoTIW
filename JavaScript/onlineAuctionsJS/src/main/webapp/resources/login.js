window.onload = function() {
	document.getElementById ("loginForm").addEventListener("submit", login, false);
}

//called when form submits
function login (e) {
      var form = e.target.closest("form");
      if (form.checkValidity()) {
        e.preventDefault();
        makeCall("POST", "Login", form,
          function(req) { // callback
            if (req.readyState === 4) { // response has arrived
              if (req.status === 200) { // if no errors
                alert("login successful");
                window.location.href = "home.html";
              } 
              else if (req.status === 400) {
				let message = "invalid parameters"
				//creare il node per il messaggio e appenderlo all'HTML
				errorMessage = document.getElementById("errorMessage");
				errorMessage.textContent = message;
              }
              else if (req.status === 500) {
				let message = "internal server error"
				//creare il node per il messaggio e appenderlo all'HTML
				errorMessage = document.getElementById("errorMessage");
				errorMessage.textContent = message;
			  }
              else if (req.status === 401) {
				let message = "unauthorized"
				//creare il node per il messaggio e appenderlo all'HTML
				errorMessage = document.getElementById("errorMessage");
				errorMessage.textContent = message;
			  }
			  else {
				let message = "unexpected behaviour"
				//creare il node per il messaggio e appenderlo all'HTML
				errorMessage = document.getElementById("errorMessage");
				errorMessage.textContent = message;
			  }
            }
          }
        );
      }
      else {
        form.reportValidity(); // trigger the client-side HTML error messaging
      }
}