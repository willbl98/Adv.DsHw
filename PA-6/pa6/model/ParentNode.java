package pa6.model;

/**
 * Helper class used when determining the score to take and tracking where the origin/parent came from
 */
class ParentNode {
    private final Direction _direction;
    private final int _value;
    private final Node _node;

    ParentNode(Direction direction, int value, Node node) {
        _direction = direction;
        _value = value;
        _node = node;
    }

    int getValue() {
        return _value;
    }

    Direction getDirection() {
        return _direction;
    }

    Node getNode() {
        return _node;
    }
}
