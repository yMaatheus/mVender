package dev.matheus.vender;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import dev.matheus.vender.commands.VenderCommand;
import dev.matheus.vender.objects.Item;
import dev.matheus.vender.objects.Vender;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static HashMap<String, Vender> vender = new HashMap<>();
	public static ArrayList<String> sell = new ArrayList<>();

	@Override
	public void onEnable() {
		plugin = this;
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		ArrayList<String> grupos = new ArrayList<>();
		for (String grupo : getConfig().getConfigurationSection("Itens").getKeys(false)) {
			grupos.add(grupo);
		}
		if (getConfig().getBoolean("consoledebug")) {
			Bukkit.getConsoleSender().sendMessage("§emVender INFO:");
			Bukkit.getConsoleSender().sendMessage("");
		}
		for (String grupo : grupos) {
			for (String categoria : getConfig().getConfigurationSection("Itens." + grupo).getKeys(false)) {
				int id = Integer.parseInt(getConfig().getString("Itens." + grupo + "." + categoria + ".id"));
				double valor = Double.parseDouble(getConfig().getString("Itens." + grupo + "." + categoria + ".valor"));
				if (getConfig().getBoolean("consoledebug")) {
					Bukkit.getConsoleSender().sendMessage("§eRank: §f" + grupo);
					Bukkit.getConsoleSender().sendMessage("§eCategoria: §f" + categoria);
					Bukkit.getConsoleSender().sendMessage("§eId: §f" + id);
					Bukkit.getConsoleSender().sendMessage("§eValor: §f" + valor);
					Bukkit.getConsoleSender().sendMessage("");
				}
				Vender v = null;
				Item item = new Item(id, valor);
				if (Main.vender.containsKey(grupo)) {
					v = vender.get(grupo);
					v.getItem().add(item);
					vender.put(grupo, v);
				} else {
					v = new Vender(grupo);
					v.getItem().add(item);
					vender.put(grupo, v);
				}
			}
		}
		setupEconomy();
		((CraftServer) getServer()).getCommandMap().register(new VenderCommand().getName(), new VenderCommand());
	}

	@Override
	public void onDisable() {

	}

	public static Economy economy = null;

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}