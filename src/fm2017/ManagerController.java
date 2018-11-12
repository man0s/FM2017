package FM2017;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;
import java.util.List;


public class ManagerController {

    String namedata;
    String surnamedata;
    String agedata;
    String positiondata;

    static int SIZE = 0;

    // UPCOMING MANAGER GAMES TABLE
    @FXML
    private TableView upcotable;
    @FXML
    private TableColumn upcodate;
    @FXML
    private TableColumn upcostadium;
    @FXML
    private TableColumn upcoopponent;
    // TEAM STATS TABLE
    @FXML
    private TableView teamstatstable;
    @FXML
    private TableColumn tsgscored;
    @FXML
    private TableColumn tsgconceded;
    @FXML
    private TableColumn tsgdifference;
    // SQUAD SIZE
    @FXML
    private TextField squadsize;
    // SQUAD TABLE
    @FXML
    private TableView squadtable;
    @FXML
    private TableColumn squadplayers;
    
    private final ObservableList<Upcoming_Games> UGdata = FXCollections.observableArrayList();
    private final ObservableList<SquadPlayer> SPdata = FXCollections.observableArrayList();
    private final ObservableList<TeamStats> TSdata = FXCollections.observableArrayList();
    // ADD PLAYER BUTTON
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        // Button was clicked, do something...
        Dialog<DBconfig> dialog = new Dialog<>();
        dialog.setTitle("Player Addition");
        dialog.setHeaderText("Player details");

        Image dbimg = new Image(getClass().getResourceAsStream("img/player.png"));
        ImageView db_img = new ImageView();
        db_img.setImage(dbimg);
        db_img.setFitWidth(96);
        db_img.setFitHeight(96);
        db_img.setPreserveRatio(true);
        db_img.setSmooth(true);
        db_img.setCache(true);
        dialog.setGraphic(db_img);

        // Set the button types.
        ButtonType cancelButtonType = new ButtonType("Close",ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType addplayerButtonType = new ButtonType("Add", ButtonBar.ButtonData.APPLY);

        dialog.getDialogPane().getButtonTypes().addAll(addplayerButtonType, cancelButtonType);
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        TextField surname = new TextField();
        TextField age = new TextField();
        List boxList = new LinkedList<String>();
        boxList.add("Other");
        boxList.add("Goalkeeper");
        ChoiceBox choicebox = new ChoiceBox(FXCollections.observableList(boxList));
        choicebox.getSelectionModel().selectFirst();


        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surname, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(age, 1, 2);
        grid.add(new Label("Position:"), 0, 3);
        grid.add(choicebox, 1, 3);

        dialog.getDialogPane().setContent(grid);

        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(name.textProperty(),
                        surname.textProperty(),
                        age.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (name.getText().isEmpty()
                        || surname.getText().isEmpty()
                        || age.getText().isEmpty());
            }
        };

        Node addPlayerButton = dialog.getDialogPane().lookupButton(addplayerButtonType);
        addPlayerButton.disableProperty().bind(bb);//Disable button when input boxes are empty

        Platform.runLater(() -> name.requestFocus());//Request focus on the name field by default
        dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addplayerButtonType && SIZE < 18) {
                    namedata = name.getText();
                    surnamedata = surname.getText();
                    agedata = age.getText();
                    positiondata = choicebox.getSelectionModel().getSelectedItem().toString();
                    setSquadPlayer(namedata + " " + surnamedata);
                    setSquadSize(SIZE + 1);
                    return new DBconfig(name.getText(), surname.getText(), age.getText(), positiondata);
                    } else if (dialogButton == addplayerButtonType){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Squad Size Error");
                        alert.setHeaderText("Squad Limit Reached..!");
                        alert.setContentText("You can't add another player to the Squad!..");
                        alert.showAndWait();
                        return null;
                    }
                    else { return null; }
        });

        dialog.showAndWait();
        
        //update the table and the available players
        Statement stmtmanadd = FM2017.conn.createStatement();
        String sqlmanadd =  "INSERT INTO Paiktis (Onomateponumo, Ilikia, Biografiko, Onoma_Omadas ) "
              + " VALUES ('"+namedata +" "+ surnamedata + "' , '" + Integer.parseInt(agedata) + "' , '" + "Professional Football Player that currently plays for "+ FM2017.ManagerAddition+ "." + "' , '" + FM2017.ManagerAddition +"' ) ";
        stmtmanadd.executeUpdate(sqlmanadd);
        
      
    }

    private static class DBconfig {

        String name;
        String surname;
        String age;
        String position;

        public DBconfig(String name, String surname, String age, String position) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.position = position;
        }
    }

    public void setUpcoming_Games(String date, String stadium, String opponent) {
        UGdata.add(new Upcoming_Games(date, stadium, opponent));
        upcodate.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Date"));
        upcostadium.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Stadium"));
        upcoopponent.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Opponent"));
        upcodate.setStyle("-fx-alignment: CENTER;");
        upcostadium.setStyle("-fx-alignment: CENTER;");
        upcoopponent.setStyle("-fx-alignment: CENTER;");
        upcotable.setItems(UGdata);
    }

    public void setTeamStats(Integer goalsscored, Integer goalsconceded, Integer goaldifference) {
        TSdata.add(new TeamStats(goalsscored, goalsconceded, goaldifference));
        tsgscored.setCellValueFactory(new PropertyValueFactory<TeamStats,Integer>("Scored"));
        tsgconceded.setCellValueFactory(new PropertyValueFactory<TeamStats,Integer>("Conceded"));
        tsgdifference.setCellValueFactory(new PropertyValueFactory<TeamStats,Integer>("GD"));
        tsgscored.setStyle("-fx-alignment: CENTER;");
        tsgconceded.setStyle("-fx-alignment: CENTER;");
        tsgdifference.setStyle("-fx-alignment: CENTER;");
        teamstatstable.setItems(TSdata);
    }

    public void setSquadSize(Integer size) {
        SIZE=size;
        squadsize.setText( "Available Players: " + size );
    }

    public void setSquadPlayer(String player) {
        SPdata.add(new SquadPlayer(player));
        squadplayers.setCellValueFactory(new PropertyValueFactory<TeamStats,Integer>("Player"));
        squadplayers.setStyle("-fx-alignment: CENTER;");
        squadtable.setItems(SPdata);
    }



    public static class Upcoming_Games {
        private final SimpleStringProperty Date;
        private final SimpleStringProperty Stadium;
        private final SimpleStringProperty Opponent;

        private Upcoming_Games(String date, String stadium, String opponent) {
            this.Date = new SimpleStringProperty(date);
            this.Stadium = new SimpleStringProperty(stadium);
            this.Opponent = new SimpleStringProperty(opponent);
        }

        public String getDate() {
            return Date.get();
        }
        public void setDate(String date) { Date.set(date); }
        public String getStadium() {
            return Stadium.get();
        }
        public void setStadium(String stadium) { Stadium.set(stadium); }
        public String getOpponent() { return Opponent.get(); }
        public void setOpponent(String opponent) { Opponent.set(opponent); }

    }

    public static class TeamStats {
        private final SimpleIntegerProperty Scored;
        private final SimpleIntegerProperty Conceded;
        private final SimpleIntegerProperty GD;

        private TeamStats(Integer goalsscored, Integer goalsconceded, Integer goaldifference) {
            this.Scored = new SimpleIntegerProperty(goalsscored);
            this.Conceded = new SimpleIntegerProperty(goalsconceded);
            this.GD = new SimpleIntegerProperty(goaldifference);
        }

        public Integer getScored() {
            return Scored.get();
        }
        public void setScored(Integer scored) { Scored.set(scored); }
        public Integer getConceded() {
            return Conceded.get();
        }
        public void setConceded(Integer conceded) { Conceded.set(conceded); }
        public Integer getGD() { return GD.get(); }
        public void setGD(Integer gd) { GD.set(gd); }

    }

    public static class SquadPlayer {
        private final SimpleStringProperty Player;

        private SquadPlayer(String player) {
            this.Player = new SimpleStringProperty(player);
        }

        public String getPlayer() {
            return Player.get();
        }
        public void setPlayer(String player) { Player.set(player); }

    }
}
