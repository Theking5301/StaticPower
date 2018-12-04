package theking530.staticpower.energy;

public class StaticVoltHandler implements IStaticVoltHandler {
        int storedPower;
        int capacity;
        int maxRecieve;
        int maxDrain;
        
        boolean canRecieve;
        boolean canDrain;
        
        public StaticVoltHandler(int capacity, int maxRecieve, int maxDrain) {
            this.capacity = capacity;
            this.maxRecieve = maxRecieve;
            this.maxDrain =maxDrain;
        }
        
        @Override
        public int getStoredPower() {
            return storedPower;
        }
        @Override 
        public int getPowerCapacity() {
            return capacity;
        }

		@Override
		public int recievePower(int amount, boolean simulate) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int drainPower(int amount, boolean simulate) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean canRecievePower() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canDrainPower() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getMaxRecieve() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getMaxDrain() {
			// TODO Auto-generated method stub
			return 0;
		}
        
        
        
                
      



}