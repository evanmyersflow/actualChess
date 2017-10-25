import java.util.*
import kotlin.collections.ArrayList

val board = HashMap<Coordinate, Piece>()
val notation = listOf("A", "B", "C", "D", "E", "F", "G", "H")
const val SEPERATOR = ","
const val BOARD_SIZE = 7
val BOARD_RANGE = 0..BOARD_SIZE

fun main(args: Array<String>) {
    setupBoard()

    println("Let the game begin!")

    while (true) {
        printBoard()
        val scanner = Scanner(System.`in`)
        val input = scanner.nextLine().toUpperCase()

        val (from, to) = try {
            input.split(SEPERATOR).let {
                Coordinate.parse(it.first().trim()) to Coordinate.parse(it.last().trim())
            }
        } catch (e: IllegalStateException) {
            println("You're dumb!")
            continue
        }

        try {
        val chosenPiece = board[from]
        if (chosenPiece == null) {
        } else {
            if (chosenPiece.validMoves.contains(to)) {
                    val oldPiece = board.remove(from)!!
                board[to] = when (oldPiece) {
                    is Rook -> Rook(oldPiece.isWhite, to)
                    is Queen -> Queen(oldPiece.isWhite, to)
                    is Bishop -> Bishop(oldPiece.isWhite, to)
                    is Knight -> Knight(oldPiece.isWhite, to)
                    is King -> King(oldPiece.isWhite, to)
                    is Pawn -> Pawn(oldPiece.isWhite, to)
                    else -> TODO()
                }
                }
            }
        } catch (e: NotImplementedError) {
            continue
        }
    }
}

private fun printBoard() {
    println()
    for (i in BOARD_SIZE downTo 0) {
        for (j in BOARD_RANGE) {
            val coordinate = Coordinate(j, i)
            val piece: Piece = board[coordinate] ?:
                    letOnSquare(coordinate) { NullPiece(it, coordinate) }

            print(if (j in 0..6) "$piece " else piece)
        }
        println()
    }
    println()
}

private fun setupBoard() {
    setupSide(true)
    setupSide(false)
}

private fun setupSide(white: Boolean) {
    val row1Y = if (white) 0 else BOARD_SIZE
    addPiece(Rook(white, Coordinate(0, row1Y)))
    addPiece(Rook(white, Coordinate(BOARD_SIZE, row1Y)))
    addPiece(Knight(white, Coordinate(1, row1Y)))
    addPiece(Knight(white, Coordinate(6, row1Y)))
    addPiece(Bishop(white, Coordinate(2, row1Y)))
    addPiece(Bishop(white, Coordinate(5, row1Y)))
    addPiece(Queen(white, Coordinate(3, row1Y)))
    addPiece(King(white, Coordinate(4, row1Y)))

    val row2Y = if (white) 1 else 6
    addPiece(Pawn(white, Coordinate(0, row2Y)))
    addPiece(Pawn(white, Coordinate(1, row2Y)))
    addPiece(Pawn(white, Coordinate(2, row2Y)))
    addPiece(Pawn(white, Coordinate(3, row2Y)))
    addPiece(Pawn(white, Coordinate(4, row2Y)))
    addPiece(Pawn(white, Coordinate(5, row2Y)))
    addPiece(Pawn(white, Coordinate(6, row2Y)))
    addPiece(Pawn(white, Coordinate(BOARD_SIZE, row2Y)))
}

private fun addPiece(piece: Piece) {
    board.put(piece.coordinate, piece)
}

interface Piece {
    val isWhite: Boolean

    val coordinate: Coordinate

    val validMoves: List<Coordinate>
}

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        fun parse(input: String): Coordinate {
            if (input.length != 2) {
                throw IllegalStateException()
            }
            val x = notation.indexOf(input.first().toString())

            if (x < 0) {
                throw  IllegalStateException()
            }

            return Coordinate(x, input.last().toString().toInt() - 1)
        }
    }
}

//Pieces

data class Rook(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            val validMoves = ArrayList<Coordinate>()

            BOARD_RANGE
                    .asSequence()
                    .map { Coordinate(coordinate.x, it) }
                    .filter { coordinate != it }
                    .forEach { validMoves += it }

            BOARD_RANGE
                    .asSequence()
                    .map { Coordinate(it, coordinate.y) }
                    .filter { coordinate != it }
                    .forEach { validMoves += it }

            return validMoves
        }

    override fun toString(): String = if (isWhite) "Rw" else "Rb"
}

data class Knight(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    override fun toString(): String = if (isWhite) "Nw" else "Nb"
}

data class Bishop(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            val validMoves = ArrayList<Coordinate>()

            for (i in coordinate.x..BOARD_SIZE) {
                val up = Coordinate(i, coordinate.y + i)
                val down = Coordinate(i, coordinate.y - i)

                if (up.y in BOARD_RANGE) {
                    validMoves += up
                }
                if (down.y in BOARD_RANGE) {
                    validMoves += down
                }
            }

            for (j in coordinate.y..BOARD_SIZE) {
                val upJ = Coordinate(coordinate.x + j, j)
                val downJ = Coordinate(coordinate.x - j, j)

                if (upJ.x in BOARD_RANGE) {
                    validMoves += upJ
                }
                if (downJ.x in BOARD_RANGE) {
                    validMoves += downJ
                }
            }
            return validMoves
        }

    override fun toString(): String = if (isWhite) "Bw" else "Bb"
}

data class Queen(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            val validMoves = ArrayList<Coordinate>()

            BOARD_RANGE
                    .asSequence()
                    .map { Coordinate(coordinate.x, it) }
                    .filter { coordinate != it }
                    .forEach { validMoves += it }

            BOARD_RANGE
                    .asSequence()
                    .map { Coordinate(it, coordinate.y) }
                    .filter { coordinate != it }
                    .forEach { validMoves += it }

            (coordinate.x..BOARD_SIZE).forEach { i ->
                val up = Coordinate(i, coordinate.y + i)
                val down = Coordinate(i, coordinate.y - i)

                if (up.y in BOARD_RANGE) {
                    validMoves += up
                }
                if (down.y in BOARD_RANGE) {
                    validMoves += down
                }
            }

            return validMoves
        }

    override fun toString(): String = if (isWhite) "Qw" else "Qb"
}

data class King(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    override fun toString(): String = if (isWhite) "Kw" else "Kb"
}

data class Pawn(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    override fun toString(): String = if (isWhite) "Pw" else "Pb"
}

//Squares

data class NullPiece(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() = emptyList()

    override fun toString(): String = if (isWhite) "~ " else "- "
}

fun <T> letOnSquare(coordinate: Coordinate, block: (Boolean) -> T): T {
    return if (coordinate.y % 2 == 0) {
        if (coordinate.x % 2 == 0) {
            block(true)
        } else {
            block(false)
        }
    } else {
        if (coordinate.x % 2 == 0) {
            block(false)
        } else {
            block(true)
        }
    }
}
