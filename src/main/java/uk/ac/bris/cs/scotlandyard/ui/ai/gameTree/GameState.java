package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.MinimumDistance;

import java.util.*;

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
     * Creates a GameState instance
     * @param graph the game board
     * @param mrX MrX
     * @param detectives the detectives
     * @param moves the moves made to get from the previous game state to this one
     */
    GameState(
            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player mrX,
            final List<Player> detectives,
            final List<Move> moves
    ) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        Objects.requireNonNull(moves);
        this.graph = graph;
        this.mrX = mrX;
        this.detectives = detectives;
        this.moves = moves;
        this.nextGameStates = createNextGameStates();
    }

    /**
     * Creates the possible game states after this one.
     * @return the possible game states
     */
    private List<GameState> createNextGameStates() {
        return null;
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
        Optional<Integer> minDist = detectives.stream()
                .map(Player::location)
                .map(location -> MinimumDistance.getInstance()
                        .getMinimumDistance(location, mrXLocation)
                ).min(Integer::compareTo);
        if (minDist.isPresent())
            return minDist.get();
        else
            return 10000000;
    }


}
