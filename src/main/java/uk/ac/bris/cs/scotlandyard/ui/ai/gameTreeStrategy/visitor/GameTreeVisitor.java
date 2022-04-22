package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures.InnerNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures.LeafNode;

/**
 * Returns integer evaluation of a node.
 */
public interface GameTreeVisitor {
    int visit(InnerNode node);

    int visit(LeafNode node);
}
