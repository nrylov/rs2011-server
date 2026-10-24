# Base-server contributor context

Last reviewed: 2026-10-23. Read [AGENTS.md](AGENTS.md) and [TODO.md](TODO.md).
These files are shared by humans, Codex and Claude. Current source and tests take
precedence over stale notes.

## Purpose and boundaries

This is the Kotlin revision-634 RuneScape base server. Keep ordinary gameplay
bug fixes and quest implementations here so they can be reviewed or contributed
upstream independently. The sibling rs2011-ts repository owns the TypeScript
SDK, clients, gateway, MCP and optional SDK login-state integration.

The baseline for our current contribution series is upstream `aa566b0d0`.
The 19 subsequent project commits before the collaboration-docs work cover base
fixes and content; upstream history should not be rewritten. See
[ported server fixes](docs/server-fixes.md) for the original nine-fix series.
Its scope statement applies to that series; later commits also add quests.

Do not add SDK-specific login snapshots, monitoring or remote-control endpoints
to the base server. Keep the shared message observer: it supports ordinary
Tutorial Island feedback and quest-message normalization without requiring an SDK.

## Existing changes to know about

- Tutorial progress requires real XP increases. Stage-gated doors/gates use
  personal pass-through so other accounts cannot skip tutors. Refusals remain
  visible while instructions cover the chatbox, and instructions recover from
  silent client-side dismissal without interrupting conversations.
- Firemaking permits nonblocking floor decoration. Missing depleted-rock
  definitions fall back to a visible depleted rock. Tutorial rats do not initiate
  combat. Quest completion messages use canonical titles and punctuation.
- Production menus explicitly send their creation type after products/quantity.
  Quest offers read canonical title and point fields. Accepting The Blood Pact
  uses started wire value 10 so surface Xenia remains visible.
- Vampire Slayer is playable, with a personal Count fight, journal and one-time
  **4,825 Attack XP and 3 quest points**. XP follows the normal server multiplier.
  Count-specific attack/death animations match his cache skeleton. Exact staking
  cinematics and periodic garlic presentation remain unfinished.
- Unimplemented quests are gray in the native quest tab; metadata alone does not
  register a quest as playable.
- Ernest the Chicken is playable with personal fountain/tube/lever puzzles and
  **4 quest points, 3,000 coins, 10 noted eggs and 300 feathers**. Its short
  transformation/passage presentation is not an exact historical cinematic.
  See [implementation details](docs/ernest-the-chicken.md). A [rendered-client/SDK
  playtest](docs/ernest-playtest.md) verified completion, rewards and persistence.
- Draynor Manor's non-choppable dead trees are NPC 152 (multi-NPC on varbit 3185,
  default 5207 "Tree"; 5208 is Animal Magnetism's choppable undead tree). The
  `nasty_tree` hunt mode swipes one adjacent player every 4 ticks for 0-20
  lifepoints, matching 2004 placements and 2009scape's 0-1 HP swipe. The hit is
  self-sourced so players don't auto-retaliate against an unattackable tree.
- All three scripted Blood Pact weapon drops use `FloorItems.NEVER`. They remain
  private and are removed by pickup or instance cleanup. This does not change
  ordinary loot or recreate an already expired weapon.

## Why dungeon_gatestone was changed

The original `dungeon_gatestone` definition claimed client varbit 1795. The
installed revision-634 cache itself decodes 1795 as **bit 1 of varp 668**.
Placed object **138 at (3100,9765,0)** in Ernest's cellar selects its door form
with that varbit: child **47380** has no Open action; child **47381** has Open.
This is direct cache evidence, rather than an inference from the new quest's names.

A source search found no gameplay use of `dungeon_gatestone`. Actual gatestone
handlers in `content/skill/magic/book/dungeoneering/Gatestone.kt` use separate
`gatestone_tile`, `group_gatestone_tile` and `group_gatestone_player` state.
The change preserves the placeholder's name/default/map values as a custom
server variable and removes the incorrect client selector. It should leave
current gatestone mechanics unaffected; a full Dungeoneering playthrough has
not been performed. Do not claim that skill is completely regression-tested.
IDs in other definition namespaces (items, animations, NPCs) can legitimately
also be 1795 and do not establish a varbit's purpose.

## Working on content and validating it

Start with [quest contribution guide](docs/quest-contributing.md),
[Ernest details](docs/ernest-the-chicken.md), and existing neighboring scripts.
Game handlers live under `game/src/main/kotlin/content/`; corresponding TOML
lives under `data/`. Register a quest only when its playable flow exists. Keep
canonical state keys, journal thresholds and cache selectors consistent.

Verify revision-634 IDs, morphs, menu slots and placements against the prepared
cache. Use 2004/2009 projects as behavioral references; their IDs are not portable.
Do not mechanically translate incompatible licensed code. The Lost City/MIT
notice for Ernest's adapted gate conditions is in `LICENSES/lost-city.txt`.

Tests need a **full Java 21 JDK** and the prepared/decrypted revision-634 cache
at `data/cache`. A mismatched/encrypted cache can fail before a world test starts;
a runtime-only JRE cannot provide MockK attachment. Example:

```sh
./gradlew :game:test --tests 'content.quest.free.ernest_the_chicken.*Test'
./gradlew :game:test --tests 'content.quest.free.vampire_slayer.*Test'
./gradlew :game:test --tests 'content.quest.free.blood_pact.BloodPactTest'
```

At the latest implementation checkpoint, 23 related Ernest/Vampire/Cook/Restless
Ghost tests passed; the later Blood Pact lifetime work passed its full quest and
six-outcome lifetime regression. These are historical results, not a substitute
for running checks appropriate to a new change. Follow repository formatting and
verify only the scope you claim. World tests do not prove visual fidelity.

## Source versus local runtime

The local live-development setup belongs to sibling `rs2011-ts/dev/`, which is
private and ignored. It runs an older Void 2.11.0 release plus a compatibility
overlay, not this checkout's full build. Source/data ABI and script registries
must match; do not copy a newly built class into that release indiscriminately.
A normal deployment of this source uses its own generated metadata and matching
data. Base-game fixes do not intrinsically require the old SDK overlay.

On 2026-10-23, the remaining Caitlin/Reese drop changes were activated after
verifying old/staged overlay hashes and backing up saves, configuration and the
previous overlay. All three Blood Pact weapons now share the no-expiry behavior
in the local release. Main game/gateway/client services are online; an agent is
playing, so coordinate reloads. `rs2011-ts/dev/backups/last-weapon-activation.json`
is the private activation record. The user's temporary **10x XP** remains an
ignored local setting, not an upstream default.

## Keeping context useful

Update this file for durable architecture, state encoding or deployment changes.
Update TODO for remaining work, evidence and next steps. Keep account-specific
notes in private bot directories in the SDK repository. Clearly distinguish a
reported symptom, source fix, passing test, deployment and live verification.
Keep unrelated changes separate, and use human-readable commit explanations of
what changed, why, how and what was checked.

## Commit history maintenance

The authorized message rewrite is complete: 58 SDK/client and 19 base-server
project messages now explain the problem, behavior, implementation and checks.
Original code trees, parent topology, identities, dates and contributor trailers
were verified unchanged; replacement commits have new verified signatures.
The shared-context commits retain their messages/content with updated parents.
Upstream server history is untouched. See [history maintenance](docs/history-maintenance.md)
for backup locations and collaboration guidance. Historical private deployment
records retain the hashes actually deployed; do not rewrite those records.
