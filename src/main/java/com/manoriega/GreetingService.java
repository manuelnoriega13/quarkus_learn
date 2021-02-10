package com.manoriega;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @ConsumeEvent(value = "greeting")
    public String consume(String name){
        return name.toUpperCase();
    }


    @ConsumeEvent(value = "greeting2")
    public StringResponse consume2(String name){
        StringResponse response = new StringResponse();
        response.setName(name.toUpperCase() + " -- ");
        return response;
    }

    @ConsumeEvent(value = "greeting3")
    public Uni<StringResponse> consume3(String name){
        StringResponse response = new StringResponse();
        response.setName(name.toUpperCase() + " ROUTING 3");
        return Uni.createFrom().item(response);
    }
    @ConsumeEvent(value = "greeting4")
    public Uni<String> consume4(String name){
        return Uni.createFrom().item(() -> "Hello " + name.toUpperCase() + "! !");
    }

    @ConsumeEvent(value = "persona")
    public Uni<PersonaDTO> getPersona(PersonaDTO personaDTO){
        return Uni.createFrom().item(personaDTO);
    }
}
