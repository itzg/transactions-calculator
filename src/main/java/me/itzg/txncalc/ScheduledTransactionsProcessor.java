package me.itzg.txncalc;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO: need to think about boundary cases and repeated runs on the same day
 */
public class ScheduledTransactionsProcessor {

  public Collection<Occurence> compute(Collection<ScheduledTransaction> given,
                                       Instant after, Instant until) {

    final List<Occurence> results = new ArrayList<>();
    for (ScheduledTransaction txn : given) {
      if (txn instanceof WeeklyScheduledTransaction) {
        results.addAll(computeWeekly(((WeeklyScheduledTransaction) txn), after, until));
      } else if (txn instanceof MonthlyScheduledTransaction) {
        results.addAll(computeMonthly(((MonthlyScheduledTransaction) txn), after, until));
      }
    }

    return results;
  }

  public Collection<Occurence> computeMonthly(MonthlyScheduledTransaction txn,
                                              Instant after, Instant until) {
    final TemporalAdjuster adjuster = new NextDayOfMonthAdjuster(txn.getDayOfMonth());

    return computeWithAdjuster(adjuster, txn, after, until);
  }

  public Collection<Occurence> computeWeekly(WeeklyScheduledTransaction txn,
                                             Instant after, Instant until) {
    final TemporalAdjuster adjuster = TemporalAdjusters.next(txn.getDayOfWeek());

    return computeWithAdjuster(adjuster, txn, after, until);
  }

  private Collection<Occurence> computeWithAdjuster(TemporalAdjuster adjuster,
                                                    ScheduledTransaction txn,
                                                    Instant after, Instant until) {

    final OffsetDateTime untilOffset = until.atOffset(ZoneOffset.UTC);

    final List<Occurence> results = new ArrayList<>();
    for (OffsetDateTime at = after.atOffset(ZoneOffset.UTC).with(adjuster);
        at.isBefore(untilOffset); at = at.with(adjuster)) {
      results.add(new Occurence(Instant.from(at), txn));
    }

    return results;
  }

}
