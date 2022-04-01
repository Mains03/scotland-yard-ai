package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;

public interface GameTreeDataStructureFactory {
    GameTreeDataStructure createGameTree(Board board);
}
