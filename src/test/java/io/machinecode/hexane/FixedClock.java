package io.machinecode.hexane;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class FixedClock extends Clock {
  static long time;

  @Override
  long currentTime() {
    return time;
  }

  static void setTime(final long time) {
    FixedClock.time = time;
  }
}
