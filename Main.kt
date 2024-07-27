const val HORIZONTAL_LINE = "---------"

fun buildGameGrid(input: String): Array<CharArray> {
    var columnCounter = 0
    var elemInRowCounter = 0
    val gameBoard = arrayOf(CharArray(3), CharArray(3), CharArray(3))
    for (i in input.indices) {
        gameBoard[columnCounter][elemInRowCounter] = input[i]
        elemInRowCounter++
        if (elemInRowCounter == 3) {
            columnCounter++
            elemInRowCounter = 0
        }
    }
    return gameBoard
}

fun checkGameConditions(gameBoard: Array<CharArray>): String {
    val xWins = checkIfPlayerHasThreeInARow(gameBoard, 'X')
    val oWins = checkIfPlayerHasThreeInARow(gameBoard, 'O')
    if (xWins && oWins) {
        return ("Impossible")
    } else if (!xWins && !oWins) {
        if (impossibilityCheck(gameBoard)) {
            return ("Impossible")
        } else if (hasEmptyCells(gameBoard)) {
            return ("Game not finished")
        } else if (!(hasEmptyCells(gameBoard))) {
            return ("Draw")
        }
    } else if (xWins) return ("X wins")
    else if (oWins) return ("O wins")
    return "Error"
}

fun hasEmptyCells(gameBoard: Array<CharArray>): Boolean {
    for (i in gameBoard.indices) {
        for (j in gameBoard[i].indices) {
            if (gameBoard[i][j] == ' ' || gameBoard[i][j] == '_') {
                return true
            }
        }
    }
    return false
}

fun impossibilityCheck(gameBoard: Array<CharArray>): Boolean {
    val xCount = gameBoard.sumOf { row -> row.count { it == 'X' } }
    val oCount = gameBoard.sumOf { row -> row.count { it == 'O' } }

    if (kotlin.math.abs(xCount - oCount) > 1) return true else return false
}

fun checkIfPlayerHasThreeInARow(gameBoard: Array<CharArray>, player: Char): Boolean {
    for (i in 0..2) {
        if (gameBoard[i][0] == player && gameBoard[i][1] == player && gameBoard[i][2] == player) return true
        if (gameBoard[0][i] == player && gameBoard[1][i] == player && gameBoard[2][i] == player) return true
    }
    // Check diagonals
    if (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) return true
    if (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player) return true

    return false
}

fun printGameBoard(gameBoard: Array<CharArray>) {
    println(HORIZONTAL_LINE)
    for (subArr in gameBoard) {
        println(subArr.joinToString(separator = " ", prefix = "| ", postfix = " |"))
    }
    println(HORIZONTAL_LINE)
}

fun main() {
    val input = "_________"
    val gameBoard: Array<CharArray> = buildGameGrid(input)
    printGameBoard(gameBoard)
    var currentPlayer = 'X'
    var gameCondition = "Game not finished"
    while (gameCondition == "Game not finished") {
        val positionCord = readln().split(" ").map { it.toIntOrNull() }
        if (positionCord.size == 2) {
            if (positionCord[0] == null || positionCord[1] == null) {
                println("You should enter numbers!")
            } else if (positionCord[0] != null && positionCord[1] != null) {
                val rowPosition = positionCord[0]!! - 1
                val columnPosition = positionCord[1]!! - 1
                if ((positionCord[0]!! > 3 || positionCord[0]!! < 1) || (positionCord[1]!! > 3 || positionCord[1]!! < 0)) {
                    println("Coordinates should be from 1 to 3!")
                } else {
                    if (gameBoard[rowPosition][columnPosition] == 'X' || gameBoard[rowPosition][columnPosition] == 'O') {
                        println("This cell is occupied! Choose another one!")
                    } else {
                        gameBoard[rowPosition][columnPosition] = currentPlayer
                        printGameBoard(gameBoard)
                        if (currentPlayer == 'X') {
                            currentPlayer = 'O'
                        } else if (currentPlayer == 'O') {
                            currentPlayer = 'X'
                        }
                        when (checkGameConditions(gameBoard)) {
                            "X wins" -> gameCondition = "X wins"
                            "O wins" -> gameCondition = "O wins"
                            "Draw" -> gameCondition = "Draw"
                        }
                    }

                }
            }
        } else {
            println("You should enter numbers!")
        }
    }
    println(gameCondition)

}