package com.company;


import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transaction {

    String name;
    Double receipt;
    Double expense;

    public Transaction(String name, Double receipt, Double expense) {
        this.name = name;
        this.receipt = receipt;
        this.expense = expense;
    }


    public static ArrayList<Transaction> getTransactions(String pathToTheFile) throws Exception {
        ArrayList<Transaction> transactions = new ArrayList<>();
        FileReader filereader = new FileReader(pathToTheFile);
        CSVReader csvReader = new CSVReader(filereader, ',', '"', '\'', 1);
        List<String[]> list = csvReader.readAll();
        for (String[] row : list) {
            transactions.add(getTransactionFromString(row[5], row[6], row[7]));
        }
        return transactions;
    }

    private static Transaction getTransactionFromString(String str, String receipt, String expense) {

        Pattern p = Pattern.compile("[\\\\/][^\\\\/]+$");
        Matcher m;

        String[] mass = str.split("\\s{3,}");
        m = p.matcher(mass[1].replaceAll(" ", ""));
        if (m.find())
            return new Transaction(m.group().substring(1),
                    Double.parseDouble(receipt.replaceAll(",", ".")),
                    Double.parseDouble(expense.replaceAll(",", ".")));
        else return null;
    }

    @Override
    public String toString() {
        return this.name + " " + this.receipt + " " + this.expense;
    }

    public static Map<String, Double> getListOfExpenses(List<Transaction> transactions){
        Map<String, Double> expenseMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (!expenseMap.containsKey(transaction.name)) {
                expenseMap.put(transaction.name, transaction.expense);
                continue;
            }
            for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
                if (entry.getKey().equals(transaction.name))
                    expenseMap.put(entry.getKey(), entry.getValue() + transaction.expense);
            }
        }
        return expenseMap;
    }

    public static Map<String, Double> getListOfReceipt(List<Transaction> transactions){
        Map<String, Double> receipt = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (!receipt.containsKey(transaction.name)) {
                receipt.put(transaction.name, transaction.receipt);
                continue;
            }
            for (Map.Entry<String, Double> entry : receipt.entrySet()) {
                if (entry.getKey().equals(transaction.name))
                    receipt.put(entry.getKey(), entry.getValue() + transaction.receipt);
            }
        }
        return receipt;
    }

}
