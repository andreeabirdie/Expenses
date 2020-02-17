package business;

import domain.Expenditure;
import domain.ExpenditureType;
import domain.FamilyMember;
import domain.MemberType;
import obs.Observable;
import obs.Observer;
import repository.ExpenditureInFileRepository;
import repository.FamilyMemberInFileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service extends Observable {
    List<Observer> observers;
    FamilyMemberInFileRepository fmRepo;
    ExpenditureInFileRepository expRepo;

    public Service(FamilyMemberInFileRepository fmRepo, ExpenditureInFileRepository expRepo) {
        observers = new ArrayList<>();
        this.fmRepo = fmRepo;
        this.expRepo = expRepo;
    }

    public FamilyMember getMember(String id){
        return fmRepo.findOne(id);
    }
    public List<FamilyMember> getFamilyMembers(){
        List<FamilyMember> fam = new ArrayList<>();
        for(FamilyMember fm : fmRepo.findAll()){
            fam.add(fm);
        }
        return fam;
    }

    public List<Expenditure> getExpenses(){
        List<Expenditure> exp = new ArrayList<>();
        for(Expenditure ex: expRepo.findAll()){
            exp.add(ex);
        }
        return exp;
    }

    public void addVTF(double sum){
        double adultsSum = 9*sum/10;
        double nrChildren = fmRepo.getNumberOfChildren();
        double childSum = (sum/10)/nrChildren;
        for(FamilyMember fm : fmRepo.findAll()){
            if(fm.getType().equals(MemberType.Adult))
                fm.setSum(fm.getSum()+adultsSum);
            else fm.setSum(fm.getSum()+childSum);
        }
        fmRepo.modifySums();
        notifyObservers();
    }

    public void addExpenditure(String idE,String idFM, double cost, String about, ExpenditureType type){
        Expenditure exp = new Expenditure(idE,type,cost, LocalDate.now(),idFM,about);
        expRepo.addExpenditure(exp);
        FamilyMember fm = fmRepo.findOne(idFM);
        if(fm.getSum()<cost)
            throw new IllegalArgumentException("Nu aveti suma necesara ca sa faceti aceasta cheltuiala!");
        if(fm.getType().equals(MemberType.Child)){
            fm.setSum(fm.getSum()-cost);
        }
        else {
            for(FamilyMember fam : fmRepo.findAll())
                if(fam.getType().equals(MemberType.Adult))
                    fam.setSum(fam.getSum()-cost);
        }
        fmRepo.modifySums();
        notifyObservers();
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void deleteObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.updateObservers();
        }
    }
}
