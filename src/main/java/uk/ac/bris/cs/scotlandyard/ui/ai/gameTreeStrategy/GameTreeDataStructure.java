package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;

/**
 * A node in the game tree.
 */
public interface GameTreeDataStructure {
    Player getMrX();

    List<Player> getDetectives();

    // returns the MrX move made to get to this position
    Move getMove();

    List<GameTreeDataStructure> getChildren();
}
