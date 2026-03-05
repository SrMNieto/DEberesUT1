package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaFormView extends GridPane {
    public ReservaFormView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        TextField id = new TextField();
        ComboBox<Socio> cbSocio = new ComboBox<>();
        cbSocio.getItems().setAll(club.getSocios());

        ComboBox<Pista> cbPista = new ComboBox<>();
        cbPista.getItems().setAll(club.getPistas());

        DatePicker fecha = new DatePicker(LocalDate.now());
        TextField hora = new TextField("10:00");
        Spinner<Integer> duracion = new Spinner<>(30, 300, 60, 30);
        TextField precio = new TextField("10.0");
        Button crear = new Button("Reservar");

        addRow(0, new Label("idReserva*"), id);
        addRow(1, new Label("Socio*"), cbSocio);
        addRow(2, new Label("Pista*"), cbPista);
        addRow(3, new Label("Fecha*"), fecha);
        addRow(4, new Label("Hora inicio* (HH:mm)"), hora);
        addRow(5, new Label("Duración (min)"), duracion);
        addRow(6, new Label("Precio (€)"), precio);
        add(crear, 1, 7);

        crear.setOnAction(e -> {
            try {
                //validacin
                if(id.getText().isBlank() || cbSocio.getValue() == null || cbPista.getValue() == null){
                    showError("Completa todos los campos obligatorios");
                    return;
                }

                LocalTime t = LocalTime.parse(hora.getText());

                Reserva r = new Reserva(
                        id.getText(),
                        cbSocio.getValue().getIdSocio(),
                        cbPista.getValue().getIdPista(),
                        fecha.getValue(),
                        t,
                        duracion.getValue(),
                        Double.parseDouble(precio.getText())
                );

                boolean ok = club.crearReserva(r);
                if(ok){
                    showInfo("Reserva creada correctamente");
                    //limpiar campos
                    id.clear();
                    hora.setText("10:00");
                    duracion.getValueFactory().setValue(60);
                    precio.setText("10.0");
                } else {
                    showError("No se pudo crear la reserva: la pista no está disponible");
                }

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}