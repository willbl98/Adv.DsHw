package pa6.model;

/**
 * Helper class used when determining the score to take and tracking where the origin/parent came from
 * The Parent Helper class will then contain data which is later used in backtracking
 *
 */
class ParentHelper {
    private Direction _direction;
    private int _potentialValue;
    private Node _parentNode;

    /**
     * ParentHelper(Direction, Potential Value, Parent Value);  The potential value is .
     * @param direction Direction relative to the current placement in the matrix
     * @param potentialValue parentNode's current value with added modifier. Used as child node's value if optimum
     * @param parentNode the current node's parent
     */
    ParentHelper(Direction direction, int potentialValue, Node parentNode) {
        _direction = direction;
        _potentialValue = potentialValue;
        _parentNode = parentNode;
    }

    int getValue() {
        return _potentialValue;
    }

    Direction getDirection() {
        return _direction;
    }

    Node getNode() {
        return _parentNode;
    }
}
