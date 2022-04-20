package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

/**
 * Generic since different visitors require different data.
 */
public abstract class GameTreeVisitor {
    public abstract int visit(GameTreeInnerNode innerNode);

    public abstract int visit(GameTreeLeafNode leafNode);
}
