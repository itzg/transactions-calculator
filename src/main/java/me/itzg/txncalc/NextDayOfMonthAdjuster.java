package me.itzg.txncalc;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextDayOfMonthAdjuster implements TemporalAdjuster {

  private final int dayOfMonth;

  public NextDayOfMonthAdjuster(int dayOfMonth) {
    if (dayOfMonth < 1) {
      throw new IllegalArgumentException("dayOfMonth must be >= 1");
    }
    this.dayOfMonth = dayOfMonth;
  }

  @Override
  public Temporal adjustInto(Temporal temporal) {
    if (dayOfMonth <= temporal.get(ChronoField.DAY_OF_MONTH)) {
      return temporal
          .with(ChronoField.DAY_OF_MONTH, 1)
          .plus(1, ChronoUnit.MONTHS)
          .with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    } else {
      return temporal
          .with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    }

  }
}
