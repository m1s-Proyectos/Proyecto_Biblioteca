package modelos;

import java.util.Objects;

/**
 * Representa un registro de la vista {@code vista_libros_mas_prestados}.
 */
public class LibroMasPrestado {

    private String titulo;
    private int cantidadPrestamos;

    public LibroMasPrestado() {
    }

    public LibroMasPrestado(String titulo, int cantidadPrestamos) {
        this.titulo = titulo;
        this.cantidadPrestamos = cantidadPrestamos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadPrestamos() {
        return cantidadPrestamos;
    }

    public void setCantidadPrestamos(int cantidadPrestamos) {
        this.cantidadPrestamos = cantidadPrestamos;
    }

    @Override
    public String toString() {
        return "LibroMasPrestado{"
                + "titulo='" + titulo + '\''
                + ", cantidadPrestamos=" + cantidadPrestamos
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibroMasPrestado that = (LibroMasPrestado) o;
        return cantidadPrestamos == that.cantidadPrestamos && Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, cantidadPrestamos);
    }
}
