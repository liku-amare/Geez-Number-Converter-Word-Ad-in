package com.likuamare.geeznumerals;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts positive decimal integers to the traditional Ethiopic numeral form.
 *
 * <p>The implementation follows the W3C ethiopic-numeric counter algorithm and
 * uses the Unicode Ethiopic digits U+1369 through U+137C. Ethiopic numerals do
 * not have a representation for zero.</p>
 */
public final class EthiopicNumeralConverter {
    private static final String[] UNITS = {
            "", "፩", "፪", "፫", "፬", "፭", "፮", "፯", "፰", "፱"
    };

    private static final String[] TENS = {
            "", "፲", "፳", "፴", "፵", "፶", "፷", "፸", "፹", "፺"
    };

    private static final char HUNDRED = '\u137B';
    private static final char TEN_THOUSAND = '\u137C';

    private EthiopicNumeralConverter() {
    }

    public static String fromDecimal(String input) {
        String decimal = normalize(input);
        if ("1".equals(decimal)) {
            return UNITS[1];
        }

        List<Integer> groups = splitIntoPairs(decimal);
        StringBuilder result = new StringBuilder(decimal.length() + groups.size());

        for (int position = groups.size() - 1; position >= 0; position--) {
            int value = groups.get(position);
            boolean mostSignificant = position == groups.size() - 1;
            boolean omitCoefficient = value == 0
                    || (mostSignificant && value == 1)
                    || (position % 2 == 1 && value == 1);

            if (!omitCoefficient) {
                result.append(TENS[value / 10]);
                result.append(UNITS[value % 10]);
            }

            if (position % 2 == 1 && value != 0) {
                result.append(HUNDRED);
            } else if (position > 0 && position % 2 == 0) {
                result.append(TEN_THOUSAND);
            }
        }

        return result.toString();
    }

    private static String normalize(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Enter a positive whole number.");
        }

        String value = input.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Enter a positive whole number.");
        }

        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            if (character < '0' || character > '9') {
                throw new IllegalArgumentException("Use decimal digits from 0 to 9 only.");
            }
        }

        int firstNonZero = 0;
        while (firstNonZero < value.length() && value.charAt(firstNonZero) == '0') {
            firstNonZero++;
        }

        if (firstNonZero == value.length()) {
            throw new IllegalArgumentException("Traditional Ethiopic numerals do not have zero.");
        }

        return value.substring(firstNonZero);
    }

    private static List<Integer> splitIntoPairs(String decimal) {
        List<Integer> groups = new ArrayList<>((decimal.length() + 1) / 2);

        for (int end = decimal.length(); end > 0; end -= 2) {
            int start = Math.max(0, end - 2);
            groups.add(Integer.parseInt(decimal.substring(start, end)));
        }

        return groups;
    }
}
