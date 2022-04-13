package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

public abstract class GameTreeVisitor<T> {
    public abstract void visit(GameTreeInnerNode innerNode);
    public abstract void visit(GameTreeLeafNode<T> leafNode);
}
