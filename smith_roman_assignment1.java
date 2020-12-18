// Author: Roman Smith
// Class/Assignment: CSE 205/Assignment 1
// Due Date: June 2, 2020
// Description: presents a food menu, takes either a manual input or file input food order from the user, and prints an invoice

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class smith_roman_assignment1 {
    public static void main(String[] args) {
        int orderType;
        orderType = orderType(); // calls the orderType method and stores the return in the orderType int
        ArrayList<String> order;

        // based on user input, calls either the manual order or file order methods to fill the order array
        if (orderType == 1) {
            order = manualOrder();
        } else {
            order = fileOrder();
        }

        // print the invoice
        printInvoice(order);
    } //main

    // Prints the order type menu and returns order type (manual input or file input) as an int selection
    public static int orderType() {
        Scanner in = new Scanner(System.in);
        int orderType = 0;
        String trash;
        boolean hasInt = false;
        boolean validInput = false;

        // prints the menu, collects user input, and loops until user enters 1 or 2
        do {
            System.out.println("Welcome to Basic Bakers Bakery!");
            System.out.println("Would you like to: ");
            System.out.println("1) Manually process an order");
            System.out.println("2) Read an order from a file");

            // checks if input is an integer and if so, reads in the integer
            if (in.hasNextInt()) {
                orderType = in.nextInt();
                hasInt = true;
            } else {
                trash = in.nextLine(); // gets rid of the non-integer input from the input stream
            }


            // checks if input is an integer and that the integer is 1 or 2
            if (hasInt && (orderType == 1 || orderType == 2)) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter your choice as the number 1 or 2.");
            }

        } while (!validInput); // loops until input is valid

        return orderType;
    } //order type

    // METHODS FOR MANUAL ORDER

    // Calls the itemSelect method to take the user's selection until the user is finished,
    // stores all selections in an ArrayList and returns that ArrayList
    public static ArrayList<String> manualOrder() {
        int selection;
        int itemCount = 0;
        ArrayList<String> order = new ArrayList<>();

        // loops the selection until the user is finished
        do {
            selection = itemSelect(itemCount); // assigns the integer that the user enters to selection

            // checks if the user wants to stop, if not, adds the selection to the order ArrayList
            if (selection != -1) {
                order.add(menuIntToString(selection)); // calls the method to convert the int selection to a String and adds it to the ArrayList
                itemCount++;
            }
        } while (selection != -1);

        return order;
    } //manual order

    // translates the integer item selection to a String to be stored in the ArrayList
    public static String menuIntToString(int intSelect) {

        // compares the entered integer choice to the menu integers and returns the corresponding string
        if (intSelect == 1) {
            return "giantcookie";
        } else if (intSelect == 2) {
            return "smallcookie";
        } else if (intSelect == 3) {
            return "bar";
        } else if (intSelect == 4) {
            return "cakesquare";
        } else if (intSelect == 5) {
            return "pie";
        } else if (intSelect == 6) {
            return "giantbrownie";
        } else if (intSelect == 7) {
            return "soda";
        } else {
            return "water";
        }
    } //menuIntToString

    // Prints the food menu and returns the user's item selection as an int
    public static int itemSelect(int itemCount) {
        Scanner in = new Scanner(System.in);
        int selection = 0;
        String trashInput;
        boolean hasInt = false;
        boolean validInput = false;

        // prints menu and loops until the user gives a valid input
        do {
            System.out.println("Menu: ");
            System.out.println("    1. Giant Cookie - $3.00");
            System.out.println("    2. Small Cookie - $1.25");
            System.out.println("    3. Bars - $5.00");
            System.out.println("    4. Cake Squares - $5.50");
            System.out.println("    5. Pie - $6.00");
            System.out.println("    6. Giant Brownie - $5.50");
            System.out.println("    7. Soda - $1.50");
            System.out.println("    8. Bottled Water - $1.25");
            System.out.printf("Please choose item #%d (if you are finished adding items, enter -1):%n", itemCount + 1);

            // checks if the input contains an integer
            if (in.hasNextInt()) {
                selection = in.nextInt();
                hasInt = true;
            } else {
                trashInput = in.nextLine(); // gets rid of the non-integer input from the input stream
            }

            // checks if the integer is on the menu or is the quit indicator -1
            if (hasInt && (selection == -1 || (selection > 0 && selection < 9))) {
                validInput = true;
            } else {
                System.out.println("Invalid input, please enter the number that corresponds to the item you would like to order");
            }
        } while (!validInput);
        return selection;
    } //itemSelect

    // METHOD FOR FILE INPUT ORDER

    // has the user input the file name and then reads each order line by line and fills the order ArrayList
    public static ArrayList<String> fileOrder() {
        Scanner in = new Scanner(System.in);
        File inputFile;
        Scanner fileIn = null;
        String filename = null;
        String trash; // for use in removing the number in the first line of the file
        int indexCount = 0; // for counting the current index as the ArrayList is filled
        ArrayList<String> order = new ArrayList<>();

        System.out.println("Processing order from file.");
        System.out.println("Please enter the filename: ");

        // collects file name from user
        filename = in.nextLine();

        // creates new file based on user input and creates a new input stream
        try {
            inputFile = new File(filename);
            fileIn = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // checks if file starts with an int and removes it since it does not go in the order and isn't needed for the ArrayList
        if (fileIn.hasNextInt()) {
            trash = fileIn.nextLine();
        }

        // adds each line to a new index in the ArrayList
        while (fileIn.hasNextLine()) {
            order.add(fileIn.nextLine()); // adds the item to the order ArrayList
            System.out.println("read " + order.get(indexCount)); // prints each order to the screen to confirm it's been read
            indexCount++; //moves to next index after current one is printed
        }

        fileIn.close();

        return order;
    } //fileOrder

    // Prints the invoice
    public static void printInvoice(ArrayList<String> orderToPrint) {
        PrintWriter outFile = null;
        double subtotal = 0;
        double tax;
        double total;

        // creates the invoice file
        try {
            outFile = new PrintWriter("invoice.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // invoice header
        outFile.println("FoodStand");
        outFile.println("---------------------------------");

        // loops through each index in the order array
        for (int index = 0; index < orderToPrint.size(); index++) {

            //checks the contents of the index and prints out the appropriate line in the invoice, adds each price to subtotal cost
            if (orderToPrint.get(index).equals("giantcookie")) {
                outFile.printf("%-15S-%13s3.00%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 3.00;
            } else if (orderToPrint.get(index).equals("smallcookie")) {
                outFile.printf("%-15S-%13s1.25%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 1.25;
            } else if (orderToPrint.get(index).equals("bar")) {
                outFile.printf("%-15S-%13s5.00%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 5.00;
            } else if (orderToPrint.get(index).equals("cakesquare")) {
                outFile.printf("%-15S-%13s5.50%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 5.50;
            } else if (orderToPrint.get(index).equals("pie")) {
                outFile.printf("%-15S-%13s6.00%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 6.00;
            } else if (orderToPrint.get(index).equals("giantbrownie")) {
                outFile.printf("%-15S-%13s5.50%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 5.50;
            } else if (orderToPrint.get(index).equals("soda")) {
                outFile.printf("%-15S-%13s1.50%n", orderToPrint.get(index), " ");
                subtotal = subtotal + 1.50;
            } else {
                outFile.printf("%-15S-%13s1.25%n",orderToPrint.get(index), " ");
                subtotal = subtotal + 1.25;
            }
        }

        // calculating tax and total
        tax = subtotal * 0.05;
        total = subtotal + tax;

        // displaying subtotal, tax, and total
        outFile.println("---------------------------------");
        outFile.printf("\t\t\t%-9s-%10.2f %n", "Subtotal", subtotal);
        outFile.printf("\t\t\t%-9s-%10.2f %n", "Tax", tax);
        outFile.printf("\t\t\t%-9s-%10.2f %n", "Total",total);

        outFile.close();

    } //printInvoice

} //class