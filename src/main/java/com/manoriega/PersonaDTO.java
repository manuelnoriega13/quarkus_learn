package com.manoriega;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PersonaDTO {
    private String nombre;
    private String apellido;
    private Integer edad;
}
