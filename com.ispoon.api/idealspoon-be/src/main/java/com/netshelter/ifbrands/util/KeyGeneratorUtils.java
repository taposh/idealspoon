package com.netshelter.ifbrands.util;

import java.util.Random;

/**
 * Utilities to generate unique keys across a system.  Simple implementations provided.
 * @author bgray
 * @author ekrevets
 *
 */
public class KeyGeneratorUtils
{
  /**
   * Basic interface for a KeyGenerator.
   */
  public static interface KeyGenerator
  {
    public String generateKey();
  }

  /*
   * Factory methods for reference implementations.
   */
  private static SimpleKeyGenerator simpleKeyGenerator = new SimpleKeyGenerator();
  public static KeyGenerator getSimpleGenerator()
  {
    return simpleKeyGenerator;
  }

  /**
   * Simple generator based on timestamp and random numbers.
   * Generates nigh-unique values.  These values have 0.0001% change of collision within
   * a millisecond interval over the next 80 years.
   * value = System.currentTimeMillis() << 20 | rnd(2^20)
   * Yields a 62 bit number, which can be represented in 12 characters with Base36 encoding.
   * Check here for more UID generator ideas:
   * http://stackoverflow.com/questions/2671858/distributed-sequence-number-generation
   * @author bgray
   *
   */
  public static class SimpleKeyGenerator implements KeyGenerator
  {
    private static final int RADIX = 36;
    private static final int KEY_SIZE_BITS  = Long.toBinaryString( 0x3fffffffffffffffl ).length();             // Yields 12-char Base36
    private static final int TIME_SIZE_BITS = Long.toBinaryString( System.currentTimeMillis() ).length() + 1;  // Buys us 60 more years
    private static final int RAND_SIZE_BITS = KEY_SIZE_BITS - TIME_SIZE_BITS;
    private static final int RAND_MAX_VALUE = (int)Math.pow( 2, RAND_SIZE_BITS );
    private static final Random R = new Random( System.currentTimeMillis() );

    /**
     * @return String The string key
     */
    @Override
    public String generateKey()
    {
      return Long.toString(( System.currentTimeMillis() << RAND_SIZE_BITS ) |  R.nextInt( RAND_MAX_VALUE ), RADIX );
    }

    @Override
    public String toString()
    {
      return String.format( "radix=%d, keySize=%d, timeSize=%d, randSize=%d, randMax=%d, prob=%.5f%%",
                            RADIX, KEY_SIZE_BITS, TIME_SIZE_BITS, RAND_SIZE_BITS, RAND_MAX_VALUE, 1f/RAND_MAX_VALUE*100 );
    }
  }
}

