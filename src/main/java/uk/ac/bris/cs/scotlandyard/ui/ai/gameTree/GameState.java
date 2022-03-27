package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
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
    private final boolean mrXTurn;
    private final List<Move> moves; // moves made to get to this state of the game
    private final List<Move> availableMoves;

    /**
     * GameState constructor
     * @param mrX MrX
     * @param detectives detectives
     * @param mrXTurn whether MrX is to move
     */
    GameState(
            final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            final Player mrX,
            final List<Player> detectives,
            final boolean mrXTurn,
            final List<Move> moves
    ) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        Objects.requireNonNull(moves);
        this.graph = graph;
        this.mrX = mrX;
        this.detectives = detectives;
        this.mrXTurn = mrXTurn;
        this.moves = moves;
        this.availableMoves = generateAvailableMoves();
    }

    private List<Move> generateAvailableMoves() {
        return null;
    }

    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() { return graph; }
    Player getMrX() { return mrX; }
    ImmutableList<Player> getDetectives() { return ImmutableList.copyOf(detectives); }
    boolean isMrXTurn() { return mrXTurn; }

    int minDistanceBetweenDetectivesAndMrX() {
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

    List<Move> getMoves() {
        return moves;
    }

    List<Move> getAvailableMoves() {
        return null;
    }
}
