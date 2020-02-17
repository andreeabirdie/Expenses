package repository;

import domain.Expenditure;
import domain.ExpenditureType;
import domain.FamilyMember;
import domain.MemberType;

import java.io.*;
import java.util.HashMap;

public class FamilyMemberInFileRepository extends AbstractRepository<String, FamilyMember> {
    String fileName;

    public FamilyMemberInFileRepository(String fileName) {
        this.fileName = fileName;
        m=new HashMap<>();
        this.loadFromFile();
    }

    public Iterable<FamilyMember> findAll(){
       return m.values();
    }

    public FamilyMember findOne(String id){
        if(id==null) {
            throw new IllegalArgumentException();
        }
        return m.get(id);
    }

    public void modifySums(){
     this.writeToFile();
    }

    public int getNumberOfChildren(){
        int nr=0;
        for(FamilyMember fm : findAll()) {
            if (fm.getType().equals(MemberType.Child))
                nr++;
        }
        return nr;
    }

    private void loadFromFile(){
        try {
            BufferedReader f = new BufferedReader(new FileReader(fileName));
            String line = f.readLine();
            while (line != null) {
                String[] args = line.split(",");
                if (args.length != 5)
                    continue;
                FamilyMember fm = new FamilyMember(args[0], MemberType.valueOf(args[1]),args[2],args[3],Double.parseDouble(args[4]));
                m.put(args[0], fm);
                line= f.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            findAll().forEach(fm -> {
                String result = fm.getId() + "," + fm.getType().toString() + "," + fm.getLastName() + "," + fm.getFirstName() + "," + fm.getSum();
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
