package me.itzg.txncalc;

import java.time.DayOfWeek;

public interface WeeklyScheduledTransaction extends ScheduledTransaction {
  DayOfWeek getDayOfWeek();
}
