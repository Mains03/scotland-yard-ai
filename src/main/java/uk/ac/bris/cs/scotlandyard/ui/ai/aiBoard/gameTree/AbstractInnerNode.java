package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import java.util.Set;

public abstract class AbstractInnerNode implements Node {
    public abstract Set<Node> getChildren();

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
