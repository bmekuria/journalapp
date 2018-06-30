package com.example.b.journalapp.Utilites;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatting {

    SimpleDateFormat mTimeAndDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public String LongtoDate(long long_date) {
        return String.valueOf(mTimeAndDate.format(new Date(long_date)));
    }

}
