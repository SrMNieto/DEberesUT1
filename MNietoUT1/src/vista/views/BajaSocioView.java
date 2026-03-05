package vista.views;

import servicio.ClubDeportivo;
import modelo.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class BajaSocioView extends GridPane {

    public BajaSocioView(ClubDeportivo club) {
        // Config
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);


        ComboBox<Socio> cbSocios = new ComboBox<>();
        cbSocios.getItems().addAll(club.getSocios());

       
        cbSocios.setCellFactory(c -> new ListCell<>() {
            @Override
            protected void updateItem(Socio s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? "" : s.getIdSocio() + " - " + s.getNombre());
            }
        });
        cbSocios.setButtonCell(cbSocios.getCellFactory().call(null));

        Button btnBaja = new Button("Dar de baja");
        addRow(0, new Label("Socio"), cbSocios);
        add(btnBaja, 1, 1);

        // accion del boton
        btnBaja.setOnAction(e -> {
            Socio s = cbSocios.getValue();
            if (s == null) {
                showError("Selecciona un socio primero");
                return;
            }

            //reservas futuras
            boolean tieneReservasFuturas = club.getReservas().stream()
                    .anyMatch(r -> r.getIdSocio().equals(s.getIdSocio()) && r.getFecha().isAfter(LocalDate.now()));
            if (tieneReservasFuturas) {
                showError("No se puede dar de baja: tiene reservas futuras");
                return;
            }

            // baja socio
            club.bajaSocio(s.getIdSocio());
            showInfo("Socio dado de baja correctamente");

            // actualizar ComboBox
            cbSocios.getItems().setAll(club.getSocios());
        });
    }

    // mostrar alertas
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