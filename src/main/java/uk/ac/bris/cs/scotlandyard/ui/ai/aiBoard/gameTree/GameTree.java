package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited.DepthLimitedGameTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited.WinnerLimitedGameTree;

import java.util.Set;

public interface GameTree {
    interface Visitor<T> {
        T visit(DepthLimitedGameTree tree);

        T visit(WinnerLimitedGameTree tree);
    }

    <T> T accept(Visitor<T> visitor);

    Set<Node> getChildren();
}
