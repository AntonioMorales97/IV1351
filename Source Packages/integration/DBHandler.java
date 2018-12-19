package integration;

import dto.GuideDTO;
import dto.LanguageDTO;
import dto.ShowDTO;
import dto.TourDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class holds and executes all the queries used by this application.
 *
 */
public class DBHandler {

    private static final DBHandler DBHANDLER = new DBHandler();
    private DBAccess db = DBAccess.getDBAccess();

    /**
     * @return the instance of this class.
     */
    public static DBHandler getDbhandler() {
        return DBHANDLER;
    }

    /**
     * Executes a query to the database to get all of the guides.
     * 
     * @return an <ArrayList> of {@link GuideDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<GuideDTO> getGuides() throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT * FROM guide";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<GuideDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new GuideDTO(rs.getString("fnamn"), rs.getString("enamn"), rs.getString("personnr"),
                    rs.getString("telefonnr"), rs.getString("epost")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the languages.
     * 
     * @return an <ArrayList> of {@link LanguageDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<LanguageDTO> getLanguages() throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT * FROM språk";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<LanguageDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new LanguageDTO(rs.getString("namn")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the shows.
     * 
     * @return an <ArrayList> of {@link ShowDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<ShowDTO> getShows() throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT * FROM utställning";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<ShowDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new ShowDTO(rs.getString("namn"), rs.getInt("id"), rs.getString("startdatum"),
                    rs.getString("slutdatum")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the languages known by the
     * given guide.
     * 
     * @param guide The given guide.
     * @return an <ArrayList> of {@link LanguageDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<LanguageDTO> getGuideLanguages(GuideDTO guide) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT språk FROM språkkunskap WHERE guide = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        ResultSet rs = stmt.executeQuery();
        ArrayList<LanguageDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new LanguageDTO(rs.getString("språk")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the languages that are not
     * known by the given guide.
     * 
     * @param guide The given guide.
     * @return an <ArrayList> of {@link LanguageDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<LanguageDTO> getPotentialLanguages(GuideDTO guide) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT namn FROM språk WHERE namn NOT IN (SELECT språk FROM språkkunskap WHERE guide = ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        ResultSet rs = stmt.executeQuery();
        ArrayList<LanguageDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new LanguageDTO(rs.getString("namn")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the shows known by the given
     * guide.
     * 
     * @param guide The given guide.
     * @return an <ArrayList> of {@link ShowDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<ShowDTO> getGuideShows(GuideDTO guide) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT namn, id, startdatum, slutdatum FROM utställning WHERE id IN (SELECT utställning FROM utställningskompetens WHERE guide = ?);";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        ResultSet rs = stmt.executeQuery();
        ArrayList<ShowDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new ShowDTO(rs.getString("namn"), rs.getInt("id"), rs.getString("startdatum"),
                    rs.getString("slutdatum")));
        }
        return result;
    }

    /**
     * Executes a query to the database to get all of the shows that are not known
     * by the given guide.
     * 
     * @param guide The given guide.
     * @return an <ArrayList> of {@link ShowDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<ShowDTO> getPotentialShows(GuideDTO guide) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT namn, id, startdatum, slutdatum FROM utställning WHERE id NOT IN (SELECT utställning FROM utställningskompetens WHERE guide = ?);";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        ResultSet rs = stmt.executeQuery();
        ArrayList<ShowDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new ShowDTO(rs.getString("namn"), rs.getInt("id"), rs.getString("startdatum"),
                    rs.getString("slutdatum")));
        }
        return result;
    }

    /**
     * Executes a query to the database to add the given language to the given
     * guide's language-knowledge table.
     * 
     * @param guide The given guide.
     * @param lang  The given language.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int addGuideLanguage(GuideDTO guide, LanguageDTO lang) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "INSERT INTO språkkunskap (guide, språk) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setString(2, lang.getLanguage());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }

    /**
     * Executes a query to the database to add the given show to the given guide's
     * show-knowledge table.
     * 
     * @param guide The given guide.
     * @param show  The given show.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int addGuideShow(GuideDTO guide, ShowDTO show) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "INSERT INTO utställningskompetens (guide, utställning) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setInt(2, show.getID());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }

    /**
     * Executes a query to the database to remove the given language from the given
     * guide's language-knowledge table.
     * 
     * @param guide The given guide.
     * @param lang  The given language to be removed.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int removeGuideLanguage(GuideDTO guide, LanguageDTO lang) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "DELETE FROM språkkunskap WHERE guide = ? AND språk= ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setString(2, lang.getLanguage());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }

    /**
     * Executes a query to the database to get the count of rows of the tours hold
     * by the given guide in the given language.
     * 
     * @param guide The given guide.
     * @param lang  The given language.
     * @return the number of rows in the table returned by the query.
     * @throws SQLException if the query to the database fails.
     */
    public int getTourCountByLanguage(GuideDTO guide, LanguageDTO lang) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT COUNT(*) AS Turer FROM tur WHERE guide = ? AND språk = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setString(2, lang.getLanguage());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("Turer");
    }

    /**
     * Executes a query to the database to remove the given show from the given
     * guide's show-knowledge table.
     * 
     * @param guide The given guide.
     * @param show  The given show to be removed.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int removeGuideShow(GuideDTO guide, ShowDTO show) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "DELETE FROM utställningskompetens WHERE guide = ? AND utställning = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setInt(2, show.getID());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }

    /**
     * Executes a query to the database to get the count of rows of the tours hold
     * by the given guide in the given show.
     * 
     * @param guide The given guide.
     * @param show  The given show.
     * @return the number of rows in the table returned by the query.
     * @throws SQLException if the query to the database fails.
     */
    public int getTourCountByShow(GuideDTO guide, ShowDTO show) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT COUNT(*) AS Turer FROM tur WHERE guide = ? AND utställning = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setInt(2, show.getID());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("Turer");
    }

    /**
     * Executes a query to the database to get all of the tours that are held by the
     * given guide.
     * 
     * @param guide The given guide.
     * @return an <ArrayList> of {@link TourDTO}.
     * @throws SQLException if the query to the database fails.
     */
    public ArrayList<TourDTO> getGuideTours(GuideDTO guide) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "SELECT tid, datum, språk, längd, id AS utställningsid, namn AS utställningsnamn, startdatum, slutdatum FROM Tur, Utställning WHERE Tur.guide = ? AND Tur.utställning = Utställning.id ORDER BY Tur.datum, Tur.tid";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        ResultSet rs = stmt.executeQuery();
        ArrayList<TourDTO> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new TourDTO(rs.getString("tid"), rs.getString("datum"), rs.getString("språk"),
                    rs.getString("längd"), new ShowDTO(rs.getString("utställningsnamn"), rs.getInt("utställningsid"),
                            rs.getString("startdatum"), rs.getString("slutdatum"))));
        }
        return result;
    }

    /**
     * Executes a query to the database to add the given tour with the given guide
     * into the database.
     * 
     * @param guide The given guide.
     * @param tour  The given tour.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int addGuideTour(GuideDTO guide, TourDTO tour) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "INSERT INTO tur (guide, tid, datum, språk, längd, utställning) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setString(2, tour.getTourTime());
        stmt.setString(3, tour.getTourDate());
        stmt.setString(4, tour.getTourLanguage());
        stmt.setString(5, tour.getTourLength());
        stmt.setInt(6, tour.getShowDTO().getID());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }

    /**
     * Executes a query to the database to remove the given tour with the given
     * guide from the database.
     * 
     * @param guide The given guide.
     * @param tour  The tour to be removed.
     * @return the number of rows affected, which can be 1 or 0.
     * @throws SQLException if the query to the database fails.
     */
    public int removeGuideTour(GuideDTO guide, TourDTO tour) throws SQLException {
        Connection con = db.getDBConnection();
        String query = "DELETE FROM tur WHERE guide = ? AND tid = ? AND datum = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, guide.getPersonnr());
        stmt.setString(2, tour.getTourTime());
        stmt.setString(3, tour.getTourDate());
        int res = stmt.executeUpdate();
        stmt.close();
        con.commit();
        return res;
    }
}
