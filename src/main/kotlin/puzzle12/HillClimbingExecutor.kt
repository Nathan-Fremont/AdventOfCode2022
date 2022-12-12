package puzzle12

import java.util.Stack

class HillClimbingExecutor {
    fun findPathInGridOfHills(gridOfHills: List<List<Hill>>): List<HillWithCost> {
        println("findPathInGridOfHills")

        val start = gridOfHills
            .flatten()
            .first { hill ->
                hill.isStart
            }

        val end = gridOfHills
            .flatten()
            .first { hill ->
                hill.isEnd
            }

        println("findPathInGridOfHills ${"start" to start}, ${"end" to end}")
        val stackOfHillsToInspect = ArrayDeque<Hill>()
        val arrayOfHillsInspected = mutableListOf<Hill>()
        val hillPath = mutableListOf<HillWithCost>()
        stackOfHillsToInspect += end
        var cost = 0
        var startIsFound = false
        while (!startIsFound) {
            val tempStack = ArrayDeque<Hill>().apply {
                addAll(stackOfHillsToInspect.distinct())
            }
            stackOfHillsToInspect.clear()
            while (tempStack.size != 0) {
                val hillInLoop = tempStack.removeFirst()
                println("findPathInGridOfHills ${"cost" to cost}, ${"hillInLoop" to hillInLoop}")
                // Add cost
                hillPath += HillWithCost(
                    hill = hillInLoop,
                    cost = cost,
                )
                startIsFound = hillInLoop.isStart || hillInLoop.elevation == 0
                // Make sure this is inspected
                arrayOfHillsInspected += hillInLoop

                // Add surrounding hills to inspect
                val surrounding = getSurroundingHills(
                    gridOfHills = gridOfHills,
                    givenHill = hillInLoop,
                )

                stackOfHillsToInspect += surrounding
                    .filter { hill ->
                        !arrayOfHillsInspected.contains(hill)
                    }
            }
            cost += 1
        }

        println("findPathInGridOfHills ${"hillPath" to hillPath}")

        val filteredHillPath = hillPath
            .groupBy { hillWithCost ->
                hillWithCost.hill.xValue to hillWithCost.hill.yValue
            }
            .map { mapEntry ->
                mapEntry.value.minByOrNull { hillWithCost ->
                    hillWithCost.cost
                }!!
            }

        println("findPathInGridOfHills ${"filteredHillPath" to filteredHillPath}")
        return filteredHillPath
    }

    private fun getSurroundingHills(gridOfHills: List<List<Hill>>, givenHill: Hill): List<Hill> {
        val surroundingHills = mutableListOf<Hill>()

        // Add left
        givenHill.xValue
            .takeIf { x -> x != 0 }
            ?.run {
                surroundingHills += gridOfHills[givenHill.yValue][this - 1]
            }

        // Add top
        givenHill.yValue
            .takeIf { y -> y != 0 }
            ?.run {
                surroundingHills += gridOfHills[this - 1][givenHill.xValue]
            }

        // Add right
        givenHill.xValue
            .takeIf { x -> gridOfHills[givenHill.yValue].size > x + 1 }
            ?.run {
                surroundingHills += gridOfHills[givenHill.yValue][this + 1]
            }

        // Add bottom
        givenHill.yValue
            .takeIf { y -> gridOfHills.size > y + 1 }
            ?.run {
                surroundingHills += gridOfHills[this + 1][givenHill.xValue]
            }


        return surroundingHills
            .filter { hill ->
                hill.canGoToOtherHill(otherHill = givenHill)
            }
    }

    private fun Hill.canGoToOtherHill(otherHill: Hill): Boolean = this.elevation >= otherHill.elevation - 1
}