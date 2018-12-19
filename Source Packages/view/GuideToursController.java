package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controller.Controller;
import dto.LanguageDTO;
import dto.PotentialDTO;
import dto.ShowDTO;
import dto.TourDTO;
import exception.NoResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Guide;
import model.TourDateValidator;
import util.AlertMaker;

/**
 * This class is the controller of GuideTours.fxml, which shows the tours that
 * are held by the guide in {@link GuideController} and has functionality such
 * as add and removal of tours.
 *
 */
public class GuideToursController implements Initializable {
    private Guide guide;
    private ObservableList<LanguageDTO> languages;
    private ObservableList<ShowDTO> shows;
    private ObservableList<TourDTO> tours;

    /**
     * This is not used here. This is used to reduce calls to the database when
     * returning to previous page!
     **/
    private PotentialDTO dataFromPreviousPage;
    /********************************************************************************************************/

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<LanguageDTO> languageCombo;
    @FXML
    private ComboBox<ShowDTO> showCombo;
    @FXML
    private TextField tourStartTimeHour;
    @FXML
    private TextField tourStartTimeMinute;
    @FXML
    private TextField tourLengthHour;
    @FXML
    private TextField tourLengthMinute;

    @FXML
    private TableView<TourDTO> toursTableView;
    @FXML
    private TableColumn<TourDTO, String> showColumn;
    @FXML
    private TableColumn<TourDTO, String> showStartColumn;
    @FXML
    private TableColumn<TourDTO, String> showEndColumn;
    @FXML
    private TableColumn<TourDTO, String> tourDateColumn;
    @FXML
    private TableColumn<TourDTO, String> tourTimeColumn;
    @FXML
    private TableColumn<TourDTO, String> tourLengthColumn;
    @FXML
    private TableColumn<TourDTO, String> tourLanguageColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private void previousPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Guide.fxml"));
        Node node;
        try {
            node = loader.load();
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Failed to load page!", "Failed load the Guide.fxml page!");
            e.printStackTrace();
            return;
        }
        GuideController guideController = loader.getController();
        guideController.setBackSelectedGuide(guide, languages, shows, dataFromPreviousPage);
        anchorPane.getChildren().setAll(node);
    }

    @FXML
    private void setAvailableTourDates(ActionEvent event) {
        this.datePicker.setDisable(false);
        ShowDTO selectedShow = showCombo.getSelectionModel().getSelectedItem();
        if (selectedShow == null) {
            // Should never come here... just in case.
            return;
        }
        final Callback<DatePicker, DateCell> availableDates = getDayCellFactory(selectedShow.getEndDate(),
                selectedShow.getStartDate());
        this.datePicker.setDayCellFactory(availableDates);
    }

    private Callback<DatePicker, DateCell> getDayCellFactory(String maxDate, String minDate) {
        DatePicker maxDatePicker = new DatePicker();
        LocalDate maximumDate = LocalDate.parse(maxDate);
        maxDatePicker.setValue(maximumDate);
        DatePicker minDatePicker = new DatePicker();
        LocalDate minimumDate = LocalDate.parse(minDate);
        minDatePicker.setValue(minimumDate);

        final Callback<DatePicker, DateCell> dayCellFactory;
        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(maxDatePicker.getValue())) { // Disable all dates after maxDate
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // To set background on different color
                } else if (item.isBefore(minDatePicker.getValue())) { // Disable all dates before minDate
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // To set background on different color
                }
            }
        };
        return dayCellFactory;
    }

    @FXML
    private void addTour(ActionEvent event) {
        if (isAnyFieldEmpty()) {
            AlertMaker.showSimpleAlert("Empty fields", "Please enter all fields!");
            return;
        }

        String tourStartTime = tourStartTimeHour.getText() + ":" + tourStartTimeMinute.getText();
        String tourLengthTime = tourLengthHour.getText() + ":" + tourLengthMinute.getText();
        String language = languageCombo.getSelectionModel().getSelectedItem().getLanguage();
        String tourDate = datePicker.getValue().toString();
        ShowDTO show = showCombo.getSelectionModel().getSelectedItem();

        TourDTO tour = new TourDTO(tourStartTime, tourDate, language, tourLengthTime, show);

        Controller contr = Controller.getController();
        TourDateValidator tourValidator;
        try {
            tourValidator = contr.getTourDateValidator(tour);
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Wrong time!", e.getMessage());
            return;
        }

        try {
            if (tourOverlaps(tourValidator)) {
                AlertMaker.showSimpleAlert("Could not add the tour.",
                        "The tour seems to overlap another already existing tour.");
                return;
            }
        } catch (ParseException e) { // should never come here...
            AlertMaker.showErrorMessage("Wrong format!", "Something went wrong when parsing the dates.");
            e.printStackTrace();
        }

        try {
            guide.addTour(tour);
            this.tours.add(tour);
            tourStartTimeHour.clear();
            tourStartTimeMinute.clear();
            tourLengthHour.clear();
            tourLengthMinute.clear();
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!", "Could not add the tour with the guide to the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void removeTour(ActionEvent event) {
        TourDTO selectedTour = toursTableView.getSelectionModel().getSelectedItem();
        if (selectedTour == null) {
            AlertMaker.showSimpleAlert("Noting Selected", "Please select a tour to remove!");
            return;
        }

        try {
            guide.removeTour(selectedTour);
            this.tours.remove(selectedTour);
        } catch (NoResultException e) {
            AlertMaker.showErrorMessage("Nothing happened!",
                    "Could not remove the tour with the guide from the database!");
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "The query to the database failed!");
            e.printStackTrace();
        }
    }

    private boolean tourOverlaps(TourDateValidator tourValidator) throws ParseException {
        for (TourDTO tour : this.tours) {
            if (tourValidator.isSameDate(tour)) {
                if (tourValidator.isTimeValid(tour)) {
                    continue;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAnyFieldEmpty() {
        if (showCombo.getSelectionModel().getSelectedItem() == null) {
            return true;
        } else if (datePicker.getValue() == null) {
            return true;
        } else if (languageCombo.getSelectionModel().getSelectedItem() == null) {
            return true;
        } else if (tourStartTimeHour.getText().length() == 0) {
            return true;
        } else if (tourStartTimeMinute.getText().length() == 0) {
            return true;
        } else if (tourLengthHour.getText().length() == 0) {
            return true;
        } else if (tourLengthMinute.getText().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        showColumn.setCellValueFactory(new PropertyValueFactory<>("showName"));
        showStartColumn.setCellValueFactory(new PropertyValueFactory<>("showStartDate"));
        showEndColumn.setCellValueFactory(new PropertyValueFactory<>("showEndDate"));
        tourDateColumn.setCellValueFactory(new PropertyValueFactory<>("tourDate"));
        tourTimeColumn.setCellValueFactory(new PropertyValueFactory<>("tourTime"));
        tourLengthColumn.setCellValueFactory(new PropertyValueFactory<>("tourLength"));
        tourLanguageColumn.setCellValueFactory(new PropertyValueFactory<>("tourLanguage"));
        this.datePicker.setDisable(true);
    }

    void setUpToursPage(Guide guide, ObservableList<LanguageDTO> languages, ObservableList<ShowDTO> shows,
            PotentialDTO dataFromPrevPage) {
        this.guide = guide;
        this.languages = languages;
        this.shows = shows;
        this.languageCombo.setItems(this.languages);
        this.showCombo.setItems(this.shows);
        this.dataFromPreviousPage = dataFromPrevPage;
        setTours();
    }

    private void setTours() {
        List<TourDTO> tours;
        try {
            tours = guide.getTours();
            this.tours = FXCollections.observableArrayList(tours);
            toursTableView.setItems(this.tours);
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Database Failure", "Failed to get tours from the database!");
            e.printStackTrace();
        }
    }

}
