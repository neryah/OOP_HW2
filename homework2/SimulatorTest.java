package homework2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimulatorTest {
    //  Black-box tests
    private static void simulateKTimes(int k, SimulatorTestDriver driver, String simName){
        for(int i = 0; i<k; i++){
            driver.simulate(simName);
        }
    }
    @Test
    public void testBasic() {
        SimulatorTestDriver driver = new SimulatorTestDriver();
        driver.createSimulator("sizeMatter");
        //create basic graph
        driver.addChannel("sizeMatter","c1", 100);
        driver.addChannel("sizeMatter","c2", 100);
        driver.addChannel("sizeMatter","c3", 2);
        driver.addChannel("sizeMatter","c4", 1);
        //not for use
        driver.addChannel("sizeMatter","cc", 100);
        driver.addChannel("sizeMatter","cv", 1);

        driver.addParticipant("sizeMatter","p1", "1", 3);
        driver.addParticipant("sizeMatter","p2", "1", 3);
        driver.addParticipant("sizeMatter","p3", "1", 3);
        driver.addParticipant("sizeMatter","p4", "1", 3);

        driver.addParticipant("sizeMatter","p5", "1", 3);
        driver.addParticipant("sizeMatter","p6", "1", 3);
        driver.addParticipant("sizeMatter","p7", "1", 3);
        driver.addParticipant("sizeMatter","p8", "1", 3);
        //not for use
        driver.addParticipant("sizeMatter","pp", "1", 3);
        driver.addParticipant("sizeMatter","pc", "1", 3);

        //add an edge
        driver.addEdge("sizeMatter", "c1", "p1", "c1-p1");
        driver.addEdge("sizeMatter", "c2", "p2", "c2-p2");
        driver.addEdge("sizeMatter", "c3", "p3", "c3-p3");
        driver.addEdge("sizeMatter", "c4", "p4", "c4-p4");

        driver.addEdge("sizeMatter", "c1", "p5", "c1-p5");
        driver.addEdge("sizeMatter", "c2", "p6", "c2-p6");
        driver.addEdge("sizeMatter", "c3", "p7", "c3-p7");
        driver.addEdge("sizeMatter", "c4", "p8", "c4-p8");

        driver.addEdge("sizeMatter", "p1", "c2", "p1-c2");
        driver.addEdge("sizeMatter", "p5", "c2", "p5-c2");
        driver.addEdge("sizeMatter", "p2", "c1", "p2-c1");
        driver.addEdge("sizeMatter", "p6", "c1", "p6-c1");
        //until here basic graph test

        //simulateKTimes(5, driver, "sizeMatter");

        //check neighbors
        assertEquals("There is no transaction yet!", "", driver.listContents("sizeMatter", "c1"));
        assertEquals("There is no transaction yet!", "", driver.listContents("sizeMatter", "c2"));
        assertEquals("There is no transaction yet!", "", driver.listContents("sizeMatter", "c3"));
        assertEquals("There is no transaction yet!", "", driver.listContents("sizeMatter", "c4"));

        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p1"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p2"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p3"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p4"),0);

        //simulateKTimes(5, driver, "sizeMatter");

        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p5"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p6"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p7"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p8"),0);

        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p1"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p2"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p3"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p4"),0);

        //simulateKTimes(5, driver, "sizeMatter");

        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p5"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p6"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p7"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p8"),0);

        //do stuff
        driver.sendTransaction("sizeMatter", "c1", new Transaction("1", 150));

        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p6"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p2"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantStorageAmount("sizeMatter", "p2"),0);
        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p6"),0);
        driver.printAllEdges("sizeMatter");

        simulateKTimes(99, driver, "sizeMatter");


        assertEquals("There is no transaction yet!", 3, driver.getParticipantStorageAmount("sizeMatter", "p1"),0);
        assertEquals("There is no transaction yet!", 3, driver.getParticipantStorageAmount("sizeMatter", "p2"),0);
        assertEquals("There is no transaction yet!", 3, driver.getParticipantStorageAmount("sizeMatter", "p5"),0);
        assertEquals("There is no transaction yet!", 3, driver.getParticipantStorageAmount("sizeMatter", "p6"),0);

        assertEquals("There is no transaction yet!", 0, driver.getParticipantToRecycleAmount("sizeMatter", "p6")+driver.getParticipantToRecycleAmount("sizeMatter", "p5")+
                driver.getParticipantToRecycleAmount("sizeMatter", "p2")+driver.getParticipantToRecycleAmount("sizeMatter", "p1"),0);


        assertEquals("This is dummy nodes", 0, driver.getParticipantToRecycleAmount("sizeMatter", "pp"),0);
        assertEquals("This is dummy nodes", 0, driver.getParticipantToRecycleAmount("sizeMatter", "pc"),0);
        assertEquals("This is dummy nodes", 0, driver.getParticipantStorageAmount("sizeMatter", "pp"),0);
        assertEquals("This is dummy nodes", 0, driver.getParticipantStorageAmount("sizeMatter", "pc"),0);
        assertEquals("This is dummy nodes", "", driver.listContents("sizeMatter", "cc"));
        assertEquals("This is dummy nodes", "", driver.listContents("sizeMatter", "cv"));
    }


}
