package FM2017;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Optional;


public class ChairmanController {
    // EARNINGS TABLE
    @FXML
    private TableView earningstable;
    @FXML
    private TableColumn ticketnumcol;
    @FXML
    private TableColumn earningscol;
    // TICKET TYPE ABLE
    @FXML
    private TableView tickettable;
    @FXML
    private TableColumn seasoncol;
    @FXML
    private TableColumn matchdaycol;
    // SEASON TICKET OFFER BUTTON
    @FXML
    private void handleSeasonTicketOffer(ActionEvent event) throws SQLException {
        // LOGIN DIALOG BOX
        String account; //get the account name
        java.util.List<String> choices = new ArrayList<>();
        
        Statement stmtchst = FM2017.conn.createStatement();
        String sqlchst = " SELECT DISTINCT pano_apo_misous.Onomateponumo, Omada.Onoma AS OnomaOmadas "
                       + " FROM pano_apo_misous, Katoxos_aplo, Filathlos, Omada, Proedros "
                       + " WHERE pano_apo_misous.Onomateponumo=Katoxos_aplo.Onomateponumo AND Katoxos_aplo.Onomateponumo=Filathlos.Onomateponumo AND Filathlos.Omada_Onoma=Omada.Onoma "
                       + " AND Proedros.Onomateponumo='" + FM2017.ChairmanName +"' AND Omada.Onoma=Proedros.Omada_Onoma ";
        ResultSet rschst = stmtchst.executeQuery(sqlchst);
        
        String OmadaOnoma = null;
        
            while (rschst.next()){
                        OmadaOnoma=rschst.getString("OnomaOmadas");
                        choices.add(rschst.getString("Onomateponumo"));
            }
                
                

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0),choices);
        dialog.setTitle("Season Ticket Offer Menu");
        dialog.setHeaderText("Season Ticket Offer");
        dialog.setContentText("Make an Offer to:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/ticket.png"));
        ImageView login_img = new ImageView();
        login_img.setImage(loginimg);
        login_img.setFitWidth(64);
        login_img.setFitHeight(64);
        login_img.setPreserveRatio(true);
        login_img.setSmooth(true);
        login_img.setCache(true);
        dialog.setGraphic(login_img);
        dialog.getDialogPane().setPrefSize(420, 260);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) { //if the user choose a choice...
            account = result.get();
            
            Statement stmtch = FM2017.conn.createStatement();
            String sqlch =  "INSERT INTO prosklisi_en (Proskeklimenos, Aitisi ) "
                  + " VALUES ('"+ account +"', '" + OmadaOnoma + "' ) ";
            stmtch.executeUpdate(sqlch);
        }
    }
    // SEASON TICKET OFFER BUTTON
    @FXML
    private void handleSeasonTicketRenewal(ActionEvent event) throws SQLException {
        // LOGIN DIALOG BOX
        String account; //get the account name
        java.util.List<String> choices = new ArrayList<>();
                
        Statement stmtchtr = FM2017.conn.createStatement();
        String sqlchtr = " SELECT DISTINCT Katoxos_diarkeias.Onomateponumo, Omada.Onoma AS OnomaOmadas "
                       + " FROM  Katoxos_diarkeias, Filathlos, Omada, Proedros "
                       + " WHERE Katoxos_diarkeias.Onomateponumo=Filathlos.Onomateponumo AND Filathlos.Omada_Onoma=Omada.Onoma "
                       + " AND Proedros.Onomateponumo='" + FM2017.ChairmanName +"' AND Omada.Onoma=Proedros.Omada_Onoma ";
        ResultSet rschtr = stmtchtr.executeQuery(sqlchtr);

        String OmadaOnoma = null;
        
            while (rschtr.next()){
                        OmadaOnoma=rschtr.getString("OnomaOmadas");
                        choices.add(rschtr.getString("Onomateponumo"));
            }
                

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0),choices);
        dialog.setTitle("Ticket Renewal Menu");
        dialog.setHeaderText("Season Ticket Renewal");
        dialog.setContentText("Make a Renewal Offer to:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/ticketrenewal.png"));
        ImageView login_img = new ImageView();
        login_img.setImage(loginimg);
        login_img.setFitWidth(64);
        login_img.setFitHeight(64);
        login_img.setPreserveRatio(true);
        login_img.setSmooth(true);
        login_img.setCache(true);
        dialog.setGraphic(login_img);
        dialog.getDialogPane().setPrefSize(420, 260);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) { //if the user choose a choice...
            account = result.get();
            
            Statement stmtch = FM2017.conn.createStatement();
            String sqlch =  "INSERT INTO prosklisi_an (Proskeklimenos, Aitisi ) "
                  + " VALUES ('"+ account +"', '" + OmadaOnoma + "' ) ";
            stmtch.executeUpdate(sqlch);
        }
    }


    public void setEarnings(Integer numberoftickets, Integer earningsoftickets) {
        ObservableList<Earnings> TEdata = FXCollections.observableArrayList();
        TEdata.add(new Earnings(numberoftickets, earningsoftickets));
        ticketnumcol.setCellValueFactory(new PropertyValueFactory<Earnings,Integer>("Number"));
        earningscol.setCellValueFactory(new PropertyValueFactory<Earnings,Integer>("Earnings"));
        ticketnumcol.setStyle("-fx-alignment: CENTER;");
        earningscol.setStyle("-fx-alignment: CENTER;");
        earningstable.setItems(TEdata);
    }

    public void setTicketTypes(Integer numberofseasontickets, Integer numberofmatchdaytickets) {
        ObservableList<Ticket> TTdata = FXCollections.observableArrayList();
        TTdata.add(new Ticket(numberofseasontickets, numberofmatchdaytickets));
        seasoncol.setCellValueFactory(new PropertyValueFactory<Ticket,Integer>("Season"));
        matchdaycol.setCellValueFactory(new PropertyValueFactory<Ticket,Integer>("Matchday"));
        seasoncol.setStyle("-fx-alignment: CENTER;");
        matchdaycol.setStyle("-fx-alignment: CENTER;");
        tickettable.setItems(TTdata);
    }


    public static class Earnings {
        private final SimpleIntegerProperty Number;
        private final SimpleIntegerProperty Earnings;

        private Earnings(Integer number, Integer earnings) {
            this.Number = new SimpleIntegerProperty(number);
            this.Earnings = new SimpleIntegerProperty(earnings);
        }

        public Integer getNumber() {
            return Number.get();
        }
        public void setNumber(Integer number) { Number.set(number); }
        public Integer getEarnings() { return Earnings.get(); }
        public void setEarnings(Integer earnings) { Earnings.set(earnings); }

    }

    public static class Ticket {
        private final SimpleIntegerProperty Season;
        private final SimpleIntegerProperty Matchday;

        private Ticket(Integer season, Integer matchday) {
            this.Season = new SimpleIntegerProperty(season);
            this.Matchday = new SimpleIntegerProperty(matchday);
        }

        public Integer getSeason() {
            return Season.get();
        }
        public void setSeason(Integer season) { Season.set(season); }
        public Integer getMatchday() { return Matchday.get(); }
        public void setMatchday(Integer matchday) { Matchday.set(matchday); }

    }


}
