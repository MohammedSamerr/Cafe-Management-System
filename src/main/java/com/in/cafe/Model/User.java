package com.in.cafe.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")

//@Data is shorthand for using several Lombok annotations, including @Getter, @Setter, @RequiredArgsConstructor, @ToString, and @EqualsAndHashCode
@Data

@Entity

//control how SQL statements are generated for inserting and updating entities in the database
@DynamicInsert
@DynamicUpdate

@Table(name = "user")

//Serializable enables an object to be converted into a byte stream and later reconstructed from that byte stream
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
