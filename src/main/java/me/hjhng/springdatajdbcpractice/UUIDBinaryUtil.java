package me.hjhng.springdatajdbcpractice;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDBinaryUtil {

  private UUIDBinaryUtil() {
  }

  public static byte[] toBinary(final UUID source) {
    if (source == null) {
      return null;
    }

    return ByteBuffer.wrap(new byte[16])
        .putLong(source.getMostSignificantBits())
        .putLong(source.getLeastSignificantBits())
        .array();
  }

  public static UUID toUUID(final byte[] source) {
    if (source == null) {
      return null;
    }

    ByteBuffer buffer = ByteBuffer.wrap(source);

    long high = buffer.getLong();
    long low = buffer.getLong();

    return new UUID(high, low);
  }
}
