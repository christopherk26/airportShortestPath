Main photo for reference to the airports and connections that exist

![Screenshot 2024-09-18 at 12 11 44 PM](https://github.com/user-attachments/assets/fa73ccc5-077b-4e05-9e31-c65c3e9fd3e5)


Section 1: Project Specification & Implementation
The following are all of the interfaces that were necessary for the completion of this project. It is important to note that these were all needed for the getCheapestPath method inside of the DirectedGraph class, and the HashedDictionary was also used in the main method in AirportApp to be able to create the airport querying system. The optimal size of the dictionary was 31, as this prevents collisions from occurring and thus allows for an O(1) time to be able to retrieve the full airport name from just the three-letter airport IATA code. 

VertexInterface.java
StackInterface.java
PriorityQueueInterface.java
DictionaryInterface.java
GraphInterface,java
BasicGraphInterface.java
GraphAlgorithmsInterface.java

And these interfaces were implemented with the classes:

Vertex.java
HeapPriorityQueue.java
HashedDictionary.java
DirectedGraph.java
ArrayStack.java

The javadoc for the interfaces are shown below. 


![Screenshot 2024-09-18 at 12 10 53 PM](https://github.com/user-attachments/assets/57f0fca3-5794-47a5-bfcd-d85105c189cd)
![Screenshot 2024-09-18 at 12 10 59 PM](https://github.com/user-attachments/assets/0d91e31d-61c1-4406-b136-324a2ccf5f2d)
![Screenshot 2024-09-18 at 12 11 07 PM](https://github.com/user-attachments/assets/1d2df534-9a83-41b8-ba48-4b2bfe2f3fd6)
![Screenshot 2024-09-18 at 12 11 11 PM](https://github.com/user-attachments/assets/ef439c6c-2100-43e7-a65e-e87c55323d32)
![Screenshot 2024-09-18 at 12 11 16 PM](https://github.com/user-attachments/assets/59ee076f-f1e5-4f31-9d1b-1f78bcf11447)
![Screenshot 2024-09-18 at 12 11 21 PM](https://github.com/user-attachments/assets/4745b32a-4c8b-4529-90d4-6716331ec5a7)



Section 2: Specification of the getCheapestPath method. 

Some background on the data structures used is necessary before a complete discussion of the algorithm itself. To start, we need a priority queue, which is implemented with a maxheap. The priority queue has EntryPQ (which is an inner class of directedGraph.java) objects within it. This means that the maximum entry is at the top of the heap. To get shorter distances (which have priority due to us wanting to find the shortest path) at the top of the heap, the compareTo() method of the EntryPQ object subtracts the weight of the current entry from the weight of the entry being compared to (which is this the “right hand side”), thus making shorter distances have higher priority. Every entryPQ object has an originVertex, endVertex, and weight. OriginVertex and endVertex are Vertex objects. Vertex objects have neighbors, which are stored in ArrayLists provided by java. These neighbors are of type edge, which is in inner class and has fields of Vertex and weight, which is a double. These can be iterated through using iterators that allow us to traverse through neighbors and their weights. Vertices, of course, also have labels, flags for isVisited to indicate if it is visited, and also have predecessors (which are a vertex and can be set using a setter method) which come in handy when looking to create the entire path between two given airports. Finally, we have the stack ADT which is used for us to pop off at the end to get the entire path between the two airports. 

Now we will discuss the getCheapestPath algorithm itself:
I will first show a photo of the algorithm and then discuss the algorithm. 

![Screenshot 2024-09-18 at 12 11 35 PM](https://github.com/user-attachments/assets/1605e291-6ebf-4829-a1f4-00633f01d57a)


To start, the algorithm resets the vertices using a helper method, which iterates through all of the vertices, (which are stored in a dictionary inside of the DirectedGraph class, with the key being the type T and the value being a Vertex of type T) and un-visits them, sets their costs to zero, and sets their predecessors to zero. Next, the path cost is initialized to -1 such that if the algorithm does not return a length, it returns -1 to indicate that the two airports are not connected. We have the “done” flag set to false, and a reference to a vertex that is the front vertex. The priorityQueue is set up, which contains entryPQ objects. The final part of the setup is to add the first Vertex that we start at, and this is a new EntryPQ object with an originVertex of null, weight of zero, and endVertex with the Vertex from the dictionary that is the value of the key “begin”, which is a parameter of our function. 
Now, continuing to the main while loop, which checks each time if the algorithm is “done” or if the priorityQueue is not empty. Each time, the frontEntry is removed and its reference is saved, and its endVertex reference is saved as well. If that frontVertex has not been visited yet, we visit it, and we get the cost to the weight of the front entry. We then set its predecessor to the originVertex of the frontEntry, which is an EntryPQ object. If the frontVertex’s label is equal to our “end”, which is the parameter for the label of the end airport, then we set done equal to true, and we get the path cost by getting the frontEntry EntryPQ object's weight. Otherwise, we get an iterator of the frontVertex’s neighbors, and an iterator of the frontVertex’s edgeWeights. We traverse through them, and if they are not visited, we compute the next cost by taking the weight of that Edge and the weight of the frontEntry. In other words, we are adding the edge’s distance to our current distance. We then add a new EntryPQ object to the priorityQueue, with that neighbor being the endVertex, the nextCost being the cost, and the originVertex being the current frontVertex. Once this while loop ends, we move onto the stack portion.
The stack portion begins with pushing onto the stack (which is a parameter of the type T) the “end” parameter, which is of type T which will be a string. This makes sense since the bottom most thing on the stack must be the last location, being the destination. We then get the get value of the end from the dictionary, which is a Vertex<T> object. Since this vertex will have a predecessor, which is another vertex, we use a while loop with while (vertex.hasPredecessor()) and we get the predecessor, set it to vertex, and push this new vertex onto the stack. We continue to do so until we reach the starting Vertex, which has no predecessor, and we return the pathCost to end the algorithm. Thus, we have returned the final pathCost, and all the main method must do is print that out, and then using the modified stack, simply pop off of the stack until the stack is empty. The first thing off the stack will be the starting location, which is exactly what we want and need. 

Section 3: Testing methodology
Querying for airports was tested by typing in airport codes and getting the airport names for all airports. Testing for the shortest path was done by drawing out a map on a piece of paper with the airport locations and labeling them by adding arrows and their lengths, being the weights of the edges. This was done digitally, too, as seen below.
![Screenshot 2024-09-18 at 12 11 44 PM](https://github.com/user-attachments/assets/c86d3796-48c0-467b-b760-8af2df0af9f0)


 Then, I would run the algorithm between any and all two locations and see if the distance was calculated correctly by comparing that to the map that I drew. It is important to note that airports like LAX → LAS were tested that have several paths that can be taken between the two, to ensure that the algorithm was indeed finding the shortest path. 

Airport codes from to? LAX LAS
The minimum distance between LAX LAS is 236:
Los Angeles CA - Los Angeles International (LAX)
Las Vegas NV - Harry Reid International (LAS)

It is also important to note that all directed edges were tested using a testing method provided called show() which printed out all of the connected edges like so:

DCA -> ORD
ORD -> MSP
IAH -> DCA
DEN -> LAS
MSP -> DEN
LAS -> IAH ORD
DFW -> IAH
LAX -> PHX LAS DEN
PHX -> DFW

There are nine rows (thus nine airports) and twelve “endpoints” on the right side of the arrows, as expected from our data files. This was able to verify the success in the ability for the main program to read vertices as airport locations, add them to the map, and then add the connected edges that are directed through iterating through the distances file. Doing several tests back to back ensures that the algorithm is resetting the vertices successfully. Finally, routes that are not connected were tested to ensure that the algorithm successfully returns -1, and thus the main prints that they are not connected.

Section 4: Lessons Learned.
I would say the biggest lesson learned from this project was how to pace myself with working on such a large project, containing 13 source code files. I also learned quite a bit about putting different classes together and making them all work together in one method that uses different data structures to achieve its goal. I also became more proficient in things like the iterator, which was used much more in this project with different classes and inner classes. The concept of the inner class was well understood, too, due to this project. Overall, the complexity of this project was quite large, and thus I learned quite a lot from this project.

