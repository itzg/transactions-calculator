
[![CircleCI](https://circleci.com/gh/itzg/transactions-calculator.svg?style=svg)](https://circleci.com/gh/itzg/transactions-calculator)
[![](https://jitpack.io/v/itzg/transactions-calculator.svg)](https://jitpack.io/#itzg/transactions-calculator)

This is a little Java library that computes occurrences of weekly and monthly transactions over a given time range.

I wrote this library to convert my [kidsbank](https://github.com/itzg/kidsbank-js/) application from using Quartz and needing to be long running over to being a stateless, [cloud run](https://cloud.google.com/run/)'able app.