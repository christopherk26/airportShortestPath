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

/** An interface of methods that create, manipulate, and process a graph. */
public interface GraphInterface<T extends Comparable<? super T>> extends BasicGraphInterface<T>,
        GraphAlgorithmsInterface<T> {
} // end GraphInterface
