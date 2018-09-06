package com.taskagile.domain.common.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractBaseId implements Serializable {

  private static final long serialVersionUID = 3435210296634626689L;

  private long id;

  public AbstractBaseId(long id) {
    this.id = id;
  }

  public long value() {
    return id;
  }

  public boolean isValid() {
    return id > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractBaseId)) return false;
    AbstractBaseId that = (AbstractBaseId) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
