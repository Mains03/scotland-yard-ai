package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;
import java.util.Objects;

/**
 * A node in the game tree.
 */
public abstract class GameTreeDataStructure {
    public interface Factory {
        GameTreeDataStructure createGameTree(Board board);
    }

    private final Player mrX;
    private final List<Player> detectives;
    private final Move mrXMoveMade;

    public GameTreeDataStructure(Player mrX, List<Player> detectives, Move mrXMoveMade) {
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        Objects.requireNonNull(mrXMoveMade);
        this.mrX = mrX;
        this.detectives = detectives;
        this.mrXMoveMade = mrXMoveMade;
    }

    public Player getMrX() {
        return mrX;
    }

    public List<Player> getDetectives() {
        return List.copyOf(detectives);
    }

    // returns the MrX move made to get to this position
    public Move getMove() {
        return mrXMoveMade;
    }

    public abstract List<GameTreeDataStructure> getChildren();
}
