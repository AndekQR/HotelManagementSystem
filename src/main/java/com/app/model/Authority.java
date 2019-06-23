package com.app.model;

import com.app.helpers.AuthorityType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AuthorityType name;

    public Authority(){}

    public Authority(AuthorityType name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public AuthorityType getName() {
        return name;
    }

    public void setName(AuthorityType name) {
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority=(Authority) o;

        if (!id.equals(authority.id)) return false;
        return name == authority.name;

    }

    @Override
    public int hashCode() {
        int result=id.hashCode();
        result=31 * result + name.hashCode();
        return result;
    }
}
