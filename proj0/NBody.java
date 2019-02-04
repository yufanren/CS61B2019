public class NBody {

	/** Read input file. */
	public static double readRadius(String file) {
		In text = new In(file);
		int total = text.readInt();
		return text.readDouble();
	}

	public static Planet[] readPlanets(String file) {
		In text = new In(file);
		int total = text.readInt();
		double radius = text.readDouble();
		Planet[] Planets = new Planet[total]; 
		/** int i = 0;
			while (!text.isEmpty()) {
			Planet P = new Planet(text.readDouble(), text.readDouble(), text.readDouble(),
								text.readDouble(), text.readDouble(), text.readString());
			Planet[] Planets1 = new Planet[Planets.length + 1];
			for (int j = 0; j < Planets.length; j++) {
				Planets1[j] = Planets[j];
			}
			Planets1[i] = P;
			i += 1;
			Planets = Planets1;
		} */
		for (int i = 0; i < total; i ++) {
			Planet P = new Planet(text.readDouble(), text.readDouble(), text.readDouble(),
								text.readDouble(), text.readDouble(), text.readString());
			Planets[i] = P;
		}
		return Planets;
	}
	public static void main(String[] args) {

		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] PlanetList = readPlanets(filename);

		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Planet i:PlanetList) {
			i.draw();
		}
		StdDraw.show();
		StdDraw.pause(1500);
		

		StdDraw.enableDoubleBuffering();

		for (double time = 0; time < T; time += dt) {
			Double[] xForces = new Double[PlanetList.length];
			Double[] yForces = new Double[PlanetList.length];
			for (int i = 0; i < PlanetList.length; i ++) {
				xForces[i] = PlanetList[i].calcNetForceExertedByX(PlanetList);
				yForces[i] = PlanetList[i].calcNetForceExertedByY(PlanetList);
			}
			for (int i = 0; i < PlanetList.length; i ++) {
				PlanetList[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet i:PlanetList) {
				i.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", PlanetList.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < PlanetList.length; i ++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  PlanetList[i].xxPos, PlanetList[i].yyPos, PlanetList[i].xxVel,
                  PlanetList[i].yyVel, PlanetList[i].mass, PlanetList[i].imgFileName);   
		}
	}












}