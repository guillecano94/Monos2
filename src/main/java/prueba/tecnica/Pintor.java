package prueba.tecnica;

public class Pintor extends Thread {

	private Cuerda cuerda;

	public Pintor(Cuerda cuerda) {
		this.cuerda = cuerda;
	}

	public void run() {
		while (true) {
			pintar();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Método que pinta en consola el estado de la cuerda y los monos.
	 */
	private void pintar() {
		System.out.println("****************************************");
		System.out.println("Cola este");
		System.out.println(cuerda.getColaEste().toString());
		System.out.println(" ");
		System.out.println("Cola oeste");
		System.out.println(cuerda.getColaOeste().toString());
		System.out.println(" ");
		System.out.println("MONOS CRUZANDO");
		System.out.println(cuerda.getCuerda().toString());
		System.out.println("FIN");
		System.out.println(cuerda.getFin().toString());
		System.out.println("****************************************");

	}
}