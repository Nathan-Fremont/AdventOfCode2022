package puzzle10

import FileUtils

internal class InstructionsParser {
    private val mapping = mutableMapOf<String, (Int?) -> Instruction>()

    init {
        mapping[NOOP_STRING] = {
            Instruction.Noop
        }
        mapping[ADDX_STRING] = { givenInt ->
            if (givenInt == null) {
                Instruction.Unknown
            } else {
                Instruction.Addx(
                    value = givenInt,
                )
            }
        }
    }

    fun parseFileToInstructions(fileContent: String): List<Instruction> {
        println("parseFileToInstructions")
        val fileLines = FileUtils.fileToLines(fileContent = fileContent)

        return fileLines.map { line ->
            val splitted = line
                .split(" ")

            val newInstruction = mapping
                .getValue(
                key = splitted[0],
                )
                .invoke(
                    splitted.getOrNull(1)?.toInt()
                )

            newInstruction
        }
    }

    companion object {
        private const val NOOP_STRING = "noop"
        private const val ADDX_STRING = "addx"
    }
}