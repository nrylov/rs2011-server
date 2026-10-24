# Ported server fixes

These changes bring the server fixes developed alongside `rs2011-ts` into the normal Kotlin source tree. Each independent fix has its own commit. Only base-game bug fixes are included. The SDK-specific login inventory/equipment/skill snapshot listener is excluded. No SDK, gateway, monitoring or remote-control support is added; the shared message hook precedes quest-message normalization.

|                                Change                                 |                                                 Commit                                                  |
|-----------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| Do not advance tutorial skill stages on XP refreshes                  | [`84de710865`](https://github.com/nrylov/rs2011-server/commit/84de7108659cd9c1fa29c4839ba21142a7abe7ac) |
| Keep Tutorial Island gates closed while eligible players pass through | [`f86157f6a7`](https://github.com/nrylov/rs2011-server/commit/f86157f6a7309044db6b8c676f9c4a49385244c4) |
| Display tutorial action refusals while chat history is hidden         | [`09131647e1`](https://github.com/nrylov/rs2011-server/commit/09131647e12739e75342ac4c22a3dd5883179667) |
| Allow firemaking on nonblocking floor decorations                     | [`52473e01a3`](https://github.com/nrylov/rs2011-server/commit/52473e01a37d2ee56c41404365233a3b363b06ac) |
| Show depleted mining rocks when replacement definitions are missing   | [`b0b6f54a48`](https://github.com/nrylov/rs2011-server/commit/b0b6f54a481e61f12f474473eae2b4fb15432791) |
| Disable autonomous aggression for Tutorial Island rats                | [`2e571892d3`](https://github.com/nrylov/rs2011-server/commit/2e571892d3c688b1921ecda3f57f3937dfc4268a) |
| Restore tutorial instructions after client-side interface dismissal   | [`e0d7681259`](https://github.com/nrylov/rs2011-server/commit/e0d7681259afa0908539510a3f15fe965d6a75d8) |
| Normalize quest completion titles and punctuation                     | [`c36cb7d72d`](https://github.com/nrylov/rs2011-server/commit/c36cb7d72d38ca11e410f8884b1538763e35ede3) |
| Initialize production menus with an explicit creation type            | [`90714aa76e`](https://github.com/nrylov/rs2011-server/commit/90714aa76e62a0b070d3efd226532ed072b5b042) |

## Validation

Validated with the repository's Gradle 9.7.1 wrapper and a full Java 21 JDK:

- 57 focused tests passed, covering the new behavior and existing mining/creation-menu tests.
- 103 existing tutorial and firemaking tests passed.
- Kotlin compilation and formatting checks passed for the changed files.
- Existing source tests were preserved. Generated script metadata registers the new listeners during a normal build.

World tests need the prepared/decrypted revision-634 cache at `data/cache`. Using an encrypted or mismatched cache can fail world initialization before a test runs. MockK needs a full JDK for agent attachment; a runtime-only JRE is insufficient.

With `JAVA_HOME` pointing to a full Java 21 JDK, run the combined affected test set:

```sh
./gradlew \
  :game:test \
  --tests 'content.area.misthalin.tutorial_island.*' \
  --tests 'content.skill.firemaking.*' \
  --tests 'content.skill.mining.MiningTest' \
  --tests 'content.entity.player.dialogue.type.MakeAmount*Test' \
  :engine:test \
  --tests 'world.gregs.voidps.engine.client.QuestCompletionMessagesTest'
```

## Runtime integration

A normal build includes these changes directly. Run the rebuilt server with its own generated script registry; the compatibility overlay used by the earlier release is no longer needed for these fixes. Local cache files, test saves and JDK installations are not included in the commits.

The live game server was not replaced while porting this code. Its existing saves and running sessions were left alone. This port covers the implemented fixes listed above; other reported issues remain separate work.

## Follow-up: quest offer metadata

Quest definitions store their title and point reward in dedicated `name` and `questPoints` fields. The quest-start screen previously read generic extra parameters instead, resulting in empty titles and omitted quest-point rewards after the real definition loader ran. `QuestStart` now reads the canonical fields directly.

Validation includes the five existing quest-start dialogue tests and a world regression that opens The Restless Ghost's offer using loaded definitions and verifies its title and one-point reward. The mock fixture deliberately provides a conflicting legacy name parameter to catch accidental reuse of the old path. All six tests and changed-file formatting checks pass. This is a base-game fix with no SDK dependency; it takes effect after rebuilding/deploying the game server. The live development server was not restarted for this change.

## Follow-up: Xenia after accepting The Blood Pact

The server encoded the named `blood_pact` state `started` as 1. Revision 634's
cache resolves surface Xenia to a hidden form at that value, making her vanish
as soon as the player accepted the quest. Encode `started` as 10, which
resolves to visible Xenia, while preserving the underground, rescued and
completed state mappings. No cache modifications or per-client overrides
are needed. Persistent saves retain the named state, so existing characters
need no progress reset or save migration.

The full Blood Pact world test now checks that acceptance keeps Xenia visible
at the same surface tile without creating an instance, then completes the
cutscene and entire quest. Two regressions additionally check a saved named
`started` state and the other cache forms. All three tests and scoped Kotlin
formatting checks pass against the prepared cache. The fix requires deploying
the updated server configuration; the live server was left untouched.
