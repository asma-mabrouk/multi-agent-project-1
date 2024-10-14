package multiagent.tp1;

import madkit.kernel.Madkit;

public class Launcher {
    public static void main(String[] args) {
        new Madkit("--launchAgents", 
            ControllerAgent.class.getName() + ",true,1;" + // Launch 1 controller
            CounterAgent.class.getName() + ",true,3;" +    // Launch 3 counters
            EmitterAgent.class.getName() + ",true,2;"      // Launch 2 emitters
        );
    }
}



