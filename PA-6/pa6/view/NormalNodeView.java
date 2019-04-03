package pa6.view;

import pa6.model.Node;

/**
 * White colored nodes
 */
class NormalNodeView extends NodeView {
    NormalNodeView(Node square) {
        super(square);
        _rectangle.getStyleClass().add("square-normal");
    }
}
