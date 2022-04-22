package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Set;

/**
 * Interface for move generation
 */
public interface MoveGenerationFactory {
    Set<Move> generateMoves(MoveGenerationBoard board, Player player);
}