package com.company;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryProgram {
    public final Path path = Paths.get("booklist.txt");
    public final Path pathBorrowed = Paths.get("borrowed.txt");

    LibraryProgram() {
        List<String> bookNameList = getBookNames();
        List<String> borrowedList = getBorrowedList();
        List<String> sortedBookList = sortBookList(bookNameList);
        borrowBook(bookNameList, sortedBookList);
        returnBook();
        searchBookInList();
    }

    public List getBookNames() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(path);
        return new ArrayList<>();
    }

    public void printBookList(List<String> bookNameList) {
        System.out.println("Tillgängliga böcker för utlåning: ");
        for (int i = 0; i < bookNameList.size(); i++) {
            System.out.println(i + 1 + " " + bookNameList.get(i));
        }
    }

    public List getBorrowedList() {
        try {
            return Files.readAllLines(pathBorrowed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public void borrowBook(List<String> bookNameList, List<String> sortedBookList) {
        Scanner scan = new Scanner(System.in);
        printBookList(sortedBookList);
        System.out.println("Ange nummer på boken du vill låna: ");
        int bookChoice = scan.nextInt();
        try {

            for (int i = 0; i < bookNameList.size() + 1; i++) {
                if (i == bookChoice) {
                    List<String> s = Arrays.asList(sortedBookList.get(bookChoice - 1));
                    System.out.println("Du valde: " + sortedBookList.get(bookChoice - 1));
                    sortedBookList.remove(bookChoice - 1);
                    Files.write(path, sortedBookList, StandardCharsets.UTF_8);
                    Files.write(pathBorrowed, s, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    getBorrowedList();
                    break;
                }
            }
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }

    public List sortBookList(List<String> bookNameList) {
        return bookNameList.stream().sorted().collect(Collectors.toList());
    }

    public void returnBook() {
        Scanner scan = new Scanner(System.in);
        List<String> borrowedList = getBorrowedList();
        System.out.println("Dina lånade böcker är följande: ");
        for (int i = 0; i < borrowedList.size(); i++) {
            System.out.println(i + 1 + " " + borrowedList.get(i));
        }
        System.out.println("Ange nummer på boken du vill lämna tillbaka: ");
        int bookChoice = scan.nextInt();
        try {
            for (int i = 0; i < borrowedList.size() + 1; i++) {
                if (i == bookChoice) {
                    List<String> s = Arrays.asList(borrowedList.get(bookChoice - 1));
                    System.out.println("Du har lämnat tillbaka: " + borrowedList.get(bookChoice - 1));
                    borrowedList.remove(bookChoice - 1);
                    Files.write(pathBorrowed, borrowedList, StandardCharsets.UTF_8);
                    Files.write(path, s, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    getBorrowedList();
                    break;
                }
            }
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
        getBorrowedList();
    }

    private void searchBookInList() {
        List <String> sortedBookList = getBookNames();
        List <String> borrowedList = getBorrowedList();
        Scanner scan = new Scanner(System.in);
        String search;
        List <String> result = new ArrayList<>();
        System.out.println("Sök efter bok: ");
        search = scan.nextLine();
        for (String s : sortedBookList) {
            if (s.contains(search)) {
                result.add(s);
            }
        }
        for (String s1 : borrowedList) {
            if (s1.contains(search)) {
                result.add(s1 + " - utlånad");
            }
        }
        System.out.println(result);
    }

}