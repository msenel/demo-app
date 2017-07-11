package me.senel.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import me.senel.demo.util.json.View;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "get.user.by.email", query = "from User u where u.email=:email"),
        @NamedQuery(name = "get.all.users", query = "from User u")
})
public class User {

    @Id
    @Column(name = "user_id")
    @JsonView({View.Public.class, View.Private.class})
    private String id;

    @Column(name = "first_name")
    @JsonView({View.Public.class, View.Private.class})
    private String firstName;

    @Column(name = "last_name")
    @JsonView({View.Private.class})
    private String lastName;

    @Column(name = "email")
    @JsonView({View.Private.class})
    private String email;


    @JsonIgnore
    private String accessToken;

    @JsonView({View.Private.class})
    private Integer role;

    @JsonView({View.Private.class})
    @Column(name = "date_of_birth_millis")
    private long dateOfBirthMillis;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public long getDateOfBirthMillis() {
        return dateOfBirthMillis;
    }

    public void setDateOfBirthMillis(long dateOfBirthMillis) {
        this.dateOfBirthMillis = dateOfBirthMillis;
    }
}
