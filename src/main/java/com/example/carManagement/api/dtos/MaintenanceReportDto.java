package com.example.carManagement.api.dtos;


import java.time.Month;

public class MaintenanceReportDto {
    private YearMonthDto yearMonth;
    private int requests;

    public YearMonthDto getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonthDto yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public static class YearMonthDto {
        private int year;
        private Month month;
        private boolean leapYear;
        private int monthValue;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public Month getMonth() {
            return month;
        }

        public void setMonth(Month month) {
            this.month = month;
        }

        public boolean isLeapYear() {
            return leapYear;
        }

        public void setLeapYear(boolean leapYear) {
            this.leapYear = leapYear;
        }

        public int getMonthValue() {
            return monthValue;
        }

        public void setMonthValue(int monthValue) {
            this.monthValue = monthValue;
        }
    }
}

