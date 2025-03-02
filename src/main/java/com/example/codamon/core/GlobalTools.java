package com.example.codamon.core;

import java.util.Scanner;

public class GlobalTools {
    public static Scanner scanner = new Scanner(System.in);

    public static void waitPressEnter(){
        System.out.print(">>> Press [Enter]");
        scanner.nextLine();
    }

}
