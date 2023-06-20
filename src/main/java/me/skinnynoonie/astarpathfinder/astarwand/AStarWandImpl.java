package me.skinnynoonie.astarpathfinder.astarwand;

import me.skinnynoonie.astarpathfinder.astar.AStarPathfinder;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AStarWandImpl implements Listener {

    private final HashMap<UUID, TwoPoints> twoPoints = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getItem() == null) return;
        if(!event.getItem().getItemMeta().hasDisplayName()) return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"A"+ChatColor.YELLOW+"* "+ChatColor.RED+"Wand")) return;
        event.setCancelled(true);
        UUID uuid = event.getPlayer().getUniqueId();

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
            twoPoints.putIfAbsent(uuid, new TwoPoints(location, null));
            twoPoints.get(uuid).setPointOne(location);
            location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 0, 1, 0, 0, 0, 1, 50, 64);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Point one has been set to: " + readableLocation(location));
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
            twoPoints.putIfAbsent(uuid, new TwoPoints(null, location));
            twoPoints.get(uuid).setPointTwo(location);
            location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 0, 1, 0, 0, 0, 1, 50, 64);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Point two has been set to: " + readableLocation(location));
        }

        if(event.getAction() == Action.LEFT_CLICK_AIR && event.getPlayer().isSneaking()) {
            twoPoints.remove(uuid);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Successfully cleared your clipboard!");
        }

        if(twoPoints.getOrDefault(uuid, new TwoPoints(null, null)).bothPointsSet() && twoPoints.get(uuid).isSameWorld()) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Attempting the A* search...");
            TwoPoints selectTwoPoints = twoPoints.get(uuid);
            List<Location> path = new AStarPathfinder(selectTwoPoints.getPointOne(), 500).findPathTo(selectTwoPoints.getPointTwo());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        twoPoints.remove(event.getPlayer().getUniqueId());
    }

    private String readableLocation(Location location) {
        return location.getX() + ", " + location.getY() + ", " + location.getZ();
    }

}
