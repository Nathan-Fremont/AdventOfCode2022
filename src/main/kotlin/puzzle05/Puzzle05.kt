package puzzle05

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle05"
private val mapper = HanoiTowerMapper()

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")

    Path(INPUT_FILE_NAME)
        .takeIf { file ->
            file.exists()
        }
        ?.also { file ->
            println("Parse file")
            val fileContent = Files.readString(
                file
            )

            val hanoiTower = mapper.mapFileToHanoiTower(
                fileContent = fileContent,
            )

//            val modifiedHanoiTowerPart1 = executeMovesOnHanoiTowerPart1(
//                givenHanoiTower = hanoiTower,
//            )

//            println("modifiedHanoiTower1 = $modifiedHanoiTowerPart1")
//
//            val topsPart1 = modifiedHanoiTowerPart1
//                .hanoiStacks
//                .joinToString {  stack ->
//                    stack.peek()
//                }
//            println("topsPart1 = $topsPart1")

            val modifiedHanoiTowerPart2 = executeMovesOnHanoiTowerPart2(
                givenHanoiTower = hanoiTower,
            )

            println("modifiedHanoiTowerPart2 = $modifiedHanoiTowerPart2")

            val topsPart2 = modifiedHanoiTowerPart2
                .hanoiStacks
                .joinToString {  stack ->
                    stack.peek()
                }
            println("topsPart2 = $topsPart2")
        }
}

private fun executeMovesOnHanoiTowerPart1(givenHanoiTower: HanoiTower): HanoiTower {
    val resHanoiTower = givenHanoiTower.copy()

    givenHanoiTower
        .hanoiMoves
        .forEach { hanoiMove ->
            repeat(hanoiMove.palletNumberToMove) {
                resHanoiTower.hanoiStacks[hanoiMove.toStack - 1].push(resHanoiTower.hanoiStacks[hanoiMove.fromStack - 1].pop())
            }
        }

    return resHanoiTower
}

private fun executeMovesOnHanoiTowerPart2(givenHanoiTower: HanoiTower): HanoiTower {
    val resHanoiTower = givenHanoiTower.copy()

    givenHanoiTower
        .hanoiMoves
        .forEach { hanoiMove ->
            val palletsToMove = mutableListOf<String>()
            repeat(hanoiMove.palletNumberToMove) {
                palletsToMove += resHanoiTower.hanoiStacks[hanoiMove.fromStack - 1].pop()
            }
            palletsToMove
                .reversed()
                .forEach { pallet ->
                    resHanoiTower.hanoiStacks[hanoiMove.toStack - 1].push(pallet)
                }
        }

    return resHanoiTower
}