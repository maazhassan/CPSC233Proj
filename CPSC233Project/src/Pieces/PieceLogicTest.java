package Pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import Game.Board;
import Game.Move;
import Game.Square;

/**
 * JUnit class that tests the getChar() method in each piece class and also the logic of ALL of the pieces
 * The logic tests include checking: the pieces' normal moves, if the player clicked the same square, invalid moves,
 * killing moves, and valid moves, but with an ally piece already there.
 */

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

	public void checkingStraight(Piece p) {
		
		// Move left
		assertTrue(p.canMove(board, new Move(new Square(3,2,null), new Square(1,2,null))));
		// Move Right
		assertTrue(p.canMove(board, new Move(new Square(3,2,null), new Square(5,2,null))));
		// Move Up
		assertTrue(p.canMove(board, new Move(new Square(3,2,null), new Square(3,5,null))));
		// Move Down
		assertTrue(p.canMove(board, new Move(new Square(3,2,null), new Square(2,2,null))));
		// Invalid Move
		assertFalse(p.canMove(board, new Move(new Square(5,6,null), new Square(4,4,null))));
		// Same Spot
		assertFalse(p.canMove(board, new Move(new Square(5,4,null), new Square(5,4,null))));
		// Valid move, but already an ally piece
		assertFalse(p.canMove(board, new Move(new Square(3,2,null), new Square(2,2,new Pawn(false)))));
		// Killing move
		assertTrue(p.canMove(board, new Move(new Square(3,2,null), new Square(2,2,new Pawn(true)))));
		
	}	

	public void checkingDiagonal(Piece p) {
		
		// Move NE
		assertTrue(p.canMove(board, new Move(new Square(2,2,null), new Square(5,5,null))));
		// Move NW
		assertTrue(p.canMove(board, new Move(new Square(4,2,null), new Square(3,3,null))));
		// Move SE
		assertTrue(p.canMove(board, new Move(new Square(0,5,null), new Square(2,3,null))));
		// Move SW
		assertTrue(p.canMove(board, new Move(new Square(5,5,null), new Square(2,2,null))));
		// Invalid Move
		assertFalse(p.canMove(board, new Move(new Square(2,5,null), new Square(3,3,null))));
		// Same spot
		assertFalse(p.canMove(board, new Move(new Square(5,5,null), new Square(5,5,null))));
		// Valid move, but already an ally piece
		assertFalse(p.canMove(board, new Move(new Square(5,5,null), new Square(2,2,new Pawn(false)))));
		// Killing Move
		assertTrue(p.canMove(board, new Move(new Square(5,5,null), new Square(2,2,new Pawn(true)))));
		
	}
	
	@Test
	public void checkRook() {
		Rook r = new Rook(false);
		checkingStraight(r);
	}
	
	@Test
	public void checkBishop() {
		Bishop b = new Bishop(false);
		checkingDiagonal(b);
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
		// Same Spot
		assertFalse(k.canMove(board, new Move(new Square(2,5,null), new Square(2,5,null))));
		// Valid move, but already an ally piece
		assertFalse(k.canMove(board, new Move(new Square(2,2,null), new Square(1,3,new Pawn(false)))));
		// Killing Move
		assertTrue(k.canMove(board, new Move(new Square(2,2,null), new Square(1,3,new Pawn(true)))));


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
		// Same spot
		assertFalse(n.canMove(board, new Move(new Square(3,3,null), new Square(3,3,null))));
		// Valid move, but there's already an ally piece there
		assertFalse(n.canMove(board, new Move(new Square(3,3,null), new Square(4,5,new Pawn(false)))));
		// Valid Move with opponent piece
		assertTrue(n.canMove(board, new Move(new Square(3,3,null), new Square(4,5,new Pawn(true)))));
	}
	@Test
	public void checkPawn() {
		Pawn p = new Pawn(false);
		
//		assertTrue(p.canMove(board, new Move(new Square(0,6,null), new Square(0,5,null))));
//		assertTrue(p.canMove(board, new Move(new Square(3,1,null), new Square(3,3,null))));
//		// Two steps NOT first turn
//		assertFalse(p.canMove(board, new Move(new Square(3,2,null), new Square(3,3,null))));
//		// Diagonal
//		assertFalse(p.canMove(board, new Move(new Square(3,3,null), new Square(4,4,null))));
		
	}
	
	public void checkQueen() {
		Queen q = new Queen(false);
		checkingStraight(q);
		checkingDiagonal(q);
		
	}
}
