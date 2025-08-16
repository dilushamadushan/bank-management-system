package com.bank.services;

import com.bank.models.Account;
import com.bank.models.Transfer;
import java.sql.SQLException;

public class TransferManger {
    private AccountManager acm = new AccountManager();

    public void transfer(Transfer transfer,String username) throws SQLException {
        String _fromACC = transfer.get_fromAcc();
        String _toAcc = transfer.get_toAcc();

        Account fromAcc = acm.getAccountByUsername(username);
        Account toAcc = acm.checkTrasferAccount(_toAcc);

        if(_fromACC == null) throw new SQLException("Sender account not found.");
        if (_toAcc == null) throw new SQLException("Receiver account not found.");
        if(_fromACC.equals(_toAcc))throw new SQLException("Cannot transfer to the same account.");

        if (toAcc == null) {
            fromAcc.withdraw(transfer.get_amount() + 30);

            acm.updateAccountBalance(fromAcc);
            acm.saveTransaction(transfer);

        } else {
            fromAcc.withdraw(transfer.get_amount());
            toAcc.deposit(transfer.get_amount());

            acm.updateAccountBalance(fromAcc);
            acm.updateAccountBalance(toAcc);
            acm.saveTransaction(transfer);
        }

    }
}

