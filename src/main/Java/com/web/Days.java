package com.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by R500 on 19.8.2014 г..
 */
public abstract class Days {

    protected final static Calendar calendar = Calendar.getInstance();

    protected static class Day {

        private int year;
        private int month;
        private int day;

        protected Day(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        protected Day(Date date) {
            calendar.setTime(date);
            this.month = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Day && obj.hashCode() == hashCode();
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{month, day});
        }

    }

    public abstract boolean contains(Date date);

}
