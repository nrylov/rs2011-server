package world.gregs.voidps.engine.client

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.entity.character.player.chat.ChatType

class QuestCompletionMessagesTest {
    private val prefix = "Congratulations, you've completed a quest: "

    @Test
    fun `All existing completion message variants have canonical casing and one period`() {
        val cases = mapOf(
            "<navy>cook's assistant" to "<navy>Cook's Assistant.",
            "<navy>tears of guthix" to "<navy>Tears of Guthix.",
            "<navy>Jungle Potion" to "<navy>Jungle Potion.",
            "<navy>The Blood Pact" to "<navy>The Blood Pact.",
            "<col=081190>Imp Catcher</col>" to "<col=081190>Imp Catcher</col>.",
            "<col=081190>Rune Mysteries</col>" to "<col=081190>Rune Mysteries</col>.",
        )
        for ((before, after) in cases) {
            assertEquals(prefix + after, QuestCompletionMessages.format(prefix + before))
            assertEquals(prefix + after, QuestCompletionMessages.format(prefix + after))
        }
    }

    @Test
    fun `Canonical lookup overrides fallback casing and unrelated messages remain untouched`() {
        assertEquals(prefix + "Canonical Quest Title.", QuestCompletionMessages.format(prefix + "a quest") { "Canonical Quest Title" })
        assertEquals("An unrelated message.", QuestCompletionMessages.format("An unrelated message."))
        val publicChat = prefix + "<navy>cook's assistant"
        assertEquals(publicChat, GameMessageFeedback.normalize(publicChat, ChatType.Chat))
    }
}
