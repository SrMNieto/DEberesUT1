package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class PistaFormView extends GridPane {

    public PistaFormView(ClubDeportivo club) {
        //GridPane
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        // campos texto
        TextField tfId = new TextField();
        TextField tfDeporte = new TextField();
        TextField tfDescripcion = new TextField();
        CheckBox cbDisponible = new CheckBox("Disponible");
        Button btnCrear = new Button("Crear");

        //añadir filas al GridPane
        addRow(0, new Label("idPista*"), tfId);
        addRow(1, new Label("Deporte"), tfDeporte);
        addRow(2, new Label("Descripción"), tfDescripcion);
        addRow(3, new Label("Operativa"), cbDisponible);
        add(btnCrear, 1, 4);

        //accion boton crear
        btnCrear.setOnAction(e -> {
            try {
                String id = tfId.getText();
                String deporte = tfDeporte.getText();
                String descripcion = tfDescripcion.getText();
                boolean disponible = cbDisponible.isSelected();

                Pista p = new Pista(id, deporte, descripcion, disponible);
                boolean ok = club.altaPista(p);

                if(ok){
                    showInfo("Pista insertada correctamente");
                    tfId.clear();
                    tfDeporte.clear();
                    tfDescripcion.clear();
                    cbDisponible.setSelected(false);
                } else {
                    showError("El ID de la pista ya existe");
                }

            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        });
    }

    //error
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }

    //information
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
