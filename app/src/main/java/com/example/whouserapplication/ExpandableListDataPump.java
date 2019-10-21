package com.example.whouserapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<DayTime>> getData(){
        HashMap<String, List<DayTime>> expandableListDetail = new HashMap<String, List<DayTime>>();

        List<DayTime> dayTime = new ArrayList<>();
        dayTime.add(new DayTime("Saturday", "10:00 am - 08:00 pm"));
        dayTime.add(new DayTime("Sunday", "10:00 am - 08:00 pm"));

        String title = "Open . Closes 10:00 pm";

        expandableListDetail.put(title, dayTime);
        return expandableListDetail;
    }

    public static String getCurrentDay(){

        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return daysArray[day];

    }
}
