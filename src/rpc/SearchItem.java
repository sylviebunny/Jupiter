package rpc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Item;
import db.DBConnection;
import db.DBConnectionFactory;



/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// allow access only if session exists
//				HttpSession session = request.getSession(false);
//				if (session == null) {
//					response.setStatus(403);
//					return;
//				}

				// optional
				//String userId = session.getAttribute("user_id").toString(); 
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		
		String term = request.getParameter("term");
		
		String userId = request.getParameter("user_id");

		DBConnection connection = DBConnectionFactory.getConnection();
           
		try {
			List<Item> items = connection.searchItems(lat, lon, term);
			Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);

			JSONArray array = new JSONArray();
			for (Item item : items) {
				JSONObject obj = item.toJSONObject();
				obj.put("favorite", favoritedItemIds.contains(item.getItemId()));//tell front-end
				array.put(obj);

				//array.put(item.toJSONObject());
			}
			RpcHelper.writeJsonArray(response, array);
 
          } catch (Exception e) {
        	  e.printStackTrace();
          } finally {
        	  connection.close();
          }
	}
//		TicketMasterAPI tmAPI = new TicketMasterAPI();
//		List<Item> items = tmAPI.search(lat, lon, null);
//		
//		JSONArray array = new JSONArray();
//		for (Item item : items) {
//			array.put(item.toJSONObject());
//		}
//		RpcHelper.writeJsonArray(response, array);
//
//		}
//	
	//response.getWriter().append("Served at: ").append(request.getContextPath());
	  //response.setContentType("text/html");
	  //response.serHeader();
//	  if(request.getParameter("username") != null) {
//		  String username = request.getParameter("username");
//		 	  
//	  	out.print("<html><body>");
//      out.print("<h1>Hello" + username + "World</h1>");
//      out.print("</body></html>"); //print html format
//		out.print("{\"username\": \"abcd\", \"age:\" \"13\"}");//json format
//		  JSONObject obj = new JSONObject();
//		  	  
//		  try {
//			obj.put("username", username);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  
//		  out.print(obj);
//	  }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
