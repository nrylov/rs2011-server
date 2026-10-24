package content.area.misthalin.draynor_village.manor

import content.entity.combat.hit.directHit
import world.gregs.voidps.engine.Script
import world.gregs.voidps.engine.entity.character.sound
import world.gregs.voidps.type.random

/**
 * The dead trees around Draynor Manor swipe at players standing next to them.
 * They aren't attackable, so the hit is self-sourced to avoid auto-retaliating against the tree.
 */
class NastyTree : Script {
    init {
        huntPlayer("undead_tree_draynor", "nasty_tree") { target ->
            anim("nasty_tree_attack")
            target.sound("nasty_tree_attack")
            target.directHit(random.nextInt(0, 21))
        }
    }
}
