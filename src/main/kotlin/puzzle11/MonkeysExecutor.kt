package puzzle11

class MonkeysExecutor() {
    fun executeMonkeysForNumberOfTurns(givenMonkeys: List<Monkey>, numberOfTurns: Int = 20): MonkeysExecutorResult {
        println("executeMonkeysForNumberOfTurns")
        val resultMonkeys = mutableListOf<Monkey>()
            .apply {
                addAll(givenMonkeys)
            }
            .toList()
        val numberOfInspectionsByMonkeys = mutableListOf<Int>()
            .apply {
                repeat(givenMonkeys.size) {
                    add(0)
                }
            }

        val divisors = resultMonkeys
            .map { monkey ->
                monkey.testOnItem.divisorValue
            }

        var commonDivisor: ULong = divisors.reduce { acc, i ->
            acc * i
        }

        for (iteration in 0 until numberOfTurns) {
            resultMonkeys.forEachIndexed { monkeyIndex, monkey ->
                numberOfInspectionsByMonkeys[monkeyIndex] += monkey.items.size
                monkey.items.forEachIndexed { itemIndex, item ->
                    println("executeMonkeysForNumberOfTurns ${"iteration" to iteration}, ${"monkeyIndex" to monkeyIndex}, ${"itemIndex" to itemIndex}")
                    // Monkey inspects item
                    println("executeMonkeysForNumberOfTurns inspection start")
                    val itemAfterInspection = monkey.operationOnItem.operation(item)
                    monkey.items[itemIndex] = itemAfterInspection
                    println("executeMonkeysForNumberOfTurns inspection end")
                    // After each monkey inspects an item but before it tests your worry level,
                    // your relief that the monkey's inspection didn't damage the item causes your
                    // worry level to be divided by three and rounded down to the nearest integer.
                    // ONLY FOR PART 1
//                    monkey.items[itemIndex] /= 3
                    // ONLY FOR PART 1

                    val itemAfterRelief = itemAfterInspection % commonDivisor

                    monkey.items[itemIndex] = itemAfterRelief

                    val newItem = monkey.items[itemIndex]
                    // Monkey tests and give item
                    println("executeMonkeysForNumberOfTurns test start")
                    if (monkey.testOnItem.test(newItem)) {
                        resultMonkeys[monkey.testOnItem.monkeyTargetWhenTrue].items += newItem
                    } else {
                        resultMonkeys[monkey.testOnItem.monkeyTargetWhenFalse].items += newItem
                    }
                    println("executeMonkeysForNumberOfTurns test end")
                }
                monkey.items.clear()
            }
        }

        val result = MonkeysExecutorResult(
            monkeysBeforeExecution = givenMonkeys,
            monkeysAfterExecution = resultMonkeys,
            numberOfInspectionsByMonkeys = numberOfInspectionsByMonkeys
        )
//        println("executeMonkeysForNumberOfTurns ${"result" to result}")

        return result
    }
}