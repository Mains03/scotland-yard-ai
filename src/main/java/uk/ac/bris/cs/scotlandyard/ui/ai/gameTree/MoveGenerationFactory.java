package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.moves.AllMoves;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Singleton class to generate moves at a state of the game.
 */
class MoveGenerationFactory {
    private static MoveGenerationFactory moveGeneration;

    static MoveGenerationFactory getInstance() {
        if (moveGeneration == null)
            moveGeneration = new MoveGenerationFactory();
        return moveGeneration;
    }

    private MoveGenerationFactory() {}

    /**
     * Generates the possible moves for MrX at the current state of the game.
     * @param gameState the state of the game
     * @return the possible moves
     */
    List<Move> generateMrXMoves(final GameState gameState) {
        return allMoves(Piece.MrX.MRX, mrXLocation(gameState)).stream()
                .filter(move -> movePossible(gameState, move))
                .collect(Collectors.toList());
    }

    private int mrXLocation(final GameState gameState) {
        return gameState.getMrX().location();
    }

    List<Map<Player, Move>> generateDetectivesMoves(final GameState gameState) {
        return null;
    }

    private List<Move> allMoves(final Piece piece, final int location) {
        return AllMoves.getInstance().getAvailableMoves(piece, location);
    }

    private boolean movePossible(final GameState gameState, final Move move) {
        return false;
    }
}
