package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.List;

/**
 * Adapter for {@link Board} used in {@link MoveGenerationFactory}
 */
public interface MoveGenerationBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();
    // Only shows where there are detectives, does not specify which
    List<Integer> getDetectiveLocations();
}
