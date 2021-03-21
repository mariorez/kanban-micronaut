package org.seariver.kanbanboard.application.usecase.write

import java.util.*

data class CreateBucketCommand(
    val bucketId: UUID,
    val position: Double,
    val name: String
)