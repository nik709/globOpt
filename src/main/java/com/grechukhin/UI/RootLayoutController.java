package com.grechukhin.UI;

import com.grechukhin.methods.BaseMethod;
import com.grechukhin.methods.BustMethod;
import com.grechukhin.methods.PiavskyMethod;
import com.grechukhin.methods.StronginMethod;
import com.grechukhin.models.Error;
import com.grechukhin.models.FunctionInterface;
import com.grechukhin.models.ModelFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

public class RootLayoutController {

    @FXML
    private TextField R;
    @FXML
    private TextField source;
    @FXML
    private TextField eps;
    @FXML
    private ListView parameters;
    @FXML
    private TextField rightX;
    @FXML
    private TextField leftX;
    @FXML
    private TextField functionText;

    private FunctionInterface functionModel;
    private Map<Double, Double> fValues = new TreeMap<>();
    private BaseMethod method;
    private Plot mPlot;

    @FXML
    public void initialize() {
        functionModel = (FunctionInterface) ModelFactory.getInstance().createModel(FunctionInterface.class);
        functionModel.registerListener(new FunctionInterface.FunctionListener() {
            @Override
            public void onFunctionChanged() {
                functionText.setText(functionModel.getFunctionAsString());
                leftX.setDisable(false);
                rightX.setDisable(false);
                source.setDisable(false);
                eps.setDisable(false);
                R.setDisable(false);
            }

            @Override
            public void onFinishCalculation(double x, double y) {
                fValues.put(new BigDecimal(x).setScale(2, RoundingMode.HALF_UP).doubleValue(), y);
                if (mPlot != null) {
                    mPlot.addBluePoint(x, y);
                }
            }

            @Override
            public void onFinishCalculationAll(Map<Double, Double> values) {
                drawValue(values);
            }
        });

        functionText.setDisable(true);
        functionText.setText("Your function");

        leftX.setText(String.valueOf(FunctionInterface.defaultLeftX));
        leftX.setDisable(true);
        rightX.setText(String.valueOf(FunctionInterface.defaultRight));
        rightX.setDisable(true);
        source.setText(String.valueOf(FunctionInterface.defaultSource));
        source.setDisable(true);
        eps.setText(String.valueOf(FunctionInterface.defaultEpsilon));
        eps.setDisable(true);
        R.setText(String.valueOf(FunctionInterface.defaultR));
        R.setDisable(true);

        parameters.getItems().clear();
        parameters.getItems().add("PARAMETERS:");
    }

    public void close(ActionEvent actionEvent) {
        functionModel.unregisterAll();
        System.exit(Error.OK.getCode());
    }

    public void openFunctionLayout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/InputFunctionLayout.fxml"));

            Pane inputPane = loader.load();

            loader.<InputFunctionLayoutController>getController().setFunction(functionModel);

            Stage stage = new Stage();
            stage.setTitle("INPUT FUNCTION");
            stage.setScene(new Scene(inputPane, 270, 200));
            stage.show();

        } catch (IOException e) {

        }
    }

    public void setMethod(ActionEvent actionEvent) {
        double a = Double.parseDouble(leftX.getText());
        double b = Double.parseDouble(rightX.getText());
        int sourceI = Integer.valueOf(source.getText());
        double epsilon = Double.valueOf(eps.getText());
        double r = Double.valueOf(R.getText());
        switch (((MenuItem)actionEvent.getSource()).getId()) {
            case "bust":
                method = new BustMethod(
                        a, b
                        , sourceI, epsilon
                ).setFunction(functionModel);
                break;
            case "piavsky":
                method = new PiavskyMethod(
                        a, b
                        , epsilon, sourceI, r
                ).setFunction(functionModel);
                break;
            case "strongin":
                method = new StronginMethod(a, b
                        , epsilon, sourceI, r
                ).setFunction(functionModel);
            default:
                break;
        }
    }

    public void callMethod(ActionEvent actionEvent) {
        functionModel.setInterval(Double.parseDouble(leftX.getText()), Double.parseDouble(rightX.getText()));
        functionModel.calculateAll(0.1);
        parameters.getItems().clear();
        if (method != null) {
            method.call();
            if (mPlot != null) {
                mPlot.addRedPoint(
                        method.getOpt().getX(),
                        method.getOpt().getY()
                );
            }
            parameters.getItems().add("Method:\n\t" + method.getClass().getSimpleName());
            for (Map.Entry<String, Object> entry : method.getParams().entrySet()) {
                parameters.getItems().add(entry.getKey() + ":\n\t" + entry.getValue().toString());
            }
            parameters.getItems().add("Iterations:\n\t" + method.getTrials().size());
            parameters.getItems().add("argmin:\n\t" + method.getOpt().getX());
            parameters.getItems().add("min:\n\t" + method.getOpt().getY());

        }
    }

    private void drawValue(Map<Double, Double> values) {
        double low = Double.parseDouble(leftX.getText());
        double high = Double.parseDouble(rightX.getText());
        Axes axes = new Axes(
                500, 400,
                -10, 10, 1,
                -10, 10, 1
        );
        Plot plot = new Plot(
                values::get,
                low, high, .1,
                axes
        );


        StackPane layout = new StackPane(plot);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(255, 255, 255);");

        Stage stage = new Stage();

        stage.setTitle("y = " + functionModel.getFunctionAsString());
        stage.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        stage.show();

        mPlot = plot;
    }

    public void onDelete(ActionEvent actionEvent) {
        initialize();
    }
}
