package teatromoro;

import java.util.Scanner;

public class TeatroMoro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        int totalVentas = 0;
        int entradasVendidas = 0;

        System.out.println("Bienvenido(a) a la página de venta de entradas para el Teatro Moro.");

        while (continuar) {
            System.out.println("--- MENÚ PRINCIPAL ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.print("Ingrese una opción: ");

            int opcion = 0;
            try {
                opcion = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
                sc.nextLine(); // limpiar el buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    int precioBase = 0;
                    int edad = 0;
                    double descuento = 0.0;
                    double precioFinal = 0.0;
                    String zona = "";

                    System.out.println("--- Plano del Teatro ---");
                    System.out.println("1. Zona A (VIP) - $30.000");
                    System.out.println("2. Zona B (Platea Baja) - $20.000");
                    System.out.println("3. Zona C (Platea Alta) - $15.000");

                    int ubicacion = 0;
                    while (true) {
                        System.out.print("Seleccione la zona (1-3): ");
                        ubicacion = sc.nextInt();
                        if (ubicacion == 1 || ubicacion == 2 || ubicacion == 3) {
                            switch (ubicacion) {
                                case 1:
                                    zona = "Zona A - VIP";
                                    precioBase = 30000;
                                    break;
                                case 2:
                                    zona = "Zona B - Platea Baja";
                                    precioBase = 20000;
                                    break;
                                case 3:
                                    zona = "Zona C - Platea Alta";
                                    precioBase = 15000;
                                    break;
                            }
                            break;
                        } else {
                            System.out.println("Ubicación inválida. Intente nuevamente.");
                        }
                    }

                    System.out.print("Ingrese su edad: ");
                    edad = sc.nextInt();

                    if (edad <= 0 || edad > 120) {
                        System.out.println("Edad no válida. Se aplicará tarifa sin descuento.");
                    } else if (edad < 25) {
                        descuento = 0.10; // estudiante
                        System.out.println("Descuento estudiante aplicado: 10%");
                    } else if (edad >= 90) {
                        descuento = 0.15; // tercera edad
                        System.out.println("Descuento tercera edad aplicado: 15%");
                    }

                    int contador = 0;
                    do {
                        precioFinal = precioBase - (precioBase * descuento);
                        contador++;
                    } while (contador < 1);

                    System.out.println("--- RESUMEN DE COMPRA ---");
                    System.out.println("Ubicación del asiento: " + zona);
                    System.out.println("Precio base: $" + precioBase);
                    System.out.println("Descuento aplicado: " + (int)(descuento * 100) + "%");
                    System.out.println("Precio final a pagar: $" + (int)precioFinal);

                    totalVentas += precioFinal;
                    entradasVendidas++;
                   
                    System.out.print("¿Desea realizar otra compra? (s/n): ");
                    sc.nextLine();
                    String respuesta = sc.nextLine();
                    if (!respuesta.equalsIgnoreCase("s")) {
                        continuar = false;
                    }
                    break;

                case 2:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
        System.out.println("Entradas vendidas: " + entradasVendidas);
        System.out.println("Total recaudado: $" + totalVentas);

        sc.close();
    }
}
