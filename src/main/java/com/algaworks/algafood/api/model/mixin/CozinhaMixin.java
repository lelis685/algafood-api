package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;

}
