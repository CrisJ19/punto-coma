package domain;

public class Cliente {
    private final String nombre;
    private final String telefono;

    public Cliente(String nombre, String telefono) {
        if (nombre == null || nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
        }
        if (telefono == null || telefono.length() != 10 || !telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe tener 10 dígitos numéricos.");
        }
        this.nombre = nombre.trim();
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }
}