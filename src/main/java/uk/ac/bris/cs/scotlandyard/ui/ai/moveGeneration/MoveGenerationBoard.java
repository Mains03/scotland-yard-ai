package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

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

    List<Integer> getDetectiveLocations();
}
