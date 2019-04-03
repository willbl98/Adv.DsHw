package pa6.view;

import pa6.model.Node;

/**
 * Transparent top 3 nodes adjacent to the headers
 */
class ExtraNodeView extends NodeView {
    ExtraNodeView(Node node) {
        super(node);
        _rectangle.getStyleClass().add("square-extra");
    }
}
