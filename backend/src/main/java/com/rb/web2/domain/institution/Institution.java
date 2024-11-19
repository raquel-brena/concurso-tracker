package com.rb.web2.domain.institution;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "institutions")
@Entity(name = "institutions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Institution { 
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // Talvez mudar para o tipo UUID

    @Column(nullable = false)
    private String name;

    @Column
    private String location;

    @Column(nullable = false)
    private boolean ativo;

    public Institution(String name, String location) {
        this.name = name;
        this.location = location;
        this.ativo = true;
    }
}