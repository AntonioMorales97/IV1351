package dto;

/**
 * This class holds information about tour.
 *
 */
public class TourDTO {
    private ShowDTO show;
    private String tourTime;
    private String tourDate;
    private String tourLanguage;
    private String tourLength;

    /**
     * Creates an instance of this class.
     * 
     * @param tourTime     The time when the tour starts.
     * @param tourDate     The date when the tour is hold.
     * @param tourLanguage The language of the tour.
     * @param tourLength   The length of the tour.
     * @param tourShow     The show that the tour will be in.
     */
    public TourDTO(String tourTime, String tourDate, String tourLanguage, String tourLength, ShowDTO tourShow) {
        this.tourTime = tourTime;
        this.tourDate = tourDate;
        this.tourLanguage = tourLanguage;
        this.tourLength = tourLength;
        this.show = tourShow;
    }

    /**
     * @return the tour's start time.
     */
    public String getTourTime() {
        return this.tourTime;
    }

    /**
     * @return the tour's date.
     */
    public String getTourDate() {
        return this.tourDate;
    }

    /**
     * @return the tour's language.
     */
    public String getTourLanguage() {
        return this.tourLanguage;
    }

    /**
     * @return the tour's length.
     */
    public String getTourLength() {
        return this.tourLength;
    }

    /**
     * @return the name of the show that the tour will be in.
     */
    public String getShowName() {
        return this.show.getShowName();
    }

    /**
     * @return the start date of the show that the tour will be in.
     */
    public String getShowStartDate() {
        return this.show.getStartDate();
    }

    /**
     * @return the end date of the show that the tour will be in.
     */
    public String getShowEndDate() {
        return this.show.getEndDate();
    }

    /**
     * @return the {@link ShowDTO} of the show that the tour will be in.
     */
    public ShowDTO getShowDTO() {
        return this.show;
    }

}
