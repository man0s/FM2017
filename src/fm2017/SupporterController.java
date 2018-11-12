package FM2017;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class SupporterController{

    // PREVIOUS GAMES TABLE
    @FXML
    private TableView prevtable;
    @FXML
    private TableColumn prevseen;
    @FXML
    private TableColumn prevdate;
    @FXML
    private TableColumn prevscorers;
    @FXML
    private TableColumn prevscore;

    private final ObservableList<Previous_Games> PGdata = FXCollections.observableArrayList();

    // UPCOMING GAMES TABLE
    @FXML
    private TableView upcotable;
    @FXML
    private TableColumn upcodate;
    @FXML
    private TableColumn upcotime;
    @FXML
    private TableColumn upcostadium;
    @FXML
    private TableColumn upcoreferees;

    private final ObservableList<Upcoming_Games> UGdata = FXCollections.observableArrayList();

    // SEAT TABLE
    @FXML
    private TableView seattable;
    @FXML
    private TableColumn seatno;
    @FXML
    private TableColumn seatavail;

    private final ObservableList<Seats> SAdata = FXCollections.observableArrayList();

    // OFFERS TABLE
    @FXML
    private TableView offers;
    @FXML
    private TableColumn offerscol;
    // TEAM's GD TABLE
    @FXML
    private TableView teamGD;
    @FXML
    private TableColumn GDcol;
    
    private final ObservableList<Favourite_Player> FPdata = FXCollections.observableArrayList();
    // FAV PLAYER TABLE
    @FXML
    private TableView favplayer;
    @FXML
    private TableColumn goalscol;
    @FXML
    private TableColumn playercol;


    public void setPrevious_Games(String seen, String date, String scorers, String score) {
        PGdata.add(new Previous_Games(seen, date, scorers, score));
        prevseen.setCellValueFactory(new PropertyValueFactory<Previous_Games,String>("Seen"));
        prevdate.setCellValueFactory(new PropertyValueFactory<Previous_Games,String>("Date"));
        prevscorers.setCellValueFactory(new PropertyValueFactory<Previous_Games,String>("Scorers"));
        prevscore.setCellValueFactory(new PropertyValueFactory<Previous_Games,String>("Score"));
        prevseen.setStyle("-fx-alignment: CENTER;");
        prevdate.setStyle("-fx-alignment: CENTER;");
        prevscorers.setStyle("-fx-alignment: CENTER;");
        prevscore.setStyle("-fx-alignment: CENTER;");
        prevtable.setItems(PGdata);
    }

    public void setUpcoming_Games(String date, String time, String stadium, String referees) {
        UGdata.add(new Upcoming_Games(date, time, stadium, referees));
        upcodate.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Date"));
        upcotime.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Time"));
        upcostadium.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Stadium"));
        upcoreferees.setCellValueFactory(new PropertyValueFactory<Upcoming_Games,String>("Referees"));
        upcodate.setStyle("-fx-alignment: CENTER;");
        upcotime.setStyle("-fx-alignment: CENTER;");
        upcostadium.setStyle("-fx-alignment: CENTER;");
        upcoreferees.setStyle("-fx-alignment: CENTER;");
        upcotable.setItems(UGdata);
    }

    public void setSeats(Integer number, String available) {
        SAdata.add(new Seats(number, available));
        seatno.setCellValueFactory(new PropertyValueFactory<Seats,Integer>("Number"));
        seatavail.setCellValueFactory(new PropertyValueFactory<Seats,String>("Available"));
        seatno.setStyle("-fx-alignment: CENTER;");
        seatavail.setStyle("-fx-alignment: CENTER;");
        seattable.setItems(SAdata);
    }

    public void setOffers(String OfferType) {
        ObservableList<Offer> data = FXCollections.observableArrayList(new Offer(OfferType));
        offerscol.setCellValueFactory(new PropertyValueFactory<Offer,String>("offerType"));
        offerscol.setStyle("-fx-alignment: CENTER;");
        offers.setItems(data);
    }

    public void setTeamGD(Integer teamgd) {
        ObservableList<TeamGD> data = FXCollections.observableArrayList(new TeamGD(teamgd));
        GDcol.setCellValueFactory(new PropertyValueFactory<TeamGD,Integer>("teamGD"));
        GDcol.setStyle("-fx-alignment: CENTER;");
        teamGD.setItems(data);
    }

    public void setFavourite_Player(Integer goals, String player) {
        FPdata.add(new Favourite_Player(goals, player));
        goalscol.setCellValueFactory(new PropertyValueFactory<Favourite_Player,Integer>("Goals"));
        playercol.setCellValueFactory(new PropertyValueFactory<Favourite_Player,String>("Player"));
        goalscol.setStyle("-fx-alignment: CENTER;");
        playercol.setStyle("-fx-alignment: CENTER;");
        favplayer.setItems(FPdata);
    }

    public static class Previous_Games {
        private final SimpleStringProperty Seen;
        private final SimpleStringProperty Date;
        private final SimpleStringProperty Scorers;
        private final SimpleStringProperty Score;

        private Previous_Games(String seen, String date, String scorers, String score) {
            this.Seen = new SimpleStringProperty(seen);
            this.Date = new SimpleStringProperty(date);
            this.Scorers = new SimpleStringProperty(scorers);
            this.Score = new SimpleStringProperty(score);
        }

        public String getSeen() {
            return Seen.get();
        }
        public void setSeen(String seen) { Date.set(seen); }
        public String getDate() {
            return Date.get();
        }
        public void setDate(String date) { Date.set(date); }
        public String getScorers() {
            return Scorers.get();
        }
        public void setScorers(String scorers) { Scorers.set(scorers); }
        public String getScore() { return Score.get(); }
        public void setScore(String score) { Score.set(score); }

    }

    public static class Upcoming_Games {
        private final SimpleStringProperty Date;
        private final SimpleStringProperty Time;
        private final SimpleStringProperty Stadium;
        private final SimpleStringProperty Referees;

        private Upcoming_Games(String date, String time, String stadium, String referees) {
            this.Date = new SimpleStringProperty(date);
            this.Time = new SimpleStringProperty(time);
            this.Stadium = new SimpleStringProperty(stadium);
            this.Referees = new SimpleStringProperty(referees);
        }

        public String getDate() {
            return Date.get();
        }
        public void setDate(String date) { Date.set(date); }
        public String getTime() {
            return Time.get();
        }
        public void setTime(String time) { Time.set(time); }
        public String getStadium() {
            return Stadium.get();
        }
        public void setStadium(String stadium) { Stadium.set(stadium); }
        public String getReferees() { return Referees.get(); }
        public void setReferees(String referees) { Referees.set(referees); }

    }

    public static class Seats{
        private final SimpleIntegerProperty Number;
        private final SimpleStringProperty Available;

        private Seats(Integer number, String available) {
            this.Number = new SimpleIntegerProperty(number);
            this.Available = new SimpleStringProperty(available);
        }

        public Integer getNumber() { return Number.get(); }
        public void setNumber(Integer number) { Number.set(number); }
        public String getAvailable() { return Available.get(); }
        public void setAvailable(String available) { Available.set(available); }

    }

    public static class Offer {
        private final SimpleStringProperty offerType;

        private Offer(String offertype) {
            this.offerType = new SimpleStringProperty(offertype);
        }

        public String getOfferType() { return offerType.get(); }
        public void setOfferType(String offertype) { offerType.set(offertype); }
    }

    public static class TeamGD {
        private final SimpleIntegerProperty teamGD;

        private TeamGD(Integer teamgd) {
            this.teamGD = new SimpleIntegerProperty(teamgd);
        }

        public Integer getTeamGD(){ return teamGD.get(); }
        public void setTeamGD(Integer teamgd) { teamGD.set(teamgd); }
    }

    public static class Favourite_Player {
        private final SimpleIntegerProperty Goals;
        private final SimpleStringProperty Player;

        private Favourite_Player(Integer goalsfp, String playerfp) {
            this.Goals = new SimpleIntegerProperty(goalsfp);
            this.Player = new SimpleStringProperty(playerfp);
        }

        public Integer getGoals() { return Goals.get(); }
        public void setGoals(Integer goals) { Goals.set(goals); }
        public String getPlayer() { return Player.get(); }
        public void setPlayer(String player) { Player.set(player); }

    }

}