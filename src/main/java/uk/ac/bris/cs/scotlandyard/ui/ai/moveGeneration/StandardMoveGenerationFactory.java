package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.Set;

/**
 * Singleton class to generate moves for a player.
 */
public class StandardMoveGenerationFactory implements MoveGenerationFactory {
    private static StandardMoveGenerationFactory moveGenerationFactory;

    public static StandardMoveGenerationFactory getInstance() {
        if (moveGenerationFactory == null)
            moveGenerationFactory = new StandardMoveGenerationFactory();
        return moveGenerationFactory;
    }

    private StandardMoveGenerationFactory() {}

    @Override
    public Set<Move> generateMoves(MoveGenerationBoard board, Player player) {
        return null;
    }


}
