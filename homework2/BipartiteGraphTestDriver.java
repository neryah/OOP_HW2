package homework2;

import java.util.*;

/**
 * This class implements a testing driver for BipartiteGraph. The driver
 * manages BipartiteGraphs whose nodes and edges are Strings.
 */
public class BipartiteGraphTestDriver {

    private Map<String, BipartiteGraph<String>> graphs = new HashMap<String, BipartiteGraph<String>>();

    /**
     * @modifies this
     * @effects Constructs a new test driver.
     */
    public BipartiteGraphTestDriver () { }


    /**
     * @requires graphName != null
     * @modifies this
     * @effects Creates a new graph named graphName. The graph is initially
     * 			empty.
     */
    public void createGraph(String graphName) {
        graphs.put(graphName, new BipartiteGraph<String>());
    }


    /**
     * @requires createGraph(graphName)
     *           && nodeName != null
     *           && neither addBlackNode(graphName,nodeName)
     *                  nor addWhiteNode(graphName,nodeName)
     *                      has already been called on this
     * @modifies graph named graphName
     * @effects Adds a black node represented by the String nodeName to the
     * 			graph named graphName.
     */
    public void addBlackNode(String graphName, String nodeName) {
        graphs.get(graphName).addNode(nodeName, new Object(), true);
    }


    /**
     * @requires createGraph(graphName)
     *           && nodeName != null
     *           && neither addBlackNode(graphName,nodeName)
     *                  nor addWhiteNode(graphName,nodeName)
     *                      has already been called on this
     * @modifies graph named graphName
     * @effects Adds a white node represented by the String nodeName to the
     * 			graph named graphName.
     */
    public void addWhiteNode(String graphName, String nodeName) {
        graphs.get(graphName).addNode(nodeName, new Object(), false);
    }


    /**
     * @requires createGraph(graphName)
     *           && ((addBlackNode(parentName) && addWhiteNode(childName))
     *              || (addWhiteNode(parentName) && addBlackNode(childName)))
     *           && edgeLabel != null
     *           && node named parentName has no other outgoing edge labeled
     * 				edgeLabel
     *           && node named childName has no other incoming edge labeled
     * 				edgeLabel
     * @modifies graph named graphName
     * @effects Adds an edge from the node parentName to the node childName
     * 			in the graph graphName. The new edge's label is the String
     * 			edgeLabel.
     */
    public void addEdge(String graphName,
                        String parentName, String childName,
                        String edgeLabel) {
        graphs.get(graphName).addEdge(parentName, childName, edgeLabel);
    }


    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the black nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listBlackNodes(String graphName) {
        List<String> list = new ArrayList<>(graphs.get(graphName).listNodes(true));
        Collections.sort(list);
        return listToString(list);
    }


    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the white nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listWhiteNodes(String graphName) {
        List<String> list = new ArrayList<>(graphs.get(graphName).listNodes(false));
        Collections.sort(list);
        return listToString(list);
    }


    /**
     * @requires createGraph(graphName) && createNode(parentName)
     * @return a space-separated list of the names of the children of
     * 		   parentName in the graph graphName, in alphabetical order.
     */
    public String listChildren(String graphName, String parentName) {
        TreeSet<String> tree = new TreeSet<>(graphs.get(graphName).listChildren(parentName));
        return TreeToString(tree);
    }


    /**
     * @requires createGraph(graphName) && createNode(childName)
     * @return a space-separated list of the names of the parents of
     * 		   childName in the graph graphName, in alphabetical order.
     */
    public String listParents(String graphName, String childName) {
        TreeSet<String> tree = new TreeSet<>(graphs.get(graphName).listParents(childName));
        return TreeToString(tree);
    }


    /**
     * @requires addEdge(graphName, parentName, str, edgeLabel) for some
     * 			 string str
     * @return the name of the child of parentName that is connected by the
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getChildByEdgeLabel(String graphName, String parentName,
                                      String edgeLabel) {
        return graphs.get(graphName).getChildByEdgeLabel(parentName, edgeLabel);
    }

    /**
     * @requires addEdge(graphName, str, childName, edgeLabel) for some
     * 			 string str
     * @return the name of the parent of childName that is connected by the
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getParentByEdgeLabel(String graphName, String childName,
                                       String edgeLabel) {
        return graphs.get(graphName).getParentByEdgeLabel(childName, edgeLabel);
    }

    private static String listToString(List<String> list){
        String spaceSeparated = new String();
        for (String s : list) {
            spaceSeparated += " " + s;
        }
        return spaceSeparated;
    }

    private static String TreeToString(TreeSet<String> tree){
        String spaceSeparated = new String();
        for (String s : tree) {
            spaceSeparated += " " + s;
        }
        return spaceSeparated;
    }

}
