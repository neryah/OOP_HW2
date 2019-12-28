package homework2;

import java.util.*;


/**
 * A Node is an object containing data and identified by a label.
 * It can have in (parent) and out (child) edges, and contains all the information about it's edges.
 * It can be classified as black or white.
 */
public class Node<T> {

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
        boolean noNull = _outEdges != null && _inEdges != null && _childrenLabels != null &&
                _parentLabels != null && _childrenLabelsSet != null && _parentLabelsSet != null;
        boolean goodSizes = _outEdges.size() == _childrenLabels.size() && _inEdges.size() == _parentLabels.size() &&
                _outEdges.size() == _childrenLabelsSet.size() && _inEdges.size() == _parentLabelsSet.size();
        return noNull && goodSizes;
    }

    private T _label;
    private Object _data;
    private boolean _isBlack;

    private Map<T, Node<T>> _outEdges = new HashMap<>();
    private Map<T, Node<T>> _inEdges = new HashMap<>();
    private Collection<T> _childrenLabels = new ArrayList<>(); //assignment requirements - return this in O(1)
    private Collection<T> _parentLabels = new ArrayList<>();   //assignment requirements - return this in O(1)
    private Collection<T> _childrenLabelsSet = new HashSet<>();
    private Collection<T> _parentLabelsSet = new HashSet<>();

    /**
     * @effects constructor of Node, initiate with all the data of the node itself: label, data, isBlack.
     */
    public Node(T label, Object data, boolean isBlack) {
        _data = data;
        _label = label;
        _isBlack = isBlack;
        assert checkRep();
    }

    /**
     * @return true if the node is black, false otherwise.
     */
    public boolean isBlack() {
        assert checkRep();
        return _isBlack;
    }

    /**
     * @return the data object of this.
     */
    public Object getData() {
        assert checkRep();
        return _data;
    }

    /**
     * @modifies this data.
     * @effects the data in this is set to 'data'.
     */
    public void setData(Object data) {
        assert checkRep();
        _data = data;
        assert checkRep();
    }

    /**
     * @return the label object of this.
     */
    public T getLabel() {
        assert checkRep();
        return _label;
    }

    /**
     * @return out edges for children, Maps edge label of type T to child Node.
     */
    public Map<T, Node<T>> getChildrenEdges() {
        assert checkRep();
        return _outEdges;
    }

    /**
     * @return in edges for parents, Maps edge label of type T to parent Node.
     */
    public Map<T, Node<T>> getParentsEdges() {
        assert checkRep();
        return _inEdges;
    }

    /**
     * @return a collection of children nodes labels.
     */
    public Collection<T> getChildrenLabels() {
        assert checkRep();
        return _childrenLabels;
    }

    /**
     * @return a collection of parent nodes labels.
     */
    public Collection<T> getParentsLabels() {
        assert checkRep();
        return _parentLabels;
    }

    /**
     * @return a collection of children nodes labels.
     */
    public Collection<T> getChildrenLabelsSet() {
        assert checkRep();
        return _childrenLabelsSet;
    }

    /**
     * @return a collection of parent nodes labels.
     */
    public Collection<T> getParentsLabelsSet() {
        assert checkRep();
        return _parentLabelsSet;
    }

    /**
     * @modifies All neighbors of this Node.
     * @effects removes this node connections from all his child\parents, so it can be destruct.
     */
    public void removeNode() {
        assert checkRep();
        for (Map.Entry<T, Node<T>> edge : _outEdges.entrySet()) {
            edge.getValue()._inEdges.remove(edge.getKey());
            edge.getValue()._parentLabels.remove(_label);
            edge.getValue()._parentLabelsSet.remove(_label);
        }
        for (Map.Entry<T, Node<T>> edge : _inEdges.entrySet()) {
            edge.getValue()._outEdges.remove(edge.getKey());
            edge.getValue()._childrenLabels.remove(_label);
            edge.getValue()._childrenLabelsSet.remove(_label);
        }
        assert checkRep();
    }

    /**
     * @modifies this edges.
     * @effects removes the edge if exist from node child and this.
     */
    public void removeChild(T edgeLabel) {
        assert checkRep();
        Node<T> toRemove = _outEdges.remove(edgeLabel);
        if(toRemove != null){
            toRemove._inEdges.remove(edgeLabel);
            toRemove._parentLabels.remove(_label);
            toRemove._parentLabelsSet.remove(_label);
            _childrenLabels.remove(toRemove._label);
            _childrenLabelsSet.remove(toRemove._label);
        }
        assert checkRep();
    }

    /**
     * @modifies this and neighbor edges.
     * @effects removes the edge if exist from node parent and this.
     */
    public void removeParent(T edgeLabel) {
        assert checkRep();
        Node<T> toRemove = _inEdges.remove(edgeLabel);
        if(toRemove != null){
            toRemove._outEdges.remove(edgeLabel);
            toRemove._childrenLabels.remove(_label);
            _parentLabels.remove(toRemove._label);
            toRemove._childrenLabelsSet.remove(_label);
            _parentLabelsSet.remove(toRemove._label);
        }
        assert checkRep();
    }

    /**
     * @return true iff 'edgeLabel' exist as an edge to
     * child in case of out==true, otherwise to parent.
     */
    public boolean edgeExist(T edgeLabel, boolean out){
        return out? _outEdges.containsKey(edgeLabel) : _inEdges.containsKey(edgeLabel);
    }

    /**
     * @modifies this children labels, children edges.
     * @effects an edge is drawn from this to child iff parameters are'nt null and
     * 'edgeLabel' is not exist in other child and this direct edge not exist with other label
     */
    public void addChild(T edgeLabel, Node<T> child) {
        assert checkRep();
        if (child != null && edgeLabel != null && !_outEdges.containsKey(edgeLabel)
                && !_childrenLabelsSet.contains(child.getLabel())) {
            _childrenLabels.add(child.getLabel());
            _childrenLabelsSet.add(child.getLabel());
            _outEdges.put(edgeLabel, child);
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
        if (parent != null && edgeLabel != null && !_inEdges.containsKey(edgeLabel)
                && !_parentLabelsSet.contains(parent.getLabel())) {
            _parentLabels.add(parent.getLabel());
            _parentLabelsSet.add(parent.getLabel());
            _inEdges.put(edgeLabel, parent);
        }
        assert checkRep();
    }
}