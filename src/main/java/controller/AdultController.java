package controller;

import business.Service;
import domain.Expenditure;
import domain.ExpenditureType;
import domain.FamilyMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import obs.Observer;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdultController extends Observer {

    private Service service;
    @FXML
    TextField expenditureCost;
    @FXML
    TextArea expenditureAbout;
    @FXML
    TableView expenditureTable;
    @FXML
    TableView membersTable;
    @FXML
    ComboBox<String> expenditureType;
    @FXML
    ComboBox<String> memberCombo;
    @FXML TextField sumMember;
    ObservableList<FamilyMember> familyList = FXCollections.observableArrayList();
    ObservableList<Expenditure> expensesList = FXCollections.observableArrayList();

    public AdultController() {
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    public void initModel(){
        familyList.setAll(service.getFamilyMembers());
        expensesList.setAll(service.getExpenses());
        List<String> types = new ArrayList<>();
        for(ExpenditureType ex : ExpenditureType.values()){
            types.add(ex.toString());
        }
        expenditureType.getItems().setAll(types);
        memberCombo.getItems().setAll(service.getFamilyMembers().stream().map(f->f.getId()).collect(Collectors.toList()));
    }

    @FXML
    public void initialize(){
        expenditureTable.setItems(expensesList);
        membersTable.setItems(familyList);
    }

    @Override
    public void updateObservers() {
        initModel();
    }

    public void buy(){
        List<Integer> ids = service.getExpenses().stream().map(f->Integer.parseInt(f.getId())).sorted().collect(Collectors.toList());
        int lastExpense = ids.size()+1;
        try {
            String idE = Integer.toString(lastExpense);
            double sumE = Double.parseDouble(expenditureCost.getText());
            String descr = expenditureAbout.getText();
            ExpenditureType typeE = ExpenditureType.valueOf(expenditureType.getValue());
            service.addExpenditure(idE,memberCombo.getValue(),sumE,descr,typeE);
        }catch(Exception e) {
            MessageBox.showErrorMessage(null, e.getMessage());
        }

    }

    public void addMoney(){
        try{
            service.addVTF(Double.parseDouble(sumMember.getText()));
        }catch(Exception e) {
            MessageBox.showErrorMessage(null, e.getMessage());
        }
    }
}
