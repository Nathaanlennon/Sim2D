package com.example.simul2d.JavaFX;

import java.util.HashMap;
import java.util.Map;

import com.example.simul2d.Entities.Entities;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


//TODO faire une fonction refreshUI qui va etre appellé à chaque étape de la simulation et qui va mettre à jour le graphique en appelant addDataPoint avec les nouvelles données de population
//TODO on va prendre une hashmap des valeurs de tot growth pour chaque type de mold
//TODO faire les bouttons sauvegarde et load


public class GraphController implements NeedsGraphValues {

    @FXML
    private LineChart<Number, Number> populationChart;

    // Pour retrouver une série par son nom (le type de moisissure)
    private final Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Configuration facultative de l'axe X (pas forcément nécessaire)
        NumberAxis xAxis = (NumberAxis) populationChart.getXAxis();
        xAxis.setForceZeroInRange(false);
    }

    /**
     * Réinitialise le graphique (utile pour une nouvelle simulation).
     */
    public void clear() {
        populationChart.getData().clear();
        seriesMap.clear();
    }

    /**
     * Ajoute un point de données pour tous les types de moisissures.
     * @param timeStep      le numéro du pas de temps
     * @param populations   une map associant le nom du type de moule → sa population
     */
    @Override
    public void addDataPoint(double timeStep, Map<Entities, Integer> populations) {
        System.out.println("Adding data point at time " + timeStep + ": " + populations.toString());
        for (Map.Entry<Entities, Integer> entry : populations.entrySet()) {
            String moldType = entry.getKey().toString();
            int count = entry.getValue();

            XYChart.Series<Number, Number> series = seriesMap.get(moldType);
            if (series == null) {
                series = new XYChart.Series<>();
                series.setName(moldType);
                populationChart.getData().add(series);
                seriesMap.put(moldType, series);
            }
            series.getData().add(new XYChart.Data<>(timeStep, count));
        }
    }
}
