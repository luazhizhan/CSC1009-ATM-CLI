package src.main.java;
import java.util.HashMap;


// Key - Country
// Value - Currency
// Currency - class containing curency code, banknotes[], countries[]

// Can I pull it from a CSV? Try later
public class CurrencyDatabase {

    public static HashMap<String, Currency> banknotes = new HashMap<String, Currency>()
    {{
        put("SINGAPORE", 
            new Currency(
                "SGD", 
                new int[] { 10000, 1000, 100, 50, 10, 5, 2 }, 
                new String[] { "SINGAPORE" }
            )
        );
        // Figure out how to map this better
        put("EU", 
            new Currency(
                "EURO", 
                new int[] { 200, 100, 50, 20, 10, 5 }, 
                new String[] { "AUSTRIA", "BELGIUM", "CYPRUS", "ESTONIA", "FINLAND", "FRANCE", "GERMANY", "GREECE", "IRELAND", "ITALY", "LATVIA", "LITHUANIA", "LUXEMBOURG", "MALTA", "NETHERLANDS", "PORTUGAL", "SLOVAKIA", "SLOVENIA", "SPAIN" }
            )
        );
    }}
    ;

}

