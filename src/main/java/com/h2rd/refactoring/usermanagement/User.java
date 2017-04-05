package com.h2rd.refactoring.usermanagement;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class User implements Comparable {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 1)
    private List<String> roles = new ArrayList<String>();

    public User() {

    }

    public User(String email, String name, List<String> roles) {
        this.setName(name);
        this.setEmail(email);
        this.setRoles(roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(email, user.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(email)
                .toHashCode();
    }

    public int compareTo(Object o) {
        User myClass = (User) o;
        return new CompareToBuilder().append(this.email, myClass.email).toComparison();
    }
}
