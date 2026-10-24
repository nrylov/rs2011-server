package content.area.misthalin.draynor_village.manor

import FakeRandom
import WorldTest
import org.junit.jupiter.api.Test
import world.gregs.voidps.engine.entity.character.player.skill.Skill
import world.gregs.voidps.type.Tile
import world.gregs.voidps.type.setRandom
import kotlin.test.assertEquals

class NastyTreeTest : WorldTest() {

    @Test
    fun `Nasty tree swipes at an adjacent player`() {
        setRandom(object : FakeRandom() {
            override fun nextInt(from: Int, until: Int) = until - 1
        })
        val player = createPlayer(Tile(3104, 3352))
        val tree = createNPC("undead_tree_draynor", Tile(3105, 3353))
        val lifepoints = player.levels.get(Skill.Constitution)

        tickIf(limit = 10) { player.levels.get(Skill.Constitution) == lifepoints }

        assertEquals(lifepoints - 20, player.levels.get(Skill.Constitution))
        assertEquals(tree.tile, Tile(3105, 3353))
    }

    @Test
    fun `Nasty tree ignores players out of reach`() {
        setRandom(object : FakeRandom() {
            override fun nextInt(from: Int, until: Int) = until - 1
        })
        val player = createPlayer(Tile(3103, 3352))
        createNPC("undead_tree_draynor", Tile(3105, 3353))
        val lifepoints = player.levels.get(Skill.Constitution)

        tick(10)

        assertEquals(lifepoints, player.levels.get(Skill.Constitution))
    }
}
