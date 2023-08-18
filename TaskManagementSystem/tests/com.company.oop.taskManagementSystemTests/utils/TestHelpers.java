package com.company.oop.taskManagementSystemTests.utils;

import java.util.Arrays;
import java.util.List;

public class TestHelpers {

        /**
         * Returns a new List with size equal to wantedSize.
         * Useful when you do not care what the contents of the List are,
         * for example when testing if a list of a command throws exception
         * when it's parameters list's size is less/more than expected.
         *
         * @param wantedSize the size of the List to be returned.
         * @return a new List with size equal to wantedSize
         */
        public static List<String> getList(int wantedSize) {
            return Arrays.asList(new String[wantedSize]);
        }

    /**
     * Returns a new String with size equal to length.
     * Useful when you do not care what the contents of a String are,
     * for example when testing if a String of given size
     * would cause an exception being thrown.
     *
     * @param length the size of the String to be returned.
     * @return a new String with size equal to length
     */

    public static String getString(int length) {
        return "x".repeat(length);
    }


    }

