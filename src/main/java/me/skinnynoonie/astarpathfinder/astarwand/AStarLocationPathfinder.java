package me.skinnynoonie.astarpathfinder.astarwand;

import me.skinnynoonie.astarpathfinder.astar.AStarEngine;
import me.skinnynoonie.astarpathfinder.astar.AStarResult;
import me.skinnynoonie.astarpathfinder.astar.util.ImmutableVector;
import org.bukkit.Location;
import org.bukkit.Material;

public class AStarLocationPathfinder extends AStarEngine {

    public AStarResult findPathTo(Location from, Location to) {
        if(from.getWorld() != to.getWorld()) return new AStarResult();
        if(to.getBlock().getType() != Material.AIR) return new AStarResult();
        return super.findPathTo(from.getWorld(), new ImmutableVector(from), new ImmutableVector(to));
    }

}
