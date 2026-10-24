package content.area.misthalin.tutorial_island

import WorldTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.data.definition.NPCDefinitions

class TutorialRatTest : WorldTest() {
    @Test
    fun `Tutorial rats disable autonomous hunting without changing other giant rats`() {
        assertTrue(NPCDefinitions.get("giant_rat_tutorial_island").huntMode.isNullOrEmpty())
        assertEquals("cowardly", NPCDefinitions.get("stronghold_giant_rat").huntMode)
    }
}
