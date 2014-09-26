
/* Don't forget to change this line to cs540.checkers.<username> */
package cs540.checkers.cong;

import cs540.checkers.*;
import static cs540.checkers.CheckersConsts.*;

import java.util.*;

/*
 * This is a skeleton for an alpha beta checkers player. Please copy this file
 * into your own directory, i.e. src/<username>/, and change the package 
 * declaration at the top to read
 *     package cs540.checkers.<username>;
 * , where <username> is your cs department login.
 */
/** This is a skeleton for an alpha beta checkers player. */
public class CongPlayer extends CheckersPlayer implements GradedCheckersPlayer
{
	/** The number of pruned subtrees for the most recent deepening iteration. */
	protected int pruneCount;
	protected Evaluator sbe;
	protected int lastPrunedNodeScore;
	protected int lastPruneScore;
	protected Move bestMove;
	//static int currentLevel = 0; //the current level of searching
	public CongPlayer(String name, int side)
	{ 
		super(name, side);
		// Use SimpleEvaluator to score terminal nodes
		sbe = new CongEvaluator();
	}

	public void calculateMove(int[] bs){

		int currentLevel = 1;
		int bestscore = Integer.MIN_VALUE;
		while(currentLevel <= depthLimit){
			pruneCount = 0;
			bestscore = maxV(bs, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, currentLevel);
			/* Remember to stop expanding after reaching depthLimit */
			/* Also, remember to count the number of pruned subtrees. */
			//List<Move> possibleMoves = Utils.getAllPossibleMoves(bs, side);
			//default best move if at root node -- handle the case if we set depthLimit = 1;
			if(Utils.verbose){
				System.out.println("Final Minimax Value is " + bestscore + " Best Move is " + bestMove);
				System.out.println("Total Prune Count for this iteration is " + getPruneCount());
				System.out.println("Last Prune Score for this iteration is " + getLastPrunedNodeScore());
				System.out.println();
			}
			currentLevel++;
		}	
	}
	/* Set the best move as the chosen move */

	public int getPruneCount()
	{
		return pruneCount;
	}
	public int maxV(int[] bs, int a, int b, int depth, int maxDepth){
		int visitedNode = 0;
		//if bs is terminal state
		if(Utils.getAllPossibleMoves(bs, side).size() == 0 ){
			if(side == RED){
			return Integer.MIN_VALUE;
				}
			return Integer.MAX_VALUE;
			}
		if(depth == maxDepth){
			if(side == BLK){
				return -sbe.evaluate(bs);
			}
			else{
				return sbe.evaluate(bs);
			}
		}
		//get bs's all possible move for the our side; 
		List<Move> succ = Utils.getAllPossibleMoves(bs, side);
		for(Move move: succ){
			//evaluate each move 
			Stack<Integer> rv = Utils.execute(bs, move);//then bs becomes min step
			visitedNode++;
			int nextScore = minV(bs,a,b,depth+1, maxDepth);
			if(nextScore > a){
				a = nextScore;
				if(depth == 0){
					setMove(move);
					bestMove = move;
				}
			}
			Utils.revert(bs, rv);
			if(a >= b){
				pruneCount += (succ.size() - visitedNode);
				lastPruneScore = a;
				break;
			}
		}
		return a;
	}

	public int minV(int[] bs, int a, int b, int depth, int maxDepth){
		//if bs is terminal state
		int visitedNode = 0;
		if(Utils.getAllPossibleMoves(bs, side).size() == 0 ){
			if(side == RED){
			return Integer.MAX_VALUE;
				}
			return Integer.MIN_VALUE;
			}
		if(depth == maxDepth){
			if(side == BLK){
				return -sbe.evaluate(bs);
			}
			else{
				return sbe.evaluate(bs);
			}
		}
		List<Move> succ = Utils.getAllPossibleMoves(bs, 1-side);
		for(Move move: succ){
			visitedNode++;
			//evaluate each move 
			Stack<Integer> rv = Utils.execute(bs, move);//then bs becomes max step
			//try to set beta
			b = Math.min(b, maxV(bs, a, b, depth+1, maxDepth));
			Utils.revert(bs, rv);
			if(a >= b){
				pruneCount += (succ.size() - visitedNode);
				lastPruneScore = b;
				break;
			}
		}
		return b;
	}
	@Override
	public int getLastPrunedNodeScore() {
		return lastPruneScore;
	}

}
