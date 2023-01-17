public class Tests {
    public static void main(String[] args) {

        SwarmOfHornets swarm = new SwarmOfHornets();
        System.out.println("size" + swarm.sizeOfSwarm());

        Tile tile = new Tile();
        tile.buildHive();

        swarm.addHornet(new Hornet(tile, 1,5));
        //swarm.addHornet(new Hornet(new Tile(), 2,5));
        //swarm.addHornet(new Hornet(new Tile(), 3,5));
        //swarm.addHornet(new Hornet(new Tile(), 4,5));


    }
}
