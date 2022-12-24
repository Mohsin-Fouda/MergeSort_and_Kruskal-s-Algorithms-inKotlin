
import java.util.*

internal class Graph(
    private var V: Int, // V-> no. of vertices & E->no.of edges
    E: Int,
) {
    // edge of graph
    internal inner class Edge : Comparable<Edge?> {
        var src = 0
        var dest = 0
        var weight = 0

        
        // sorting of edges by their weight
        override operator fun compareTo(other: Edge?): Int {
            return weight - (other as Edge).weight
        }
    }

    
    // represent a Subset for union-find
    internal inner class Subset {
        var parent = 0
        var rank = 0
    }

    var edge: Array<Edge?> // collection of all edges

    // Creates a graph with V vertices and E edges
    init {
        edge = arrayOfNulls(E)
        for (i in 0 until E) edge[i] = Edge()
    }

    // find set of anelement i
    private fun find(subsets: Array<Subset?>, i: Int): Int {
        // find root and make it parent of i
        if (subsets[i]!!.parent != i) subsets[i]!!.parent = find(subsets, subsets[i]!!.parent)
        return subsets[i]!!.parent
    }

	//union of two sets of x and y 
    private fun union(subsets: Array<Subset?>, x: Int, y: Int) {
        val xroot = find(subsets, x)
        val yroot = find(subsets, y)

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot]!!.rank < subsets[yroot]!!.rank) subsets[xroot]!!.parent =
            yroot else if (subsets[xroot]!!.rank > subsets[yroot]!!.rank) subsets[yroot]!!.parent = xroot else {
            subsets[yroot]!!.parent = xroot
            subsets[xroot]!!.rank++
        }
    }

    // construct MST using Kruskal's algorithm

    fun kruskalMST() {
        
        val result = arrayOfNulls<Edge>(V)        
        var e = 0        
        var i = 0
        while (i < V) {
            result[i] = Edge()
            ++i
        }

        /* Step 1: Sort all the edges in non-decreasing
         order of their weight. If we are not allowed to
         change the given graph, we can create a copy of
         array of edges*/
        Arrays.sort(edge)
        val subsets = arrayOfNulls<Subset>(V)
        i = 0
        while (i < V) {
            subsets[i] = Subset()
            ++i
        }
        
        for (v in 0 until V) {
            subsets[v]!!.parent = v
            subsets[v]!!.rank = 0
        }
        i = 0 

        // Number of edges to be taken is equal to V-1
        while (e < V - 1) {
        	// Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            val nextedge = edge[i++]
            val x = find(subsets, nextedge!!.src)
            val y = find(subsets, nextedge.dest)

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = nextedge
                union(subsets, x, y)
            }
            // Else discard the nextedge
        }

        // print the contents of result[] to display MST
 
        println("Following are the edges in "
                + "the constructed MST")
        var minimumCost = 0
        i = 0
        while (i < e) {
            println(result[i]!!.src.toString() + " -- "
                    + result[i]!!.dest
                    + " == " + result[i]!!.weight)
            minimumCost += result[i]!!.weight
            ++i
        }
        println("MST Cost "
                + minimumCost)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            /* Let us create following weighted graph
				10
			0--------1
			| \	 |
		6| 5\ |15
			|	 \ |
			2--------3
				4	 */
            val v= 4 // Number of vertices in graph
            val e = 5 // Number of edges in graph
            val graph = Graph(v, e)

            // add edge 0-1
            graph.edge[0]!!.src = 0
            graph.edge[0]!!.dest = 1
            graph.edge[0]!!.weight = 10

            // add edge 0-2
            graph.edge[1]!!.src = 0
            graph.edge[1]!!.dest = 2
            graph.edge[1]!!.weight = 6

            // add edge 0-3
            graph.edge[2]!!.src = 0
            graph.edge[2]!!.dest = 3
            graph.edge[2]!!.weight = 5

            // add edge 1-3
            graph.edge[3]!!.src = 1
            graph.edge[3]!!.dest = 3
            graph.edge[3]!!.weight = 15

            // add edge 2-3
            graph.edge[4]!!.src = 2
            graph.edge[4]!!.dest = 3
            graph.edge[4]!!.weight = 4

            // Function call
            graph.kruskalMST()
        }
    }
}
