package com.example.unitconverter.domain.usecase

import com.example.unitconverter.domain.model.LengthUnit
import kotlin.math.roundToInt

/**
 * USE CASE (a.k.a. "Interactor")
 * ------------------------------
 * A UseCase represents ONE single piece of business logic. Here it is: "convert a value
 * from one length unit to another".
 *
 * Why pull this out of the UI/ViewModel?
 *  1. PURE & TESTABLE: It has no Android, no Compose, no state. It takes inputs and returns
 *     an output. You can unit-test it with plain JUnit in milliseconds (no emulator needed).
 *  2. REUSABLE: Any screen or ViewModel can call it.
 *  3. READABLE: The "rule" of the app lives in one tiny, obvious place instead of being
 *     buried inside a Composable's onValueChange lambda.
 *
 * `operator fun invoke` is a Kotlin nicety: it lets callers use the object like a function,
 * e.g. `convertLength(value = 5.0, from = CM, to = METER)` instead of
 * `convertLength.execute(...)`. It reads more naturally.
 */
class ConvertLengthUseCase {

    operator fun invoke(
        value: Double,
        from: LengthUnit,
        to: LengthUnit,
    ): Double {
        // Step 1: normalise the input into a common base unit (meters).
        val valueInMeters = value * from.meterFactor

        // Step 2: convert from meters into whatever the target unit is.
        val converted = valueInMeters / to.meterFactor

        // Step 3: round to 2 decimal places so the UI shows e.g. 12.35 instead of 12.34999.
        return (converted * 100).roundToInt() / 100.0
    }
}
