package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private final String idReserva;
    private final String idSocio;
    private final String idPista;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracionMin;
    private double precio;

    public Reserva(String idReserva, String idSocio, String idPista,
                   LocalDate fecha, LocalTime horaInicio,
                   int duracionMin, double precio)
            throws IdObligatorioException {

        if (idReserva == null || idReserva.isBlank())
            throw new IdObligatorioException("idReserva obligatorio");

        if (duracionMin <= 0)
            throw new IdObligatorioException("Duración debe ser > 0");

        if (precio < 0)
            throw new IdObligatorioException("Precio debe ser >= 0");

        this.idReserva = idReserva;
        this.idSocio = idSocio;
        this.idPista = idPista;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionMin = duracionMin;
        this.precio = precio;
    }

    public String getIdReserva() { return idReserva; }
    public String getIdSocio() { return idSocio; }
    public String getIdPista() { return idPista; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public int getDuracionMin() { return duracionMin; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return idReserva + ";" + idSocio + ";" + idPista + ";" +
               fecha + ";" + horaInicio + ";" +
               duracionMin + ";" + precio;
    }
}
