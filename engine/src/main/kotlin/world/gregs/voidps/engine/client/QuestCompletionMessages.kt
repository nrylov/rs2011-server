package world.gregs.voidps.engine.client

/** Shared formatting for quest completion chat, preserving existing colour tags. */
object QuestCompletionMessages {
    private const val PREFIX = "Congratulations, you've completed a quest: "
    private val tags = Regex("<[^>]*>")
    private val leadingTags = Regex("^(?:<[^>]*>)*")
    private val trailingTags = Regex("(?:</[^>]*>)*$")
    private val smallWords = setOf("a", "an", "and", "at", "for", "in", "of", "on", "the", "to")

    fun format(text: String, canonicalName: (String) -> String? = { null }): String {
        if (!text.startsWith(PREFIX)) return text
        val body = text.removePrefix(PREFIX).trim().removeSuffix(".")
        val plain = body.replace(tags, "").trim().removeSuffix(".")
        if (plain.isEmpty()) return text
        val title = canonicalName(plain) ?: if (plain == plain.lowercase()) {
            plain.split(" ").mapIndexed { index, word ->
                if (index > 0 && word in smallWords) word else word.replaceFirstChar { it.titlecase() }
            }.joinToString(" ")
        } else {
            plain
        }
        return PREFIX + leadingTags.find(body)!!.value + title + trailingTags.find(body)!!.value + "."
    }
}
