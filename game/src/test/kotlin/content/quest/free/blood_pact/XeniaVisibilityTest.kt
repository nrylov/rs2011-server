package content.quest.free.blood_pact

import WorldTest
import content.quest.instance
import content.quest.quest
import content.quest.questStage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.data.definition.NPCDefinitions
import world.gregs.voidps.type.Tile

class XeniaVisibilityTest : WorldTest() {
    @Test
    fun `Saved named started state resolves to visible surface Xenia without an instance`() {
        val player = createPlayer(Tile(3245, 3198, 0))
        // PlayerVariables persists the named stage, not its wire encoding.
        player.variables.data["blood_pact"] = "started"
        assertEquals("started", player.quest("blood_pact"))
        assertEquals(10, player.questStage("blood_pact"))
        assertEquals(11476, NPCDefinitions.resolve(NPCDefinitions.get("xenia"), player).id)
        assertNull(player.instance())
    }

    @Test
    fun `Xenia keeps the cache forms for unstarted underground rescued and completed stages`() {
        val player = createPlayer(Tile(3245, 3198, 0))
        val xenia = NPCDefinitions.get("xenia")
        for ((stage, form) in listOf("unstarted" to 11476, "watched_cutscene" to -1, "untied_ilona" to 11476, "completed" to 9634)) {
            player["blood_pact"] = stage
            assertEquals(form, NPCDefinitions.resolve(xenia, player).id, stage)
        }
    }
}
