package org.seariver.kanbanboard.application.domain

import java.util.*

data class Bucket(
    var bucketId: UUID,
    var position: Double,
    var name: String
)
