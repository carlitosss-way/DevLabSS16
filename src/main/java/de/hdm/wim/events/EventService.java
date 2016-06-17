package de.hdm.wim.events;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.Token;

/**
 * Test class for JPA testing within JEE environment
 * @author Jens
 *
 */
@Path("/events")
public class EventService {
	private static KieServices kieServices;
	private static KieContainer kieContainer;
	private static KieSession kieSession;
	
	static {
		//set everything up
		kieServices = KieServices.Factory.get();
		kieContainer = kieServices.getKieClasspathContainer();
		kieSession = kieContainer.newKieSession();
	}
	
	public static void main( String[] args) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Token t = new Token();
			t.setId("1");
			t.setKeyword("amg");
			t.setTimestamp( new Date());
			
			String jsonInString = mapper.writeValueAsString(t);
					//writerWithDefaultPrettyPrinter().writeValueAsString(t);
			System.out.println(jsonInString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() {
		return Response.status(200).entity("test successful").build();
	}

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response insertToken(Token token) throws JsonProcessingException {
		//kieSession.addEventListener( new EventStorageInterceptor());
		try {
			insert(kieSession, "SpeechTokenEventStream", token);
			kieSession.fireAllRules(); //TODO: this should run in a separate thread or something, so we check for correlation every X seconds. or after Y events got inserted.
		} catch (ParseException e) {
			System.out.println("A ParseException happened during creation of SpeechTokenEvents: " + e.getMessage());
		} finally {
			//kieSession.dispose();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(token);
        return Response.status(200).entity(jsonInString).build();
	}
	
	private static void insert(KieSession kieSession, String stream, Event event) {
		//insert event
		SessionPseudoClock pseudoClock = kieSession.getSessionClock();
		EntryPoint ep = kieSession.getEntryPoint(stream);
		ep.insert(event);
		
		//advance pseudoClock(System) time to event time
		long advanceTime = ((Event) event).getTimestamp().getTime() - pseudoClock.getCurrentTime();
		pseudoClock.advanceTime(advanceTime, TimeUnit.MILLISECONDS);
	}
}
