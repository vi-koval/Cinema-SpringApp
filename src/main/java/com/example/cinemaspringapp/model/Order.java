package com.example.cinemaspringapp.model;

import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean bought = false;
    private int time;
    private int seat;
    private int price;

    @Getter(AccessLevel.NONE)
    private LocalDateTime date;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private User user;

    @Builder
    public Order (int seat, boolean bought, int time,  LocalDateTime date, User user, Movie movie){
        this.seat = seat;
        this.date = date;
        this.user = user;
        this.movie = movie;
        this.bought = bought;
        this.time = time;
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }
}
