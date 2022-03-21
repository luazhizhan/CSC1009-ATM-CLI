def _get_change_making_matrix(set_of_coins, value: int):
    m = [[0 for _ in range(value + 1)] for _ in range(len(set_of_coins) + 1)]
    for i in range(1, value + 1):
        m[0][i] = float('inf')  # By default there is no way of making change
    return m

# Change-making problem, get minimum number of coins, assuming infinite amount.


def change_making(coins, value: int):
    """This function assumes that all coins are available infinitely.
    n is the number to obtain with the fewest coins.
    coins is a list or tuple with the available denominations.
    """
    dp = _get_change_making_matrix(coins, value)
    for c, coin in enumerate(coins, 1):
        print(c)
        print(coin)
        for i in range(1, value + 1):
            # Just use the coin
            if coin == i:
                dp[c][i] = 1
            # coin cannot be included.
            # Use the previous solution for making r,
            # excluding coin
            elif coin > i:
                dp[c][i] = dp[c - 1][i]
            # coin can be used.
            # Decide which one of the following solutions is the best:
            # 1. Using the previous solution for making r (without using coin).
            # 2. Using the previous solution for making r - coin (without
            #      using coin) plus this 1 extra coin.
            else:
                dp[c][i] = min(dp[c - 1][i], 1 + dp[c][i - coin])

    return dp[-1][-1]


coinList = [1, 2, 3]
value = 6

print(change_making(coinList, value))
