package pa6.view;

import pa6.model.Node;

class ColHeaderNodeView extends NodeView {

    ColHeaderNodeView(Node node) {
        super(node);
        _rectangle.getStyleClass().add("square-col-header");
    }
}
