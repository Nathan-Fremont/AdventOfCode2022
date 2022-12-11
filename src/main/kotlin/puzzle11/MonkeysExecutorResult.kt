package puzzle11

data class MonkeysExecutorResult(
    val monkeysBeforeExecution: List<Monkey>,
    val monkeysAfterExecution: List<Monkey>,
    val numberOfInspectionsByMonkeys: List<Int>,
)