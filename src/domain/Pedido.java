package domain;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final Cliente cliente;
    private final List<ItemPedido> items;
    private EstadoPedido estado;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.EN_CREACION;
    }

    public void agregarItem(ItemPedido item) {
        if (estado == EstadoPedido.CONFIRMADO) {
            throw new IllegalStateException("No se pueden modificar pedidos confirmados.");
        }
        items.add(item);
    }

    public void confirmar() {
        estado = EstadoPedido.CONFIRMADO;
    }

    public double calcularTotalBruto() {
        return items.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public double calcularDescuento() {
        double totalBruto = calcularTotalBruto();
        long cantidadAnillados = items.stream()
                .filter(item -> item.getProducto() == Producto.ANILLADO)
                .mapToLong(ItemPedido::getCantidad)
                .sum();
        long cantidadImpresiones = items.stream()
                .filter(item -> item.getProducto() == Producto.IMPRESION_BN || item.getProducto() == Producto.IMPRESION_COLOR)
                .mapToLong(ItemPedido::getCantidad)
                .sum();

        if (cantidadAnillados >= 1 && cantidadImpresiones >= 30) {
            return totalBruto * 0.10;
        } else if (totalBruto > 30000) {
            return totalBruto * 0.05;
        }
        return 0.0;
    }

    public double calcularTotalFinal() {
        return calcularTotalBruto() - calcularDescuento();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItems() {
        return new ArrayList<>(items);
    }

    public EstadoPedido getEstado() {
        return estado;
    }
}