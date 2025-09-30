package de.Warp.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Name;
import de.Main.database.DBM;
import de.Warp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class swCommand extends BaseCommand {
    DBM dbm;
    private String tableName = Main.tableName;
    public swCommand(PaperCommandManager manager, DBM dbm){
        this.dbm = dbm;
        manager.getCommandCompletions().registerCompletion("swarps", c -> {
            List<String> tempswarpNames = dbm.getAllValues(tableName, "name");




            return tempswarpNames;
        });


    }

    @CommandAlias("sw")
    @CommandPermission("be.sw")
    @CommandCompletion("@swarps")
    public void onDefault(Player player, @Name("swarp Name") String name){

        int x = dbm.getInt(tableName, Main.createDBKey(player, name), "x", 0);
        int y = dbm.getInt(tableName, Main.createDBKey(player, name), "y", 0);
        int z = dbm.getInt(tableName, Main.createDBKey(player, name), "z", 0);
        String world = dbm.getString(tableName, Main.createDBKey(player, name), "world", null);
        World world1 = Bukkit.getWorld(world);
        Location loc = new Location(world1, x, y, z);
        player.teleport(loc);

    }
}
