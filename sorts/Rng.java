import java.time.Instant;

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
}

