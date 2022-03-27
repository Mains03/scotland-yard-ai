package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.*;
import java.util.stream.Collectors;

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
        return new GameState(board.getSetup().graph, mrX, detectives, false, List.of(mrXMove));
    }

    /**
     * Return a collection of all the possible game states for the next turn
     * @param gameState current game state
     * @return possible game states for the next turn
     */
    List<GameState> nextGameStates(final GameState gameState) {
        if (gameState.isMrXTurn()) {
            List<List<Move>> moveList = List.of(gameState.getAvailableMoves());
            return constructGameStates(gameState, moveList);
        } else {
            Map<Piece, List<Move>> availableMoves = new HashMap<>();
            for (Move move : gameState.getAvailableMoves()) {
                if (availableMoves.containsKey(move.commencedBy()))
                    availableMoves.get(move.commencedBy()).add(move);
                else
                    availableMoves.put(move.commencedBy(), new ArrayList<>(List.of(move)));
            }
            List<List<Move>> moveList = createDetectivesMoveList(
                    gameState.getDetectives().stream().map(Player::piece).collect(Collectors.toList()),
                    availableMoves,
                    new ArrayList<>()
            );
            return constructGameStates(gameState, moveList);
        }
    }

    /**
     * Recursively generates all combinations of the detective moves.
     * @param remaining detectives yet to move
     * @param currentMoveList move list of detectives who have moved
     * @return move list
     */
    private List<List<Move>> createDetectivesMoveList(
            final List<Piece> remaining,
            final Map<Piece, List<Move>> availableMoves,
            final List<List<Move>> currentMoveList
    ) {
        for (Piece piece : remaining) {
            if (currentMoveList.size() == 0) {
                for (Move move : availableMoves.get(piece))
                    currentMoveList.add(new ArrayList<>(List.of(move)));
            } else {
                List<List<Move>> newMoveList = new ArrayList<>();
                for (Move move : availableMoves.get(piece)) {
                    for (List<Move> moveList : currentMoveList) {
                        if (!hasSameDestination(moveList, move)) {
                            newMoveList.add(moveList);
                            newMoveList.get(newMoveList.size()-1).add(move);
                        }
                    }
                }
                List<Piece> newRemaining = List.copyOf(remaining);
                newRemaining.remove(piece);
                currentMoveList.addAll(createDetectivesMoveList(newRemaining, availableMoves, newMoveList));
            }
        }
        return currentMoveList;
    }

    /**
     * Checks if the given move has the same destination as a move in the move list.
     * @param moveList the move list
     * @param move the new move
     * @return whether the same destination is present
     */
    private boolean hasSameDestination(final List<Move> moveList, final Move move) {
        int moveDestination = moveDestination(move);
        for (Move moveListMove : moveList) {
            if (moveDestination(moveListMove) == moveDestination)
                return true;
        }
        return false;
    }

    /**
     * Creates the game state associated to each list of moves.
     * @param gameState the game state
     * @param moveList the list of moves for players to make
     * @return the new game states
     */
    private List<GameState> constructGameStates(final GameState gameState, final List<List<Move>> moveList) {
        List<GameState> gameStates = new ArrayList<>();
        for (List<Move> moves : moveList) {
            Player mrX = gameState.getMrX();
            List<Player> detectives = gameState.getDetectives();
            for (Move move : moves) {
                int destination = moveDestination(move);
                if (move.commencedBy().isMrX())
                    mrX = mrX.use(move.tickets()).at(destination);
                else {
                    mrX = mrX.give(move.tickets());
                    for (int i=0; i<detectives.size(); ++i) {
                        if (detectives.get(i).piece() == move.commencedBy())
                            detectives.set(i, detectives.get(i).use(move.tickets()).at(destination));
                    }
                }
            }
            gameStates.add(new GameState(
                    gameState.getGraph(),
                    mrX,
                    detectives,
                    !gameState.isMrXTurn(),
                    moves
            ));
        }
        return gameStates;
    }

    /**
     * Destination of a move.
     * @param move the move
     * @return the destination
     */
    private int moveDestination(final Move move) {
        return move.accept(new Move.Visitor<>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }
}
