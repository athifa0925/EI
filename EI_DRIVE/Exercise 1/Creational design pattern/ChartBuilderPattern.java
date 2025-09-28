import java.util.ArrayList;
import java.util.List;

class Chart {
    private String title;
    private String xAxisLabel;
    private String yAxisLabel;
    private List<String> dataSeries;
    private String chartType;
    private String colorScheme;

    private Chart(ChartBuilder builder) {
        this.title = builder.title;
        this.xAxisLabel = builder.xAxisLabel;
        this.yAxisLabel = builder.yAxisLabel;
        this.dataSeries = builder.dataSeries;
        this.chartType = builder.chartType;
        this.colorScheme = builder.colorScheme;
    }

    public void displayConfiguration() {
        System.out.println("Chart Configuration:");
        System.out.println("Title: " + title);
        System.out.println("X-Axis Label: " + xAxisLabel);
        System.out.println("Y-Axis Label: " + yAxisLabel);
        System.out.println("Data Series: " + dataSeries);
        System.out.println("Chart Type: " + chartType);
        System.out.println("Color Scheme: " + colorScheme);
    }

    public static class ChartBuilder {
        private String title;
        private String xAxisLabel;
        private String yAxisLabel;
        private List<String> dataSeries;
        private String chartType;
        private String colorScheme;

        public ChartBuilder() {
            this.dataSeries = new ArrayList<>();
        }

        public ChartBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ChartBuilder setXAxisLabel(String xAxisLabel) {
            this.xAxisLabel = xAxisLabel;
            return this;
        }

        public ChartBuilder setYAxisLabel(String yAxisLabel) {
            this.yAxisLabel = yAxisLabel;
            return this;
        }

        public ChartBuilder addDataSeries(String series) {
            this.dataSeries.add(series);
            return this;
        }

        public ChartBuilder setChartType(String chartType) {
            this.chartType = chartType;
            return this;
        }

        public ChartBuilder setColorScheme(String colorScheme) {
            this.colorScheme = colorScheme;
            return this;
        }

        public Chart build() {
            return new Chart(this);
        }
    }
}

public class ChartBuilderPattern {
    public static void main(String[] args) {
       
        Chart barChart = new Chart.ChartBuilder()
                .setTitle("Sales Performance")
                .setXAxisLabel("Months")
                .setYAxisLabel("Revenue (USD)")
                .addDataSeries("Jan: 5000")
                .addDataSeries("Feb: 7000")
                .addDataSeries("Mar: 6000")
                .setChartType("Bar")
                .setColorScheme("Blue")
                .build();

        System.out.println("Bar Chart Configuration:");
        barChart.displayConfiguration();

        System.out.println("\n-----------------\n");

        Chart lineChart = new Chart.ChartBuilder()
                .setTitle("Website Traffic")
                .setXAxisLabel("Days")
                .setYAxisLabel("Visitors")
                .addDataSeries("Day 1: 100")
                .addDataSeries("Day 2: 150")
                .addDataSeries("Day 3: 200")
                .setChartType("Line")
                .setColorScheme("Green")
                .build();

        System.out.println("Line Chart Configuration:");
        lineChart.displayConfiguration();
    }
}