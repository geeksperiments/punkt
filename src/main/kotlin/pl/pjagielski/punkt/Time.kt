package pl.pjagielski.punkt

import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

interface Clock {
    fun currentTime() : LocalDateTime
}

class DefaultClock : Clock {
    override fun currentTime() = LocalDateTime.now()
}

class Metronome(val clock: Clock) {
    lateinit var startAt: LocalDateTime

    val bpm = 100
    val beatsPerBar = 8

    val millisPerBeat: Long
        get() = ((60.0 / bpm) * 1000).toLong()

    val millisPerBar: Long
        get() = millisPerBeat * beatsPerBar

    fun start() {
        startAt = clock.currentTime().plus(150, ChronoUnit.MILLIS)
    }

    fun nextBarAt(): LocalDateTime {
        val currentBar = Math.floorDiv(Duration.between(startAt, clock.currentTime()).toMillis(), millisPerBar)
        println("Current bar $currentBar")
        return startAt.plus((currentBar + 1) * millisPerBar, ChronoUnit.MILLIS)
    }

    fun millisToNextBar() = Duration.between(currentTime(), nextBarAt()).toMillis()

    fun currentTime() = clock.currentTime()
}