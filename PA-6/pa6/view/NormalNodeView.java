package pa6.view;

import pa6.model.Node;

class NormalNodeView extends NodeView {

    NormalNodeView(Node square) {
        super(square);
        _rectangle.getStyleClass().add("square-normal");
    }
}
