package puzzle06

internal class SignalHelper {
    fun parseSignalReceived(fileContent: String): Signal {
        println("parseSignalReceived")
        val signalReceived = fileContent
            .trim()
            .replace("\r", "")

        val packetMarketValue = getStartOfPacketMarker(signalReceived)
        val firstMessagePacketMarker = getFirstMessagePacket(
            signalReceived = signalReceived,
            startOfPacketMarker = packetMarketValue + START_OF_PACKET_MARKER_SIZE,
        )
        return Signal(
            signalReceived = signalReceived,
            packetMarkerValue = signalReceived.substring(
                startIndex = packetMarketValue,
                endIndex = packetMarketValue + START_OF_PACKET_MARKER_SIZE,
            ),
            startOfPacketMarker = packetMarketValue + START_OF_PACKET_MARKER_SIZE,
            firstMessagePacketMarkerValue = signalReceived.substring(
                startIndex = firstMessagePacketMarker,
                endIndex = firstMessagePacketMarker + MESSAGE_PACKET_SIZE,
            ),
            firstMessagePacketMarker = firstMessagePacketMarker + MESSAGE_PACKET_SIZE,
        )
    }

    private fun getStartOfPacketMarker(signalReceived: String): Int {
        println("getStartOfPacketMarker")
        signalReceived.forEachIndexed { index, _ ->
            val signalSubstring = signalReceived.substring(
                startIndex = index,
                endIndex = index + START_OF_PACKET_MARKER_SIZE,
            )

            if (signalSubstring.toCharArray().distinct().size == START_OF_PACKET_MARKER_SIZE) {
                return index
            }
        }
        return START_OF_PACKET_NOT_FOUND
    }

    private fun getFirstMessagePacket(
        signalReceived: String,
        startOfPacketMarker: Int,
    ): Int {
        println("getFirstMessagePacket")
        signalReceived
            .substring(startOfPacketMarker)
            .forEachIndexed { index, _ ->
            val signalSubstring = signalReceived.substring(
                startIndex = index,
                endIndex = index + MESSAGE_PACKET_SIZE,
            )

            if (signalSubstring.toCharArray().distinct().size == MESSAGE_PACKET_SIZE) {
                return index
            }
        }
        return MESSAGE_PACKET_NOT_FOUND
    }

    companion object {
        private const val START_OF_PACKET_MARKER_SIZE = 4
        private const val START_OF_PACKET_NOT_FOUND = -1
        private const val MESSAGE_PACKET_SIZE = 14
        private const val MESSAGE_PACKET_NOT_FOUND = -2
    }
}