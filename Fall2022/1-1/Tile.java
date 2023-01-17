public class Tile {
    private int food;
    private boolean beeHiveBuilt;
    private boolean hornetNestBuilt;
    private boolean isPath;
    private Tile toHive;
    private Tile toNest;
    private HoneyBee honeyBee;
    private SwarmOfHornets swarmOfHornets;

    public Tile() {
        this.food = 0;
        this.beeHiveBuilt = false;
        this.hornetNestBuilt = false;
        this.isPath = false;
        this.toHive = null;
        this.toNest = null;
        this.honeyBee = null;
        this.swarmOfHornets = new SwarmOfHornets();
    }

    public Tile(int food, boolean beeHiveBuilt, boolean hornetHiveBuilt, boolean isPath, Tile toBees, Tile toHornets, HoneyBee honeyBee, SwarmOfHornets swarmOfHornets) {
        this.food = food;
        this.beeHiveBuilt = beeHiveBuilt;
        this.hornetNestBuilt = hornetHiveBuilt;
        this.isPath = isPath;
        this.toHive = toBees;
        this.toNest = toHornets;
        this.honeyBee = honeyBee;
        this.swarmOfHornets = swarmOfHornets;
    }

    public boolean isHive() {
        return this.beeHiveBuilt;
    }

    public boolean isNest() {
        return this.hornetNestBuilt;
    }

    public void buildHive() {
        this.beeHiveBuilt = true;
    }

    public void buildNest() {
        this.hornetNestBuilt = true;
    }

    public boolean isOnThePath() { return this.isPath; }
    public Tile towardTheHive() {
        return this.toHive;
    }

    public Tile towardTheNest() {
        return this.toNest;
    }

    public void createPath(Tile tile1, Tile tile2) {
        this.toHive = tile1;
        this.toNest = tile2;
        this.isPath = true;
    }

    public int collectFood() {
        int temp = this.food;
        this.food = 0;
        return temp;
    }

    public void storeFood(int food) {
        this.food += food;
    }

    public HoneyBee getBee() {
        return this.honeyBee;
    }


    public Hornet getHornet() {
        return this.swarmOfHornets.getFirstHornet();
    }

    public int getNumOfHornets() {
        return this.swarmOfHornets.sizeOfSwarm();
    }

    public boolean addInsect(Insect insect) {

        if ((insect instanceof HoneyBee) && (this.honeyBee == null) && (!this.hornetNestBuilt) ) {
            this.honeyBee = (HoneyBee) insect; // DownCasting
            insect.setPosition(this);
            return true;
        }
        else if ((insect instanceof Hornet) && ((this.hornetNestBuilt) || (this.beeHiveBuilt) || (this.isPath))) {

            this.swarmOfHornets.addHornet((Hornet) insect);
            insect.setPosition(this);
            return true;

        }
        else {
            return false;
        }

    }

    public boolean removeInsect(Insect insect) {
        if (insect instanceof HoneyBee) {
            this.honeyBee = null;
            insect.setPosition(null);
            return true;
        }
        else if (insect instanceof Hornet) {
            this.swarmOfHornets.removeHornet((Hornet) insect);
            insect.setPosition(null);
            return true;
        }
        else {
            return false;
        }
    }

}
