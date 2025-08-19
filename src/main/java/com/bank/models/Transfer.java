package com.bank.models;

public class Transfer {
    private String _fromAcc;
    private String _toAcc;
    private double _amount;
    private String _remarker;
    private String date;

    public Transfer(String _fromAcc, String _toAcc, double _amount, String _remarker, String date) {
        this._fromAcc = _fromAcc;
        this._toAcc = _toAcc;
        this._amount = _amount;
        this._remarker = _remarker;
        this.date = date;
    }

    public String get_fromAcc() {
        return _fromAcc;
    }

    public String get_toAcc() {
        return _toAcc;
    }

    public double get_amount() {
        return _amount;
    }

    public String get_remarker() {
        return _remarker;
    }

    public String getDate() {
        return date;
    }
}
