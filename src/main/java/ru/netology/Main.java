package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static volatile String[] texts = new String[100_000];
    private static AtomicInteger counter3 = new AtomicInteger(0);
    private static AtomicInteger counter4 = new AtomicInteger(0);
    private static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        generateNickName();

        Thread t1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    operationOfCounters(text);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    operationOfCounters(text);
                }
            }
        });

        Thread t3 = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingLetters(text)) {
                    operationOfCounters(text);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Красивых слов с длиной 3: " + counter3.get() + " шт ");
        System.out.println("Красивых слов с длиной 4: " + counter4.get() + " шт ");
        System.out.println("Красивых слов с длиной 5: " + counter5.get() + " шт ");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void generateNickName() {
        Random random = new Random();

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
    }

    private static void operationOfCounters(String text) {
        int length = text.length();
        if (length == 3) {
            counter3.incrementAndGet();
        } else if (length == 4) {
            counter4.incrementAndGet();
        } else if (length == 5) {
            counter5.incrementAndGet();
        }
    }

    public static boolean isPalindrome(String text) { // проверка на палиндром
        int n = text.length();
        for (int i = 0; i < (n / 2); ++i) {
            if (text.charAt(i) != text.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) { // проверка слова на одинаковые буквы
        char firstLetter = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstLetter) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingLetters(String text) { // проверка на возрастание букв
//        if (text == null || text.length() == 0){
//            return false;
//        }
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) <= text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}