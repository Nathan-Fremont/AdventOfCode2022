package puzzle06

internal data class Signal(
    val signalReceived: String,
    val packetMarkerValue: String,
    val startOfPacketMarker: Int,
    val firstMessagePacketMarkerValue: String,
    val firstMessagePacketMarker: Int,
)