package org.simelabs.catelog.application.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Document
public class Category {

    @Id
    private String id;
    @Column(unique = true)
    private String name;
}
