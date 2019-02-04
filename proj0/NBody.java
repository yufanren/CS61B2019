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
		Planet[] Planets = new Planet[5]; 
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
		for (int i = 0; i < 5; i ++) {
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
		StdDraw.show();
		StdDraw.pause(1500);

		for (Planet i:PlanetList) {
			i.draw();
		}
	}












}