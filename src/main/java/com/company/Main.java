package com.company;

        import java.util.List;
        import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try {
            Double allReceipt = 0.0;
            Double allExpense = 0.0;

            List<Transaction> transactionList = Transaction.getTransactions("res/movementList.csv");

            for (Transaction transaction : transactionList) {
                allReceipt = allReceipt + transaction.receipt;
                allExpense = allExpense + transaction.expense;
            }

            System.out.println("Общий приток средств на счет: " + allReceipt + " RUR");
            System.out.println("Общий расход средст по счету: " + allExpense + " RUR");

            System.out.println();

            Map<String, Double> expense = Transaction.getListOfExpenses(transactionList);
            printExpense(expense);
            Map<String, Double> receipt = Transaction.getListOfReceipt(transactionList);
            printReceipt(receipt);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void printExpense(Map<String, Double> expense){
        System.out.println("------------------------------------------------------- " + "\n");

        System.out.println("Детальная выписка по счету " + "\n");
        for (Map.Entry<String, Double> entry : expense.entrySet()) {
            System.out.println("Наименование расхода: " + entry.getKey() + " RUR" + "\n" + "Расход по счету: " + entry.getValue() + " RUR" + "\n");
        }
    }

    public static void printReceipt(Map<String, Double> receipt){
        System.out.println("------------------------------------------------------- " + "\n");
        System.out.println("Детальная выписка по счету " + "\n");
        for (Map.Entry<String, Double> entry : receipt.entrySet()) {
            System.out.println("Наименование прихода: " + entry.getKey() + " RUR" + "\n" + "Приход по счету: " + entry.getValue() + " RUR" + "\n");
        }
    }
}

