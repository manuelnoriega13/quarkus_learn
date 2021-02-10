package com.manoriega;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
//import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-resteasy")
public class GreetingResource {

    @Inject
    EventBus bus;

    @Path("{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<StringResponse> greeting(@PathParam("name") String name) {
        return bus.<StringResponse>request("greeting2", name).onItem().transform(Message::body);
    }


}