package app;

import domain.Cliente;
import domain.Producto;
import service.PuntoComaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PuntoComaService service = new PuntoComaService();

        Cliente cliente = null;
        while (cliente == null) {
            System.out.println("Ingrese nombre del cliente (solo letras y espacios):");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese teléfono (10 dígitos numéricos):");
            String telefono = scanner.nextLine();
            try {
                cliente = new Cliente(nombre, telefono);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        service.crearPedido(cliente);

        while (true) {
            System.out.println("\nSeleccione producto (1: B/N, 2: Color, 3: Anillado, 0: Terminar):");
            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }
            if (opcion == 0) break;
            Producto producto;
            switch (opcion) {
                case 1: producto = Producto.IMPRESION_BN; break;
                case 2: producto = Producto.IMPRESION_COLOR; break;
                case 3: producto = Producto.ANILLADO; break;
                default:
                    System.out.println("Opción inválida.");
                    continue;
            }
            System.out.println("Ingrese cantidad:");
            int cantidad;
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }
            try {
                service.agregarItem(producto, cantidad);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (service.getPedidoActual().getItems().isEmpty()) {
            System.out.println("Error: No se puede continuar sin ítems en el pedido.");
            scanner.close();
            return;
        }

        System.out.println("\n" + service.generarResumen());

        System.out.println("¿Confirmar pedido? (s/n):");
        String confirmar = scanner.nextLine();
        if (confirmar.equalsIgnoreCase("s")) {
            try {
                service.confirmarPedido();
                System.out.println("Pedido confirmado exitosamente.");
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\nIntentando agregar un ítem después de confirmar...");
        try {
            service.agregarItem(Producto.IMPRESION_BN, 1);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}