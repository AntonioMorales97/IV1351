package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import dto.GuideDTO;
import dto.LanguageDTO;
import dto.PotentialDTO;
import dto.ShowDTO;
import exception.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Guide;
import util.AlertMaker;

/**
 * This class is the controller of Guide.fxml, which shows information about a
 * specific guide and functionality such as add language and shows to the
 * guide's knowledge.
 *
 */
public class GuideController implements Initializable {
    private Guide guide;
    private ObservableList<LanguageDTO> languages;
    private ObservableList<ShowDTO> shows;
    private ObservableList<LanguageDTO> potentialLanguages;
    private ObservableList<ShowDTO> potentialShows;

    @FXML
    private ComboBox<LanguageDTO> languageCombo;
    @FXML
    private ComboBox<ShowDTO> showCombo;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<LanguageDTO> languagesTableView;
    @FXML
    private TableView<ShowDTO> showsTableView;

    @FXML
    private TableColumn<LanguageDTO, String> languageColumn;
    @FXML
    private TableColumn<ShowDTO, String> showColumn;
    @FXML
    private TableColumn<ShowDTO, String> startDateColumn;
    @FXML
    private TableColumn<ShowDTO, String> endDateColumn;

    @FXML
    private Label fNamn;
    @FXML
    private Label eNamn;
    @FXML
    private Label personnr;
    @FXML
    private Label telefonnr;
    @FXML
    private Label epost;

    @FXML
    private void previousPage(ActionEvent event) {
        Node node;
        try {
            node = (Node) FXMLLoader.load(getClass().getResource("GuidesView.fxml"));
        } catch (IOException e) {
            AlertMaker.showErrorMessage("FXML Failure", "Failed to load 'GuidesView.fxml'");
            e.printStackTrace();
            return;
        }
        anchorPane.getChildren().setAll(node);
    }

    @FXML
    private void goToGuideTours(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GuideTours.fxml"));
        Node node;
        try {
            node = loader.load();
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Failed to load page!", "Failed load the GuideTours.fxml page!");
            e.printStackTrace();
            return;
        }
        GuideToursController toursController = loader.getController();
        toursController.setUpToursPage(guide, languages, shows, new PotentialDTO(potentialLanguages, potentialShows));
        anchorPane.getChildren().setAll(node);
    }

    @FXML
    private void addLanguage(ActionEvent event) {
        LanguageDTO selectedLanguage = languageCombo.getSelectionModel().getSelectedItem();
        if (selectedLanguage == null) {
            AlertMaker.showSimpleAlert("Nothing Selected", "Please select a language!");
            return;
        }

        try {
            guide.addLanguage(selectedLanguage);
            languages.add(selectedLanguage);
            potentialLanguages.remove(selectedLanguage);
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!", "Could not add the language to the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void removeLanguage(ActionEvent event) {
        LanguageDTO selectedLanguage = languagesTableView.getSelectionModel().getSelectedItem();
        if (selectedLanguage == null) {
            AlertMaker.showSimpleAlert("Nothing Selected", "Please select a language to remove!");
            return;
        }

        try {
            guide.removeLanguage(selectedLanguage);
            languages.remove(selectedLanguage);
            potentialLanguages.add(selectedLanguage);
        } catch (IllegalStateException e) {
            AlertMaker.showErrorMessage("Could not remove language",
                    "This is because the guide probably has tour in the language to be removed!");
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!", "Could not remove the language from the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void addShow(ActionEvent event) {
        ShowDTO selectedShow = showCombo.getSelectionModel().getSelectedItem();
        if (selectedShow == null) {
            AlertMaker.showSimpleAlert("Nothing Selected", "Please select a show!");
            return;
        }

        try {
            guide.addShow(selectedShow);
            shows.add(selectedShow);
            potentialShows.remove(selectedShow);
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!", "Could not add the show to the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void removeShow(ActionEvent event) {
        ShowDTO selectedShow = showsTableView.getSelectionModel().getSelectedItem();
        if (selectedShow == null) {
            AlertMaker.showSimpleAlert("Nothing Selected", "Please select a show to remove!");
            return;
        }

        try {
            guide.removeShow(selectedShow);
            shows.remove(selectedShow);
            potentialShows.add(selectedShow);
        } catch (IllegalStateException e) {
            AlertMaker.showErrorMessage("Could not remove show",
                    "This is because the guide probably has tour in the show to be removed!");
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!", "Could not add the show to the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        showColumn.setCellValueFactory(new PropertyValueFactory<>("showName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    void setSelectedGuide(Guide guide) {
        this.guide = guide;
        setGuideInformation();
        displayLanguages();
        displayShows();
        displayPotentialLanguages();
        displayPotentialShows();
    }

    void setBackSelectedGuide(Guide guide, ObservableList<LanguageDTO> languages, ObservableList<ShowDTO> shows,
            PotentialDTO potential) {
        this.guide = guide;
        setGuideInformation();
        this.languages = languages;
        this.shows = shows;
        this.potentialLanguages = potential.getPotentialLanguages();
        this.potentialShows = potential.getPotentialShows();
        languagesTableView.setItems(this.languages);
        showsTableView.setItems(this.shows);
        languageCombo.setItems(this.potentialLanguages);
        showCombo.setItems(this.potentialShows);   
    }

    private void setGuideInformation() {
        GuideDTO guideDTO = this.guide.getGuideDTO();
        fNamn.setText(guideDTO.getFnamn());
        eNamn.setText(guideDTO.getEnamn());
        personnr.setText(guideDTO.getPersonnr());
        telefonnr.setText(guideDTO.getTelefonnr());
        epost.setText(guideDTO.getEpost());
    }

    private void displayLanguages() {
        List<LanguageDTO> languages;
        try {
            languages = guide.getLanguages();
            this.languages = FXCollections.observableArrayList(languages);
            languagesTableView.setItems(this.languages);
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "Failed to get languages from the database!");
            e.printStackTrace();
        }
    }

    private void displayShows() {
        List<ShowDTO> showExperties;
        try {
            showExperties = guide.getShows();
            this.shows = FXCollections.observableArrayList(showExperties);
            showsTableView.setItems(this.shows);
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "Failed to get shows from the database!");
            e.printStackTrace();
        }

    }

    private void displayPotentialLanguages() {
        List<LanguageDTO> potentialLanguages;
        try {
            potentialLanguages = guide.getPotentialLanguages();
            this.potentialLanguages = FXCollections.observableArrayList(potentialLanguages);
            languageCombo.setItems(this.potentialLanguages);
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "Failed to get potential languages from the database!");
            e.printStackTrace();
        }

    }

    private void displayPotentialShows() {
        List<ShowDTO> potentialShows;
        try {
            potentialShows = guide.getPotentialShows();
            this.potentialShows = FXCollections.observableArrayList(potentialShows);
            showCombo.setItems(this.potentialShows);
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "Failed to get potential shows from the database!");
            e.printStackTrace();
        }

    }

}