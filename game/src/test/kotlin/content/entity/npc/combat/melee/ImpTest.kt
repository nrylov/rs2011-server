package content.entity.npc.combat.melee

import FakeRandom
import WorldTest
import content.entity.combat.target
import npcOption
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.entity.character.mode.combat.CombatMovement
import world.gregs.voidps.engine.entity.character.move.tele
import world.gregs.voidps.engine.entity.character.npc.NPC
import world.gregs.voidps.type.Tile
import world.gregs.voidps.type.setRandom
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ImpTest : WorldTest() {

    private fun noTeleports() = setRandom(object : FakeRandom() {
        override fun nextDouble() = 0.99
    })

    // Real timers and destinations from a fixed seed, but every teleport roll succeeds
    private fun alwaysTeleport() = setRandom(object : Random() {
        val seeded = Random(634)
        override fun nextBits(bitCount: Int) = seeded.nextBits(bitCount)
        override fun nextDouble() = 0.0
    })

    private fun assertFightsBack(imp: NPC) {
        val player = createPlayer(imp.tile.addX(1))
        player.npcOption(imp, "Attack")
        tick(4)
        assertTrue(imp.mode is CombatMovement, "imp mode was ${imp.mode}")
        assertEquals(player, imp.target)
    }

    @Test
    fun `Imp fights back when attacked near its spawn`() {
        noTeleports()
        val imp = createNPC("imp", Tile(3205, 3355))
        assertFightsBack(imp)
    }

    @Test
    fun `Imp teleports on its timer and still fights back where it lands`() {
        alwaysTeleport()
        val imp = createNPC("imp", Tile(3205, 3355))
        val start = imp.tile

        tickIf(limit = 250) { imp.tile == start }

        assertNotEquals(start, imp.tile, "imp should teleport on its timer")
        assertTrue(imp.tile.distanceTo(start) >= 5, "teleports should move at least five tiles")
        assertEquals(start, imp.get<Tile>("respawn_tile"), "respawn location is unchanged")
        noTeleports()
        assertFightsBack(imp)
    }

    @Test
    fun `Imp attacked far from its original spawn fights back once re-anchored`() {
        noTeleports()
        val imp = createNPC("imp", Tile(3205, 3355))
        imp.tele(3220, 3355)
        imp["spawn_tile"] = imp.tile
        assertFightsBack(imp)
    }
}
