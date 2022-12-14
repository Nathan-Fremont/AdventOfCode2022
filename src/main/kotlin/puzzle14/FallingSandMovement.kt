package puzzle14

sealed interface FallingSandMovement {
    object Bottom: FallingSandMovement
    object DiagonallyLeft: FallingSandMovement
    object DiagonallyRight: FallingSandMovement
    object NoMovementPossible: FallingSandMovement
    object LeadToVoid: FallingSandMovement
}