package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import it.polimi.tiw.projects.DAO.AuctionDAO;
import it.polimi.tiw.projects.DAO.BidDAO;
import it.polimi.tiw.projects.beans.Auction;
import it.polimi.tiw.projects.beans.AuctionJS;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.beans.Bid;

import it.polimi.tiw.projects.connection.ConnectionHandler;

@WebServlet("/LoadBuyingAuctions")
@MultipartConfig
public class LoadBuyingAuctions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
     
    public LoadBuyingAuctions() {
        super();
    }
    
    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuctionDAO auctionDAO = new AuctionDAO(connection);
		BidDAO bidDao = new BidDAO(connection);
		List<Auction> auctions = null;
		List<AuctionJS> auctionsJS = new ArrayList<AuctionJS>();
		HttpSession s = request.getSession(false);
		User user = (User) s.getAttribute ("user");
		
		
		//questo controllo verrà fatto con i filtri
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401
			response.getWriter().write("DB error");
		}
		
		try {
			auctions = auctionDAO.descendingOrderOpenAuction (user);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
			response.getWriter().write("DB error");
		}
		

		
		for (Auction a : auctions) {
			AuctionJS auctionJS = new AuctionJS();
			auctionJS.setId_Code(a.getId_Code());
			auctionJS.setMinimum_Offset(a.getMinimum_Offset());
			LocalDateTime ldt = a.getExpiry_Date_Time();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			String formattedDateTime = ldt.format(formatter);
			auctionJS.setExpiry_Date_Time(formattedDateTime);
			auctionsJS.add(auctionJS);
			float curr;
			try {
				curr = bidDao.getHighestBid(a.getId_Code());
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			auctionJS.setCurrent_Bid(curr);
			
		}
		
		Gson json = new Gson();
		String jsonString = json.toJson(auctionsJS);
		response.setStatus(HttpServletResponse.SC_OK); //200
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString);
	}
	
	public void destroy () {
		try {
			ConnectionHandler.closeConnection(connection);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
