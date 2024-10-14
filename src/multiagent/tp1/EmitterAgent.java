package multiagent.tp1;

import java.util.Random;
import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
import madkit.kernel.Message;


public class EmitterAgent extends Agent {
	private static final int MIN_MESSAGES = 1;
    private static final int MAX_MESSAGES = 5; // Send between 1 and 5 messages
    /*
     * Firstly, take a position in the artificial society.
     */
	

    @Override
    protected void activate() {
        getLogger().setLevel(Level.FINEST);
        createGroup("communication", "GroupTest");
        requestRole("communication", "GroupTest", "emitter");
        pause(500);
    }

    @Override
    protected void live() {
        Random random = new Random();
        AgentAddress counterAddress=null;

        int numberOfMessages = MIN_MESSAGES + random.nextInt(MAX_MESSAGES - MIN_MESSAGES + 1);
        getLogger().info("Emitter will send " + numberOfMessages + " messages.");

        for (int i = 0; i < numberOfMessages; i++) {
        	while (counterAddress==null) {
        		counterAddress = getAgentWithRole("communication", "GroupTest", "counter");
        		pause(500);
        	}
            sendMessage(counterAddress, new Message());
            pause(500);
            getLogger().info("Sent message to " + counterAddress);
           
            
            // Wait a random time between 1 and 3 seconds
            int randomWaitTime = 1000 + random.nextInt(2000);
            long endTime = System.currentTimeMillis() + randomWaitTime;
            while (System.currentTimeMillis() < endTime) {
                // Busy wait to simulate random waiting without using Thread.sleep()
            }
        }

        // Emitter agent terminates after sending messages
        getLogger().info("Emitter done sending messages. Terminating.");
    }


    /*
     * Set where the agent's window will be for avoiding a clear presentation. This method give
	 * the opportunity to modify the default settings of the frame.
     * It will be more explained in GUI tutorial.
     */
    @Override
    public void setupFrame(AgentFrame frame) {
        super.setupFrame(frame);
        frame.setLocation(100, 320 * (hashCode() - 2));
    }

//    public static void main(String[] args) {
//        new Madkit("--launchAgents", EmitterAgent.class.getName() + ",true,2;", EmitterAgent.class.getName() + ",true,2;");
//    }
    
    
}
