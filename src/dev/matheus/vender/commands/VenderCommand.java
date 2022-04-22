package dev.matheus.vender.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.matheus.vender.Main;
import dev.matheus.vender.objects.Item;
import dev.matheus.vender.objects.Vender;

public class VenderCommand extends Command {

	public VenderCommand() {
		super("vender");
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public boolean execute(CommandSender sender, String arg, String[] args) {
		if (sender instanceof Player == false) {
			return true;
		}
		Player player = (Player) sender;
		if (args.length >= 1) {
			String grupo = args[0].toLowerCase();
			if (Main.vender.containsKey(grupo)) {
				Vender vender = Main.vender.get(grupo);
				if (player.hasPermission("vender." + vender.getGrupo())) {
					int aumount = 0;
					double valor = 0;
					ArrayList<ItemStack> m = new ArrayList<>();
					for (ItemStack itens : player.getInventory().getContents()) {
						if (itens != null) {
							if (itens.getType() != Material.AIR) {
								for (Item i : vender.getItem()) {
									if (itens.getType().getId() == i.getId()) {
										if (itens.getAmount() != 0) {
											aumount += itens.getAmount();
											valor += itens.getAmount() * i.getValor();
											Main.plugin.economy.depositPlayer(player, itens.getAmount() * i.getValor());
											if (m.contains(itens) == false) {
												m.add(itens);
											}
										}
									}
								}
							}
							
						}
					}
					if (m.isEmpty()) {
						player.sendMessage("§cVocê não possui itens para vender.");
						return true;
					}
					for (ItemStack delete : m) {
						player.getInventory().remove(delete);
					}
					player.sendMessage(Main.plugin.getConfig().getString("vender").replace("&", "§").replace("%quantidade%", Integer.toString(aumount)).replace("%valor%", Double.toString(valor)));
					player.updateInventory();
				} else {
					player.sendMessage(Main.plugin.getConfig().getString("noperm").replace("&", "§"));
				}
			} else {
				player.sendMessage("§cEsse rank não existe.");
			}
			return true;
		}
		player.sendMessage(Main.plugin.getConfig().getString("use").replace("&", "§"));
		return false;
	}
}