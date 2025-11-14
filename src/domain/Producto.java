package domain;

public enum Producto {
    IMPRESION_BN(200, 150, 100),
    IMPRESION_COLOR(500, 400, 50),
    ANILLADO(3000, 3000, 0);

    private final double precioNormal;
    private final double precioVolumen;
    private final int umbralVolumen;

    Producto(double precioNormal, double precioVolumen, int umbralVolumen) {
        this.precioNormal = precioNormal;
        this.precioVolumen = precioVolumen;
        this.umbralVolumen = umbralVolumen;
    }

    public double getPrecio(int cantidad) {
        return (cantidad >= umbralVolumen && umbralVolumen > 0) ? precioVolumen : precioNormal;
    }
}