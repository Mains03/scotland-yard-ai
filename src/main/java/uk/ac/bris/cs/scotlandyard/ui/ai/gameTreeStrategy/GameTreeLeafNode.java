package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Objects;
import java.util.Optional;

public abstract class GameTreeLeafNode<T> extends GameTree<T> {
    private final T data;

    public GameTreeLeafNode(T data) {
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public Optional<Move> accept(GameTreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public T getData() { return data; }

    public interface StaticEvalStrategy<T> {
        int staticEvaluation(T data);
    }
}
