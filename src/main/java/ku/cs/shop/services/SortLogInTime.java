package ku.cs.shop.services;

import ku.cs.shop.models.Account;

import java.util.Comparator;

public class SortLogInTime implements Comparator<Account> {
    @Override
    public int compare(Account o1, Account o2) {
        if (o1.getTime().isBefore(o2.getTime())){
            return 1;
        }
        return -1;
    }
}
