package dto;

import javafx.collections.ObservableList;

/**
 * This class is used in the View for passing information about potential
 * languages and shows that could be added for a guide. This is mostly used for
 * reducing database calls when navigating through pages.
 *
 */
public class PotentialDTO {
    private ObservableList<LanguageDTO> potentialLanguages;
    private ObservableList<ShowDTO> potentialShows;

    /**
     * Creates an instance of this class.
     * 
     * @param potentialLanguages Languages that could be added for a guide.
     * @param potentialShows     Shows that could be added for guide.
     */
    public PotentialDTO(ObservableList<LanguageDTO> potentialLanguages, ObservableList<ShowDTO> potentialShows) {
        this.potentialLanguages = potentialLanguages;
        this.potentialShows = potentialShows;
    }

    /**
     * @return The languages that could be added to a guide.
     */
    public ObservableList<LanguageDTO> getPotentialLanguages() {
        return this.potentialLanguages;
    }

    /**
     * @return The shows that could be added to a guide.
     */
    public ObservableList<ShowDTO> getPotentialShows() {
        return this.potentialShows;
    }

}
