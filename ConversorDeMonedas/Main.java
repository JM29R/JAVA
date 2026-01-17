package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConversorMonedas v = new ConversorMonedas(
                "https://v6.exchangerate-api.com/v6/3e2311887dc2c66a065cb5b0/latest/USD");
        Scanner scanner = new Scanner(System.in);
        int opcion;
        float cantidad;

        do {
            System.out.println("""
                    ---------------------------------------
                    |  BIENVENIDO AL CONVERSOR DE MONEDAS  |
                    ---------------------------------------
                    | 1. DOLAR -> PESO ARGENTINO          |
                    | 2. PESO ARGENTINO -> DOLAR          |
                    | 3. DOLAR -> REAL BRASILEÑO          |
                    | 4. REAL BRASILEÑO -> DOLAR          |
                    | 5. DOLAR -> PESO COLOMBIANO         |
                    | 6. PESO COLOMBIANO -> DOLAR         |
                    | 7. SALIR                             |
                    | ELIJA UNA OPCION:                    |
                    ---------------------------------------
                    """);

            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.println("INGRESE LA CANTIDAD A CONVERTIR:");
                cantidad = scanner.nextFloat();

                switch (opcion) {
                    case 1 ->
                        System.out.println(cantidad + " DOLARES SON " + v.DLRtoARS(cantidad) + " PESOS ARGENTINOS");
                    case 2 ->
                        System.out.println(cantidad + " PESOS ARGENTINOS SON " + v.ARStoDLR(cantidad) + " DOLARES");
                    case 3 ->
                        System.out.println(cantidad + " DOLARES SON " + v.DLRtoBRL(cantidad) + " REALES BRASILEÑOS");
                    case 4 ->
                        System.out.println(cantidad + " REALES BRASILEÑOS SON " + v.BRLtoDLR(cantidad) + " DOLARES");
                    case 5 ->
                        System.out.println(cantidad + " DOLARES SON " + v.DLRtoCOP(cantidad) + " PESOS COLOMBIANOS");
                    case 6 ->
                        System.out.println(cantidad + " PESOS COLOMBIANOS SON " + v.COPtoDLR(cantidad) + " DOLARES");
                }

            } else if (opcion == 7) {
                System.out.println("GRACIAS POR USAR EL CONVERSOR DE MONEDAS");
            } else {
                System.out.println("OPCION NO VALIDA");
            }

        } while (opcion != 7);

        scanner.close();
    }
}
