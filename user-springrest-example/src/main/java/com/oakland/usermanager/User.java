package com.oakland.usermanager;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

/**
 * Model pojo for input and output service methods
 */

public class User implements Serializable {

  private static final long serialVersionUID = -2638132888009264164L;
  private int id;
  private String userName;
  private String password;
  private boolean isSpecial;

  public User() {}

  public User(Builder builder) {
    this.id = builder.id;
    this.userName = builder.userName;
    this.password = builder.password;
    this.isSpecial = builder.isSpecial;
  }

  public static Builder builder() {
    return new Builder();
  }

  public int getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public boolean getIsSpecial() {
    return isSpecial;
  }

  static class Builder {
    private int id;
    private String userName;
    private String password;
    private boolean isSpecial;

    private Builder() {}

    public Builder withId(int id) {
      this.id = requireNonNull(id, "id is null");
      return this;
    }

    public Builder withUserName(String userName) {
      this.userName = requireNonNull(userName, "userName is null");
      return this;
    }

    public Builder withPassword(String password) {
      this.password = requireNonNull(password, "password is null");
      return this;
    }

    public Builder withIsSpecial(boolean isSpecial) {
      this.isSpecial = requireNonNull(isSpecial, "isSpecial is null");
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}

