package simulator.model;

import simulator.misc.Vector;

public class Body {
	private String id;
	private double m;
	private Vector v;
	private Vector a;
	private Vector p;
	
	//hemos hecho un constructor sin parametro aceleracion
	public Body(String id, Vector v, Vector p, double m) {
		double[] zero = {0.0, 0.0};
		this.id = id;
		this.v = v;
		this.a = new Vector(zero);
		this.p = p;
		this.m = m;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Vector getVelocity() {
		//devuelve una COPIA del vector de velocidad
		return new Vector(this.v);
	}
	
	public Vector getAcceleration() {
		return new Vector(this.a);
	}
	
	public Vector getPosition() {
		return new Vector(this.p);
	}
	
	public double getMass() {
		return this.m;
	}
	
	void setVelocity(Vector v) {
		//hace una COPIA de v y se la asigna al vector de velocidad
		this.v = new Vector(v);
	}
	
	void setAcceleration(Vector a) {
		this.a = new Vector(a);
	}
	
	
	void setPosition(Vector p) {
		this.p = new Vector(p);
	}
	
	
	void setMass(double mass) {
		this.m = mass;
	}
	
	void move(double t) {
		
		//mueve el cuerpo durante t segundos utilizando los atributos del mismo
		
		//cambia la posicion: p = p + v * t + 1/2 * a * t^2
		//y la velocidad: v = v + a * t
		
		double tCuad = t*t;
		
		this.p = p.plus(v.scale(t)).plus(a.scale(0.5).scale(tCuad));
		this.v = v.plus(a.scale(t));
	}
	
	public String toString() {
		//devuelve un string con la información del cuerpo en formato JSON:
		// { "id": id, "mass": m, "pos": p, "vel": v, "acc": a }
		
		return "{  \"id\": " + "\"" + id + "\", \"mass\": " + m + ", \"pos\": " + p.toString() + ", \"vel\": " + v.toString() + ", \"acc\": " + a.toString() + " }";
	}
}
