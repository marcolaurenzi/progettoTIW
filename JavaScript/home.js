window.onload = function() {

    // at the beginning, buying auctions and a link to switch page is displayed
    var sellingLink = document.getElementById("sellingLink");
    sellingLink.className = "displayed";
    var buyingLink = document.getElementById("buyingLink");
    buyingLink.className = "masked";
    var sellingAuctions = document.getElementById("sellingAuctions");
    sellingAuctions.className = "masked";
    var buyingAuctions = document.getElementById("buyingAuctions");
    buyingAuctions.className = "displayed";
    var auctionDetails = document.getElementById("auctionDetails");
    auctionDetails.className = "masked";
    var biddingPage = document.getElementById("biddingPage");
    biddingPage.className = "masked";

    // add event listener to the bidding form
    document.getElementById("biddingForm").addEventListener("submit",
        function(e) {
          e.preventDefault();
          var bidTableBody = document.getElementById("bidTableBody");
          var bids = bidTableBody.querySelectorAll(".amount");
          var bidAmount = this.bidAmount.value;

          // calculates the max bid
          var maxBid = Math.max.apply(null, Array.from(bids).map(function(element) {
            return parseFloat(element.textContent);
          }));
          if (bidAmount <= maxBid) {
            this.reset();
            alert("Error: bid must be > " + maxBid);
          } else {
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
        }, false);
    };

    sellingLink.onclick = function() {
        var element1 = document.getElementById("buyingAuctions");
        element1.className = "masked";
        var element2 = document.getElementById("sellingAuctions");
        element2.className = "displayed";
        var buyingLink = document.getElementById("buyingLink");
        buyingLink.className = "displayed";
        var sellingLink = document.getElementById("sellingLink");
        sellingLink.className = "masked";
        var auctionDetails = document.getElementById("auctionDetails");
        auctionDetails.className = "masked";
        var biddingPage = document.getElementById("biddingPage");
        biddingPage.className = "masked";
    };

    buyingLink.onclick = function() {
        var element1 = document.getElementById("buyingAuctions");
        element1.className = "displayed";
        var element2 = document.getElementById("sellingAuctions");
        element2.className = "masked";
        var sellingAuctions = document.getElementById("sellingLink");
        sellingAuctions.className = "displayed";
        var buyingLink = document.getElementById("buyingLink");
        buyingLink.className = "masked";
        var auctionDetails = document.getElementById("auctionDetails");
        auctionDetails.className = "masked";
        var biddingPage = document.getElementById("biddingPage");
        biddingPage.className = "masked";
    };

    var sellingBody = document.getElementById("sellingAuctionsBody");
    var sellingAuctions = sellingBody.querySelectorAll(".id");
    for(let i = 0; i < sellingAuctions.length; i++) {
        sellingAuctions[i].addEventListener("click", function() {
                var auctionDetails = document.getElementById("auctionDetails");
                var auctionIdMessage = auctionDetails.querySelector("#auctionIdMessage");
                if(auctionIdMessage.hasChildNodes()) {
                    auctionIdMessage.removeChild(auctionIdMessage.firstChild);
                }
                auctionDetails.className = "displayed";

                // STRANO, MA FUNZIONA
                auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + sellingAuctions[i].textContent));
            }, false);
    }

    var buyingBody = document.getElementById("buyingAuctionsBody");
    var buyingAuctions = buyingBody.querySelectorAll(".id");
    for(let i = 0; i < buyingAuctions.length; i++) {
        buyingAuctions[i].addEventListener("click", function() {
                var biddingPage = document.getElementById("biddingPage");
                var auctionIdMessage = biddingPage.querySelector("#auctionIdMessage");
                if(auctionIdMessage.hasChildNodes()) {
                    auctionIdMessage.removeChild(auctionIdMessage.firstChild);
                }
                biddingPage.className = "displayed";

                // STRANO, MA FUNZIONA
                auctionIdMessage.appendChild(document.createTextNode("Auction ID: " + buyingAuctions[i].textContent));
            }, false);
    }