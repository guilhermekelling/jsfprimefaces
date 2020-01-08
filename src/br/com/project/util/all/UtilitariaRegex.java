package br.com.project.util.all;

public class UtilitariaRegex {
	
	public String retiraAcentos(String string) {
		String aux = new String (string);
		aux = aux.replaceAll("[èéêëÈÉÊË]", "e");
		aux = aux.replaceAll("[ùúûüÙÚÛÜ]", "u");
		aux = aux.replaceAll("[ìíîïÌÍÎÏ]", "i");
		aux = aux.replaceAll("[àáâäÀÁÂÄ]", "a");
		aux = aux.replaceAll("[òóôöÒÓÔÖ]", "o");
		return aux;		
	}
}
