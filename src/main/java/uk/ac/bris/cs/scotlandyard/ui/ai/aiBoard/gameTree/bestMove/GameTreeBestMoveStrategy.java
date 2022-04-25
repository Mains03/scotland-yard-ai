package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.Node;

public interface GameTreeBestMoveStrategy extends Node.Visitor<Move> {
}
