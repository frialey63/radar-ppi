package org.pjp.radar.ppi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.pjp.radar.Radar;
import org.pjp.radar.db.Plot;
import org.pjp.radar.db.PlotDatabase;
import org.pjp.radar.db.PlotExtractor;
import org.pjp.radar.sim.TargetRequestor;
import org.pjp.radar.util.Constants;
import org.pjp.radar.util.MathUtils;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RadarPPI extends Application {

    static {
        TargetRequestor.request();

        PlotExtractor.extract();
    }

    private static final class PlotPoint2D {
        final int targetSize;

        final Point2D point;

        int decay;

        public PlotPoint2D(int targetSize, Point2D point, int decay) {
            super();
            this.targetSize = targetSize;
            this.point = point;
            this.decay = decay;
        }
    }

    private static final double FULL_CIRCLE = 2 * Math.PI;

    private static final double QUADRANT = Math.PI / 4;

    private static final int SCAN_PERSISTENCE = 200;

    private static final double ALPHA = 0.8;

    private static final double GREEN = 0.9;

    private static final Color GREENISH = new Color(0, GREEN, 0, ALPHA);

    private static final Font FONT = new Font("Arial", 10);

    private static final int TEXT_OFFSET = 2;

    private static int width = 800;
    private static int halfWidth = width / 2;
    private static int radius = halfWidth;

    private ChangeListener<? super Number> widthChangeListener;
    private ChangeListener<? super Number> heightChangeListener;

    @Override
    public void start(Stage stage) {
        double centreX = halfWidth;
        double centreY = halfWidth;

        Circle scope = new Circle(centreX, centreY, radius);
        RadialGradient radialGradient = new RadialGradient(0, 0.1, centreX, centreY, radius, false, CycleMethod.NO_CYCLE, new Stop(0, Color.GREEN), new Stop(1, Color.BLACK));
        scope.setFill(radialGradient);

        Canvas canvas = new Canvas(width, width);

        StackPane root = new StackPane();
        root.getChildren().add(scope);
        root.getChildren().add(canvas);

        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                width = (int) Math.round((double) newSceneWidth);
                halfWidth = width / 2;
                radius = halfWidth;

                Circle newScope = new Circle(centreX, centreY, radius);
                RadialGradient radialGradient = new RadialGradient(0, 0.1, centreX, centreY, radius, false, CycleMethod.NO_CYCLE, new Stop(0, Color.GREEN), new Stop(1, Color.BLACK));
                newScope.setFill(radialGradient);

                root.getChildren().remove(0);
                root.getChildren().add(0, newScope);
            }
        };

        Scene scene = new Scene(root);
        scene.widthProperty().addListener(listener);
        scene.heightProperty().addListener(listener);

        stage.setTitle("Radar PPI");
        stage.setScene(scene);
        stage.show();

        double titleHeight = stage.getHeight() - stage.getWidth();

        widthChangeListener = (observable, oldValue, newValue) -> {
            stage.heightProperty().removeListener(heightChangeListener);
            stage.setHeight(newValue.doubleValue() + titleHeight);
            stage.heightProperty().addListener(heightChangeListener);
        };
        heightChangeListener = (observable, oldValue, newValue) -> {
            stage.widthProperty().removeListener(widthChangeListener);
            stage.setWidth(newValue.doubleValue() - titleHeight);
            stage.widthProperty().addListener(widthChangeListener);
        };

        stage.widthProperty().addListener(widthChangeListener);
        stage.heightProperty().addListener(heightChangeListener);

        CanvasTask task = new CanvasTask();
        task.valueProperty()
                .addListener((ObservableValue<? extends Canvas> observable, Canvas oldValue, Canvas newValue) -> {
                    root.getChildren().remove(oldValue);
                    root.getChildren().add(newValue);
                });

        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        executorService.submit(task);
    }

    private static class CanvasTask extends Task<Canvas> {

        private final Radar radar = new Radar("ASR 9", "Northrop Grumman", "AN/GPN-27", 2_800_000_000L, 325, 0.000_001, 1_300_000, 60, 450, 1.4, 12.5);

        private final double radsPerPulse = radar.getRadsPerPulse();

        private final long sleep = sleepForDelta(radar.getSecsPerScan(), radsPerPulse);

        private double theta = 0;

        @Override
        protected Canvas call() throws Exception {
            List<Point2D> scans = new ArrayList<>();
            List<PlotPoint2D> plots = new ArrayList<>();

            boolean running = true;

            while (running) {
                Canvas canvas = new Canvas(width, width);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                double r = radius;
                double xc = halfWidth;
                double yc = halfWidth;

                double x = xc + r * Math.sin(theta);
                double y = yc - r * Math.cos(theta);

                drawPlot(gc, xc, yc, theta, plots);
                drawPlotPersistence(gc, xc, yc, plots);
                drawScan(gc, xc, yc, x, y, scans);
                drawScanPersistence(gc, xc, yc, scans);
                drawReticle(gc, radar.getInstrumentedRange(), 5, true);

                theta = MathUtils.mod(theta + radsPerPulse, FULL_CIRCLE);

                updateValue(canvas);

                Thread.sleep(sleep);
            }

            return null;
        }

        private void drawReticle(GraphicsContext gc, int maxRange, int numRings, boolean annotate) {
            gc.setStroke(Color.LIGHTSLATEGRAY);

            // draw range rings

            double r = radius;
            double rStep = radius / numRings;

            for (int i = 0; i < numRings; i++) {
                double x = width - radius - r;
                double y = width - radius - r;
                double width = 2 * r;

                gc.strokeOval(x, y, width, width);

                r -= rStep;
            }

            // draw bearing directions

            double x1 = halfWidth;
            double y1 = 0;
            double x2 = halfWidth;
            double y2 = width;

            gc.strokeLine(x1, y1, x2, y2);

            x1 = 0;
            y1 = halfWidth;
            x2 = width;
            y2 = halfWidth;

            gc.strokeLine(x1, y1, x2, y2);

            double cos45 = Math.cos(QUADRANT);
            double sin45 = Math.sin(QUADRANT);

            x1 = halfWidth - radius * cos45;
            y1 = halfWidth - radius * sin45;
            x2 = halfWidth + radius * cos45;
            y2 = halfWidth + radius * sin45;

            gc.strokeLine(x1, y1, x2, y2);

            x1 = halfWidth - radius * cos45;
            y1 = halfWidth + radius * sin45;
            x2 = halfWidth + radius * cos45;
            y2 = halfWidth - radius * sin45;

            gc.strokeLine(x1, y1, x2, y2);

            // draw annotations

            if (annotate) {
                gc.setFont(FONT);

                double x = halfWidth;
                double y = halfWidth - rStep;

                double rangeStep = maxRange / numRings;
                double range = rangeStep;

                for (int i = 1; i < numRings; i++) {
                    gc.strokeText(String.format("%2.0f nm", range), x + TEXT_OFFSET, y - TEXT_OFFSET);

                    y -= rStep;
                    range += rangeStep;
                }
            }
        }

        private void drawScanPersistence(GraphicsContext gc, double xc, double yc, List<Point2D> scans) {
            int scanPersistence = SCAN_PERSISTENCE;
            double alphaStep = ALPHA / scanPersistence;

            double alpha = ALPHA;

            for (Point2D p : scans) {
                Color color = new Color(0, GREEN, 0, alpha);
                gc.setStroke(color);
                gc.strokeLine(xc, yc, p.getX(), p.getY());
                alpha -= alphaStep;
            }

            if (scans.size() >= scanPersistence) {
                scans.remove(scans.size() - 1);
            }
        }

        private void drawScan(GraphicsContext gc, double xc, double yc, double x, double y, List<Point2D> scans) {
            gc.setStroke(GREENISH);
            gc.strokeLine(xc, yc, x, y);

            scans.add(0, new Point2D(x, y));
        }

        private void drawPlotPersistence(GraphicsContext gc, double xc, double yc, List<PlotPoint2D> plots) {
            int pulsesPerScan = radar.getPulsesPerScan();
            double alphaStep = ALPHA / pulsesPerScan;

            for (Iterator<PlotPoint2D> iterator = plots.iterator(); iterator.hasNext();) {
                PlotPoint2D plotPoint2D = iterator.next();

                double w = plotPoint2D.targetSize;
                double h = plotPoint2D.targetSize;

                double alpha = plotPoint2D.decay * alphaStep;
                Color color = new Color(0, GREEN, 0, alpha);

                gc.setFill(color);
                gc.fillOval(plotPoint2D.point.getX(), plotPoint2D.point.getY(), w, h);

                plotPoint2D.decay--;

                if (plotPoint2D.decay == 0) {
                    iterator.remove();
                }
            }
        }

        private void drawPlot(GraphicsContext gc, double xc, double yc, double theta, List<PlotPoint2D> plots) {
            double radarRange = Constants.NM_TO_M * radar.getInstrumentedRange();
            double radarSector = Math.toRadians(radar.getBeamwidth());

            gc.setFill(GREENISH);

            for (Plot plot : PlotDatabase.getInstance().getPlots()) {
                double thetaMin = theta - radarSector / 2.0;
                double thetaMax = theta + radarSector / 2.0;

                if (thetaMin <= plot.getBearing() && plot.getBearing() <= thetaMax) {
                    if (plot.getRange() <= radarRange) {
                        double r = radius * plot.getRange() / radarRange;

                        double x = xc + r * Math.sin(theta);
                        double y = yc - r * Math.cos(theta);

                        int targetSize = plot.getTargetSize().getPixels();

                        double w = targetSize;
                        double h = targetSize;

                        double xb = x - w / 2.0;
                        double yb = y - h / 2.0;

                        gc.fillOval(xb, yb, w, h);

                        plots.add(new PlotPoint2D(targetSize, new Point2D(x, y), radar.getPulsesPerScan()));
                    }
                }
            }
        }

        private long sleepForDelta(double scanPeriod, double delta) {
            return (long) (1000 * scanPeriod * delta / (2 * Math.PI));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}