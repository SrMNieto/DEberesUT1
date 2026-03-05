package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class CancelarReservaView extends GridPane {

    public CancelarReservaView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        // combobbox con reservas 
        ComboBox<Reserva> cbReservas = new ComboBox<>();
        cbReservas.getItems().setAll(club.getReservas());

        Button btnCancelar = new Button("Cancelar reserva");

        addRow(0, new Label("Reserva"), cbReservas);
        add(btnCancelar, 1, 1);

        // boton cancelar
        btnCancelar.setOnAction(e -> {
            Reserva r = cbReservas.getValue();
            if(r == null){
                showError("Selecciona una reserva primero");
                return;
            }

            club.cancelarReserva(r.getIdReserva());
            showInfo("Reserva cancelada correctamente");

            // actualizar 
            cbReservas.getItems().setAll(club.getReservas());
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