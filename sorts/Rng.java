import java.io.FileWriter;
import java.time.Instant;
import java.util.Random;

public class Rng {
	public int seed;

	public Rng() {
		seed = Instant.now().getNano();
	}

	public Rng(int seed) {
		this.seed = seed;
	}

	// xorshift
	public int rand_int() {
		seed ^= seed << 13;
		seed ^= seed >> 17;
		seed ^= seed << 5;
		return seed;
	}
	
	private static long rdtsc() {
		return LinkedList.rdtsc();
	}

	public static void test() {
		Rng rng = new Rng();
		long count = 0xffff;

		Random random = new Random(Instant.now().getNano());

		double avg1 = 0;
		double avg2 = 0;
		long sum = 0;

		try {
			FileWriter f1 = new FileWriter("stats/rng/xorshift");
			FileWriter f2 = new FileWriter("stats/rng/java_rand");

			f1.write("");
			f2.write("");

			String s1 = "";
			String s2 = "";

			for (int i = 0; i < 0xff; i++) {
				long tmp_avg1 = 0;
				long tmp_avg2 = 0;

				for (int a = 0; a < count; a++) {
					long start1 = rdtsc();
					sum += rng.rand_int();
					long cycles1 = (rdtsc() - start1);
					tmp_avg1 += cycles1;

					long start2 = rdtsc();
					sum += random.nextInt();
					long cycles2 = (rdtsc() - start2);
					tmp_avg2 += cycles2;
				}

				tmp_avg1 /= count;
				tmp_avg2 /= count;

				s1 = s1.concat(String.format("%d %d\n", i, tmp_avg1));
				s2 = s2.concat(String.format("%d %d\n", i, tmp_avg2));

				avg1 += tmp_avg1;
				avg2 += tmp_avg2;
			}

			f1.write(s1);
			f2.write(s2);

			f1.close();
			f2.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		avg1 /= 0xff;
		System.out.println("xorshift");
		System.out.println("cycles " + avg1);

		avg2 /= 0xff;
		System.out.println("java random");
		System.out.println("cycles " + avg2);
	}

	public static void main(String[] args) {
		test();
	}
}

