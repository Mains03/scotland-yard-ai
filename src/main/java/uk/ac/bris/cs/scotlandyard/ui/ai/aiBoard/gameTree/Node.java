package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

public interface Node {
    interface Visitor<T> {
        T visit(GameTree tree);

        T visit(InnerNodeWithMove node);

        T visit(InnerNode node);

        T visit(LeafNodeWithMove node);

        T visit(LeafNode node);
    }

    <T> T accept(Visitor<T> visitor);
}
