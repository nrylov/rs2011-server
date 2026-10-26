@[.claude/CLAUDE.md](.claude/CLAUDE.md)

# Base-server development guide

Before changing code, read [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md) and
[TODO.md](TODO.md), then inspect current Git status and relevant source/tests.
These are the shared handoff for Codex, Claude and human contributors. Update
both when work changes durable context or remaining tasks; do not maintain a
separate agent-specific history.

This repository owns ordinary revision-634 gameplay fixes and content. Keep
SDK-only login snapshots, gateway and monitoring features in rs2011-ts or an
optional integration. See [server fix scope](docs/server-fixes.md) and
[quest contribution guide](docs/quest-contributing.md).

Coordinate when another agent is working. Do not stage, revert or overwrite
unrelated work, private files or account saves. Do not control a live character
or restart/deploy services merely to validate a source change; coordinate active
runs first. Keep secrets and local cache/runtime artifacts out of commits.

Verify revision-specific definitions and placements instead of borrowing numeric
IDs from rs-sdk or 2009scape. Respect reference licenses. Run focused meaningful
checks with the proper JDK/cache; distinguish source tests from live visual
verification. Deploy source, data and generated script metadata together.

Keep independent fixes in separate commits, grouping only closely related work.
Use a concrete descriptive title and readable body explaining the problem,
resulting behavior, relevant implementation and validation/limitations. Provide
ample context for a reviewer without dumping implementation inventories or chat
history. Preserve author identity and signing configuration. Do not rewrite
published history unless authorized and coordinated; back up first and use
force-with-lease if rewriting is requested.
