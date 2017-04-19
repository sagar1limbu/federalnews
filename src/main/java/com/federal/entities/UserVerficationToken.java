package com.federal.entities;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sagar on 4/18/17.
 */
@Entity
public class UserVerficationToken {

    private static final int EXPIRATION=60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Users users;

    private Date expiryDate;

    public UserVerficationToken(){}

    public UserVerficationToken(final String token, final Users users) {

        super();
        this.token = token;
        this.users = users;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final  int expiryTimeinMinutes){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE,expiryTimeinMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token){
        this.token=token;
        this.expiryDate= calculateExpiryDate(EXPIRATION);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
