package eg.edu.guc.santorini.ai;

import java.util.ArrayList;

import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.Cell;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;
import eg.edu.guc.santorini.utilities.Location;

public class AIPlayer {
	private Board AIboard;
	private int depth = 3;
	private Player currentPlayer;
	private Piece turnPiece1, turnPiece2, otherPiece1, otherPiece2;// for
																	// evaluating

	public AIPlayer(Board board) {
		this.AIboard = board;
	}

	public void setDepth(int depth) {
		if (depth % 2 != 0)
			return;
		this.depth = depth;
	}

	public Board copyBoard(Board b, boolean isMoved, boolean isPlaced,
			boolean isP1Turn) {
		String p1Name = b.getP1().getName();
		int p1Type = b.getP1().getType();
		String p2Name = b.getP2().getName();
		int p2Type = b.getP2().getType();
		String[][] display = b.display();
		String[][] copiedDisplay = new String[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				copiedDisplay[i][j] = display[i][j];
			}
		}
		Board copied = new Board(copiedDisplay, new Player(p1Name, p1Type),
				new Player(p2Name, p2Type));
		copied.setMoved(isMoved);
		copied.setPlaced(isPlaced);
		copied.setP1Turn(isP1Turn);
		return copied;
	}

	public Piece copyPiece(Piece p) {
		Piece piece;
		if (p instanceof Cube) {
			p = (Cube) p;
			int x = p.getLocation().getX();
			int y = p.getLocation().getY();
			piece = new Cube(new Location(y, x));
		} else {
			p = (Pyramid) p;
			int x = p.getLocation().getX();
			int y = p.getLocation().getY();
			piece = new Pyramid(new Location(y, x));
		}
		return piece;

	}

	public int[] move() throws InvalidPlacementException, InvalidMoveException {
		int[] result = minimax(depth, true, AIboard);
		return result; // piece number ,row, column
	}

	public void printBoard(Board b) {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (b.getGrid()[y][x].getPlayer() == null) {
					System.out.print(b.getGrid()[y][x].toString());
				} else if (b.getGrid()[y][x].getPlayer() == b.getP1()) {
					System.out.print(b.getGrid()[y][x].toString() + "1");
				} else {
					System.out.print(b.getGrid()[y][x].toString() + "2");
				}
				System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println("the turn is for: " + b.getTurn().getName());
		System.out.println("isP1Turn = " + b.isP1Turn());
		System.out.println("isMoved = " + b.isMoved());
		System.out.println("isPlaced = " + b.isPlaced());
	}

	private int[] minimax(int depth, boolean AITurn, Board board) {
		Player currentPlayer;
		boolean gameOver = false;
		if (AITurn) {
			currentPlayer = board.getP2();

		} else {
			currentPlayer = board.getP1();
		}
		// AI Turn is maximizing else is minimizing
		int bestScore = (AITurn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int pieceNumber = 0;
		Location bestMove = new Location(-1, -1);
		Location bestPlace = new Location(-1, -1);
		// TRYING PIECE ONE
		ArrayList<Location> possibleMoves1 = currentPlayer.getT1()
				.possibleMoves();

		if (board.isGameOver() || depth == 0) {
			bestScore = evaluate(board, AITurn);
		} else {
			for (int i = 0; i < possibleMoves1.size(); i++) {
				Board copiedBoardToMove = copyBoard(board, board.isMoved(),
						board.isPlaced(), board.isP1Turn());
				Piece copiedPiece = copyPiece(currentPlayer.getT1());
				if (copiedBoardToMove.canMove(copiedPiece,
						possibleMoves1.get(i))) {
					try {
						copiedBoardToMove.move(copiedPiece,
								possibleMoves1.get(i));
					} catch (InvalidMoveException e) {
						continue;
					}
					ArrayList<Location> possiblePlacements = copiedPiece
							.possiblePlacements();
					for (int j = 0; j < possiblePlacements.size(); j++) {
						if (AITurn) {
							Board copiedBoardToPlace = copyBoard(
									copiedBoardToMove,
									copiedBoardToMove.isMoved(),
									copiedBoardToMove.isPlaced(),
									copiedBoardToMove.isP1Turn());
							Piece copiedPiece2 = copyPiece(copiedPiece);
							if (copiedBoardToPlace.isGameOver()) {
								currentScore = evaluate(copiedBoardToPlace,
										AITurn);
								if (currentScore >= bestScore) {
									bestScore = currentScore;
									bestMove = possibleMoves1.get(i);
									pieceNumber = 1;
								}
								continue;

							}

							if (copiedBoardToPlace.canPlace(copiedPiece2,
									possiblePlacements.get(j))) {
								try {
									copiedBoardToPlace.place(copiedPiece2,
											possiblePlacements.get(j));
								} catch (InvalidPlacementException e) {
									continue;
								}
								currentScore = minimax(depth - 1, false,
										copiedBoardToPlace)[0];
								if (currentScore > bestScore) {
									bestScore = currentScore;
									bestMove = possibleMoves1.get(i);
									bestPlace = possiblePlacements.get(j);
									pieceNumber = 1;
								}
							}
						} else {
							Board copiedBoardToPlace = copyBoard(
									copiedBoardToMove,
									copiedBoardToMove.isMoved(),
									copiedBoardToMove.isPlaced(),
									copiedBoardToMove.isP1Turn());
							Piece copiedPiece2 = copyPiece(copiedPiece);
							if (copiedBoardToPlace.isGameOver()) {
								currentScore = evaluate(copiedBoardToPlace,
										AITurn);
								if (currentScore <= bestScore) {
									bestScore = currentScore;
									bestMove = possibleMoves1.get(i);
									pieceNumber = 1;
								}
								continue;
							}
							if (copiedBoardToPlace.canPlace(copiedPiece2,
									possiblePlacements.get(j))) {
								try {
									copiedBoardToPlace.place(copiedPiece2,
											possiblePlacements.get(j));
								} catch (InvalidPlacementException e) {
									continue;
								}
								currentScore = minimax(depth - 1, true,
										copiedBoardToPlace)[0];
								if (currentScore < bestScore) {
									bestScore = currentScore;
									bestMove = possibleMoves1.get(i);
									bestPlace = possiblePlacements.get(j);
									pieceNumber = 1;
								}
							}
						}
					}
				}
			}
			if (!gameOver) {
				// for piece2
				ArrayList<Location> possibleMoves2 = currentPlayer.getT2()
						.possibleMoves();
				for (int i = 0; i < possibleMoves2.size(); i++) {
					Board copiedBoardToMove = copyBoard(board, board.isMoved(),
							board.isPlaced(), board.isP1Turn());
					Piece copiedPiece = copyPiece(currentPlayer.getT2());
					if (copiedBoardToMove.canMove(copiedPiece,
							possibleMoves2.get(i))) {
						try {
							copiedBoardToMove.move(copiedPiece,
									possibleMoves2.get(i));
						} catch (InvalidMoveException e) {
							continue;
						}
						ArrayList<Location> possiblePlacements = copiedPiece
								.possiblePlacements();

						for (Location placement : possiblePlacements) {
							if (AITurn) {
								Board copiedBoardToPlace = copyBoard(
										copiedBoardToMove,
										copiedBoardToMove.isMoved(),
										copiedBoardToMove.isPlaced(),
										copiedBoardToMove.isP1Turn());
								if (copiedBoardToPlace.isGameOver()) {
									currentScore = evaluate(copiedBoardToPlace,
											AITurn);
									if (currentScore >= bestScore) {
										bestScore = currentScore;
										bestMove = possibleMoves2.get(i);
										bestPlace = placement;
										pieceNumber = 2;
									}
									continue;
								}

								Piece copiedPiece2 = copyPiece(copiedPiece);
								if (copiedBoardToPlace.canPlace(copiedPiece2,
										placement)) {
									try {
										copiedBoardToPlace.place(copiedPiece2,
												placement);
									} catch (InvalidPlacementException e) {
										continue;
									}
									currentScore = minimax(depth - 1, false,
											copiedBoardToPlace)[0];
									if (currentScore > bestScore) {
										bestScore = currentScore;
										bestMove = possibleMoves2.get(i);
										bestPlace = placement;
										pieceNumber = 2;
									}
								}
							} else {
								Board copiedBoardToPlace = copyBoard(
										copiedBoardToMove,
										copiedBoardToMove.isMoved(),
										copiedBoardToMove.isPlaced(),
										copiedBoardToMove.isP1Turn());
								if (copiedBoardToPlace.isGameOver()) {
									currentScore = evaluate(copiedBoardToPlace,
											AITurn);
									if (currentScore <= bestScore) {
										bestScore = currentScore;
										bestMove = possibleMoves2.get(i);
										bestPlace = placement;
										pieceNumber = 2;
									}
									continue;
								}

								Piece copiedPiece2 = copyPiece(copiedPiece);
								if (copiedBoardToPlace.canPlace(copiedPiece2,
										placement)) {
									try {
										copiedBoardToPlace.place(copiedPiece2,
												placement);
									} catch (InvalidPlacementException e) {
										continue;
									}
									currentScore = minimax(depth - 1, true,
											copiedBoardToPlace)[0];
									if (currentScore < bestScore) {
										bestScore = currentScore;
										bestMove = possibleMoves2.get(i);
										bestPlace = placement;
										pieceNumber = 2;
									}
								}
							}
						}
					}
				}
			}
		}
		return new int[] { bestScore, pieceNumber, bestMove.getY(),
				bestMove.getX(), bestPlace.getY(), bestPlace.getX() };
	}

	private int evaluate(Board board, Boolean AITurn) {
		currentPlayer = (AITurn) ? board.getP2() : board.getP1();
		if (AITurn) {
			turnPiece1 = board.getP2().getT1();
			turnPiece2 = board.getP2().getT2();
			otherPiece1 = board.getP1().getT1();
			otherPiece2 = board.getP1().getT2();
		} else {
			turnPiece1 = board.getP1().getT1();
			turnPiece2 = board.getP1().getT2();
			otherPiece1 = board.getP2().getT1();
			otherPiece2 = board.getP2().getT2();
		}
		Cell turnPiece1Cell = board.getGrid()[turnPiece1.getLocation().getY()][turnPiece1
				.getLocation().getX()];
		Cell turnPiece2Cell = board.getGrid()[turnPiece2.getLocation().getY()][turnPiece2
				.getLocation().getX()];
		Cell otherPiece1Cell = board.getGrid()[otherPiece1.getLocation().getY()][otherPiece1
				.getLocation().getX()];
		Cell otherPiece2Cell = board.getGrid()[otherPiece2.getLocation().getY()][otherPiece2
				.getLocation().getX()];
		ArrayList<Location> turnPieceMoves1 = getPieceMoves(board, turnPiece1);
		ArrayList<Location> turnPieceMoves2 = getPieceMoves(board, turnPiece2);
		ArrayList<Location> otherPieceMoves1 = getPieceMoves(board, otherPiece1);
		ArrayList<Location> otherPieceMoves2 = getPieceMoves(board, otherPiece2);
		int score = 0;
		if (board.isGameOver()) {
			if (board.getWinner() == currentPlayer) {
				if (AITurn)
					return Integer.MAX_VALUE;
				else {
					return Integer.MIN_VALUE;
				}
			}
		}
		// checking the lists
		if (otherPieceMoves1.isEmpty() || otherPieceMoves2.isEmpty()) {
			score += 10;
		}
		// if (turnPieceMoves1.isEmpty() || turnPieceMoves2.isEmpty()) {
		// score -= 10;
		// }
		// // checking the levels for p1
		if (turnPiece1Cell.getLevel() == 2) {
			int maxScore = 0;
			for (int i = 0; i < turnPieceMoves1.size(); i++) {
				Location loc = turnPieceMoves1.get(i);
				Cell current = board.getGrid()[loc.getY()][loc.getX()];
				int score1 = 0;
				if (current.getLevel() == 3) {
					score1 += 150;
				} else if (current.getLevel() == 2) {
					score1 += 10;
				} else if (current.getLevel() == 1) {
					score1 += 1;
				}
				if (score1 > maxScore) {
					maxScore = score1;
				}
			}
			score += 20 + maxScore;
		} else if (turnPiece1Cell.getLevel() == 1) {
			score += 10;
		} else if (turnPiece1Cell.getLevel() == 0) {
			score -= 10;
		}
		if (turnPiece2Cell.getLevel() == 2) {
			int maxScore = 0;
			for (int i = 0; i < turnPieceMoves2.size(); i++) {
				Location loc = turnPieceMoves2.get(i);
				Cell current = board.getGrid()[loc.getY()][loc.getX()];
				int score1 = 0;
				if (current.getLevel() == 3) {
					score1 += 150;
				} else if (current.getLevel() == 2) {
					score1 += 10;
				} else if (current.getLevel() == 1) {
					score1 += 1;
				}
				if (score1 > maxScore) {
					maxScore = score1;
				}
			}
			score += 20 + maxScore;
		} else if (turnPiece2Cell.getLevel() == 1) {
			score += 10;
		} else if (turnPiece2Cell.getLevel() == 0) {
			score -= 10;
		}

		if (otherPiece1Cell.getLevel() == 2) {
			score -= 20;
		} else if (otherPiece1Cell.getLevel() == 1) {
			score -= 10;
		} else if (otherPiece1Cell.getLevel() == 0) {
			score += 1;
		}
		if (otherPiece2Cell.getLevel() == 2) {
			score -= 20;
		} else if (otherPiece2Cell.getLevel() == 1) {
			score -= 10;
		} else if (otherPiece2Cell.getLevel() == 0) {
			score += 10;
		}
		if (!AITurn)
			return score * -1;
		else
			return score;
	}

	private ArrayList<Location> getPieceMoves(Board board, Piece piece) {
		ArrayList<Location> pieceMoves = piece.possibleMoves();
		for (int i = 0; i < pieceMoves.size(); i++) {
			if (!(board.canMove(piece, pieceMoves.get(i)))) {
				pieceMoves.remove(i);
			}
		}
		return pieceMoves;
	}

	public Board getRandomMovePiece1(Board myBoard) {
		ArrayList<Location> piece1Moves = myBoard.getP2().getT1()
				.possibleMoves();
		ArrayList<Location> piece1Placements = myBoard.getP2().getT1()
				.possiblePlacements();
		Piece piece1 = myBoard.getP2().getT1();
		boolean p1Moved = false;
		for (int i = 0; i < piece1Moves.size(); i++) {
			if (myBoard.canMove(piece1, piece1Moves.get(i))) {
				try {
					myBoard.move(piece1, piece1Moves.get(i));
					p1Moved = true;
				} catch (InvalidMoveException e) {
					continue;
				}
			}
		}
		if (p1Moved) {
			for (int i = 0; i < piece1Placements.size(); i++) {
				if (myBoard.canMove(piece1, piece1Placements.get(i))) {
					try {
						myBoard.place(piece1, piece1Placements.get(i));
						return myBoard;
					} catch (InvalidPlacementException e) {
						continue;
					}
				}
			}
		}
		return getRandomMovePiece2(myBoard);
	}

	public Board getRandomMovePiece2(Board myBoard) {
		ArrayList<Location> piece2Moves = myBoard.getP2().getT2()
				.possibleMoves();
		ArrayList<Location> piece1Placements = myBoard.getP2().getT2()
				.possiblePlacements();
		Piece piece2 = myBoard.getP2().getT2();
		boolean p2Moved = false;
		for (int i = 0; i < piece2Moves.size(); i++) {
			if (myBoard.canMove(piece2, piece2Moves.get(i))) {
				try {
					myBoard.move(piece2, piece2Moves.get(i));
					p2Moved = true;
				} catch (InvalidMoveException e) {
					continue;
				}
			}
		}
		if (p2Moved) {
			for (int i = 0; i < piece1Placements.size(); i++) {
				if (myBoard.canMove(piece2, piece1Placements.get(i))) {
					try {
						myBoard.place(piece2, piece1Placements.get(i));
						return myBoard;
					} catch (InvalidPlacementException e) {
						continue;
					}
				}
			}
		}
		return null;
	}
}
