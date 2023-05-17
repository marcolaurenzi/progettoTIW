window.onload = function() {
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
};