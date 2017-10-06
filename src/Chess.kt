import java.util.*

val board = HashMap<Coordinate, Piece>()
val notation = listOf("A", "B", "C", "D", "E", "F", "G", "H")
const val SEPERATOR = ","

fun main(args: Array<String>) {
    setupBoard()

    println("Let the game begin!")

    while (true) {
        printBoard()
        val scanner = Scanner(System.`in`)
        val input = scanner.nextLine().toUpperCase()

        val (from, to) = input.split(SEPERATOR).let {
            Coordinate.parse(it.first().trim()) to Coordinate.parse(it.last().trim())
        }

        board[to] = board.remove(from)!!
    }
}


private fun printBoard() {
    println()
    for (i in 7 downTo 0) {
        for (j in 0..7) {
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
    val row1Y = if (white) 0 else 7
    addPiece(Rook(white, Coordinate(0, row1Y)))
    addPiece(Rook(white, Coordinate(7, row1Y)))
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
    addPiece(Pawn(white, Coordinate(7, row2Y)))
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    override fun toString(): String = if (isWhite) "Bw" else "Bb"
}

data class Queen(override val isWhite: Boolean, override val coordinate: Coordinate) : Piece {
    override val validMoves: List<Coordinate>
        get() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
