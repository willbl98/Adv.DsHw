package pa6.view;

import pa6.model.Node;

class ExtraNodeView extends NodeView {
    ExtraNodeView(Node node) {
        super(node);
        _rectangle.getStyleClass().add("square-extra");
    }
}
