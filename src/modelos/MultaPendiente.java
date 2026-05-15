package modelos;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un registro de la vista {@code vista_multas_pendientes}.
 */
public class MultaPendiente {

    private String estudiante;
    private String libro;
    private double monto;
    private LocalDateTime fechaGeneracion;

    public MultaPendiente() {
    }

    public MultaPendiente(String estudiante, String libro, double monto, LocalDateTime fechaGeneracion) {
        this.estudiante = estudiante;
        this.libro = libro;
        this.monto = monto;
        this.fechaGeneracion = fechaGeneracion;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public void setFechaGeneracionDesdeSql(Timestamp ts) {
        this.fechaGeneracion = ts == null ? null : ts.toLocalDateTime();
    }

    @Override
    public String toString() {
        return "MultaPendiente{"
                + "estudiante='" + estudiante + '\''
                + ", libro='" + libro + '\''
                + ", monto=" + monto
                + ", fechaGeneracion=" + fechaGeneracion
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
        MultaPendiente that = (MultaPendiente) o;
        return Double.compare(that.monto, monto) == 0
                && Objects.equals(estudiante, that.estudiante)
                && Objects.equals(libro, that.libro)
                && Objects.equals(fechaGeneracion, that.fechaGeneracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, libro, monto, fechaGeneracion);
    }
}
