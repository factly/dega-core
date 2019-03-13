package com.factly.dega.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the DegaUser entity.
 */
public class KeyCloakUserDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    private Boolean enabled;

    private Boolean emailVerified;

    @NotNull
    private String email;

    private String username;

    public KeyCloakUserDTO(String id, String firstName, String lastName, @NotNull Boolean enabled, Boolean emailVerified, @NotNull String email, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.emailVerified = emailVerified;
        this.email = email;
        this.username = username;
    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyCloakUserDTO that = (KeyCloakUserDTO) o;

        if (!id.equals(that.id)) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (!enabled.equals(that.enabled)) return false;
        if (!emailVerified.equals(that.emailVerified)) return false;
        if (!email.equals(that.email)) return false;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + enabled.hashCode();
        result = 31 * result + emailVerified.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
