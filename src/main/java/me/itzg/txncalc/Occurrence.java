package me.itzg.txncalc;

import java.time.Instant;

public class Occurrence {
  private Instant when;
  private ScheduledTransaction transaction;

  public Occurrence(Instant when, ScheduledTransaction transaction) {
    this.when = when;
    this.transaction = transaction;
  }

  public Instant getWhen() {
    return when;
  }

  public void setWhen(Instant when) {
    this.when = when;
  }

  public ScheduledTransaction getTransaction() {
    return transaction;
  }

  public void setTransaction(ScheduledTransaction transaction) {
    this.transaction = transaction;
  }
}
