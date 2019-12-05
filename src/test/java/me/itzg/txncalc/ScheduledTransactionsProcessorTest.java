package me.itzg.txncalc;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;

public class ScheduledTransactionsProcessorTest {

  @Test
  public void testComputeMonthly() {
    final Instant after = Instant.parse("2019-01-01T10:15:30.00Z");
    final Instant until = Instant.parse("2019-10-02T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-01-05T00:00:00Z",
            "2019-02-05T00:00:00Z",
            "2019-03-05T00:00:00Z",
            "2019-04-05T00:00:00Z",
            "2019-05-05T00:00:00Z",
            "2019-06-05T00:00:00Z",
            "2019-07-05T00:00:00Z",
            "2019-08-05T00:00:00Z",
            "2019-09-05T00:00:00Z"
        );
  }

  @Test
  public void testComputeMonthly_AfterOnDay() {
    final Instant after = Instant.parse("2019-01-05T10:15:30.00Z");
    final Instant until = Instant.parse("2019-01-06T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).isEmpty();
  }

  @Test
  public void testComputeMonthly_AfterOnDayUntilOnNext() {
    final Instant after = Instant.parse("2019-01-05T23:15:30.00Z");
    final Instant until = Instant.parse("2019-02-05T01:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-02-05T00:00:00Z"
        );
  }

  @Test
  public void testComputeMonthly_UntilOnDay() {
    final Instant after = Instant.parse("2019-01-04T10:15:30.00Z");
    final Instant until = Instant.parse("2019-01-05T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-01-05T00:00:00Z"
        );
  }

  @Test
  public void testComputeMonthly_UntilAfterSameDay() {
    final Instant after = Instant.parse("2019-01-05T09:15:30.00Z");
    final Instant until = Instant.parse("2019-01-05T23:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).isEmpty();
  }
  @Test
  public void testComputeWeekly() {
    final Instant after = Instant.parse("2019-11-25T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-26T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeWeekly(() -> DayOfWeek.WEDNESDAY, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-11-27T00:00:00Z",
            "2019-12-04T00:00:00Z",
            "2019-12-11T00:00:00Z",
            "2019-12-18T00:00:00Z",
            "2019-12-25T00:00:00Z"
        );
  }

  @Test
  public void testComputeWeekly_AfterOnDay() {
    final Instant after = Instant.parse("2019-12-04T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-05T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeWeekly(() -> DayOfWeek.WEDNESDAY, after, until);

    assertThat(results).isEmpty();
  }

  @Test
  public void testComputeWeekly_AfterOnDayUntilOnNext() {
    final Instant after = Instant.parse("2019-12-04T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-11T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeWeekly(() -> DayOfWeek.WEDNESDAY, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-12-11T00:00:00Z"
        );
  }

  @Test
  public void testComputeWeekly_UntilOnDay() {
    final Instant after = Instant.parse("2019-12-03T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-04T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeWeekly(() -> DayOfWeek.WEDNESDAY, after, until);

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-12-04T00:00:00Z"
        );
  }

  @Test
  public void testComputeWeekly_UntilAfterSameDay() {
    final Instant after = Instant.parse("2019-12-11T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-11T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor()
        .computeWeekly(() -> DayOfWeek.WEDNESDAY, after, until);

    assertThat(results).isEmpty();
  }

  @Test
  public void testCompute() {
    final Instant after = Instant.parse("2019-11-25T10:15:30.00Z");
    final Instant until = Instant.parse("2019-12-26T10:15:30.00Z");

    final Collection<Occurrence> results = new ScheduledTransactionsProcessor().compute(
        Arrays.asList(
            (WeeklyScheduledTransaction) () -> DayOfWeek.WEDNESDAY,
            (MonthlyScheduledTransaction) () -> 5
        ), after, until
    );

    assertThat(results).extracting(Occurrence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            // weekly ones
            "2019-11-27T00:00:00Z",
            "2019-12-04T00:00:00Z",
            "2019-12-11T00:00:00Z",
            "2019-12-18T00:00:00Z",
            "2019-12-25T00:00:00Z",
            // monthly one
            "2019-12-05T00:00:00Z"
        );
  }
}