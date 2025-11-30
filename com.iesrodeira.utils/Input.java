package com.iesrodeira.utils;

import java.util.Scanner;

public class Input {

    private static final Scanner SCN = new Scanner(System.in);
    private static final String CANCEL_STR = "*";

    // --- Métodos de Test/Validación ---

    public static void testNif(String nif) throws IllegalArgumentException {
        if (!nif.matches("\\d{8}[A-Z]")) {
            throw new IllegalArgumentException("El NIF debe tener 8 números seguidos de una letra mayúscula (ej: 12345678A).");
        }
    }

    public static void testEmail(String email) throws IllegalArgumentException {
        if (!email.matches("^([a-zA-Z0-9]+\\.?)+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,3})+$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido (ej: usuario@dominio.com).");
        }
    }

    public static void testPhone(String phone) throws IllegalArgumentException {
        if (!phone.matches("^\\d{9}$") && !phone.matches("^\\+\\d{2}\\d{9}$")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos o seguir el formato internacional (+XX9 dígitos).");
        }
    }

    // --- Métodos de Lectura Base ---
    
    // Lee texto, lanza CancelException si es '*'
    public static String readText(String title) throws CancelException {
        System.out.print(title + " (" + CANCEL_STR + " para cancelar): ");
        String input = SCN.nextLine();
        if (input.trim().equals(CANCEL_STR)) {
            throw new CancelException("Entrada cancelada por el usuario.");
        }
        return input.trim();
    }
    
    // Lee texto, retorna default si es Enter vacío, lanza CancelException si es '*'
    public static String readText(String title, String defaultVal) throws CancelException {
        System.out.print(title + " (Actual: " + defaultVal + ", " + CANCEL_STR + " para cancelar): ");
        String input = SCN.nextLine();
        
        if (input.trim().equals(CANCEL_STR)) {
            throw new CancelException("Entrada cancelada por el usuario.");
        }
        if (input.trim().isEmpty()) {
            return defaultVal;
        }
        return input.trim();
    }


    // --- Métodos de Lectura con Validación e Insistencia ---

    // Lee teléfono insistiendo
    public static String readPhone(String title) throws CancelException {
        String input;
        boolean ok = false;
        do {
            input = readText(title);
            try {
                if (input.isEmpty()) { return ""; } // Permite vacío
                testPhone(input);
                ok = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\t[ERROR]: " + e.getMessage());
            }
        } while (!ok);
        return input;
    }
    
    // Lee teléfono, permite default
    public static String readPhone(String title, String defaultVal) throws CancelException {
        String input;
        boolean ok = false;
        do {
            input = readText(title, defaultVal);
            if (input.equals(defaultVal)) { return defaultVal; }
            try {
                if (input.isEmpty()) { return ""; }
                testPhone(input);
                ok = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\t[ERROR]: " + e.getMessage());
            }
        } while (!ok);
        return input;
    }

    // Lee email insistiendo
    public static String readEmail(String title) throws CancelException {
        String input;
        boolean ok = false;
        do {
            input = readText(title);
            try {
                if (input.isEmpty()) { return ""; } // Permite vacío
                testEmail(input);
                ok = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\t[ERROR]: " + e.getMessage());
            }
        } while (!ok);
        return input;
    }
    
    // Lee email, permite default
    public static String readEmail(String title, String defaultVal) throws CancelException {
        String input;
        boolean ok = false;
        do {
            input = readText(title, defaultVal);
            if (input.equals(defaultVal)) { return defaultVal; }
            
            try {
                if (input.isEmpty()) { return ""; }
                testEmail(input);
                ok = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\t[ERROR]: " + e.getMessage());
            }
        } while (!ok);
        return input;
    }


    // Lee una opción válida
    public static char option(String title, String validos) throws CancelException {
        String input;
        char op = ' ';
        boolean ok = false;
        do {
            input = readText(title + " (" + validos + ")"); 
            input = input.trim().toUpperCase();

            if (input.length() == 1 && validos.toUpperCase().contains(input)) {
                op = input.charAt(0);
                ok = true;
            } else {
                System.out.println("\t[ERROR]: Opción no válida. Debe ser una de estas: " + validos);
            }
        } while (!ok);
        return op;
    }

    // Pregunta de confirmación
    public static boolean areYouSure() throws CancelException {
        char op = option("Está vostede seguro? (S/N)", "SN");
        return op == 'S';
    }

    // Espera Enter
    public static boolean waitEnter(String message) {
        System.out.print(message);
        SCN.nextLine();
        return true; 
    }

    public static boolean waitEnter() {
        return waitEnter("Para continuar pulsa Enter");
    }

}
