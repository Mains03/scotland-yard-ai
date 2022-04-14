package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Set;

public interface MoveGenerationFactory {
    Set<Move> generateMoves(MoveGenerationBoard board, Player player);
}