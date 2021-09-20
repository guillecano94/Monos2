package prueba.tecnica;

public class Mono extends Thread {

	private String direccion;
	private String idMono;
	private Cuerda cuerda;

	public Mono(String direccion, String idMono, Cuerda cuerda) {
		this.direccion = direccion;
		this.idMono = idMono;
		this.cuerda = cuerda;
	}

	public void run() {
		// Método para que el hilo se encole en su cola
		cuerda.encolar(this);
		// Método principal para cruzar
		cuerda.cruzar(this);
		cuerda.subirCuerda(this); 
		cuerda.desplazar(this);
		// Simulamos el rato que está cruzando la cuerda
		dormir(4);
		// Método para salir
		cuerda.salir(this);

	}

	/*
	 * Método para dormir un hilo el tiempo que le indique por parámetro
	 */
	public void dormir(int tiempo) {
		try {
			sleep(1000 * tiempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getIdMono() {
		return idMono;
	}

	public void setId(String idMono) {
		this.idMono = idMono;
	}

	public Cuerda getCuerdaDefinitiva() {
		return cuerda;
	}

	public void setCuerdaDefinitiva(Cuerda cuerda) {
		this.cuerda = cuerda;
	}

	public void setIdMono(String idMono) {
		this.idMono = idMono;
	}

	@Override
	public String toString() {
		return idMono + " - " + direccion;
	}

}
