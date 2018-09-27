package com.taskagile.utils;

import java.io.Serializable;
import java.util.Objects;

public class Size implements Serializable {

  private static final long serialVersionUID = -4143050815950980095L;

  private int width;
  private int height;

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Size)) return false;
    Size size = (Size) o;
    return height == size.height &&
      width == size.width;
  }

  @Override
  public int hashCode() {
    return Objects.hash(height, width);
  }

  @Override
  public String toString() {
    return width + "x" + height;
  }
}
