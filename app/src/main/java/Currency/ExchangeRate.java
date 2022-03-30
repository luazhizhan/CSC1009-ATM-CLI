package Currency;

import java.math.BigDecimal;

// Structure for holding exchange rates from one currency to another.
public class ExchangeRate {
    // Exchange rate TO this currency.
    private final Currency currency;
    // Conversion rate to currency,
    // i.e. if this rate is for SGD, and currency is JPY.
    // If x is an amount of SGD, x * rate = y JPY yen.
    private final BigDecimal rate;

    public ExchangeRate(Currency currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    // Override hashCode and equals to allow for ExchangeRate ==
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExchangeRate other = (ExchangeRate) obj;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        return true;
    }
}
