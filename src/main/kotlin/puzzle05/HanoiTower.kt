package puzzle05

import java.util.*

internal data class HanoiTower(
    val hanoiStacks: List<Stack<String>>,
    val hanoiMoves: List<HanoiMove>,
)