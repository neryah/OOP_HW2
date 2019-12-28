package homework2;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * BipartiteGraphTest contains JUnit block-box unit tests for BipartiteGraph.
 */
public class BipartiteGraphTest {
    //  Black-box tests

	@Test
    public void testExample() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
        
        //create a graph
        driver.createGraph("graph1");
        
        //add a pair of nodes
        driver.addBlackNode("graph1", "n1");
        driver.addWhiteNode("graph1", "n2");
        
        //add an edge
        driver.addEdge("graph1", "n1", "n2", "edge");
        
        //check neighbors
        assertEquals("wrong black nodes", "n1", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "n2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong children", "n2", driver.listChildren ("graph1", "n1"));
        assertEquals("wrong children", "", driver.listChildren ("graph1", "n2"));
        assertEquals("wrong parents", "", driver.listParents ("graph1", "n1"));
        assertEquals("wrong parents", "n1", driver.listParents ("graph1", "n2"));
    }


    @Test
    public void generalTestDriver() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();

        //create a graph
        driver.createGraph("g1");

        //add some legal nodes
        driver.addBlackNode("g1", "bn1");
        driver.addWhiteNode("g1", "wn1");
        driver.addBlackNode("g1", "bn2");
        driver.addWhiteNode("g1", "wn2");
        driver.addBlackNode("g1", "bn3");
        driver.addWhiteNode("g1", "wn3");
        driver.addBlackNode("g1", "bn4");
        driver.addWhiteNode("g1", "wn4");
        //assert nodes works
        assertEquals("Wrong black nodes", "bn1 bn2 bn3 bn4", driver.listBlackNodes("g1"));
        assertEquals("Wrong white nodes", "wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));
        //add some legal edges
        driver.addEdge("g1","bn1","wn1","b1 to w1");
        driver.addEdge("g1","bn1","wn2","b1 to w2");
        driver.addEdge("g1","bn4","wn4","b4 to w4");
        driver.addEdge("g1","wn4","bn3","w4 to b3");
        driver.addEdge("g1","bn3","wn3","b3 to w3");
        driver.addEdge("g1","wn3","bn2","w3 to b2");
        //assert same nodes
        assertEquals("Wrong black nodes", "bn1 bn2 bn3 bn4", driver.listBlackNodes("g1"));
        assertEquals("Wrong white nodes", "wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));
        //assert edges works
        assertEquals("Wrong parents of wn1", "bn1", driver.listParents("g1", "wn1"));
        assertEquals("Wrong parents of wn2", "bn1", driver.listParents("g1", "wn2"));
        assertEquals("Wrong parents of wn3", "bn3", driver.listParents("g1", "wn3"));
        assertEquals("Wrong parents of wn4", "bn4", driver.listParents("g1", "wn4"));
        assertEquals("Wrong parents of bn1","" , driver.listParents("g1", "bn1"));
        assertEquals("Wrong parents of bn2","wn3" , driver.listParents("g1", "bn2"));
        assertEquals("Wrong parents of bn3","wn4" , driver.listParents("g1", "bn3"));
        assertEquals("Wrong parents of bn4", "", driver.listParents("g1", "bn4"));
        assertEquals("Wrong children of wn1", "", driver.listChildren("g1", "wn1"));
        assertEquals("Wrong children of wn2", "", driver.listChildren("g1", "wn2"));
        assertEquals("Wrong children of wn3", "bn2", driver.listChildren("g1", "wn3"));
        assertEquals("Wrong children of wn4", "bn3", driver.listChildren("g1", "wn4"));
        assertEquals("Wrong children of bn1", "wn1 wn2", driver.listChildren("g1", "bn1"));
        assertEquals("Wrong children of bn2", "", driver.listChildren("g1", "bn2"));
        assertEquals("Wrong children of bn3", "wn3", driver.listChildren("g1", "bn3"));
        assertEquals("Wrong children of bn4", "wn4", driver.listChildren("g1", "bn4"));

        //assert for edges names
        assertEquals("Wrong child of bn1", "wn1", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w1"));
        assertEquals("Wrong child of bn1", "wn2", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w2"));
        assertEquals("Wrong parent of wn1", "bn1", driver.getParentByEdgeLabel("g1", "wn1", "b1 to w1"));
        assertEquals("Wrong parent of wn2", "bn1", driver.getParentByEdgeLabel("g1", "wn2", "b1 to w2"));
        assertEquals("Wrong child of bn1", "wn1", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w1"));
        assertEquals("Wrong child of bn1", "wn2", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w2"));
        assertEquals("Wrong child of bn4", "wn4", driver.getChildByEdgeLabel("g1", "bn4", "b4 to w4"));
        assertEquals("Wrong child of wn4", "bn3", driver.getChildByEdgeLabel("g1", "wn4", "w4 to b3"));
        assertEquals("Wrong parent of wn4", "bn4", driver.getParentByEdgeLabel("g1", "wn4", "b4 to w4"));
        assertEquals("Wrong parent of bn3", "wn4", driver.getParentByEdgeLabel("g1", "bn3", "w4 to b3"));
        assertEquals("Wrong parent of wn3", "bn3", driver.getParentByEdgeLabel("g1", "wn3", "b3 to w3"));
        assertEquals("Wrong parent of bn2", "wn3", driver.getParentByEdgeLabel("g1", "bn2", "w3 to b2"));
    }

    @Test
    public void nodesTestDriver() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();

        //create a graph
        driver.createGraph("g1");

        //add 12 nodes
        for(int j = 0 ; j < 3 ; j++){
            for(int i = 1 ; i < 4 ; i++) {
                driver.addWhiteNode("g1", "wn" + i);
                driver.addBlackNode("g1", "bn" + i);
                driver.addWhiteNode("g1", i + "wn" + i);
                driver.addBlackNode("g1", i + "bn" + i);
            }
        }

        assertEquals("Wrong black nodes", "1bn1 2bn2 3bn3 bn1 bn2 bn3", driver.listBlackNodes("g1"));
        assertEquals("Wrong white nodes", "1wn1 2wn2 3wn3 wn1 wn2 wn3", driver.listWhiteNodes("g1"));
    }


    @Test
    public void edgesTestDriver() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();

        //create a graph
        driver.createGraph("g1");

        //add some legal nodes
        driver.addBlackNode("g1", "bn1");
        driver.addWhiteNode("g1", "wn1");
        driver.addBlackNode("g1", "bn2");
        driver.addWhiteNode("g1", "wn2");
        driver.addBlackNode("g1", "bn3");
        driver.addWhiteNode("g1", "wn3");
        driver.addBlackNode("g1", "bn4");
        driver.addWhiteNode("g1", "wn4");

        //add some legal edges
        driver.addEdge("g1","bn1","wn1","b1 to w1");
        driver.addEdge("g1","bn1","wn2","b1 to w2");
        driver.addEdge("g1","bn4","wn4","b4 to w4");
        driver.addEdge("g1","wn4","bn3","w4 to b3");
        driver.addEdge("g1","bn3","wn3","b3 to w3");
        driver.addEdge("g1","wn3","bn2","w3 to b2");

        //add edges with existing labels but different nodes
        driver.addBlackNode("g1", "extra1");
        driver.addBlackNode("g1", "extra2");
        driver.addWhiteNode("g1", "extra3White");
        driver.addEdge("g1","wn1","extra","label");
        driver.addEdge("g1","wn2","extra1","label");
        driver.addEdge("g1","bn4","extra3White","label");
        driver.addEdge("g1","extra","wn1","label");
        driver.addEdge("g1","extra1","wn2","label");
        driver.addEdge("g1","extra3White","bn2","label");
        driver.addEdge("g1","bn4","wn1","w3 to b2");

        //add illegal edges (same color nodes)
        driver.addEdge("g1","bn1","bn2","b1 to b2");
        driver.addEdge("g1","bn2","bn3","b2 to b3");
        driver.addEdge("g1","wn1","wn2","w1 to w2");
        driver.addEdge("g1","wn3","wn2","w3 to w2");
        driver.addEdge("g1","wn4","wn1","w4 to w1");
        driver.addEdge("g1","bn4","bn3","b4 to b3");



        //add illegal edges (same nodes)
        driver.addEdge("g1", "bn1", "bn1", "b1 to b1");
        driver.addEdge("g1", "wn1", "wn1", "w1 to w1");

        //add illegal edges (non-existing nodes)
        driver.addEdge("g1", "wn1", "BBB", "w1 to BBB");
        driver.addEdge("g1", "AAA", "BBB", "AAA to BBB");
        driver.addEdge("g1", "AAA", "bn1", "AAA to bn1");

        //add illegal edges (existing edges)
        driver.addEdge("g1","bn1","wn1","b1 to w1");
        driver.addEdge("g1","bn1","wn2","b1 to w2");
        driver.addEdge("g1","wn3","bn2","w3 to b2");
        driver.addEdge("g1", "bn1", "wn4", "b1 to w1");


        //check that only legal edges were added
        assertEquals("Wrong black nodes", "bn1 bn2 bn3 bn4 extra1 extra2", driver.listBlackNodes("g1"));
        assertEquals("Wrong white nodes", "extra3White wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));

        assertEquals("Wrong child of bn1", "wn1", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w1"));
        assertEquals("Wrong child of bn1", "wn2", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w2"));
        assertEquals("Wrong child of bn4", "wn4", driver.getChildByEdgeLabel("g1", "bn4", "b4 to w4"));
        assertEquals("Wrong child of wn4", "bn3", driver.getChildByEdgeLabel("g1", "wn4", "w4 to b3"));
        assertEquals("Wrong parent of wn4", "bn4", driver.getParentByEdgeLabel("g1", "wn4", "b4 to w4"));
        assertEquals("Wrong parent of bn3", "wn4", driver.getParentByEdgeLabel("g1", "bn3", "w4 to b3"));
        assertEquals("Wrong parent of wn3", "bn3", driver.getParentByEdgeLabel("g1", "wn3", "b3 to w3"));

        assertEquals("Wrong children of wn1", "", driver.listChildren("g1", "wn1"));
        assertEquals("Wrong children of wn2", "extra1", driver.listChildren("g1", "wn2"));
        assertEquals("Wrong children of wn3", "bn2", driver.listChildren("g1", "wn3"));
        assertEquals("Wrong children of wn4", "bn3", driver.listChildren("g1", "wn4"));
        assertEquals("Wrong children of bn1", "wn1 wn2", driver.listChildren("g1", "bn1"));
        assertEquals("Wrong children of bn2", "", driver.listChildren("g1", "bn2"));
        assertEquals("Wrong children of bn3", "wn3", driver.listChildren("g1", "bn3"));
        assertEquals("Wrong children of bn4", "extra3White wn1 wn4", driver.listChildren("g1", "bn4"));

        assertEquals("Wrong parents of wn1", "bn1 bn4", driver.listParents("g1", "wn1"));
        assertEquals("Wrong parents of wn2", "bn1 extra1", driver.listParents("g1", "wn2"));
        assertEquals("Wrong parents of wn3", "bn3", driver.listParents("g1", "wn3"));
        assertEquals("Wrong parents of wn4", "bn4", driver.listParents("g1", "wn4"));
        assertEquals("Wrong parents of bn1","" , driver.listParents("g1", "bn1"));
        assertEquals("Wrong parents of bn2","extra3White wn3" , driver.listParents("g1", "bn2"));
        assertEquals("Wrong parents of bn3","wn4" , driver.listParents("g1", "bn3"));
        assertEquals("Wrong parents of bn4", "", driver.listParents("g1", "bn4"));
    }

    @Test
    public void getNeighborsAsSet() {
        BipartiteGraph<String> g = new BipartiteGraph<String>();

        assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn2", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn3", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn3", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn4", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn4", null, false));

        //add some legal edges
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn4", "wn4", "e3"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn4", "bn3", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn3", "wn3", "e2"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn3", "bn2", "e3"));
        assertEquals("e1 is already in edge for bn3", false, g.addEdge("wn2", "bn3", "e1"));

        assertEquals("Wrong parents of wn1", g.listParents("wn1") + " ", g.SetParents("wn1") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn2") + " ", g.SetParents("wn2") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn3") + " ", g.SetParents("wn3") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn4") + " ", g.SetParents("wn4") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn1") + " ", g.SetParents("bn1") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn2") + " ", g.SetParents("bn2") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn3") + " ", g.SetParents("bn3") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn4") + " ", g.SetParents("bn4") + " ");

        assertEquals("Wrong parents of wn1", g.listChildren("wn1") + " ", g.SetChildren("wn1") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn2") + " ", g.SetChildren("wn2") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn3") + " ", g.SetChildren("wn3") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn4") + " ", g.SetChildren("wn4") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn2") + " ", g.SetChildren("bn2") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn3") + " ", g.SetChildren("bn3") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn4") + " ", g.SetChildren("bn4") + " ");

        //change the graph and then check again
        g.removeEdge("e1", "bn1", true);
        g.removeEdge("e2", "wn2", true);
        g.removeEdge("e2", "bn1", false);
        assertEquals("Legal node failed to be deleted", true, g.removeNode("bn1"));
        assertEquals("Legal node failed to be deleted", true, g.removeNode("wn2"));

        assertEquals("Wrong parents of wn1", g.listParents("wn1") + " ", g.SetParents("wn1") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn2") + " ", g.SetParents("wn2") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn3") + " ", g.SetParents("wn3") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("wn4") + " ", g.SetParents("wn4") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn1") + " ", g.SetParents("bn1") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn2") + " ", g.SetParents("bn2") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn3") + " ", g.SetParents("bn3") + " ");
        assertEquals("Wrong parents of wn1", g.listParents("bn4") + " ", g.SetParents("bn4") + " ");

        assertEquals("Wrong parents of wn1", g.listChildren("wn1") + " ", g.SetChildren("wn1") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn2") + " ", g.SetChildren("wn2") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn3") + " ", g.SetChildren("wn3") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("wn4") + " ", g.SetChildren("wn4") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn2") + " ", g.SetChildren("bn2") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn3") + " ", g.SetChildren("bn3") + " ");
        assertEquals("Wrong parents of wn1", g.listChildren("bn4") + " ", g.SetChildren("bn4") + " ");
    }

    @Test
    public void removeNodes() {
        BipartiteGraph<String> g = new BipartiteGraph<String>();

        assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn2", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn3", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn3", null, false));

        assertNotEquals("Node should exist", null, g.findNode("bn1"));
        assertNotEquals("Node should exist", null, g.findNode("wn1"));

        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn2", "e2"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn3", "e3"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e2"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e3"));

        assertEquals("Legal edge was added", false, g.addEdge("wn2", "bn1", "e1"));
        assertEquals("Legal edge was added", true, g.addEdge("wn1", "bn1", "e4"));
        assertEquals("edge was renamed", "bn1", g.getChildByEdgeLabel("wn1", "e1"));
        assertEquals("edge was renamed", null, g.getChildByEdgeLabel("wn1", "e4"));


        assertNotEquals("Node should exist", null, g.findNode("bn1"));
        assertNotEquals("Node should exist", null, g.findNode("wn1"));

        assertEquals("Illegal node was deleted", false, g.removeNode("Non existing node"));
        assertEquals("Legal node failed to be deleted", true, g.removeNode("bn1"));
        assertEquals("Legal node failed to be deleted", true, g.removeNode("wn2"));
        assertEquals("Legal node failed to be deleted", true, g.removeNode("wn1"));

        assertEquals("Node should exist", null, g.findNode("bn1"));
        assertEquals("Node should exist", null, g.findNode("wn1"));
    }

    @Test
    public void nullLabels() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();

        //create a graph
        driver.createGraph("g1");

        driver.addBlackNode("g1", null);
        driver.addWhiteNode("g1", "wn1");
        driver.addBlackNode("g1", "bn1");

        driver.addEdge("g1", null, "wn1", "e1");
        driver.addEdge("g1", "wn1", null, null);

        assertEquals("Wrong child of wn1", null, driver.getChildByEdgeLabel("g1", "wn1", null));

        assertEquals("Node should not exist", "", driver.listChildren("g1", "BBB"));
    }


    @Test
    public void removeEdges() {
        BipartiteGraph<String> g = new BipartiteGraph<String>();

        assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn2", null, false));

        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn2", "wn1", "e2"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn2", "bn2", "e2"));

        assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn1", "e1"));
        assertEquals("Wrong parent", "bn2", g.getParentByEdgeLabel("wn1", "e2"));

        //check that nothing changed
        assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn1", "e1"));
        assertEquals("Wrong child", "bn1", g.getChildByEdgeLabel("wn1", "e1"));
        assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn2", "e2"));
        assertEquals("Wrong child", null, g.getChildByEdgeLabel("wn2", "e4"));
        assertEquals("Wrong parent", "bn1", g.getParentByEdgeLabel("wn1", "e1"));
        assertEquals("Wrong parent", "wn1", g.getParentByEdgeLabel("bn1", "e1"));
        assertEquals("Wrong parent", "wn2", g.getParentByEdgeLabel("bn2", "e2"));

        g.removeEdge("e1", "bn1", true);
        g.removeEdge("e2", "wn2", true);
        g.removeEdge("e2", "bn1", false);

        assertEquals("edge not removed", null, g.getChildByEdgeLabel("wn2", "e2"));
        assertEquals("edge not removed", null, g.getParentByEdgeLabel("wn1", "e1"));
        assertEquals("Wrong parent", "wn1", g.getParentByEdgeLabel("bn1", "e1"));
        assertEquals("Wrong parent", "bn2", g.getParentByEdgeLabel("wn1", "e2"));
        assertEquals("edge not removed", null, g.getParentByEdgeLabel("bn2", "e2"));
        assertEquals("Wrong parent", null, g.getParentByEdgeLabel("bn2", "e2"));
        assertEquals("edge not removed", null, g.getChildByEdgeLabel("bn1", "e1"));
        assertEquals("Wrong child", "bn1", g.getChildByEdgeLabel("wn1", "e1"));
        assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn2", "e2"));


        g.removeEdge("e1", "bn1", false);
        g.removeEdge("e2", "wn1", false);

        assertEquals("edge not removed", null, g.getChildByEdgeLabel("wn1", "e1"));
        assertEquals("edge not removed", null, g.getParentByEdgeLabel("wn1", "e2"));
        assertEquals("edge not removed", null, g.getParentByEdgeLabel("bn1", "e1"));
        assertEquals("edge not removed", null, g.getChildByEdgeLabel("bn2", "e2"));

    }

    @Test
    public void clearTest() {
        BipartiteGraph<String> g = new BipartiteGraph<String>();
        assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());
        g.clear();
        assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());

        assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
        assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
        assertEquals("Legal node failed to be added", true, g.addNode("wn2", null, false));

        assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e1"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("bn2", "wn1", "e2"));
        assertEquals("Legal edge failed to be added", true, g.addEdge("wn2", "bn2", "e2"));

        g.clear();
        assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());

        assertEquals("Legal node failed to be addedd", true, g.addNode("bn1", null, true));
        assertEquals("Legal node failed to be addedd", true, g.addNode("bn2", null, false));
        assertEquals("Legal node failed to be addedd", true, g.addNode("wn1", null, true));
        assertEquals("Legal node failed to be addedd", true, g.addNode("wn2", null, false));

        g.addEdge("bn1", "wn1", "THE EDGE");
        assertEquals("Graph should have 4 nodes", 4 ,g.listNodes(true).size() + g.listNodes(false).size());

        g.clear();
        assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());
    }

    @Test
    public void NodeDataTest() {
        BipartiteGraph<String> g = new BipartiteGraph<String>();
        String data = "MY FRIGGIN DATA";

        g.addNode("bn1", data, true);
        g.addNode("wn1", null, false);

        assertEquals("wrong data", data, g.getNodeData("bn1"));
        assertEquals("wrong data", null, g.getNodeData("wn1"));
        assertEquals("Non existing node should return null", null, g.getNodeData("ANOTHER NODE"));

        g.clear();

        String data1 = "DATA1";
        String data2 = "DATA";

        g.addNode("bn1", data2, true);
        g.addNode("wn1", null, false);

        assertEquals("wrong data", data2, g.getNodeData("bn1"));
        assertEquals("wrong data", null, g.getNodeData("wn1"));



        g.setNodeData("bn1", data1);
        g.setNodeData("wn1", data2);

        assertEquals("wrong data", data1, g.getNodeData("bn1"));
        assertEquals("wrong data", data2, g.getNodeData("wn1"));
    }
//
//    @Test
//    public void getNeighborsAsList() {
//        BipartiteGraph<String> g = new BipartiteGraph<String>();
//
//        assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
//        assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
//        assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
//        assertEquals("Legal node failed to be added", true, g.addNode("bn3", null, true));
//        assertEquals("Legal node failed to be added", true, g.addNode("bn4", null, true));
//
//        //add some legal edges
//        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e1"));
//        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn2", "e2"));
//        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn3", "e3"));
//        assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn4", "e4"));
//
//
//        assertEquals("Wrong children of wn1", "[bn1, bn2, bn3, bn4]", g.listChildren("wn1") );

//    }
}
