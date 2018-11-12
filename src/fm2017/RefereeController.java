package FM2017;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class RefereeController{
    // REF's UPCOMMING GAMES table
    @FXML
    private TableView upcotable;
    @FXML
    private TableColumn upcorole;
    @FXML
    private TableColumn upcodate;
    @FXML
    private TableColumn upcostadium;
    @FXML
    private TableColumn upcoteams;

    private final ObservableList<Upcoming_Games> UGdata = FXCollections.observableArrayList();

    public void setUpcoming_Games(String role, String date, String stadium, String teams) {
        UGdata.add(new Upcoming_Games(role, date, stadium, teams));
        upcorole.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Role"));
        upcodate.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Date"));
        upcostadium.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Stadium"));
        upcoteams.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Teams"));
        upcorole.setStyle("-fx-alignment: CENTER;");
        upcodate.setStyle("-fx-alignment: CENTER;");
        upcostadium.setStyle("-fx-alignment: CENTER;");
        upcoteams.setStyle("-fx-alignment: CENTER;");
        upcotable.setItems(UGdata);
    }

    public static class Upcoming_Games {
        private final SimpleStringProperty Role;
        private final SimpleStringProperty Date;
        private final SimpleStringProperty Stadium;
        private final SimpleStringProperty Teams;

        private Upcoming_Games(String role, String date, String stadium, String teams) {
            this.Role = new SimpleStringProperty(role);
            this.Date = new SimpleStringProperty(date);
            this.Stadium = new SimpleStringProperty(stadium);
            this.Teams = new SimpleStringProperty(teams);
        }

        public String getRole() {
            return Role.get();
        }
        public void setRole(String role) { Role.set(role); }
        public String getDate() {
            return Date.get();
        }
        public void setDate(String date) { Date.set(date); }
        public String getStadium() {
            return Stadium.get();
        }
        public void setStadium(String stadium) { Stadium.set(stadium); }
        public String getTeams() { return Teams.get(); }
        public void setReferees(String teams) { Teams.set(teams); }

    }
}
