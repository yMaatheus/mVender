package dev.matheus.vender.objects;

public class Item {
	
	private int id;
	private double valor;
	
	public Item(int id, double valor) {
		this.setId(id);
		this.setValor(valor);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}