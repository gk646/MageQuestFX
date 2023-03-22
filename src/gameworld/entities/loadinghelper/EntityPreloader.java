package gameworld.entities.loadinghelper;

public class EntityPreloader {
    public static ResourceLoaderEntity mushroom;
    public static ResourceLoaderEntity skeletonWarrior;
    public static ResourceLoaderEntity skeletonArcher;
    public static ResourceLoaderEntity skeletonSpear;
    public static ResourceLoaderEntity snake;
    public static ResourceLoaderEntity wolf;
    // public static ResourceLoaderEntity skeletonWarrior;

    public static void load() {
        mushroom = new ResourceLoaderEntity("enemies/mushroom");
        skeletonWarrior = new ResourceLoaderEntity("enemies/skeletonWarrior");
        skeletonArcher = new ResourceLoaderEntity("enemies/skeletonArcher");
        skeletonSpear = new ResourceLoaderEntity("enemies/skeletonSpear");
        snake = new ResourceLoaderEntity("enemies/snake");
        wolf = new ResourceLoaderEntity("enemies/wolf");
    }
}
