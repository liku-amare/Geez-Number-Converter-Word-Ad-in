package com.likuamare.geeznumerals;

public final class ConverterSpecTest {
    private static int assertions;

    public static void main(String[] args) {
        expect("1", "፩");
        expect("9", "፱");
        expect("10", "፲");
        expect("11", "፲፩");
        expect("20", "፳");
        expect("99", "፺፱");
        expect("100", "፻");
        expect("101", "፻፩");
        expect("110", "፻፲");
        expect("111", "፻፲፩");
        expect("200", "፪፻");
        expect("999", "፱፻፺፱");
        expect("1000", "፲፻");
        expect("2345", "፳፫፻፵፭");
        expect("10000", "፼");
        expect("10001", "፼፩");
        expect("100000", "፲፼");
        expect("1000000", "፻፼");
        expect("100000000", "፼፼");
        expect("78010092", "፸፰፻፩፼፺፪");
        expect("780100000092", "፸፰፻፩፼፼፺፪");
        expect("00002345", "፳፫፻፵፭");
        accepts("9".repeat(120));

        rejects(null);
        rejects("");
        rejects("   ");
        rejects("0");
        rejects("000");
        rejects("-1");
        rejects("1.5");
        rejects("١");
        rejects("፩");

        System.out.println("Converter specification checks passed: " + assertions);
    }

    private static void expect(String decimal, String expected) {
        String actual = EthiopicNumeralConverter.fromDecimal(decimal);
        if (!expected.equals(actual)) {
            throw new AssertionError(decimal + " expected " + expected + " but got " + actual);
        }
        assertions++;
    }

    private static void rejects(String input) {
        try {
            EthiopicNumeralConverter.fromDecimal(input);
            throw new AssertionError("Expected input to be rejected: " + input);
        } catch (IllegalArgumentException expected) {
            assertions++;
        }
    }

    private static void accepts(String input) {
        String result = EthiopicNumeralConverter.fromDecimal(input);
        if (result.isEmpty()) {
            throw new AssertionError("Expected a non-empty result for: " + input);
        }
        for (int index = 0; index < result.length(); index++) {
            char character = result.charAt(index);
            if (character < '\u1369' || character > '\u137C') {
                throw new AssertionError("Unexpected character in result: U+"
                        + Integer.toHexString(character));
            }
        }
        assertions++;
    }
}
