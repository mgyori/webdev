package hu.unideb.webdev.model;

import lombok.*;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Staff {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private Store store;
    private String username;
    private String password;
    private Address address;
}
