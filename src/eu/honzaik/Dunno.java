package eu.honzaik;

public class Dunno {

	public Dunno(String s) {
		this.s = s;
		pocetZnamenek();
		pocatecniZnamenkoCheck();
		if (pocetZnamenek != 0)
			vytvoreniZnamenek();
		try {
			onlyNums();
		} catch (Exception e) {
		}
		if (pocetZnamenek != 0 && fine && syntaxCheck()) {
			createNumField();
			makeNumbers();
			this.priklad = new Priklad(nums, znamenka);
			Vysledek vysl = new Vysledek(priklad, s);
			vysledek = vysl.vysledek;
		}
		System.out.println(numz);
	}

	public Dunno(String s, boolean bol) {
		this.s = s;
		this.bol = bol;
		pocetZnamenek();
		onlyNums();
		vytvoreniZnamenek();
		createNumField();
		makeNumbers();
	}

	public String s = null;
	public Priklad priklad;
	public int pocetZnamenek;
	public int numz;
	private int indexZnamenek = 0;
	public double vysledek = 0.0;
	public boolean isSyntaxOk = true;
	public boolean pew = false;
	public boolean fine = false;
	private boolean bol = false;
	private boolean prvniCisloKladne;
	private boolean znamenkoFound = false;
	public Znamenko[] znamenka = new Znamenko[0];
	public Cislo[] nums = new Cislo[0];
	public char[] operace = { '+', '-', '/', '*', };
	public char[] cisla = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };

	private void onlyNums() {
		numz = 0;
		boolean error = false;
		boolean derp = false;
		boolean meh = false;
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < cisla.length; j++) {
				 if (s.charAt(i) == '.' || s.charAt(i) == cisla[j]) {
					if (s.charAt(i) == '.') {
						if (i == s.length() - 1)
							error = true;
						if (s.charAt(i - 1) == '.')
							error = true;
					}
					int index = 0;
					if (!meh) {
						for (int k = 0; k < znamenka.length; k++) {
							if (s.charAt(i) == '.') {
								if (s.charAt(i - 1) == znamenka[k].c)
									error = true;
							}
							if (s.charAt(i) == cisla[j])
								numz++;
							if (znamenka[k].index > i) {
								index = znamenka[k].index + 1;
								i = index;
							} else {
								i = s.length() - 1;
							}
							derp = true;
						}
					}
				}
			}
			if (derp) {
				numz++;
				meh = true;
				derp = false;
			}
		}
		if (!error) {
			if (numz > pocetZnamenek)
				fine = true;
			else
				fine = false;
			if (znamenkoFound & (numz >= pocetZnamenek))
				fine = true;
		} else {
			fine = false;
		}
	}

	private void pocetZnamenek() {
		if (!bol) {
			for (int i = 1; i < s.length(); i++) {
				for (int j = 0; j < operace.length; j++) {
					if (s.charAt(i) == operace[j]) {
						pocetZnamenek++;
					}
				}
			}
		} else {
			for (int i = 0; i < s.length(); i++) {
				for (int j = 0; j < operace.length; j++) {
					if (s.charAt(i) == operace[j]) {
						pocetZnamenek++;
					}
				}
			}
		}

	}

	private void vytvoreniZnamenek() {
		znamenka = new Znamenko[pocetZnamenek];
		for (int i = 1; i < s.length(); i++) {
			for (int j = 0; j < operace.length; j++) {
				if (s.charAt(i) == operace[j]) {
					znamenka[indexZnamenek] = new Znamenko(s, i, s.charAt(i));
					indexZnamenek++;
				}
			}
		}
	}

	private void pocatecniZnamenkoCheck() {
		char c = s.charAt(0);
		for (int i = 0; i < operace.length; i++) {
			if (c == operace[i]) {
				if (c == '+')
					prvniCisloKladne = true;
				if (c == '-')
					prvniCisloKladne = false;
				znamenkoFound = true;
			}
		}
	}

	private void createNumField() {
		nums = new Cislo[pocetZnamenek + 1];
	}

	private void makeNumbers() {
		for (int i = 0; i < nums.length; i++) {
			int startIndex = 0;
			int endIndex = 0;

			if (i == 0) {
				if (znamenkoFound) {
					startIndex = 1;
				} else {
					startIndex = 0;
				}
				endIndex = znamenka[0].index - 1;
			} else {
				startIndex = znamenka[i - 1].index + 1;
				if (i + 1 <= znamenka.length) {
					endIndex = znamenka[i].index - 1;
				} else {
					endIndex = s.length() - 1;
				}
			}

			if (znamenkoFound) {
				nums[i] = new Cislo(s, startIndex, endIndex, i,
						prvniCisloKladne);
			} else {
				nums[i] = new Cislo(s, startIndex, endIndex, i);
			}

		}
	}

	private boolean syntaxCheck() {
		boolean temp = false;
		boolean chyba = false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				if (s.charAt(i) == operace[2] || s.charAt(i) == operace[3])
					chyba = true;
			} else {
				if (temp) {
					for (int j = 0; j < operace.length; j++) {
						if (i + 1 == s.length()) {
							if (s.charAt(i) == operace[j])
								chyba = true;
						} else {
							if (s.charAt(i) == operace[j])
								chyba = true;
							else
								temp = false;
						}
					}
				} else {
					for (int j = 0; j < operace.length; j++) {
						if (i + 1 == s.length()) {
							if (s.charAt(i) == operace[j])
								chyba = true;
						} else {
							if (s.charAt(i) == operace[j])
								temp = true;
						}
					}
				}
			}
		}
		if (chyba) {
			return false;
		} else {
			return true;
		}

	}
}
