# Ernest rendered-client and SDK playtest

Validated on 2026-10-23 using a disposable account, a separate Void 2.11.0
runtime with the tested compatibility overlay/data, and the regular Kotlin/JS
client connected to the TypeScript SDK. The test used separate saves and ports
43598/8084. Main-server characters, credentials and progress were not changed.
The fixture disabled initial tutorial/appearance setup and used 1x XP; the main
runtime retained its private 10x setting.

## Observed behavior

- Veronica's acceptance choice persisted `started`; Oddenstein's explanation
  advanced to `parts`. SDK quest queries agreed with varp 32.
- Ordinary ground pickups supplied fish food, poison and a spade. Mixing,
  compost searching, personal fountain poisoning and gauge collection worked.
- The key unlocked the tube room, and pickup worked from inside. An attempt
  across its solid wall returned the actual "I can't reach that" message.
- Candle/lever passages and both cellar ladders worked. Exiting the cellar
  reset lever/door masks without losing the oil can.
- The standard six-lever solution exercised all nine puzzle doors. SDK queries
  resolved their live cache forms/options, lever operations changed varp 33,
  and each scripted crossing reached its expected opposite-side tile. The
  recorded sequence contains nine lever changes and fifteen crossings including
  the return from the final room. The oil can was collected normally inside it.
- The professor accepted the three parts, the machine statement appeared, and
  the temporary actor changed to human NPC 287 with Ernest's dialogue. The actor
  was removed after completion.
- The native completion screen and SDK both showed completion: **4 quest points,
  3,000 coins, 10 noted eggs and 300 feathers**. All three parts were consumed;
  the egg definition confirmed a noted form rather than ten unstackable eggs.
- Talking to the professor again did not change reward totals. A fresh client
  login retained completion and all rewards. The stopped-server save also
  contains the persisted `completed` state.

## Recovery and SDK findings

This was not an uninterrupted starting-character speedrun. The character died
while idle near the aggressive manor skeleton during separate monitoring work.
Quest progress and personal flags survived. Lost gauge/tube parts were recovered
through normal game interactions; no save, inventory or quest state was edited.

Two initial destinations were inside solid scenery (the fish-food table and
compost footprint); their valid adjacent approaches worked. An optional exterior
waypoint timed out, while direct fountain travel succeeded. Manor front doors
also send "The doors won't open." The SDK previously waited for an opening
before recognizing failure; its separate navigation fix now recognizes singular
and plural door/gate won't-open refusals immediately and reuses the normal
blocked-door rerouting. Three regressions cover the wording and apostrophes.
The active gameplay browser was not refreshed to load that later SDK fix.

## Scope and records

This confirms the playable quest on the release compatibility runtime, ordinary
SDK interactions, native dialogue/completion rendering, reward safety and a
logout/login persistence cycle. Multiplayer puzzle isolation and full-inventory
rewards remain covered by the source world tests; this browser run used one
character and did not repeat every negative path. Exact historical dialogue,
wall/machine cinematics and animation pacing remain outside this check.

Private scripts, QA saves, result JSON, puzzle steps, logs and screenshots are
under `rs2011-ts/dev/validation/ernest-20261023/`, excluded from Git. The QA browser
and its four services were stopped afterward, verifying that main service
listener PIDs were unchanged. The monitoring preview also checked live and
last-known offline positions without a game login from the viewer.
