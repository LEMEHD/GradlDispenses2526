package org.isfce.pid.GradleDispenses2526.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // Pour que le frontend reçoive un objet {code, name} et pas juste une string
public enum Section {
    INFO("Informatique, Développement d'applications | Bachelier"),
    COMPTA("Comptabilité | Bachelier"),
    MARKETING("Marketing | Bachelier"),
    ASSISTANT("Assistant de Direction | Bachelier");

    private final String label;

    Section(String label) {
        this.label = label;
    }
    
    // Pour faciliter la récupération du code
    public String getCode() {
        return this.name();
    }
}