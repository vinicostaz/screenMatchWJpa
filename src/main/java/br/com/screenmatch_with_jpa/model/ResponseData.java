package br.com.screenmatch_with_jpa.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseData( @JsonAlias(value = "translatedText") String translatedText) {
}