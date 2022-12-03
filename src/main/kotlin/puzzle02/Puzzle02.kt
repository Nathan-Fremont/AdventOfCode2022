package puzzle02

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val LINE_PLAY_SEPARATOR = " "
private const val INPUT_FILE_NAME = "inputPuzzle02"
private const val RESULT_WIN_SCORE = 6
private const val RESULT_DRAW_SCORE = 3
private const val RESULT_LOSS_SCORE = 0

data class ElfAndMyPlay(
    val elfPlay: ElfPlay,
    val myPlay: MyPlay,
    val wantedPlayResult: PlayResult,
) {
    fun calculateScoreForPlayPartOne(): Int {
        val resultScore = when {
            elfPlay is ElfPlay.ROCK && myPlay is MyPlay.PAPER -> RESULT_WIN_SCORE
            elfPlay is ElfPlay.ROCK && myPlay is MyPlay.SCISSORS -> RESULT_LOSS_SCORE
            elfPlay is ElfPlay.PAPER && myPlay is MyPlay.SCISSORS -> RESULT_WIN_SCORE
            elfPlay is ElfPlay.PAPER && myPlay is MyPlay.ROCK -> RESULT_LOSS_SCORE
            elfPlay is ElfPlay.SCISSORS && myPlay is MyPlay.ROCK -> RESULT_WIN_SCORE
            elfPlay is ElfPlay.SCISSORS && myPlay is MyPlay.PAPER -> RESULT_LOSS_SCORE
            else -> RESULT_DRAW_SCORE
        }
        return myPlay.scoreValue + resultScore
    }

    fun calculateScoreForPlayPartTwo(): Int {
        val playedScore = when {
            wantedPlayResult is PlayResult.WIN -> {
                when (elfPlay) {
                    ElfPlay.PAPER -> MyPlay.SCISSORS.scoreValue
                    ElfPlay.ROCK -> MyPlay.PAPER.scoreValue
                    ElfPlay.SCISSORS -> MyPlay.ROCK.scoreValue
                }
            }
            wantedPlayResult is PlayResult.LOSS -> {
                when (elfPlay) {
                    ElfPlay.PAPER -> MyPlay.ROCK.scoreValue
                    ElfPlay.ROCK -> MyPlay.SCISSORS.scoreValue
                    ElfPlay.SCISSORS -> MyPlay.PAPER.scoreValue
                }
            }
            else -> elfPlay.scoreValue
        }
        return wantedPlayResult.scoreValue + playedScore
    }
}

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")
    Path(INPUT_FILE_NAME)
        .takeIf { file ->
            file.exists()
        }
        ?.also { file ->
            println("Parse file")
            val listOfElfAndMyPlays = getElfAndMyPlays(
                Files.readAllLines(
                    file
                )
            )
            println("==========\n${"listOfElfAndMyPlays" to listOfElfAndMyPlays}\n==========")

            val partOneScore = listOfElfAndMyPlays
                .map { elfAndMyPlay ->
                    elfAndMyPlay.calculateScoreForPlayPartOne()
                }
                .sumOf { score ->
                    score
                }
            println("==========\n${"partOneScore" to partOneScore}\n==========")

            val partTwoScore = listOfElfAndMyPlays
                .map { elfAndMyPlay ->
                    elfAndMyPlay.calculateScoreForPlayPartTwo()
                }
                .sumOf { score ->
                    score
                }
            println("==========\n${"partTwoScore" to partTwoScore}\n==========")
        } ?: println("Error $INPUT_FILE_NAME does not exists")
}

fun getElfAndMyPlays(fileLines: List<String>): MutableList<ElfAndMyPlay> {
    val listOfElfAndMyPlays = mutableListOf<ElfAndMyPlay>()
    fileLines
        .map { line ->
            line.split(LINE_PLAY_SEPARATOR)
        }
        .forEach { chunk ->
            listOfElfAndMyPlays += ElfAndMyPlay(
                elfPlay = ElfPlay.valueOf(chunk[0]),
                myPlay = MyPlay.valueOf(chunk[1]),
                wantedPlayResult = PlayResult.valueOf(chunk[1])
            )
        }
    return listOfElfAndMyPlays
}