import java.util.Scanner;

interface TrafficLightState {
    void transitionToNext(TrafficLight context);
    String getCurrentState();
    int getDuration();
}
class RedState implements TrafficLightState {
    private static final int DURATION = 30; 

    @Override
    public void transitionToNext(TrafficLight context) {
        context.setState(new YellowState());
    }

    @Override
    public String getCurrentState() {
        return "Red";
    }

    @Override
    public int getDuration() {
        return DURATION;
    }
}

// Concrete state: Green
class GreenState implements TrafficLightState {
    private static final int DURATION = 30; // seconds

    @Override
    public void transitionToNext(TrafficLight context) {
        context.setState(new RedState());
    }

    @Override
    public String getCurrentState() {
        return "Green";
    }

    @Override
    public int getDuration() {
        return DURATION;
    }
}

// Concrete state: Yellow
class YellowState implements TrafficLightState {
    private static final int DURATION = 5; // seconds

    @Override
    public void transitionToNext(TrafficLight context) {
        context.setState(new GreenState());
    }

    @Override
    public String getCurrentState() {
        return "Yellow";
    }

    @Override
    public int getDuration() {
        return DURATION;
    }
}

// Context class
class TrafficLight {
    private TrafficLightState state;

    public TrafficLight() {
        this.state = new RedState(); // Initial state
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }

    public void transition() {
        state.transitionToNext(this);
    }

    public String getCurrentState() {
        return state.getCurrentState();
    }

    public int getCurrentDuration() {
        return state.getDuration();
    }
}

// Main class to simulate traffic light system
public class TrafficLightStatePattern {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrafficLight trafficLight = new TrafficLight();


        System.out.println("Starting Traffic Light Simulation...");
       
            System.out.println("Current State: " + trafficLight.getCurrentState() +
                               ", Duration: " + trafficLight.getCurrentDuration() + " seconds");
            trafficLight.transition();
            
            System.out.println("Current State: " + trafficLight.getCurrentState() +
                               ", Duration: " + trafficLight.getCurrentDuration() + " seconds");
            trafficLight.transition();
            System.out.println("Current State: " + trafficLight.getCurrentState() +
                               ", Duration: " + trafficLight.getCurrentDuration() + " seconds");
            trafficLight.transition();
        

        scanner.close();
    }
}