{//defining scope of the variables with this visibility block
	
let buyingAuctionsTable = null;
let buyingAuctionsAlert = document.getElementById ("buyingAuctionsAlert");



window.onload = function() {
	buyingAuctionsTable = new BuyingAuctionsTable(
    document.getElementById("buyingAuctionsTable"),
    document.getElementById("buyingAuctionsTableBody"));

  	buyingAuctionsTable.show();
}


function BuyingAuctionsTable(_buyingAuctionsContainer, _buyingAuctionsContainerBody) {
    this.buyingAuctionsContainer = _buyingAuctionsContainer;
    this.buyingAuctionsContainerBody = _buyingAuctionsContainerBody;

    this.show = function() {
      let self = this; // this not direcly visible in nested functions,
      // need to copy it in a local variable
      makeCall("GET", "LoadBuyingAuctions", null,
        function(req) { // callback of the GET request
          if (req.readyState === 4) {
            if (req.status === 200) {
              let buyingAuctionsToShow = JSON.parse(req.responseText);
              if (buyingAuctionsToShow.length === 0) {
                buyingAuctionsAlert.textContent = "No auctions yet!";
                return;
              }
              self.update(buyingAuctionsToShow); // self visible by closure, view status has topic ID as parameter
            } else { //printing errors
              buyingAuctionsAlert.textContent = req.responseText;
            }
          }
        }
      );
    };

    this.update = function(buyingAuctionsToShow) {
      let row;
      let idCodeCell;
      let minimumOffsetCell;
      let currentBidCell;
      let linktext;
      let anchor;
      let exp_dateCell;
      this.buyingAuctionsContainerBody.innerHTML = ""; // empty the table body
      // build updated list
      let self = this; //  nested function updates the object bound to this, which is not visible directly
      buyingAuctionsToShow.forEach(function(auction) { // self visible in nested function by closure
        
        row = document.createElement("tr");
        
        idCodeCell = document.createElement("td");
        idCodeCell.textContent = auction.id_code;
        //anchor.setAttribute("topicid", topic.id);
        row.appendChild(idCodeCell);
        
        minimumOffsetCell = document.createElement("td");
        minimumOffsetCell.textContent = auction.minimum_offset;
        row.appendChild(minimumOffsetCell);
        
        currentBidCell = document.createElement("td");
        currentBidCell.textContent = auction.current_bid;
        row.appendChild(currentBidCell);
        
        exp_dateCell = document.createElement("td");
        exp_dateCell.textContent = auction.expiry_date_time;
        row.appendChild(exp_dateCell);
        
        
        self.buyingAuctionsContainerBody.appendChild(row);
      });
    };
  }
  
  
  
  } //closing visibility block