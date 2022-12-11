package puzzle11

import FileUtils

class MonkeysParser() {
    fun parseFileToMonkeys(fileContent: String): List<Monkey> {
        println("parseFileToMonkeys")
        val monkeysLines = FileUtils.fileToLines(
            fileContent = fileContent,
            delimiter = MONKEY_DELIMITER
        )
//        println("parseFileToMonkeys ${"monkeysLines" to monkeysLines}")

        val monkeys = monkeysLines
            .map { monkeyContent ->
                parseLinesToMonkey(
                    monkeyContent = monkeyContent,
                )
            }
//        println("parseFileToMonkeys ${"monkeys" to monkeys}")

        return monkeys
    }

    private fun parseLinesToMonkey(monkeyContent: String): Monkey {
        val monkeyLines = FileUtils
            .fileToLines(
                fileContent = monkeyContent,
            )
//        println("parseLinesToMonkey ${"monkeyLines" to monkeyLines}")

        // Get list of starting items
        val startingItems = getListOfStartingItems(
            line = monkeyLines[1],
        )

        // Get operation on item inspection
        val operation = getOperationOnItem(
            line = monkeyLines[2],
        )

        // Get test on item
        val testOnItem = getTestOnItem(
            testLines = monkeyLines.subList(
                fromIndex = 3,
                toIndex = monkeyLines.size,
            )
        )

        val monkey = Monkey(
            monkeyAsString = monkeyContent,
            items = startingItems,
            operationOnItem = operation,
            testOnItem = testOnItem,
        )
//        println("parseLinesToMonkey ${"monkey" to monkey}")
        return monkey
    }

    private fun getListOfStartingItems(line: String): MutableList<ULong> {
        val startingItems = line
            .run {
                FileUtils.fileToLines(
                    fileContent = substring(startIndex = this.indexOf(":") + 1),
                    delimiter = ","
                )
            }
            .map { item ->
                item.toULong()
            }
            .toMutableList()
//        println("getListOfStartingItems ${"startingItems" to startingItems}")

        return startingItems
    }

    private fun getOperationOnItem(line: String): OperationOnItem {
        val operationString = line
            .run {
                substring(startIndex = this.indexOf("=") + 1)
            }
            .trim()
//        println("getOperationOnItem ${"operationString" to operationString}")

        val addOrMultiply: (ULong, ULong) -> ULong = if (operationString.contains("+")) {
            { value1, value2 ->
                value1 + value2
            }
        } else {
            { value1, value2 ->
                value1 * value2
            }
        }
        val variables = operationString
            .split("+", "*")
            .map { variable ->
                variable.trim()
            }

        val otherValue = if (variables[1] == "old") {
            null
        } else {
            variables[1].toULong()
        }

        val operationOnItem = OperationOnItem(
            operationString = line,
            operation = { old ->
//                old
//                if (variables[0] == "old" && variables[1] == "old") {
                addOrMultiply(old, otherValue ?: old)
//                } else {
//                    addOrMultiply(old, variables[1].toBigInteger())
//                }
            },
        )
//        println("getOperationOnItem ${"operationOnItem" to operationOnItem}")
        return operationOnItem
    }

    private fun getTestOnItem(testLines: List<String>): TestOnItem {
        val filteredLines = testLines
            .map { line ->
                line
                    .substring(
                        startIndex = line.lastIndexOf(" ")
                    )
                    .trim()
            }
        // Test: divisible by 23
        val divisorValue = filteredLines[0]
            .toULong()

        // If true: throw to monkey 2
        val monkeyTargetWhenTrue = filteredLines[1]
            .toInt()

        // If false: throw to monkey 3
        val monkeyTargetWhenFalse = filteredLines[2]
            .toInt()
//        println("getTestOnItem ${"testLines" to testLines}")

        val testOnItem = TestOnItem(
            testLines = testLines,
            divisorValue = divisorValue,
            test = { value ->
                value % divisorValue == ULong.MIN_VALUE
            },
            monkeyTargetWhenTrue = monkeyTargetWhenTrue,
            monkeyTargetWhenFalse = monkeyTargetWhenFalse,
        )

//        println("getTestOnItem ${"testOnItem" to testOnItem}")
        return testOnItem
    }

    companion object {
        private const val MONKEY_DELIMITER = "\n\n"
    }
}