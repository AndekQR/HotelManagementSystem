package com.app.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="UNIQUE_NAME")
    private String uniqueName;
    @Column(name="EMAIL")
    private String email;
    @Column(name="PASSWORD")
    private String password;

    public void setId(Integer id) {
        this.id=id;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName=uniqueName;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((id == null)? 0 : id.hashCode());
        result = prime * result + ((uniqueName == null)? 0 : uniqueName.hashCode());
        result = prime * result + ((email == null)? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other =(User) obj;
        if (this.id == null){
            if (other.id != null)
                return false;
        }
        else if (!uniqueName.equals(other.uniqueName))
            return false;
        else if (!email.equals(other.email))
            return false;

        return true;

    }
}
