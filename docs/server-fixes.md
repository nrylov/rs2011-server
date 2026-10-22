# Ported server fixes

These changes bring the server fixes developed alongside `rs2011-ts` into the normal Kotlin source tree. Each independent fix has its own commit. Only base-game bug fixes are included. The SDK-specific login inventory/equipment/skill snapshot listener is excluded. No SDK, gateway, monitoring or remote-control support is added; the shared message hook precedes quest-message normalization.

|                                Change                                 |                                                 Commit                                                  |
|-----------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| Do not advance tutorial skill stages on XP refreshes                  | [`c4027fc4f5`](https://github.com/nrylov/rs2011-server/commit/c4027fc4f5bf8e64eccf6f10a807100565813b5d) |
| Keep Tutorial Island gates closed while eligible players pass through | [`ad85b1c79e`](https://github.com/nrylov/rs2011-server/commit/ad85b1c79e15814ffd8175f19b9e04951809288b) |
| Display tutorial action refusals while chat history is hidden         | [`ada905a816`](https://github.com/nrylov/rs2011-server/commit/ada905a816cdc587872b533bf3294646df5333f0) |
| Allow firemaking on nonblocking floor decorations                     | [`bf6e6b6fa7`](https://github.com/nrylov/rs2011-server/commit/bf6e6b6fa70b92da4c9f62951b9c887efe6ad903) |
| Show depleted mining rocks when replacement definitions are missing   | [`d42af39243`](https://github.com/nrylov/rs2011-server/commit/d42af39243d971fa8edbeeaa509c2e8d67920a67) |
| Disable autonomous aggression for Tutorial Island rats                | [`2c666dbdf4`](https://github.com/nrylov/rs2011-server/commit/2c666dbdf48761048c028811148194321b07153e) |
| Restore tutorial instructions after client-side interface dismissal   | [`9315cbeb62`](https://github.com/nrylov/rs2011-server/commit/9315cbeb62c3d848084f584a2736ef4afe8c9695) |
| Normalize quest completion titles and punctuation                     | [`6cde9d164f`](https://github.com/nrylov/rs2011-server/commit/6cde9d164fe8124ed116e8582739e75abe18399f) |
| Initialize production menus with an explicit creation type            | [`79f8eb2327`](https://github.com/nrylov/rs2011-server/commit/79f8eb23272346bd9131e5acc20d9a2742784f76) |

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
