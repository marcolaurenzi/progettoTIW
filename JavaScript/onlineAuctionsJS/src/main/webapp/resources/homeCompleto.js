window.onload = function() {

  // setting the visibility of DOM nodes
  //non sappiamo se l'assegnamento della classe con class name sovrascrive tutte le classi presenti
  //con la nuova
  //partiamo nella buying page
  var sellingLink = document.getElementById("sellingLink");
  sellingLink.className = "displayed";
  var buyingLink = document.getElementById("buyingLink");
  buyingLink.className = "masked";
  var sellingAuctions = document.getElementById("sellingPage");
  sellingAuctions.className = "masked";
  var buyingAuctions = document.getElementById("buyingPage");
  buyingAuctions.className = "displayed";
  var auctionDetails = document.getElementById("auctionDetails");
  auctionDetails.className = "masked";
  var biddingPage = document.getElementById("biddingPage");
  biddingPage.className = "masked";
  var searchingForm = document.getElementById("searchingForm");
  searchingForm.className = "displayed"

  //adding event listeners for the forms
  document.getElementById ("auctionCreationForm").addEventListener("submit", createAuction, false);
  document.getElementById("biddingForm").addEventListener("submit", submitBid, false);
  document.getElementById("searchingForm").addEventListener("submit", submitSearch, false);

  // setting the event listeners for the item creation form
  document.getElementById("itemForm").addEventListener("submit", function(e) {
    e.preventDefault();
    var name = this.itemName.value;
    var description = this.itemDescription.value;
    var itemPrice = this.itemPrice.value;
    var itemsBody = document.getElementById("itemsBody");
    var node = document.createElement("tr");
    node.className = "draggable";
    var nameCell = document.createElement("td");
    nameCell.className = "name";
    nameCell.textContent = name;
    node.appendChild(nameCell);
    var itemPriceCell = document.createElement("td");
    itemPriceCell.textContent = itemPrice;
    node.appendChild(itemPriceCell);
    var descriptionCell = document.createElement("td");
    descriptionCell.className = "description";
    descriptionCell.textContent = description;
    node.appendChild(descriptionCell);
    itemsBody.appendChild(node);
    this.reset();
  }, false);
}

// setting the event listeners for the selling auctions
var sellingBody = document.getElementById("sellingAuctionsBody");
var sellingAuctions = sellingBody.querySelectorAll(".id");
for(let i = 0; i < sellingAuctions.length; i++) {
  sellingAuctions[i].addEventListener("click", clickSellingAuction , false);
}

// setting the event listeners for the buying auctions
var buyingBody = document.getElementById("buyingAuctionsBody");
var buyingAuctions = buyingBody.querySelectorAll(".id");
var offsets = buyingBody.querySelectorAll(".minimumOffset");
for(let i = 0; i < buyingAuctions.length; i++) {
  buyingAuctions[i].addEventListener("click", function() {
    var biddingPage = document.getElementById("biddingPage");
    var auctionIdMessage = biddingPage.querySelector("#auctionIdMessage");
    var minimumOffsetMessage = biddingPage.querySelector("#minimumOffsetMessage");

    // remove the previous auction id message
    if(auctionIdMessage.hasChildNodes()) {
      auctionIdMessage.removeChild(auctionIdMessage.firstChild);
    }

    // remove the previous minimum offset message
    if(minimumOffsetMessage.hasChildNodes()) {
      minimumOffsetMessage.removeChild(minimumOffsetMessage.firstChild);
    }
    biddingPage.className = "displayed";
    
    // STRANO, MA FUNZIONA
    auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + buyingAuctions[i].textContent));
    minimumOffsetMessage.appendChild(document.createTextNode("Minimum offset: " + offsets[i].textContent));
  }, false);
}

// setting the event listeners for the buying winning auctions
var buyingBody = document.getElementById("buyingWinningAuctionsBody");
var buyingAuctions = buyingBody.querySelectorAll(".id");
var offsets = buyingBody.querySelectorAll(".minimumOffset");
for(let i = 0; i < buyingAuctions.length; i++) {
  buyingAuctions[i].addEventListener("click", function() {
    var biddingPage = document.getElementById("biddingPage");
    var auctionIdMessage = biddingPage.querySelector("#auctionIdMessage");
    var minimumOffsetMessage = biddingPage.querySelector("#minimumOffsetMessage");

    // remove the previous auction id message
    if(auctionIdMessage.hasChildNodes()) {
      auctionIdMessage.removeChild(auctionIdMessage.firstChild);
    }

    // remove the previous minimum offset message
    if(minimumOffsetMessage.hasChildNodes()) {
      minimumOffsetMessage.removeChild(minimumOffsetMessage.firstChild);
    }
    biddingPage.className = "displayed";
    
    // STRANO, MA FUNZIONA
    auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + buyingAuctions[i].textContent));
    minimumOffsetMessage.appendChild(document.createTextNode("Minimum offset: " + offsets[i].textContent));
  }, false);
}

// setting the event listeners for the buying won auctions
var buyingBody = document.getElementById("buyingWonAuctionsBody");
var buyingAuctions = buyingBody.querySelectorAll(".id");
var offsets = buyingBody.querySelectorAll(".minimumOffset");
for(let i = 0; i < buyingAuctions.length; i++) {
  buyingAuctions[i].addEventListener("click", function() {
    var biddingPage = document.getElementById("biddingPage");
    var auctionIdMessage = biddingPage.querySelector("#auctionIdMessage");
    var minimumOffsetMessage = biddingPage.querySelector("#minimumOffsetMessage");

    // remove the previous auction id message
    if(auctionIdMessage.hasChildNodes()) {
      auctionIdMessage.removeChild(auctionIdMessage.firstChild);
    }

    // remove the previous minimum offset message
    if(minimumOffsetMessage.hasChildNodes()) {
      minimumOffsetMessage.removeChild(minimumOffsetMessage.firstChild);
    }
    biddingPage.className = "displayed";
    
    // STRANO, MA FUNZIONA
    auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + buyingAuctions[i].textContent));
    minimumOffsetMessage.appendChild(document.createTextNode("Minimum offset: " + offsets[i].textContent));
  }, false);
}

// setting the selling link onclick event listener
sellingLink.onclick = function() {
  var element1 = document.getElementById("buyingPage");
  element1.className = "masked";
  var element2 = document.getElementById("sellingPage");
  element2.className = "displayed";
  var buyingLink = document.getElementById("buyingLink");
  buyingLink.className = "displayed";
  var sellingLink = document.getElementById("sellingLink");
  sellingLink.className = "masked";
  var auctionDetails = document.getElementById("auctionDetails");
  auctionDetails.className = "masked";
  var biddingPage = document.getElementById("biddingPage");
  biddingPage.className = "masked";
  var searchingForm = document.getElementById("searchingForm");
  searchingForm.className = "masked"
};

// setting the buying link onclick event listener
buyingLink.onclick = function() {
  var element1 = document.getElementById("buyingPage");
  element1.className = "displayed";
  var element2 = document.getElementById("sellingPage");
  element2.className = "masked";
  var sellingAuctions = document.getElementById("sellingLink");
  sellingAuctions.className = "displayed";
  var buyingLink = document.getElementById("buyingLink");
  buyingLink.className = "masked";
  var auctionDetails = document.getElementById("auctionDetails");
  auctionDetails.className = "masked";
  var biddingPage = document.getElementById("biddingPage");
  biddingPage.className = "masked";
  var searchingForm = document.getElementById("searchingForm");
  searchingForm.className = "displayed"
};

    // implementation of the submit bid function
    function submitBid (e) {
          e.preventDefault();
          var bidTableBody = document.getElementById("bidTableBody");
          var bids = bidTableBody.querySelectorAll(".amount");
          var bidAmount = this.bidAmount.value;
          var biddingPage = document.getElementById("biddingPage");
          var minimumOffset = biddingPage.querySelector("#minimumOffsetMessage").textContent;

          // calculates the max bid
          var maxBid = Math.max.apply(null, Array.from(bids).map(function(element) {
            return parseFloat(element.textContent);
          }));
          if (bidAmount <= maxBid) {
            this.reset();
            alert("Error: bid must be > " + maxBid);
          } 
          else if(bidAmount - maxBid < minimumOffset ) {
            this.reset();
            alert("Error: bid must be at least > " + maxBid + minimumOffset);
          }
          else {
            var currentDate = new Date().toISOString().slice(0, 10);
            var newRow = document.createElement("tr");

            // creates the date cell
            var dateCell = document.createElement("td");
            dateCell.textContent = currentDate;
            newRow.appendChild(dateCell);

            // create a new cell for the amount and sets the className to "amount"
            var amountCell = document.createElement("td");
            amountCell.className = "amount";
            amountCell.textContent = bidAmount;
            newRow.appendChild(amountCell);

            // appends the new row to the table body
            bidTableBody.appendChild(newRow);

            this.reset();
          }
    }

    // implementation of the submit search function
    function submitSearch (e) {
          e.preventDefault();
          var keyWord = this.keyWord.value;
          alert("Searching for: " + keyWord);
          this.reset();
          // TODO farlo lato server
    }

    //auction creation (evento scatenato dal submit del form)
    function createAuction (e) {
        e.preventDefault();

        var auctionCreationForm = document.getElementById("auctionCreationForm");
        var dateTime = this.dateTime.value;
        dateTime = new Date(dateTime);
        var minimumOffset = this.minimumOffset.value;

        //controllare che la data e l'offset siano validi
        if (minimumOffset <= 0) {
          alert("invalid offset");
          return;
        }
        var now = new Date();

        if (dateTime <= now) {
          alert("invalid expiry date");
          return;
        }

        //se non hai selezionato items con drag and drop mandare messaggio di errore
        var itemsBody = document.getElementById("selectedItemsTableBody");
        var items = itemsBody.querySelectorAll(".id").textContent;

        if (items.length === 0 || items === null) {
            alert("you need to select at least an item to put in the auction");
            return;
        }

        alert("selected items " + items.id);
        //prendere gli items dalla lista degli items (richiedere al server gli item di quell'utente)
        //se gli items ci sono e data e offset sono corretti invocare la servlet di creazione
        //se la creazione ha avuto successo lato server creare la nuova linea


        //creazione della nuova linea della tabella

        var node = document.createElement("tr");
        var idCell = document.createElement("td");
        idCell.className = "id";
        //id calcolato lato server
        idCell.textContent = 50;
        node.appendChild(idCell);
        //inizializiamo current bid a zero perchè nessuno ha fatto offerte
        var currentBidCell = document.createElement("td");
        currentBidCell.textContent = 0;
        node.appendChild(currentBidCellCell);
        //creazione della cella per la data di scadenza
        var expiryDateTimeCell = document.createElement("td");
        expiryDateTimeCell.textContent = dateTime.value;
        node.appendChild(expiryDateTimeCell);
        //minimum offset cell creation
        var minimumOffsetCell = document.createElement("td");
        minimumOffsetCell.textContent = minimumOffset.value;
        node.appendChild(minimumOffsetCell);

        itemsBody.appendChild(node);
        auctionCreationForm.reset();

        //se la creazione non ha avuto successo mandare una richiesta di aggiornamento della lista di items
        //al server (magari qualcuno loggato con il mio stesso user da un altro pc ha messo in vendita i miei items)
    }

    // implementation of the click selling auction function
    function clickSellingAuction () {
      var auctionDetails = document.getElementById("auctionDetails");
      var auctionIdMessage = auctionDetails.querySelector("#auctionIdMessage");
      if(auctionIdMessage.hasChildNodes()) {
          auctionIdMessage.removeChild(auctionIdMessage.firstChild);
      }
      auctionDetails.className = "displayed";

      // STRANO, MA FUNZIONA
      auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + sellingAuctions[i].textContent));
    }

    // Drag and Drop implementation
    (function() {

      window.addEventListener("load", () => {
  
        var elements = document.getElementsByClassName("draggable")
        for (let i = elements.length - 1; i >= 0; i--) {
          if(elements[i].querySelector("td").textContent !== "") {
            elements[i].draggable = true;
          }
          elements[i].addEventListener("dragstart", dragStart); //save dragged element reference
          elements[i].addEventListener("dragover", dragOver); // change color of reference element to red
          elements[i].addEventListener("dragleave", dragLeave); // change color of reference element to black
          elements[i].addEventListener("drop", drop); //change position of dragged element using the referenced element 
        }
  
      }, false);
  
  
      let startElement;
  
      /* 
          This fuction puts all row to "notselected" class, 
          then we use CSS to put "notselected" in black and "selected" in red
      */
      function unselectRows(rowsArray) {
          for (var i = 0; i < rowsArray.length; i++) {
              rowsArray[i].className = "notselected";
          }
      }
  
  
      /* 
          The dragstart event is fired when the user starts 
          dragging an element (if it is draggable=True)
          https://developer.mozilla.org/en-US/docs/Web/API/Document/dragstart_event
      */
      function dragStart(event) {
          /* we need to save in a variable the row that provoked the event
           to then move it to the new position */
          startElement = event.target.closest("tr");
      }
  
      /*
          The dragover event is fired when an element 
          is being dragged over a valid drop target.
          https://developer.mozilla.org/es/docs/Web/API/Document/dragover_event
      */
      function dragOver(event) {
          // We need to use prevent default, otherwise the drop event is not called
          event.preventDefault(); 
  
          // We need to select the row that triggered this event to marked as "selected" so it's clear for the user
          var dest = event.target.closest("tr");
  
          // Mark  the current element as "selected", then with CSS we will put it in red
          dest.className = "selected";
      }
  
      /*
          The dragleave event is fired when a dragged 
          element leaves a valid drop target.
          https://developer.mozilla.org/en-US/docs/Web/API/Document/dragleave_event
      */
      function dragLeave(event) {
          // We need to select the row that triggered this event to marked as "notselected" so it's clear for the user 
          var dest = event.target.closest("tr");
  
          // Mark  the current element as "notselected", then with CSS we will put it in black
          dest.className = "notselected";
      }
  
      /*
          The drop event is fired when an element or text selection is dropped on a valid drop target.
          https://developer.mozilla.org/en-US/docs/Web/API/Document/drop_event
      */
      function drop(event) {
          
          // Obtain the row on which we're dropping the dragged element
          var dest = event.target.closest("tr");
          var tableDestination = event.target.closest("table");
          // Obtain the index of the row in the table to use it as reference
          // for changing the dragged element position
          var table = dest.closest('table');
          var rowsArray = Array.from(table.querySelectorAll('tbody > tr'));
          var rowsArray2 = Array.from(tableDestination.querySelectorAll('tbody > tr'));
          var indexDest = rowsArray.indexOf(dest);

          if (startElement.closest("table") !== tableDestination) {
            
            var tableBody = table.querySelector("tbody");
            tableBody.appendChild(startElement);
            
            
          }
  
          // Move the dragged element to the new position
          if (rowsArray.indexOf(startElement) < indexDest)
              // If we're moving down, then we insert the element after our reference (indexDest)
              startElement.parentElement.insertBefore(startElement, rowsArray[indexDest + 1]);
          else
              // If we're moving up, then we insert the element before our reference (indexDest)
              startElement.parentElement.insertBefore(startElement, rowsArray[indexDest]);
  
          // Mark all rows in "not selected" class to reset previous dragOver
          unselectRows(rowsArray);
          unselectRows(rowsArray2);
      }
  
  
  })();
