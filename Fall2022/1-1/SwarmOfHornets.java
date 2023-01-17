public class SwarmOfHornets {

    private Hornet[] hornets;
    private int swarmSize;

    public SwarmOfHornets() {
        this.hornets = new Hornet[0];
        this.swarmSize = 0;
    }

    public int sizeOfSwarm() {
        return this.swarmSize;
    }

    public Hornet[] getHornets() {
        return this.hornets;

    }

    public Hornet getFirstHornet() {

        if (this.sizeOfSwarm() != 0) {
            return this.hornets[0];
        }

        return null;
    }

    public void addHornet(Hornet hornet) {

        Hornet[] tempArray = new Hornet[this.sizeOfSwarm() + 1];

        for(int i = 0; i < this.sizeOfSwarm(); i++) {
            tempArray[i] = this.hornets[i];
        }

        this.hornets = tempArray;

        this.hornets[this.sizeOfSwarm()] = hornet;

        this.swarmSize += 1;
    }

    public boolean removeHornet(Hornet hornet) {

        if (this.swarmSize != 0) {

            for(int i = 0; i < this.sizeOfSwarm(); i++) {
                if (this.hornets[i].equals(hornet)) {
                    this.hornets[i] = null;
                }
            }

            Hornet[] tempArray = new Hornet[this.sizeOfSwarm() - 1];
            int count = 0;

            for(int i = 0; i < this.sizeOfSwarm() ; i++) {
                if (this.hornets[i] != null) {
                    tempArray[count] = this.hornets[i];
                    count += 1;
                }
            }

            this.hornets = tempArray;

            this.swarmSize -= 1;

            return true;
        }

        return false;

    }

}

