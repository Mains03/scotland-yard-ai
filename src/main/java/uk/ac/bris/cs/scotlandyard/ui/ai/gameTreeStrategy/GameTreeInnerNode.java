package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import java.util.Set;

public abstract class GameTreeInnerNode extends GameTree {
    @Override
    public void accept(GameTreeVisitor visitor) {
        visitor.visit(this);
    }

    public abstract boolean hasChildren();

    public abstract Set<GameTree> getChildren();
}
