package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dto.TourDTO;

/**
 * This class is responsible of checking the formats of a created
 * {@link TourDTO} in the View and also has methods necessary to check if the
 * tour can be created and added into the database.
 * 
 *
 */
public class TourDateValidator {
    private TourDTO tour;

    /**
     * Creates an instance of this class.
     * 
     * @param tour The tour to be created.
     * @throws IllegalArgumentException If the tour is not valid.
     */
    public TourDateValidator(TourDTO tour) throws IllegalArgumentException {
        if (isRightTimeFormat(tour.getTourTime()) && isRightTimeFormat(tour.getTourLength())) {
            if (timeExists(tour.getTourTime())) {
                if (tourInSameDate(tour.getTourTime(), tour.getTourLength())) {
                    this.tour = tour;
                } else {
                    throw new IllegalArgumentException("The length of the tour exceeds the tour's date!");
                }
            } else {
                throw new IllegalArgumentException("Please enter a valid time!");
            }
        } else {
            throw new IllegalArgumentException("Please enter the times in HH:mm");
        }

    }

    private boolean tourInSameDate(String timeString, String length) {
        String[] times = timeString.split(":");
        double time = Double.parseDouble(times[0]);
        time += Double.parseDouble(times[1]) / 60;
        times = length.split(":");
        time += Double.parseDouble(times[0]);
        time += Double.parseDouble(times[1]) / 60;
        return time < 24;
    }

    private boolean timeExists(String time) {
        String[] times = time.split(":");
        int hours = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);

        if (((hours <= 24) && (hours >= 0)) && ((minutes <= 60) && (minutes >= 0))) {
            return true;
        } else {
            return false;
        }

    }

    private boolean isRightTimeFormat(String time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        try {
            df.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if this tour is in the same date as the given tour.
     * 
     * @param otherTour The given tour.
     * @return <code>true</code> if the tours are in the same date; false otherwise.
     * @throws ParseException if failed to parse.
     */
    public boolean isSameDate(TourDTO otherTour) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date firstD = df.parse(this.tour.getTourDate());
        Date secondD = df.parse(otherTour.getTourDate());
        int res = firstD.compareTo(secondD);
        if (res == 0) {
            return true;
        }
        return false;

    }

    /**
     * This method checks if the time of this tour is valid; i.e it does not overlap
     * with the given tour.
     * 
     * @param otherTour The given tour.
     * @return <code>true</code> if the tour does not overlap; false otherwise.
     * @throws ParseException if failed to parse.
     */
    public boolean isTimeValid(TourDTO otherTour) throws ParseException {
        String otherTourEndTimeString = calculateEndTime(otherTour.getTourTime(), otherTour.getTourLength());
        String otherTourStartTimeString = otherTour.getTourTime();
        String thisTourEndTimeString = calculateEndTime(this.tour.getTourTime(), this.tour.getTourLength());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        Date thisTourEndTime = df.parse(thisTourEndTimeString);
        Date thisTourStartTime = df.parse(this.tour.getTourTime());
        Date otherTourEndTime = df.parse(otherTourEndTimeString);
        Date otherTourStartTime = df.parse(otherTourStartTimeString);
        // System.out.println(thisTourStartTime.toString() + " " +
        // otherTourEndTimeString);
        int beforeEnd = thisTourStartTime.compareTo(otherTourStartTime); // <= 0 if this tour start time is before or
                                                                         // equal to other
                                                                         // tour start time
        int afterStart = thisTourEndTime.compareTo(otherTourEndTime); // >= 0 if this tour end time is after or equal to
                                                                      // other tour
                                                                      // end time
        if ((beforeEnd <= 0) && (afterStart >= 0)) {
            return false;
        } else {

            beforeEnd = thisTourStartTime.compareTo(otherTourEndTime); // < 0 if this tour start time is before other
                                                                       // tour end time
            afterStart = thisTourStartTime.compareTo(otherTourStartTime); // >= 0 if this tour start time is after or
                                                                          // equal to other
                                                                          // tour start time
            if ((beforeEnd < 0) && (afterStart >= 0)) {
                return false;
            } else {
                beforeEnd = thisTourEndTime.compareTo(otherTourEndTime); // < 0 if this tour end time is before other
                                                                         // tour end time
                afterStart = thisTourEndTime.compareTo(otherTourStartTime); // > 0 if this tour end time is after other
                                                                            // tour start time
                if ((beforeEnd < 0) && (afterStart > 0)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private String calculateEndTime(String startTime, String lengthTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();

        Date dTime = df.parse(lengthTime);
        cal.setTime(dTime);
        int hourToAdd = cal.get(Calendar.HOUR);
        int minuteToAdd = cal.get(Calendar.MINUTE);

        dTime = df.parse(startTime); // set startTime
        cal.setTime(dTime);
        cal.add(Calendar.HOUR, hourToAdd);
        cal.add(Calendar.MINUTE, minuteToAdd);

        return df.format(cal.getTime());
    }

}
