package com.szymon.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime time;

    private UUID uuid;

    public Log(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
    }

    public Log() {

    }
}
