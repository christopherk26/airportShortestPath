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

import java.util.Iterator;
import java.util.*;

/** A class that implements the ADT directed graph. */
public class DirectedGraph<T extends Comparable<? super T>> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new HashedDictionary<>(31);
        edgeCount = 0;
    } // end default constructor

    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null; // Was addition to
        // dictionary successful?
    } // end addVertex

    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    } // end addEdge

    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex, edgeWeight);
            // connects the vertices and will return true if successful.
        if (result)
            edgeCount++;
        return result;
    } // end addEdge

    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if
        return found;
    } // end hasEdge

    public boolean isEmpty() {
        return vertices.isEmpty();
    } // end isEmpty

    public void clear() {
        vertices.clear();
        edgeCount = 0;
    } // end clear

    public int getNumberOfVertices() {
        return vertices.getSize();
    } // end getNumberOfVertices

    public int getNumberOfEdges() {
        return edgeCount;
    } // end getNumberOfEdges

    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    @Override
    public PriorityQueueInterface<T> getBreadthFirstTraversal(T origin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBreadthFirstTraversal'");
    }

    @Override
    public PriorityQueueInterface<T> getDepthFirstTraversal(T origin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepthFirstTraversal'");
    }

    @Override
    public StackInterface<T> getTopologicalOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTopologicalOrder'");
    }

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getShortestPath'");
    }

    // TODO!
    @Override
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        // we must unvisit all vertices in case this is the second time that we 
        // are calling this method
        // we must also change all of the costs to become 0.
        resetVertices();
        
        boolean done = false;
        EntryPQ frontEntry;
        VertexInterface<T> frontVertex;
        PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();
        priorityQueue.add(new EntryPQ(vertices.getValue(begin), 0, null));
        while (!done && !priorityQueue.isEmpty()) {
            frontEntry = priorityQueue.remove();
            frontVertex = frontEntry.getEndVertex();
            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getWeight());
                frontVertex.setPredecessor(frontEntry.getOriginVertex());
                if (frontVertex.getLabel().equals(end)) {
                    done = true;
                } else {
                    Iterator<VertexInterface<T>> vertexNeighborIterator = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeightIterator = frontVertex.getWeightIterator();
                    while (vertexNeighborIterator.hasNext()) {
                        VertexInterface<T> nextNeighbor = vertexNeighborIterator.next();
                        double weightOfEdgeToNeighbor = edgeWeightIterator.next(); 
                        if (!nextNeighbor.isVisited()) {
                            double nextCost = frontEntry.getWeight() + weightOfEdgeToNeighbor;
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        }
                    }
                }
            }
        }
        // if the end is not found, there is no path there.
        // cycles will not happen because of the flag that we use (visited)
        if (priorityQueue.isEmpty()) {
            return -1;
        }
        // traversal ends, construct cheapest path
        // get the pathcost by looking at the queue
        double pathCost = priorityQueue.peek().getWeight();

        path.push(end);
        VertexInterface<T> vertex = vertices.getValue(end);
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        // the path object is now modified correctly
        
        return pathCost;
    }

    protected class EntryPQ implements Comparable<EntryPQ> {
        private VertexInterface<T> originVertex; // Vertex at end of edge
        private VertexInterface<T> endVertex; // Vertex at end of edge
        private double weight;

        protected EntryPQ(VertexInterface<T> endVertex, double weight, VertexInterface<T> originVertex) {
            this.originVertex = originVertex;
            this.endVertex = endVertex;
            this.weight = weight;
        } // end constructor

        protected VertexInterface<T> getOriginVertex() {
            return originVertex;
        } // end getEndVertex

        protected VertexInterface<T> getEndVertex() {
            return endVertex;
        } // end getEndVertex

        protected double getWeight() {
            return weight;
        } // end getWeight

        public String toString() {
            return "" + weight;
        }

        @Override
        public int compareTo(EntryPQ o) {
            return (int)(weight) - (int)o.weight;
        }
    } // end Edge

} // end DirectedGraph
