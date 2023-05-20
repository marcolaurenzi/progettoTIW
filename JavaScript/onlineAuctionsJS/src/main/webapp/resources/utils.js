/**
 * AJAX call management
 */

/*function makeCall(method, url, formElement, cback, reset = true) {
  let req = new XMLHttpRequest(); // visible by closure
  
  //fare on upload 
  req.onreadystatechange = function() {
    cback(req);
  }; // closure
  req.open(method, url);
  if (formElement === null) {
    req.send();
  } else {
	var f = new FormData(formElement);
    req.send(f);
    // Alert field values
      var fieldValues = "";
      for (var pair of f.entries()) {
        fieldValues += pair[0] + ": " + pair[1] + "\n";
      }
      alert(fieldValues);
  }
  if (formElement !== null && reset === true) {
    formElement.reset();
  }
}*/



function makeCall(method, url, formElement, cback, reset = true) {
  let req = new XMLHttpRequest(); // visible by closure
  req.onreadystatechange = function() {
    cback(req);
  }; // closure
  req.open(method, url);
  if (formElement === null) {
    req.send();
  } else {
    req.send(new FormData(formElement));
  }
  if (formElement !== null && reset === true) {
    formElement.reset();
  }
}
