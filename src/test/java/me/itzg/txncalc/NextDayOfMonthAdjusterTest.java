package me.itzg.txncalc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Test;

public class NextDayOfMonthAdjusterTest {

  @Test
  public void testNoWrap() {
    final LocalDateTime input = buildTemporal("2019-10-01T10:15:30.00Z");

    final NextDayOfMonthAdjuster adjuster = new NextDayOfMonthAdjuster(5);

    final LocalDateTime result = input.with(adjuster);
    assertThat(result.atOffset(ZoneOffset.UTC)).isEqualTo("2019-10-05T10:15:30.00Z");
  }

  private LocalDateTime buildTemporal(String text) {
    return Instant.parse(text).atOffset(ZoneOffset.UTC).toLocalDateTime();
  }

  @Test
  public void testWrap() {
    final LocalDateTime input = buildTemporal("2019-10-10T10:15:30.00Z");

    final NextDayOfMonthAdjuster adjuster = new NextDayOfMonthAdjuster(5);

    final LocalDateTime result = input.with(adjuster);
    assertThat(result.atOffset(ZoneOffset.UTC)).isEqualTo("2019-11-05T10:15:30.00Z");
  }

  @Test
  public void testSameDay() {
    final LocalDateTime input = buildTemporal("2019-10-10T10:15:30.00Z");

    final NextDayOfMonthAdjuster adjuster = new NextDayOfMonthAdjuster(10);

    final LocalDateTime result = input.with(adjuster);
    assertThat(result.atOffset(ZoneOffset.UTC)).isEqualTo("2019-11-10T10:15:30.00Z");
  }

  @Test
  public void testWrapTo30DayMonth() {
    // start from Aug 31 since Sep only has 30 days
    final LocalDateTime input = buildTemporal("2019-08-31T10:15:30.00Z");

    final NextDayOfMonthAdjuster adjuster = new NextDayOfMonthAdjuster(10);

    final LocalDateTime result = input.with(adjuster);
    assertThat(result.atOffset(ZoneOffset.UTC)).isEqualTo("2019-09-10T10:15:30.00Z");
  }

  @Test
  public void testInvalidDayOfMonth() {
    assertThatThrownBy(() -> {
      new NextDayOfMonthAdjuster(0);
    }).isInstanceOf(IllegalArgumentException.class);

  }
}