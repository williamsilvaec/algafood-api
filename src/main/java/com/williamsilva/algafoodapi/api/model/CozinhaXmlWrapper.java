package com.williamsilva.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.williamsilva.algafoodapi.domain.model.Cozinha;

import java.util.Collections;
import java.util.List;

/*
 * @JacksonXmlRootElement é uma alternativa à @JsonRootName e
 * @JacksonXmlProperty à @JsonProperty.
 *
 * A diferença é que as anotações iniciadas com @JacksonXml só afetam
 * a serialização em XML. Já as anotações iniciadas com @Json
 * afetam tanto a serialização JSON como também XML.
 */
@JsonRootName("cozinhas")
//@JacksonXmlRootElement(localName = "cozinhas")
public class CozinhaXmlWrapper {

    @JacksonXmlProperty(localName = "cozinha")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Cozinha> cozinhas;

    public CozinhaXmlWrapper(List<Cozinha> cozinhas) {
        this.cozinhas = cozinhas;
    }

    public List<Cozinha> getCozinhas() {
        return Collections.unmodifiableList(cozinhas);
    }
}
