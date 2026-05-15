package modelos;

import java.util.Objects;

/**
 * Representa un registro de la vista {@code vista_reporte_ejemplares}.
 */
public class ReporteEjemplar {

    private int idEjemplar;
    private String titulo;
    private String autor;
    private String isbn;
    private String codigoBarra;
    private String ubicacion;
    private String estado;
    private int totalPrestamos;

    public ReporteEjemplar() {
    }

    public ReporteEjemplar(int idEjemplar, String titulo, String autor, String isbn, String codigoBarra,
                           String ubicacion, String estado, int totalPrestamos) {
        this.idEjemplar = idEjemplar;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.codigoBarra = codigoBarra;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.totalPrestamos = totalPrestamos;
    }

    public int getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTotalPrestamos() {
        return totalPrestamos;
    }

    public void setTotalPrestamos(int totalPrestamos) {
        this.totalPrestamos = totalPrestamos;
    }

    @Override
    public String toString() {
        return "ReporteEjemplar{"
                + "idEjemplar=" + idEjemplar
                + ", titulo='" + titulo + '\''
                + ", autor='" + autor + '\''
                + ", isbn='" + isbn + '\''
                + ", codigoBarra='" + codigoBarra + '\''
                + ", ubicacion='" + ubicacion + '\''
                + ", estado='" + estado + '\''
                + ", totalPrestamos=" + totalPrestamos
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
        ReporteEjemplar that = (ReporteEjemplar) o;
        return idEjemplar == that.idEjemplar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEjemplar);
    }
}
