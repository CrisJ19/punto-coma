package service;

import domain.*;

public class PuntoComaService {
    private Pedido pedidoActual;

    public void crearPedido(Cliente cliente) {
        pedidoActual = new Pedido(cliente);
    }

    public void agregarItem(Producto producto, int cantidad) {
        ItemPedido item = new ItemPedido(producto, cantidad);
        pedidoActual.agregarItem(item);
    }

    public void confirmarPedido() {
        pedidoActual.confirmar();
    }

    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        Cliente cliente = pedidoActual.getCliente();
        resumen.append("Resumen del Pedido\n");
        resumen.append("------------------\n");
        resumen.append(String.format("Cliente: %s\nTeléfono: %s\n\n", cliente.getNombre(), cliente.getTelefono()));
        resumen.append("Detalle:\n");
        double totalBruto = pedidoActual.calcularTotalBruto();
        for (ItemPedido item : pedidoActual.getItems()) {
            resumen.append(String.format("- %s x%d: $%.0f c/u = $%.0f\n",
                    item.getProducto(), item.getCantidad(), item.getPrecioUnitario(), item.getSubtotal()));
        }
        double descuento = pedidoActual.calcularDescuento();
        double totalFinal = pedidoActual.calcularTotalFinal();
        double porcentajeDescuento = totalBruto > 0 ? (descuento / totalBruto) * 100 : 0;
        resumen.append(String.format("\nTotal Bruto: $%.0f\n", totalBruto));
        resumen.append(String.format("Descuento: %.0f%% ($%.0f)\n", porcentajeDescuento, descuento));
        resumen.append(String.format("Total Final: $%.0f\n", totalFinal));
        return resumen.toString();
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }
}