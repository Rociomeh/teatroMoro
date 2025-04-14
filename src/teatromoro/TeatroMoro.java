package teatromoro;

import java.util.Scanner;

public class TeatroMoro {
    static int num1 = 0, num2 = 0, num3 = 0, num4 = 0;
    static String zona1 = "", zona2 = "", zona3 = "", zona4 = "";
    static double precio1 = 0, precio2 = 0, precio3 = 0, precio4 = 0;
    static String tipo1 = "", tipo2 = "", tipo3 = "", tipo4 = "";

    static int totalEntradas = 0;
    static double totalIngresos = 0;
    static int entradaActual = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bienvenido(a) a la página de venta de entradas para el Teatro Moro.");

        while (continuar) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entrada por número");
            System.out.println("4. Eliminar entrada por número");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                int disponibles = 4 - totalEntradas;
                if (disponibles == 0) {
                    System.out.println("Ya ha comprado el máximo de 4 entradas.");
                    continue;
                }

                System.out.print("¿Cuántas entradas desea comprar? (máximo " + disponibles + "): ");
                int cantidad = sc.nextInt();
                if (cantidad < 1 || cantidad > disponibles) {
                    System.out.println("Cantidad inválida.");
                    continue;
                }

                for (int i = 0; i < cantidad; i++) {
                    System.out.println("\nCompra de entrada " + entradaActual);
                    System.out.println("1. VIP ($30000)\n2. Platea ($20000)\n3. General ($15000)");
                    System.out.print("Seleccione zona (1-3): ");
                    int zona = sc.nextInt();
                    int precioBase = 0;
                    String zonaTxt = "";
                    if (zona == 1) {
                        zonaTxt = "VIP";
                        precioBase = 30000;
                    } else if (zona == 2) {
                        zonaTxt = "Platea";
                        precioBase = 20000;
                    } else if (zona == 3) {
                        zonaTxt = "General";
                        precioBase = 15000;
                    } else {
                        System.out.println("Zona inválida. Operación cancelada.");
                        break;
                    }

                    System.out.print("Ingrese edad: ");
                    int edad = sc.nextInt();
                    double descuento = 0;
                    String tipo = "General";

                    if (edad < 25) {
                        descuento = 0.10;
                        tipo = "Estudiante";
                    } else if (edad >= 65) {
                        descuento = 0.15;
                        tipo = "Tercera edad";
                    }

                    double precioFinal = precioBase - (precioBase * descuento);
                    System.out.println("Total: $" + (int) precioFinal);

                    System.out.print("¿Desea confirmar la compra? (s/n): ");
                    char confirmacion = sc.next().charAt(0);
                    if (confirmacion != 's' && confirmacion != 'S') {
                        System.out.println("Compra cancelada.");
                        break;
                    }

                    if (entradaActual == 1) {
                        num1 = 1; zona1 = zonaTxt; precio1 = precioFinal; tipo1 = tipo;
                    } else if (entradaActual == 2) {
                        num2 = 2; zona2 = zonaTxt; precio2 = precioFinal; tipo2 = tipo;
                    } else if (entradaActual == 3) {
                        num3 = 3; zona3 = zonaTxt; precio3 = precioFinal; tipo3 = tipo;
                    } else if (entradaActual == 4) {
                        num4 = 4; zona4 = zonaTxt; precio4 = precioFinal; tipo4 = tipo;
                    }

                    totalEntradas++;
                    totalIngresos += precioFinal;
                    entradaActual++;
                    System.out.println("Compra realizada con éxito.");
                }

            } else if (opcion == 2) {
                System.out.println("10% para estudiantes (<25 años)");
                System.out.println("15% para tercera edad (>=65 años)");
                if (totalEntradas >= 3) {
                    System.out.println("Promoción: participa en sorteo por pase doble VIP");
                }

            } else if (opcion == 3) {
                System.out.print("Ingrese número de entrada que desea buscar(1-4): ");
                int numBuscar = sc.nextInt();
                if (numBuscar == 1 && num1 != 0)
                    mostrarEntrada(num1, zona1, tipo1, precio1);
                else if (numBuscar == 2 && num2 != 0)
                    mostrarEntrada(num2, zona2, tipo2, precio2);
                else if (numBuscar == 3 && num3 != 0)
                    mostrarEntrada(num3, zona3, tipo3, precio3);
                else if (numBuscar == 4 && num4 != 0)
                    mostrarEntrada(num4, zona4, tipo4, precio4);
                else
                    System.out.println("Entrada no encontrada.");

            } else if (opcion == 4) {
                System.out.print("Ingrese número de entrada que desea eliminar (1-4): ");
                int numEliminar = sc.nextInt();
                if (numEliminar == 1 && num1 != 0) {
                    num1 = 0; zona1 = ""; tipo1 = ""; precio1 = 0; totalEntradas--;
                    System.out.println("Entrada 1 eliminada.");
                } else if (numEliminar == 2 && num2 != 0) {
                    num2 = 0; zona2 = ""; tipo2 = ""; precio2 = 0; totalEntradas--;
                    System.out.println("Entrada 2 eliminada.");
                } else if (numEliminar == 3 && num3 != 0) {
                    num3 = 0; zona3 = ""; tipo3 = ""; precio3 = 0; totalEntradas--;
                    System.out.println("Entrada 3 eliminada.");
                } else if (numEliminar == 4 && num4 != 0) {
                    num4 = 0; zona4 = ""; tipo4 = ""; precio4 = 0; totalEntradas--;
                    System.out.println("Entrada 4 eliminada.");
                } else {
                    System.out.println("Número inválido o entrada ya eliminada.");
                }

            } else if (opcion == 5) {
                continuar = false;

            } else {
                System.out.println("Opción no válida.");
            }
        }

        System.out.println("\nGracias por usar el sistema de venta del Teatro Moro. ¡Hasta pronto!");
        System.out.println("Entradas vendidas: " + totalEntradas);
        System.out.println("Total recaudado: $" + (int) totalIngresos);
        sc.close();
    }

    public static void mostrarEntrada(int num, String zona, String tipo, double precio) {
        System.out.println("\nEntrada #" + num);
        System.out.println("Zona: " + zona);
        System.out.println("Tipo: " + tipo);
        System.out.println("Precio: $" + (int) precio);
    }
}
