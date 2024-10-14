package multiagent.tp1;
import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;
import madkit.kernel.Message;
import madkit.message.IntegerMessage;

public class ControllerAgent extends Agent{
	@Override
    protected void activate() {
        getLogger().setLevel(Level.FINEST);
        createGroupIfAbsent("communication", "GroupTest");
        requestRole("communication", "GroupTest", "controller");
        pause(500);
    }

    @Override
    protected void live() {
        while (true) {
            Message m = waitNextMessage();
            if (m instanceof IntegerMessage) {
                int messageCount = ((IntegerMessage) m).getContent();
                getLogger().info("Controller received count: " + messageCount);
                
                // Launch a new CounterAgent to replace the one that transformed
                CounterAgent counterAgent = new CounterAgent();
                counterAgent.setNumberMessages(messageCount);
                launchAgent(counterAgent,true);
                getLogger().info("Launched new CounterAgent.");
            }
        }
    }
    
    @Override
    public void setupFrame(AgentFrame frame) {
        super.setupFrame(frame);
        frame.setLocation(300, 320 * (hashCode() - 2));
    }

//    public static void main(String[] args) {
//        new Madkit("--launchAgents", ControllerAgent.class.getName() + ",true,1;");
//    }	



}
