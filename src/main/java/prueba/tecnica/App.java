package prueba.tecnica;

import java.util.ArrayList;
import java.util.Random;


public class App {

	public static void main(String[] args) {
		// Array para elegir las 2 direcciones despues con un random
		ArrayList<String> posibilidades = new ArrayList<String>();
		posibilidades.add("este");
		posibilidades.add("oeste");
		
		Random r = new Random();
		Cuerda cuerda = new Cuerda();
		// Hilo a parte para imprimir el estado de la cuerda.
		Pintor p = new Pintor(cuerda);
		// Numero de monos que se van a generar, random entre 10 y 30
		int numMonos = r.nextInt(10) + 20;
		System.out.println("Se van a generar " + numMonos + " monos");
		// Sleep simplmente para poder visualizarlo mejor
		dormir(2);
		p.start();
		/*
		// Generamos los monos con un for
		for (int i = 0; i < numMonos; i++) {
			// Obtenemos la dirección del mono
			int randomDirección = r.nextInt(2);
			// Generamos el mono
			Mono m = new Mono(posibilidades.get(randomDirección), "Mono - " + i, cuerda);
			// Lo iniciamos
			m.start();
			// Obtenemos un numero random para dormir para generar los monos
			int randomDormir = r.nextInt(8) + 1;
			dormir(randomDormir);
		}*/
		 
		//PRUEBAS
		//Todos los monos de un mismo lugar
		/*
		for (int i = 0; i < 5; i++) {
			Mono m = new Mono("este", "Mono - " + i, cuerda);
			m.start();
		}
		
		for (int i = 0; i < 5; i++) {
			Mono m = new Mono("oeste", "Mono - " + i, cuerda);
			m.start();
		}*/
		
		//Monos del este, intercalado uno del oeste a mitad
		for (int i = 0; i < 10; i++) {
		Mono m = new Mono("este", "Mono - " + i, cuerda);
		m.start();
		if(i==5) {
			Mono m2 = new Mono("oeste", "MONO OESTE", cuerda);
			m2.start();
		}
		dormir(1);
	}/*
		
		for (int i = 0; i < 10; i++) {
		Mono m = new Mono("oeste", "Mono - " + i, cuerda);
		m.start();
		if(i==5) {
			Mono m2 = new Mono("este", "MONO ESTE", cuerda);
			m2.start();
		}
		dormir(1);
	}
		
		for (int i = 0; i < 10; i++) {
			Mono m = new Mono("oeste", "Mono - " + i, cuerda);
			m.start();
			if(i==2 || i==5) {
				Mono m2 = new Mono("este", "MONO ESTE", cuerda);
				m2.start();
			}
		}
		*/
		/*
		for (int i = 0; i < 10; i++) {
			int randomDirección = r.nextInt(2);
			Mono m = new Mono(posibilidades.get(randomDirección), "Mono - " + i, cuerda);
			m.start();
			dormir(1);
			}*/
			
		
		
/*
		for (int i = 0; i < 10; i++) {
			int randomDirección = r.nextInt(2);
			Mono m = new Mono("este", "Mono - " + i, cuerda);
			m.start();

		
		}
		Mono m = new Mono("oeste", "Mono - OESTE", cuerda);
		m.start();

		
		*/
		
		

	}

	/*
	 * Método para dormir el hilo picipal y generar los monos de forma espaciada.
	 */
	private static void dormir(int time) {
		try {
			Thread.sleep(1000 * time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
