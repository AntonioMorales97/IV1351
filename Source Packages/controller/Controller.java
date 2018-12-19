package controller;

import java.sql.SQLException;
import java.util.List;

import dto.GuideDTO;
import dto.TourDTO;
import model.Guide;
import model.Guides;
import model.TourDateValidator;

/**
 * The Controller class; handles the communication between the View and the
 * Model.
 *
 */
public class Controller {
    private static final Controller CONTROLLER = new Controller();

    private Controller() {
    }

    /**
     * Returns the {@link Controller}-instance.
     * 
     * @return the {@link Controller}
     */
    public static Controller getController() {
        return Controller.CONTROLLER;
    }

    /**
     * Gets and returns all of the guides as {@link GuideDTO}:s.
     * 
     * @return a <code>List</code> of {@link GuideDTO}:s.
     * @throws SQLException if the query to the database fails.
     */
    public List<GuideDTO> getGuides() throws SQLException {
        Guides guides = new Guides();
        return guides.getGuides();
    }

    /**
     * Creates and returns a {@link Guide} with the given guide information in
     * {@link GuideDTO}.
     * 
     * @param guideDTO A DTO holding basic information of a guide.
     * @return the created {@link Guide}.
     * @throws SQLException if the query to the database fails.
     */
    public Guide getGuide(GuideDTO guideDTO) throws SQLException {
        Guide guide = new Guide(guideDTO);
        return guide;
    }

    /**
     * Creates and returns a {@link TourDateValidator} with the given tour
     * information in the given {@link TourDTO}.
     * 
     * @param tour The given tour.
     * @return a {@link TourDateValidator} for the given tour.
     */
    public TourDateValidator getTourDateValidator(TourDTO tour) {
        return new TourDateValidator(tour);
    }

}