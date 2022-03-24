package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.*;

class GameStateFactory {
    private static GameStateFactory gameStateFactory;

    static GameStateFactory getInstance() {
        if (gameStateFactory == null)
            gameStateFactory = new GameStateFactory();
        return gameStateFactory;
    }

    GameState createGameState(final Board board, final Move mrXMove) {
        Player mrX = PlayerFactory.getInstance().createMrX(board, mrXMove);
        List<Player> detectives = PlayerFactory.getInstance().createDetectives(board);
        return new GameState(mrX, detectives, false) {
            @Override
            Move getMove() {
                return mrXMove;
            }
        };
    }

    Collection<GameState> nextGameStates(final GameState gameState) {
        if (gameState.isMrXTurn())
            return nextGameStatesMrXTurn(gameState);
        else
            return nextGameStatesDetectivesTurn(gameState);
    }

    private Collection<GameState> nextGameStatesMrXTurn(final GameState gameState) {
        Collection<GameState> nextGameStates = new ArrayList<>();
        for (Move move : mrXMoves(gameState)) {
            Player mrX = PlayerFactory.getInstance().moveMrX(gameState.getMrX(), move);
            List<Player> detectives = gameState.getDetectives();
            nextGameStates.add(new GameState(mrX, detectives, false) {
                @Override
                Move getMove() {
                    throw new NoSuchElementException();
                }
            });
        }
        return nextGameStates;
    }

    private Iterable<Move> mrXMoves(final GameState gameState) {
        return null;
    }

    private Collection<GameState> nextGameStatesDetectivesTurn(final GameState gameState) {
        Collection<GameState> nextGameStates = new ArrayList<>();
        for (Map<Player, Move> detectiveMoves : detectiveMoves(gameState)) {
            Player mrX = gameState.getMrX();
            List<Player> detectives = PlayerFactory.getInstance()
                    .moveDetectives(gameState.getDetectives(), detectiveMoves);
            nextGameStates.add(new GameState(mrX, detectives, true) {
                @Override
                Move getMove() {
                    throw new NoSuchElementException();
                }
            });
        }
        return nextGameStates;
    }

    private Iterable<Map<Player, Move>> detectiveMoves(final GameState gameState) {
        return null;
    }
}
