package content.entity.player.dialogue.type

import WorldTest
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.queue.strongQueue
import world.gregs.voidps.network.login.protocol.encode.interfaceText

class QuestStartMetadataTest : WorldTest() {
    @Test
    fun `Quest offer sends the title and points loaded from canonical definition fields`() {
        val (player, client) = createClient("quest-offer", emptyTile)
        mockkStatic("world.gregs.voidps.network.login.protocol.encode.InterfaceEncodersKt")
        try {
            player.strongQueue("test_quest_offer") {
                startQuest("the_restless_ghost")
            }
            tick(2)
            verify { client.interfaceText((178 shl 16) or 14, "The Restless Ghost") }
            verify { client.interfaceText((178 shl 16) or 66, "1 Quest Point") }
        } finally {
            unmockkStatic("world.gregs.voidps.network.login.protocol.encode.InterfaceEncodersKt")
        }
    }
}
