package com.manoriega;

import io.quarkus.vertx.web.Route;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.MultiMap;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.mutiny.core.eventbus.Message;
import io.vertx.reactivex.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class GreetingResourceReactive {

    @Inject
    private EventBus eventBus;


    @Route(path = "/getstring", methods = HttpMethod.GET, produces = MediaType.TEXT_PLAIN)
    public Uni<String> getString(RoutingContext routingContext) {
        String name = routingContext.request().getParam("nombre");
        return eventBus.<String>request("greeting4", name).onItem().transform(Message::body);
    }

    //request http://localhost:8080/persona?nombre=manuel&apellido=noriega&edad=33
    @Route(path = "/persona", methods = HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Uni<PersonaDTO> getPersona(RoutingContext routingContext) {

        return eventBus.<PersonaDTO>request("persona",
                PersonaDTO.builder().nombre(routingContext
                        .request()
                        .getParam("nombre"))
                        .apellido(routingContext
                                .request()
                                .getParam("apellido"))
                        .edad(Integer.parseInt(routingContext
                                .request()
                                .getParam("edad")))
                        .build())
                .onItem()
                .transform(Message::body);
    }

    @Route(path = "/reactive", methods = HttpMethod.GET)
    public Uni<StringResponse> testReactive1(RoutingContext context) {
        String name = context.request().getParam("nombre");
//        eventBus.request("greeting2", name).onItem().transform(Message::body);
//        context.response().end(Json.encode(response));

        return Uni.createFrom()
                .item(context)
                .onItem()
                .transformToUni(context1 -> eventBus.<StringResponse>request("Greeting3", name))
                .onItem()
                .transform(Message::body);


//    @Route(path = "/reactive", methods = HttpMethod.POST)
//    public Uni<StringResponse> postReactive(RoutingContext context){
//        StringResponse response = new StringResponse();
//        response.setName("reactive");
//       return Uni.createFrom().item(response);
//    }

    }
}