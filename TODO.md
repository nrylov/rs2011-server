# Base-server backlog

Last reviewed: 2026-10-23. See [project context](PROJECT_CONTEXT.md).
Reported issues below are not yet verified against current source unless stated.
SDK/client issues belong in the sibling
[rs2011-ts backlog](https://github.com/nrylov/rs2011-ts/blob/main/TODO.md).

## Vampire Slayer presentation

- [ ] Review the user's video when supplied locally:
  https://www.youtube.com/watch?v=V3Nl-4motj4 (coffin about 4:37, kill about 5:12).
- [ ] Verify and implement the opening staking sequence and message:
  "You stab the vampire with the stake, but it does not go deep enough."
- [ ] Verify and implement the final staking animation, with safe boss/quest
  cleanup if the action is interrupted. Do not guess incompatible skeleton IDs.
- [ ] Verify periodic "The garlic in your pack weakens the vampire." timing and
  whether historical behavior includes stat reduction. Current garlic suppresses
  regeneration; additional stat drain is not established.
- [ ] Visually confirm the corrected Count attack/death sequences. Cache-backed
  tests establish compatible skeletons; they do not establish animation pacing.

## Gameplay reports needing investigation

Capture a current-source reproduction before choosing a fix. Some reports came
from older runtime builds or agent assumptions and may already be resolved.

- [ ] Following a player reportedly arrives on the same tile rather than adjacent.
- [ ] Tutorial prospecting reportedly advances at click time, before completion.
- [ ] Clicking the same rock again while mining reportedly cancels mining; inspect
  similar repeating resource actions before changing shared cancellation rules.
- [ ] Tutorial smithing reportedly offers non-dagger products. Verify disabled
  presentation and refusal behavior/text; do not invent the historical wording.
- [ ] Equipment can reportedly be worn before the Combat Instructor teaches it.
  Verify which stages/items should be gated and the historical refusal text.
- [ ] Zero-damage hitsplats reportedly do not appear. Compare native client packets
  and rendering; user identified the expected blue-shield appearance.
- [ ] The final tutorial instructor reportedly lacks a guide arrow at his step.
- [ ] NPCs/chickens reportedly keep facing the former opponent after combat ends.
- [ ] Mining sometimes seems unusually slow. Measure attempts/success rates and
  compare revision-appropriate behavior before adjusting probability.

## Deferred content and architecture

- [ ] Visually confirm the Draynor Manor nasty-tree swipe (animation 73, sound
  651) in the rendered client. The four `undead_tree_draynor` spawns east of the
  manor (3137-3143, 3352-3365) share the NPC and also swipe; 2004 had no trees
  there, so confirm that when Animal Magnetism is implemented.
- [ ] Research a complete regular-client lobby with world/social service flow.
  The lightweight client does not need one. A separate lobby server is a design
  possibility, not an established implementation requirement yet.
- [ ] Design optional modular SDK server hooks in coordination with rs2011-ts;
  keep ordinary upstream contributions independent of those hooks.
- [ ] Evaluate a separate clean modular TypeScript server base if prioritized.
  This repository remains the Kotlin base; no server rewrite is underway.

## Upstream contributions

- [ ] Review and submit the separate Ernest, Vampire Slayer and Tutorial Island
  topic branches (independent tests passed: Ernest 10, Vampire 9, Tutorial 138).
  Branch names/worktrees and scope
  are recorded in PROJECT_CONTEXT.md; no upstream PR has been opened yet.
- [ ] Prepare other fixes on separate upstream-based branches when requested.
  The Tutorial Island rat fix is already upstream and needs no duplicate PR.

## Recently completed

- [x] Imps fight back after teleporting: each teleport re-anchors `spawn_tile`
  (respawn still uses `respawn_tile`), so the combat leash follows the imp
  (`Imp.kt`, `ImpTest`). Source only; not yet deployed or watched in-game.

- [x] Merge ten GregHib/void upstream commits through b3ddc3f67, preserving our
  fixes/content and upstream's equivalent rat change. Passed 505 gameplay,
  1,340 engine and 25 cache tests; eight existing engine/cache skips. Source
  only: live game runtime and services were untouched.


- [x] Draynor Manor's non-choppable trees (NPC 152 -> 5207 "Tree") swipe adjacent
  players every 4 ticks for 0-20 lifepoints via the `nasty_tree` hunt mode
  (`NastyTree.kt`, `NastyTreeTest`). Source only; not yet deployed.

- [x] Complete the rendered-client/SDK Ernest playtest, including the full lever
  route, recovery after death, native reward display, repeat-payment guard and
  fresh-login/save persistence. Local report: `dev/notes/ernest-playtest.md`.

- [x] Activate all three Blood Pact private no-expiry drops in the local release
  runtime, with verified overlay hashes, save backups and unchanged XP settings.

- [x] Reword the 58 SDK/client and 19 base-server project commits with readable
  problem/behavior/implementation/validation explanations. Preserved trees,
  topology, identities, dates and contributor trailers; re-signed replacements.
  See [history maintenance](docs/history-maintenance.md) for scope and backups.

- [x] Playable Vampire Slayer, correct original reward, compatible Count combat
  animation definitions and gray native quest entries for unimplemented content.
- [x] Xenia visibility after Blood Pact acceptance; canonical quest-offer metadata.
- [x] Playable Ernest, personal puzzle state, correct revision reward and tests.
- [x] Remove the unused gatestone placeholder's incorrect Ernest-door selector.
- [x] Non-expiring private quest weapons for all three Blood Pact cultists in source.
- [x] Shared context/backlog and Codex/Claude contributor entry points.

When an item is fixed, note the relevant source/tests and deployment state.
Summarize or remove completed entries once reference docs cover them; this is
an actionable backlog, not a full chat transcript.
