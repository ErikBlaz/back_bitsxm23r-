package api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.Comment;
import domain.Thread;
import domain.controllers.comments.TxCreateComment;
import domain.controllers.comments.TxViewThreadComments;
import domain.controllers.threads.TxCreateThread;
import domain.controllers.threads.TxViewThread;
import domain.controllers.threads.TxViewThreads;

@Path("/threads")
public class Threads {

	@Context
	private HttpServletRequest request;
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{threadId}")
	public Response getThread(@PathParam("threadId") int threadId) {
		TxViewThread tx = new TxViewThread(threadId);
		tx.execute();
		return Response.ok(tx.getResult().toJSON().toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/")
	public Response getThreads(@QueryParam("theme") String theme) {
		TxViewThreads tx = new TxViewThreads(theme);
		tx.execute();
		return Response.ok(Thread.toJSON(tx.getResult()).toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{threadId}/comments")
	public Response getThreadComments(@PathParam("threadId") int threadId) {
		TxViewThreadComments tx = new TxViewThreadComments(threadId);
		tx.execute();
		return Response.ok(Comment.toJSON(tx.getResult()).toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{threadId}/comments")
	public Response createComment(@PathParam("threadId") int threadId, String content) {
		String token = getCookie("key", request.getCookies());
		if(token.equals("null"))
			return Response.status(401).header("Access-Control-Allow-Origin", "*").build();
		JSONObject json;
		try {
			json = new JSONObject(content);
			String comment = json.getString("comment");
			TxCreateComment tx = new TxCreateComment(threadId, token, comment);
			tx.execute();
			return Response.ok(tx.getResult()).header("Access-Control-Allow-Origin", "*").build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/")
	public Response createThread(String content) {
		JSONObject json;
		String token = getCookie("key", request.getCookies());
		if(token.equals("null"))
			return Response.status(401).header("Access-Control-Allow-Origin", "*").build();
		try {
			json = new JSONObject(content);
			String title = json.getString("title");
			String description = json.getString("description");
			String theme = json.getString("theme");
			TxCreateThread tx = new TxCreateThread(token, title, description, theme);
			tx.execute();
			return Response.ok(tx.getResult()).header("Access-Control-Allow-Origin", "*").build();
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
	@Path("/{threadId}/comments")
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