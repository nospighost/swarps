package de.Warp;

import co.aikar.commands.PaperCommandCompletions;
import co.aikar.commands.PaperCommandManager;
import de.Main.database.DBM;
import de.Main.database.SQLConnection;
import de.Main.database.SQLDataType;
import de.Warp.command.swCommand;
import de.Warp.command.swarpCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin {


    public static String tableName = "swarps";
    private SQLConnection connection;
    public static String prefix = "§b§lBlockEngine §8» §7";
    DBM dbm;
    @Override
    public void onEnable() {

        connection = new SQLConnection("blockyplayz.de", 3306, "swarps", "developer", "wBeBYXcdaDsQTw[!");

        HashMap<String, SQLDataType> userdatacolumns = new HashMap<>();
        userdatacolumns.put("owner_uuid", SQLDataType.CHAR);
        userdatacolumns.put("owner", SQLDataType.CHAR);
        userdatacolumns.put("name", SQLDataType.CHAR);
        userdatacolumns.put("x", SQLDataType.INT);
        userdatacolumns.put("z", SQLDataType.INT);
        userdatacolumns.put("y", SQLDataType.INT);
        userdatacolumns.put("world", SQLDataType.CHAR);
        dbm = new DBM(connection, tableName, userdatacolumns);
        PaperCommandManager manager = new PaperCommandManager(this);
        registerComamnds(manager);

    }


    public static String createDBKey(Player player, String swarpName){




        return player.getWorld().getName() +"#" + swarpName;
    }

    private void registerComamnds(PaperCommandManager manager){

        manager.registerCommand(new swarpCommand(dbm));
        manager.registerCommand(new swCommand(manager, dbm));

    }

    @Override
    public void onDisable() {
    }
}
