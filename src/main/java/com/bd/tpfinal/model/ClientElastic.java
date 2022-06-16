package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "client_elastic")
@Builder
public class ClientElastic {

    @Id
    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuuMMdd")
    private LocalDate dateOfBirth;
}
