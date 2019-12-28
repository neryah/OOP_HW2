package homework2;

import java.util.*;

/**
 * A bipartite graph is a directed graph with two types (colors) of nodes: white and black,
 * Any edge connects two nodes, one of every type.
 * Any node has different label.
 * All edges starting from the same node has different labels.
 * All edges ending at the same node has different labels.
 * All the labels has the same immutable type, which has equal and hash function.
 */

public class BipartiteGraph<T> {

    /**
     * Abstraction function:	BipartiteGraph is a bipartite graph where every node contains it's
     * 							label, data and color. The nodes stored in 'nodes' map from label to node.
     * 						    Every node contains all the information about in and out edges.
     */

    /**
     * Rep. Invariant:	for all edges touching node V:
     * 						V.color != otherEndOfEdges.color
     * 	    				for every parent w of v:
     * 				             v is child of w
     * 	    				for every child w of v:
     * 				             v is parent of w
     */

    private boolean checkRep() {
        for(Node<T> node : _nodes.values()) {
            for (Map.Entry<T, Node<T>> edge : node.getChildrenEdges().entrySet()) {
                assert edge.getValue().getParentsEdges().get(edge.getKey()) == node;
                assert edge.getValue().isBlack() != node.isBlack();
            }
            for (Map.Entry<T, Node<T>> edge : node.getParentsEdges().entrySet()) {
                assert edge.getValue().getChildrenEdges().get(edge.getKey()) == node;
                assert edge.getValue().isBlack() != node.isBlack();
            }
        }
        return true;
    }

    private Map<T, Node<T>> _nodes = new HashMap<>();

    /**
     * @return a new empty instance of this.
     */
    public BipartiteGraph() {
        assert checkRep();
    }

    /**
     * @return the object in node 'label'. if not found, null is returned.
     */
    public Node<T> findNode(T label) {
        assert checkRep();
        return _nodes.get(label);
    }

    /**
     * @modifies this nodes.
     * @effects adds label to the graph's nodes.
     * @return true iff the node was added now.
     */
    public boolean addNode(T label, Object data, boolean isBlack) {
        assert checkRep();
        if (label == null || findNode(label) != null) {
            assert checkRep();
            return false;
        }
        return _nodes.put(label, new Node<T>(label, data, isBlack)) == null;
    }

    /**
     * @modifies this nodes.
     * @effects removes label's node from the graph.
     * @return a boolean value representing if the label was removed.
     */
    public boolean removeNode(T label) {
        assert checkRep();
        Node<T> toRemove = _nodes.get(label);
        if(toRemove != null){
            toRemove.removeNode();
            _nodes.remove(label);
            assert checkRep();
            return true;
        }
        assert checkRep();
        return false;
    }

    /**
     * @modifies this nodes and edges.
     * @effects adds label to the graph's edges.
     * @return true iff the edge from->to was added now or before,
     * notice, if this edge was added before with different name - the old name will stay
     */
    public boolean addEdge(T from, T to, T label) {
        assert checkRep();
        Node<T> parent = findNode(from);
        Node<T> child = findNode(to);
        if(parent != null && child != null && !parent.edgeExist(label, true) &&
                !child.edgeExist(label, false) && parent.getLabel() != child.getLabel() &&
                parent.isBlack() != child.isBlack()){
            parent.addChild(label, child);
            child.addParent(label, parent);
            assert checkRep();
            return true;
        }
        assert checkRep();
        return false;
    }

    /**
     * @modifies this nodes and edges.
     * @effects removes edge from node's in\out edges as specified if exist.
     */
    public void removeEdge(T edge, T nodeLabel, boolean out) {
        assert checkRep();
        Node<T> from = findNode(nodeLabel);
        if(from != null){
            if(out){
                from.removeChild(edge);
            }
            else{
                from.removeParent(edge);
            }
        }
        assert checkRep();
    }

    /**
     * @return a collection of the graph's black or white nodes labels.
     */
    public Collection<T> listNodes(boolean isBlack) {
        assert checkRep();
        Collection<T> container = new ArrayList<>();
        for (Node<T> val : _nodes.values()) {
            if (val.isBlack() == isBlack) {
                container.add(val.getLabel());
            }
        }
        assert checkRep();
        return container;
    }

    /**
     * @return an immutable collection of the labels of the parent's children.
     * empty collection in case there is no node 'parent'
     */
    public Collection<T> listChildren(T parent) {
        assert checkRep();
        Collection<T> container = new ArrayList<>();
        Node<T> p = findNode(parent);
        if (p != null) {
            container = p.getChildrenLabels();
        }
        assert checkRep();
        return Collections.unmodifiableCollection(container);
    }

    /**
     * @return an immutable collection of the labels of the child's parents.
     * empty collection in case there is no node 'child'
     */
    public Collection<T> listParents(T child) {
        assert checkRep();
        Collection<T> container = new ArrayList<>();
        Node<T> c = findNode(child);
        if (c != null) {
            container = c.getParentsLabels();
        }
        assert checkRep();
        return Collections.unmodifiableCollection(container);
    }

    /**
     * @return an immutable collection of the labels of the parent's children.
     * empty collection in case there is no node 'parent'
     */
    public Collection<T> SetChildren(T parent) {
        assert checkRep();
        Collection<T> container = new HashSet<>();
        Node<T> p = findNode(parent);
        if (p != null) {
            container = p.getChildrenLabelsSet();
        }
        assert checkRep();
        return Collections.unmodifiableCollection(container);
    }

    /**
     * @return an immutable collection of the labels of the child's parents.
     * empty collection in case there is no node 'child'
     */
    public Collection<T> SetParents(T child) {
        assert checkRep();
        Collection<T> container = new HashSet<>();
        Node<T> c = findNode(child);
        if (c != null) {
            container = c.getParentsLabelsSet();
        }
        assert checkRep();
        return Collections.unmodifiableCollection(container);
    }

    /**
     * @return the child label of the edge. If doesn't exist, null is returned.
     */
    public T getChildByEdgeLabel(T parent, T edgeLabel) {
        assert checkRep();
        Node<T> p = findNode(parent);
        if(p != null){
            Node<T> child = p.getChildrenEdges().get(edgeLabel);
            assert checkRep();
            return child != null ? child.getLabel() : null;
        }
        return null;
    }

    /**
     * @return the parent label of the edge. If doesn't exist, null is returned.
     */
    public T getParentByEdgeLabel(T child, T edgeLabel) {
        assert checkRep();
        Node<T> c = findNode(child);
        if(c != null){
            Node<T> parent = c.getParentsEdges().get(edgeLabel);
            assert checkRep();
            return parent != null ? parent.getLabel() : null;
        }
        return null;
    }

    /**
     * @return the data in the node with the given label. If doesn't exist, null is returned.
     */
    public Object getNodeData(T label) {
        assert checkRep();
        Node<T> node = findNode(label);
        assert checkRep();
        return node != null ? node.getData() : null;
    }

    /**
     * @modifies a node in this.
     * @effects if node with the label exist, it's set to store the data given.
     */
    public void setNodeData(T label, Object data) {
        assert checkRep();
        Node<T> node = findNode(label);
        if (node != null) {
            node.setData(data);
        }
        assert checkRep();
    }

    /**
     * @modifies this.
     * @effects resets the graph.
     */
    public void clear() {
        assert checkRep();
        _nodes.clear();
        assert checkRep();
    }

}
