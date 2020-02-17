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
import javafx.scene.text.Text;
import obs.Observer;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChildController extends Observer {

    private Service service;
    String id;
    @FXML
    TableView expenditureTable;
    @FXML
    Text totalSum;
    @FXML
    ComboBox<String> expenditureType;
    @FXML
    TextField sum;
    @FXML
    TextArea expenditureAbout;
    ObservableList<Expenditure> expensesList = FXCollections.observableArrayList();

    public void setService(Service service, String id) {
        this.id = id;
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        expenditureTable.setItems(expensesList);
    }

    public void initModel() {
        List<Expenditure> exp = service.getExpenses();
        exp = exp.stream().filter(ex -> ex.getBy().equals(id)).collect(Collectors.toList());
        expensesList.setAll(exp);
        totalSum.setText("Suma totala disponibila este " + service.getMember(id).getSum());
        List<String> types = new ArrayList<>();
        for (ExpenditureType ex : ExpenditureType.values()) {
            types.add(ex.toString());
        }
        expenditureType.getItems().setAll(types);


    }

    @Override
    public void updateObservers() {
        initModel();
    }


    public void buy() {
        List<Integer> ids = service.getExpenses().stream().map(f->Integer.parseInt(f.getId())).sorted().collect(Collectors.toList());
        int lastExpense = ids.size()+1;
        try {
            String idE = Integer.toString(lastExpense);
            double sumE = Double.parseDouble(sum.getText());
            String descr = expenditureAbout.getText();
            ExpenditureType typeE = ExpenditureType.valueOf(expenditureType.getValue());
        service.addExpenditure(idE,id,sumE,descr,typeE);
        }catch(Exception e) {
            MessageBox.showErrorMessage(null, e.getMessage());
        }
    }
}
