import business.Service;
import controller.AdultController;
import controller.ChildController;
import domain.FamilyMember;
import domain.MemberType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.ExpenditureInFileRepository;
import repository.FamilyMemberInFileRepository;

import java.io.IOException;

public class MainApp extends Application {
    private FamilyMemberInFileRepository fmRepo;
    private ExpenditureInFileRepository expRepo;
    private Service serv;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    //getParameters()getRaw() -> un array de stringuri
    public void start(Stage primaryStage) throws IOException {
        fmRepo = new FamilyMemberInFileRepository("D:\\Cheltuieli\\src\\main\\java\\files\\FamilyMembers.txt");
        expRepo = new ExpenditureInFileRepository("D:\\Cheltuieli\\src\\main\\java\\files\\Expenditure.txt");
        serv = new Service(fmRepo,expRepo);

        for(FamilyMember fm : fmRepo.findAll()){
            if(fm.getType().equals(MemberType.Adult))
                newAdultWindow(fm.getFirstName());
            else newChildWindow(fm.getFirstName(),fm.getId());
        }

    }

    public void newAdultWindow(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/adultFXML.fxml"));
        BorderPane layout = loader.load();

        Stage stage = new Stage();
        stage.setTitle(name);

        AdultController  controller = loader.getController();
        controller.setService(this.serv);
        stage.setScene(new Scene(layout));
        stage.show();
    }

    public void newChildWindow(String name,String id) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/childFXML.fxml"));
        BorderPane layout = loader.load();

        Stage stage = new Stage();
        stage.setTitle(name);

        ChildController controller = loader.getController();
        controller.setService(this.serv,id);
        stage.setScene(new Scene(layout));
        stage.show();
    }
}
