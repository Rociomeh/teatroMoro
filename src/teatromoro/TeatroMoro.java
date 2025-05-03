package teatromoro;

import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoro {
    static String teatroNombre = "Teatro Moro";
    static final int capacidadSala = 5;
    static int totalEntradas = 0;
    static double totalIngresos = 0;
    static ArrayList<Venta> ventas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bienvenido(a) al sistema de ventas del " + teatroNombre);

        while (continuar) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver resumen de ventas");
            System.out.println("3. Imprimir boleta");
            System.out.println("4. Mostrar ingresos totales");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> comprarEntradas(sc);
                case 2 -> visualizarVentas();
                case 3 -> imprimirBoletas();
                case 4 -> mostrarIngresosTotales();
                case 5 -> continuar = false;
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        System.out.println("\nGracias por su compra. ¡Esperamos verlo pronto en el " + teatroNombre + "!");
        sc.close();
    }

    public static void comprarEntradas(Scanner sc) {
        if (totalEntradas >= capacidadSala) {
            System.out.println("No hay más entradas disponibles.");
            return;
        }

        System.out.print("¿Cuántas entradas desea comprar? (máximo " + (capacidadSala - totalEntradas) + "): ");
        int cantidad = sc.nextInt();
        if (cantidad < 1 || cantidad > capacidadSala - totalEntradas) {
            System.out.println("Cantidad no disponible.");
            return;
        }

        double totalCompra = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nCompra entrada #" + (i + 1));
            sc.nextLine();
            System.out.println("Seleccione zona:");
            System.out.println("1. VIP ($30000)");
            System.out.println("2. Platea ($20000)");
            System.out.println("3. Balcón ($15000)");
            int zonaSeleccion = sc.nextInt();
            String zona = seleccionarZona(zonaSeleccion);
            int precioBase = obtenerPrecioZona(zona);

            System.out.print("Ingrese edad del comprador: ");
            int edad = sc.nextInt();
            double descuento = (edad < 25) ? 0.10 : (edad >= 65) ? 0.15 : 0.0;
            String tipoCliente = (edad < 25) ? "Estudiante" : (edad >= 65) ? "Tercera Edad" : "General";

            double precioFinal = precioBase - (precioBase * descuento);

            Venta nuevaVenta = new Venta(totalEntradas + 1, zona, tipoCliente, precioBase, descuento, precioFinal);
            ventas.add(nuevaVenta);

            totalEntradas++;
            totalIngresos += precioFinal;
            totalCompra += precioFinal;
        }

        System.out.println("\nTotal a pagar por las " + cantidad + " entradas: $" + (int) totalCompra);
        System.out.println("¡Compra realizada exitosamente!");
    }

    public static void visualizarVentas() {
        if (ventas.isEmpty()) {
            System.out.println("\nNo hay ventas realizadas aún.");
            return;
        }

        System.out.println("\n--- Resumen de Ventas ---");
        for (Venta v : ventas) {
            System.out.println("Asiento #" + v.numeroAsiento + " | Zona: " + v.zona + " | Tipo: " + v.tipoCliente +
                               " | Precio final: $" + (int) v.precioFinal + " | Descuento aplicado: " + (int)(v.descuentoAplicado * 100) + "%");
        }
    }

    public static void imprimirBoletas() {
        if (ventas.isEmpty()) {
            System.out.println("\nNo hay ventas para imprimir boletas.");
            return;
        }

        System.out.println("\n--- Boletas Detalladas ---");
        for (Venta v : ventas) {
            System.out.println("\n--- Boleta ---");
            System.out.println("Teatro: " + teatroNombre);
            System.out.println("Número de Asiento: " + v.numeroAsiento);
            System.out.println("Ubicación: " + v.zona);
            System.out.println("Tipo de Cliente: " + v.tipoCliente);
            System.out.println("Precio Base: $" + (int) v.precioBase);
            System.out.println("Descuento Aplicado: " + (int)(v.descuentoAplicado * 100) + "%");
            System.out.println("Precio Final: $" + (int) v.precioFinal);
            System.out.println("¡Gracias por su compra y que disfrute la función!\n");
        }
    }

    public static void mostrarIngresosTotales() {
        System.out.println("\nTotal de entradas vendidas: " + totalEntradas);
        System.out.println("Total recaudado: $" + (int) totalIngresos);
    }

    public static String seleccionarZona(int opcion) {
        return switch (opcion) {
            case 1 -> "VIP";
            case 2 -> "Platea";
            case 3 -> "Balcón";
            default -> "General";
        };
    }

    public static int obtenerPrecioZona(String zona) {
        return switch (zona) {
            case "VIP" -> 30000;
            case "Platea" -> 20000;
            case "Balcón" -> 15000;
            default -> 0;
        };
    }
}

class Venta {
    int numeroAsiento;
    String zona;
    String tipoCliente;
    double precioBase;
    double descuentoAplicado;
    double precioFinal;

    public Venta(int numeroAsiento, String zona, String tipoCliente, double precioBase, double descuentoAplicado, double precioFinal) {
        this.numeroAsiento = numeroAsiento;
        this.zona = zona;
        this.tipoCliente = tipoCliente;
        this.precioBase = precioBase;
        this.descuentoAplicado = descuentoAplicado;
        this.precioFinal = precioFinal;
    }
}
