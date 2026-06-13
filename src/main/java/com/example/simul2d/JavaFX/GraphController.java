package com.example.simul2d.JavaFX;

import java.util.HashMap;
import java.util.Map;

import com.example.simul2d.Entities.Entities;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Controller for the population and infected LineCharts in the UI.
 *
 * <p>Receives aggregated graph values via {@link #graphStep(double, Map, Map)}
 * and appends them to the appropriate charts. Maintains per-series labels
 * displayed beneath each chart to show current counts by entity type.
 */
public class GraphController implements NeedsGraphValues {

    @FXML
    private LineChart<Number, Number> populationChart;

    @FXML
    private LineChart<Number, Number> infectedChart;

    @FXML
    private Label populationLabel;

    @FXML
    private Label infectedLabel;

    @FXML
    private HBox populationSeriesBox;

    @FXML
    private HBox infectedSeriesBox;

    private final Map<String, Label> populationSeriesLabels = new HashMap<>();
    private final Map<String, Label> infectedSeriesLabels = new HashMap<>();

    // Pour retrouver une série par son nom (le type de moisissure)
    private final Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
    private final Map<String, XYChart.Series<Number, Number>> seriesMapInfected = new HashMap<>();

    @FXML

    public void initialize() {
        // Configuration facultative de l'axe X (pas forcément nécessaire)
        NumberAxis xAxis = (NumberAxis) populationChart.getXAxis();
        populationChart.setLegendVisible(false);
        xAxis.setForceZeroInRange(false);
        if (infectedChart != null) {
            infectedChart.setLegendVisible(false);
            NumberAxis xAxis2 = (NumberAxis) infectedChart.getXAxis();
            xAxis2.setForceZeroInRange(false);
        }
        if (populationLabel != null) populationLabel.setText("Population: 0");
        if (infectedLabel != null) infectedLabel.setText("Infected: 0");
        if (populationSeriesBox != null) populationSeriesBox.getChildren().clear();
        if (infectedSeriesBox != null) infectedSeriesBox.getChildren().clear();
    }

    /**
     * Called each time the simulation produces new aggregate values for the graphs.
     *
     * @param timeStep current simulation time step
     * @param populationsWeight map of entity type → aggregated growth/population
     * @param infectedCells map of entity type → number of infected cells
     */
    

    @Override
    public void graphStep(double timeStep, Map<Entities, Integer> populationsWeight, Map<Entities, Integer> infectedCells){
        addDataPoint(timeStep, populationsWeight);
        addInfectedDataPoint(timeStep, infectedCells);
        // Mettre à jour les labels de totaux
        if (populationLabel != null && populationsWeight != null) {
            int total = populationsWeight.values().stream().mapToInt(Integer::intValue).sum();
            populationLabel.setText("Population: " + total);
        }
        if (infectedLabel != null && infectedCells != null) {
            int totalInf = infectedCells.values().stream().mapToInt(Integer::intValue).sum();
            infectedLabel.setText("Infected: " + totalInf);
        }
    }
    /**
     * Adds a data point for every entity type to the population chart.
     *
     * @param timeStep the current simulation timestep
     * @param populations map of entity type → value to plot
     */

    private void addDataPoint(double timeStep, Map<Entities, Integer> populations) {
        addDataPointToChart(populationChart, seriesMap, timeStep, populations);
    }

    private void addInfectedDataPoint(double timeStep, Map<Entities, Integer> infected) {
        addDataPointToChart(infectedChart, seriesMapInfected, timeStep, infected);
    }

    private void addDataPointToChart(LineChart<Number, Number> chart,
                                     Map<String, XYChart.Series<Number, Number>> map,
                                     double timeStep,
                                     Map<Entities, Integer> data) {
        if (chart == null || data == null) return;
        System.out.println("Adding data point to chart at time " + timeStep + ": " + data.toString());
        for (Map.Entry<Entities, Integer> entry : data.entrySet()) {
            String moldType = entry.getKey().toString();
            int count = entry.getValue();

            XYChart.Series<Number, Number> series = map.get(moldType);
            if (series == null) {
                series = new XYChart.Series<>();
                series.setName(moldType);
                chart.getData().add(series);

                chart.applyCss();
                chart.layout();
                Node sNode = series.getNode();
                if (sNode != null) {
                    sNode.setStyle("-fx-stroke: " + entry.getKey().getColorHex() + ";");
                }
                map.put(moldType, series);

                // create a label for this series and add to appropriate HBox
                Label lbl = new Label(moldType + ": " + count);
                lbl.setMaxWidth(Double.MAX_VALUE);
                lbl.setAlignment(Pos.CENTER);
                HBox.setHgrow(lbl, Priority.ALWAYS);
                if (chart == populationChart) {
                    populationSeriesLabels.put(moldType, lbl);
                    if (populationSeriesBox != null) populationSeriesBox.getChildren().add(lbl);
                } else if (chart == infectedChart) {
                    infectedSeriesLabels.put(moldType, lbl);
                    if (infectedSeriesBox != null) infectedSeriesBox.getChildren().add(lbl);
                }
            } else {
                // update corresponding label
                Label lbl = null;
                if (chart == populationChart) lbl = populationSeriesLabels.get(moldType);
                else if (chart == infectedChart) lbl = infectedSeriesLabels.get(moldType);
                if (lbl != null) {
                    lbl.setText(moldType + ": " + count);
                    lbl.setMaxWidth(Double.MAX_VALUE);
                    lbl.setAlignment(Pos.CENTER);
                    HBox.setHgrow(lbl, Priority.ALWAYS);
                }
            }
            series.getData().add(new XYChart.Data<>(timeStep, count));
        }
    }

    /**
     * Clears both charts, per-series labels and resets summary labels.
     * Use when starting a fresh simulation or resetting the UI.
     */
    public void clear() {
        populationChart.getData().clear();
        seriesMap.clear();
        if (infectedChart != null) {
            infectedChart.getData().clear();
            seriesMapInfected.clear();
        }
        if (populationLabel != null) populationLabel.setText("Population: 0");
        if (infectedLabel != null) infectedLabel.setText("Infected: 0");
        if (populationSeriesBox != null) populationSeriesBox.getChildren().clear();
        if (infectedSeriesBox != null) infectedSeriesBox.getChildren().clear();
        populationSeriesLabels.clear();
        infectedSeriesLabels.clear();
    }
}
