package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.*;

/**
 * Singleton class used to create game states.
 */
class GameStateFactory {
    private static GameStateFactory gameStateFactory;

    static GameStateFactory getInstance() {
        if (gameStateFactory == null)
            gameStateFactory = new GameStateFactory();
        return gameStateFactory;
    }

    /**
     * Create a game state from a board and a chosen MrX move
     * @param board the board
     * @param mrXMove the chosen MrX move
     * @return
     */
    GameState createGameState(final Board board, final Move mrXMove) {
        Player mrX = PlayerFactory.getInstance().createMrX(board, mrXMove);
        List<Player> detectives = PlayerFactory.getInstance().createDetectives(board);
        return new GameState(mrX, detectives, false, mrXMove);
    }

    /**
     * Return a collection of all the possible game states for the next turn
     * @param gameState current game state
     * @return possible game states for the next turn
     */
    Collection<GameState> nextGameStates(final GameState gameState) {
        if (gameState.isMrXTurn())
            return nextGameStatesMrXTurn(gameState);
        else
            return nextGameStatesDetectivesTurn(gameState);
    }

    /**
     * Generates all the game states where MrX is to move.
     * @param gameState current game state
     * @return possible game states with detectives to move
     */
    private Collection<GameState> nextGameStatesMrXTurn(final GameState gameState) {
        if (!gameState.isMrXTurn()) throw new IllegalArgumentException();
        Collection<GameState> nextGameStates = new ArrayList<>();
        for (Move move : MoveGenerationFactory.getInstance().generateMrXMoves(gameState)) {
            Player mrX = PlayerFactory.getInstance().moveMrX(gameState.getMrX(), move);
            List<Player> detectives = gameState.getDetectives();
            nextGameStates.add(new GameState(mrX, detectives, false, move));
        }
        return nextGameStates;
    }

    /**
     * Generates all the game states where the detectives are to move.
     * @param gameState current game state
     * @return possible game states with MrX to move
     */
    private Collection<GameState> nextGameStatesDetectivesTurn(final GameState gameState) {
        Collection<GameState> nextGameStates = new ArrayList<>();
        List<Map<Player, Move>> allDetectiveMoves = MoveGenerationFactory.getInstance().generateDetectivesMoves(gameState);
        for (Map<Player, Move> detectiveMoves : allDetectiveMoves) {
            Player mrX = gameState.getMrX();
            List<Player> detectives = PlayerFactory.getInstance()
                    .moveDetectives(gameState.getDetectives(), detectiveMoves);
            nextGameStates.add(new GameState(mrX, detectives, true, null));
        }
        return nextGameStates;
    }
}
