package com.grechukhin.UI;

import com.grechukhin.models.FunctionInterface;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class InputFunctionLayoutController implements EventHandler {

    @FXML
    private TextField function;
    @FXML
    private Pane pane;

    private FunctionInterface functionModel;

    @Override
    public void handle(Event event) {
        Object source = event.getSource();
        if (source == null) {
            return;
        }
        if ("Your function".equals(function.getText())) {
            function.setText("");
        }
        function.setText(function.getText() + ((Button) source).getText());
        functionModel.setFunction(function.getText());
    }

    @FXML
    public void initialize() {
        for (Node node : pane.getChildren()) {
            if (node instanceof Button) {
                if (((Button) node).getText().equals("OK")) {
                    continue;
                }
                ((Button) node).setOnAction(this);
            }
            if (node instanceof TextField) {
                node.setDisable(true);
                ((TextField) node).setText("Your function");
            }
        }
    }

    public void onOK(ActionEvent actionEvent) {
        pane.getScene().getWindow().hide();
    }

    public void setFunction(FunctionInterface functionModel) {
        this.functionModel = functionModel;
    }
}
