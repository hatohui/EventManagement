package controller.loan;

import models.Loan;

public interface ILoanBank {

    void add(Loan loan);

    void update(String id, Loan data);

    void delete(String id);

    void parseFromString(String str);

    boolean have(String id);

    void commit();
}
