package pa6.view;

import pa6.model.Node;

/**
 * Green colored nodes
 */
class OptimumNodeView extends NodeView {
    OptimumNodeView(Node square) {
        super(square);
        _rectangle.getStyleClass().add("square-optimum");
    }
}
