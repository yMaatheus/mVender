package dev.matheus.vender.objects;

import java.util.ArrayList;
import java.util.List;

public class Vender {

	private String grupo;
	private List<Item> item;

	public Vender(String grupo) {
		this.setGrupo(grupo);
		this.item = new ArrayList<>();
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}
	
}