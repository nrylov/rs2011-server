package content.entity.player.dialogue.type

import WorldTest
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.client.ui.hasOpen
import world.gregs.voidps.engine.client.variable.PlayerVariables
import world.gregs.voidps.engine.queue.strongQueue
import world.gregs.voidps.network.login.protocol.encode.sendVarc

class MakeAmountDefaultTypeTest : WorldTest() {
    @Test
    fun `Opening a production menu transmits the unchanged default Make type`() {
        val (player, client) = createClient("make-menu", emptyTile)
        (player.variables as PlayerVariables).client = client
        player["skill_creation_type"] = "Make"
        mockkStatic("world.gregs.voidps.network.login.protocol.encode.VarcEncoderKt")
        try {
            player.strongQueue("test_creation_menu") {
                makeAmount(listOf("ball_of_wool"), "Make", 1)
            }
            tick(2)
            assertTrue(player.hasOpen("dialogue_skill_creation"))
            verify { client.sendVarc(754, 0) }
        } finally {
            unmockkStatic("world.gregs.voidps.network.login.protocol.encode.VarcEncoderKt")
        }
    }
}
