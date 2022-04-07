import java.util.*;
import java.lang.Math;

public class Gravity {
    public String planet;
    public double result;

    public Gravity(String planet) {
        this.planet = planet;
        this.result = calculate();
    }

    public double calculate() {
        double mass = getMass() * Math.pow(10, 24);
        double earthMass = 5.97 * Math.pow(10, 24);
        double distance = getDistance() * Math.pow(10, 11) * 1.496;
        return result = (6.674) * Math.pow(10, -11) * mass * earthMass / Math.pow(distance, 2);
    }

    public double getDistance() {
        if (planet.equals("Mercury")) {
            return 0.61;
        }
        if (planet.equals("Venus")) {
            return 0.28;
        }
        if (planet.equals("Mars")) {
            return 0.52;
        }
        if (planet.equals("Jupiter")) {
            return 4.2;
        }
        if (planet.equals("Saturn")) {
            return 8.52;
        }
        if (planet.equals("Uranus")) {
            return 18.21;
        }
        if (planet.equals("Neptune")) {
            return 29.09;
        }
        else {
            return 38.5;
        }
    }

    public double getMass() {
        if (planet.equals("Mercury")) {
            return 0.33;
        }
        if (planet.equals("Venus")) {
            return 4.87;
        }
        if (planet.equals("Mars")) {
            return 0.642;
        }
        if (planet.equals("Jupiter")) {
            return 1898.0;
        }
        if (planet.equals("Saturn")) {
            return 568.0;
        }
        if (planet.equals("Uranus")) {
            return 86.8;
        }
        if (planet.equals("Neptune")) {
            return 102.0;
        }
        else {
            return 0.013;
        }
    }

    public String toString() {
        return "The force of gravity between Earth and " + planet + " based on their average distance is: " + result + " Newtons.";
    }

    public static void test() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Find the force of attraction between Earth and what planet? (not Earth, Pluto is an honorary planet according to me): ");
        String answer = sc.next();
        Gravity force = new Gravity(answer);
        System.out.println(force);
    }

    public static void main(String[] args) {
        test();
    }
}
