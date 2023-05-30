package javaee8;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/userposts")
public class UserPostService {

	@Inject
	@ConfigProperty(name = "api.userposts.url")
	private String userPostAPI;

	private Client client;

	@PostConstruct
	public void initClient() {
		client = ClientBuilder.newBuilder().connectTimeout(5, TimeUnit.SECONDS).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserPosts() {
		System.out.println("API = " + userPostAPI);
		// return "{}";
		WebTarget target = client.target(userPostAPI);
		JsonArray posts = target.request().get(JsonArray.class);
		// System.out.println("POSTS = " + posts);

		JsonArrayBuilder arr = Json.createArrayBuilder();
		Iterator<JsonValue> iterator = posts.iterator();
		while (iterator.hasNext()) {
			JsonValue p = iterator.next();
			System.out.println("POST = " + p);
			String body = p.asJsonObject().getString("body");
			System.out.println("body = " + body);

			arr.add(body);
		}
		return Response.ok(arr).build();

	}

}
