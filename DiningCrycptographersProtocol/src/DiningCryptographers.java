
public class DiningCryptographers {
	private int nodes;
	private Topography topography;
	private int[][] network;

	public DiningCryptographers(int nodes, Topography topography) {
		this.nodes = nodes;
		this.topography = topography;
		this.network = new int[nodes][nodes];
		if (topography == Topography.RING) {
			createRingTopography();
			return;
		}
		createNetTopography();
	}

	private void createNetTopography() {
		for (int i = 0; i < this.nodes; i++) {
			for (int j = 0; j < this.nodes; j++) {
				if (i != j) {
					this.network[i][j] = 1;
				}
			}
		}
	}

	private void createRingTopography() {
		for (int i = 0; i < this.nodes; i++) {
			this.network[i][(i - 1) % i] = this.network[i][(i + 1) % i] = 1;
		}
	}

}