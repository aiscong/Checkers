package cs540.checkers.cong;
import static cs540.checkers.CheckersConsts.BLANK;
import static cs540.checkers.CheckersConsts.BLK_KING;
import static cs540.checkers.CheckersConsts.BLK_PAWN;
import static cs540.checkers.CheckersConsts.H;
import static cs540.checkers.CheckersConsts.RED_KING;
import static cs540.checkers.CheckersConsts.RED_PAWN;
import static cs540.checkers.CheckersConsts.W;
import cs540.checkers.Evaluator;

public class CongEvaluator implements  Evaluator{
	@Override
	public int evaluate(int[] bs) {
		//Openning-Middle Game Evaluation functions
		//pawns value: 5 + row number
		//kings value: every king is 15 points

		int red = 0;
		int blk = 0;
		for (int i = 0; i < H * W; i++){
			int v = bs[i];
			int row = i/16;
			switch(v){
			case RED_PAWN: 
				red += 7-row; 
				if(i <= 53){
					if(bs[i+7] == RED_PAWN || bs[i+9] == RED_PAWN){
						red += 2;
						if(bs[i+7] == RED_PAWN && bs[i+9] == RED_PAWN){
							red += 4;
						}
					}
				}
				if(i == 55){
					if(bs[62] == RED_PAWN){
						red+=2;
					}
				}
				if(i == 8 || i == 24 || i == 40 || i == 56 || i == 7 || i ==23 || i == 39 || i == 55 ){
					red += 3;
				}

				break;

			case BLK_PAWN: blk += 4 + row; 
			if(i >= 10){
				if(bs[i-7] == BLK_PAWN || bs[i-9] == BLK_PAWN){
					blk += 2;
					if(bs[i-7] == BLK_PAWN && bs[i-9] == BLK_PAWN){
						blk+=4;
					}
				}
			}
			if(i == 8){
				if(bs[1] == BLK_PAWN){
					blk += 2;
				}
			}
			if(i == 8 || i == 24 || i == 40 || i == 56 || i == 7 || i ==23 || i == 39 || i ==55 ){
				blk += 3;
			}

			break;

			case RED_KING: red += 15; 
			break;
			case BLK_KING: blk += 15; 
			break;
			}

		}
		// TODO Auto-generated method stub
		return (red - blk);
	}
}