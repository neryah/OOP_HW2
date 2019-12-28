package homework2;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * a simulator is a class for simulating a system represented by a bipartite graph of channels and filters.
 */
public class Simulator<L, D> {

    /**
     * Abstraction function:	a simulator holds a bipartite graph,
     *                          it able to alter this graph using it's API and simulate it.
     *                          simulator implement the API needed by system of channels and filters.
     */

    /**
     * Rep. Invariant:	kept in the graph itself.
     */

    private BipartiteGraph<L> _graph = new BipartiteGraph<>();

    /**
     * @return a new instance of this labeled 'simLabel'.
     */
    public Simulator() {}

    /**
     * @modifies this graph.
     * @effects advances the simulator one time-slot forward.
     */
    @SuppressWarnings("unchecked")//Assumption - _graph.getNodeData(nodeLabel) is instanceof Simulatable<?>
    public void simulate() {
        for (L nodeLabel : _graph.listNodes(true)) {
            ((Simulatable<L>)_graph.getNodeData(nodeLabel)).simulate(_graph);
        }

        for (L nodeLabel : _graph.listNodes(false)) {
            ((Simulatable<L>)_graph.getNodeData(nodeLabel)).simulate(_graph);
        }
    }

    /**
     * @return the data in node 'label'.
     */
    public Object getNodeData(L label) {
        return _graph.getNodeData(label);
    }

    /**
     * @requires nodeData is instanceof Simulatable<?>
     * @modifies this graph.
     * @effects adds the labeled data as a pipe node to graph.
     * @return a boolean value representing if addition was successful.
     */
    public boolean addPipeNode(L nodeLabel, Object nodeData) {
        return _graph.addNode(nodeLabel, nodeData, true);
    }

    /**
     * @requires nodeData is instanceof Simulatable<?>
     * @modifies this graph.
     * @effects adds the labeled data as a filter node to graph.
     * @return a boolean value representing if addition was successful.
     */
    public boolean addFilterNode(L nodeLabel, Object nodeData) {
        return this._graph.addNode(nodeLabel, nodeData, false);
    }

    /**
     * @modifies this graph.
     * @effects creates an edge in the graph.
     * @return true iff the edge from->to was added now or before,
     *      * notice, if this edge was added before with different name - the old name will stay
     */
    public boolean addEdge(L from, L to, L edgeLabel) {
        return _graph.addEdge(from, to, edgeLabel);
    }


    /**
     * Prints the all edges.
     *
     * @effects Prints the all edges.
     */
    public void printAllEdges() {
        StringBuilder allEdges = new StringBuilder("");
        for (L nodeLabel : _graph.listNodes(true)) {
            Node<L> node = _graph.findNode(nodeLabel);
            allEdges.append(node.getParentsEdges().keySet()).append(" ");
            allEdges.append(node.getChildrenEdges().keySet()).append(" ");
        }
        System.out.println(allEdges);
    }
}
