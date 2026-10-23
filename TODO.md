# Base-server backlog

Last reviewed: 2026-10-23. See [project context](PROJECT_CONTEXT.md).
Reported issues below are not yet verified against current source unless stated.
SDK/client issues belong in the sibling
[rs2011-ts backlog](https://github.com/nrylov/rs2011-ts/blob/main/TODO.md).

## Next session

- [ ] Coordinate the pending commit-message rewrite with rs2011-ts. Review of the
  19 project commits after upstream `aa566b0d0` is complete; no history rewrite
  has happened. Back up and preserve code trees, topology, identities, dates,
  trailers and signatures; leave upstream history alone. Update commit links in
  `docs/server-fixes.md` afterward in a new commit. User authorized guarded
  force-pushing. Keep the new collaboration documentation intact.
- [ ] Deploy Caitlin/Reese's non-expiring quest weapons at a coordinated restart.
  Base source is committed/tested; compatibility overlay is staged in the SDK
  repo's ignored dev directory. Kayle is already deployed. Verify manifest/hash
  before activation and keep saves and the previous overlay backed up.
- [ ] Playtest Ernest with a regular client and SDK. Verify personal gates/forms,
  fountain/tube collection, transit and full-inventory rewards. Tests cover logic;
  the transformation and secret-wall presentation remain simplified.

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

- [ ] Draynor Manor's specific non-choppable dead trees should attack nearby
  players, often for zero damage. Verify tree IDs, trigger range/timing,
  animations/damage and Animal Magnetism interactions before implementing.
- [ ] Research a complete regular-client lobby with world/social service flow.
  The lightweight client does not need one. A separate lobby server is a design
  possibility, not an established implementation requirement yet.
- [ ] Design optional modular SDK server hooks in coordination with rs2011-ts;
  keep ordinary upstream contributions independent of those hooks.
- [ ] Evaluate a separate clean modular TypeScript server base if prioritized.
  This repository remains the Kotlin base; no server rewrite is underway.

## Recently completed

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
