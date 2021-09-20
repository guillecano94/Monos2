package prueba.tecnica;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Cuerda {
	// ConcurrentLinkedQueue que contiene los monos que están cruzando
	private ConcurrentLinkedQueue<Mono> cuerda = new ConcurrentLinkedQueue<Mono>();
	// ConcurrentLinkedQueue que contiene los monos cuyo inicio es el este
	private ConcurrentLinkedQueue<Mono> colaEste = new ConcurrentLinkedQueue<Mono>();
	// ConcurrentLinkedQueue que contiene los monos cuyo inicio es el oeste
	private ConcurrentLinkedQueue<Mono> colaOeste = new ConcurrentLinkedQueue<Mono>();
	// ConcurrentLinkedQueue para mostrar los monos que han termindo
	private ConcurrentLinkedQueue<Mono> fin = new ConcurrentLinkedQueue<Mono>();
	// Varible para ver si hay monos cruzando de E-O. True si hay, false si no hay
	private boolean hayMonosCruzandoEsteOeste = false;
	// Varible para ver si hay monos cruzando de O-E
	private boolean hayMonosCruzandoOesteEste = false;
	// Variable para saber si el mono que viene del este tendría prioridad de paso.
	// Si la variable está a true, el este tiene prioridad. Si está en false, el
	// oeste es el que tiene
	// la prioridad.
	private boolean prioridadEste = true;

	/*
	 * Método para encolar los monos en su correspondiente sitio
	 */
	public void encolar(Mono m) {
		if (m.getDireccion().equals("este")) {
			colaEste.add(m);
		} else {
			colaOeste.add(m);
		}
	}

	/*
	 * Método para cruzar Este método se encarga de realizar las fonciones diferenes
	 * para que la prioridad de los monos se actualice según van llegando, que no se
	 * crucén unos sentidos con otros y por último evitar la inacición(starvation)
	 * de los hilos Ejemplo para un mono que proviene del este: Actualizará la
	 * prioridad, ya que si hay monos cruzando desde el oeste, estos tendrán que
	 * parar y dejar paso a este nuevo mono del este(evitando la inanición así),
	 * esperará a que los monos del oeste terminen y cruzará.
	 */
	public synchronized void cruzar(Mono m) {
		// Actualiamos la prioridad del mono nuevo para evitar la inanición
		actualizarPrioridad(m);
		// Si proviene del este
		if (m.getDireccion().equals("este")) {
			// El hilo entrará en espera si hay monos cruzando desde el oeste, o si no tiene
			// prioridad.
			while ((hayMonosCruzandoOesteEste || !prioridadEste)) {
				try {
					// Espera a ser llamado
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Si sale de while, significa que puede continuar su ejecución porque cumple
			// las condiciones para pasar
			// Eliminamos ese mono de su cola se encola en la cuerda.
			colaEste.remove(m);
			cuerda.add(m);
			// Tiempo de subida sin salir del método
			m.dormir(1);
			// Actualizamos la variable de hayMonosCruzandoEsteOeste a true, porque ahora
			// hay un poco subiendo
			hayMonosCruzandoEsteOeste = true;
		} else {
			while ((hayMonosCruzandoEsteOeste || prioridadEste)) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			colaOeste.remove(m);
			cuerda.add(m);
			m.dormir(1);
			hayMonosCruzandoOesteEste = true;

		}
	}

	/*
	 * Método para salir de la cuerda
	 */
	public synchronized void salir(Mono m) {
		// El mono baja de la cuerda
		cuerda.remove(m);
		// El mono se añade a una lista para contabilizar cuantos monos han terminado
		fin.add(m);
		// Actualizamos el estado de la cuerda
		actualizarEstadoCuerda();
		// Despertamos a los hilos para que puedan cruzar si cumplen con los requisitos.
		notifyAll();
	}

	/*
	 * Método para actualizar el estado de la cuerda y la prioridad. Actualizamos la
	 * prioridad en este método tambien para los últimos hilos que se quedan en la
	 * última cola no vacía.
	 */
	private synchronized void actualizarEstadoCuerda() {
		// Si la cuerda está vacia, no hay nadie, por lo que actualizamos el valor de
		// las 2 variables a false
		if (cuerda.isEmpty()) {
			hayMonosCruzandoEsteOeste = false;
			hayMonosCruzandoOesteEste = false;

		} else {
			// Si el primer mono de la cola viene del este, ponemos la variable
			// hayMonosCruzandoEsteOeste=true
			if (cuerda.peek().getDireccion().equals("este")) {
				hayMonosCruzandoEsteOeste = true;
			} else {
				// Si no viene del este, significa que viene del oeste.
				hayMonosCruzandoOesteEste = true;
			}
		}
		// Si la cola del este está vacía, damos prioridad al oeste.
		if (colaEste.isEmpty()) {
			prioridadEste = false;
		}
		// Si la cola del oeste está vacía, damos prioridad al este.
		if (colaOeste.isEmpty()) {
			prioridadEste = true;
		}

	}

	/*
	 * Método al que llaman de primeras los hilos para actualizar la prioridad de la
	 * cola y evitar la inanición.
	 */
	private synchronized void actualizarPrioridad(Mono m) {
		// Si la cuerda esta vacía, damos prioridad al primer mono que llegue
		// Si viene del este --> prioridadEste= este.equeals(este); que es true
		// Si viniera del oeste--> prioridadEste= oeste.equals(este); que sería false
		if (cuerda.isEmpty()) {
			prioridadEste = m.getDireccion().equals("este");

		} else {
			// Si los monos que hay en la cuerda son dirección contraría al mono que quiere
			// pasr, se le da prioridad al mono que quiere pasar y se cambia el estado de
			// prioridadEste al del mono.
			if (!cuerda.peek().getDireccion().equals(m.getDireccion())) {
				prioridadEste = m.getDireccion().equals("este");
			}
		}
	}

	public ConcurrentLinkedQueue<Mono> getCuerda() {
		return cuerda;
	}

	public void setCuerda(ConcurrentLinkedQueue<Mono> cuerda) {
		this.cuerda = cuerda;
	}

	public ConcurrentLinkedQueue<Mono> getColaEste() {
		return colaEste;
	}

	public void setColaEste(ConcurrentLinkedQueue<Mono> colaEste) {
		this.colaEste = colaEste;
	}

	public ConcurrentLinkedQueue<Mono> getColaOeste() {
		return colaOeste;
	}

	public void setColaOeste(ConcurrentLinkedQueue<Mono> colaOeste) {
		this.colaOeste = colaOeste;
	}

	public boolean isHayMonosCruzandoEsteOeste() {
		return hayMonosCruzandoEsteOeste;
	}

	public void setHayMonosCruzandoEsteOeste(boolean hayMonosCruzandoEsteOeste) {
		this.hayMonosCruzandoEsteOeste = hayMonosCruzandoEsteOeste;
	}

	public boolean isHayMonosCruzandoOesteEste() {
		return hayMonosCruzandoOesteEste;
	}

	public void setHayMonosCruzandoOesteEste(boolean hayMonosCruzandoOesteEste) {
		this.hayMonosCruzandoOesteEste = hayMonosCruzandoOesteEste;
	}

	public boolean isPrioridadEste() {
		return prioridadEste;
	}

	public void setPrioridadEste(boolean prioridadEste) {
		this.prioridadEste = prioridadEste;
	}

	public ConcurrentLinkedQueue<Mono> getFin() {
		return fin;
	}

	public void setFin(ConcurrentLinkedQueue<Mono> fin) {
		this.fin = fin;
	}

}