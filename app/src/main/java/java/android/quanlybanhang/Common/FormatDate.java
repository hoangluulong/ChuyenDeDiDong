package java.android.quanlybanhang.Common;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FormatDate {

    public FormatDate(){}

    public int startWeek(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        int firstDayOfWeek = 0;

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 1){
            firstDayOfWeek = 6;
        }else if(dayOfWeek == 2){
            firstDayOfWeek = 0;
        }else if(dayOfWeek == 3){
            firstDayOfWeek = 1;
        }else if(dayOfWeek == 4){
            firstDayOfWeek = 2;
        }else if(dayOfWeek == 5){
            firstDayOfWeek = 3;
        }else if(dayOfWeek == 6){
            firstDayOfWeek = 4;
        }else if(dayOfWeek == 7){
            firstDayOfWeek = 5;
        }

        return firstDayOfWeek;
    }
    public int startWeekBefor(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        int firstDayOfWeek = 0;

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 1){
            firstDayOfWeek = 13;
        }else if(dayOfWeek == 2){
            firstDayOfWeek = 7;
        }else if(dayOfWeek == 3){
            firstDayOfWeek = 8;
        }else if(dayOfWeek == 4){
            firstDayOfWeek = 9;
        }else if(dayOfWeek == 5){
            firstDayOfWeek = 10;
        }else if(dayOfWeek == 6){
            firstDayOfWeek = 11;
        }else if(dayOfWeek == 7){
            firstDayOfWeek = 12;
        }

        return firstDayOfWeek;
    }
    public int startWeekBefor2(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        int firstDayOfWeek = 0;

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 1){
            firstDayOfWeek = 7;
        }else if(dayOfWeek == 2){
            firstDayOfWeek = 1;
        }else if(dayOfWeek == 3){
            firstDayOfWeek = 2;
        }else if(dayOfWeek == 4){
            firstDayOfWeek = 3;
        }else if(dayOfWeek == 5){
            firstDayOfWeek = 4;
        }else if(dayOfWeek == 6){
            firstDayOfWeek = 5;
        }else if(dayOfWeek == 7){
            firstDayOfWeek = 6;
        }

        return firstDayOfWeek;
    }
    public String startDayOfYear(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);

        int year = calendar.get(Calendar.YEAR);
        String ngay = "01/01/"+year;

        return ngay;
    }
    public String StartDayOfMonthBefor(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);

        calendar.add(Calendar.MONTH,  + 1);
        Date date = calendar.getTime();
        int ngay = 0;
        int month = date.getMonth()-1;
        int year = (date.getYear() - 100) + 2000;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                ngay = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                ngay = 30;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    ngay = 29;
                } else {
                    ngay = 28;
                }
                break;
        }

        String result = ngay + "/" + month + "/" + year;

        return result;
    }

    public String setMM(int month){
        String m = month+"";
        if (month < 10){
            m = "0" + month;
        }
        return m;
    }

    public int truThoiGian(String ngayBatDau, String ngayKetThuc){
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayBatDau);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThuc);
        }catch (ParseException e){
            return 0;
        }
        //int days = Days.daysBetween(date1, date2).getDays();
        int days = (int)( (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
        return days;
    }

    public String getDate(Calendar calendar, int amount){
        String dinhDang = "dd/MM/yyyy";

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);
        Calendar cal = calendar;
        cal.add(Calendar.DAY_OF_YEAR, amount);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

}
