package domain;

public class FamilyMember extends Entity<String> {
    MemberType type;
    String lastName,firstName;
    double sum;

    public FamilyMember(String id,MemberType type, String lastName, String firstName, double sum) {
        this.setId(id);
        this.type = type;
        this.lastName = lastName;
        this.firstName = firstName;
        this.sum=sum;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
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
