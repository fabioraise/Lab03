package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	private String parola;
	private boolean correct;
	
	
	public RichWord(String parola) {
		this.parola = parola;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isCorrect() {
		return correct;
	}

	public String getParola() {
		return parola;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parola == null) ? 0 : parola.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RichWord other = (RichWord) obj;
		if (parola == null) {
			if (other.parola != null)
				return false;
		} else if (!parola.equals(other.parola))
			return false;
		return true;
	}
	
	
}
