package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import java.util.Objects;

public abstract class GameTreeLeafNode<T> extends GameTree<T> {
    private final T data;

    public GameTreeLeafNode(T data) {
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public void accept(GameTreeVisitor<T> visitor) {
        visitor.visit(this);
    }

    public T getData() { return data; }

    public interface StaticEvalStrategy<T> {
        int staticEvaluation(T data);
    }
}
