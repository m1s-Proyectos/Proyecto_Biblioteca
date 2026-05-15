package modelos;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un registro de la vista {@code vista_prestamos_activos}.
 */
public class PrestamoActivo {

    private int idPrestamo;
    private String estudiante;
    private String libro;
    private String bibliotecario;
    private LocalDateTime fechaPrestamo;
    private String estado;

    public PrestamoActivo() {
    }

    public PrestamoActivo(int idPrestamo, String estudiante, String libro, String bibliotecario,
                          LocalDateTime fechaPrestamo, String estado) {
        this.idPrestamo = idPrestamo;
        this.estudiante = estudiante;
        this.libro = libro;
        this.bibliotecario = bibliotecario;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(String bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Asigna la fecha de préstamo a partir de un {@link Timestamp} JDBC.
     *
     * @param ts marca de tiempo SQL (puede ser {@code null})
     */
    public void setFechaPrestamoDesdeSql(Timestamp ts) {
        this.fechaPrestamo = ts == null ? null : ts.toLocalDateTime();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PrestamoActivo{"
                + "idPrestamo=" + idPrestamo
                + ", estudiante='" + estudiante + '\''
                + ", libro='" + libro + '\''
                + ", bibliotecario='" + bibliotecario + '\''
                + ", fechaPrestamo=" + fechaPrestamo
                + ", estado='" + estado + '\''
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
        PrestamoActivo that = (PrestamoActivo) o;
        return idPrestamo == that.idPrestamo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrestamo);
    }
}
