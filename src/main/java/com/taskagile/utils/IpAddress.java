package com.taskagile.utils;

import java.io.Serializable;
import java.util.Objects;

public class IpAddress implements Serializable {

  private static final long serialVersionUID = -146284720882028407L;

  private String value;

  public IpAddress(String value) {
    this.value = value == null ? "" : value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IpAddress)) return false;
    IpAddress ipAddress = (IpAddress) o;
    return Objects.equals(value, ipAddress.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "IpAddress{" +
      "value='" + value + '\'' +
      '}';
  }
}
