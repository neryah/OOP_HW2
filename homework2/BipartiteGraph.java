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
     * 	    				    for every child w of v:
     * 				                v is parent of w
     */

    private boolean checkRep() {
        for(Node<T> node : nodes.values()) {
            for (Map.Entry<T, Node<T>> edge : node.outEdges.entrySet()) {
                assert edge.getValue().inEdges.get(edge.getKey()) == node;
                assert edge.getValue().isBlack() != node.isBlack();
            }
            for (Map.Entry<T, Node<T>> edge : node.inEdges.entrySet()) {
                assert edge.getValue().outEdges.get(edge.getKey()) == node;
                assert edge.getValue().isBlack() != node.isBlack();
            }
        }
        return true;
    }

    private Map<T, Node<T>> nodes = new HashMap<>();

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
        return nodes.get(label);
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
        return nodes.put(label, new Node<T>(label, data, isBlack)) == null;
    }

    /**
     * @modifies this nodes.
     * @effects removes label's node from the graph.
     * @return a boolean value representing if the label was removed.
     */
    public boolean removeNode(T label) {
        assert checkRep();
        Node<T> toRemove = nodes.get(label);
        if(toRemove != null){
            toRemove.removeNode();
            nodes.remove(label);
            assert checkRep();
            return true;
        }
        assert checkRep();
        return false;
    }

    /**
     * @modifies this nodes and edges.
     * @effects adds label to the graph's edges.
     * @return a boolean value iff the edge from->to was added now or before,
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
        for (Node<T> val : nodes.values()) {
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
        this.checkRep();
        nodes.clear();
        this.checkRep();
    }

    /**
     * A Node is an object containing data and identified by a label.
     * It can have in (parent) and out (child) edges, and contains all the information about it's edges.
     * It can be classified as black or white.
     */
    private class Node<T> {

        /**
         * Abstraction function:	Node is a labeled node. The label is from the same type of the instance
         *                          of BipartiteGraph - T. In addition, any node contains:
         *                          'isBlack' - true for black, otherwise - white. 'data' - data of type Object.
         *                          'outEdges' - out edges for children, Maps edge label of type T to child Node.
         *                          'inEdges' - in edges for parents, Maps edge label of type T to parent Node.
         *                          'childrenLabels' - List of all the children labels.
         *                          'parentLabels' - List of all the children labels.
         *                          'childrenLabelsSet' - Set of all the children labels.
         *                          'parentLabelsSet' - Set of all the children labels.
         */

        /**
         * Rep. Invariant:(outEdges, inEdges, childrenLabels, parentLabels, childrenLabelsSet, parentLabelsSet) != null
         *                  outEdges.size() == childrenLabels.size() && inEdges.size() == parentLabels.size() &&
         *                  outEdges.size() == childrenLabelsSet.size() && inEdges.size() == parentLabelsSet.size()
         */

        private boolean checkRep() {
            boolean noNull = outEdges != null && inEdges != null && childrenLabels != null &&
                    parentLabels != null && childrenLabelsSet != null && parentLabelsSet != null;
            boolean goodSizes = outEdges.size() == childrenLabels.size() && inEdges.size() == parentLabels.size() &&
                    outEdges.size() == childrenLabelsSet.size() && inEdges.size() == parentLabelsSet.size();
            return noNull && goodSizes;
        }

        private T label;
        private Object data;
        private boolean isBlack;

        private Map<T, Node<T>> outEdges = new HashMap<>();
        private Map<T, Node<T>> inEdges = new HashMap<>();
        private Collection<T> childrenLabels = new ArrayList<>(); //assignment requirements - return this in O(1)
        private Collection<T> parentLabels = new ArrayList<>();   //assignment requirements - return this in O(1)
        private Collection<T> childrenLabelsSet = new HashSet<>();
        private Collection<T> parentLabelsSet = new HashSet<>();

        /**
         * @effects constructor of Node, initiate with all the data of the node itself: label, data, isBlack.
         */
        public Node(T label, Object data, boolean isBlack) {
            this.data = data;
            this.label = label;
            this.isBlack = isBlack;
            assert checkRep();
        }

        /**
         * @return true if the node is black, false otherwise.
         */
        public boolean isBlack() {
            assert checkRep();
            return isBlack;
        }

        /**
         * @return the data object of this.
         */
        public Object getData() {
            assert checkRep();
            return data;
        }

        /**
         * @modifies this data.
         * @effects the data in this is set to 'data'.
         */
        public void setData(Object data) {
            assert checkRep();
            this.data = data;
            assert checkRep();
        }

        /**
         * @return the label object of this.
         */
        public T getLabel() {
            assert checkRep();
            return label;
        }

        /**
         * @return out edges for children, Maps edge label of type T to child Node.
         */
        public Map<T, Node<T>> getChildrenEdges() {
            assert checkRep();
            return outEdges;
        }

        /**
         * @return in edges for parents, Maps edge label of type T to parent Node.
         */
        public Map<T, Node<T>> getParentsEdges() {
            assert checkRep();
            return inEdges;
        }

        /**
         * @return a collection of children nodes labels.
         */
        public Collection<T> getChildrenLabels() {
            assert checkRep();
            return childrenLabels;
        }

        /**
         * @return a collection of parent nodes labels.
         */
        public Collection<T> getParentsLabels() {
            assert checkRep();
            return parentLabels;
        }

        /**
         * @return a collection of children nodes labels.
         */
        public Collection<T> getChildrenLabelsSet() {
            assert checkRep();
            return childrenLabelsSet;
        }

        /**
         * @return a collection of parent nodes labels.
         */
        public Collection<T> getParentsLabelsSet() {
            assert checkRep();
            return parentLabelsSet;
        }

        /**
         * @modifies All neighbors of this Node.
         * @effects removes this node connections from all his child\parents, so it can be destruct.
         */
        public void removeNode() {
            assert checkRep();
            for (Map.Entry<T, Node<T>> edge : outEdges.entrySet()) {
                edge.getValue().inEdges.remove(edge.getKey());
                edge.getValue().parentLabels.remove(label);
                edge.getValue().parentLabelsSet.remove(label);
            }
            for (Map.Entry<T, Node<T>> edge : inEdges.entrySet()) {
                edge.getValue().outEdges.remove(edge.getKey());
                edge.getValue().childrenLabels.remove(label);
                edge.getValue().childrenLabelsSet.remove(label);
            }
            assert checkRep();
        }

        /**
         * @modifies this edges.
         * @effects removes the edge if exist from node child and this.
         */
        public void removeChild(T edgeLabel) {
            assert checkRep();
            Node<T> toRemove = outEdges.remove(edgeLabel);
            if(toRemove != null){
                toRemove.inEdges.remove(edgeLabel);
                toRemove.parentLabels.remove(label);
                toRemove.parentLabelsSet.remove(label);
                childrenLabels.remove(toRemove.label);
                childrenLabelsSet.remove(toRemove.label);
            }
            assert checkRep();
        }

        /**
         * @modifies this and neighbor edges.
         * @effects removes the edge if exist from node parent and this.
         */
        public void removeParent(T edgeLabel) {
            assert checkRep();
            Node<T> toRemove = inEdges.remove(edgeLabel);
            if(toRemove != null){
                toRemove.outEdges.remove(edgeLabel);
                toRemove.childrenLabels.remove(label);
                parentLabels.remove(toRemove.label);
                toRemove.childrenLabelsSet.remove(label);
                parentLabelsSet.remove(toRemove.label);
            }
            assert checkRep();
        }

        /**
         * @return true iff 'edgeLabel' exist as an edge to
         * child in case of out==true, otherwise to parent.
         */
        public boolean edgeExist(T edgeLabel, boolean out){
            return out? outEdges.containsKey(edgeLabel) : inEdges.containsKey(edgeLabel);
        }

        /**
         * @modifies this children labels, children edges.
         * @effects an edge is drawn from this to child iff parameters are'nt null and
         * 'edgeLabel' is not exist in other child and this direct edge not exist with other label
         */
        public void addChild(T edgeLabel, Node<T> child) {
            assert checkRep();
            if (child != null && edgeLabel != null && !outEdges.containsKey(edgeLabel)
                    && !childrenLabelsSet.contains(child.getLabel())) {
                childrenLabels.add(child.getLabel());
                childrenLabelsSet.add(child.getLabel());
                outEdges.put(edgeLabel, child);
            }
            assert checkRep();
        }

        /**
         * @modifies this parent labels, parent edges.
         * @effects an edge is drawn from parent to this iff parameters are'nt null and
         * 'edgeLabel' is not exist in other parent and this direct edge not exist with other label
         */
        public void addParent(T edgeLabel, Node<T> parent) {
            assert checkRep();
            if (parent != null && edgeLabel != null && !inEdges.containsKey(edgeLabel)
                    && !parentLabelsSet.contains(parent.getLabel())) {
                parentLabels.add(parent.getLabel());
                parentLabelsSet.add(parent.getLabel());
                inEdges.put(edgeLabel, parent);
            }
            assert checkRep();
        }
    }

}
