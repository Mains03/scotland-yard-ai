package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

/**
 * Adapter for Board. Provides functionality for generating the MrX player.
 */
public interface MinimumDistanceBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    ImmutableSet<MinimumDistanceMove> getAvailableMoves();

    MinimumDistancePlayer getMrX();

    ImmutableList<MinimumDistancePlayer> getDetectives();
}
