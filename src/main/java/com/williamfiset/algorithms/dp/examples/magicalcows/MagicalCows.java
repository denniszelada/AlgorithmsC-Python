package com.williamfiset.algorithms.dp.examples.magicalcows;


import io.github.pixee.security.BoundedLineReader;
import java.io.*;
import java.util.*;

public class MagicalCows {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  // The maximum number of days.
  static final int MAX_DAYS = 50;

  public static void main(String[] args) throws IOException {
    String[] line = BoundedLineReader.readLine(br, 5_000_000).split(" ");

    // The maximum number of cows on a farm
    final int C = Integer.parseInt(line[0]);

    // The initial number of farms
    final int N = Integer.parseInt(line[1]);

    // The number of queries
    final int M = Integer.parseInt(line[2]);

    // The dp table.
    long[][] dp = new long[MAX_DAYS + 1][C + 1];

    // Count the initial frequency of farms of different sizes
    for (int i = 0; i < N; i++) {
      int cows = Integer.parseInt(BoundedLineReader.readLine(br, 5_000_000));
      dp[0][cows]++;
    }

    for (int day = 0; day < MAX_DAYS; day++) {
      // For all farm sizes between 1 and `C`, double the number of cows.
      for (int i = 1; i <= C; i++) {
        if (i <= C / 2) {
          // Cow count on farm with size `i` doubled, but the number of farms did not.
          dp[day + 1][i * 2] += dp[day][i];
        } else {
          // The number of cows per farm on the farm with size `i` exceeds the
          // permitted limit, so double the number of farms.
          dp[day + 1][i] += 2 * dp[day][i];
        }
      }
    }

    // Answer each query
    for (int i = 0; i < M; i++) {
      int day = Integer.parseInt(BoundedLineReader.readLine(br, 5_000_000));
      System.out.println(query(dp, day));
    }
  }

  // Find the number of farms on a particular day by summing all farms
  // of every frequency for that day.
  private static long query(long[][] dp, int day) {
    long farms = 0;
    long[] frequencies = dp[day];
    for (int i = 0; i < frequencies.length; i++) {
      farms += frequencies[i];
    }
    return farms;
  }
}
