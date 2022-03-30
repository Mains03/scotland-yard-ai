package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

public class MinimumDistanceLookupTable {
    private int minimumDistances[];

    public MinimumDistanceLookupTable(int nodeCount) {
        minimumDistances = new int[nodeCount+1];
        for (int i=0; i<minimumDistances.length; ++i)
            minimumDistances[i] = -1;
    }

    public boolean foundMinimumDistance(int node) {
        return minimumDistances[node] >= 0;
    }

    public int getMinimumDistance(int node) {
        return minimumDistances[node];
    }

    public void setMinimumDistance(int node, int distance) {
        minimumDistances[node] = distance;
    }

    public void clearDistances() {
        for (int i=0; i<minimumDistances.length; ++i)
            minimumDistances[i] = -1;
    }
}
