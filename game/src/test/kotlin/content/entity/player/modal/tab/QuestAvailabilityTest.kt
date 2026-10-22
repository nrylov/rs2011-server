package content.entity.player.modal.tab

import WorldTest
import content.quest.refreshQuestJournal
import content.quest.showUnavailableQuests
import io.mockk.clearStaticMockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.koin.test.inject
import world.gregs.voidps.cache.Cache
import world.gregs.voidps.cache.definition.decoder.ClientScriptDecoder
import world.gregs.voidps.engine.data.definition.EnumDefinitions
import world.gregs.voidps.engine.data.definition.QuestDefinitions
import world.gregs.voidps.engine.data.definition.StructDefinitions
import world.gregs.voidps.network.login.protocol.encode.sendScript
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuestAvailabilityTest : WorldTest() {
    private val definitions: QuestDefinitions by inject()
    private val cache: Cache by inject()

    @Test
    fun `Unimplemented quests receive gray labels while implemented quests keep native progress colours`() {
        val (player, client) = createClient("quest-colours", emptyTile)
        mockkStatic("world.gregs.voidps.network.login.protocol.encode.ScriptEncoderKt")
        try {
            assertTrue(player.interfaces.contains("quest_journals"))
            clearStaticMockk(Class.forName("world.gregs.voidps.network.login.protocol.encode.ScriptEncoderKt").kotlin)
            player.showUnavailableQuests()
            val missing = definitions.get("dragon_slayer")
            val implemented = definitions.get("vampire_slayer")
            val rows = EnumDefinitions.get(StructDefinitions.get(508).get<Int>(61))
            val label = StructDefinitions.get(rows.int(missing.id)).get<String>(845)
            verify(exactly = 1) { client.sendScript(1624, listOf((190 shl 16) or 18, missing.id, "<col=888888>$label</col>")) }
            verify(exactly = 0) { client.sendScript(1624, match { it[1] == implemented.id }) }
            clearStaticMockk(Class.forName("world.gregs.voidps.network.login.protocol.encode.ScriptEncoderKt").kotlin)
            player.refreshQuestJournal()
            verify { client.sendScript(2169, emptyList()) }
            verify { client.sendScript(1624, listOf((190 shl 16) or 18, missing.id, "<col=888888>$label</col>")) }
            clearStaticMockk(Class.forName("world.gregs.voidps.network.login.protocol.encode.ScriptEncoderKt").kotlin)
            player.interfaces.close("quest_journals")
            player.showUnavailableQuests()
            verify(exactly = 0) { client.sendScript(1624, any()) }
        } finally {
            unmockkStatic("world.gregs.voidps.network.login.protocol.encode.ScriptEncoderKt")
        }
    }

    @Test
    fun `Revision 634 label updater selects a dynamic child and sets only its text`() {
        val script = ClientScriptDecoder().load(cache)[1624]
        assertEquals(2, script.intArgumentCount)
        assertEquals(1, script.stringArgumentCount)
        assertTrue(script.instructions.contains(200)) // cc_find: parent and dynamic child index
        assertTrue(script.instructions.contains(1112)) // cc_settext
        assertEquals(0, script.instructions.count { it == 1101 }) // colour remains native; the label supplies gray
    }
}
