package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.MinimumDistance;

import java.util.*;

/**
 * A state of the game, either MrX to move or all detectives to move.
 */
class GameState {
    private final Player mrX;
    private final List<Player> detectives;

    private final boolean mrXTurn;

    private final Move move;

    /**
     * GameState constructor
     * @param mrX MrX
     * @param detectives detectives
     * @param mrXTurn whether MrX is to move
     */
    GameState(final Player mrX, final List<Player> detectives, final boolean mrXTurn, final Move move) {
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        this.mrX = mrX;
        this.detectives = detectives;
        this.mrXTurn = mrXTurn;
        this.move = move;
    }

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

    Move getMove() {
        return move;
    }
}
