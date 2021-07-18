import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int N;
    private int[][] blocks;

    public Board(int[][] b) {
    	N = b.length;
    	//Creation du tableau
    	blocks = new int[N][N];
    	//Copie du tableau
    	for(int i=0;i<N;i++) {
    		for(int j=0;j<N;j++) {
    			blocks[i][j] = b[i][j];
    		}
    	}
    	
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
    	int k=0;
    	for(int i=0;i<N;i++) {
    		for(int j=0;j<N;j++) {
    			if((i== N-1)&&(j== N-1)) {
    				if(blocks[i][j] != 0) {
    					k++;
    					continue;
    				}
    			}
    			if (blocks[i][j] != i*N+(1+j)) k++;
    		}
    	}
        return k-1;
    }
    
    public int manhattan() {
    	Board goalBoard = new Board(blocks);
    	for(int x=0;x<N;x++) {
			for(int y=0;y<N;y++) {
				goalBoard.blocks[x][y] = 3*x+1+y;
			}
    	}
    	int sum_manh=0;
    	int val = 0;
    		for(int i=0;i<N;i++) {
    			for(int j=0;j<N;j++) {
    				val = this.blocks[i][j];
    				if ((val !=0) && (val != goalBoard.blocks[i][j])) {
    					for(int k=0;k<N;k++) {
    		    			for(int l=0;l<N;l++) {
    		    				if(goalBoard.blocks[k][l] == val) {
    		    					sum_manh += Math.abs(i-k) + Math.abs(j-l);
    		    					break;
    		    				}
    		    			}
    		    		}
    				}		
    			}			
    		}
        return sum_manh;
    }

    public boolean isGoal() { 
    	if (this.hamming() == 0) return true;
    	else return false;
    }
    
    public Board twin() {
    	Board twin = new Board(blocks);
    	Random rand = new Random();
    	int temp = 0;
    	int k = 0;
    	int i = 0;
    	int l = 0;
    	for(int j=0;j<N;j++) {
    		if(twin.blocks[i][j] == 0) {
    				i++;
    				j = 0;
    		}
    	}
    	k = Math.round(rand.nextFloat()*(N-1));
    	temp = twin.blocks[i][k];
    	if(k+1<N) {
    		l=k+1;
    		twin.blocks[i][k] = twin.blocks[i][l];
    	}
    	else if(k-1>=0) {
    		l=k-1;
    		twin.blocks[i][k] = twin.blocks[i][l];
    	}
    	twin.blocks[i][l] = temp;	
        return twin;
    }

    public boolean equals(Object that) {
        if (that instanceof Board) {
        	Board b = (Board)that; 
        	if (b.N != N) return false;
        	for(int i=0;i<N;i++) {
        		for(int j=0;j<N;j++) {
        			if (b.blocks[i][j] != blocks[i][j]) return false;
        		}
        	}
        } 
        return true;
    }

    public Iterable<Board> neighbors() {
    	ArrayList<Board> l = new ArrayList<Board>();
    	//Je crée un tableau de taille N pour stocker les indices i j de la case 0 du board
    	int[] caseVide = new int [2];
    	for(int i=0; i<N; i++) {
    		for(int j=0; j<N; j++) {
    			if(blocks[i][j]==0) {
    				caseVide[0]=i;
    				caseVide[1]=j;
    			}
    		}
    	}
    	//On a au maximum 4 boards voisins
    	if(caseVide[0]-1>=0) {
    		Board voisin1 = new Board(this.blocks);
    		//Si je peux me deplacer vers le haut, créer le tableau voisin pour ce déplacement et
    		//ajouter à  la liste
    		voisin1.blocks[caseVide[0]][caseVide[1]] = voisin1.blocks[caseVide[0]-1][caseVide[1]];
    		voisin1.blocks[caseVide[0]-1][caseVide[1]] = 0;
    		l.add(voisin1);
    	}
    	if(caseVide[0]+1<N) {
    		Board voisin2 = new Board(this.blocks);
    		//Si je peux me deplacer vers le bas, créer le tableau voisin pour ce déplacement et
    		//ajouter à  la liste
    		voisin2.blocks[caseVide[0]][caseVide[1]] = voisin2.blocks[caseVide[0]+1][caseVide[1]];
    		voisin2.blocks[caseVide[0]+1][caseVide[1]] = 0;
    		l.add(voisin2);
    	}
    	if(caseVide[1]-1>=0) {
    		Board voisin3 = new Board(this.blocks);
    		//Si je peux me deplacer à gauche, créer le tableau voisin pour ce déplacement et
    		//ajouter à  la liste
    		voisin3.blocks[caseVide[0]][caseVide[1]] = voisin3.blocks[caseVide[0]][caseVide[1]-1];
    		voisin3.blocks[caseVide[0]][caseVide[1]-1] = 0;
    		l.add(voisin3);
    	}
    	if(caseVide[1]+1<N) {
    		Board voisin4 = new Board(this.blocks);
    		//Si je peux me deplacer à droite, créer le tableau voisin pour ce déplacement et
    		//ajouter à  la liste
    		voisin4.blocks[caseVide[0]][caseVide[1]] = voisin4.blocks[caseVide[0]][caseVide[1]+1];
    		voisin4.blocks[caseVide[0]][caseVide[1]+1] = 0;
    		l.add(voisin4);
    	}
    	return l;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) {
    	//Board initial = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
    	//Board initial = new Board(new int[][]{{2, 5, 6}, {4, 1, 3}, {0, 8, 7}});
    	/*Board initial = new Board(new int[][]{
    		 {1,  2,  3,  4,  5,  6,  7,  8,  9, 10},
    		{11, 12, 13, 14, 15, 16, 17, 18, 19, 20}, 
    		{21, 22, 23, 24, 25, 26, 27, 28, 29, 30}, 
    		{31, 32, 33, 34, 35, 36, 37, 38, 39, 40}, 
    		{41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
    		{51, 52, 53, 54, 55, 56, 57, 58, 59, 60}, 
    		{61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
    		{71, 72, 73, 74, 75, 76, 77, 78, 79, 80}, 
    		{81, 82, 83, 84, 85, 86, 87, 88, 89, 90}, 
    		{91, 92, 93, 94, 95, 96, 97 ,98, 99,  0}});
    	*/
    	Board initial = new Board(new int[][]{
    		{1,  2,  3,  4,  5,  6,  7,  8,  9}, 
    		{10, 11, 12, 13, 14, 15, 16, 17, 18}, 
    		{19, 20, 21, 22, 23, 24, 25, 26, 27}, 
    		{28, 29, 30, 31, 32, 33, 34, 35, 36}, 
    		{37, 38, 39, 40, 41, 42, 43, 44, 45}, 
    		{46, 47, 48, 49, 50, 51, 52, 53, 54}, 
    		{55, 56, 57, 58, 59, 60, 61, 62, 63}, 
    		{64, 65, 66, 67, 68, 69, 70,  0, 71}, 
    		{73, 74, 75, 76, 77, 78, 79, 80, 72}});

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}


