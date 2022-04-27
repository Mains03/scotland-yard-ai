package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

public interface Node {
    interface Visitor<T> {
        T visit(AbstractInnerNodeWithMove node);

        T visit(AbstractInnerNode node);

        T visit(AbstractLeafNodeWithMove node);

        T visit(AbstractLeafNode node);
    }

    <T> T accept(Visitor<T> visitor);
}
