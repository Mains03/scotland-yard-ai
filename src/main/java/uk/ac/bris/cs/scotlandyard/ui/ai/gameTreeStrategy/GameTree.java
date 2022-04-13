package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

public abstract class GameTree<T> {
    public abstract void accept(GameTreeVisitor<T> visitor);
}
