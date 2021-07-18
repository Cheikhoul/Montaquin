public class Solver {
    
    private static class SearchNode implements Comparable<SearchNode> {
    	
    	private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode parent;
        
        public SearchNode(SearchNode parent, Board board, int moves, int priority) {
        
        	this.parent = parent;
          	this.board = board;
           	this.moves = moves;
        	this.priority = priority;
        	
        }
        
        @Override
        public int compareTo(SearchNode searchNode) {
        	
        	if (priority < searchNode.priority)
                return -1;
            if (priority > searchNode.priority)
                return 1;
            return 0;
            
        }
    }

    private final Board initialBoard;
    private boolean solvable = false;
    private SearchNode solution = null;
    
    public Solver(Board initial) {
    	
    	this.initialBoard = initial;
        aStar();
    }

    private void aStar() {
    	
    	   MinPQ<SearchNode> minPQ1 = new MinPQ<SearchNode>();
           MinPQ<SearchNode> minPQ2 = new MinPQ<SearchNode>();
           minPQ1.insert(new SearchNode(null, initialBoard, 0, heuristic(initialBoard)));
           Board twin = initialBoard.twin();
           minPQ2.insert(new SearchNode(null, twin, 0, heuristic(twin)));

           SearchNode current1 = minPQ1.delMin();
           SearchNode current2 = minPQ2.delMin();
           while (!current1.board.isGoal() && !current2.board.isGoal()) {
               addNext(minPQ1, current1);
               addNext(minPQ2, current2);
               current1 = minPQ1.delMin();
               current2 = minPQ2.delMin();
           }
           if (current1.board.isGoal()) {
               solvable = true;
               solution = current1;
           }
    }
    
    private void addNext(MinPQ<SearchNode> minPQ, SearchNode current) {
        for (Board next : current.board.neighbors()) {
            if (current.parent == null || !next.equals(current.parent.board)) {
                minPQ.insert(new SearchNode(current, next, current.moves + 1,
                        current.moves + 1 + heuristic(next)));
            }
        }
    }
    
    private int heuristic(Board board) {
        return board.manhattan();
    }
    
    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
    	if (!solvable)
            return -1;
        return solution.moves;
    }

    public Iterable<Board> solution() {
    	if (!solvable)
            return null;
        Stack<Board> res = new Stack<Board>();
        SearchNode current = solution;
        while (current != null) {
            res.push(current.board);
            current = current.parent;
        }
        return res;
    }
   
}
