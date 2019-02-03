public class NBody {

/** Read input file. */
public static double readRadius(String file) {
	In text = new In(file);
	int total = text.readInt();
	return text.readDouble();
}

public static Planet[] ReadPlanets(String file) {
	In text  =new In(file);
	int total = text.readInt();
	double radius = text.readDouble();
	Planet[] Planets = new Planet[1]; i = 0;
	while (!text.isempty()) {
			P = new Planet(text.readDoule(), text.readDouble(), text.readDoule(),
							text.readDoule(), text.readDoule(), text.readString());
			Planets[i] = P;
			i += 1;
			Planet[] Planets1 = new Planet[Planets.length + 1];

	}
}














}