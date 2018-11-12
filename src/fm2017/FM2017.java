package FM2017;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


public class FM2017 extends Application {

    static Connection conn = null;
    static String DB_IP = "127.0.0.1";
    static String DB_NAME = "FM2017";
    static String DB_USER = "root";
    static String DB_PASS = "toor";

    static String DB_EXPR = "jdbc:mysql://";
    static String DB_EXPR2 = "?verifyServerCertificate=false" //Certificate validation bypass
            + "&useSSL=true"; //SSL Enabled
    
    static String ManagerAddition = null;
    static String ChairmanName = null;

    //Get Screen WIDTH & HEIGHT from the display driver
    /*---------------------------------------------------------------
        static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        static int width = gd.getDisplayMode().getWidth();
        static int height = gd.getDisplayMode().getHeight();
      ---------------------------------------------------------------*/
    
    /* Fixed width/height values */
    static int width = 1366;//Main width
    static int height = 768;//Main height
    static int scene_width = width - 300;//Scene width
    static int scene_height = height - 100;//Scene height
    static int img_width = (scene_width/2);//Image width
    static int img_height = (scene_height/2) - 28;//Image height

    static GridPane homegrid;//main GridPane variable

    @Override
    public void start(Stage stage) throws Exception {
        
        StatusBar statusBar = new StatusBar();//Create a new StatusBar Object
        conn = connector(statusBar); //SQL Connection variable

        Image img1 = new Image(getClass().getResourceAsStream("img/supporter.jpg"));//Load the Image from a Stream
        ImageView iv1 = new ImageView();//Create an ImageView Object
        iv1.setPickOnBounds(true);//Allow click on transparent areas
        iv1.setImage(img1);//Set the specific Image in the ImageView Object
        iv1.setFitWidth(img_width);//Set the Object's Image width
        iv1.setFitHeight(img_height);//Set the Object's Image height
        iv1.setPreserveRatio(true);//Scale
        iv1.setSmooth(true);
        iv1.setCache(true);//Improve the performance
        iv1.setOnMouseEntered((MouseEvent t) -> {//When Mouse Enters the Image Area
            iv1.setImage(new Image(getClass().getResourceAsStream("img/supporter_hovered.jpg")));//Change Image
            statusBar.setText("Proceed as Supporter..");//StatusBar status update
        });
        iv1.setOnMouseExited((MouseEvent t2) -> {//When Mouse Exits the Image Area
            iv1.setImage(img1);//Restore the image to the main
            statusBar.setText(" ");//Empty the StatusBar text field
        });
        iv1.setOnMouseClicked((MouseEvent e) -> {//When mouse clicks the image
            try {
                if ( conn == null){//If there is no connection, show up an error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Connection Error");
                    alert.setHeaderText("You are not connected to a database yet..!");
                    alert.setContentText("More info at Settings/Database Configuration");
                    alert.showAndWait();
                } else { Supporter(stage, statusBar); }//Set stage to the Supporter screen
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Image img2 = new Image(getClass().getResourceAsStream("img/referee.jpg"));//Load the Image from a Stream
        ImageView iv2 = new ImageView();//Create an ImageView Object
        iv2.setPickOnBounds(true);//Allow click on transparent areas
        iv2.setImage(img2);//Set the specific Image in the ImageView Object
        iv2.setFitWidth(img_width);//Set the Object's Image width
        iv2.setFitHeight(img_height);//Set the Object's Image height
        iv2.setPreserveRatio(true);//Scale
        iv2.setSmooth(true);
        iv2.setCache(true);//Improve the performance
        iv2.setOnMouseEntered((MouseEvent t) -> {//When Mouse Enters the Image Area
            iv2.setImage(new Image(getClass().getResourceAsStream("img/referee_hovered.jpg")));//Change Image
            statusBar.setText("Proceed as Referee..");//StatusBar status update
        });
        iv2.setOnMouseExited((MouseEvent t) -> {//When Mouse Exits the Image Area
            iv2.setImage(img2);//Restore the image to the main
            statusBar.setText(" ");//Empty the StatusBar text field
        });
        iv2.setOnMouseClicked((MouseEvent e) -> {//When mouse clicks the image
            try {
                if ( conn == null){//If there is no connection, show up an error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Connection Error");
                    alert.setHeaderText("You are not connected to a database yet..!");
                    alert.setContentText("More info at Settings/Database Configuration");
                    alert.showAndWait();
                } else { Referee(stage,statusBar); }//Set stage to the Referee screen
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        Image img3 = new Image(getClass().getResourceAsStream("img/manager.jpg"));//Load the Image from a Stream
        ImageView iv3 = new ImageView();//Create an ImageView Object
        iv3.setPickOnBounds(true);//Allow click on transparent areas
        iv3.setImage(img3);//Set the specific Image in the ImageView Object
        iv3.setFitWidth(img_width);//Set the Object's Image width
        iv3.setFitHeight(img_height);//Set the Object's Image height
        iv3.setPreserveRatio(true);//Scale
        iv3.setSmooth(true);
        iv3.setCache(true);//Improve the performance
        iv3.setOnMouseEntered((MouseEvent t) -> {//When Mouse Enters the Image Area
            iv3.setImage(new Image(getClass().getResourceAsStream("img/manager_hovered.jpg")));//Change Image
            statusBar.setText("Proceed as Manager..");//StatusBar status update
        });
        iv3.setOnMouseExited((MouseEvent t2) -> {//When Mouse Exits the Image Area
            iv3.setImage(img3);//Restore the image to the main
            statusBar.setText(" ");//Empty the StatusBar text field
        });
        iv3.setOnMouseClicked((MouseEvent e) -> {//When mouse clicks the image
            try {
                if ( conn == null){//If there is no connection, show up an error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Connection Error");
                    alert.setHeaderText("You are not connected to a database yet..!");
                    alert.setContentText("More info at Settings/Database Configuration");
                    alert.showAndWait();
                } else { Manager(stage,statusBar); }//set stage to the Manager Screen
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        Image img4 = new Image(getClass().getResourceAsStream("img/chairman.jpg"));//Load the Image from a Stream
        ImageView iv4 = new ImageView();//Create an ImageView Object
        iv4.setPickOnBounds(true);//Allow click on transparent areas
        iv4.setImage(img4);//Set the specific Image in the ImageView Object
        iv4.setFitWidth(img_width);//Set the Object's Image width
        iv4.setFitHeight(img_height);//Set the Object's Image height
        iv4.setPreserveRatio(true);//Scale
        iv4.setSmooth(true);
        iv4.setCache(true);//Improve the performance
        iv4.setOnMouseEntered((MouseEvent t) -> {//When Mouse Enters the Image Area
            iv4.setImage(new Image(getClass().getResourceAsStream("img/chairman_hovered.jpg")));//Change Image
            statusBar.setText("Proceed as Chairman..");//StatusBar status update
        });
        iv4.setOnMouseExited((MouseEvent t2) -> {//When Mouse Exits the Image Area
            iv4.setImage(img4);//Restore the image to the main
            statusBar.setText(" ");//Empty the StatusBar text field
        });
        iv4.setOnMouseClicked((MouseEvent e) -> {//When mouse clicks the image
            try {
                if ( conn == null){//If there is no connection, show up an error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Connection Error");
                    alert.setHeaderText("You are not connected to a database yet..!");
                    alert.setContentText("More info at Settings/Database Configuration");
                    alert.showAndWait();
                } else { Chairman(stage,statusBar); }//set stage to the Chairman screen
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        GridPane gridPane = new GridPane();//Create the main GridPane
        GridPane.setHgrow(gridPane, Priority.ALWAYS);
        GridPane.setVgrow(gridPane, Priority.ALWAYS);
        gridPane.setAlignment(Pos.CENTER);//Homescreen CENTER Allignment
        gridPane.setHgap(5);//Horizontal gap
        gridPane.setVgap(5);//Vertical gap
        gridPane.add(iv1, 0, 0, 1, 1);//Supporter image allignment
        gridPane.add(iv2, 1, 0, 1, 1);//Referee image allignment
        gridPane.add(iv3, 0, 1, 1, 1);//Manager image allignment
        gridPane.add(iv4, 1, 1, 1, 1);//Chairman image allignment
        gridPane.setStyle("-fx-background-color: lightgrey;-fx-padding: 0px;");//CSS background color
        homegrid = gridPane; //Store the gridPane for the Back Button
        
        BorderPane root = new BorderPane();//Create a root node as BorderPane.
        /* Adding the menus as well as the content pane to the root node. */
        root.setTop(Menu(stage, statusBar));
        root.setCenter(gridPane);
        root.setBottom(statusBar);

        Scene menuscreen = new Scene(root, scene_width, scene_height);//Add root Borderpane to the scene with the fixed scene width and height
        stage.setTitle("FM2017");//Window title
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/FM2017.png")));//Window icon
        stage.setScene(menuscreen);//Set the current stage to menuscreen
        stage.sizeToScene();
        stage.setResizable(false);//Prevent user from resizing the window
        stage.show();//Show the stage

        /* Startup information popup */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account Type Selection");
        alert.setHeaderText("Please select your account type in order to proceed..");
        alert.setContentText(null);
        ButtonType buttonTypeGotcha = new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeGotcha);
        alert.showAndWait();
        statusBar.setText(" ");//Revert statusBar from CONNECTED/DISCONNECTED text
    }

    /*
        Function connector(StatusBar Object) initializes the connection with the
        MySQL server and stores it in a variable named conn, after
        that the function returns this variable. In the other hand if
        no connection is available, the function returns the var conn
        that has NULL value.
    */
    public Connection connector(StatusBar statusBar) {
        try {
            /* Initialize the connection to the MySQL server */
            conn = DriverManager.getConnection(DB_EXPR + DB_IP + "/" + DB_NAME + DB_EXPR2, DB_USER, DB_PASS);//Variable conn "has" the connection
            ImageView connected = new ImageView(new Image(getClass().getResourceAsStream("img/connected.png")));//Connected Icon
            connected.setFitWidth(23);
            connected.setFitHeight(23);
            connected.setPreserveRatio(true);
            connected.setSmooth(true);
            connected.setCache(true);//Improve the performance
            statusBar.getRightItems().setAll(connected);//Add to the right of the status bar the connected icon
            Tooltip.install(connected, new Tooltip("Connected\n[" + DB_NAME + "@" + DB_IP + "]"));//When hover through the icon it shows some info
            statusBar.setText(" Successfully connected to the Database..!");//Update the statusBar status text
        } catch (SQLException ex) { //Handle any errors(ex. not connected)
            ImageView disconnected = new ImageView(new Image(getClass().getResourceAsStream("img/disconnected.png")));
            disconnected.setFitWidth(23);
            disconnected.setFitHeight(23);
            disconnected.setPreserveRatio(true);
            disconnected.setSmooth(true);
            disconnected.setCache(true);//Improve the performance
            statusBar.getRightItems().setAll(disconnected);//Add to the right of the status bar the disconnected icon
            Tooltip.install(disconnected, new Tooltip("Disconnected"));//When hover through the icon it shows some info
            statusBar.setText(" Can't connect to the Database..!");//Update the statusBar status text
            /* Console Log */
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
    

    /*
        Supporter handler function
    */
    public void Supporter(Stage stage, StatusBar statusBar) throws Exception{

        BorderPane root = new BorderPane();//Create a root node as BorderPane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Supporter.fxml"));//Load the specific FXML
        Parent container = loader.load();
        SupporterController controller = loader.getController();

        java.util.List<String> choices = new ArrayList<>();//Login Dialog Box
        
        /*  
            Name & Surname SQL Query Fetching
        */
        Statement stmt = conn.createStatement();
        String sql = " SELECT Onomateponumo FROM Filathlos "
                   + " ORDER BY Filathlos.Onomateponumo";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            String fullname = rs.getString("Onomateponumo");//Retrieve by column name
            choices.add( fullname );//Add the fullname to the choices list
        }


        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);//Create the Dialog Box
        dialog.setTitle("Login Menu");
        dialog.setHeaderText("Supporter Login");
        dialog.setContentText("Login as:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/login.png"));
        ImageView login_img = new ImageView();
        login_img.setImage(loginimg);
        login_img.setFitWidth(64);
        login_img.setFitHeight(64);
        login_img.setPreserveRatio(true);//Scalling
        login_img.setSmooth(true);
        login_img.setCache(true);//Improve the perfomance
        dialog.setGraphic(login_img);
        dialog.getDialogPane().setPrefSize(420, 260);
        Optional<String> result = dialog.showAndWait();//Get the response value
        if (result.isPresent()){//User's choice
            String account = result.get();

            // PREVIOUS GAMES TABLE
            stmt = conn.createStatement();
            sql = " SELECT DISTINCT Agonas.ID_Agona, Agonas.Imerominia, Agonas.Skor_Gipedouxou, Agonas.Skor_Filoksenoumenou, Skorarei.Onomateponumo, Skorarei.Lepto "
                + "FROM (((Filathlos "
                + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                + "LEFT JOIN Skorarei ON Agonas.ID_Agona=Skorarei.ID_Agona) "
                + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Agonas.Imerominia<CURRENT_DATE ";
            rs = stmt.executeQuery(sql);

            /*
                Previous Game table handling
            */
            Integer currentID = 1;
            Integer oldID = 0;
            Integer count = 0;
            String Scorers = null;
            String Date = null;
            String HomeScored = null;
            String AwayScored = null;
            while (rs.next()){
                if (count==0){ oldID = rs.getInt("ID_Agona"); } //The first Match ID will be and the oldID variable content

                currentID = rs.getInt("ID_Agona");//Fetch the current match ID
                if ( Objects.equals(oldID, currentID))//If current match ID is the same as the old match ID
                {
                        String lepto = rs.getString("Lepto");
                        String onomata = rs.getString("Onomateponumo");
                        Date = rs.getString("Imerominia");
                        HomeScored = rs.getString("Skor_Gipedouxou");
                        AwayScored = rs.getString("Skor_Filoksenoumenou");
                        /*
                            Scorers text handling
                        */
                        if (Scorers == null) {
                            if (lepto == null && onomata == null) { Scorers = "-"; }
                            else { Scorers = lepto + " " +  onomata; }
                        }
                        else { Scorers = Scorers + " | " + lepto + " " +  onomata; }
                }
                else//If current match ID is different from old match ID
                {
                    Statement stmt2 = conn.createStatement();
                    /*
                        Not Seen Games Handling
                    
                    */
                    String sql2 = "SELECT DISTINCT Agonas.ID_Agona "
                        + "FROM Agonas "
                        + "WHERE Agonas.ID_Agona NOT IN(SELECT DISTINCT Agonas.ID_Agona "
                        + "FROM (((((((Filathlos "
                        + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                        + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                        + "LEFT JOIN Aplo ON Agonas.ID_Agona=Aplo.ID_Agona) "
                        + "LEFT JOIN antistoixei_diarkeias ON Agonas.ID_Agona=antistoixei_diarkeias.ID_Agona) "
                        + "LEFT JOIN Diarkeias ON Diarkeias.Kodikos=antistoixei_diarkeias.Diarkeias_Kodikos)"
                        + "LEFT JOIN Eisitirio ON (Eisitirio.Kodikos = Aplo.Kodikos OR Eisitirio.Kodikos = Diarkeias.Kodikos)) "
                        + "LEFT JOIN Agora ON Eisitirio.Kodikos = Agora.Eisitirio) "
                        + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Agora.Agorastis=Filathlos.Onomateponumo) ";
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    String Seen;
                    Seen = "Yes";
                    while (rs2.next()){ if (oldID == rs2.getInt("ID_Agona")) { Seen="No"; } }//If the ID is in the Query table of the not seen, set Seen as No
                    controller.setPrevious_Games(Seen,Date,Scorers,HomeScored+"-"+AwayScored);//After the handling add this Match to the Previous Games table
                    if (rs.getString("Lepto") != null && rs.getString("Onomateponumo") != null) Scorers = rs.getString("Lepto") + " " + rs.getString("Onomateponumo");
                    else { Scorers = "-"; }
                    Date = rs.getString("Imerominia");
                    HomeScored = rs.getString("Skor_Gipedouxou");
                    AwayScored = rs.getString("Skor_Filoksenoumenou");
                }
                oldID = currentID;
                count++;
            }
            if (Scorers != null)//If the loop ends and Scorers isn't null then handle the last Match
            {
                Statement stmt3 = conn.createStatement();
                String sql2 = "SELECT DISTINCT Agonas.ID_Agona "
                          + "FROM Agonas "
                          + "WHERE Agonas.ID_Agona NOT IN(SELECT DISTINCT Agonas.ID_Agona "
                          + "FROM (((((((Filathlos "
                          + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                          + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                          + "LEFT JOIN Aplo ON Agonas.ID_Agona=Aplo.ID_Agona) "
                          + "LEFT JOIN antistoixei_diarkeias ON Agonas.ID_Agona=antistoixei_diarkeias.ID_Agona) "
                          + "LEFT JOIN Diarkeias ON Diarkeias.Kodikos=antistoixei_diarkeias.Diarkeias_Kodikos)"
                          + "LEFT JOIN Eisitirio ON (Eisitirio.Kodikos = Aplo.Kodikos OR Eisitirio.Kodikos = Diarkeias.Kodikos)) "
                          + "LEFT JOIN Agora ON Eisitirio.Kodikos = Agora.Eisitirio) "
                          + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Agora.Agorastis=Filathlos.Onomateponumo) ";
                ResultSet rs2 = stmt3.executeQuery(sql2);
                String Seen="Yes";
                while (rs2.next()){ if (currentID == rs2.getInt("ID_Agona")){ Seen="No"; } }
                controller.setPrevious_Games(Seen,Date,Scorers,HomeScored+"-"+AwayScored);
            }

            

            /*
                Upcoming Game table handling
            */
            stmt = conn.createStatement();
            sql = " SELECT DISTINCT Agonas.ID_Agona, Agonas.Imerominia, Agonas.Ora, Agonas.Gip_Onoma, Agonas.diaititis, Agonas.epoptisA, Agonas.epoptisB, Agonas.tetartos_diaititis , Agonas.paratiritis "
                    + "FROM ((Filathlos "
                    + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                    + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                    + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Agonas.Imerominia>=CURRENT_DATE ";
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                controller.setUpcoming_Games(rs.getString("Imerominia"), rs.getString("Ora"), rs.getString("Gip_Onoma"), rs.getString("diaititis")+", "+rs.getString("epoptisA")+", "+rs.getString("epoptisB")+", "+rs.getString("tetartos_diaititis")+", "+rs.getString("paratiritis"));
            }
            
            
             /*
                Available Seats table handling
            */
            ResultSet rs2;
            String sql2;
            Statement stmt2 = conn.createStatement();
            
            
            sql2 = " SELECT Agonas.ID_Agona  "
                    + "FROM ((((((Katoxos_diarkeias "
                    + "LEFT JOIN Filathlos ON Katoxos_diarkeias.Onomateponumo=Filathlos.Onomateponumo) "
                    + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                    + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                    + "LEFT JOIN antistoixei_diarkeias ON Agonas.ID_Agona=antistoixei_diarkeias.ID_Agona) "
                    + "LEFT JOIN Diarkeias ON Diarkeias.Kodikos=antistoixei_diarkeias.Diarkeias_Kodikos) "
                    + "LEFT JOIN Eisitirio ON Eisitirio.Kodikos = Diarkeias.Kodikos) "
                    + "WHERE Katoxos_diarkeias.Onomateponumo='"+ account +"' AND Agonas.Imerominia>=CURRENT_DATE "
                    + "ORDER BY Agonas.Imerominia ";
            rs2 = stmt2.executeQuery(sql2);
            
   
            ResultSet rs22;
            String sql22;
            Statement stmt22 = conn.createStatement();
            
            sql22 = " SELECT Agonas.ID_Agona  "
                    + "FROM (((((Katoxos_aplo "
                    + "LEFT JOIN Filathlos ON Katoxos_aplo.Onomateponumo=Filathlos.Onomateponumo) "
                    + "LEFT JOIN Omada ON Filathlos.Omada_Onoma=Omada.Onoma) "
                    + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                    + "LEFT JOIN Aplo ON Agonas.ID_Agona=Aplo.ID_Agona) "
                    + "LEFT JOIN Eisitirio ON Eisitirio.Kodikos = Aplo.Kodikos) "
                    + "WHERE Katoxos_aplo.Onomateponumo='"+ account +"' AND Agonas.Imerominia>=CURRENT_DATE "
                    + "ORDER BY Agonas.Imerominia ";
            rs22 = stmt22.executeQuery(sql22);
            
            Boolean diarkeias = false; // Boolean var to check whether this account has full season ticket or matchday
            
            Integer id_agwna = 0;
            if (rs2.next()) { //if the account has season ticket
                diarkeias = true;
                id_agwna = rs2.getInt("ID_Agona");
            }
            else { //else he/she has match-day ticket
                diarkeias = false;
                rs22.next();
                id_agwna = rs22.getInt("ID_Agona");
             }
            
            if( diarkeias == false )
            {          

                ResultSet rs3;
                String sql3;
                Statement stmt3 = conn.createStatement();
                
                sql3 = " SELECT antistoixei_diarkeias.Diarkeias_Kodikos  "
                        + "FROM Agonas, antistoixei_diarkeias "
                        + "WHERE Agonas.ID_Agona=" + id_agwna +" AND antistoixei_diarkeias.ID_Agona=Agonas.ID_Agona ";
                rs3 = stmt3.executeQuery(sql3);

                ResultSet rs4;
                String sql4;
                Statement stmt4 = conn.createStatement();

                sql4 = " SELECT Gipedo.Xoritikotita  "
                        + "FROM Gipedo, Agonas "
                        + "WHERE Agonas.ID_Agona=" + id_agwna +" AND Agonas.Gip_Onoma=Gipedo.Onoma ";
                rs4 = stmt4.executeQuery(sql4);

                rs4.next();
                Integer xoritikotita = rs4.getInt("Xoritikotita");

                int[] seatstaken;
                seatstaken = new int[xoritikotita+1];
                while (rs3.next()){
                    controller.setSeats(rs3.getInt("Diarkeias_Kodikos"),"No");
                    seatstaken[rs3.getInt("Diarkeias_Kodikos")] = rs3.getInt("Diarkeias_Kodikos");    
                }
                for (int i=1;i<=xoritikotita;i++) { 
                    if(seatstaken[i] == 0) { controller.setSeats(i,"Yes"); }
                }
            } else { //if its a season ticket
                ResultSet rs3;
                String sql3;
                Statement stmt3 = conn.createStatement();

                sql3 = " SELECT Agora.Eisitirio  "
                        + "FROM Filathlos, Agora "
                        + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Filathlos.Onomateponumo=Agora.Agorastis ";
                rs3 = stmt3.executeQuery(sql3);
                
                rs3.next();
                controller.setSeats(rs3.getInt("Eisitirio"),"Reserved");
            }
            
            
             /*
                Offers handling
            */
            Boolean Enarksi = false;
            Boolean Ananeosi = false;
            
            ResultSet rsspe;
            String sqlspe;
            Statement stmtspe = conn.createStatement();

            sqlspe = " SELECT  prosklisi_en.Proskeklimenos "
                  + "FROM prosklisi_en, Filathlos "
                  + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Filathlos.Onomateponumo=prosklisi_en.Proskeklimenos ";
            rsspe = stmtspe.executeQuery(sqlspe);
                
                
            if( rsspe.next() ) { Enarksi = true; }
            
            ResultSet rsspa;
            String sqlspa;
            Statement stmtspa = conn.createStatement();

            sqlspa = " SELECT  prosklisi_an.Proskeklimenos "
                  + "FROM prosklisi_an, Filathlos "
                  + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Filathlos.Onomateponumo=prosklisi_an.Proskeklimenos ";
            rsspa = stmtspa.executeQuery(sqlspa);
                
                
            if( rsspa.next() ) { Ananeosi = true; }
            
            if (Enarksi == true || Ananeosi == true) { controller.setOffers("You have an Offer!"); } else { controller.setOffers("No Offer received.."); }
            
             /*
                Team GoalDifference handling
            */
            ResultSet rs3;
            String sql3;
            Statement stmt3 = conn.createStatement();
            
            sql3 = " SELECT Omada.termata_simeiose, Omada.termata_dextike  "
                        + "FROM Omada, Filathlos "
                        + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Filathlos.Omada_Onoma=Omada.Onoma ";

            rs3 = stmt2.executeQuery(sql3);   
            rs3.next();
            Integer goals = rs3.getInt("termata_simeiose");
            Integer conceded = rs3.getInt("termata_dextike");
            controller.setTeamGD(goals-conceded);


             /*
                Supporter's Favourite Player handling
            */
            ResultSet rs4;
            String sql4;
            Statement stmt4 = conn.createStatement();
            
            sql4 = " SELECT Paiktis.termata_simeiose, Paiktis.Onomateponumo  "
                        + "FROM Filathlos, ypostiriksi, Paiktis "
                        + "WHERE Filathlos.Onomateponumo='"+ account +"' AND Filathlos.Onomateponumo=ypostiriksi.Ypostiriktis AND ypostiriksi.Paiktis=Paiktis.onomateponumo ";

            rs4 = stmt4.executeQuery(sql4);  
            
            while(rs4.next()){ controller.setFavourite_Player(rs4.getInt("termata_simeiose"), rs4.getString("Onomateponumo")); }


            root.setTop(Menu(stage, statusBar)); //set menuBar on top
            root.setBottom(statusBar); //set statusBar on Bottom
            String bg = getClass().getResource("img/supporter_background.jpg").toExternalForm();
            container.setStyle("-fx-background-image: url('" + bg + "');"); //set scene background
            root.setCenter(container); //set container at center
            Scene supporterscreen = new Scene(root, scene_width, scene_height);
            stage.setScene(supporterscreen); //Set scene to Supporter
            if (conn != null ) statusBar.setText(" Connected to " + DB_NAME + "@" + DB_IP);
        }
    }

    public void Referee(Stage stage, StatusBar statusBar) throws Exception{
        BorderPane root = new BorderPane(); /* Create a root node as BorderPane. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Referee.fxml"));
        Parent container = loader.load();
        RefereeController controller = loader.getController();

        // LOGIN DIALOG BOX
        java.util.List<String> choices = new ArrayList<>();
        // SQL QUERY FETCH
        Statement stmt = conn.createStatement();
        String sql = " SELECT Onomateponumo FROM Diaititis ";//" SELECT Onomateponumo FROM Filathlos ";//" SELECT Onomateponumo FROM Diaititis ";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            //Retrieve by column name
            String fullname = rs.getString("Onomateponumo");//rs.getString("Onomateponumo");
            choices.add( fullname );
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Login Menu");
        dialog.setHeaderText("Referee Login");
        dialog.setContentText("Login as:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/login.png"));
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
        if (result.isPresent()){ //if the user choose a choice...
            String account = result.get();
            
            /*
                Referee set handling
            */
            Statement stmtref = conn.createStatement();
            String sqlref =  " SELECT Agonas.ID_Agona, Agonas.Imerominia, Agonas.Gip_Onoma, Agonas.Ora, Agonas.Onoma_Gipedouxou, Agonas.Onoma_Filoksenoumenou  "
                    + "FROM (Diaititis "
                    + "LEFT JOIN Agonas ON (Agonas.epoptisA=Diaititis.Onomateponumo OR Agonas.epoptisB=Diaititis.Onomateponumo OR Agonas.tetartos_diaititis=Diaititis.Onomateponumo OR Agonas.diaititis=Diaititis.Onomateponumo OR Agonas.tetartos_diaititis=Diaititis.Onomateponumo OR Agonas.paratiritis=Diaititis.Onomateponumo)) "
                    + "WHERE Diaititis.Onomateponumo='"+ account +"' AND Agonas.Imerominia>=CURRENT_DATE "
                    + "ORDER BY Agonas.Imerominia ";
            ResultSet rsref = stmtref.executeQuery(sqlref);
            

            while (rsref.next()){
                Integer id_agwna = rsref.getInt("ID_Agona");
                String RefType = null;
                
                Statement stmtref21 = conn.createStatement();
                String sqlref21 =  " SELECT Agonas.ID_Agona  "
                    + "FROM (Diaititis_epoptis "
                    + "LEFT JOIN Agonas ON (Agonas.epoptisA=Diaititis_epoptis.Onomateponumo OR Agonas.epoptisB=Diaititis_epoptis.Onomateponumo)) "
                    + "WHERE Diaititis_epoptis.Onomateponumo='"+ account +"' AND Agonas.ID_Agona="+ id_agwna +" "
                    + "ORDER BY Agonas.Imerominia ";
                ResultSet rsref21 = stmtref21.executeQuery(sqlref21);
                if(rsref21.next()) {
                        RefType="Linesman";
                }
                
                Statement stmtref22 = conn.createStatement();
                String sqlref22 =  " SELECT Agonas.diaititis  "
                    + "FROM (Diaititis_diaititis "
                    + "LEFT JOIN Agonas ON Agonas.diaititis=Diaititis_diaititis.Onomateponumo) "
                    + "WHERE Diaititis_diaititis.Onomateponumo='"+ account +"' AND Agonas.ID_Agona="+ id_agwna +" "
                    + "ORDER BY Agonas.Imerominia ";
                ResultSet rsref22 = stmtref22.executeQuery(sqlref22);
                if(rsref22.next()) {
                        RefType="Referee";
                }
                
                Statement stmtref23 = conn.createStatement();
                String sqlref23 =  " SELECT Agonas.tetartos_diaititis  "
                    + "FROM (Diaititis_tetartos "
                    + "LEFT JOIN Agonas ON Agonas.tetartos_diaititis=Diaititis_tetartos.Onomateponumo) "
                    + "WHERE Diaititis_tetartos.Onomateponumo='"+ account +"' AND Agonas.ID_Agona="+ id_agwna +" "
                    + "ORDER BY Agonas.Imerominia ";
                ResultSet rsref23 = stmtref23.executeQuery(sqlref23);
                if(rsref23.next()) {
                        RefType="Replacement";
                }
                
                Statement stmtref24 = conn.createStatement();
                String sqlref24 =  " SELECT Agonas.paratiritis  "
                    + "FROM (Diaititis_paratiritis "
                    + "LEFT JOIN Agonas ON Agonas.paratiritis=Diaititis_paratiritis.Onomateponumo) "
                    + "WHERE Diaititis_paratiritis.Onomateponumo='"+ account +"' AND Agonas.ID_Agona="+ id_agwna +" "
                    + "ORDER BY Agonas.Imerominia ";
                ResultSet rsref24 = stmtref24.executeQuery(sqlref24);
                if(rsref24.next()) {
                        RefType="Assistant";
                }
                
                controller.setUpcoming_Games(RefType, rsref.getString("Imerominia")+" - "+rsref.getString("Ora"), rsref.getString("Gip_Onoma"), rsref.getString("Onoma_Gipedouxou")+"-"+rsref.getString("Onoma_Filoksenoumenou"));

            }

            root.setTop(Menu(stage, statusBar)); //set menuBar on top
            root.setBottom(statusBar); //set statusBar on Bottom
            String bg = getClass().getResource("img/referee_background.jpg").toExternalForm();
            container.setStyle("-fx-background-image: url('" + bg + "');"); //set scene background
            root.setCenter(container); //set container at center
            Scene supporterscreen = new Scene(root, scene_width, scene_height);
            stage.setScene(supporterscreen); //Set scene to Supporter
            if (conn != null ) statusBar.setText(" Connected to " + DB_NAME + "@" + DB_IP);
        }
    }

    public void Manager(Stage stage, StatusBar statusBar) throws Exception{
        BorderPane root = new BorderPane(); /* Create a root node as BorderPane. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager.fxml"));
        Parent container = loader.load();
        ManagerController controller = loader.getController();

        // LOGIN DIALOG BOX
        java.util.List<String> choices = new ArrayList<>();
        // SQL QUERY FETCH
        Statement stmt = conn.createStatement();
        String sql = " SELECT Onomateponumo FROM Proponitis ";//" SELECT Onomateponumo FROM Filathlos ";//" SELECT Onomateponumo FROM Proponitis ";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            //Retrieve by column name
            String fullname = rs.getString("Onomateponumo");//rs.getString("Onomateponumo");
            choices.add( fullname );
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Login Menu");
        dialog.setHeaderText("Manager Login");
        dialog.setContentText("Login as:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/login.png"));
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
        if (result.isPresent()){ //if the user choose a choice...
            String account = result.get();
            
            Statement stmtman = conn.createStatement();
            String sqlman =  " SELECT DISTINCT Omada.Onoma, Agonas.Imerominia, Agonas.Ora, Agonas.Gip_Onoma, Agonas.Onoma_Gipedouxou, Agonas.Onoma_Filoksenoumenou "
                    + "FROM (Omada "
                    + "LEFT JOIN Agonas ON (Omada.Onoma=Agonas.Onoma_Gipedouxou OR Omada.Onoma=Agonas.Onoma_Filoksenoumenou)) "
                    + "WHERE Omada.Propon_Onoma='"+ account +"' AND Agonas.Imerominia>=CURRENT_DATE "
                    + "ORDER BY Agonas.Imerominia ";
            ResultSet rsman = stmtman.executeQuery(sqlman);
            
            while (rsman.next()){
                String teamname = rsman.getString("Onoma");
                ManagerAddition = teamname;
                String opponentname = null;
                
                if( teamname.equals(rsman.getString("Onoma_Gipedouxou")) )
                { 
                    opponentname=rsman.getString("Onoma_Filoksenoumenou");
                }
                else { opponentname=rsman.getString("Onoma_Gipedouxou"); }
                
                controller.setUpcoming_Games(rsman.getString("Imerominia")+" - "+rsman.getString("Ora"), rsman.getString("Gip_Onoma"), opponentname);

            }
            
            
            Statement stmtman2 = conn.createStatement();
            String sqlman2 =  " SELECT DISTINCT Omada.termata_simeiose, Omada.termata_dextike "
                    + " FROM Omada "
                    + " WHERE Omada.Propon_Onoma='"+ account +"' ";
            ResultSet rsman2 = stmtman.executeQuery(sqlman2);
            
            while (rsman2.next()){
               
                controller.setTeamStats( rsman2.getInt("termata_simeiose"), rsman2.getInt("termata_dextike"), rsman2.getInt("termata_simeiose")-rsman2.getInt("termata_dextike"));

            }
            
            Statement stmtman3 = conn.createStatement();
            String sqlman3 =  " SELECT DISTINCT Paiktis.Onomateponumo "
                    + " FROM Omada, Paiktis "
                    + " WHERE Omada.Propon_Onoma='"+ account +"' AND Paiktis.Onoma_Omadas=Omada.Onoma ";
            ResultSet rsman3 = stmtman3.executeQuery(sqlman3);
            
            while (rsman3.next()){
               
                controller.setSquadPlayer(rsman3.getString("Onomateponumo"));

            }
            
            Statement stmtman3count = conn.createStatement();
            String sqlman3count =  " SELECT COUNT(Paiktis.Onomateponumo) AS count "
                    + " FROM Omada, Paiktis "
                    + " WHERE Omada.Propon_Onoma='"+ account +"' AND Paiktis.Onoma_Omadas=Omada.Onoma ";
            ResultSet rsman3count = stmtman3count.executeQuery(sqlman3count);
            rsman3count.next();
                       
            controller.setSquadSize(rsman3count.getInt("count"));



            root.setTop(Menu(stage, statusBar)); //set menuBar on top
            root.setCenter(container); //set container at center
            String bg = getClass().getResource("img/manager_background.jpg").toExternalForm();
            container.setStyle("-fx-background-image: url('" + bg + "');");
            root.setBottom(statusBar); //set statusBar on Bottom
            Scene supporterscreen = new Scene(root, scene_width, scene_height);
            stage.setScene(supporterscreen); //Set scene to Supporter
            if (conn != null ) statusBar.setText(" Connected to " + DB_NAME + "@" + DB_IP);
        }
    }

    public void Chairman(Stage stage, StatusBar statusBar) throws Exception{
        BorderPane root = new BorderPane(); /* Create a root node as BorderPane. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chairman.fxml"));
        Parent container = loader.load();
        ChairmanController controller = loader.getController();


        // LOGIN DIALOG BOX
        java.util.List<String> choices = new ArrayList<>();
        // SQL QUERY FETCH
        Statement stmt = conn.createStatement();
        String sql = " SELECT Onomateponumo FROM Proedros ";//" SELECT Onomateponumo FROM Filathlos ";//" SELECT Onomateponumo FROM Proedros ";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            //Retrieve by column name
            String fullname = rs.getString("Onomateponumo");//rs.getString("Onomateponumo");
            choices.add( fullname );
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Login Menu");
        dialog.setHeaderText("Chairman Login");
        dialog.setContentText("Login as:");

        Image loginimg = new Image(getClass().getResourceAsStream("img/login.png"));
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
        if (result.isPresent()){ //if the user choose a choice...
            String account = result.get();
            ChairmanName = account;

            Statement stmtchair = conn.createStatement();
            String sqlchair = " SELECT COUNT( DISTINCT Diarkeias.Kodikos) AS diarkeiascount , COUNT(DISTINCT Aplo.Kodikos) AS aplocount "
                    + "FROM (((((Proedros "
                    + "LEFT JOIN Omada ON Omada.Onoma=Proedros.Omada_Onoma) "
                    + "LEFT JOIN Agonas ON Omada.Onoma=Agonas.Onoma_Gipedouxou) "
                    + "LEFT JOIN Aplo ON Agonas.ID_Agona=Aplo.ID_Agona) "
                    + "LEFT JOIN antistoixei_diarkeias ON Agonas.ID_Agona=antistoixei_diarkeias.ID_Agona) "
                    + "LEFT JOIN Diarkeias ON Diarkeias.Kodikos=antistoixei_diarkeias.Diarkeias_Kodikos)"
                    + "WHERE Proedros.Onomateponumo='"+ account +"' AND Agonas.Imerominia<CURRENT_DATE ";
            ResultSet rschair = stmtchair.executeQuery(sqlchair);

            int totalcount = 0;
            int diarkeiascount = 0;
            int aplocount = 0;
            
            rschair.next();
            diarkeiascount = rschair.getInt("diarkeiascount");//count of the season tickets
            aplocount = rschair.getInt("aplocount");//count of the match-day tickets
            totalcount = diarkeiascount + aplocount;//total count of the tickets
                
            controller.setEarnings(totalcount,(200*diarkeiascount)+(10*aplocount));
            controller.setTicketTypes(diarkeiascount, aplocount);



            root.setTop(Menu(stage, statusBar)); //set menuBar on top
            root.setCenter(container); //set container at center
            String bg = getClass().getResource("img/chairman_background.jpg").toExternalForm();
            container.setStyle("-fx-background-image: url('" + bg + "');");
            root.setBottom(statusBar); //set statusBar on Bottom
            Scene supporterscreen = new Scene(root, scene_width, scene_height);
            stage.setScene(supporterscreen); //Set scene to Supporter
            if (conn != null ) statusBar.setText(" Connected to " + DB_NAME + "@" + DB_IP);
        }
    }

    public HBox Menu(Stage stage, StatusBar statusBar)
    {

        MenuBar leftBar = new MenuBar();
        leftBar.prefWidthProperty().bind(stage.widthProperty());
        Menu settings = new Menu("Settings");
        MenuItem db = new MenuItem("Database Configuration");
        settings.getItems().addAll(db);
        db.setOnAction((ActionEvent event) -> {
            Dialog<DBconfig> dialog = new Dialog<>();
            dialog.setTitle("Database Configuration");
            dialog.setHeaderText("Database details");
            
            Image dbimg = new Image(getClass().getResourceAsStream("img/db.png"));
            ImageView db_img = new ImageView();
            db_img.setImage(dbimg);
            db_img.setFitWidth(64);
            db_img.setFitHeight(64);
            db_img.setPreserveRatio(true);
            db_img.setSmooth(true);
            db_img.setCache(true);
            dialog.setGraphic(db_img);
            
            // Set the button types.
            ButtonType cancelButtonType = new ButtonType("Close",ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType loginButtonType;
            if (conn == null ) loginButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OTHER);
            else loginButtonType = new ButtonType("Disconnect", ButtonBar.ButtonData.OTHER);
            
            dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType,loginButtonType);
            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField ip = new TextField();
            if ( DB_IP != null ) ip.setText(DB_IP);
            else ip.setPromptText("ip address");
            TextField name = new TextField();
            if ( DB_NAME != null ) name.setText(DB_NAME);
            else name.setPromptText("name");
            TextField username = new TextField();
            if ( DB_USER != null ) username.setText(DB_USER);
            else username.setPromptText("username");
            PasswordField password = new PasswordField();
            if ( DB_PASS != null ) password.setText(DB_PASS);
            else password.setPromptText("password");
            
            grid.add(new Label("DB IP:"), 0, 0);
            grid.add(ip, 1, 0);
            grid.add(new Label("DB Name:"), 0, 1);
            grid.add(name, 1, 1);
            grid.add(new Label("DB Username:"), 0, 2);
            grid.add(username, 1, 2);
            grid.add(new Label("DB Password:"), 0, 3);
            grid.add(password, 1, 3);
            
            dialog.getDialogPane().setContent(grid);
            
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(ip.textProperty(),
                            name.textProperty(),
                            username.textProperty());
                }
                
                @Override
                protected boolean computeValue() {
                    return (ip.getText().isEmpty()
                            || name.getText().isEmpty()
                            || username.getText().isEmpty()); //DB without password can be possible!
                }
            };
            
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.disableProperty().bind(bb); //disable button when inputs are empty
            
            // Request focus on the ip field by default.
            Platform.runLater(() -> ip.requestFocus());
            
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    if ("Connect".equals(loginButtonType.getText()))
                    {
                        DB_IP = ip.getText();
                        DB_NAME = name.getText();
                        DB_USER = username.getText();
                        DB_PASS = password.getText();
                        conn = connector(statusBar);
                        if ( conn == null){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Database Connection Error");
                            alert.setHeaderText("The connection was unsuccessful!");
                            alert.setContentText("Make sure the database details are correct!\nMore info at Settings/Database Configuration");
                            alert.showAndWait();
                        }
                    }
                    else {
                        conn = null;
                        ImageView disconnected = new ImageView(new Image(getClass().getResourceAsStream("img/disconnected.png")));
                        disconnected.setFitWidth(23);
                        disconnected.setFitHeight(23);
                        disconnected.setPreserveRatio(true);
                        disconnected.setSmooth(true);
                        disconnected.setCache(true);
                        statusBar.getRightItems().setAll(disconnected);
                        statusBar.setText(" Disconnected..!");
                        Tooltip.install(disconnected, new Tooltip("Disconnected"));
                    }
                    return new DBconfig(ip.getText(), name.getText(), username.getText(), password.getText());
                }
                return null;
            });
            
            if ( conn != null )
            {
                ip.setDisable(true);
                name.setDisable(true);
                username.setDisable(true);
                password.setDisable(true);
            }
            dialog.showAndWait();
        });

        Menu help = new Menu("Help");
        MenuItem about = new MenuItem("About");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        help.getItems().addAll(about,new SeparatorMenuItem(),exitMenuItem);
        about.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //dialog box
            alert.setTitle("About");
            alert.setHeaderText("\tCreators");
            FlowPane fp = new FlowPane();
            Label lbl1 = new Label("\t\n\n\tEmmanouil Katefidis  ~ ");
            Hyperlink link1 = new Hyperlink("\n\nwww.katefidis.ga");
            Label lbl2 = new Label("\t\n\tPanagiotis Stavrinakis  ~ ");
            Hyperlink link2 = new Hyperlink("\nstavrinakis@ceid.upatras.gr");
            fp.getChildren().addAll( lbl1, link1, lbl2, link2);
            
            Image infoimg = new Image(getClass().getResourceAsStream("img/info.png"));
            ImageView info_img = new ImageView();
            info_img.setImage(infoimg);
            info_img.setFitWidth(100);
            info_img.setFitHeight(100);
            info_img.setPreserveRatio(true);
            info_img.setSmooth(true);
            info_img.setCache(true);
            alert.setGraphic(info_img);
            
            link1.setOnAction( (evt) -> {
                String url = "https://www.katefidis.ga";
                if(Desktop.isDesktopSupported()){
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }else{
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec("xdg-open " + url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } );
            link2.setOnAction( (evt) -> {
                String url2 = "mailto:stavrinakis@ceid.upatras.gr";
                if(Desktop.isDesktopSupported()){
                    Desktop desktop2 = Desktop.getDesktop();
                    try {
                        desktop2.browse(new URI(url2));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }else{
                    Runtime runtime2 = Runtime.getRuntime();
                    try {
                        runtime2.exec("xdg-open " + url2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } );
            
            alert.getDialogPane().contentProperty().set( fp );
            alert.showAndWait();
        });

        MenuBar rightBar = new MenuBar();
        Label menulbl = new Label();
        Image backimg = new Image(getClass().getResourceAsStream("img/back.png"));
        ImageView back_img = new ImageView();
        back_img.setImage(backimg);
        back_img.setFitWidth(16);
        back_img.setFitHeight(16);
        back_img.setPreserveRatio(true);
        back_img.setSmooth(true);
        back_img.setCache(true);
        menulbl.setGraphic(back_img);
        menulbl.setOnMouseClicked((MouseEvent event) -> {
            BorderPane root = new BorderPane();
            /* Adding the menus as well as the content pane to the root node. */
            root.setTop(Menu(stage, statusBar));
            root.setCenter(homegrid);
            root.setBottom(statusBar);
            Scene menuscreen = new Scene(root, scene_width, scene_height);
            stage.setTitle("FM2017");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/FM2017.png")));
            stage.setScene(menuscreen);
            stage.sizeToScene();
            stage.setResizable(false); //prevent user from resizing the window
            /* Lift the curtain :0). */
            stage.show();
        });
        Region spacer = new Region();
        Menu back = new Menu();
        back.setGraphic(menulbl);
        leftBar.getMenus().addAll(settings,help);
        rightBar.getMenus().addAll(back);
        spacer.getStyleClass().add("menu-bar");
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menuBar = new HBox(leftBar, spacer, rightBar);

        return menuBar;
    }

    private boolean equals(int i, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class DBconfig {

        String ip;
        String name;
        String username;
        String password;

        public DBconfig(String ip, String name, String username, String password) {
            this.ip = ip;
            this.name = name;
            this.username = username;
            this.password = password;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}