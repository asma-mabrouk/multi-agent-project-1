package multiagent.tp1;
import java.util.Random;
import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
import madkit.kernel.Message;
import madkit.message.IntegerMessage;


public class CounterAgent extends Agent{
	private int messageCount;

    @Override
    protected void activate() {
        getLogger().setLevel(Level.FINEST);
        createGroupIfAbsent("communication", "GroupTest");
        requestRole("communication", "GroupTest", "counter");
        pause(500);
    }
    
    @Override
    protected void live() {
        while (true) {
            Message m = nextMessage();
            if (m != null) {
                messageCount++;
                getLogger().info("Counter received a message. Total: " + messageCount);
                
            }
            pause(2000);

            // Simulate random time before transforming into an emitter
            Random random = new Random();
            if (random.nextBoolean()) { // Randomly decide when to transform
                informControllerAndTransform();
                break;
            }
        }
    }

    /*
     * First getting an Agent address, and then sending him a message.
     */
    
    
    protected void informControllerAndTransform() {
    	AgentAddress controller=null;
    	while (controller==null) {
            controller = getAgentWithRole("communication", "GroupTest", "controller");
            pause(500);
    	}
        getLogger().info("Controller found: "+controller);

        
        sendMessage(controller, new IntegerMessage(messageCount));
        pause(500);
        // Launch a new EmitterAgent to simulate transformation
        launchAgent(new EmitterAgent(),true);
        getLogger().info("Counter transforming into an emitter.");
	
    }
    public void setNumberMessages(int nb) {
    	this.messageCount=nb;
    }
    
    @Override
    public void setupFrame(AgentFrame frame) {
        super.setupFrame(frame);
        frame.setLocation(200, 320 * (hashCode() - 2));
    }

//    public static void main(String[] args) {
//        new Madkit("--launchAgents", CounterAgent.class.getName() + ",true,2;", CounterAgent.class.getName() + ",true,2;");
//    }
}
