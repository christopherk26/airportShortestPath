//
// Name:       Kurdoghlian, Christopher
// Project:    4
// Due:        April 30, 2024
// Course:     cs-2400-03-sp24
//
// Description:
//             In this project we create an airport app where we can find the optimal path between
//             two airports, given their names and locations. We can also query Airports using their code. 
//

import java.util.Scanner;
import java.io.*;

public class AirportApp {
    public static void main(String[] args) throws IOException {
        System.out.println("Airports v0.24 by C. Kurdoghlian");
        System.out.println();

        GraphInterface<String> routeMap = new DirectedGraph<>();

        Scanner airportsFile = new Scanner(new File("airports.csv"));
        // by doing tests, 31 is the ideal number for the dictionary size to
        // obtain zero collisions using our hashing method.
        DictionaryInterface<String, String> airportsDict = new HashedDictionary<>(31);
        String[] airportArr = new String[2];
        while (airportsFile.hasNext()) {
            airportArr = airportsFile.nextLine().split(",");
            airportsDict.add(airportArr[0], airportArr[1]);
            routeMap.addVertex(airportArr[0]);
        }

        Scanner distancesFile = new Scanner(new File("distances.csv"));
        String[] distancesArr = new String[3];
        while (distancesFile.hasNext()) {
            distancesArr = distancesFile.nextLine().split(",");
            routeMap.addEdge(distancesArr[0], distancesArr[1], Double.parseDouble(distancesArr[2]));
        }

        Scanner kb = new Scanner(System.in);
        boolean hasQuit = false;
        String command;
        String code;
        String fromTo;
        String[] fromToArr = new String[2];
        do {
            System.out.print("Command? ");
            command = kb.nextLine();
            if (command.equals("H")) {
                System.out.println("Q Query the airport information by entering the airport code.");
                System.out.println("D Find the minimum distance between two airports.");
                System.out.println("H Display this message.");
                System.out.println("E Exit.");
            } else if (command.equals("E")) {
                hasQuit = true;
            } else if (command.equals("D")) {
                System.out.print("Airport codes from to? ");
                fromTo = kb.nextLine();
                fromToArr = fromTo.split(" ");
                if (!airportsDict.contains(fromToArr[0]) || !airportsDict.contains(fromToArr[1])) {
                    System.out.println("Airport code unknown");
                } else {
                    StackInterface<String> route = new ArrayStack<>();
                    double distance = routeMap.getCheapestPath(fromToArr[0], fromToArr[1], route);
                    if (distance != -1) {
                        System.out.println("The minimum distance between " + fromToArr[0] + " " + fromToArr[1] + ": " + distance);
                        while (!route.isEmpty()) {
                            String loc = route.pop();
                            System.out.println(airportsDict.getValue(loc) + " - (" + loc + ")");
                        }
                    } else {
                        System.out.println("no route found");
                    }
                }
            } else if (command.equals("Q")) {
                System.out.print("Airport code? ");
                code = kb.nextLine();
                if (!airportsDict.contains(code)) {
                    System.out.println("Airport code unknown");
                } else {
                    System.out.println(airportsDict.getValue(code));
                }
            } else {
                System.out.println("Invalid command");
            }
        } while (!hasQuit);
    }
}