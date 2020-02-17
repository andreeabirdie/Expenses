package repository;

import domain.Expenditure;
import domain.ExpenditureType;
import validator.ExpenditureValidator;
import validator.ValidationException;
import validator.Validator;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;


public class ExpenditureInFileRepository extends AbstractRepository<String, Expenditure> {
    String fileName;
    public Validator<Expenditure> vali;

    public ExpenditureInFileRepository(String fileName) {
        this.fileName = fileName;
        m = new HashMap<>();
        vali = new ExpenditureValidator();
        this.loadFromFile();
    }

    public void addExpenditure(Expenditure exp){
        if(exp==null)
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        vali.validate(exp);
        m.putIfAbsent(exp.getId(),exp);
        this.writeToFile();
    }

    public Iterable<Expenditure> findAll(){
        return m.values();
    }

    private void loadFromFile() {
        try {
            BufferedReader f = new BufferedReader(new FileReader(fileName));
            String line = f.readLine();
            while (line != null) {
                String[] args = line.split(",");
                if (args.length != 6)
                    continue;
                Expenditure exp = new Expenditure(args[0], ExpenditureType.valueOf(args[1]),
                        Double.parseDouble(args[2]), LocalDate.parse(args[3]), args[4],args[5]);
                m.put(args[0], exp);
                line = f.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))){
        findAll().forEach(exp -> {
            String result = exp.getId()+","+exp.getType().toString()+","+((Double)exp.getCost()).toString()+","+exp.getDate().toString()+","+exp.getBy()+","+exp.getAbout();
            try {
                writer.write(result);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}