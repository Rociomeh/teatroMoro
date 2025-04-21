package teatromoro;

import java.util.Scanner;

public class TeatroMoro {
    static String teatroNombre = "Teatro Moro";
    static final int capacidadSala = 5;
    static int totalEntradas = 0;
    static double totalIngresos = 0;

    static int num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0;
    static String zona1 = "", zona2 = "", zona3 = "", zona4 = "", zona5 = "";
    static double precio1 = 0, precio2 = 0, precio3 = 0, precio4 = 0, precio5 = 0;
    static String tipo1 = "", tipo2 = "", tipo3 = "", tipo4 = "", tipo5 = "";

    static boolean reserva1 = false, reserva2 = false, reserva3 = false, reserva4 = false, reserva5 = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bienvenido(a) al sistema de reservas y ventas del " + teatroNombre);

        while (continuar) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Reservar entrada");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Modificar venta");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    reservarEntrada(sc);
                    break;
                case 2:
                    comprarEntradas(sc);
                    break;
                case 3:
                    modificarVenta(sc);
                    break;
                case 4:
                    imprimirBoleta();
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("\nGracias por usar el sistema de venta del " + teatroNombre + ". ¡Hasta pronto!");
        sc.close();
    }
    
        public static void reservarEntrada(Scanner sc) {
        System.out.print("Ingrese el número de asiento a reservar (1-5): ");
        int asiento = sc.nextInt();
        if (asiento < 1 || asiento > 5) {
            System.out.println("Número de asiento inválido.");
            return;
        }
        if (getEstadoReserva(asiento)) {
            System.out.println("Asiento ya reservado o comprado.");
        } else {
            setReserva(asiento, true);
            System.out.println("Asiento " + asiento + " reservado exitosamente.");
        }
    }

    public static void comprarEntradas(Scanner sc) {
        System.out.print("¿Cuántas entradas desea comprar? (máximo 5): ");
        int cantidad = sc.nextInt();
        if (cantidad < 1 || cantidad > 5 - totalEntradas) { 
            System.out.println("Cantidad no disponible.");
            return;
        }

        double totalCompra = 0;
        int[] asientosComprados = new int[cantidad];
        int contador = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el número de asiento a comprar (1-5): ");
            int asiento = sc.nextInt();
            if (asiento < 1 || asiento > 5 || getEstadoCompra(asiento)) {
                System.out.println("Asiento inválido o ya comprado.");
                i--;
                continue;
            }

            String zona = seleccionarZona(sc);
            int precioBase = obtenerPrecioZona(zona);

            System.out.print("Ingrese edad del comprador: ");
            int edad = sc.nextInt();
            double descuento = (edad < 25) ? 0.10 : (edad >= 65) ? 0.15 : 0.0; //Prueba de depuración para validar que se está generando el descuento correctamente
            String tipo = (edad < 25) ? "Estudiante" : (edad >= 65) ? "Tercera Edad" : "General";

            double precioFinal = precioBase - (precioBase * descuento);
            totalCompra += precioFinal;

            asignarDatos(asiento, zona, tipo, precioFinal);
            totalEntradas++;
            totalIngresos += precioFinal;
            asientosComprados[contador++] = asiento;
        }

        System.out.println("\nTotal a pagar por las " + cantidad + " entradas: $" + (int) totalCompra);
        System.out.print("¿Desea confirmar el pago? (s/n): ");
        char confirmar = sc.next().charAt(0);

        if (confirmar == 's' || confirmar == 'S') {
            System.out.println("Pago confirmado. ¡Gracias por su compra!");
            System.out.println("\nResumen de entradas compradas:");
            for (int i = 0; i < contador; i++) {
                int num = asientosComprados[i];
                System.out.println("Asiento #" + num);
                System.out.println("Zona: " + obtenerZona(num));
                System.out.println("Tipo: " + obtenerTipo(num));
                System.out.println("Precio: $" + (int) obtenerPrecio(num) + "\n");
            }
        } else {
            System.out.println("Compra cancelada. Se liberarán los asientos.");
            cancelarUltimaCompra(cantidad);
        }
    }

    public static void cancelarUltimaCompra(int cantidad) {
        for (int i = 5; i >= 1 && cantidad > 0; i--) {
            if (getEstadoCompra(i)) {
                asignarDatos(i, "", "", 0);
                switch (i) {
                    case 1 -> num1 = 0;
                    case 2 -> num2 = 0;
                    case 3 -> num3 = 0;
                    case 4 -> num4 = 0;
                    case 5 -> num5 = 0;
                }
                totalEntradas--;
                cantidad--;
            }
        }
    }

    public static void modificarVenta(Scanner sc) {
        System.out.print("Ingrese el número de asiento a modificar (1-5): ");
        int asiento = sc.nextInt();
        if (!getEstadoCompra(asiento)) {
            System.out.println("No hay venta registrada para este asiento.");
            return;
        }
        System.out.print("Ingrese nueva edad del comprador: ");
        int edad = sc.nextInt();
        double descuento = (edad < 25) ? 0.10 : (edad >= 65) ? 0.15 : 0.0;
        String tipo = (edad < 25) ? "Estudiante" : (edad >= 65) ? "Tercera Edad" : "General";
        String zona = obtenerZona(asiento);
        double precioBase = obtenerPrecioZona(zona);
        double precioFinal = precioBase - (precioBase * descuento);

        asignarDatos(asiento, zona, tipo, precioFinal);
        System.out.println("Venta modificada exitosamente.");
    }

    public static void imprimirBoleta() {
        for (int i = 1; i <= 5; i++) {
            if (getEstadoCompra(i)) { //debugg para verificar qué asientos están vendidos.
                System.out.println("Boleta Asiento #" + i);
                System.out.println("Zona: " + obtenerZona(i));
                System.out.println("Tipo: " + obtenerTipo(i));
                System.out.println("Precio: $" + (int) obtenerPrecio(i));
            }
        }
        System.out.println("Total entradas vendidas: " + totalEntradas);
        System.out.println("Total ingresos: $" + (int) totalIngresos);
    }

    public static boolean getEstadoReserva(int num) {
        return switch (num) {
            case 1 -> reserva1 || num1 != 0;
            case 2 -> reserva2 || num2 != 0;
            case 3 -> reserva3 || num3 != 0;
            case 4 -> reserva4 || num4 != 0;
            case 5 -> reserva5 || num5 != 0;
            default -> false;
        };
    }

    public static boolean getEstadoCompra(int num) {
        return switch (num) {
            case 1 -> num1 != 0;
            case 2 -> num2 != 0;
            case 3 -> num3 != 0;
            case 4 -> num4 != 0;
            case 5 -> num5 != 0;
            default -> false;
        };
    }

    public static void setReserva(int num, boolean estado) {
        switch (num) {
            case 1 -> reserva1 = estado;
            case 2 -> reserva2 = estado;
            case 3 -> reserva3 = estado;
            case 4 -> reserva4 = estado;
            case 5 -> reserva5 = estado;
        }
    }

    public static void asignarDatos(int num, String zona, String tipo, double precio) {
        switch (num) {
            case 1 -> { num1 = num; zona1 = zona; tipo1 = tipo; precio1 = precio; reserva1 = false; }
            case 2 -> { num2 = num; zona2 = zona; tipo2 = tipo; precio2 = precio; reserva2 = false; }
            case 3 -> { num3 = num; zona3 = zona; tipo3 = tipo; precio3 = precio; reserva3 = false; }
            case 4 -> { num4 = num; zona4 = zona; tipo4 = tipo; precio4 = precio; reserva4 = false; }
            case 5 -> { num5 = num; zona5 = zona; tipo5 = tipo; precio5 = precio; reserva5 = false; }
        }
    }

    public static String obtenerZona(int num) {
        return switch (num) {
            case 1 -> zona1;
            case 2 -> zona2;
            case 3 -> zona3;
            case 4 -> zona4;
            case 5 -> zona5;
            default -> "";
        };
    }

    public static String obtenerTipo(int num) {
        return switch (num) {
            case 1 -> tipo1;
            case 2 -> tipo2;
            case 3 -> tipo3;
            case 4 -> tipo4;
            case 5 -> tipo5;
            default -> "";
        };
    }

    public static double obtenerPrecio(int num) {
        return switch (num) {
            case 1 -> precio1;
            case 2 -> precio2;
            case 3 -> precio3;
            case 4 -> precio4;
            case 5 -> precio5;
            default -> 0;
        };
    }

    public static String seleccionarZona(Scanner sc) {
        System.out.println("Seleccione zona:");
        System.out.println("1. VIP ($30000)");
        System.out.println("2. Platea ($20000)");
        System.out.println("3. General ($15000)");
        int opcion = sc.nextInt();
        return switch (opcion) {
            case 1 -> "VIP";
            case 2 -> "Platea";
            case 3 -> "General";
            default -> "General";
        };
    }

    public static int obtenerPrecioZona(String zona) {
        return switch (zona) {
            case "VIP" -> 30000;
            case "Platea" -> 20000;
            case "General" -> 15000;
            default -> 0;
        };
    }
}