package servicio;

import modelo.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClubDeportivo {

    private ArrayList<Socio> socios = new ArrayList<>();
    private ArrayList<Pista> pistas = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();

    private final String FSOCIOS = "src/data/socios.txt";
    private final String FPISTAS = "src/data/pistas.txt";
    private final String FRESERVAS = "src/data/reservas.txt";
    public ClubDeportivo() {

        //donde busca los archivos
        System.out.println("Ruta socios: " + new File(FSOCIOS).getAbsolutePath());
        System.out.println("Ruta pistas: " + new File(FPISTAS).getAbsolutePath());
        System.out.println("Ruta reservas: " + new File(FRESERVAS).getAbsolutePath());

        cargarSocios();
        cargarPistas();
        cargarReservas();

        //cuantos ha cargado
        System.out.println("Socios cargados: " + socios.size());
        System.out.println("Pistas cargadas: " + pistas.size());
        System.out.println("Reservas cargadas: " + reservas.size());
    }

    // ================= SOCIOS =================

    public boolean altaSocio(Socio s) {
        for (Socio x : socios)
            if (x.getIdSocio().equals(s.getIdSocio()))
                return false;

        socios.add(s);
        guardarSocios();
        return true;
    }

    public void bajaSocio(String id) {
        for (Reserva r : reservas)
            if (r.getIdSocio().equals(id) &&
                r.getFecha().isAfter(LocalDate.now()))
                return;

        socios.removeIf(s -> s.getIdSocio().equals(id));
        guardarSocios();
    }

    // ================= PISTAS =================

    public boolean altaPista(Pista p) {
        for (Pista x : pistas)
            if (x.getIdPista().equals(p.getIdPista()))
                return false;

        pistas.add(p);
        guardarPistas();
        return true;
    }

    public void cambiarDisponibilidadPista(String id, boolean disponible) {
        for (Pista p : pistas)
            if (p.getIdPista().equals(id))
                p.setDisponible(disponible);

        guardarPistas();
    }

    // ================= RESERVAS =================

    public boolean crearReserva(Reserva r) {

        for (Pista p : pistas)
            if (p.getIdPista().equals(r.getIdPista())
                    && !p.isDisponible())
                return false;

        reservas.add(r);
        guardarReservas();
        return true;
    }

    public void cancelarReserva(String id) {
        reservas.removeIf(r -> r.getIdReserva().equals(id));
        guardarReservas();
    }

    // ================= PERSISTENCIA =================

    private void cargarSocios() {
        File f = new File(FSOCIOS);
        if (!f.exists()) {
            System.out.println("No existe socios.txt");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(";");
                socios.add(new Socio(d[0], d[1], d[2], d[3], d[4], d[5]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPistas() {
        File f = new File(FPISTAS);
        if (!f.exists()) {
            System.out.println("No existe pistas.txt");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(";");
                pistas.add(new Pista(d[0], d[1], d[2],
                        Boolean.parseBoolean(d[3])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarReservas() {
        File f = new File(FRESERVAS);
        if (!f.exists()) {
            System.out.println("No existe reservas.txt");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(";");
                reservas.add(new Reserva(
                        d[0], d[1], d[2],
                        LocalDate.parse(d[3]),
                        LocalTime.parse(d[4]),
                        Integer.parseInt(d[5]),
                        Double.parseDouble(d[6])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarSocios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FSOCIOS))) {
            for (Socio s : socios)
                pw.println(s);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void guardarPistas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FPISTAS))) {
            for (Pista p : pistas)
                pw.println(p);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void guardarReservas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FRESERVAS))) {
            for (Reserva r : reservas)
                pw.println(r);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public ArrayList<Socio> getSocios() { return socios; }
    public ArrayList<Pista> getPistas() { return pistas; }
    public ArrayList<Reserva> getReservas() { return reservas; }
}