package me.itzg.txncalc;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Collection;
import org.junit.Test;

public class ScheduledTransactionsProcessorTest {

  @Test
  public void testComputeMonthly() {
    final Instant after = Instant.parse("2019-01-01T10:15:30.00Z");
    final Instant until = Instant.parse("2019-10-02T10:15:30.00Z");

    final Collection<Occurence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).extracting(Occurence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-01-05T10:15:30Z",
            "2019-02-05T10:15:30Z",
            "2019-03-05T10:15:30Z",
            "2019-04-05T10:15:30Z",
            "2019-05-05T10:15:30Z",
            "2019-06-05T10:15:30Z",
            "2019-07-05T10:15:30Z",
            "2019-08-05T10:15:30Z",
            "2019-09-05T10:15:30Z"
        );
  }

  @Test
  public void testComputeMonthly_AfterOnDay() {
    final Instant after = Instant.parse("2019-01-05T10:15:30.00Z");
    final Instant until = Instant.parse("2019-01-06T10:15:30.00Z");

    final Collection<Occurence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).extracting(Occurence::getWhen)
        .extracting(Instant::toString)
        .containsExactly(
            "2019-01-05T10:15:30Z"
        );
  }

  @Test
  public void testComputeMonthly_UntilOnDay() {
    final Instant after = Instant.parse("2019-01-04T10:15:30.00Z");
    final Instant until = Instant.parse("2019-01-05T10:15:30.00Z");

    final Collection<Occurence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).isEmpty();
  }

  @Test
  public void testComputeMonthly_UntilAfterSameDay() {
    final Instant after = Instant.parse("2019-01-05T09:15:30.00Z");
    final Instant until = Instant.parse("2019-01-05T23:15:30.00Z");

    final Collection<Occurence> results = new ScheduledTransactionsProcessor()
        .computeMonthly(() -> 5, after, until);

    assertThat(results).isEmpty();
  }

  @Test
  public void testComputeWeekly() {
  }
}