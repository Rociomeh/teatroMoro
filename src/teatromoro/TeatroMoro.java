package teatromoro;

import java.util.*;

public class TeatroMoro {
    static String teatroNombre = "Teatro Moro";
    static final int capacidadSala = 5;
    static int totalEntradas = 0;
    static double totalIngresos = 0;

    static Venta[] ventasArray = new Venta[capacidadSala]; 
    static ArrayList<Venta> ventasList = new ArrayList<>(); 
    static Vector<Venta> ventasVector = new Vector<>(); 

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

            int opcion = validarEntero(sc);

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

        int cantidad;
        do {
            System.out.print("¿Cuántas entradas desea comprar? (máximo " + (capacidadSala - totalEntradas) + "): ");
            cantidad = validarEntero(sc);
            if (cantidad < 1 || cantidad > capacidadSala - totalEntradas) {
                System.out.println("Cantidad inválida.");
            }
        } while (cantidad < 1 || cantidad > capacidadSala - totalEntradas);
        //Se confirmó que los índices no sobrepasan capacidadSala y que el asiento es único por entrada.
        
        double totalCompra = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nCompra entrada #" + (i + 1));

            int zonaSeleccion;
            do {
                System.out.println("Seleccione zona:");
                System.out.println("1. VIP ($30000)");
                System.out.println("2. Palco ($25000)");
                System.out.println("3. Platea Baja ($20000)");
                System.out.println("4. Platea Alta ($18000)");
                System.out.println("5. Galería ($10000)");
                zonaSeleccion = validarEntero(sc);
            } while (zonaSeleccion < 1 || zonaSeleccion > 5);

            String zona = seleccionarZona(zonaSeleccion);
            int precioBase = obtenerPrecioZona(zona);

            int edad;
            do {
                System.out.print("Ingrese edad del comprador: ");
                edad = validarEntero(sc);
            } while (edad < 0 || edad > 100);

            sc.nextLine();
            String genero;
            do {
                System.out.print("Ingrese género (M/F): ");
                genero = sc.nextLine().trim().toUpperCase();
            } while (!genero.equals("M") && !genero.equals("F"));
            //Punto de quiebre para validar que el input solo permita M o F. Funciona bien y evita errores de lógica en descuentos por género.

            System.out.print("¿Es estudiante? (s/n): ");
            String estudiante = sc.nextLine().trim().toLowerCase();

            double descuento = 0.0;
            if (edad <= 12) descuento = 0.10;
            if (genero.equals("F")) descuento = Math.max(descuento, 0.20);
            if (estudiante.equals("s")) descuento = Math.max(descuento, 0.15);
            if (edad >= 65) descuento = Math.max(descuento, 0.25);

            String tipoCliente = obtenerTipoCliente(edad, genero, estudiante);
            double precioFinal = precioBase - (precioBase * descuento);

            Venta nuevaVenta = new Venta(totalEntradas + 1, zona, tipoCliente, precioBase, descuento, precioFinal);
            ventasArray[totalEntradas] = nuevaVenta;
            ventasList.add(nuevaVenta);
            ventasVector.add(nuevaVenta);

            totalEntradas++;
            totalIngresos += precioFinal;
            totalCompra += precioFinal;
        }

        System.out.println("\nTotal a pagar por las " + cantidad + " entradas: $" + (int) totalCompra);
        System.out.println("¡Compra realizada exitosamente!");
    }

    public static void visualizarVentas() {
        if (ventasList.isEmpty()) {
            System.out.println("\nNo hay ventas realizadas aún.");
            return;
        }
        System.out.println("\n--- Resumen de Ventas ---");
        for (Venta v : ventasList) {
            System.out.println("Asiento #" + v.numeroAsiento + " | Zona: " + v.zona + " | Tipo: " + v.tipoCliente +
                    " | Precio final: $" + (int) v.precioFinal + " | Descuento aplicado: " + (int)(v.descuentoAplicado * 100) + "%");
        }
    }

    public static void imprimirBoletas() {
        if (ventasList.isEmpty()) {
            System.out.println("\nNo hay ventas para imprimir boletas.");
            return;
        }
        System.out.println("\n--- Boletas Detalladas ---");
        for (Venta v : ventasList) {
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
            case 2 -> "Palco";
            case 3 -> "Platea Baja";
            case 4 -> "Platea Alta";
            case 5 -> "Galería";
            default -> "General";
        };
    }

    public static int obtenerPrecioZona(String zona) {
        return switch (zona) {
            case "VIP" -> 30000;
            case "Palco" -> 25000;
            case "Platea Baja" -> 20000;
            case "Platea Alta" -> 18000;
            case "Galería" -> 10000;
            default -> 0;
        };
    }

    public static String obtenerTipoCliente(int edad, String genero, String estudiante) {
        if (edad <= 12) return "Niño/a";
        if (edad >= 65) return "Tercera Edad";
        if (estudiante.equals("s")) return "Estudiante";
        if (genero.equals("F")) return "Mujer";
        return "General";
    }

    public static int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. Ingrese un número válido: ");
            sc.next();
        }
        return sc.nextInt();
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
