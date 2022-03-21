package src.main.Enrique.Algos;

import java.util.Arrays;

import src.main.Enrique.Helpers.Tuple;

// I cannot emphasise how crap this algo is.
// Not sure if it can account for any number given variable banknote arrays.
// Current reasons why this is broken as hell and sucks:
// Current algo doesn't account for current number of notes in system.
// Current algo gets a close number, not exact. Therefore- broken
public class CountMoneyAlgo {

    // private static final int DEFAULT_VALUE = 10000;
    // private static int[] table;

    // public static void setUpTable(int[] notes) {
    // table = new int[DEFAULT_VALUE + 1];
    // table[0] = 0;
    // for (int i = 1; i <= DEFAULT_VALUE; i++) {
    // table[i] = DEFAULT_VALUE + 1;
    // }
    // }

    // Bottom up approach using dynamic programming and caching results in a table
    // E.g. notes is [1,2,5], value = 11.
    public static int MakeChange(int[] notes, int value) {
        // array size is 3, 12
        // int[][] dp = new int[value + 1][notes.length];

        SubSolution[] minSolution = new SubSolution[value + 1];

        int[] minForValue = new int[value + 1];
        for (int i = 0; i < value + 1; i++) {
            if (i == 0) {
                minForValue[i] = 0;
                // minSolution[i] = new SubSolution(false, new int[notes.length], 0);
                continue;
            }
            minForValue[i] = value + 1;
        }

        for (int i = 0; i < value + 1; i++) {
            if (i == 0)
                minSolution[i] = new SubSolution(false, new int[notes.length], 0, 0);
            else
                minSolution[i] = new SubSolution(false, new int[notes.length], value + 1, i);
            for (int j = 0; j < notes.length; j++) {
                minSolution[i].bills[j] = 0;
            }
        }

        // For every value up to the amount we want to cache,
        for (int i = 1; i < value + 1; i++) {
            // Cache for increment later.
            // Flag. If true, then we know that the value matches one of the bill values.
            boolean flag = false;
            for (int j = 0; j < notes.length; j++) {
                if (notes[j] == i)
                    flag = true;
            }

            for (int j = 0; j < notes.length; j++) {
                // If the value of the note <= the value,
                if (notes[j] <= i) {
                    // We take the lesser of [(previous value index - value of note) + 1] and
                    // [current value in index- where the default value is value + 1].
                    // This will be the minimum number of bills to solve for the value.
                    minForValue[i] = Math.min(minForValue[i - notes[j]] + 1, minForValue[i]);

                    // Wrong if e.g. 3, and notes are [2,5,10]
                    minSolution[i].billCount = Math.min(minSolution[i - notes[j]].billCount + 1,
                            minSolution[i].billCount);

                    // Update bills array.
                    // If bill matches value, use that bill.
                    if (notes[j] == i) {
                        for (int k = 0; k < notes.length; k++) {
                            if (j == k) {
                                minSolution[i].bills[k] = 1;
                                continue;
                            }
                            minSolution[i].bills[k] = 0;
                        }
                    }

                    // If it isn't impossible, flag it
                    if (minSolution[i].billCount != value + 1)
                        minSolution[i].possible = true;
                }
            }

            // If value doesn't match any bill, compute result from previous iterations.
            if (flag == false) {
                for (int j = notes.length - 1; j >= 0; j--) {
                    // If the value of the note > the value, skip it.
                    if (notes[j] > i)
                        continue;

                    // If the [index - largest bill].possible is false, skip it.
                    if (minSolution[i - notes[j]].possible == false)
                        continue;

                    for (int k = 0; k < notes.length; k++) {
                        minSolution[i].bills[k] = minSolution[i - notes[j]].bills[k]
                                + minSolution[notes[j]].bills[k];
                    }
                    break;
                }

            }

        }

        System.out.println("minSolution w array");
        for (int i = 0; i < value + 1; i++) {
            System.out.print("value: " + i + " possible: " + minSolution[i].possible
                    + " billCount: " + minSolution[i].billCount + ": [");
            for (int j = 0; j < notes.length; j++) {
                System.out.print(minSolution[i].bills[j] + ", ");
            }
            System.out.print("]\n");
        }

        System.out.println("minForValue");
        for (int i = 0; i < value + 1; i++) {
            System.out.print(minForValue[i] + ", ");
        }
        return minSolution[value].billCount;
        // return dp[value][notes.length - 1];
    }

    // function to count and print currency notes
    // notes = values to use for counting
    // value = value to match.
    public static Tuple<Boolean, int[]> countCurrency(int[][] notes, int value) {
        // int[] notes = new int[]{ 2000, 500, 200, 100, 50, 20, 10, 5, 1 };
        int[] noteCounter = new int[notes[0].length];

        for (int i = 0; i < notes[0].length; i++) {
            // Skip values that are too large
            System.out.println(notes[0][i]);
            if (notes[0][i] <= value) {
                noteCounter[i] = value / notes[0][i];
                // noteCounter[i] = (value - value % notes[0][i]) / notes[0][i];
                System.out.println("post divide" + noteCounter[i]);
                if (noteCounter[i] > notes[1][i])
                    noteCounter[i] = notes[1][i];
                value -= noteCounter[i] * notes[0][i];
            }
            System.out.println(notes[0][i] + " " + noteCounter[i] + " remaining value: " + value);
        }

        if (value != 0) {
            for (int i = 0; i < notes[0].length; i++) {
                if (noteCounter[i] == notes[1][i]) {
                    System.out.println("Out of notes!");
                }
            }
            System.out.println("Algo is bad.");
        }

        System.out.println("noteCounter");
        for (int i = 0; i < noteCounter.length; i++) {
            System.out.println(noteCounter[i]);
        }
        System.out.println("end noteCounter");

        return new Tuple<Boolean, int[]>(true, noteCounter);
    }

    public static int dynamic(int[] notes, int value) {
        int[][] solution = new int[notes.length + 1][value + 1];

        // if amount=0 then just return empty set to make the change
        for (int i = 0; i <= notes.length; i++) {
            solution[i][0] = 1;
        }

        // if no notes given, 0 ways to change the amount
        for (int i = 1; i <= value; i++) {
            solution[0][i] = 0;
        }

        // now fill rest of the matrix.
        for (int i = 1; i <= notes.length; i++) {
            for (int j = 1; j <= value; j++) {
                // check if the coin value is less than the amount needed
                if (notes[i - 1] <= j) {
                    // reduce the amount by coin value and
                    // use the subproblem solution (amount-v[i]) and
                    // add the solution from the top to it
                    solution[i][j] = solution[i - 1][j]
                            + solution[i][j - notes[i - 1]];
                } else {
                    // just copy the value from the top
                    solution[i][j] = solution[i - 1][j];
                }
            }
        }
        return solution[notes.length][value];
    }

    public static void main(String[] args) throws java.lang.Exception {
        // arranged in increasing order
        int[] coins = { 2, 5, 10 };
        int[] count = { 3, 3, 3 };
        int sum = 7;
        int n;
        // n = countWays(coins, count, sum);
        // System.out.println(n);

        coins[2] = 10;
        sum = 7;
        n = MakeChange(coins, sum);
        System.out.println(n);
    }

    /**
     * Coin change problem with finite number of coins available denominations
     * of coins = {1,2,3} count of coins = ={ 2, 2, 2 } find the number of ways for
     * getting change for S=6
     */
    public static int countWays(int[] billValue, int[] billLimit, int value) {
        int n = billValue.length;
        int[][] dp = new int[n + 1][value + 1];

        int ret = 0;
        // No way to return a value of 0.
        dp[0][0] = 1;
        // For each denomination,
        for (int coins = 1; coins <= n; coins++) {
            // Iterate upwards until we reach value.
            for (int i = 0; i <= value; i++) {
                // For dp[0][0], we want to get the value of
                dp[coins][i] += dp[coins - 1][i];
                // dp[coins][i] = dp[coins - 1][i];
            }

            // For each billValue, limit the computation to
            // the number of bills left in the machine using k
            for (int k = 1; k <= billLimit[coins - 1]; k++) {
                int initial = billValue[coins - 1] * k;
                for (int i = initial; i <= value; i++) {
                    dp[coins][i] += dp[coins - 1][i - initial];
                    // if (initial == i)
                    // dp[coins][i] = 1;
                    // else if (initial > i)
                    // dp[coins][i] = dp[coins - 1][i];
                    // else
                    // dp[coins][i] = Math.min(dp[coins - 1][i], 1 + dp[coins][i - initial]);
                }
            }
        }

        // // Print
        // For each denomination,
        for (int coin = 0; coin <= n; coin++) {
            // Iterate upwards until we reach value.
            System.out.print("For " + coin + ": ");
            for (int i = 0; i <= value; i++) {
                System.out.print(dp[coin][i] + " ");
            }
            System.out.println("");
        }

        // for (int bValue = 0; bValue <= n; bValue++) {
        // System.out.print("For " + bValue + ": ");
        // for (int amount = 0; amount <= value; amount++) {
        // System.out.print(dp[bValue][amount] + " ");
        // }
        // System.out.println("");
        // }

        // for (int i = 0; i <= n; i++) {
        // ret += dp[i][value];
        // }
        // return ret;
        return dp[n][value];
    }

    private static class SubSolution {
        public boolean possible; // To know if the solution is valid
        // Array of bills required to match this amount
        public int[] bills;
        // Total number of bills used
        public int billCount;
        // Total value of bills used
        public int value;

        public SubSolution(boolean possible, int[] bills, int billCount, int value) {
            this.possible = possible;
            this.bills = bills;
            this.billCount = billCount;
            this.value = value;
        }
    }

    // private static class SubSolution {
    // public boolean possible; // To know if the solution is valid
    // public int coinCount; //
    // public int amount; // Reference to amount of the best solution we came here
    // from
    // public int coin; // Reference to greatest coin of the best solution we came
    // here from

    // public SubSolution(boolean possible, int coinCount, int amount, int coin) {
    // this.possible = possible;
    // this.coinCount = coinCount;
    // this.amount = amount;
    // this.coin = coin;
    // }
    // }

    // public static void change(int[] coins, int amount) {
    // Arrays.sort(coins);
    // SubSolution optimalSubSolutions[][] = new SubSolution[amount +
    // 1][coins.length];
    // for (int i = 0; i < coins.length; i++) {
    // optimalSubSolutions[0][i] = new SubSolution(true, 0, 0, 0);
    // }
    // for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
    // for (int currentMaxCoin = 0; currentMaxCoin < coins.length; currentMaxCoin++)
    // {
    // optimalSubSolutions[currentAmount][currentMaxCoin] = new SubSolution(false,
    // 0, 0, 0);
    // if (currentAmount >= coins[currentMaxCoin]) {
    // optimalSubSolutions[currentAmount][currentMaxCoin].possible =
    // optimalSubSolutions[currentAmount
    // - coins[currentMaxCoin]][currentMaxCoin].possible;
    // optimalSubSolutions[currentAmount][currentMaxCoin].coinCount =
    // optimalSubSolutions[currentAmount
    // - coins[currentMaxCoin]][currentMaxCoin].coinCount + 1;
    // optimalSubSolutions[currentAmount][currentMaxCoin].amount = currentAmount -
    // coins[currentMaxCoin];
    // optimalSubSolutions[currentAmount][currentMaxCoin].coin = currentMaxCoin;
    // }
    // if (currentMaxCoin > 0 &&
    // (!optimalSubSolutions[currentAmount][currentMaxCoin].possible ||
    // optimalSubSolutions[currentAmount][currentMaxCoin
    // - 1].coinCount <
    // optimalSubSolutions[currentAmount][currentMaxCoin].coinCount)) {
    // optimalSubSolutions[currentAmount][currentMaxCoin].possible =
    // optimalSubSolutions[currentAmount][currentMaxCoin
    // - 1].possible;
    // optimalSubSolutions[currentAmount][currentMaxCoin].coinCount =
    // optimalSubSolutions[currentAmount][currentMaxCoin
    // - 1].coinCount;
    // optimalSubSolutions[currentAmount][currentMaxCoin].amount = currentAmount;
    // optimalSubSolutions[currentAmount][currentMaxCoin].coin = currentMaxCoin - 1;
    // }
    // }
    // }
    // if (!optimalSubSolutions[amount][coins.length - 1].possible) {
    // System.out.println("No solution with specified coins.");
    // } else {
    // int coinsCount[] = new int[coins.length];
    // for (int i = 0; i < coinsCount.length; i++) {
    // coinsCount[i] = 0;
    // }
    // int currentCoin = coins.length - 1;
    // for (int remainingAmount = amount; remainingAmount > 0;) {
    // if (optimalSubSolutions[remainingAmount][currentCoin].amount !=
    // remainingAmount) {
    // coinsCount[currentCoin]++;
    // }
    // remainingAmount = optimalSubSolutions[remainingAmount][currentCoin].amount;
    // currentCoin = optimalSubSolutions[remainingAmount][currentCoin].coin;
    // }

    // for (int i = 0; i < coinsCount.length; i++) {
    // System.out.println(coins[i] + " " + coinsCount[i]);
    // }
    // }
    // }
}