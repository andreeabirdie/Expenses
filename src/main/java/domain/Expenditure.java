package domain;

import java.time.LocalDate;

public class Expenditure extends Entity<String>{
    ExpenditureType type;
    String about;
    double cost;
    LocalDate date;
    String by;

    public Expenditure(String id,ExpenditureType type, double cost, LocalDate date, String by,String about) {
        this.setId(id);
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.by = by;
        this.about=about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ExpenditureType getType() {
        return type;
    }

    public void setType(ExpenditureType type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
