package edu.uees.tutorias.domain;

import java.util.Objects;

public abstract class User {

    private final String id;
    private final String name;
    private final String email;

    protected User(String id, String name, String email) {
        this.id = requireText(id, "User id is required");
        this.name = requireText(name, "User name is required");
        this.email = requireText(email, "User email is required");
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    private String requireText(String value, String message) {
        Objects.requireNonNull(value, message);

        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }
}
