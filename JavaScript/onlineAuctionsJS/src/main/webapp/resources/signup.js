window.onload = function() {
	document.getElementById ("signUpForm").addEventListener("submit", signup, false);
}

//called when form submits
function signup (e) {
      var form = e.target.closest("form");
      if (form.checkValidity()) {
        e.preventDefault();
        makeCall("POST", "SignUp", form,
          function(req) { // callback
            if (req.readyState === 4) { // response has arrived
              if (req.status === 200) { // if no errors
                alert("signup successful");
                window.location.href = "LoginPage.html";
              } 
              else if (req.status === 400) {
				let message = "invalid parameters"
				//creare il node per il messaggio e appenderlo all'HTML
				errorMessage = document.getElementById("errorMessage");
				errorMessage.textContent = message;
              }
              else { //req.status === 500
				let message = "internal server error"
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