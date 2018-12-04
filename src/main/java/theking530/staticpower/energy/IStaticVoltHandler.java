package theking530.staticpower.energy;

public interface IStaticVoltHandler {
        /*
          Returns the amount of power currenty stored
          in this IStaticVoltHandler
        */
        public int getStoredPower();
        /*
          Returns the maximum amount of power that can be stored
          in this IStaticVoltHandler
        */
        public int getPowerCapacity();
        
        /*
          Tries to recieve the amount of power passed in
          and returns the total amount of power accepted. 
          @param amount = Amount of power to recieve
          @param simulate = If true, power recieve is simulated
          @return t= The amount of power accepted by the handler.
        */
        public int recievePower(int amount, boolean simulate);
        public int drainPower(int amount, boolean simulate);
        
        public boolean canRecievePower();
        public boolean canDrainPower();
        
        public int getMaxRecieve();
        public int getMaxDrain();


}