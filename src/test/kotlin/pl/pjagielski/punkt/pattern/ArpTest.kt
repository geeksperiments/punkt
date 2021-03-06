package pl.pjagielski.punkt.pattern

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import org.junit.jupiter.api.Test
import pl.pjagielski.punkt.melody.*
import pl.pjagielski.punkt.melody.Intervals.minor

class ArpTest {

    @Test
    fun shouldCreateUpArp() {
        val indexes = arp(0, 5, 14, ArpType.UP)

        assertThat(indexes)
            .containsExactly(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1)
    }

    @Test
    fun shouldCreateUpDownArp() {
        val indexes = arp(0, 5, 14, ArpType.UPDOWN)

        assertThat(indexes)
            .containsExactly(0, 1, 2, 3, 2, 1, 0, 1, 2, 3, 2, 1, 0, 1)
    }

    @Test
    fun shouldUseArpWithChordProgression() {
        val scale = Scale(E, minor)
        val progression = listOf(Chord.I, Chord.IV, Chord.VI, Chord.VII)

        val pats = patterns(beats = 8) {
            + scale
                .phrase(degrees(progression.flatMap { arp(it.degrees[0], 4, 6) }), repeat(0.25))
                .synth("test")
        }

        assertThat(pats).extracting { it.midinote }
            .containsExactly(
                E, F.sharp(), G, A, B, E,
                A, B, C.high(), D.high(), E.high(), A,
                C.high(), D.high(), E.high(), F.sharp().high(), G.high(), C.high(),
                D, E, F.sharp(), G, A, D
            )
    }
}
