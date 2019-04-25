package it.polito.tdp.spellchecker.model;

import java.util.*;
import java.io.*;

public class Dictionary {
	
	private List<String> dizionario;
	private String language;
	
	public Dictionary() {

	}

	public boolean loadDictionary(String language) {
		if (dizionario != null && this.language.equals(language)) {
			return true;
		}
		
		// Genera una nuova ArrayList ogni volta che viene cambiata lingua, così che 
		//	la vecchia lista venga cancellata
		dizionario = new LinkedList<String>();
		this.language = language;
		
		try {

			FileReader fr = new FileReader("rsc/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;

			while ((word = br.readLine()) != null) {
				dizionario.add(word.toLowerCase());
			}
			
			// Lista ordinata alfabeticamente
			Collections.sort(dizionario);

			br.close();
			System.out.println("Dizionario " + language + " loaded. Found " + dizionario.size() + " words.");
			
			return true;

		} catch (IOException e) {
			System.err.println("Errore nella lettura del file");
			return false;
		}

	}

	public List<RichWord> spellCheckText(List<String> inputTextList) {

		List<RichWord> parole = new ArrayList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str);
			if (dizionario.contains(str.toLowerCase())) {
				richWord.setCorrect(true);
			} else {
				richWord.setCorrect(false);
			}
			parole.add(richWord);
		}

		return parole;
	}
	
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		
		List<RichWord> parole = new ArrayList<RichWord>();
		
		for(String str : inputTextList) {
			
			RichWord rw = new RichWord(str);
			rw.setCorrect(false);
			for(String currentWord : dizionario) {
				if(currentWord.equals(str.toLowerCase())) {
					rw.setCorrect(true);
					parole.add(rw);
					break;
				}
			}
			parole.add(rw);
			
		}
		
		return parole;
		
	}
	
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		
		List<RichWord> parole = new ArrayList<RichWord>();
		
		for(String str : inputTextList) {
			
			int inizio = 0;
			int fine = dizionario.size();
			
			RichWord rw = new RichWord(str);
			rw.setCorrect(false);
			while(inizio != fine) {
				int medio = (inizio + fine)/2;
				
				if(str.compareToIgnoreCase(dizionario.get(medio)) == 0) {
					rw.setCorrect(true);
					break;
				}
				else if(str.compareToIgnoreCase(dizionario.get(medio)) < 0) {
					fine = medio;
				}
				else if(str.compareToIgnoreCase(dizionario.get(medio)) > 0) {
					inizio = medio + 1;
				}
				
			}
			
			parole.add(rw);
		}
		
		return parole;
		
	}

}
