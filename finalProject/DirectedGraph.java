import java.util.Iterator;

/** A class that implements the ADT directed graph. */
public class DirectedGraph<T> implements GraphInterface<T> {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCheapestPath'");
    }

} // end DirectedGraph
