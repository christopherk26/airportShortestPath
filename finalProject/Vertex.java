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
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

/** A class of vertices for a graph. */
class Vertex<T> implements VertexInterface<T> {
    private T label;
    private List<Edge> edgeList; // Edges to neighbors
    private boolean visited; // True if visited
    private VertexInterface<T> previousVertex; // On path to this vertex
    private double cost; // Of path to this vertex

    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new ArrayList<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor

    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    } // end connect

    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    } // end hasNeighbor

    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null)) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while
        return result;
    } // end getUnvisitedNeighbor

    @Override
    public boolean equals(Object other) {
        boolean result;
        if ((other == null) || (getClass() != other.getClass()))
            result = false;
        else {
            // The cast is safe within this else clause
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>) other;
            result = label.equals(otherVertex.label);
        } // end if
        return result;
    } // end equals

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) { // Vertices are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            } // end while
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if
        return result;
    } // end connect

    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    } // end getNeighborIterator

    private class NeighborIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;

        private NeighborIterator() {
            edges = edgeList.iterator();
            // creates iterator from the List upon creation of a 
            // NeighborIterator object
        } // end default constructor

        public boolean hasNext() {
            return edges.hasNext();
        } // end hasNext

        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            } else
                throw new NoSuchElementException();
            return nextNeighbor;
        } // end next
    }

    private class WeightIterator implements Iterator<Double> {
        private Iterator<Edge> edges;

        private WeightIterator() {
            edges = edgeList.iterator();
        } // end default constructor

        public boolean hasNext() {
            return edges.hasNext();
        } // end hasNext

        public Double next() {
            double nextWeight = -1;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextWeight = edgeToNextNeighbor.getWeight();
            } else
                throw new NoSuchElementException();
            return nextWeight;
        } // end next
    }

    protected class Edge {
        private VertexInterface<T> vertex; // Vertex at end of edge
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor

        protected Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
            weight = 0;
        } // end constructor

        protected VertexInterface<T> getEndVertex() {
            return vertex;
        } // end getEndVertex

        protected double getWeight() {
            return weight;
        } // end getWeight
    } // end Edge

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {
        visited = true;
    }

    @Override
    public void unvisit() {
        visited = false;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {
        this.cost = newCost;
    }

    @Override
    public double getCost() {
        return cost;
    }
} // end Vertex
