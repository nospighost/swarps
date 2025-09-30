package de.Warp.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.*;
import de.Main.database.DBM;
import de.Warp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@CommandAlias("swarp")
public class swarpCommand extends BaseCommand {

    DBM dbm;
    private String prefix = Main.prefix;
    public String tableName = Main.tableName;

    public swarpCommand(DBM dbm) {
        this.dbm = dbm;
    }


    public swarpCommand(PaperCommandManager manager) {
        manager.getCommandCompletions().registerCompletion("swarps", c -> {
            List<String> tempswarpNames = dbm.getAllValues(tableName, "name");




            return tempswarpNames;
        });
    }





    @Default
    public void onDefault(Player player) {

    }

    @Subcommand("create")
    @CommandPermission("be.swarp.create")
    public void onCreate(Player player, String swarpName) {
        Location loc = player.getLocation();
        UUID uuid = player.getUniqueId();
        String key = Main.createDBKey(player, swarpName);
        if (dbm.getString(tableName, key, "owner_uuid", null) == null) {
            HashMap<String, Object> values = new HashMap<>();
            values.put("x", player.getLocation().getBlockX());
            values.put("y", player.getLocation().getBlockY());
            values.put("z", player.getLocation().getBlockZ());
            values.put("world", player.getWorld().getName());
            values.put("name", swarpName);
            values.put("owner_uuid", key);
            values.put("owner", player.getName());
            dbm.insertDefaultValues(tableName, key, values);
            player.sendMessage(prefix + "§eDer Swarp §c" + swarpName + "§e wurde erfolgreich erstellt");

        } else {
            player.sendMessage(prefix + "§cDieser Swarp Name gibt es bereits!");
        }

    }

    @Subcommand("delete")
    @CommandPermission("be.swarp.delete")
    @CommandCompletion("@swarps")
    public void onDelete(Player player, String swarpName) {
        String key = Main.createDBKey(player, swarpName);
        int x = dbm.getInt(tableName, key, "x", 0);
        int y = dbm.getInt(tableName, key, "y", 0);
        int z = dbm.getInt(tableName, key, "z", 0);
        String worldName = dbm.getString(tableName, key, "world", null);

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage(Main.prefix + "§cDie Welt existiert nicht!");
            return;
        }

        Location loc = new Location(world, x, y, z);
        if (loc == null) {
            player.sendMessage(prefix + "Keine Location gefunden!");
            return;
        }

        dbm.remove(tableName, key);
        player.sendMessage(prefix + "§eDer Swarp §c" + swarpName + "§e wurde erfolgreich Gelöscht");

    }
}







