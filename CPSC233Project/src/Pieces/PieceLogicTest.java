package Pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import Game.Board;
import Game.Move;
import Game.Square;

public class PieceLogicTest {

public static final Board board = new Board(true);
	
	@Test
	public void checkGetChar() {
		Rook r = new Rook(false);
		Bishop b = new Bishop(false);
		King k = new King(false);
		Knight n = new Knight(false);
		Pawn p = new Pawn(false);
		Queen q = new Queen(false);
		
		char rook = r.getPieceChar();
		char bishop = b.getPieceChar();
		char king = k.getPieceChar();
		char knight = n.getPieceChar();
		char pawn = p.getPieceChar();
		char queen = q.getPieceChar();
		
		assertEquals('r',rook);
		assertEquals('b', bishop);
		assertEquals('k', king);
		assertEquals('n', knight);
		assertEquals('p', pawn);
		assertEquals('q', queen);
		
	}

	@Test
	public void checkRook() {
		Rook r = new Rook(false);
		
		// Move left
		assertTrue(r.canMove(board, new Move(new Square(3,2,null), new Square(1,2,null))));
		// Move Right
		assertTrue(r.canMove(board, new Move(new Square(3,2,null), new Square(5,2,null))));
		// Move Up
		assertTrue(r.canMove(board, new Move(new Square(3,2,null), new Square(3,5,null))));
		// Move Down
		assertTrue(r.canMove(board, new Move(new Square(3,2,null), new Square(2,2,null))));
		// Invalid Move
		assertFalse(r.canMove(board, new Move(new Square(5,4,null), new Square(3,2,null))));
	}	
	@Test
	public void checkBishop() {
		Bishop b = new Bishop(false);
		
		// Move NE
		assertTrue(b.canMove(board, new Move(new Square(2,2,null), new Square(5,5,null))));
		// Move NW
		assertTrue(b.canMove(board, new Move(new Square(4,2,null), new Square(3,3,null))));
		// Move SE
		assertTrue(b.canMove(board, new Move(new Square(0,5,null), new Square(2,3,null))));
		// Move SW
		assertTrue(b.canMove(board, new Move(new Square(5,5,null), new Square(2,2,null))));
		// Invalid Move
		assertFalse(b.canMove(board, new Move(new Square(3,2,null), new Square(4,2,null))));
	}
	@Test
	public void checkKing() {
		King k = new King(false);
		
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(2,3,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(2,1,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(3,2,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(1,2,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(3,3,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(3,1,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(1,1,null))));
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(1,3,null))));
		// Invalid Move
		assertFalse(k.canMove(board, new Move(new Square(2,2,null), new Square(4,5,null))));
	}
	@Test
	public void checkKnight() {
		Knight n = new Knight(false);
		
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(5,4,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(5,2,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(4,1,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(2,1,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(1,2,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(1,4,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(2,5,null))));
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(4,5,null))));
		// Invalid Move
		assertFalse(n.canMove(board, new Move(new Square(3,3,null), new Square(4,4,null))));
	}
	@Test
//	public void checkPawn() {
//		Pawn p = new Pawn(false);
//		
//		assertTrue(p.canMove(board, new Move(new Square(4,1,null), new Square(4,2,null))));
//		assertTrue(p.canMove(board, new Move(new Square(3,1,null), new Square(3,3,null))));
//		// Two steps NOT first turn
//		assertFalse(p.canMove(board, new Move(new Square(3,2,null), new Square(3,3,null))));
//		// Diagonal
//		assertFalse(p.canMove(board, new Move(new Square(3,3,null), new Square(4,4,null))));
//		
//	}
	
	public void checkQueen() {
		Queen q = new Queen(false);
		
		// Rook Logic
		
		// Move left
		assertTrue(q.canMove(board, new Move(new Square(3,2,null), new Square(1,2,null))));
		// Move Right
		assertTrue(q.canMove(board, new Move(new Square(3,2,null), new Square(5,2,null))));
		// Move Up
		assertTrue(q.canMove(board, new Move(new Square(3,2,null), new Square(3,5,null))));
		// Move Down
		assertTrue(q.canMove(board, new Move(new Square(3,2,null), new Square(2,2,null))));	
		
		// Bishop Logic
		
		// Move NE
		assertTrue(q.canMove(board, new Move(new Square(2,2,null), new Square(5,5,null))));
		// Move NW
		assertTrue(q.canMove(board, new Move(new Square(4,2,null), new Square(3,3,null))));
		// Move SE
		assertTrue(q.canMove(board, new Move(new Square(0,5,null), new Square(2,3,null))));
		// Move SW
		assertTrue(q.canMove(board, new Move(new Square(5,5,null), new Square(2,2,null))));
	
		// Invalid Move
		assertFalse(q.canMove(board, new Move(new Square(5,5,null), new Square(6,7,null))));
	}
}
