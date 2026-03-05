package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CambiarDisponibilidadView extends GridPane {
    public CambiarDisponibilidadView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        // comboBox con las pistas 
        ComboBox<Pista> cbPistas = new ComboBox<>();
        cbPistas.getItems().setAll(club.getPistas());

        CheckBox chkDisponible = new CheckBox("Disponible");
        Button btnCambiar = new Button("Aplicar");

        addRow(0, new Label("Pista"), cbPistas);
        addRow(1, new Label("Estado"), chkDisponible);
        add(btnCambiar, 1, 2);

        //boton Aplicar
        btnCambiar.setOnAction(e -> {
            Pista p = cbPistas.getValue();
            if(p == null){
                showError("Selecciona una pista primero");
                return;
            }

            // cambiar disponibilidad
            club.cambiarDisponibilidadPista(p.getIdPista(), chkDisponible.isSelected());
            showInfo("Disponibilidad actualizada");

            // actualiza
            cbPistas.getItems().setAll(club.getPistas());
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