package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.List;

/**
 * Adapter for {@link Board} used in {@link MoveGenerationFactory}
 */
public interface MoveGenerationBoard {
    List<Integer> getDetectiveLocations();
}
