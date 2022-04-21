package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.InnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures.LeafNode;

/**
 * Returns integer evaluation of a node.
 */
public interface GameTreeVisitor {
    int visit(InnerNode node);

    int visit(LeafNode node);
}
