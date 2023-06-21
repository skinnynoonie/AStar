package me.skinnynoonie.astarpathfinder.astar.distances;

import me.skinnynoonie.astarpathfinder.astar.ImmutableVector;

public class ManhattanDistanceCalculator implements DistanceCalculator {

    @Override
    public double calculateDistance(ImmutableVector from, ImmutableVector to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) + Math.abs(from.getZ() - to.getZ());
    }

}
