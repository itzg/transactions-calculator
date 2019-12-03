package me.itzg.txncalc;

import java.time.OffsetDateTime;
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
    final OffsetDateTime normalized = OffsetDateTime.from(temporal);

    if (dayOfMonth <= normalized.getDayOfMonth()) {
      return normalized
          .with(ChronoField.DAY_OF_MONTH, 1)
          .plus(1, ChronoUnit.MONTHS)
          .with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    } else {
      return normalized
          .with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    }

  }
}
