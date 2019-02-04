public class Planet {
	/** Class Variables. */
	public static final double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	/** Instance Constructors. */
	public Planet(double xP, double yP, double xV,
					double yV, double m, String img) {
		xxPos = xP; yyPos = yP; xxVel = xV;
		yyVel = yV; mass = m; imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos; yyPos = p.yyPos; xxVel = p.xxVel;
		yyVel = p.yyVel; mass = p.mass; imgFileName = p.imgFileName;
	}

	/** Calculate Distance. */
	public double calcDistance(Planet p) {
		return Math.pow((Math.pow((p.xxPos - xxPos), 2) + Math.pow((p.yyPos - yyPos), 2)), 0.5);
	}

	/** Calculate Force. */
	public double calcForceExertedBy(Planet p) {
		return G * mass * p.mass / Math.pow(calcDistance(p), 2);
	}

	public double calcForceExertedByX(Planet p) {
		double dx = p.xxPos - xxPos;
		return calcForceExertedBy(p) * dx / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		double dy = p.yyPos - yyPos;
		return calcForceExertedBy(p) * dy / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet p[]) {
		double F = 0;
		for (Planet x:p) {
			if (!x.equals(this)) {
				F += calcForceExertedByX(x);
			}
		}
		return F;
	}
	public double calcNetForceExertedByY(Planet p[]) {
		double F = 0;
		for (Planet x:p) {
			if (!x.equals(this)) {
				F += calcForceExertedByY(x);
			}
		}
		return F;
	}

	/**Update Object position and velosity. */
	public void update(double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;
		xxVel += aX * dt;
		yyVel += aY * dt;
		xxPos += dt * xxVel;
		yyPos += dt * yyVel;
	}
	/** Draw planet itself on the background. */
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}

}

