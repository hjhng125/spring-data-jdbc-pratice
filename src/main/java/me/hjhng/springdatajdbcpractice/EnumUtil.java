package me.hjhng.springdatajdbcpractice;

public class EnumUtil {

  private EnumUtil() {
  }

  public static <T extends Enum<T>> String toString(final Enum<T> source) {
    if (source == null) {
      return null;
    }

    return source.name();
  }
}
