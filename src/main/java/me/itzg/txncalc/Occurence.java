package me.itzg.txncalc;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Occurence {
  Instant when;
  ScheduledTransaction transaction;
}
