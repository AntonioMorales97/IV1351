package model;

import java.sql.SQLException;
import java.util.List;

import dto.GuideDTO;
import dto.LanguageDTO;
import dto.ShowDTO;
import dto.TourDTO;
import exception.NoResultException;
import integration.DBHandler;

/**
 * This class holds a {@link GuideDTO} with information of a specific guide with
 * functionality to request more data about the guide.
 *
 */
public class Guide {
    private GuideDTO guideDTO;

    /**
     * Creates an instance of {@link Guide}.
     * 
     * @param guideDTO The specific guide information to be stored.
     */
    public Guide(GuideDTO guideDTO) {
        this.guideDTO = guideDTO;
    }

    /**
     * Adds a language to the user by sending the guide information and the language
     * to {@link DBHandler}.
     * 
     * @param lang The language to be added.
     * @return true if the language was added; false otherwise.
     * @throws SQLException      if the query to the database failed.
     * @throws NoResultException if the language could not be added.
     */
    public void addLanguage(LanguageDTO lang) throws NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int rowCount = handler.addGuideLanguage(this.guideDTO, lang);
        if (rowCount == 0) {
            throw new NoResultException("Could not add language to the guide.");
        }
    }

    /**
     * Adds a show to the user by sending the guide information and the show to
     * {@link DBHandler}.
     * 
     * @param show The show to be added.
     * @return true if the show was added; false otherwise.
     * @throws SQLException      if the query to the database fails.
     * @throws NoResultException if the show could not be added.
     */
    public void addShow(ShowDTO show) throws NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int rowCount = handler.addGuideShow(this.guideDTO, show);
        if (rowCount == 0) {
            throw new NoResultException("Could not add show to the guide.");
        }
    }

    /**
     * Removes the given language from the guide by sending information to
     * {@link DBHandler}. The language will not be removed if there is a tour with
     * the guide and in the language to be removed.
     * 
     * @param lang The language to be removed.
     * @return true if the language was removed; false otherwise.
     * @throws SQLException          if the query to the database fails.
     * @throws NoResultException     if the language could not be removed.
     * @throws IllegalStateException if the guide has tours in the language to be
     *                               removed.
     */
    public void removeLanguage(LanguageDTO lang) throws IllegalStateException, NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int tourCount = handler.getTourCountByLanguage(this.guideDTO, lang);
        if (tourCount == 0) {
            int rowCount = handler.removeGuideLanguage(this.guideDTO, lang);
            if (rowCount == 0) {
                throw new NoResultException("Could not remove the language.");
            }
        } else
            throw new IllegalStateException(
                    "Could not remove the language because the guide has tours in that language!");
    }

    /**
     * Removes the given show from the guide's experties by sending information to
     * {@link DBHandler}. The show experties will not be removed if there is a tour
     * with the guide and to that show that would be removed.
     * 
     * @param show The show to be removed.
     * @return true if the show was removed; false otherwise.
     * @throws SQLException          if the query to the database fails.
     * @throws NoResultException     if the language could not be removed.
     * @throws IllegalStateException if the guide has tours in the show to be
     *                               removed.
     */
    public void removeShow(ShowDTO show) throws IllegalStateException, NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int tourCount = handler.getTourCountByShow(this.guideDTO, show);
        if (tourCount == 0) {
            int rowCount = handler.removeGuideShow(this.guideDTO, show);
            if (rowCount == 0) {
                throw new NoResultException("Could not remove the show.");
            }
        } else
            throw new IllegalStateException("Could not remove the show because the guide has tours in that show!");
    }

    /**
     * Gets and returns the languages of the guide.
     * 
     * @return a <code>List</code> of {@link LanguageDTO}:s.
     * @throws SQLException if the query to the database failed.
     */
    public List<LanguageDTO> getLanguages() throws SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        return handler.getGuideLanguages(this.guideDTO);
    }

    /**
     * Gets and returns the shows of the guide.
     * 
     * @return a <code>List</code> of {@link ShowDTO}:s.
     * @throws SQLException if the query to the database failed.
     */
    public List<ShowDTO> getShows() throws SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        return handler.getGuideShows(this.guideDTO);
    }

    /**
     * Gets and returns the languages that is not of the guide, hence potential
     * languages.
     * 
     * @return a <code>List</code> of {@link LanguageDTO}:s.
     * @throws SQLException if the query to the database failed.
     */
    public List<LanguageDTO> getPotentialLanguages() throws SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        return handler.getPotentialLanguages(this.guideDTO);
    }

    /**
     * Gets and returns the shows that is not of the guide, hence potential shows.
     * 
     * @return a <code>List</code> of {@link ShowDTO}:s.
     * @throws SQLException if the query to the database failed.
     */
    public List<ShowDTO> getPotentialShows() throws SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        return handler.getPotentialShows(this.guideDTO);
    }

    /**
     * Returns the information of the guide.
     * 
     * @return the {@link GuideDTO}.
     */
    public GuideDTO getGuideDTO() {
        return this.guideDTO;
    }

    /**
     * Gets and returns the tours that are held by the guide.
     * 
     * @return a <code>List</code> of {@link TourDTO}:s.
     * @throws SQLException if the query to the database failed.
     */
    public List<TourDTO> getTours() throws SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        return handler.getGuideTours(this.guideDTO);
    }

    /**
     * Adds a tour with the user by sending the guide information and the tour to
     * {@link DBHandler}.
     * 
     * @param tour the tour to be added.
     * @throws NoResultException if the tour was not added.
     * @throws SQLException      if the query to the database failed.
     */
    public void addTour(TourDTO tour) throws NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int rowCount = handler.addGuideTour(this.guideDTO, tour);
        if (rowCount == 0) {
            throw new NoResultException("Could not add the tour with the guide!");
        }
    }

    /**
     * Removes the tour that is held by the guide by sending information to
     * {@link DBHandler}.
     * 
     * @param tour the tour to be removed.
     * @throws NoResultException if the tour was not removed.
     * @throws SQLException      if the query to the database failed.
     */
    public void removeTour(TourDTO tour) throws NoResultException, SQLException {
        DBHandler handler = DBHandler.getDbhandler();
        int rowCount = handler.removeGuideTour(this.guideDTO, tour);
        if (rowCount == 0) {
            throw new NoResultException("Could not remove the tour with the guide!");
        }
    }
}
