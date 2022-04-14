package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiPlayer.AiPlayerAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.minimumDistance.DijkstraWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.minimumDistance.MinimumDistance;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A state of the game, either MrX to move or all detectives to move.
 */
class GameState {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player mrX;
    private final List<Player> detectives;
    private final List<Move> moves; // moves made to get to this state of the game
    private final List<GameState> nextGameStates;

    /**
     * Creates a GameState instance applying the moves as appropriate
     * @param graph the game board
     * @param mrX MrX
     * @param detectives the detectives
     * @param mrXMove whether MrX is to move
     * @param moves the moves made to get from the previous game state to this one
     */
    GameState(

            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player mrX,
            final List<Player> detectives,
            final boolean mrXMove,
            final int depth,
            final List<Move> moves
    ) {

        Objects.requireNonNull(graph);
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        Objects.requireNonNull(moves);

        // move all the players as appropriate
        Player newMrX = new Player(mrX.piece(), mrX.tickets(), mrX.location());
        List<Player> newDetectives = detectives.stream()
                .map(detective -> new Player(detective.piece(), detective.tickets(), detective.location()))
                .collect(Collectors.toList());
        for (Move move : moves) {
            int destination = getMoveDestination(move);
            if (move.commencedBy().isMrX())
                newMrX = newMrX.use(move.tickets()).at(destination);
            else {
                newMrX = newMrX.give(move.tickets());
                for (int i=0; i<newDetectives.size(); ++i) {
                    if (newDetectives.get(i).piece() == move.commencedBy()) {
                        Player newDetective = newDetectives.get(i).use(move.tickets()).at(destination);
                        newDetectives.set(i, newDetective);
                    }
                }
            }
        }
        this.graph = graph;
        this.mrX = newMrX;
        this.detectives = newDetectives;
        this.moves = moves;
        if (depth > 0) {
            nextGameStates = createNextGameStates(
                    graph, mrX, detectives, mrXMove, depth
            );
        } else
            nextGameStates = new ArrayList<>();
    }

    /**
     *
     * @param move the move
     * @return destination of the move
     */
    private int getMoveDestination(final Move move) {
        // Use visitor pattern as Move can be SingleMove or DoubleMove
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

    /**
     * Creates the possible game states after this one.
     * @return the possible game states
     */
    private List<GameState> createNextGameStates(
            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player mrX,
            final List<Player> detectives,
            final boolean mrXMove,
            final int depth
    ) {
        // determine which players are to move
        List<Player> players = detectives;
        if (mrXMove) players = List.of(mrX);
        // construct the game states from the available moves
        return getAvailableMoves(graph, mrX, detectives, players).stream()
                .map(moveList -> new GameState(graph, mrX, detectives, !mrXMove, depth-1, moveList))
                .collect(Collectors.toList());
    }

    /**
     * Creates the possible moves the players can each make simultaneously.
     * @param graph board graph
     * @param mrX MrX
     * @param detectives detectives
     * @param playersToMove players to move
     * @return possible moves
     */
    private List<List<Move>> getAvailableMoves(
            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player mrX,
            final List<Player> detectives,
            final List<Player> playersToMove
    ) {
        List<Player> allPlayers = new ArrayList<>(detectives);
        allPlayers.add(mrX);
        // construct the possible moves each player can make
        List<List<Move>> moveLists = playersToMove.stream()
                .map(player -> getAvailableMoves(graph, player, allPlayers))
                .collect(Collectors.toList());
        List<List<Move>> result = new ArrayList<>();
        // generate the permutations of the moves each player can make
        generateMoveCombinations(moveLists, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * Creates the possible moves a single player can make.
     * @param graph board graph
     * @param player player to move
     * @param players all players in the game
     * @return possible moves
     */
    private List<Move> getAvailableMoves(
            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player player,
            final List<Player> players
    ) {
        // generate the single moves available to the player
        List<Move> moves = new ArrayList<>();
        for (int destination : graph.adjacentNodes(player.location())) {
            boolean occupied = false;
            for (Player otherPlayer : players) {
                if (otherPlayer.location() == destination)
                    occupied = true;
            }
            if (!occupied) {
                graph.edgeValue(player.location(), destination).ifPresent(allTransport -> {
                    for (ScotlandYard.Transport transport : allTransport) {
                        if (player.has(transport.requiredTicket()))
                            moves.add(new Move.SingleMove(
                                    player.piece(), player.location(), transport.requiredTicket(), destination
                            ));
                    }
                });
            }
        }
        return moves;
    }

    /**
     * Creates combinations of the given move lists.
     * @param moveLists moves each player can make simultaneously
     * @param index index of moveLists
     * @param current current moveList being created
     * @param result the permutations
     */
    private void generateMoveCombinations(final List<List<Move>> moveLists, int index, List<Move> current, List<List<Move>> result) {
        if (index == moveLists.size())
            result.add(current);
        else {
            for (Move move : moveLists.get(index)) {
                List<Move> newCurrent = new ArrayList<>(current);
                newCurrent.add(move);
                generateMoveCombinations(moveLists, index + 1, newCurrent, result);
            }
        }
    }

    /**
     *
     * @return the moves made
     */
    List<Move> getMoves() { return moves; }
    List<GameState> nextGameStates() { return nextGameStates; }

    /**
     * Statically evaluates the game at this state.
     * @return integer evaluation of the position
     */
    int staticEvaluation() {
        // return min distance between detectives and MrX
        int mrXLocation = mrX.location();
        MinimumDistance minDistCalc = new DijkstraWithTickets(graph);
        AiPlayer aiMrX = new AiPlayerAdapter(graph, mrX);
        Optional<Integer> minDist = detectives.stream()
                .map(player -> minDistCalc.minimumDistance(aiMrX, new AiPlayerAdapter(graph, player)))
                .min(Integer::compareTo);
        if (minDist.isPresent())
            return minDist.get();
        else
            return 10000000;
    }
}
