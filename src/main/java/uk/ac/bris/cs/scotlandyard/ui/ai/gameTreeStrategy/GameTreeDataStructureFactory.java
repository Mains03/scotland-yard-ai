package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;

/**
 * Factory to create a game tree from the baord.
 */
public interface GameTreeDataStructureFactory {
    GameTreeDataStructure createGameTree(Board board);
}
