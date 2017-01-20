package com.tk.service.util;

import de.svenjacobs.loremipsum.LoremIpsum;

import java.util.Random;

/**
 * DummyDataGenerator
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class DummyDataGenerator {

    public static String getServiceName() {
        return "PROVIDE-DATA-SERVICE";
    }

    public static String getServiceData() {
        Random random = new Random();
        LoremIpsum loremIpsum = new LoremIpsum();
        return loremIpsum.getWords(random.nextInt(51) + 100, random.nextInt(21));
    }
}
