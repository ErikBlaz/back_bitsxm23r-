package api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.controllers.users.TxRegister;
import domain.controllers.users.TxGetLoggedUser;
import domain.controllers.users.TxLogIn;

@Path("/users")
public class Users {

	@Context
	private HttpServletRequest request;

	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/logged")
	public Response getLoggedUser() {
		String key = getCookie("key", request.getCookies());
		if(key.equals("null"))
			return Response.status(401).header("Access-Control-Allow-Origin", "*").build();
		TxGetLoggedUser tx = new TxGetLoggedUser(key);
		tx.execute();
		return Response.ok(tx.getResult().toJSON().toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/")
	public Response createUser(String content) {
		JSONObject json;
		try {
			json = new JSONObject(content);
			String username = json.getString("username");
			String password = json.getString("password");
			String email = json.getString("email");
			TxRegister tx = new  TxRegister(username, password, email);
			tx.execute();
			return Response.ok(tx.getResult()).header("Access-Control-Allow-Origin", "*").build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/login")
	public Response logIn(String content) {
		JSONObject json;
		try {
			json = new JSONObject(content);
			String username = json.getString("username");
			String password = json.getString("password");
			TxLogIn tx = new  TxLogIn(username, password);
			tx.execute();
			String result = tx.getResult();
			ResponseBuilder response = Response.ok(result);
			response = response.header("Set-Cookie", "key=" + result+";Path=/Bitsx;Max-Age=86400");
			return response.build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@OPTIONS
	@Path("/")
	public Response options(){
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Headers", "*").header("Access-Control-Allow-Methods", "OPTIONS,GET,PUT,DELETE,POST").build();
	}

	@OPTIONS
	@Path("/login")
	public Response options2(){
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Headers", "*").header("Access-Control-Allow-Methods", "OPTIONS,GET,PUT,DELETE,POST").build();
	}

	private String getCookie(String key, Cookie[] cookies){
		if(cookies == null) return "null";
		for(Cookie c : cookies){
			if(c.getName().equals(key)) return c.getValue();
		}
		return "null";
	}
}