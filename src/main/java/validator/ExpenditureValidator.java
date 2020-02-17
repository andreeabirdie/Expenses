package validator;

import domain.Expenditure;

public class ExpenditureValidator implements Validator<Expenditure>{

    @Override
    public void validate(Expenditure entity) throws ValidationException {
        String errors= "";
        if(entity.getId().compareTo("")==0) {
            errors += "Id nu poate fi vid\n";
        }
        if(entity.getAbout().compareTo("")==0) {
            errors += "Id nu poate fi vid\n";
        }
        if(entity.getBy().compareTo("")==0) {
            errors += "Id nu poate fi vid\n";
        }

        if(errors!="") throw new ValidationException(errors);
    }
}
