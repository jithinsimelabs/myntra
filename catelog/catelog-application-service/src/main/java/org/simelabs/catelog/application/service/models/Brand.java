package org.simelabs.catelog.application.service.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Document
public class Brand {
    @Id
    private String id;

    @Column(unique = true)
    private String name;

}
